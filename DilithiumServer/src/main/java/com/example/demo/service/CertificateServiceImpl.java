package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Certificate;
import com.example.demo.repository.CertificateRepository;

import net.thiim.dilithium.provider.DilithiumProvider;
import net.thiim.dilithium.provider.DilithiumSignature;
import net.thiim.dilithium.provider.DilithiumKeyFactory;
import net.thiim.dilithium.provider.DilithiumKeyPairGenerator;
import net.thiim.dilithium.interfaces.DilithiumParameterSpec;
import net.thiim.dilithium.interfaces.DilithiumPrivateKey;
import net.thiim.dilithium.interfaces.DilithiumPrivateKeySpec;
import net.thiim.dilithium.interfaces.DilithiumPublicKey;
import net.thiim.dilithium.interfaces.DilithiumPublicKeySpec;

@Service
public class CertificateServiceImpl implements CertificateService {

	private DilithiumParameterSpec spec;

	@Autowired
	private CertificateRepository CertificateRepository;

	public CertificateServiceImpl() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, IOException {
		spec = new DilithiumParameterSpec[] { DilithiumParameterSpec.LEVEL2, DilithiumParameterSpec.LEVEL3,
				DilithiumParameterSpec.LEVEL5 }[2];
		System.out.print(spec);
		createKeyPair();
	}

	@Override
	public Certificate saveCertificate(Certificate Certificate) {
		return CertificateRepository.save(Certificate);
	}

	@Override
	public Certificate updateCertificate(Certificate Certificate, Long CertificateId) {
		Certificate depDB = CertificateRepository.findById(CertificateId).get();

		if (Objects.nonNull(Certificate.getName()) && !"".equalsIgnoreCase(Certificate.getName())) {
			depDB.setName(Certificate.getName());
		}
		return CertificateRepository.save(depDB);
	}

	@Override
	public void deleteCertificateById(Long CertificateId) {
		CertificateRepository.deleteById(CertificateId);
	}

	@Override
	public boolean signCertificate(Long certificateID) throws NoSuchAlgorithmException, SignatureException,
			InvalidKeyException, InvalidKeySpecException, IOException {
		Certificate certificate = CertificateRepository.findById(certificateID).get();
		// Sign
		try {
			DilithiumPrivateKey privateKey = getPrivateKey();
			Signature sig = Signature.getInstance("Dilithium");
			System.out.println(certificate.toString());
			sig.initSign(privateKey);
			sig.update(certificate.toString().getBytes());
			System.out.println(certificate.toString());
			byte[] signature = sig.sign();
			certificate.setSignature(signature);
			updateCertificate(certificate, certificateID);
			return true;
		} catch (Exception e) {
			
		}
		return false;
	}

	@Override
	public Optional<Certificate> findCertificateById(Long certificateID) {
		return CertificateRepository.findById(certificateID);
	}

	@Override
	public boolean verifyCertificate(Long certificateID)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException, IOException {
		DilithiumPublicKey publicKey = getPublicKey();
		Certificate certificate = CertificateRepository.findById(certificateID).get();
		Signature sig = Signature.getInstance("Dilithium");
		sig.initVerify(publicKey);
		sig.update(certificate.toString().getBytes());
		byte[] byteCertificate = certificate.getSignature();
		return (sig.verify(byteCertificate));

	}

	public KeyPair gen() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		DilithiumProvider provider = new DilithiumProvider();
		Security.addProvider(provider);
		SecureRandom sr = new SecureRandom();
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("Dilithium");
		kpg.initialize(this.spec, sr);
		KeyPair kp = kpg.generateKeyPair();
		return kp;
	}

	@Override
	public boolean createKeyPair() throws IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		try {
			KeyPair keyPair = gen();
			PrivateKey privateKey = keyPair.getPrivate();
			PublicKey publicKey = keyPair.getPublic();

			// Store Public Key.
			DilithiumPublicKeySpec encodedPublicKeySpec = 
					new DilithiumPublicKeySpec(this.spec, publicKey.getEncoded());
			FileOutputStream fos = new FileOutputStream("./public.key");
			fos.write(encodedPublicKeySpec.getBytes());
			fos.close();

			// Store Private Key.
			DilithiumPrivateKeySpec encodedPrivateKeySpec = 
					new DilithiumPrivateKeySpec(this.spec, privateKey.getEncoded());
			fos = new FileOutputStream("./private.key");
			fos.write(encodedPrivateKeySpec.getBytes());
			fos.close();
			return true;
		} catch (Exception e) {
			return false;
		}
		

	}

	@Override
	public DilithiumPrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		byte[] keyBytes = Files.readAllBytes(Paths.get("./private.key"));

		DilithiumPrivateKeySpec spec = new DilithiumPrivateKeySpec(this.spec, keyBytes);
		KeyFactory kf = KeyFactory.getInstance("Dilithium", new DilithiumProvider());
		DilithiumPrivateKey privateKey = (DilithiumPrivateKey) kf.generatePrivate(spec);
		System.out.println(privateKey.toString());
		return privateKey;

	}

	@Override
	public DilithiumPublicKey getPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyBytes = Files.readAllBytes(Paths.get("./public.key"));

		DilithiumPublicKeySpec spec = new DilithiumPublicKeySpec(this.spec, keyBytes);
		KeyFactory kf = KeyFactory.getInstance("Dilithium", new DilithiumProvider());
		DilithiumPublicKey publicKey = (DilithiumPublicKey) kf.generatePublic(spec);
		return publicKey;
	}

	@Override
	public List<Certificate> findAllCertificate() {
		return (List<Certificate>) CertificateRepository.findAll();
	}

	@Override
	public String messageCertificate(Long certificateID) throws NoSuchAlgorithmException, SignatureException,
			InvalidKeyException, InvalidKeySpecException, IOException {
		Certificate certificate = CertificateRepository.findById(certificateID).get();
		return certificate.toString();
	}
}
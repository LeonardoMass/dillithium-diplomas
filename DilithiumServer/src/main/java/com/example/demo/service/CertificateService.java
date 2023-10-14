package com.example.demo.service;
import java.io.IOException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Certificate;


import net.thiim.dilithium.interfaces.DilithiumPrivateKey;
import net.thiim.dilithium.interfaces.DilithiumPublicKey;

public interface CertificateService {
	Certificate saveCertificate(Certificate certificate);
	Optional<Certificate> findCertificateById(Long certificateID);
	List<Certificate> findAllCertificate();
	Certificate updateCertificate(Certificate certificate, Long certificateID);
	void deleteCertificateById(Long certificateID);
	String messageCertificate(Long certificateID) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidKeySpecException, IOException;
	
	boolean signCertificate(Long certificateID) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidKeySpecException, IOException;
	boolean verifyCertificate(Long certificateID)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException, IOException;
	boolean createKeyPair() throws IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException;
	DilithiumPrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException;
	DilithiumPublicKey getPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException;
}
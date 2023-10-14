package com.example.demo.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Certificate;
import com.example.demo.service.CertificateService;
import com.example.demo.service.DilithiumService;

import net.thiim.dilithium.interfaces.DilithiumParameterSpec;
import net.thiim.dilithium.provider.DilithiumProvider;

@CrossOrigin
@RestController
public class DilithiumController {
	@Autowired
	private CertificateService certificateService;


	// Get existed dilithium public key
	@GetMapping("/dilithium/publickey")
	public PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		return certificateService.getPublicKey();
	}
	
	@GetMapping("/dilithium/privatekey")
	public PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		return certificateService.getPrivateKey();
	}

	// Create new dilithium
	@GetMapping("/dilithium")
	public boolean createKeyPair() throws IOException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		System.out.print("Creating private key and public key ...");
		return certificateService.createKeyPair();
	}
	
	@PutMapping("/dilithium")
	public void test() throws Exception
	{
		DilithiumProvider provider = new DilithiumProvider();
		Security.addProvider(provider);
		
		SecureRandom sr = new SecureRandom();
		
		DilithiumParameterSpec[] specs = new DilithiumParameterSpec[] {
				DilithiumParameterSpec.LEVEL2,
				DilithiumParameterSpec.LEVEL3,
				DilithiumParameterSpec.LEVEL5
				
		};
		
		
		final int CNT = 100;
		final int WARMUP = 500;
		boolean warmingup = true;
		System.out.println("Test running...please hold on...");
		for(int i = 0; i < CNT; i++) {
			if(i % 10 == 0) {
				System.out.println("" + i + " out of " + CNT + " iterations.");
			}
			if(i >= WARMUP) {
				warmingup = false;
			}
			for(DilithiumParameterSpec spec : specs) {
				
				// Generate
				KeyPairGenerator kpg = KeyPairGenerator.getInstance("Dilithium");
				kpg.initialize(spec, sr);
				
				long start = System.currentTimeMillis();
				KeyPair kp = kpg.generateKeyPair();
				long end = System.currentTimeMillis();

				// Sign
				Signature sig = Signature.getInstance("Dilithium");
				sig.initSign(kp.getPrivate());
				sig.update("Joy!".getBytes());
				start = System.currentTimeMillis();
				byte[] signature = sig.sign();
				// System.out.printf("Signature" + Arrays.toString(signature));
				end = System.currentTimeMillis();
		
				// Verify
				sig.initVerify(kp.getPublic());
				sig.update("Joy!1".getBytes());
				start = System.currentTimeMillis();
				end = System.currentTimeMillis();
			}
		}
		
	}
}

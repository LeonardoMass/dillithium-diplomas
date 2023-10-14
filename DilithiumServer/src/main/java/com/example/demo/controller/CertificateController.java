package com.example.demo.controller;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Certificate;

import com.example.demo.service.CertificateService;
import com.example.demo.service.DilithiumService;

import net.thiim.dilithium.interfaces.DilithiumPrivateKey;

@CrossOrigin
@RestController
public class CertificateController {

	@Autowired
	private CertificateService certificateService;

	@GetMapping("/certificate/sign/{id}")
	public boolean signCertificate(@PathVariable("id") Long certificateId)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, SignatureException {

		return certificateService.signCertificate(certificateId);
	}
	
	@GetMapping("/certificate/message/{id}")
	public String messageCertificate(@PathVariable("id") Long certificateId)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, SignatureException {
		return certificateService.messageCertificate(certificateId);
	}
	
	@GetMapping("/certificate/verify/{id}")
	public boolean verifyCertificate(@PathVariable("id") Long certificateId)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, SignatureException {

		return certificateService.verifyCertificate(certificateId);
	}
	

	@GetMapping("/certificate")
	public List<Certificate> fetchDepartmentList() {
		return (List<Certificate>) certificateService.findAllCertificate();
	}
	
	@PostMapping("/certificate")
	public Certificate saveCertificate(@RequestBody Certificate certificate) {
		return certificateService.saveCertificate(certificate);
	}

	@GetMapping("/certificate/{id}")
	public Optional<Certificate> fetchCertificate(@PathVariable("id") Long certificateId) {
		return certificateService.findCertificateById(certificateId);
	}

	@PutMapping("/certificate/{id}")
	public Certificate updateCertificate(@RequestBody Certificate certificate, @PathVariable("id") Long certificateId) {
		return certificateService.updateCertificate(certificate, certificateId);
	}

	@DeleteMapping("/certificate/{id}")
	public String deleteCertificateById(@PathVariable("id") Long certificateId) {
		certificateService.deleteCertificateById(certificateId);
		return "Deleted Successfully";
	}
}

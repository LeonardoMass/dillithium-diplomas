package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Certificate;

public interface CertificateRepository extends CrudRepository<Certificate, Long> { }

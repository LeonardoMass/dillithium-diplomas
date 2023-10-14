package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "Certificate")
@Component
public class Certificate {
	@Id
	@GeneratedValue
	@Column(name = "certificateID")
	private Long certificateID;
	@Column(name = "type")
	private String type;
	@Column(name = "name")
	private String name;
	@Column(name = "gender")
	private String gender;
	@Column(name = "birthday")
	private String birthday;
	@Column(name = "spec")
	private String spec;
	@Column(name = "grade")
	private String grade;
	@Column(name = "completeday")
	private String completeday;
	
	@Column(name = "signature",columnDefinition = "BLOB")
	private byte[] signature;
	public Certificate() {
		this.name = "";
	}

	public long getId() {
		return this.certificateID;
	}

	public void setId(long id) {
		this.certificateID = id;
	}

	public String getType() {
		return this.type;
	}

	public void setId(String type) {
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getCompleteday() {
		return completeday;
	}

	public void setCompleteday(String completeday) {
		this.completeday = completeday;
	}
	
	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
	
	public boolean isSigned() {
		return this.signature != null;
	}
	
	public String toString() {
		var builder = new StringBuilder();
        builder.append("Certificate{id=").append(this.certificateID)
        		.append(", type=").append(type)
        		.append(", name=").append(name)
        		.append(", gender=").append(gender)
        		.append(", birthday=").append(birthday)
        		.append(", spec=").append(spec)
        		.append(", grade=").append(grade)
        		.append(", completeday=").append(completeday)
        		.append("}");

        return builder.toString();
	}
	
	
	
	
}
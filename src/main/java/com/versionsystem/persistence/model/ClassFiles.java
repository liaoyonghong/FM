package com.versionsystem.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "CLASS_FILES")
@NamedQuery(name = "ClassFiles.findAll", query = "SELECT m FROM ClassFiles m")
public class ClassFiles {

	@EmbeddedId
	private ClassFilesId classFilesId;
	private Long seq;
	private String company;

	public ClassFiles() {
	}

	public ClassFiles(ClassFilesId classFilesId, Long seq, String company) {
		this.classFilesId = classFilesId;
		this.seq = seq;
		this.company = company;
	}

	public ClassFilesId getClassFilesId() {
		return classFilesId;
	}

	public void setClassFilesId(ClassFilesId classFilesId) {
		this.classFilesId = classFilesId;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}

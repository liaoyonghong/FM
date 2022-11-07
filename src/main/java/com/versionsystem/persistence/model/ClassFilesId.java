package com.versionsystem.persistence.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ClassFilesId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CLASS")
	private String clas;
	private String docid;

	public ClassFilesId() {
	}

	public ClassFilesId(String class_, String docid) {
		this.clas = class_;
		this.docid = docid;
	}

	public String getClas() {
		return clas;
	}

	public void setClas(String clas_) {
		this.clas = clas_;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null || !(other instanceof ClassFilesId))
			return false;

		ClassFilesId castOther = (ClassFilesId) other;
		return ((this.getClas() == castOther.getClas())
			|| (this.getClas() != null && castOther.getClas() != null && this.getClas().equals(castOther.getClas())))
			&& ((this.getDocid() == castOther.getDocid())
			|| (this.getDocid() != null && castOther.getDocid() != null && this.getDocid().equals(castOther.getDocid())));
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + (getClas() == null ? 0 : this.getClas().hashCode());
		result = 37 * result + (getDocid() == null ? 0 : this.getDocid().hashCode());
		return result;
	}
}

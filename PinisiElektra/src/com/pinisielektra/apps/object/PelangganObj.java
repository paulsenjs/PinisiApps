package com.pinisielektra.apps.object;

import java.io.Serializable;

public class PelangganObj implements Serializable {
	private static final long serialVersionUID = 1L;
	private String idPel;
	private String nama;
	private String alamat;
	private String phone;
	private String creator;
	private String dateCreated;
	
	
	public String getIdPel() {
		return idPel;
	}
	public void setIdPel(String idPel) {
		this.idPel = idPel;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
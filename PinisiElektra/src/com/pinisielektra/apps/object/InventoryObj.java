package com.pinisielektra.apps.object;

import java.io.Serializable;

public class InventoryObj implements Serializable{
	private static final long serialVersionUID = 1L;
	private String kodeBarang;
	private String catId;
	private String namaBarang;
	private String satuan;
	private String hargaJual;
	private String hargaBeli;
	private String expDate;
	private String creator;
	private String dateCreated;
	private String editor;
	private String dateEdited;
	
	public String getKodeBarang() {
		return kodeBarang;
	}
	public void setKodeBarang(String kodeBarang) {
		this.kodeBarang = kodeBarang;
	}
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getNamaBarang() {
		return namaBarang;
	}
	public void setNamaBarang(String namaBarang) {
		this.namaBarang = namaBarang;
	}
	public String getSatuan() {
		return satuan;
	}
	public void setSatuan(String satuan) {
		this.satuan = satuan;
	}
	public String getHargaJual() {
		return hargaJual;
	}
	public void setHargaJual(String hargaJual) {
		this.hargaJual = hargaJual;
	}
	public String getHargaBeli() {
		return hargaBeli;
	}
	public void setHargaBeli(String hargaBeli) {
		this.hargaBeli = hargaBeli;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
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
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getDateEdited() {
		return dateEdited;
	}
	public void setDateEdited(String dateEdited) {
		this.dateEdited = dateEdited;
	}
}

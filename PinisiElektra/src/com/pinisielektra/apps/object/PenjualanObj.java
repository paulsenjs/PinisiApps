package com.pinisielektra.apps.object;

import java.io.Serializable;

public class PenjualanObj implements Serializable {
	private static final long serialVersionUID = 1L;
	private String idJual;
	private String kodeBarang;
	private String namaBarang;
	private String tglTransaksi;
	private String satuan;
	private String creator;
	private String dateCreated;
	private String editor;
	private String dateEdited;
	private String hargaBarang;
	
	public String getIdJual() {
		return idJual;
	}
	public void setIdJual(String idJual) {
		this.idJual = idJual;
	}
	public String getKodeBarang() {
		return kodeBarang;
	}
	public void setKodeBarang(String kodeBarang) {
		this.kodeBarang = kodeBarang;
	}
	public String getNamaBarang() {
		return namaBarang;
	}
	public void setNamaBarang(String namaBarang) {
		this.namaBarang = namaBarang;
	}
	public String getTglTransaksi() {
		return tglTransaksi;
	}
	public void setTglTransaksi(String tglTransaksi) {
		this.tglTransaksi = tglTransaksi;
	}
	public String getSatuan() {
		return satuan;
	}
	public void setSatuan(String satuan) {
		this.satuan = satuan;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getHargaBarang() {
		return hargaBarang;
	}
	public void setHargaBarang(String hargaBarang) {
		this.hargaBarang = hargaBarang;
	}
}

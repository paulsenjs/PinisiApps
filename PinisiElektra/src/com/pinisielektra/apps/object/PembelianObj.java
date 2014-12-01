package com.pinisielektra.apps.object;

import java.io.Serializable;

public class PembelianObj implements Serializable{
	private static final long serialVersionUID = 1L;
	private String idTrans;
	private String kodeBarang;
	private String tglTransaksi;
	private String satuan;
	private String kodeDistributor;
	private String creator;
	private String dateCreated;
	private String editor;
	private String dateEdited;
	
	
	public String getIdTrans() {
		return idTrans;
	}
	public void setIdTrans(String idTrans) {
		this.idTrans = idTrans;
	}
	public String getKodeBarang() {
		return kodeBarang;
	}
	public void setKodeBarang(String kodeBarang) {
		this.kodeBarang = kodeBarang;
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
	public String getKodeDistributor() {
		return kodeDistributor;
	}
	public void setKodeDistributor(String kodeDistributor) {
		this.kodeDistributor = kodeDistributor;
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

package com.pinisielektra.apps.object;

public class ReportObj {
	
	//penjualan
	private int id;
	private String kodeBarang;
	private String tglTransaksi;
	
	
	//pembelian
	private String kodeDistributor;


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the kodeBarang
	 */
	public String getKodeBarang() {
		return kodeBarang;
	}


	/**
	 * @param kodeBarang the kodeBarang to set
	 */
	public void setKodeBarang(String kodeBarang) {
		this.kodeBarang = kodeBarang;
	}


	/**
	 * @return the tglTransaksi
	 */
	public String getTglTransaksi() {
		return tglTransaksi;
	}


	/**
	 * @param tglTransaksi the tglTransaksi to set
	 */
	public void setTglTransaksi(String tglTransaksi) {
		this.tglTransaksi = tglTransaksi;
	}


	/**
	 * @return the kodeDistributor
	 */
	public String getKodeDistributor() {
		return kodeDistributor;
	}


	/**
	 * @param kodeDistributor the kodeDistributor to set
	 */
	public void setKodeDistributor(String kodeDistributor) {
		this.kodeDistributor = kodeDistributor;
	}
	
}

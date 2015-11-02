package com.thousandsunny.kemis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Keluarga {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("no_kk")
    @Expose
    private String noKk;
    @SerializedName("nama_kepala")
    @Expose
    private String namaKepala;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("luas_lantai")
    @Expose
    private Integer luasLantai;
    @SerializedName("jenis_lantai")
    @Expose
    private Integer jenisLantai;
    @SerializedName("jenis_dinding")
    @Expose
    private Integer jenisDinding;
    @SerializedName("toilet")
    @Expose
    private Integer toilet;
    @SerializedName("listrik")
    @Expose
    private Integer listrik;
    @SerializedName("air")
    @Expose
    private Integer air;
    @SerializedName("bahan_bakar_masak")
    @Expose
    private Integer bahanBakarMasak;
    @SerializedName("daging_susu")
    @Expose
    private Integer dagingSusu;
    @SerializedName("baju")
    @Expose
    private Integer baju;
    @SerializedName("makan")
    @Expose
    private Integer makan;
    @SerializedName("bayar_obat")
    @Expose
    private Integer bayarObat;
    @SerializedName("pendapatan")
    @Expose
    private Integer pendapatan;
    @SerializedName("pendidikan")
    @Expose
    private Integer pendidikan;
    @SerializedName("tabungan")
    @Expose
    private Integer tabungan;
    @SerializedName("foto_keluarga")
    @Expose
    private String fotoKeluarga;
    @SerializedName("foto_kk")
    @Expose
    private Object fotoKk;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("no_rt")
    @Expose
    private Integer noRt;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The noKk
     */
    public String getNoKk() {
        return noKk;
    }

    /**
     *
     * @param noKk
     * The no_kk
     */
    public void setNoKk(String noKk) {
        this.noKk = noKk;
    }

    /**
     *
     * @return
     * The namaKepala
     */
    public String getNamaKepala() {
        return namaKepala;
    }

    /**
     *
     * @param namaKepala
     * The nama_kepala
     */
    public void setNamaKepala(String namaKepala) {
        this.namaKepala = namaKepala;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The alamat
     */
    public String getAlamat() {
        return alamat;
    }

    /**
     *
     * @param alamat
     * The alamat
     */
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    /**
     *
     * @return
     * The luasLantai
     */
    public Integer getLuasLantai() {
        return luasLantai;
    }

    /**
     *
     * @param luasLantai
     * The luas_lantai
     */
    public void setLuasLantai(Integer luasLantai) {
        this.luasLantai = luasLantai;
    }

    /**
     *
     * @return
     * The jenisLantai
     */
    public Integer getJenisLantai() {
        return jenisLantai;
    }

    /**
     *
     * @param jenisLantai
     * The jenis_lantai
     */
    public void setJenisLantai(Integer jenisLantai) {
        this.jenisLantai = jenisLantai;
    }

    /**
     *
     * @return
     * The jenisDinding
     */
    public Integer getJenisDinding() {
        return jenisDinding;
    }

    /**
     *
     * @param jenisDinding
     * The jenis_dinding
     */
    public void setJenisDinding(Integer jenisDinding) {
        this.jenisDinding = jenisDinding;
    }

    /**
     *
     * @return
     * The toilet
     */
    public Integer getToilet() {
        return toilet;
    }

    /**
     *
     * @param toilet
     * The toilet
     */
    public void setToilet(Integer toilet) {
        this.toilet = toilet;
    }

    /**
     *
     * @return
     * The listrik
     */
    public Integer getListrik() {
        return listrik;
    }

    /**
     *
     * @param listrik
     * The listrik
     */
    public void setListrik(Integer listrik) {
        this.listrik = listrik;
    }

    /**
     *
     * @return
     * The air
     */
    public Integer getAir() {
        return air;
    }

    /**
     *
     * @param air
     * The air
     */
    public void setAir(Integer air) {
        this.air = air;
    }

    /**
     *
     * @return
     * The bahanBakarMasak
     */
    public Integer getBahanBakarMasak() {
        return bahanBakarMasak;
    }

    /**
     *
     * @param bahanBakarMasak
     * The bahan_bakar_masak
     */
    public void setBahanBakarMasak(Integer bahanBakarMasak) {
        this.bahanBakarMasak = bahanBakarMasak;
    }

    /**
     *
     * @return
     * The dagingSusu
     */
    public Integer getDagingSusu() {
        return dagingSusu;
    }

    /**
     *
     * @param dagingSusu
     * The daging_susu
     */
    public void setDagingSusu(Integer dagingSusu) {
        this.dagingSusu = dagingSusu;
    }

    /**
     *
     * @return
     * The baju
     */
    public Integer getBaju() {
        return baju;
    }

    /**
     *
     * @param baju
     * The baju
     */
    public void setBaju(Integer baju) {
        this.baju = baju;
    }

    /**
     *
     * @return
     * The makan
     */
    public Integer getMakan() {
        return makan;
    }

    /**
     *
     * @param makan
     * The makan
     */
    public void setMakan(Integer makan) {
        this.makan = makan;
    }

    /**
     *
     * @return
     * The bayarObat
     */
    public Integer getBayarObat() {
        return bayarObat;
    }

    /**
     *
     * @param bayarObat
     * The bayar_obat
     */
    public void setBayarObat(Integer bayarObat) {
        this.bayarObat = bayarObat;
    }

    /**
     *
     * @return
     * The pendapatan
     */
    public Integer getPendapatan() {
        return pendapatan;
    }

    /**
     *
     * @param pendapatan
     * The pendapatan
     */
    public void setPendapatan(Integer pendapatan) {
        this.pendapatan = pendapatan;
    }

    /**
     *
     * @return
     * The pendidikan
     */
    public Integer getPendidikan() {
        return pendidikan;
    }

    /**
     *
     * @param pendidikan
     * The pendidikan
     */
    public void setPendidikan(Integer pendidikan) {
        this.pendidikan = pendidikan;
    }

    /**
     *
     * @return
     * The tabungan
     */
    public Integer getTabungan() {
        return tabungan;
    }

    /**
     *
     * @param tabungan
     * The tabungan
     */
    public void setTabungan(Integer tabungan) {
        this.tabungan = tabungan;
    }

    /**
     *
     * @return
     * The fotoKeluarga
     */
    public String getFotoKeluarga() {
        return fotoKeluarga;
    }

    /**
     *
     * @param fotoKeluarga
     * The foto_keluarga
     */
    public void setFotoKeluarga(String fotoKeluarga) {
        this.fotoKeluarga = fotoKeluarga;
    }

    /**
     *
     * @return
     * The fotoKk
     */
    public Object getFotoKk() {
        return fotoKk;
    }

    /**
     *
     * @param fotoKk
     * The foto_kk
     */
    public void setFotoKk(Object fotoKk) {
        this.fotoKk = fotoKk;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The noRt
     */
    public Integer getNoRt() {
        return noRt;
    }

    /**
     *
     * @param noRt
     * The no_rt
     */
    public void setNoRt(Integer noRt) {
        this.noRt = noRt;
    }

}
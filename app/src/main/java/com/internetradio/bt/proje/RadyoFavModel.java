package com.internetradio.bt.proje;

/**
 * Created by HakanKurt on 17.12.2017.
 */

public class RadyoFavModel {
    private int id;
    private String dbRadyoAd;
    private String dbRadyoUrl;
    private byte[] dbRadyoImg;
    private String dbRadyoKategori;

    public RadyoFavModel(int id, String dbRadyoAd, String dbRadyoUrl, byte[] dbRadyoImg, String dbRadyoKategori) {
        this.id = id;
        this.dbRadyoAd = dbRadyoAd;
        this.dbRadyoUrl = dbRadyoUrl;
        this.dbRadyoImg = dbRadyoImg;
        this.dbRadyoKategori = dbRadyoKategori;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDbRadyoAd() {
        return dbRadyoAd;
    }

    public void setDbRadyoAd(String dbRadyoAd) {
        this.dbRadyoAd = dbRadyoAd;
    }

    public String getDbRadyoUrl() {
        return dbRadyoUrl;
    }

    public void setDbRadyoUrl(String dbRadyoUrl) {
        this.dbRadyoUrl = dbRadyoUrl;
    }

    public byte[] getDbRadyoImg() {
        return dbRadyoImg;
    }

    public void setDbRadyoImg(byte[] dbRadyoImg) {
        this.dbRadyoImg = dbRadyoImg;
    }

    public String getDbRadyoKategori() {
        return dbRadyoKategori;
    }

    public void setDbRadyoKategori(String dbRadyoKategori) {
        this.dbRadyoKategori = dbRadyoKategori;
    }
}

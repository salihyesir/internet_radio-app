package com.internetradio.bt.proje;



public class RadioModel {

    private String radyoAd;
    private String radyoUrl;
    private String radyoImg;
    private String radyoKategori;
    private String radyoDescription;

    public RadioModel(){

    }

    public RadioModel(String radyoAd,String radyoUrl,String radyoImg,String radyoDescription, String radyoKategori){
        this.radyoAd=radyoAd;
        this.radyoUrl=radyoUrl;
        this.radyoImg=radyoImg;
        this.radyoKategori=radyoKategori;
        this.radyoDescription=radyoDescription;
    }

    public String getRadyoAd() {
        return radyoAd;
    }

    public void setRadyoAd(String radyoAd) {
        this.radyoAd = radyoAd;
    }

    public String getRadyoUrl() {
        return radyoUrl;
    }

    public void setRadyoUrl(String radyoUrl) {
        this.radyoUrl = radyoUrl;
    }

    public String getRadyoImg() {
        return radyoImg;
    }

    public void setRadyoImg(String radyoImg) {
        this.radyoImg = radyoImg;
    }

    public String getRadyoKategori() {
        return radyoKategori;
    }

    public void setRadyoKategori(String radyoKategori) {
        this.radyoKategori = radyoKategori;
    }

    public String getRadyoDescription() {
        return radyoDescription;
    }

    public void setRadyoDescription(String radyoDescription) {
        this.radyoDescription = radyoDescription;
    }




}

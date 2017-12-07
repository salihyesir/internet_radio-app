package com.internetradio.bt.proje;

/**
 * Created by HakanKurt on 7.12.2017.
 */

public class RadioModel {

    private String radyoAd;
    private String radyoUrl;
    private String radyoImg;


    public RadioModel(){

    }

    public RadioModel(String radyoAd,String radyoUrl,String radyoImg){
        this.radyoAd=radyoAd;
        this.radyoUrl=radyoUrl;
        this.radyoImg=radyoImg;
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


}

package com.example.projeodevi;

import java.util.ArrayList;

public class Kitap extends Yayinlanmislar {
    private String kitapAd;
    private String yazarAd;
    private String yayineviAd;
    private String tarih;
    private int stok;

    public Kitap(String kitapAd, String yazarAd, String yayineviAd, String tarih, int stok) {
        this.kitapAd = kitapAd;
        this.yazarAd = yazarAd;
        this.yayineviAd = yayineviAd;
        this.tarih = tarih;
        this.stok = stok;
    }

    public String getKitapAd() {
        return kitapAd;
    }

    public String getYazarAd() {
        return yazarAd;
    }

    public String getYayineviAd() {
        return yayineviAd;
    }

    public String getTarih() {
        return tarih;
    }

    public int getStok() {
        return stok;
    }

    public void addArrayList(){
        addToArrayList(this);
    }

    public ArrayList<Kitap> getArrayList(){
        return printArrayList();
    }

    @Override
    public boolean kitapSat(int adet) {
        if(adet > this.stok){
            System.out.println("Yeterli Stok Yok!");
            return false;
        }
        else {
            this.stok -= adet;
            return true;
        }
    }
}

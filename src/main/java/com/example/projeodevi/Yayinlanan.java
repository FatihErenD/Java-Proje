package com.example.projeodevi;

import java.util.ArrayList;

public abstract class Yayinlanan {
    private static ArrayList<Kitap> kitaplarListesi = new ArrayList<>();

    public void addToArrayList(Kitap o){
        kitaplarListesi.add(o);
    }

    public ArrayList<Kitap> returnArrayList(){
        return kitaplarListesi;
    }

    public void clearArrayList(){
        kitaplarListesi.clear();
    }

    public abstract boolean kitapSat(int adet);
}

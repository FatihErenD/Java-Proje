package com.example.projeodevi;

import java.util.ArrayList;

public abstract class Yayinlanmislar {
    private static ArrayList<Kitap> kitaplarListesi = new ArrayList<>();

    public void addToArrayList(Kitap o){
        kitaplarListesi.add(o);
    }

    public ArrayList<Kitap> printArrayList(){
        return kitaplarListesi;
    }

    public abstract boolean kitapSat(int adet);
}

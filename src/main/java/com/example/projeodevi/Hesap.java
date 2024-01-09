package com.example.projeodevi;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Hesap implements Hesaplar {
    private final File file = new File("hesaplar.txt");
    private ArrayList<String> hesaplar = new ArrayList<>();

    @Override
    public ArrayList<String> hesaplarArray() {
        try {
            BufferedReader read = new BufferedReader(new FileReader(file));
            String hesapAd;
            while ((hesapAd = read.readLine()) != null) {
                String sifre = read.readLine();
                hesaplar.add(hesapAd);
                hesaplar.add(sifre);
            }
            return hesaplar;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hata");
            alert.setHeaderText("Giriş Yapılamadı");
            alert.show();
            return null;
        }
    }
}

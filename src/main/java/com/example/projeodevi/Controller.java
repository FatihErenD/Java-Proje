package com.example.projeodevi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.*;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private final Kitap k = new Kitap("", "", "", "", 0);
    private final Hesap hesap = new Hesap();

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label yanlisHesap, satBilgiLabel, yayinlaBilgiLabel;

    @FXML
    private AnchorPane book_publish, published_books, sell_book;

    @FXML
    private TableView<Kitap> tableView = new TableView<>();

    @FXML
    private TableColumn<Kitap, String> nameColumn = new TableColumn<>("kitapAd");

    @FXML
    private TableColumn<Kitap, String> authorColumn = new TableColumn<>("yazarAd");

    @FXML
    private TableColumn<Kitap, String> publisherColumn = new TableColumn<>("yayineviAd");

    @FXML
    private TableColumn<Kitap, String> dateColumn = new TableColumn<>("tarih");

    @FXML
    private TableColumn<Kitap, String> totalColumn = new TableColumn<>("stok");

    @FXML
    private TextField bookName, authorName, publisherName, bookTotalNumber, sellBookName, sellBookNumber;

    @FXML
    private DatePicker dateSelect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        ArrayList<String> hesaplar = hesap.hesaplarArray();
        int index = hesaplar.indexOf(username.getText());
        if (index % 2 == 0 && Objects.equals(hesaplar.get(index + 1), password.getText())) {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-menu.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Kitap Yayıncılığı Otomasyonu");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Hata");
                alert.setHeaderText("Giriş Yapılamadı");
                alert.show();
            }
        }
        else {
            yanlisHesap.setText("Bilgiler Yanlış");
        }
    }

    @FXML
    void onBookPublish(ActionEvent event) {
        book_publish.toFront();
    }

    @FXML
    void onSell(ActionEvent event) {
        satBilgiLabel.setTextFill(Color.color(1, 0, 0));
        if(!sellBookName.getText().isEmpty() && !sellBookNumber.getText().isEmpty()){
            ArrayList<Kitap> arrayList = k.getArrayList();
            int boyut = arrayList.size();
            boolean buldu = false, sattiMi = true;
            String kitap_adi = sellBookName.getText();
            for (int i = 0; i < boyut; i++) {
                if (Objects.equals(arrayList.get(i).getKitapAd(), kitap_adi)) {
                    sattiMi = arrayList.get(i).kitapSat(Integer.parseInt(sellBookNumber.getText()));
                    buldu = true;
                }
            }
            if (!buldu)
                satBilgiLabel.setText("Aradığınız Kitap Bulunamadı");
            else {
                if (sattiMi) {
                    satBilgiLabel.setTextFill(Color.color(0, 1, 0));
                    satBilgiLabel.setText("Başarıyla Satıldı");
                    sellBookName.clear();
                    sellBookNumber.clear();
                    tableView.getItems().setAll(k.getArrayList());
                } else
                    satBilgiLabel.setText("Yeterli Stok Yok");
            }
        }
    }

    @FXML
    void onPublishedBooks(ActionEvent event) {
        published_books.toFront();
    }

    @FXML
    void onSellBook(ActionEvent event) {
        sell_book.toFront();
    }

    @FXML
    void onSaveToFile(ActionEvent event) {
        FileChooser selectFile = new FileChooser();
        selectFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
        selectFile.setTitle("Dosya Seç");
        File file = selectFile.showSaveDialog(stage);
        try {
            if(!file.getName().contains(".")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }
            PrintWriter write = new PrintWriter(new FileOutputStream(file));
            ArrayList<Kitap> array = k.getArrayList();
            for (Kitap o : array){
                write.println(o.getKitapAd());
                write.println(o.getYazarAd());
                write.println(o.getYayineviAd());
                write.println(o.getTarih());
                write.println(o.getStok());
            }
            write.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dosya Açılamadı");
            alert.setHeaderText("Dosya Açılamadı");
            alert.show();
        }
    }

    @FXML
    void onLoadFromFile(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
        alert.setTitle("Uyarı");
        alert.setHeaderText("Şimdiye kadar ki eklemeleriniz silinecektir. Devam etmek istediğinize emin misiniz?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            FileChooser selectFile = new FileChooser();
            selectFile.setTitle("Dosya Seç");
            File file = selectFile.showOpenDialog(stage);
            try (BufferedReader read = new BufferedReader(new FileReader(file))) {
                String satir;
                k.clearArrayList();
                while ((satir = read.readLine()) != null) {
                    String yazarAd = read.readLine();
                    String yayineviAd = read.readLine();
                    String tarih = read.readLine();
                    int stok = Integer.parseInt(read.readLine());
                    k.addToArrayList(new Kitap(satir, yazarAd, yayineviAd, tarih, stok));
                }
                tableView.setEditable(true);
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("kitapAd"));
                authorColumn.setCellValueFactory(new PropertyValueFactory<>("yazarAd"));
                publisherColumn.setCellValueFactory(new PropertyValueFactory<>("yayineviAd"));
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("tarih"));
                totalColumn.setCellValueFactory(new PropertyValueFactory<>("stok"));
                tableView.getItems().setAll(k.getArrayList());
                }
                catch (IOException e) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Dosya Açılamadı");
                    alert2.setHeaderText("Dosya Açılamadı");
                    alert2.show();
                }
            }
        }

    @FXML
    void onPublishBook(ActionEvent event) {
        yayinlaBilgiLabel.setTextFill(Color.color(1, 0, 0));
        if(!bookName.getText().isEmpty() && !authorName.getText().isEmpty() && !publisherName.getText().isEmpty() && !bookTotalNumber.getText().isEmpty() && dateSelect.getValue() != null){
            try {
                int stokSayiyi = Integer.parseInt(bookTotalNumber.getText());
                Kitap kitap = new Kitap(bookName.getText(), authorName.getText(), publisherName.getText(), dateSelect.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), stokSayiyi);
                kitap.addArrayList();
                tableView.setEditable(true);
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("kitapAd"));
                authorColumn.setCellValueFactory(new PropertyValueFactory<>("yazarAd"));
                publisherColumn.setCellValueFactory(new PropertyValueFactory<>("yayineviAd"));
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("tarih"));
                totalColumn.setCellValueFactory(new PropertyValueFactory<>("stok"));
                tableView.getItems().setAll(kitap.getArrayList());
                yayinlaBilgiLabel.setTextFill(Color.color(0, 1, 0));
                yayinlaBilgiLabel.setText("Başarıyla Yayınlandı");
                bookName.clear();
                authorName.clear();
                publisherName.clear();
                bookTotalNumber.clear();
            }
            catch (Exception NumberFormatException){
                yayinlaBilgiLabel.setText("Geçersiz Değer");
            }
        }
        else
            yayinlaBilgiLabel.setText("Geçersiz Değer");
    }

    @FXML
    void onExitButtonClicked(ActionEvent event){
        try{
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-screen.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Giriş");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hata");
            alert.setHeaderText("Çıkış Yapılamadı");
            alert.show();
        }
    }
}

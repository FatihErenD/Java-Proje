package com.example.projeodevi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label yanlisHesap, satBilgiLabel;

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
    void onLoginButtonClick(ActionEvent event) throws IOException {
        if(Objects.equals(username.getText(), "AA") && Objects.equals(password.getText(), "123")){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-menu.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Kitap Yayıncılığı Otomasyonu");
            stage.setScene(scene);
            stage.show();
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
            Kitap kitap = new Kitap("", "", "", "", 0);
            ArrayList<Kitap> arrayList = kitap.getArrayList();
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
                    tableView.getItems().setAll(kitap.getArrayList());
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

    }

    @FXML
    void onLoadFromFile(ActionEvent event) {

    }

    @FXML
    void onPublishBook(ActionEvent event) throws IOException {
        if(!bookName.getText().isEmpty() && !authorName.getText().isEmpty() && !publisherName.getText().isEmpty() && !bookTotalNumber.getText().isEmpty() && dateSelect.getValue() != null){
            Kitap kitap = new Kitap(bookName.getText(), authorName.getText(), publisherName.getText(), dateSelect.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), Integer.parseInt(bookTotalNumber.getText()));
            kitap.addArrayList();
            tableView.setEditable(true);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("kitapAd"));
            authorColumn.setCellValueFactory(new PropertyValueFactory<>("yazarAd"));
            publisherColumn.setCellValueFactory(new PropertyValueFactory<>("yayineviAd"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("tarih"));
            totalColumn.setCellValueFactory(new PropertyValueFactory<>("stok"));
            tableView.getItems().setAll(kitap.getArrayList());
        }
    }

    @FXML
    void onExitButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-screen.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Giriş");
        stage.setScene(scene);
        stage.show();
    }
}
package com.example.projeodevi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button loginButton;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label yanlisHesap;

    @FXML
    private Button exitButton;


    @FXML
    void onLoginButtonClick(ActionEvent event) throws IOException {
        if(Objects.equals(username.getText(), "AA") && Objects.equals(password.getText(), "123")){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-menu.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            yanlisHesap.setText("Bilgiler Yanlış");
        }
    }

    @FXML
    void onExitButtonClicked(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-screen.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
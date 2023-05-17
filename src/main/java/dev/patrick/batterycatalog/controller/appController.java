package dev.patrick.batterycatalog.controller;

import dev.patrick.batterycatalog.DAO.VeiculoDAO;
import dev.patrick.batterycatalog.model.VeiculoModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class appController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField txtFiltro;
    @FXML
    private ScrollPane scPane;
    @FXML
    private ListView<VeiculoModel> lvVeiculos;
    @FXML
    private Label lblAH;
    @FXML
    private Label lblBatteryCode;
    @FXML
    private Label lblCCA;
    @FXML
    private Label lblCarModel;
    @FXML
    private Label lblDimension;
    @FXML
    private Label lblPolarity;
    @FXML
    private Label lblTecnologyTipe;
    @FXML
    private ListView<?> listTools;
    VeiculoModel vm;

    @FXML
    void cadVeiculo() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addCar.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> {
            atualizaGrade();
        });
        stage.show();
    }

    private void atualizaGrade() {
        txtFiltro.setOnKeyReleased(event -> {
            try {
                lvVeiculos.setItems(VeiculoDAO.listar(txtFiltro.getText().trim()));
                scPane.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Desabilita a lista ao clicar fora dela
            if (scPane.isVisible()) {
                anchorPane.setOnMouseClicked(ev -> {
                    scPane.setVisible(false);
                });
            }
        });
        // Oculta a lista quando clicada duas vezes
        lvVeiculos.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                try {
                    vm = lvVeiculos.getSelectionModel().getSelectedItem();
                    this.vm = VeiculoDAO.recuperar(vm.getId());
                    txtFiltro.setText(String.valueOf(vm));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                scPane.setVisible(false);
            }
        });
    }

    @FXML
    private void actionBuscar() {
        if (!txtFiltro.getText().trim().isEmpty()) {
            lblCarModel.setText(String.valueOf(vm));
            txtFiltro.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            atualizaGrade();
    }
}

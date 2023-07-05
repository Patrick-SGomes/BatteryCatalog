package dev.patrick.batterycatalog.controller;

import dev.patrick.batterycatalog.DAO.ToolDAO;
import dev.patrick.batterycatalog.model.ToolModel;
import dev.patrick.batterycatalog.util.CustomAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ToolController implements Initializable {


    @FXML private TableColumn columnNome;
    @FXML private TableColumn columnTam;
    @FXML private TableView<ToolModel> tblTool;
    @FXML private TextField txtNome;
    @FXML private TextField txtTamanho;
    ToolModel tm = new ToolModel();
    @FXML
    void actionEditar() throws Exception {
        tm = tblTool.getSelectionModel().getSelectedItem();
        this.tm = ToolDAO.recuperar(tm.getId());

        txtNome.setText(tm.getNome());
        txtTamanho.setText(tm.getTamanho());
    }

    @FXML
    void actionExcluir() {
        ToolModel selecionado = tblTool.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            CustomAlert.alert("Null", "Ação nula", "Para prosseguir, selecione uma ferramenta na tabela.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType btnSim = new ButtonType("sim");
            ButtonType btnCancelar = new ButtonType("Cancelar");
            alert.getButtonTypes().setAll(btnSim, btnCancelar);

            alert.setTitle("Cuidado!");
            alert.setHeaderText("Você está prestes a excluir a ferramenta " + selecionado.getNome());
            alert.setContentText("Deseja prosseguir com esta ação?");
            alert.showAndWait().ifPresent(e -> {
                if (e == btnSim) {
                    try {
                        ToolDAO.excluir(selecionado);
                        reloadTable();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    void actionSalvar(ActionEvent event) throws Exception {
        tm.setNome(txtNome.getText());
        tm.setTamanho(txtTamanho.getText());

        ToolDAO.salvar(tm);

        CustomAlert.alert("Successful", "Operação concluída", "Veículo " + tm.getNome() + " salvo com sucesso.");
        reloadTable();
    }

    private void close() {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }

    private void carregaTable() {
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnTam.setCellValueFactory(new PropertyValueFactory<>("tamanho"));
        reloadTable();
    }

    private void reloadTable() {
        try {
            tblTool.setItems(ToolDAO.listar(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregaTable();
    }
}

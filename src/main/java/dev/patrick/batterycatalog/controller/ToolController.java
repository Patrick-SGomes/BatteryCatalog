package dev.patrick.batterycatalog.controller;

import dev.patrick.batterycatalog.DAO.ToolDAO;
import dev.patrick.batterycatalog.model.ToolModel;
import dev.patrick.batterycatalog.util.CustomAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ToolController implements Initializable {


    @FXML private TableColumn<?, ?> columnNome;
    @FXML private TableColumn<?, ?> columnTam;
    @FXML private TableView<ToolModel> tblTool;
    @FXML private TextField txtNome;
    @FXML private TextField txtTamanho;
    ToolModel tm;
    @FXML
    void actionEditar(ActionEvent event) {

    }

    @FXML
    void actionExcluir(ActionEvent event) {

    }

    @FXML
    void actionSalvar(ActionEvent event) throws Exception {
        tm = new ToolModel();
        tm.setNome(txtNome.getText());
        tm.setTamanho(txtTamanho.getText());

        ToolDAO.salvar(tm);

        CustomAlert.alert("Successful", "Operação concluída", "Veículo " + tm.getNome() + " salvo com sucesso.");
        close();
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

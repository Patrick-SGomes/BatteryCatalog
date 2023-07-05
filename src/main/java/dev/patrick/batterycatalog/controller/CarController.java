package dev.patrick.batterycatalog.controller;

import dev.patrick.batterycatalog.DAO.VeiculoDAO;
import dev.patrick.batterycatalog.model.VeiculoModel;
import dev.patrick.batterycatalog.util.CustomAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CarController implements Initializable {

    VeiculoModel vm;
    @FXML private TextField txtModelo;
    @FXML private Spinner<Integer> spAno;
    @FXML private ComboBox<String> cbMarca, cbTecnologia;
    @FXML private TableView<VeiculoModel> tblVeiculos;
    @FXML private TableColumn<?,?> columnMarca, columnModelo, columnAno, columnTecnologia;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbTecnologia.getItems().add("Start Stop");
        loadMarca();
        carregaTable();
    }
    @FXML void novo() {

    }
    @FXML void alterar() throws Exception {
        vm = tblVeiculos.getSelectionModel().getSelectedItem();
        this.vm = VeiculoDAO.recuperar(vm.getId());

        System.out.println(vm.getId());
        txtModelo.setText(vm.getModelo());
        spAno.getEditor().setText(String.valueOf(vm.getAno()));
        cbMarca.getSelectionModel().select(vm.getMarca());
        cbTecnologia.getSelectionModel().select(vm.getTecnologia());
    }
    @FXML
    private void salvar() throws Exception {
        vm = new VeiculoModel();
        vm.setModelo(txtModelo.getText());
        vm.setMarca(String.valueOf(cbMarca.getSelectionModel().getSelectedItem()));
        vm.setAno(Integer.parseInt(spAno.getEditor().getText()));
        vm.setTecnologia(String.valueOf(cbTecnologia.getSelectionModel().getSelectedItem()));

        VeiculoDAO.salvar(vm);
        CustomAlert.alert("Successful", "Operação concluída", "Veículo " + vm.getModelo() + " salvo com sucesso.");
        close();
    }

    @FXML void excluir() {
        VeiculoModel selecionado = tblVeiculos.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            CustomAlert.alert("Null", "Ação nula", "Para prosseguir, selecione um veículo na tabela.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnCancelar = new ButtonType("Cancelar");
            alert.getButtonTypes().setAll(btnSim, btnCancelar);

            alert.setTitle("Cuidado!");
            alert.setHeaderText("Você está prestes a excluir o veículo " + selecionado.getModelo());
            alert.setContentText("Deseja prosseguir com esta ação?");
            alert.showAndWait().ifPresent(e -> {
                if (e == btnSim)
                try {
                    VeiculoDAO.excluir(selecionado);
                    reloadTable();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    private void close() {
        Stage stage = (Stage) txtModelo.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }

    private void carregaTable() {
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        columnTecnologia.setCellValueFactory(new PropertyValueFactory<>("tecnologia"));
        reloadTable();
    }

    private void reloadTable() {
        try {
            tblVeiculos.setItems(VeiculoDAO.listar(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMarca() {

        File file = new File("./scripts/marca.txt");

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String row = br.readLine();

            while (row != null) {
                cbMarca.getItems().addAll(row);
                row = br.readLine();
            }
            fr.close();
        } catch (IOException e) {
            System.err.printf("Erro na leitura do Arquivo: %s.\n",
                    e.getMessage());
        }
    }

}

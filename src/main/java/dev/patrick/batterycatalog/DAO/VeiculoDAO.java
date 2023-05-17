package dev.patrick.batterycatalog.DAO;

import dev.patrick.batterycatalog.model.VeiculoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VeiculoDAO {

    public static void salvar(VeiculoModel vm) throws Exception {
        if (vm.getId() == 0)
            inserir(vm);
        else
            alterar(vm);
    }

    public static void inserir(VeiculoModel vm) throws Exception {
        Conexao c = new Conexao();
        String sql = "INSERT INTO veiculo (marca,modelo,ano,tecnologia) VALUES (?,?,?,?)";
        PreparedStatement pst = c.getConexao().prepareStatement(sql);
        pst.setString(1, vm.getMarca());
        pst.setString(2, vm.getModelo());
        pst.setInt(3, vm.getAno());
        pst.setString(4, vm.getTecnologia());
        pst.execute();
        c.close();
    }

    public static void alterar(VeiculoModel vm) throws Exception {
        Conexao c = new Conexao();
        String sql = "UPDATE veiculo SET marca=?, modelo=?, ano=?, tecnologia=? WHERE id=?";
        PreparedStatement pst = c.getConexao().prepareStatement(sql);
        pst.setString(1, vm.getMarca());
        pst.setString(2, vm.getModelo());
        pst.setInt(3, vm.getAno());
        pst.setString(4, vm.getTecnologia());
        pst.setInt(5, vm.getId());
        pst.execute();
        c.close();
    }

    public static void excluir(VeiculoModel vm) throws Exception {
        Conexao c = new Conexao();
        String sql = "DELETE FROM veiculo WHERE id=?";
        PreparedStatement pst = c.getConexao().prepareStatement(sql);
        pst.setInt(1, vm.getId());
        pst.execute();
        c.close();
    }

    public static ObservableList<VeiculoModel> listar(String filtro) throws Exception {
        Conexao c = new Conexao();
        String sql = "SELECT * FROM veiculo WHERE CONCAT(marca,' ', modelo,' ', ano) LIKE ? ORDER BY modelo";

        PreparedStatement pst = c.getConexao().prepareStatement(sql);
        pst.setString(1, "%" + filtro + "%");
        ResultSet rs = pst.executeQuery();

        ObservableList listaVeiculos = FXCollections.observableArrayList();
        while (rs.next()) {
            VeiculoModel vm = new VeiculoModel();
            vm.setId(rs.getInt("id"));
            vm.setMarca(rs.getString("marca"));
            vm.setModelo(rs.getString("modelo"));
            vm.setAno(rs.getInt("ano"));
            vm.setTecnologia(rs.getString("tecnologia"));
            listaVeiculos.add(vm);
        }
        return listaVeiculos;
    }

    public static VeiculoModel recuperar(int codigo) throws Exception {
        Conexao c = new Conexao();
        String sql = "SELECT * FROM veiculo WHERE id=?";
        PreparedStatement pst = c.getConexao().prepareStatement(sql);
        pst.setInt(1, codigo);
        ResultSet rs = pst.executeQuery();

        VeiculoModel vm = new VeiculoModel();
        if (rs.next()) {
            vm.setId(rs.getInt("id"));
            vm.setMarca(rs.getString("marca"));
            vm.setModelo(rs.getString("modelo"));
            vm.setAno(rs.getInt("ano"));
            vm.setTecnologia(rs.getString("tecnologia"));
        }
        return vm;
    }

}

package dev.patrick.batterycatalog.DAO;

import dev.patrick.batterycatalog.model.ToolModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ToolDAO {
    private static void salvar(ToolModel tm) throws Exception {
        if (tm.getId() == 0)
            inserir(tm);
        else
            alterar(tm);
    }

    private static void inserir(ToolModel tm) throws Exception {
        Conexao c = new Conexao();
        String sql = "INSERT INTO ferramenta (nome, tamanho, imagem) VALUES (?,?,?)";
        PreparedStatement pst = c.getConexao().prepareStatement(sql);
        pst.setString(1, tm.getNome());
        pst.setString(2, tm.getTamanho());
        pst.setString(3, tm.getImage());
        pst.execute();
        c.close();
    }

    private static void alterar(ToolModel tm) throws Exception {
        Conexao c = new Conexao();
        String sql = "UPDATE ferramenta SET nome=?, tamanho=?, imagem=? WHERE id=?";
        PreparedStatement pst = c.getConexao().prepareStatement(sql);
        pst.setString(1, tm.getNome());
        pst.setString(2, tm.getTamanho());
        pst.setString(3, tm.getImage());
        pst.setInt(4, tm.getId());
        pst.execute();
        c.close();
    }

    private static void excluir(ToolModel toolModel) throws Exception {
        Conexao c = new Conexao();
        String sql = " DELETE FROM ferramenta WHERE id=?";
        PreparedStatement pst = c.getConexao().prepareStatement(sql);
        pst.setInt(1, toolModel.getId());
        pst.execute();
        c.close();
    }

    private static ObservableList<ToolModel> listar(String filtro) throws Exception {
        Conexao c = new Conexao();
        String sql = "SELECT * FROM ferramenta WHERE nome LIKE ? ORDER BY nome";
        PreparedStatement pst = c.getConexao().prepareStatement(sql);
        pst.setString(1, "%" + filtro + "%");
        ResultSet rs = pst.executeQuery();

        ObservableList listarFerramentas = FXCollections.observableArrayList();
        while (rs.next()) {
            ToolModel tm = new ToolModel();
            tm.setId(rs.getInt("id"));
            tm.setNome(rs.getString("nome"));
            tm.setTamanho(rs.getString("tamanho"));
            tm.setImage(rs.getString("imagem"));
            listarFerramentas.add(tm);
        }
        return listarFerramentas;
    }

    private static ToolModel recuperar(int codigo) throws Exception {
        Conexao c = new Conexao();
        String sql = "SELECT * FROM ferramenta WHERE id=?";
        PreparedStatement pst = c.getConexao().prepareStatement(sql);
        pst.setInt(1, codigo);
        ResultSet rs = pst.executeQuery();

        ToolModel tm = new ToolModel();
        if (rs.next()) {
            tm.setId(rs.getInt("id"));
            tm.setNome(rs.getString("nome"));
            tm.setTamanho(rs.getString("tamanho"));
            tm.setImage(rs.getString("imagem"));
        }
        return tm;
    }
}

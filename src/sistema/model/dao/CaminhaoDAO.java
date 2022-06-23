package sistema.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistema.model.domain.Caminhao;

public class CaminhaoDAO {
    private Connection connection;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public boolean inserir(Caminhao caminhao) {
        String sql = "INSERT INTO caminhoes(modelo, marca, placa, cor, quilometrosRodados, capacidadeMaximaPorcos) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, caminhao.getModelo());
            stmt.setString(2, caminhao.getMarca());
            stmt.setString(3, caminhao.getPlaca());
            stmt.setString(4, caminhao.getCor());
            stmt.setDouble(5, caminhao.getQuilometrosRodados());
            stmt.setInt(6, caminhao.getCapacidadeMaximaPorcos());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CaminhaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(Caminhao caminhao) {
        String sql = "UPDATE caminhoes SET modelo=?, marca=?, placa=?, cor=?, quilometrosRodados=?, capacidadeMaximaPorcos=? WHERE cdCaminhao=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, caminhao.getModelo());
            stmt.setString(2, caminhao.getMarca());
            stmt.setString(3, caminhao.getPlaca());
            stmt.setString(4, caminhao.getCor());
            stmt.setDouble(5, caminhao.getQuilometrosRodados());
            stmt.setInt(6, caminhao.getCapacidadeMaximaPorcos());
            stmt.setInt(7, caminhao.getCdCaminhao());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CaminhaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean remover(Caminhao caminhao) {
        String sql = "DELETE FROM caminhoes WHERE cdCaminhao=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, caminhao.getCdCaminhao());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CaminhaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Caminhao> listar() {
        String sql = "SELECT * FROM caminhoes";
        List<Caminhao> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Caminhao caminhao = new Caminhao();
                caminhao.setCdCaminhao(resultado.getInt("cdCaminhao"));
                caminhao.setModelo(resultado.getString("modelo"));
                caminhao.setMarca(resultado.getString("marca"));
                caminhao.setPlaca(resultado.getString("placa"));
                caminhao.setCor(resultado.getString("cor"));
                caminhao.setQuilometrosRodados(resultado.getDouble("quilometrosRodados"));
                caminhao.setCapacidadeMaximaPorcos(resultado.getInt("capacidadeMaximaPorcos"));
                retorno.add(caminhao);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaminhaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Caminhao buscar(Caminhao caminhao){
        String sql = "SELECT * FROM caminhoes WHERE cdCaminhao=?";
        Caminhao retorno = new Caminhao();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, caminhao.getCdCaminhao());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                caminhao.setCdCaminhao(resultado.getInt("cdCaminhao"));
                caminhao.setModelo(resultado.getString("modelo"));
                caminhao.setMarca(resultado.getString("marca"));
                caminhao.setPlaca(resultado.getString("placa"));
                caminhao.setCor(resultado.getString("cor"));
                caminhao.setQuilometrosRodados(resultado.getDouble("quilometrosRodados"));
                caminhao.setCapacidadeMaximaPorcos(resultado.getInt("capacidadeMaximaPorcos"));
                
                retorno = caminhao;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaminhaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
}

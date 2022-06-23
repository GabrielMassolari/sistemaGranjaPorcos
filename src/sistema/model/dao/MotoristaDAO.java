package sistema.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistema.model.domain.Motorista;

public class MotoristaDAO {
    private Connection connection;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public boolean inserir(Motorista motorista) {
        String sql = "INSERT INTO motoristas(nome, cpf, categoriaCnh, dataNascimento, dataContratacao) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCpf());
            stmt.setString(3, motorista.getCategoriaCNH());
            stmt.setDate(4, Date.valueOf(motorista.getDataNascimento()));
            stmt.setDate(5, Date.valueOf(motorista.getDataContratacao()));
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotoristaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(Motorista motorista) {
        String sql = "UPDATE motoristas SET nome=?, cpf=?, categoriaCnh=?, dataNascimento=?, dataContratacao=? WHERE cdMotorista=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCpf());
            stmt.setString(3, motorista.getCategoriaCNH());
            stmt.setDate(4, Date.valueOf(motorista.getDataNascimento()));
            stmt.setDate(5, Date.valueOf(motorista.getDataContratacao()));
            stmt.setInt(6, motorista.getCdMotorista());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotoristaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean remover(Motorista motorista) {
        String sql = "DELETE FROM motoristas WHERE cdMotorista=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, motorista.getCdMotorista());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotoristaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Motorista> listar() {
        String sql = "SELECT * FROM motoristas";
        List<Motorista> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Motorista motorista = new Motorista();
                
                motorista.setCdMotorista(resultado.getInt("cdMotorista"));
                motorista.setNome(resultado.getString("nome"));
                motorista.setCpf(resultado.getString("cpf"));
                motorista.setCategoriaCNH(resultado.getString("categoriaCnh"));
                motorista.setDataNascimento(resultado.getDate("dataNascimento").toLocalDate());
                motorista.setDataContratacao(resultado.getDate("dataContratacao").toLocalDate());
                
                retorno.add(motorista);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MotoristaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Motorista buscar(Motorista motorista){
        String sql = "SELECT * FROM motoristas WHERE cdMotorista=?";
        Motorista retorno = new Motorista();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, motorista.getCdMotorista());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                motorista.setCdMotorista(resultado.getInt("cdMotorista"));
                motorista.setNome(resultado.getString("nome"));
                motorista.setCpf(resultado.getString("cpf"));
                motorista.setCategoriaCNH(resultado.getString("categoriaCnh"));
                motorista.setDataNascimento(resultado.getDate("dataNascimento").toLocalDate());
                motorista.setDataContratacao(resultado.getDate("dataContratacao").toLocalDate());
                
                retorno = motorista;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaminhaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}

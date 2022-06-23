package sistema.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistema.model.domain.Galpao;

public class GalpaoDAO {
    private Connection connection;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public boolean inserir(Galpao galpao) {
        String sql = "INSERT INTO galpoes(nome, metrosQuadrados, capacidadeMaxima, numeroBaias, refrigerado, maternidade, limiteDiario, quantidadeAtual) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, galpao.getNome());
            stmt.setDouble(2, galpao.getMetrosQuadrados());
            stmt.setInt(3, galpao.getCapacidadeMaxima());
            stmt.setInt(4, galpao.getNumeroBaias());
            stmt.setBoolean(5, galpao.isRefrigerado());
            stmt.setBoolean(6, galpao.isMaternidade());
            stmt.setInt(7, galpao.getLimiteDiario());
            stmt.setInt(8, galpao.getQuantidadeAtual());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GalpaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(Galpao galpao) {
        String sql = "UPDATE galpoes SET nome=?, metrosQuadrados=?, capacidadeMaxima=?, numeroBaias=?, refrigerado=?, maternidade=?, limiteDiario=?, quantidadeAtual=? WHERE cdGalpao=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, galpao.getNome());
            stmt.setDouble(2, galpao.getMetrosQuadrados());
            stmt.setInt(3, galpao.getCapacidadeMaxima());
            stmt.setInt(4, galpao.getNumeroBaias());
            stmt.setBoolean(5, galpao.isRefrigerado());
            stmt.setBoolean(6, galpao.isMaternidade());
            stmt.setInt(7, galpao.getLimiteDiario());
            stmt.setInt(8, galpao.getQuantidadeAtual());
            stmt.setInt(9, galpao.getCdGalpao());

            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GalpaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean remover(Galpao galpao) {
        String sql = "DELETE FROM galpoes WHERE cdGalpao=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, galpao.getCdGalpao());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GalpaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Galpao> listar() {
        String sql = "SELECT * FROM galpoes";
        List<Galpao> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Galpao galpao = new Galpao();
                
                galpao.setCdGalpao(resultado.getInt("cdGalpao"));
                galpao.setNome(resultado.getString("nome"));
                galpao.setMetrosQuadrados(resultado.getDouble("metrosQuadrados"));
                galpao.setCapacidadeMaxima(resultado.getInt("capacidadeMaxima"));
                galpao.setNumeroBaias(resultado.getInt("numeroBaias"));
                galpao.setRefrigerado(resultado.getBoolean("refrigerado"));
                galpao.setMaternidade(resultado.getBoolean("maternidade"));
                galpao.setLimiteDiario(resultado.getInt("limiteDiario"));
                galpao.setQuantidadeAtual(resultado.getInt("quantidadeAtual"));
                
                retorno.add(galpao);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaminhaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Galpao buscar(Galpao galpao){
        String sql = "SELECT * FROM galpoes WHERE cdGalpao=?";
        Galpao retorno = new Galpao();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, galpao.getCdGalpao());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                galpao.setCdGalpao(resultado.getInt("cdGalpao"));
                galpao.setNome(resultado.getString("nome"));
                galpao.setMetrosQuadrados(resultado.getDouble("metrosQuadrados"));
                galpao.setCapacidadeMaxima(resultado.getInt("capacidadeMaxima"));
                galpao.setNumeroBaias(resultado.getInt("numeroBaias"));
                galpao.setRefrigerado(resultado.getBoolean("refrigerado"));
                galpao.setMaternidade(resultado.getBoolean("maternidade"));
                galpao.setLimiteDiario(resultado.getInt("limiteDiario"));
                galpao.setQuantidadeAtual(resultado.getInt("quantidadeAtual"));
                
                retorno = galpao;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GalpaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}

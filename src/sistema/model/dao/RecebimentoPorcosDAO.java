package sistema.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistema.model.domain.Caminhao;
import sistema.model.domain.Galpao;
import sistema.model.domain.Motorista;
import sistema.model.domain.RecebimentoPorcos;

public class RecebimentoPorcosDAO {
    private Connection connection;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public boolean inserir(RecebimentoPorcos recebimentoPorcos){
        String sql = "INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, recebimentoPorcos.getCaminhao().getCdCaminhao());
            stmt.setInt(2, recebimentoPorcos.getMotorista().getCdMotorista());
            stmt.setInt(3, recebimentoPorcos.getGalpao().getCdGalpao());
            stmt.setDate(4, Date.valueOf(recebimentoPorcos.getData()));
            stmt.setInt(5, recebimentoPorcos.getQuantidadePorcos());
            stmt.setBoolean(6, recebimentoPorcos.isFilhotes());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RecebimentoPorcosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
     public boolean alterar(RecebimentoPorcos recebimentoPorcos) {
        String sql = "UPDATE recebimentosporcos SET cdCaminhao=?, cdMotorista=?, cdGalpao=?, data=?, quantidadePorcos=?, filhotes=? WHERE cdRecebimentoPorcos=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, recebimentoPorcos.getCaminhao().getCdCaminhao());
            stmt.setInt(2, recebimentoPorcos.getMotorista().getCdMotorista());
            stmt.setInt(3, recebimentoPorcos.getGalpao().getCdGalpao());
            stmt.setDate(4, Date.valueOf(recebimentoPorcos.getData()));
            stmt.setInt(5, recebimentoPorcos.getQuantidadePorcos());
            stmt.setBoolean(6, recebimentoPorcos.isFilhotes());
            stmt.setInt(7, recebimentoPorcos.getCdRecebimentoPorcos());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RecebimentoPorcosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    public boolean remover(RecebimentoPorcos recebimentoPorcos) {
        String sql = "DELETE FROM recebimentosporcos WHERE cdRecebimentoPorcos=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, recebimentoPorcos.getCdRecebimentoPorcos());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RecebimentoPorcos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
     public List<RecebimentoPorcos> listar() {
        String sql = "SELECT * FROM recebimentosporcos";
        List<RecebimentoPorcos> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                RecebimentoPorcos recebimentoPorcos = new RecebimentoPorcos();
                Caminhao caminhao = new Caminhao();
                Motorista motorista = new Motorista();
                Galpao galpao = new Galpao();
                
                recebimentoPorcos.setCdRecebimentoPorcos(resultado.getInt("cdRecebimentoPorcos"));
                recebimentoPorcos.setData(resultado.getDate("data").toLocalDate());
                recebimentoPorcos.setQuantidadePorcos(resultado.getInt("quantidadePorcos"));
                recebimentoPorcos.setFilhotes(resultado.getBoolean("filhotes"));
                
                caminhao.setCdCaminhao(resultado.getInt("cdCaminhao"));
                motorista.setCdMotorista(resultado.getInt("cdMotorista"));
                galpao.setCdGalpao(resultado.getInt("cdGalpao"));
                
                //Obtendo os dados completos do Banco de Dados
                CaminhaoDAO caminhaoDAO = new CaminhaoDAO();
                caminhaoDAO.setConnection(connection);
                caminhao = caminhaoDAO.buscar(caminhao);
                
                MotoristaDAO motoristaDAO = new MotoristaDAO();
                motoristaDAO.setConnection(connection);
                motorista = motoristaDAO.buscar(motorista);
                
                GalpaoDAO galpaoDAO = new GalpaoDAO();
                galpaoDAO.setConnection(connection);
                galpao = galpaoDAO.buscar(galpao);
                
                recebimentoPorcos.setCaminhao(caminhao);
                recebimentoPorcos.setMotorista(motorista);
                recebimentoPorcos.setGalpao(galpao);
                
                retorno.add(recebimentoPorcos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecebimentoPorcosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
     
     public int verificarQuantidadeDiaria(RecebimentoPorcos recebimentoPorcos){
         String sql = "SELECT SUM(quantidadePorcos) FROM recebimentosporcos WHERE date_trunc('day', data) = ? AND cdGalpao = ?";
         int retorno = -1;
         try{
             PreparedStatement stmt = connection.prepareStatement(sql);
             stmt.setDate(1, Date.valueOf(recebimentoPorcos.getData()));
             stmt.setInt(2, recebimentoPorcos.getGalpao().getCdGalpao());
             ResultSet resultado = stmt.executeQuery();
             if(resultado.next()){
                 retorno = resultado.getInt("sum");
             }
         }catch (SQLException ex){
             Logger.getLogger(RecebimentoPorcos.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         return retorno;
     }
     
     public List<Integer> listarAnosQuePossuemRecebimentos() {
        String sql = "SELECT DISTINCT EXTRACT(year from data) AS ano FROM recebimentosporcos GROUP BY ano ORDER BY ano;";
        List<Integer> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(resultado.getInt("ano"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecebimentoPorcosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
     
    public Map<Integer, Integer> listarSomaQuantidadePorcosPorMesNoAno(Galpao galpao, int ano) {
       String sql = "SELECT SUM(quantidadePorcos), EXTRACT(year from data) AS ano, EXTRACT(month from data) AS mes FROM recebimentosporcos WHERE cdGalpao = ? GROUP BY ano, mes HAVING EXTRACT(year from data) = ? ORDER BY ano, mes;";
       Map<Integer, Integer> retorno = new HashMap();

       try {
           PreparedStatement stmt = connection.prepareStatement(sql);
           stmt.setInt(1, galpao.getCdGalpao());
           stmt.setInt(2, ano);
           ResultSet resultado = stmt.executeQuery();

           while (resultado.next()) {
               retorno.put(resultado.getInt("mes"), resultado.getInt("sum"));
           }
           return retorno;
       } catch (SQLException ex) {
           Logger.getLogger(RecebimentoPorcosDAO.class.getName()).log(Level.SEVERE, null, ex);
       }
       return retorno;
    }
    
    public Map<Integer, Integer> listarQuantidadeRecebimentosPorMesNoAno(Galpao galpao, int ano) {
       String sql = "SELECT COUNT(*), EXTRACT(year from data) AS ano, EXTRACT(month from data) AS mes FROM recebimentosporcos WHERE cdGalpao = ? GROUP BY ano, mes HAVING EXTRACT(year from data) = ? ORDER BY ano, mes;";
       Map<Integer, Integer> retorno = new HashMap();

       try {
           PreparedStatement stmt = connection.prepareStatement(sql);
           stmt.setInt(1, galpao.getCdGalpao());
           stmt.setInt(2, ano);
           ResultSet resultado = stmt.executeQuery();

           while (resultado.next()) {
               retorno.put(resultado.getInt("mes"), resultado.getInt("count"));
           }
           return retorno;
       } catch (SQLException ex) {
           Logger.getLogger(RecebimentoPorcosDAO.class.getName()).log(Level.SEVERE, null, ex);
       }
       return retorno;
    }
    
    public HashMap obterInformacoesGeraisPorPeriodo(LocalDate dataInicio, LocalDate dataFinal, int dias){
       String sql = "SELECT SUM(quantidadePorcos) AS porcosRecebidos, COALESCE(SUM(CASE WHEN filhotes THEN recebimentosporcos.quantidadePorcos END), 0) AS filhotes, ROUND(SUM(quantidadeporcos)::decimal / ?, 2)  as mediaDiaria, COUNT(*) AS qtdRecebimento FROM recebimentosporcos WHERE data BETWEEN ? AND ?";
       HashMap retorno = new HashMap();
       
       try {
           PreparedStatement stmt = connection.prepareStatement(sql);
           stmt.setInt(1, dias);
           stmt.setDate(2, Date.valueOf(dataInicio));
           stmt.setDate(3, Date.valueOf(dataFinal));
           ResultSet resultado = stmt.executeQuery();

           if (resultado.next()) {
               retorno.put("totalPorcos", resultado.getInt("porcosRecebidos"));
               retorno.put("totalFilhotes", resultado.getInt("filhotes"));
               retorno.put("mediaDiariaGeral", resultado.getDouble("mediaDiaria"));
               retorno.put("qtdTotal", resultado.getInt("qtdRecebimento"));
           }
           return retorno;
       } catch (SQLException ex) {
           Logger.getLogger(RecebimentoPorcosDAO.class.getName()).log(Level.SEVERE, null, ex);
       }
       return retorno;
    }
}

package sistema.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistema.model.domain.Usuario;

public class UsuarioDAO {
    private Connection connection;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public String buscarSenha(Usuario usuario){
        String sql = "SELECT senha FROM usuarios WHERE idUsuario=?";
        String senha = null;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, usuario.getIdUsuario());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                senha = resultado.getString("senha");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return senha;
    }
}

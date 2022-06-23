package sistema.model.domain;

import java.io.Serializable;

public class Usuario implements Serializable{
    private int idUsuario;
    private String senha;
    
    public Usuario(){
    }
    
    public Usuario(int idUsuario, String senha) {
        this.idUsuario = idUsuario;
        this.senha = senha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}



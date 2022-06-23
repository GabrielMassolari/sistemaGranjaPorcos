package sistema.model.domain;

import java.io.Serializable;
import java.time.LocalDate;


public class Motorista implements Serializable {
    private int cdMotorista;
    private String nome;
    private String cpf;
    private String categoriaCNH;
    private LocalDate dataNascimento;
    private LocalDate dataContratacao;
    
    public Motorista(){
    }

    public Motorista(int cdMotorista, String nome, String cpf, String categoriaCNH, LocalDate dataNascimento, LocalDate dataContratacao) {
        this.cdMotorista = cdMotorista;
        this.nome = nome;
        this.cpf = cpf;
        this.categoriaCNH = categoriaCNH;
        this.dataNascimento = dataNascimento;
        this.dataContratacao = dataContratacao;
    }
    
    public int getCdMotorista() {
        return cdMotorista;
    }


    public void setCdMotorista(int cdMotorista) {
        this.cdMotorista = cdMotorista;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCpf() {
        return cpf;
    }


    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public String getCategoriaCNH() {
        return categoriaCNH;
    }


    public void setCategoriaCNH(String categoriaCNH) {
        this.categoriaCNH = categoriaCNH;
    }


    public LocalDate getDataNascimento() {
        return dataNascimento;
    }


    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


    public LocalDate getDataContratacao() {
        return dataContratacao;
    }


    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    } 
    
    @Override
    public String toString(){
        return this.nome;
    }
}

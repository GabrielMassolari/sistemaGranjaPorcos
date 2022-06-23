package sistema.model.domain;

import java.io.Serializable;


public class Caminhao implements Serializable{
    private int cdCaminhao;
    private String modelo;
    private String marca;
    private String placa;
    private String cor;
    private Double quilometrosRodados;
    private int capacidadeMaximaPorcos;
    
    public Caminhao(){
    }

    public Caminhao(int cdCaminhao, String modelo, String marca, String placa, String cor, Double quilometrosRodados, int capacidadeMaximaPorcos) {
        this.cdCaminhao = cdCaminhao;
        this.modelo = modelo;
        this.marca = marca;
        this.placa = placa;
        this.cor = cor;
        this.quilometrosRodados = quilometrosRodados;
        this.capacidadeMaximaPorcos = capacidadeMaximaPorcos;
    }
    
    public int getCdCaminhao() {
        return cdCaminhao;
    }

    public void setCdCaminhao(int cdCaminhao) {
        this.cdCaminhao = cdCaminhao;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Double getQuilometrosRodados() {
        return quilometrosRodados;
    }

    public void setQuilometrosRodados(Double quilometrosRodados) {
        this.quilometrosRodados = quilometrosRodados;
    }

    public int getCapacidadeMaximaPorcos() {
        return capacidadeMaximaPorcos;
    }

    public void setCapacidadeMaximaPorcos(int capacidadeMaximaPorcos) {
        this.capacidadeMaximaPorcos = capacidadeMaximaPorcos;
    }
    
    @Override
    public String toString(){
        return this.modelo + "-" + this.placa;
    }
}



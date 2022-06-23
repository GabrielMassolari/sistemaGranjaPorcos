package sistema.model.domain;

import java.io.Serializable;

public class Galpao implements Serializable{
    private int cdGalpao;
    private String nome;
    private double metrosQuadrados;
    private int capacidadeMaxima;
    private int numeroBaias;
    private boolean refrigerado;
    private boolean maternidade;
    private int limiteDiario;
    private int quantidadeAtual;
    
    public Galpao(){
    }

    public Galpao(int cdGalpao, String nome, double metrosQuadrados, int capacidadeMaxima, int numeroBaias, boolean refrigerado, boolean maternidade, int limiteDiario, int quantidadeAtual) {
        this.cdGalpao = cdGalpao;
        this.nome = nome;
        this.metrosQuadrados = metrosQuadrados;
        this.capacidadeMaxima = capacidadeMaxima;
        this.numeroBaias = numeroBaias;
        this.refrigerado = refrigerado;
        this.maternidade = maternidade;
        this.limiteDiario = limiteDiario;
        this.quantidadeAtual = quantidadeAtual;
    }
    
    public int getCdGalpao() {
        return cdGalpao;
    }

    public void setCdGalpao(int cdGalpao) {
        this.cdGalpao = cdGalpao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getMetrosQuadrados() {
        return metrosQuadrados;
    }

    public void setMetrosQuadrados(double metrosQuadrados) {
        this.metrosQuadrados = metrosQuadrados;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public int getNumeroBaias() {
        return numeroBaias;
    }

    public void setNumeroBaias(int numeroBaias) {
        this.numeroBaias = numeroBaias;
    }

    public boolean isRefrigerado() {
        return refrigerado;
    }

    public void setRefrigerado(boolean refrigerado) {
        this.refrigerado = refrigerado;
    }

    public boolean isMaternidade() {
        return maternidade;
    }

    public void setMaternidade(boolean maternidade) {
        this.maternidade = maternidade;
    }
    
    public int getLimiteDiario(){
        return limiteDiario;
    }
    
    public void setLimiteDiario(int limiteDiario){
        this.limiteDiario = limiteDiario;
    }
    
    public int getQuantidadeAtual(){
        return quantidadeAtual;
    }
    
    public void setQuantidadeAtual(int quantidadeAtual){
        this.quantidadeAtual = quantidadeAtual;
    }
    
    @Override
    public String toString(){
        return this.cdGalpao + "-" + this.nome;
    }
}

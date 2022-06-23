package sistema.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecebimentoPorcos implements Serializable{
    private int cdRecebimentoPorcos;
    private Caminhao caminhao;
    private Motorista motorista;
    private Galpao galpao;
    private LocalDate data;
    private int quantidadePorcos;
    private boolean filhotes;

    public RecebimentoPorcos(){
    }

    public RecebimentoPorcos(int cdRecebimentoPorcos, Caminhao caminhao, Motorista motorista, Galpao galpao, LocalDate data, int quantidadePorcos, boolean filhotes) {
        this.cdRecebimentoPorcos = cdRecebimentoPorcos;
        this.caminhao = caminhao;
        this.motorista = motorista;
        this.galpao = galpao;
        this.data = data;
        this.quantidadePorcos = quantidadePorcos;
        this.filhotes = filhotes;
    }
    
    public int getCdRecebimentoPorcos() {
        return cdRecebimentoPorcos;
    }

    public void setCdRecebimentoPorcos(int cdRecebimentoPorcos) {
        this.cdRecebimentoPorcos = cdRecebimentoPorcos;
    }

    public Caminhao getCaminhao() {
        return caminhao;
    }

    public void setCaminhao(Caminhao caminhao) {
        this.caminhao = caminhao;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Galpao getGalpao() {
        return galpao;
    }

    public void setGalpao(Galpao galpao) {
        this.galpao = galpao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getQuantidadePorcos() {
        return quantidadePorcos;
    }

    public void setQuantidadePorcos(int quantidadePorcos) {
        this.quantidadePorcos = quantidadePorcos;
    }

    public boolean isFilhotes() {
        return filhotes;
    }

    public void setFilhotes(boolean filhotes) {
        this.filhotes = filhotes;
    }
    
    @Override
    public String toString(){
        return this.cdRecebimentoPorcos + "- " + "Recebimento " + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(this.data) + " em " + this.galpao.getNome();
        
    }
}

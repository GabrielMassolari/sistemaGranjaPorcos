package sistema.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import sistema.model.dao.GalpaoDAO;
import sistema.model.dao.RecebimentoPorcosDAO;
import sistema.model.database.Database;
import sistema.model.database.DatabaseFactory;
import sistema.model.domain.Galpao;

public class FXMLAnchorPaneGraficoController implements Initializable {
    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    private JFXComboBox<Integer> comboBoxAno;

    @FXML
    private JFXComboBox<Galpao> comboBoxGalpao;

    @FXML
    private JFXComboBox<String> comboBoxInformacao;

    @FXML
    private Label labelMaiorValor;

    @FXML
    private Label labelSomaValor;

    @FXML
    private Label labelMediaValor;
    
    @FXML
    private JFXButton buttonGerar;
    
    private List<Integer> listAnos;
    private List<Galpao> listGalpoes;
    
    private ObservableList<Integer> observableListAnos;
    private ObservableList<String> observableListMeses = FXCollections.observableArrayList();
    private ObservableList<Galpao> observableListGalpoes;

    private Map<Integer, Integer> dados;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final GalpaoDAO galpaoDAO = new GalpaoDAO();
    private final RecebimentoPorcosDAO recebimentoPorcosDAO = new RecebimentoPorcosDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        galpaoDAO.setConnection(connection);
        recebimentoPorcosDAO.setConnection(connection);
        
        //Configuração dos eixos do Gráfico
        String[] arrayMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        observableListMeses.addAll(Arrays.asList(arrayMeses));
        categoryAxis.setCategories(observableListMeses);
        
        //Carregar ComboxBoxs de filtragem
        carregarComboBoxAnos();
        carregarComboBoxGalpoes();
        carregarComboBoxInformacao();
    }
    
    public void carregarComboBoxAnos(){
        listAnos = recebimentoPorcosDAO.listarAnosQuePossuemRecebimentos();
        observableListAnos = FXCollections.observableArrayList(listAnos);
        comboBoxAno.setItems(observableListAnos);
    }
    
    public void carregarComboBoxGalpoes(){
        listGalpoes = galpaoDAO.listar();
        observableListGalpoes = FXCollections.observableArrayList(listGalpoes);
        comboBoxGalpao.setItems(observableListGalpoes);  
    }
    
    public void carregarComboBoxInformacao(){
        comboBoxInformacao.getItems().add("Quantidade de Recebimentos");
        comboBoxInformacao.getItems().add("Soma de Porcos Recebidos");
    }
    
    public boolean validarCampos(){
        return (comboBoxAno.getSelectionModel().getSelectedItem() != null &&
                comboBoxGalpao.getSelectionModel().getSelectedItem() != null &&
                comboBoxInformacao.getSelectionModel().getSelectedItem() != null);
    }
    
    public void gerarDashboard(){
        if(validarCampos()){
            if(comboBoxInformacao.getSelectionModel().getSelectedItem().equals("Quantidade de Recebimentos")){
                gerarGraficoQuantidade();
                if(!dados.isEmpty()){
                    labelMaiorValor.setText("Melhor Mês: " + 
                                        retornaNomeMesCompleto((int)Collections.max(dados.entrySet(), Map.Entry.comparingByValue()).getKey()) + " - " + 
                                        (int)Collections.max(dados.entrySet(), Map.Entry.comparingByValue()).getValue() +
                                        " Recebimentos");
                    labelSomaValor.setText("Total Recebimentos no Ano: " + dados.values().stream().mapToInt(Integer::intValue).sum());
                    labelMediaValor.setText("Media de Recebimentos por Mês: " + (dados.values().stream().mapToInt(Integer::intValue).sum()) / 12);
                }else{
                    labelMaiorValor.setText("");
                    labelSomaValor.setText("");
                    labelMediaValor.setText("");
                }
                
            }else if(comboBoxInformacao.getSelectionModel().getSelectedItem().equals("Soma de Porcos Recebidos")){
                gerarGraficoSoma();
                if(!dados.isEmpty()){
                    labelMaiorValor.setText("Melhor Mês: " + 
                                        retornaNomeMesCompleto((int)Collections.max(dados.entrySet(), Map.Entry.comparingByValue()).getKey()) + " - " + 
                                        (int)Collections.max(dados.entrySet(), Map.Entry.comparingByValue()).getValue() + 
                                        " Porcos");
                    labelSomaValor.setText("Total Soma no Ano: " + dados.values().stream().mapToInt(Integer::intValue).sum());
                    labelMediaValor.setText("Media de Porcos Recebidos por Mês: " + (dados.values().stream().mapToInt(Integer::intValue).sum()) / 12);
                }else{
                    labelMaiorValor.setText("");
                    labelSomaValor.setText("");
                    labelMediaValor.setText("");
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Favor preencher os campos!");
            alert.show();
        }
    }
    
    public void gerarGraficoQuantidade(){
        barChart.getData().clear();
        barChart.layout();
        int ano = comboBoxAno.getSelectionModel().getSelectedItem();
        Galpao galpao = comboBoxGalpao.getSelectionModel().getSelectedItem();
        barChart.setTitle("Quantidade de Recebimentos por Mês em " + ano);
        dados = recebimentoPorcosDAO.listarQuantidadeRecebimentosPorMesNoAno(galpao, ano);
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName(String.valueOf(ano));
        for(Map.Entry<Integer, Integer> dadosItem : dados.entrySet()){
            String mes = retornaNomeMes((int)dadosItem.getKey());
            Integer valor = dadosItem.getValue();
            series.getData().add(new XYChart.Data<>(mes, valor));
        }
        barChart.getData().add(series);
    }
    
    public void gerarGraficoSoma(){
        barChart.getData().clear();
        barChart.layout();
        int ano = comboBoxAno.getSelectionModel().getSelectedItem();
        Galpao galpao = comboBoxGalpao.getSelectionModel().getSelectedItem();
        barChart.setTitle("Soma de Porcos recebidos por Mês em " + ano);
        dados = recebimentoPorcosDAO.listarSomaQuantidadePorcosPorMesNoAno(galpao, ano);
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName(String.valueOf(ano));
        for(Map.Entry<Integer, Integer> dadosItem : dados.entrySet()){
            String mes = retornaNomeMes((int)dadosItem.getKey());
            Integer valor = dadosItem.getValue();
            series.getData().add(new XYChart.Data<>(mes, valor));
        }
        barChart.getData().add(series);
    }
    
    public String retornaNomeMes(int mes){
        switch (mes) {
            case 1:
                return "Jan";
            case 2:
                return "Fev";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "Mai";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Set";
            case 10:
                return "Out";
            case 11:
                return "Nov";
            case 12:
                return "Dez";
            default:
                return "";
        }
    }
    
    public String retornaNomeMesCompleto(int mes){
        switch (mes) {
            case 1:
                return "Janeiro";
            case 2:
                return "Fevereiro";
            case 3:
                return "Março";
            case 4:
                return "Abril";
            case 5:
                return "Maio";
            case 6:
                return "Junho";
            case 7:
                return "Julho";
            case 8:
                return "Agosto";
            case 9:
                return "Setembro";
            case 10:
                return "Outubro";
            case 11:
                return "Novembro";
            case 12:
                return "Dezembro";
            default:
                return "";
        }
    }
    
}

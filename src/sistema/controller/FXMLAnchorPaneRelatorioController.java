package sistema.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import sistema.model.dao.RecebimentoPorcosDAO;
import sistema.model.database.Database;
import sistema.model.database.DatabaseFactory;

public class FXMLAnchorPaneRelatorioController implements Initializable {
    @FXML
    private JFXDatePicker datePickerInicio;

    @FXML
    private JFXDatePicker datePickerFinal;

    @FXML
    private JFXButton buttonGerarRelatorio;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final RecebimentoPorcosDAO recebimentoPorcosDAO = new RecebimentoPorcosDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recebimentoPorcosDAO.setConnection(connection);
        
        //Declaração dos objetos de validações de dados
        RequiredFieldValidator validadorCampoObrigatorio = new RequiredFieldValidator();
        
        //Configuração dos objetos de validações de dados
        validadorCampoObrigatorio.setMessage("Insira uma data");
        
        //Adição dos objetos de validações de dados nos componentes visuais
        datePickerInicio.getValidators().add(validadorCampoObrigatorio);
        datePickerFinal.getValidators().add(validadorCampoObrigatorio);
        
        //Criação de Listeners para validação imediata após inserção de algum valor
        datePickerInicio.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal)datePickerInicio.validate();
        });
        datePickerFinal.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal)datePickerFinal.validate();
        });
    }
    
    public boolean validarDataInicioMenorDataFinal(){
        return (datePickerInicio.getValue().compareTo(datePickerFinal.getValue()) < 0);
    }
    
    public boolean validarCamposData(){
        return (datePickerInicio.validate() && datePickerFinal.validate());
    }
    
    @FXML
    public void handleButtonGerarRelatorio() throws JRException{
        if(validarCamposData()){
            //Validação das datas
            if(validarDataInicioMenorDataFinal()){
                gerarRelatorio();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("A Data de Início não pode ser maior que Data Final!");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Favor preencher os campos de data!");
            alert.show();
        }
    }
    
    public void gerarRelatorio() throws JRException{
        HashMap filtro = new HashMap();
        HashMap dadosGerais = new HashMap();
        
        LocalDate dataInicio = this.datePickerInicio.getValue();
        LocalDate dataFinal = this.datePickerFinal.getValue();
        //Obter diferença de dias entre as datas para realização dos calculos
        int dias = (int)ChronoUnit.DAYS.between(dataInicio, dataFinal) + 1;
        
        //Carregando Url da Logo do Relatorio
        URL logo = this.getClass().getResource("/sistema/assets/logo.png");
        dadosGerais = recebimentoPorcosDAO.obterInformacoesGeraisPorPeriodo(dataInicio, dataFinal, dias);
        
        //Inserção dos filtros
        filtro.put("dias", dias);
        filtro.put("dataInicio", dataInicio.toString());
        filtro.put("dataFinal", dataFinal.toString());
        filtro.put("logo", logo);
        dadosGerais.forEach((key, value) -> {
            filtro.put(key, value);
        });
        
        URL url = this.getClass().getResource("/sistema/relatorios/sistemaGranjaRelatorio.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, connection);
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
        jasperViewer.setVisible(true);
    }
}

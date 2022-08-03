/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import payrollsys.db.Conexao;
import payrollsys.db.PresencaOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Funcionario;
import payrollsys.models.Presenca;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class PresencasController implements Initializable {
    @FXML
    private TableView<Presenca> tabelaPresencas;
    @FXML
    private TableColumn<Presenca, Date> colDataPresenca;
    @FXML
    private TableColumn<Presenca, Funcionario> colNomeFuncionario;
    @FXML
    private TableColumn<Presenca, Time> colEntradaFuncionario;
    @FXML
    private TableColumn<Presenca, Time> colSaidaFuncionario;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXDatePicker dataPickerDataPresenca;
    @FXML
    private JFXButton btnGerarRelatorio;

    
    /* Data collections */
    private ObservableList<Presenca> listaPresencas = FXCollections.observableArrayList();
    
    /* Selected */
    private Presenca selectedPresenca = null;
    
    /* Conexao Banco Dados */
    private Conexao conexao;
    
    Date dataPresenca = null;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dataPresenca = Date.valueOf(LocalDate.now());
        dataPickerDataPresenca.setValue(dataPresenca.toLocalDate());
                
        desableEditDeleteButtons();
      
        setDataInTable(listaPresencas);
        setTableColumns();

        manageTableSelectionMode();
    }    
    
    @FXML
    private void searchPresenca(ActionEvent event) {
        dataPresenca = Date.valueOf(dataPickerDataPresenca.getValue());
        setDataInTable(listaPresencas);
    }

    @FXML
    private void actualizarTabela(ActionEvent event) {
        desableEditDeleteButtons();
        setDataInTable(listaPresencas);
    }

    @FXML
    private void eliminar(ActionEvent event) {
        boolean confirmationResult = DynamicViews.confirmationAlert("Eliminar Registro", "Eliminar presença!",
                "Tens a certeza disso?");
        if(confirmationResult){
            conexao = new Conexao();
            int operationResult = PresencaOp.EliminarPresenca(conexao.getConnection(), selectedPresenca);
            
            if (operationResult == 1) {
                String title = "Registro Eliminado";
                String content = "O Registro foi eliminado com exito!";
                String header = "Resultado:";

                DynamicViews.informationAlert(title, content, header);
            }
            else {
                String title = "Registro Eliminado";
                String content = "O Registro não foi eliminado com exito!"
                        + "Ocorreu algum erro ao eliminar o registro no banco de dados.";
                String header = "Resultado:";

                DynamicViews.informationAlert(title, content, header);
            }
            conexao.fecharConexao();
        }  
    }
    
    @FXML
    private void gerarReport(ActionEvent event) {
        //GERA UM RELATORIO DE FUNCIONARIOS
        System.out.println("Gerando Reports ...");
        conexao = new Conexao();
        try{
            
            HashMap hm = new HashMap();
            hm.put("dataPresenca", dataPresenca);
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/payrollsys/reports/reportPresencas.jasper"), hm, conexao.getConnection());
            JasperViewer.viewReport(print, false);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        conexao.fecharConexao();
    }
    
    /* Metodos Auxiliares */
    private void setTableColumns(){
       colDataPresenca.setCellValueFactory(new PropertyValueFactory<Presenca, Date>("dataPresenca"));
       colEntradaFuncionario.setCellValueFactory(new PropertyValueFactory<Presenca, Time>("inicioPresenca"));
       colSaidaFuncionario.setCellValueFactory(new PropertyValueFactory<Presenca, Time>("fimPresenca"));
       colNomeFuncionario.setCellValueFactory(new PropertyValueFactory<Presenca, Funcionario>("funcionarioPresenca"));
    }
    
    private void setDataInTable(ObservableList<Presenca> listaPresencas){
        conexao = new Conexao();
        listaPresencas.clear();
        PresencaOp.SelecionarPresencasData(conexao.getConnection(), dataPresenca, listaPresencas);
        //System.out.println(listaPresencas.get(0).getFuncionarioPresenca().getCodigo());
        if(listaPresencas.size() > 0){
            tabelaPresencas.setItems(listaPresencas);
        }
        conexao.fecharConexao();
    }
    
    public void manageTableSelectionMode(){
        tabelaPresencas.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Presenca>() {
                @Override
                public void changed(ObservableValue<? extends Presenca> observable, Presenca oldValue, Presenca selectedValue) {
                    if(selectedValue != null){
                        System.out.println(selectedValue);
                        selectedPresenca = selectedValue;
                        enableEditDeleteButtons();
                    }
                }
            }
        );
    }
    
    private void enableEditDeleteButtons(){
        btnEliminar.setDisable(false);
    }
    
    private void desableEditDeleteButtons(){
        btnEliminar.setDisable(true);
    }

    

    
    
}

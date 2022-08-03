/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import payrollsys.db.Conexao;
import payrollsys.db.HorasExtrasOp;
import payrollsys.db.SaidaOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Funcionario;
import payrollsys.models.HorasExtras;


public class HorasExtrasController implements Initializable {
    @FXML
    private TableView<HorasExtras> tabelaHorasExtras;
    @FXML
    private TableColumn<HorasExtras, Funcionario> colNomeFuncionario;
    @FXML
    private TableColumn<HorasExtras, Date> colDataHorasExtras;
    @FXML
    private TableColumn<HorasExtras, Time> colTempoInicioHorasExtras;
    @FXML
    private TableColumn<HorasExtras, Time> colTempoFimHorasExtras;
    @FXML
    private TableColumn<HorasExtras, Integer> colTempoHorasExtras;
    @FXML
    private TableColumn<HorasExtras, Double> colPagamentoHorasExtras;
    @FXML
    private JFXButton btnGerarReport;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    

    
    /* Data Collections */
    private ObservableList<HorasExtras> listaHorasExtras = FXCollections.observableArrayList();
    
    /* Selected */
    private HorasExtras selectedHoraExtra = null;
    
    /* Conexao Banco Dados */
    private Conexao conexao;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        desableEditDeleteButtons();
      
        setDataInTable(listaHorasExtras);
        setTableColumns();

        manageTableSelectionMode();
    }    

    @FXML
    private void loadAddHorasExtras(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/addHorasExtras.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(path));

            Stage stage = new Stage();
            stage.setTitle("Adicioar Horas Extras");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }

    @FXML
    private void gerarReport(ActionEvent event) {
        //GERA UM RELATORIO DAS HORAS EXTRAS
        System.out.println("Gerando Reports ...");
        conexao = new Conexao();
        try{
            Date dataActual = Date.valueOf(LocalDate.now());
            HashMap hm = new HashMap();
            hm.put("dataActual", dataActual);
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/payrollsys/reports/reportHorasExtras.jasper"), hm, conexao.getConnection());
            JasperViewer.viewReport(print, false);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        conexao.fecharConexao();
    }
    
    @FXML
    private void actualizarTabela(ActionEvent event) {
        desableEditDeleteButtons();
        setDataInTable(listaHorasExtras);
    }

    @FXML
    private void loadEditarHorasExtras(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/editarHorasExtras.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource(path).openStream());
            
            EditarHorasExtrasController controller = (EditarHorasExtrasController) loader.getController();
            controller.setUI(selectedHoraExtra);
            
            Stage stage = new Stage();
            stage.setTitle("Editar HoraExtra");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }

    @FXML
    private void loadEliminarHorasExtras(ActionEvent event) {
        boolean confirmationResult = DynamicViews.confirmationAlert("Eliminar Registro", "Eliminar horas extras!", "Tens a certeza disso?");
        if(confirmationResult){
            conexao = new Conexao();
            int operationResult = HorasExtrasOp.EliminarHorasExtras(conexao.getConnection(), selectedHoraExtra);
            conexao.fecharConexao();
            if (operationResult == 1) {
                String title = "Registro Eliminado";
                String content = "O Registro foi eliminado com exito!";
                String header = "Resultado:";

                DynamicViews.informationAlert(title, content, header);
            }  
        }
    }
    
    
    /* Metodos Auxiliares */
    private void setTableColumns(){
       colNomeFuncionario.setCellValueFactory(new PropertyValueFactory<HorasExtras, Funcionario>("funcionario"));
       colDataHorasExtras.setCellValueFactory(new PropertyValueFactory<HorasExtras, Date>("dataHorasExtras"));
       colTempoInicioHorasExtras.setCellValueFactory(new PropertyValueFactory<HorasExtras, Time>("entradaHorasExtras"));
       colTempoFimHorasExtras.setCellValueFactory(new PropertyValueFactory<HorasExtras, Time>("saidaHorasExtras"));
       colPagamentoHorasExtras.setCellValueFactory(new PropertyValueFactory<HorasExtras, Double>("pagamentoHorasExtras"));
       colTempoHorasExtras.setCellValueFactory(new PropertyValueFactory<HorasExtras, Integer>("horasExtrasFeita"));
       
    }
    
    private void setDataInTable(ObservableList<HorasExtras> listaHorasExtras){
        conexao = new Conexao();
        listaHorasExtras.clear();
        //Date data = cmbDataPresenca.getValue();
        HorasExtrasOp.SelecionarHorasExtrasData(conexao.getConnection(), Date.valueOf(LocalDate.now()), listaHorasExtras);
        //HorasExtrasOp.SelecionarHorasExtras(conexao.getConnection(), listaHorasExtras);
        //System.out.println(listaHorasExtras.get(0).getFuncionario().getCodigo());
        if(listaHorasExtras.size() > 0){
            btnGerarReport.setDisable(false);
            tabelaHorasExtras.setItems(listaHorasExtras);
        }
        else{
            btnGerarReport.setDisable(true);
        }
        conexao.fecharConexao();
    }
    
    public void manageTableSelectionMode(){
        tabelaHorasExtras.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<HorasExtras>() {
                @Override
                public void changed(ObservableValue<? extends HorasExtras> observable, HorasExtras oldValue, HorasExtras selectedValue) {
                    if(selectedValue != null){
                        System.out.println(selectedValue);
                        selectedHoraExtra = selectedValue;
                        enableEditDeleteButtons();
                    } 
                }
            }
        );
    }
    
    private void enableEditDeleteButtons(){
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
    }
    
    private void desableEditDeleteButtons(){
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
    }

    
}

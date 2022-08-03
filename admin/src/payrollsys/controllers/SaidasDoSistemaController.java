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
import payrollsys.db.SaidaOp;
import payrollsys.db.TurnoOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Saida;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class SaidasDoSistemaController implements Initializable {
    @FXML
    private TableView<Saida> tabelaSaidas;
    @FXML
    private TableColumn<Saida, String> colCodigoFuncionario;
    @FXML
    private TableColumn<Saida, String> colNomeFuncionario;
    @FXML
    private TableColumn<Saida, String> colTipoDeSaida;
    @FXML
    private TableColumn<Saida, Date> colDataInicio;
    @FXML
    private TableColumn<Saida, Date> colDataFim;
    @FXML
    private TableColumn<Saida, Integer> colDias;
    @FXML
    private TableColumn<Saida, String> colMotivo;
    @FXML
    private JFXButton btnGerarReport;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;

    /* Data Collections */
    private ObservableList<Saida> listaSaidas = FXCollections.observableArrayList();
    
    /* Selected */
    private Saida selectedSaida = null;
    
    /* Conexao Banco Dados */
    private Conexao conexao;
    
    
  
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        desableEditDeleteButtons();
      
        setDataInTable(listaSaidas);
        setTableColumns();
        manageTableSelectionMode();
    }    

    @FXML
    private void loadAddSaidasDoSistema(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/addSaidas.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(path));

            Stage stage = new Stage();
            stage.setTitle("Nova Saida");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }
    
    @FXML
    private void gerarReport(ActionEvent event) {
        //GERA UM RELATORIO DE FUNCIONARIOS
        System.out.println("Gerando Reports ...");
        conexao = new Conexao();
        try{
            Date dataActual = Date.valueOf(LocalDate.now());
            HashMap hm = new HashMap();
            hm.put("dataActual", dataActual);
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/payrollsys/reports/reportSaidas.jasper"), hm, conexao.getConnection());
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
        setDataInTable(listaSaidas);
    }

    @FXML
    private void loadEditarSaidasDoSistema(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/editarSaidas.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource(path).openStream());
            
            EditarSaidasController controller = (EditarSaidasController) loader.getController();
            controller.setUI(selectedSaida);
            
            Stage stage = new Stage();
            stage.setTitle("Editar Saida do Sistema");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }

    @FXML
    private void eliminarSaidasDoSistema(ActionEvent event) {
        boolean confirmationResult = DynamicViews.confirmationAlert("Eliminar Registro", "Eliminar saida do sistema!", "Tens a certeza disso?");
        if(confirmationResult){
            conexao = new Conexao();
            int operationResult = SaidaOp.EliminarSaida(conexao.getConnection(), selectedSaida);
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
       colCodigoFuncionario.setCellValueFactory(new PropertyValueFactory<Saida, String>("codigoFuncionario"));
       colNomeFuncionario.setCellValueFactory(new PropertyValueFactory<Saida, String>("nomeFuncionario"));
       colTipoDeSaida.setCellValueFactory(new PropertyValueFactory<Saida, String>("tipoSaida"));
       colDataInicio.setCellValueFactory(new PropertyValueFactory<Saida, Date>("dataInicioSaida"));
       colDataFim.setCellValueFactory(new PropertyValueFactory<Saida, Date>("dataFimSaida"));
       colDias.setCellValueFactory(new PropertyValueFactory<Saida, Integer>("diasSaida"));
       colMotivo.setCellValueFactory(new PropertyValueFactory<Saida, String>("motivoSaida"));
       
    }
    
    private void setDataInTable(ObservableList<Saida> listaSaidas){
        conexao = new Conexao();
        listaSaidas.clear();
        SaidaOp.SelecionarSaidasActivas(conexao.getConnection(), listaSaidas, Date.valueOf(LocalDate.now()));

        if(listaSaidas.size() > 0){
            btnGerarReport.setDisable(false);
            tabelaSaidas.setItems(listaSaidas);
        }
        else{
            btnGerarReport.setDisable(true);
        }
        conexao.fecharConexao();
    }
    
    public void manageTableSelectionMode(){
        tabelaSaidas.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Saida>() {
                @Override
                public void changed(ObservableValue<? extends Saida> observable, Saida oldValue, Saida selectedValue) {
                    if(selectedValue != null){
                        System.out.println(selectedValue);
                        selectedSaida = selectedValue;
                        enableEditDeleteButtons();
                        System.out.println(selectedSaida.getDiasSaida());
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

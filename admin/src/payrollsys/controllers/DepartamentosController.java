/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
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
import payrollsys.db.DepartamentoOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Departamento;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class DepartamentosController implements Initializable {
    @FXML
    private TableView<Departamento> tabelaDepartamentos;
    @FXML
    private TableColumn<Departamento, String> colNome;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    
    
    /* Data collections */
    private ObservableList<Departamento> listaDepartamentos = FXCollections.observableArrayList();
    
    /* Selected */
    private Departamento selectedDepartamento = null;
    
    /* Conexao Banco Dados */
    private Conexao conexao = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desableEditDeleteButtons();
        
        setDataInTable(listaDepartamentos);
        setTableColumns();

        manageTableSelectionMode();
    }    

    @FXML
    private void loadAddDepartamentos(ActionEvent event) {
        //DynamicViews.getwindows("Novo Departamento", "/payrollsys/ui/addDepartamento.fxml");
        try {
            String path = "/payrollsys/ui/addDepartamento.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(path));

            Stage stage = new Stage();
            stage.setTitle("Novo Departamento");
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
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/payrollsys/reports/reportDepartamentos.jasper"), null, conexao.getConnection());
            JasperViewer.viewReport(print, false);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        conexao.fecharConexao();
    }
    
    @FXML
    private void actualizarTabela(ActionEvent event) {
        setDataInTable(listaDepartamentos);
        desableEditDeleteButtons();
    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/editarDepartamento.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource(path).openStream());
            
            EditarDepartamentoController controller = (EditarDepartamentoController) loader.getController();
            controller.setUI(selectedDepartamento);
            
            Stage stage = new Stage();
            stage.setTitle("Editar Departamento");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {
        boolean confirmationResult = DynamicViews.confirmationAlert("Eliminar Registro", "Eliminar departamento!", "Tens a certeza disso?");
        if(confirmationResult){
            conexao = new Conexao();
            int operationResult = DepartamentoOp.EliminarDados(conexao.getConnection(), selectedDepartamento);
            conexao.fecharConexao();
            if (operationResult == 1) {
                String title = "Registro Eliminado";
                String content = "O Registro foi eliminado com exito!";
                String header = "Resultado:";

                DynamicViews.informationAlert(title, content, header);
            }  
        }
    } 
    
    
    //METODOS AUXILIARES 
    private void setTableColumns(){
       colNome.setCellValueFactory(new PropertyValueFactory<Departamento, String>("nome"));
    }
    
    private void setDataInTable(ObservableList<Departamento> listaDepartamentos){
        conexao = new Conexao();
        listaDepartamentos.clear();
        DepartamentoOp.SelecionarDados(conexao.getConnection(), listaDepartamentos);
        if(listaDepartamentos.size() > 0){
            tabelaDepartamentos.setItems(listaDepartamentos);
        }
        conexao.fecharConexao();
    }
    
    public void manageTableSelectionMode(){
        tabelaDepartamentos.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Departamento>() {
                @Override
                public void changed(ObservableValue<? extends Departamento> observable, Departamento oldValue, Departamento selectedValue) {
                    if(selectedValue != null){
                        System.out.println(selectedValue);
                        selectedDepartamento = selectedValue;
                        enableEditDeleteButtons();
                    }
                }
            }
        );
    }
    
    private void enableEditDeleteButtons(){
        btnEditar.setDisable(false);
        btnEliminar.setDisable(false);
    }
    
    private void desableEditDeleteButtons(){
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
    }

    

      
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import payrollsys.db.CargoOp;
import payrollsys.db.Conexao;
import payrollsys.db.TurnoOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Cargo;
import payrollsys.models.Turno;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class TurnosController implements Initializable {
    /* GUI Components */
    @FXML
    private TableView<Turno> tabelaTurnos;
    @FXML
    private TableColumn<Turno, String> colNome;
    @FXML
    private TableColumn<Turno, Time> colEntrada;
    @FXML
    private TableColumn<Turno, Time> colSaida;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private ImageView imgTurno;
    
    
    /* Data collections */
    private ObservableList<Turno> listaTurnos = FXCollections.observableArrayList();
    
    /* Selected */
    private Turno selectedTurno = null;
    
    /* Conexao Banco Dados */
    private Conexao conexao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desableEditDeleteButtons();
      
        setDataInTable(listaTurnos);
        setTableColumns();

        manageTableSelectionMode();
    }    

    @FXML
    private void loadAddTurno(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/addTurno.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(path));

            Stage stage = new Stage();
            stage.setTitle("Novo Turno");
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
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/payrollsys/reports/reportTurnos.jasper"), null, conexao.getConnection());
            JasperViewer.viewReport(print, false);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        conexao.fecharConexao();
    }

    @FXML
    private void actualizarTabela(ActionEvent event) {
        setDataInTable(listaTurnos);
        desableEditDeleteButtons();
    }

    @FXML
    private void editarTurno(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/editarTurno.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource(path).openStream());
            
            EditarTurnoController controller = (EditarTurnoController) loader.getController();
            controller.setUI(selectedTurno);
            
            Stage stage = new Stage();
            stage.setTitle("Editar Turno");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }

    @FXML
    private void eliminarTurno(ActionEvent event) {
        boolean confirmationResult = DynamicViews.confirmationAlert("Eliminar Registro", "Eliminar turno!", "Tens a certeza disso?");
        if(confirmationResult){
            conexao = new Conexao();
            int operationResult = TurnoOp.EliminarTurno(conexao.getConnection(), selectedTurno);
            conexao.fecharConexao();
            if (operationResult == 1) {
                String title = "Registro Eliminado";
                String content = "O Registro foi eliminado com exito!";
                String header = "Resultado:";

                DynamicViews.informationAlert(title, content, header);
            }  
        }
    }
    
    
    /* Metodos auxiliares */
    private void setTableColumns(){
       colNome.setCellValueFactory(new PropertyValueFactory<Turno, String>("nome"));
       colEntrada.setCellValueFactory(new PropertyValueFactory<Turno, Time>("entrada"));
       colSaida.setCellValueFactory(new PropertyValueFactory<Turno, Time>("saida"));
    }
    
    private void setDataInTable(ObservableList<Turno> listaTurnos){
        conexao = new Conexao();
        listaTurnos.clear();
        TurnoOp.SelecionarTurnos(conexao.getConnection(), listaTurnos);
        if(listaTurnos.size() > 0){
            tabelaTurnos.setItems(listaTurnos);
        }
        conexao.fecharConexao();
    }
    
    public void manageTableSelectionMode(){
        tabelaTurnos.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Turno>() {
                @Override
                public void changed(ObservableValue<? extends Turno> observable, Turno oldValue, Turno selectedValue) {
                    if(selectedValue != null){
                        System.out.println(selectedValue);
                        selectedTurno = selectedValue;
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

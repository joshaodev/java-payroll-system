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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import payrollsys.db.CargoOp;
import payrollsys.db.Conexao;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Cargo;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class CargosController implements Initializable {
    //GUI components
    @FXML
    private TableView<Cargo> tabelaCargo;
    @FXML
    private TableColumn<Cargo, String> colNome;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private ImageView imgCargo;
    
    
    /* Data collections */
    private ObservableList<Cargo> listaCargos = FXCollections.observableArrayList();
    
    //Var to connect to database.
    private Conexao conexao = null;
    /* Selected */
    private Cargo selectedCargo = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        desableEditDeleteButtons();
        
        setDataInTable(listaCargos);
        setTableColumns();

        manageTableSelectionMode();
    }    

    @FXML
    private void loadAddCargo(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/addCargo.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(path));

            Stage stage = new Stage();
            stage.setTitle("Novo Cargo");
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
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/payrollsys/reports/reportCargos.jasper"), null, conexao.getConnection());
            JasperViewer.viewReport(print, false);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        conexao.fecharConexao();
    }

    @FXML
    private void actualizarTabela(ActionEvent event) {
        setDataInTable(listaCargos);
        desableEditDeleteButtons();
    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/editarCargo.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource(path).openStream());
            
            EditarCargoController controller = (EditarCargoController) loader.getController();
            controller.setUI(selectedCargo);
            
            Stage stage = new Stage();
            stage.setTitle("Editar Cargo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }
    
    @FXML
    private void eliminar(ActionEvent event) {
        boolean confirmationResult = DynamicViews.confirmationAlert("Eliminar Registro", "Eliminar cargo!", "Tens a certeza disso?");
        if(confirmationResult){
            conexao = new Conexao();
            int operationResult = CargoOp.EliminarCargo(conexao.getConnection(), selectedCargo);
            conexao.fecharConexao();
            if (operationResult == 1) {
                String title = "Registro Eliminado";
                String content = "O Registro foi eliminado com exito!";
                String header = "Resultado:";

                DynamicViews.informationAlert(title, content, header);
            }  
        }
    }
    
    
    /* =============== Metodos auxiliares ====================== */
    private void setTableColumns(){
       colNome.setCellValueFactory(new PropertyValueFactory<Cargo, String>("nome"));
    }
    
    private void setDataInTable(ObservableList<Cargo> listaCargos){
        conexao = new Conexao();
        listaCargos.clear();
        CargoOp.SelecionarCargos(conexao.getConnection(), listaCargos);
        if(listaCargos.size() > 0){
            tabelaCargo.setItems(listaCargos);
        }
        conexao.fecharConexao();
    }
    
    public void manageTableSelectionMode(){
        tabelaCargo.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Cargo>() {
                @Override
                public void changed(ObservableValue<? extends Cargo> observable, Cargo oldValue, Cargo selectedValue) {
                    if(selectedValue != null){
                        System.out.println(selectedValue);
                        selectedCargo = selectedValue;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import payrollsys.db.CargoOp;
import payrollsys.db.Conexao;
import payrollsys.db.DepartamentoOp;
import payrollsys.db.FuncionarioOp;
import payrollsys.db.TurnoOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Cargo;
import payrollsys.models.Departamento;
import payrollsys.models.Funcionario;
import payrollsys.models.Turno;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class AddFuncionarioController implements Initializable {
    //GUI components
    @FXML
    private JFXTextField textFieldCodigo;
    @FXML
    private JFXTextField textFieldNome;
    @FXML
    private JFXTextArea textEndereco;
    @FXML
    private JFXTextField textFieldTelefone;
    @FXML
    private JFXTextField textFieldEmail;
    @FXML
    private JFXDatePicker dataPickerNascimento;
    @FXML
    private JFXComboBox<String> cmbGenero;
    @FXML
    private JFXComboBox<String> cmbEstadoCivil;
    @FXML
    private ImageView imgFoto;
    @FXML
    private JFXButton btnImg;
    @FXML
    private JFXTextField textFieldTituloEmprego;
    @FXML
    private JFXComboBox<Departamento> cmbDepartamento;
    @FXML
    private JFXComboBox<Cargo> cmbCargo;
    @FXML
    private JFXComboBox<Turno> cmbTurno;
    @FXML
    private JFXDatePicker dataPickerContrato;
    @FXML
    private JFXTextField textFieldSalarioBase;

    
    // Data collections (list to store datas from db).
    private ObservableList<Departamento> listaDepartamentos = FXCollections.observableArrayList();
    private ObservableList<Cargo> listaCargos = FXCollections.observableArrayList();
    private ObservableList<Turno> listaTurnos = FXCollections.observableArrayList();
    
    private ObservableList<String> listaGenero = FXCollections.observableArrayList("Masculino","Feminino");
    private ObservableList<String> listaEstadoCivil = FXCollections.observableArrayList("Solteiro", "Casado", "Divorciado", "Viuvo");
    
    //Var to connect to database.
    Conexao conexao;
    //To store image path
    String camihoImagem = "";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize
        
        loadFromBDComboBoxesValues();
    }    

    @FXML
    private void carregarCaminhoFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);

        //Define the initial directory
        fileChooser.setInitialDirectory (new File("C:\\Users\\Celma Oatanha\\Desktop\\Projecto PayrollSys\\NetBeansProjects\\PayrollSys_Admin\\src\\payrollsys\\images"));
        //C:\Users\Celma Oatanha\Desktop\Projecto PayrollSys\NetBeansProjects\PayrollSys_Admin\src\payrollsys\images
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            camihoImagem = "/payrollsys/images/" + file.getName();
            System.out.println(camihoImagem);
            Image image = new Image(camihoImagem);
            imgFoto.setImage(image);
                
            btnImg.setText(file.getName());
        }
    }

    @FXML
    private void addFuncionario(ActionEvent event) {
        Funcionario funcionarioToInsert = new Funcionario();
        
        funcionarioToInsert.setCodigo(textFieldCodigo.getText());
        funcionarioToInsert.setNome(textFieldNome.getText());
        funcionarioToInsert.setEndereco(textEndereco.getText());
        funcionarioToInsert.setTelefone(textFieldTelefone.getText());
        funcionarioToInsert.setEmail(textFieldEmail.getText());
        funcionarioToInsert.setDataNascimento(Date.valueOf(dataPickerNascimento.getValue()));
        funcionarioToInsert.setGenero(cmbGenero.getValue());
        funcionarioToInsert.setEstadoCivil(cmbEstadoCivil.getValue());
        
        if(camihoImagem != ""){
            funcionarioToInsert.setFoto(camihoImagem);
        }
        else {
            funcionarioToInsert.setFoto("/payrollsys/images/profile.jpg");
        }
        funcionarioToInsert.setDepartamento(cmbDepartamento.getValue());
        funcionarioToInsert.setCargo(cmbCargo.getValue());
        funcionarioToInsert.setTurno(cmbTurno.getValue());
        funcionarioToInsert.setDataContrato(Date.valueOf(dataPickerContrato.getValue()));
        funcionarioToInsert.setSalarioBase(Double.valueOf(textFieldSalarioBase.getText()));
        
        conexao = new Conexao();
        int result = FuncionarioOp.InserirFuncionario(conexao.getConnection(), funcionarioToInsert);
        conexao.fecharConexao();
        
        if (result == 1) {
            String title = "Registro Inserido";
            String content = "O Registro foi inserido com exito!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
        }
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    
    // MÉTODOS AUXILIARES
    private void loadFromBDComboBoxesValues(){
 
        listaDepartamentos.clear();
        listaCargos.clear();
        listaTurnos.clear();
        
        conexao = new Conexao();
        DepartamentoOp.SelecionarDados(conexao.getConnection(), listaDepartamentos);
        CargoOp.SelecionarCargos(conexao.getConnection(), listaCargos);
        TurnoOp.SelecionarTurnos(conexao.getConnection(), listaTurnos);
        conexao.fecharConexao();
        
        cmbDepartamento.setItems(listaDepartamentos);
        cmbCargo.setItems(listaCargos);
        cmbTurno.setItems(listaTurnos);
        
        cmbGenero.setItems(listaGenero);
        cmbEstadoCivil.setItems(listaEstadoCivil);        
    }
    
}

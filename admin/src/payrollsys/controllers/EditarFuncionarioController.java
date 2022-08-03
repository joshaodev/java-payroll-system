/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import payrollsys.db.CargoOp;
import payrollsys.db.Conexao;
import payrollsys.db.DepartamentoOp;
import payrollsys.db.FuncionarioOp;
import payrollsys.db.TurnoOp;
import payrollsys.models.Cargo;
import payrollsys.models.Departamento;
import payrollsys.models.Funcionario;
import payrollsys.models.Turno;
import payrollsys.utils.Actions;
import payrollsys.utils.DynamicViews;


public class EditarFuncionarioController implements Initializable {
    @FXML
    private JFXCheckBox checkBoxActive;
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

    
    /* Data collections */
    private ObservableList<Departamento> listaDepartamentos = FXCollections.observableArrayList();
    private ObservableList<Cargo> listaCargos = FXCollections.observableArrayList();
    private ObservableList<Turno> listaTurnos = FXCollections.observableArrayList();
    
    private ObservableList<String> listaGenero = FXCollections.observableArrayList("Masculino","Feminino");
    private ObservableList<String> listaEstadoCivil = FXCollections.observableArrayList("Solteiro", "Casado", "Divorciado", "Viuvo");
    
    Conexao conexao;
    private Funcionario funcionarioToEdit;
    
    String camihoImagem;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadFromBDComboBoxesValues();
    }    

    @FXML
    private void carregarCaminhoFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
             
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = 
                    new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
            FileChooser.ExtensionFilter extFilterjpg = 
                    new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
            FileChooser.ExtensionFilter extFilterPNG = 
                    new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
            FileChooser.ExtensionFilter extFilterpng = 
                    new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
            fileChooser.getExtensionFilters()
                    .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
            
            //Define the initial directory
            fileChooser.setInitialDirectory (new File("C:\\Users\\Devs\\Documents\\Projecto PayrollSys\\NetBeansProjects\\PayrollSys_Admin\\src\\payrollsys\\images"));
            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);
            if(file != null){
                camihoImagem = "/payrollsys/images/" + file.getName();
                System.out.println(camihoImagem);
                
                Image image = new Image(camihoImagem);
                imgFoto.setImage(image);
                
                btnImg.setText(file.getName());
            }
           //C:\Users\joshtag096\Documents\NetBeansProjects\PayrollSys_Admin\src\payrollsys\images
           ///payrollsys/images/IMG_JAO.jpg
    }

    @FXML
    private void editarFuncionario(ActionEvent event) {
        funcionarioToEdit.setCodigo(textFieldCodigo.getText());
        funcionarioToEdit.setCodigo(textFieldCodigo.getText());
        funcionarioToEdit.setNome(textFieldNome.getText());
        funcionarioToEdit.setEndereco(textEndereco.getText());
        funcionarioToEdit.setTelefone(textFieldTelefone.getText());
        funcionarioToEdit.setEmail(textFieldEmail.getText());
        funcionarioToEdit.setDataNascimento(Date.valueOf(dataPickerNascimento.getValue()));
        funcionarioToEdit.setGenero(cmbGenero.getValue());
        funcionarioToEdit.setEstadoCivil(cmbEstadoCivil.getValue());
        funcionarioToEdit.setDepartamento(cmbDepartamento.getValue());
        funcionarioToEdit.setCargo(cmbCargo.getValue());
        funcionarioToEdit.setTurno(cmbTurno.getValue());
        funcionarioToEdit.setDataContrato(Date.valueOf(dataPickerContrato.getValue()));
        funcionarioToEdit.setSalarioBase(Double.valueOf(textFieldSalarioBase.getText()));
        funcionarioToEdit.setFoto(camihoImagem);
        
        if(checkBoxActive.isSelected()){
            funcionarioToEdit.setActive(1);
        }
        else{
            funcionarioToEdit.setActive(0);
        }
        
        conexao = new Conexao();
        int result = FuncionarioOp.ActualizarFuncionario(conexao.getConnection(), funcionarioToEdit);
        conexao.fecharConexao();
        
        if (result == 1) {
            String title = "Registro Actualizado";
            String content = "O Registro foi actualizado com exito!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
        }
        Actions.closeWindow(event);
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        Actions.closeWindow(event);
    }
    
    
    /* Metodos Auxiliares */
    private void loadFromBDComboBoxesValues(){
        listaDepartamentos.clear();
        listaCargos.clear();
        listaTurnos.clear();
        
        conexao = new Conexao();
        DepartamentoOp.SelecionarDados(conexao.getConnection(), listaDepartamentos);
        CargoOp.SelecionarCargos(conexao.getConnection(), listaCargos);
        TurnoOp.SelecionarTurnos(conexao.getConnection(), listaTurnos);
        conexao.fecharConexao();
        
        cmbGenero.setItems(listaGenero);
        cmbEstadoCivil.setItems(listaEstadoCivil);
        
        cmbDepartamento.setItems(listaDepartamentos);
        cmbCargo.setItems(listaCargos);
        cmbTurno.setItems(listaTurnos);  
        
    }
    
    public void setUI(Funcionario funcionario){
        this.funcionarioToEdit = funcionario;
        
        textFieldCodigo.setText(funcionarioToEdit.getCodigo());
        textFieldNome.setText(funcionarioToEdit.getNome());
        textEndereco.setText(funcionarioToEdit.getEndereco());
        textFieldTelefone.setText(funcionarioToEdit.getTelefone());
        textFieldEmail.setText(funcionario.getEmail());
        dataPickerNascimento.setValue(funcionarioToEdit.getDataNascimento().toLocalDate());
        cmbGenero.setValue(funcionarioToEdit.getGenero());
        cmbEstadoCivil.setValue(funcionarioToEdit.getEstadoCivil());    
        if(funcionarioToEdit.getFoto() != null){
            imgFoto.setImage(new Image(funcionarioToEdit.getFoto()));
        }
        else {
            funcionarioToEdit.setFoto("/payrollsys/images/profile.jpg");
            imgFoto.setImage(new Image(funcionarioToEdit.getFoto()));
        }
        btnImg.setText(funcionarioToEdit.getFoto());
        cmbDepartamento.setValue(funcionarioToEdit.getDepartamento());
        System.out.println(funcionarioToEdit.getCargo());
        cmbCargo.setValue(funcionarioToEdit.getCargo());
        cmbTurno.setValue(funcionarioToEdit.getTurno());
        dataPickerContrato.setValue(funcionarioToEdit.getDataContrato().toLocalDate());
        textFieldSalarioBase.setText(Double.toString(funcionarioToEdit.getSalarioBase()));
                
        if(funcionarioToEdit.getActive() == 1){
            checkBoxActive.setSelected(true);
        }
        else if (funcionarioToEdit.getActive() == 0){
            checkBoxActive.setSelected(false);
        }        
    }

}

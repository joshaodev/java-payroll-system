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
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import payrollsys.db.Conexao;
import payrollsys.db.FuncionarioOp;
import payrollsys.db.SaidaOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Funcionario;
import payrollsys.models.Saida;
import payrollsys.utils.Actions;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class AddSaidasController implements Initializable {
    //GUI components
    @FXML
    private JFXTextField textFieldCodigoFuncionario;
    @FXML
    private JFXTextField textFieldNomeFuncionario;
    @FXML
    private JFXTextField textFieldTurnoFuncionario;
    @FXML
    private JFXComboBox<String> cmbTipoDeSaida;
    @FXML
    private JFXDatePicker dataPickerInicioSaida;
    @FXML
    private JFXDatePicker dataPickerFimSaida;
    @FXML
    private JFXTextArea textMotivoSaida;
    @FXML
    private JFXButton btnInserir;
    
    
    private ObservableList<String> listaTiposSaidas = FXCollections.observableArrayList("Doença", "Licença", "Ferias", "Outros");
    
    //Var to connect to database.
    Conexao conexao;
    // Var to store searched Funcionario
    private Funcionario searchFuncionario = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTipoDeSaida.setItems(listaTiposSaidas);
    }    

    @FXML
    private void pesquisarFuncionario(ActionEvent event) {
        
        String codigoFuncionario = textFieldCodigoFuncionario.getText();
        conexao = new Conexao();
        searchFuncionario = FuncionarioOp.SelecionarFuncionario(conexao.getConnection(), codigoFuncionario);
        
        if(searchFuncionario != null){
           textFieldNomeFuncionario.setText(searchFuncionario.getNome());
           textFieldTurnoFuncionario.setText(searchFuncionario.getTurno().getNome());
        }
        else{
            System.out.println("Funcionario não existe!!!");
            String title = "Pesquisa Indesejada";
            String content = "O campo esta vazio ou o funcionario não existe!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
            //textFieldCodigoFuncionario.setText("");
        }
        conexao.fecharConexao();
    }

    @FXML
    private void inserirSaida(ActionEvent event) {
        Saida saidaToInsert = new Saida();
        
        saidaToInsert.setTipoSaida(cmbTipoDeSaida.getValue());
        saidaToInsert.setDataInicioSaida(Date.valueOf(dataPickerInicioSaida.getValue()));
        saidaToInsert.setDataFimSaida(Date.valueOf(dataPickerFimSaida.getValue()));
        saidaToInsert.setMotivoSaida(textMotivoSaida.getText());
        saidaToInsert.setFuncionarioSaida(searchFuncionario);
         
        conexao = new Conexao();
        int result = SaidaOp.InserirSaida(conexao.getConnection(), saidaToInsert);
        conexao.fecharConexao();
        
        if (result == 1) {
            String title = "Registro Inserido";
            String content = "O Registro foi inserido com exito!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
        }
        Actions.closeWindow(event);
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        Actions.closeWindow(event);
    }   
}

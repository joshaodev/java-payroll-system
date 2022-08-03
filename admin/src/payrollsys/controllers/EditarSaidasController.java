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
import javafx.scene.Node;
import payrollsys.db.Conexao;
import payrollsys.db.FuncionarioOp;
import payrollsys.db.SaidaOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Funcionario;
import payrollsys.models.Saida;
import payrollsys.models.Turno;
import payrollsys.utils.Actions;


public class EditarSaidasController implements Initializable {
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
    private JFXButton btncalcularSalario;
    @FXML
    private JFXButton btnPostarFolhaDeSalario;
    
    private ObservableList<String> listaTiposSaidas = FXCollections.observableArrayList("Doença", "Licença", "Ferias", "Outros");
    
    Conexao conexao;
    private Saida saidaToEdit;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cmbTipoDeSaida.setItems(listaTiposSaidas);
        
    }    

    @FXML
    private void pesquisarFuncionario(ActionEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Actions.closeWindow(event);
    }

    @FXML
    private void editarSaida(ActionEvent event) {
        conexao = new Conexao();
        
        saidaToEdit.setTipoSaida(cmbTipoDeSaida.getValue());
        saidaToEdit.setDataInicioSaida(Date.valueOf(dataPickerInicioSaida.getValue()));
        saidaToEdit.setDataInicioSaida(Date.valueOf(dataPickerFimSaida.getValue()));
        saidaToEdit.setMotivoSaida(textMotivoSaida.getText());
        
        int result = SaidaOp.ActualizarSaida(conexao.getConnection(), saidaToEdit);
        conexao.fecharConexao();
        
        if (result == 1) {
            String title = "Registro Actualizado";
            String content = "O Registro foi actualizado com exito!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
        }
        Actions.closeWindow(event);
    }
    
    public void setUI(Saida saida){
        this.saidaToEdit = saida;
        
        String codigoFuncionario = saidaToEdit.getFuncionarioSaida().getCodigo();
        conexao = new Conexao();
        Funcionario searchFuncionario = FuncionarioOp.SelecionarFuncionario(conexao.getConnection(), codigoFuncionario);
        conexao.fecharConexao();
        
        textFieldCodigoFuncionario.setText(saidaToEdit.getFuncionarioSaida().getCodigo());
        textFieldNomeFuncionario.setText(saidaToEdit.getFuncionarioSaida().getNome());
        textFieldTurnoFuncionario.setText(searchFuncionario.getTurno().getNome());
        cmbTipoDeSaida.setValue(saidaToEdit.getTipoSaida());
        dataPickerInicioSaida.setValue(saidaToEdit.getDataInicioSaida().toLocalDate());
        dataPickerFimSaida.setValue(saidaToEdit.getDataFimSaida().toLocalDate());
        textMotivoSaida.setText(saidaToEdit.getMotivoSaida());
        
    }
    
}

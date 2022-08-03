/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import payrollsys.db.Conexao;
import payrollsys.db.FuncionarioOp;
import payrollsys.db.HorasExtrasOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Funcionario;
import payrollsys.models.HorasExtras;
import payrollsys.utils.Actions;


public class EditarHorasExtrasController implements Initializable {
    @FXML
    private JFXTextField textFieldCodigoFuncionario;
    @FXML
    private JFXTextField textFieldNomeFuncionario;
    @FXML
    private JFXTextField textFieldCargoFuncionario;
    @FXML
    private JFXTextField textFieldTurnoFuncionario;
    @FXML
    private JFXDatePicker dataPickerHoraExtra;
    @FXML
    private JFXTimePicker timePickerEntrada;
    @FXML
    private JFXTimePicker timePickerSaida;
    @FXML
    private JFXTextField textFieldPagamentosPorHora;

    
    Conexao conexao;
    private HorasExtras horasExtrasToEdit;
    private Funcionario funcionario;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timePickerEntrada.setIs24HourView(true);
        timePickerSaida.setIs24HourView(true);
    }    

    @FXML
    private void cancelar(ActionEvent event) {
        Actions.closeWindow(event);
    }

    @FXML
    private void editarHorasExtras(ActionEvent event) {
        conexao = new Conexao();
        
        horasExtrasToEdit.setDataHorasExtras(Date.valueOf(dataPickerHoraExtra.getValue()));
        horasExtrasToEdit.setEntradaHorasExtras(Time.valueOf(timePickerEntrada.getValue()));
        horasExtrasToEdit.setSaidaHorasExtras(Time.valueOf(timePickerSaida.getValue()));
        horasExtrasToEdit.setHorasExtrasFeita(horasExtrasToEdit.getHorasExtrasFeita());
        horasExtrasToEdit.setPagamentoHorasExtras(Double.valueOf(textFieldPagamentosPorHora.getText()));
        int result = HorasExtrasOp.ActualizarHorasExtras(conexao.getConnection(), horasExtrasToEdit);
        conexao.fecharConexao();
        
        if (result == 1) {
            String title = "Registro Actualizado";
            String content = "O Registro foi actualizado com exito!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
        }
        Actions.closeWindow(event);
    }
    
    
    public void setUI(HorasExtras horasExtras){
        this.horasExtrasToEdit = horasExtras;  
        conexao = new Conexao();
        this.funcionario = FuncionarioOp.SelecionarFuncionario(conexao.getConnection(), horasExtrasToEdit.getFuncionario().getCodigo());
        conexao.fecharConexao();
        setFields();
    }
    
    public void setFields(){
        textFieldCodigoFuncionario.setText(funcionario.getCodigo());
        textFieldNomeFuncionario.setText(funcionario.getNome());
        textFieldCargoFuncionario.setText(funcionario.getCargo().getNome());
        textFieldTurnoFuncionario.setText(funcionario.getTurno().getNome());
        dataPickerHoraExtra.setValue(horasExtrasToEdit.getDataHorasExtras().toLocalDate());
        timePickerEntrada.setValue(horasExtrasToEdit.getEntradaHorasExtras().toLocalTime());
        timePickerSaida.setValue(horasExtrasToEdit.getSaidaHorasExtras().toLocalTime());
        textFieldPagamentosPorHora.setText(String.valueOf(horasExtrasToEdit.getPagamentoHorasExtras()));
    }
    
}

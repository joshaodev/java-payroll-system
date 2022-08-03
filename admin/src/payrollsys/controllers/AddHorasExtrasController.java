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
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import payrollsys.db.Conexao;
import payrollsys.db.FuncionarioOp;
import payrollsys.db.HorasExtrasOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Funcionario;
import payrollsys.models.HorasExtras;
import payrollsys.utils.Actions;

public class AddHorasExtrasController implements Initializable {
    //GUI components
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
    
    //Var to connect to database.
    Conexao conexao;
    // Var to store selected Funcionario
    Funcionario funcionarioSearched = null; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timePickerEntrada.setIs24HourView(true);
        timePickerSaida.setIs24HourView(true);
    }    

    @FXML
    private void pesquisar(ActionEvent event) {
        conexao = new Conexao();
        funcionarioSearched = FuncionarioOp.SelecionarFuncionario(conexao.getConnection(), textFieldCodigoFuncionario.getText());
        conexao.fecharConexao();
        textFieldNomeFuncionario.setText(funcionarioSearched.getNome());
        textFieldCargoFuncionario.setText(funcionarioSearched.getCargo().getNome());
        textFieldTurnoFuncionario.setText(funcionarioSearched.getTurno().getNome());
    }

    @FXML
    private void addHorasExtras(ActionEvent event) {
        HorasExtras horaExtraToInsert = new HorasExtras();
        LocalDate dataSelecionada = dataPickerHoraExtra.getValue();
        if(dataSelecionada.isBefore(LocalDate.now())){
            String title = "Inserção de Registro";
            String content = "A data selecionada já passou, Selecione outra data!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
        }
        else{
            horaExtraToInsert.setDataHorasExtras(Date.valueOf(dataPickerHoraExtra.getValue()));
            horaExtraToInsert.setEntradaHorasExtras(Time.valueOf(timePickerEntrada.getValue().toString().concat(":00")));
            horaExtraToInsert.setSaidaHorasExtras(Time.valueOf(timePickerSaida.getValue().toString().concat(":00")));
            horaExtraToInsert.setHorasExtrasFeita(horaExtraToInsert.getHorasExtrasFeita());
            horaExtraToInsert.setPagamentoHorasExtras(Double.valueOf(textFieldPagamentosPorHora.getText()));
            horaExtraToInsert.setFuncionario(funcionarioSearched);

            conexao = new Conexao();
            int result = HorasExtrasOp.InserirHorasExtras(conexao.getConnection(), horaExtraToInsert);
            conexao.fecharConexao();

            if (result == 1) {
                String title = "Registro Inserido";
                String content = "O Registro foi inserido com exito!";
                String header = "Resultado:";
                DynamicViews.informationAlert(title, content, header);
            }
            Actions.closeWindow(event);
        }  
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        Actions.closeWindow(event);
    }
    
}

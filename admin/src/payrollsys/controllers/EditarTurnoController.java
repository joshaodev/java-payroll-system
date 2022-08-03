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
import java.sql.Time;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import payrollsys.db.Conexao;
import payrollsys.db.TurnoOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Turno;
import payrollsys.utils.Actions;


public class EditarTurnoController implements Initializable {
    @FXML
    private JFXTextField fextFieldNome;
    @FXML
    private JFXTimePicker timePickerEntrada;
    @FXML
    private JFXTimePicker timePickerSaida;    
    @FXML
    private JFXDatePicker dtpick;
    
    
    Conexao conexao;
    private Turno turnoToEdit;
    
    
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
    private void actualizar(ActionEvent event) {
        conexao = new Conexao();
        
        turnoToEdit.setNome(fextFieldNome.getText());        
        //Pega os valores capturado do timePicker concatena a eles os segundos(:00) e coloca eles a time do objecto a editar.
        turnoToEdit.setEntrada(Time.valueOf(timePickerEntrada.getValue().toString().concat(":00")));
        turnoToEdit.setSaida(Time.valueOf(timePickerSaida.getValue().toString().concat(":00")));
     
        int result = TurnoOp.ActualizarTurno(conexao.getConnection(), turnoToEdit);
        conexao.fecharConexao();
        
        if (result == 1) {
            String title = "Registro Actualizado";
            String content = "O Registro foi actualizado com exito!";
            String header = "Resultado:";
            DynamicViews.informationAlert(title, content, header);
        }
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    
    public void setUI(Turno turno){
        this.turnoToEdit = turno;
        fextFieldNome.setText(turno.getNome());
        timePickerEntrada.setValue(turno.getEntrada().toLocalTime());
        timePickerSaida.setValue(turno.getSaida().toLocalTime());
    }
}



package payrollsys.controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import payrollsys.db.Conexao;
import payrollsys.db.TurnoOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Turno;
import payrollsys.utils.Actions;


public class AddTurnoController implements Initializable {
    //GUI components
    @FXML
    private JFXTextField fextFieldNome;
    @FXML
    private JFXTimePicker timePickerEntrada;
    @FXML
    private JFXTimePicker timePickerSaida;

    //Var to connect to database.
    Conexao conexao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timePickerEntrada.setIs24HourView(true);
        timePickerSaida.setIs24HourView(true);
    }

    @FXML
    private void addTurno(ActionEvent event) {
        Turno turnoToInsert = new Turno();
        
        turnoToInsert.setNome(fextFieldNome.getText());
        //Pega os valores capturado do timePicker concatena a eles os segundos(:00) e coloca eles a time do objecto a inserir.
        turnoToInsert.setEntrada(Time.valueOf(timePickerEntrada.getValue().toString().concat(":00")));
        turnoToInsert.setSaida(Time.valueOf(timePickerSaida.getValue().toString().concat(":00")));
        
        conexao = new Conexao();
        int result = TurnoOp.InserirTurno(conexao.getConnection(), turnoToInsert);
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

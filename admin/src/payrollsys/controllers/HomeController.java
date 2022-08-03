/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import payrollsys.db.CargoOp;
import payrollsys.db.Conexao;
import payrollsys.db.DepartamentoOp;
import payrollsys.db.FuncionarioOp;


public class HomeController implements Initializable {
    @FXML
    private Text numFuncionariosRegistrados;
    @FXML
    private Text numFuncionariosHomensRegistrados;
    @FXML
    private Text numFuncionariosMulherRegistrados;
    @FXML
    private Text numDepartamentosRegistrados;
    @FXML
    private Text numCargosRegistrados;

    
    Conexao conexao;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setFields();
    }    
    
    private void setFields(){
        conexao = new Conexao();
        
        int funcs = FuncionarioOp.numeroFuncionarios(conexao.getConnection());
        numFuncionariosRegistrados.setText(String.valueOf(funcs));
        
        int funcsHomens = FuncionarioOp.numeroFuncionariosHomens(conexao.getConnection());
        numFuncionariosHomensRegistrados.setText(String.valueOf(funcsHomens));
        
        int funcsMulheres = FuncionarioOp.numeroFuncionariosMulheres(conexao.getConnection());
        numFuncionariosMulherRegistrados.setText(String.valueOf(funcsMulheres));
        
        int depts = DepartamentoOp.numeroDepartamentos(conexao.getConnection());
        numDepartamentosRegistrados.setText(String.valueOf(depts));
        
        int cargos = CargoOp.numeroCargos(conexao.getConnection());
        numCargosRegistrados.setText(String.valueOf(cargos));
        
        conexao.fecharConexao();
    }
    
}

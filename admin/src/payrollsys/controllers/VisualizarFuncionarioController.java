/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import payrollsys.db.Conexao;
import payrollsys.models.Funcionario;
import payrollsys.utils.Actions;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class VisualizarFuncionarioController implements Initializable {
    /* GUI Components */
    @FXML
    private ImageView imgFoto;
    @FXML
    private Text departamento;
    @FXML
    private Text cargo;
    @FXML
    private Text turno;
    @FXML
    private Text dataCadastro;
    @FXML
    private Text salarioBase;
    @FXML
    private Text nomeFuncionario;
    @FXML
    private Text codigoFuncionario;
    @FXML
    private Text endereco;
    @FXML
    private Text telefone;
    @FXML
    private Text email;
    @FXML
    private Text dataNascimento;
    @FXML
    private Text genero;
    @FXML
    private Text estadoCivil;
    
    
    Conexao conexao = null;
    private Funcionario funcionarioToView;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void imprimir(ActionEvent event) {
        //GERA UM RELATORIO DE FUNCIONÁRIOS
        System.out.println("Gerando Reports ...");
        conexao = new Conexao();
        try{
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/payrollsys/reports/reportFuncionario.jasper"), null, conexao.getConnection());
            JasperViewer.viewReport(print, false);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        conexao.fecharConexao();  
    }

    @FXML
    private void fechar(ActionEvent event) {
        Actions.closeWindow(event);
    }
    
    //Setta os campos da tela com os dados vindo da tela que o chamou
    public void setUI(Funcionario funcionario){
        this.funcionarioToView = funcionario;
        System.out.println(funcionarioToView.getFoto());
        if(funcionarioToView.getFoto() != null){
            imgFoto.setImage(new Image(funcionarioToView.getFoto()));
        }
        else {
            funcionarioToView.setFoto("/payrollsys/images/profile.jpg");
            imgFoto.setImage(new Image(funcionarioToView.getFoto()));
        }
        
        departamento.setText(funcionarioToView.getDepartamento().getNome());
        cargo.setText(funcionarioToView.getCargo().getNome());
        turno.setText(funcionarioToView.getTurno().getNome());
        dataCadastro.setText(funcionarioToView.getDataContrato().toString());
        salarioBase.setText(String.valueOf(funcionarioToView.getSalarioBase()));
        nomeFuncionario.setText(funcionarioToView.getNome());
        codigoFuncionario.setText(funcionarioToView.getCodigo());
        endereco.setText(funcionarioToView.getEndereco());
        telefone.setText(funcionarioToView.getTelefone());
        //email.setText();
        dataNascimento.setText(funcionarioToView.getDataNascimento().toString());
        genero.setText(funcionarioToView.getGenero());
        estadoCivil.setText(funcionarioToView.getEstadoCivil());
    }
}

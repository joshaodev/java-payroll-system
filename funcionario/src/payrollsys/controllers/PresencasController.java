/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import payrollsys.db.Conexao;
import payrollsys.db.PresencaOp;
import payrollsys.models.Funcionario;
import payrollsys.models.Presenca;



public class PresencasController implements Initializable {
    @FXML
    private Text nomeFuncionario;
    @FXML
    private Text horasTrabalhadasMes;
    @FXML
    private TableView<Presenca> tabelaPresenca;
    @FXML
    private TableColumn<Presenca, Date> colDia;
    @FXML
    private TableColumn<Presenca, Integer> colHoras;

    
    /* Data collections */
    private ObservableList<Presenca> listaPresencas = FXCollections.observableArrayList();
    
    /* Selected */
    private Funcionario searchFuncionario = null;
    /* Conexao Banco Dados */
    private Conexao conexao;
    /* VARs */
    int idFuncionario = 0;
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void voltar(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    
    
    /* Metodos Auxiares */
    
    // Settar as colunas a tabela.
    private void setTableColumns(){
       colDia.setCellValueFactory(new PropertyValueFactory<Presenca, Date>("dataPresenca"));
       colHoras.setCellValueFactory(new PropertyValueFactory<Presenca, Integer>("horasTrabalhadas"));  
    }
    
    // Preeencher a tabela com os dados das presencas
    private void setDataInTable(ObservableList<Presenca> listaPresencas){
        conexao = new Conexao();
        listaPresencas.clear();
        System.out.println(LocalDate.now().getMonthValue());
        System.out.println(idFuncionario);
        PresencaOp.SelecionarPresencasFuncionario(conexao.getConnection(), LocalDate.now().getMonthValue(), idFuncionario, listaPresencas);
        System.out.println("funfou");
        if(listaPresencas.size() > 0){
            System.out.println("Turno do primeiro funcionario: "+listaPresencas.get(0).getTurnoFuncionario());
            tabelaPresenca.setItems(listaPresencas);  
        }
        conexao.fecharConexao();
    }
    
    public void setUI(Funcionario funcionario){
        searchFuncionario = funcionario;
        idFuncionario = searchFuncionario.getIdFuncionario();
        nomeFuncionario.setText(searchFuncionario.getNome());
        
        conexao = new Conexao();
        int totalHorasTrabalhadaMes = PresencaOp.SelecionarTotalHorasTrabalhadas(conexao.getConnection(), searchFuncionario.getIdFuncionario(), LocalDate.now().getMonthValue());
        conexao.fecharConexao();
        
        horasTrabalhadasMes.setText(String.valueOf(totalHorasTrabalhadaMes));
        
        setDataInTable(listaPresencas);
        setTableColumns();
    }
}


//Nota: primeiro inicializa () depois chama o metodo.
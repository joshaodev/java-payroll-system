/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import payrollsys.db.FuncionarioOp;
import payrollsys.db.HorasExtrasOp;
import payrollsys.db.PagamentoOp;
import payrollsys.db.PresencaOp;
import payrollsys.db.SaidaOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Funcionario;
import payrollsys.models.HorasExtras;
import payrollsys.models.Pagamento;
import payrollsys.models.Presenca;
import payrollsys.models.Saida;
import payrollsys.utils.Actions;


public class FolhaSalarioController implements Initializable {
    @FXML
    private ImageView imgFoto;
    @FXML
    private JFXTextField textFieldCodigoFuncionario;
    @FXML
    private Text labelNomeFuncionario;
    @FXML
    private Text labelCargoFuncionario;
    @FXML
    private Text labelDepartamentoFuncionario;
    @FXML
    private Text labelTurnoFuncionario;
    @FXML
    private Text labelHorasTrabalhadaFuncionario;
    @FXML
    private JFXButton btncalcularSalario;
    @FXML
    private JFXButton btnPostarFolhaDeSalario;
    @FXML
    private JFXTextField textFieldSalarioBase;
    @FXML
    private JFXTextField textFieldHorasExtrasAcomulada;
    @FXML
    private JFXTextField textFieldSalarioBruto;
    @FXML
    private JFXTextField textFieldSalarioFinal;
    @FXML
    private JFXTextField textFieldSubcidioNatal;
    @FXML
    private JFXTextField textFieldSubcidioFerias;
    @FXML
    private JFXTextField textFieldDeducoesFaltas;
    @FXML
    private JFXTextField textFieldIRT;
    @FXML
    private JFXTextField textFieldSegurancaSocial;
    @FXML
    private JFXDatePicker dataPickerInicioPagamento;
    @FXML
    private JFXDatePicker dataPickerFimPagamento;
    @FXML
    private JFXButton btnPostarCancelar;
    
    /* Data collections */
    private ObservableList<Presenca> listaPresencas = FXCollections.observableArrayList();
    
    /* Selected */
    private Funcionario searchFuncionario = null;
    private Presenca selectedPresenca = null;
    private Pagamento pagamentoFuncionario = null;
    
    /* Conexao Banco Dados */
    private Conexao conexao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        disableButtons();
    }    

    @FXML
    private void pesquisarFuncionario(ActionEvent event) {
        disableButtons();
        
        String codigoFuncionario = textFieldCodigoFuncionario.getText();
        conexao = new Conexao();
        searchFuncionario = FuncionarioOp.SelecionarFuncionario(conexao.getConnection(), codigoFuncionario);
        
        if(searchFuncionario != null){
            setFieldsFuncionario();
            //Cria uma instancia de pagamento do funcionario pesquisado.
            pagamentoFuncionario = new Pagamento(searchFuncionario);
            //Setta os dados do pagamento.
            setFieldsPagamentos();
            btncalcularSalario.setDisable(false);
            
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
    private void calcularSalario(ActionEvent event) {
        
        textFieldSalarioBruto.setText(String.valueOf(pagamentoFuncionario.getValorBruto()));
        textFieldSalarioFinal.setText(String.valueOf(pagamentoFuncionario.getValorAReceber()));

        btnPostarFolhaDeSalario.setDisable(false);
    }
    
    @FXML
    private void postarFolhaDeSalario(ActionEvent event) {
        Actions.closeWindow(event);    //FECHA A JANELA
        //GERA UM RELATORIO DA FOLHA DE PAGAMENTO
        System.out.println("Gerando Reports ...");
        conexao = new Conexao();
        try{
            Date dataActual = Date.valueOf(LocalDate.now());
            HashMap hm = new HashMap();
            hm.put("codigoFuncionario", searchFuncionario.getCodigo());
            hm.put("dataFolhaSalario", Date.valueOf(LocalDate.now()));
            
            hm.put("valorHorasExtras", pagamentoFuncionario.getHorasExtras());
            hm.put("valorSubsidioFerias", pagamentoFuncionario.getSubsidioFerias());
            hm.put("valorSubsidioNatal", pagamentoFuncionario.getSubsidioNatal());
            hm.put("salarioBruto", pagamentoFuncionario.getValorBruto());
            
            hm.put("valorDescontosFaltas", pagamentoFuncionario.getValorFaltas());
            hm.put("irt", pagamentoFuncionario.getValorIRT());
            hm.put("segurancaSocial", pagamentoFuncionario.getValorINSS());
            hm.put("salarioFinal", pagamentoFuncionario.getValorAReceber());
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/payrollsys/reports/reportFolhaSalario.jasper"), hm, conexao.getConnection());
            JasperViewer.viewReport(print, false);
            
            // INSERIR PAGAMENTO
                // VERIFICAR SE O FUNCIONARIO JÁ ESTA INSERIDO NA LISTA DE PAGAMENTO. SE NÃO, INSERIR SE SIM, IGNORAR.
            conexao = new Conexao();
            Pagamento pagamento = PagamentoOp.SelecionarPagamentoFuncionario(conexao.getConnection(), searchFuncionario, LocalDate.now().getMonthValue());
            conexao.fecharConexao();
            if(pagamento == null){
                Pagamento pagamentoToInsert = new Pagamento(searchFuncionario);
                
                pagamentoToInsert.setDataPagamento(dataActual);
                pagamentoToInsert.setValorFaltas(pagamentoToInsert.getValorFaltas());
                pagamentoToInsert.setValorINSS(pagamentoToInsert.getValorINSS());
                pagamentoToInsert.setValorIRT(pagamentoToInsert.getValorIRT());
                pagamentoToInsert.setValorDescontoTotal(pagamentoToInsert.getValorDescontoTotal());
                pagamentoToInsert.setValorBonus(pagamentoToInsert.getValorBonus());
                pagamentoToInsert.setValorAReceber(pagamentoToInsert.getValorAReceber());

                conexao = new Conexao();
                int result = PagamentoOp.InserirPagamento(conexao.getConnection(), pagamentoToInsert);
                conexao.fecharConexao();

                if (result == 1) {
                    String title = "Registro Inserido";
                    String content = "O pagamento foi adicionado a lista de salário!";
                    String header = "Resultado:";
                    DynamicViews.informationAlert(title, content, header);
                }
            }
            else {
                String title = "AVISO";
                String content = "O funcionario já tem o seu pagamento adicionado a lista!";
                String header = "Resultado:";
                DynamicViews.informationAlert(title, content, header);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        conexao.fecharConexao();
        
    }
    
    @FXML
    private void Cancelar(ActionEvent event) {
        Actions.closeWindow(event);
    }
    
    
    private void setFieldsFuncionario(){
        //SETAR DADOS DO FUNCIONARIO
        labelNomeFuncionario.setText(searchFuncionario.getNome());
        labelCargoFuncionario.setText(searchFuncionario.getCargo().getNome());
        labelDepartamentoFuncionario.setText(searchFuncionario.getDepartamento().getNome());
        labelTurnoFuncionario.setText(searchFuncionario.getTurno().getNome());
        
        if(searchFuncionario.getFoto() != null){
            imgFoto.setImage(new Image(searchFuncionario.getFoto()));
        }
        else {
            searchFuncionario.setFoto("/payrollsys/images/profile.jpg");
            imgFoto.setImage(new Image(searchFuncionario.getFoto()));
        }
        
        //Horas Trabalhadas
        conexao = new Conexao();
        Presenca totalPresenca = PresencaOp.SelecionarTotalHorasTrabalhadas(conexao.getConnection(), searchFuncionario.getIdFuncionario(), LocalDate.now().getMonthValue());
        labelHorasTrabalhadaFuncionario.setText(String.valueOf(totalPresenca.getHorasTrabalhadas()));
        //horasTrabalhadas = totalPresenca.getHorasTrabalhadas();
        conexao.fecharConexao();    
    }
    
    private void setFieldsPagamentos(){
        //SETAR DADOS DO Pagamento
        
        //Salarios e bonus
        textFieldSalarioBase.setText(String.valueOf(pagamentoFuncionario.getFuncionario().getSalarioBase()));
        textFieldHorasExtrasAcomulada.setText(String.valueOf(pagamentoFuncionario.getHorasExtras()));
        textFieldSubcidioFerias.setText(String.valueOf(pagamentoFuncionario.getSubsidioFerias()));
        textFieldSubcidioNatal.setText(String.valueOf(pagamentoFuncionario.getSubsidioNatal()));
        
        //Descontos 
        textFieldDeducoesFaltas.setText(String.valueOf(pagamentoFuncionario.getValorFaltas()));
        textFieldIRT.setText(String.valueOf(pagamentoFuncionario.getValorIRT()));
        textFieldSegurancaSocial.setText(String.valueOf(pagamentoFuncionario.getValorINSS()));
    }
    
    private void disableButtons(){
        btncalcularSalario.setDisable(true);
        btnPostarFolhaDeSalario.setDisable(true);
    }

    
}



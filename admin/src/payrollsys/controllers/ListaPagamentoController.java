

package payrollsys.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import payrollsys.db.Conexao;
import payrollsys.db.PagamentoOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Funcionario;
import payrollsys.models.Pagamento;


public class ListaPagamentoController implements Initializable {
    /* GUI Components */
    @FXML
    private JFXDatePicker dataPickerDataPagamento;
    @FXML
    private TableView<Pagamento> tabelaPagamento;
    @FXML
    private TableColumn<Pagamento, String> colCodigo;
    @FXML
    private TableColumn<Pagamento, String> colNome;
    @FXML
    private TableColumn<Pagamento, String> colCargo;
    @FXML
    private TableColumn<Pagamento, Double> colSalario;
    @FXML
    private TableColumn<Pagamento, Double> colFaltas;
    @FXML
    private TableColumn<Pagamento, Double> colValorINSS;
    @FXML
    private TableColumn<Pagamento, Double> colValorIRT;
    @FXML
    private TableColumn<Pagamento, Double> colTotalDesconto;
    @FXML
    private TableColumn<Pagamento, Double> colBonus;
    @FXML
    private TableColumn<Pagamento, Double> colValorAReceber;
    @FXML
    private JFXButton btnEliminar;

    
    /* Data collections */
    private ObservableList<Funcionario> listaFuncionarios = FXCollections.observableArrayList();
    private ObservableList<Pagamento> listaPagamento = FXCollections.observableArrayList();
    
    /* Selected */
    private Pagamento selectedPagamento = null;
    
    /* Conexao Banco Dados */
    private Conexao conexao = null;
    
    Date dataPagamento = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataPagamento = Date.valueOf(LocalDate.now());
        btnEliminar.setDisable(true);
      
        setTableColumns();
        setDataInTable(listaPagamento);

        manageTableSelectionMode();
    }    

    @FXML
    private void searchPagamento(ActionEvent event) {
        dataPagamento = Date.valueOf(dataPickerDataPagamento.getValue());
        setDataInTable(listaPagamento);
    }
    
    @FXML
    private void gerarReport(ActionEvent event) {
        //GERA UM RELATORIO DA Lista DE PAGAMENTO
        System.out.println("Gerando Reports ...");
        conexao = new Conexao();
        try{
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/payrollsys/reports/reportListaPagamentos.jasper"), null, conexao.getConnection());
            JasperViewer.viewReport(print, false);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        conexao.fecharConexao();
    }

    @FXML
    private void actualizarTabela(ActionEvent event) {
        setDataInTable(listaPagamento);
        btnEliminar.setDisable(true);
    }

    @FXML
    private void eliminar(ActionEvent event) {
        boolean confirmationResult = DynamicViews.confirmationAlert("Eliminar Registro", "Eliminar Pagamento!", "Tens a certeza disso?");
        if(confirmationResult){
            conexao = new Conexao();
            int operationResult = PagamentoOp.EliminarPagamento(conexao.getConnection(), selectedPagamento);
            conexao.fecharConexao();
            if (operationResult == 1) {
                String title = "Registro Eliminado";
                String content = "O Registro foi eliminado com exito!";
                String header = "Resultado:";

                DynamicViews.informationAlert(title, content, header);
            }  
        }
    }

    
    // METODOS AUX
    private void setTableColumns(){
       colCodigo.setCellValueFactory(new PropertyValueFactory<Pagamento, String>("codigo"));
       colNome.setCellValueFactory(new PropertyValueFactory<Pagamento, String>("nome"));
       colCargo.setCellValueFactory(new PropertyValueFactory<Pagamento, String>("cargo"));
       
       colSalario.setCellValueFactory(new PropertyValueFactory<Pagamento, Double>("salario"));
       colFaltas.setCellValueFactory(new PropertyValueFactory<Pagamento, Double>("faltas"));
       colValorINSS.setCellValueFactory(new PropertyValueFactory<Pagamento, Double>("valorINSS"));
       colValorIRT.setCellValueFactory(new PropertyValueFactory<Pagamento, Double>("valorIRT"));
       colTotalDesconto.setCellValueFactory(new PropertyValueFactory<Pagamento, Double>("valorDescontoTotal"));
       colBonus.setCellValueFactory(new PropertyValueFactory<Pagamento, Double>("valorBonus"));
       colValorAReceber.setCellValueFactory(new PropertyValueFactory<Pagamento, Double>("valorAReceber"));
    }
    
    private void setDataInTable(ObservableList<Pagamento> listaFolhaPagamento){
        conexao = new Conexao();
        listaPagamento.clear();
        PagamentoOp.SelecionarPagamentos(conexao.getConnection(), dataPagamento.toLocalDate().getMonthValue(), dataPagamento.toLocalDate().getYear(), listaPagamento);
        //System.out.println(listaFuncionarios.get(0));
        if(listaPagamento.size() > 0){
            tabelaPagamento.setItems(listaPagamento);
        }
        conexao.fecharConexao();
    }
    
    public void manageTableSelectionMode(){
        tabelaPagamento.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Pagamento>() {
                @Override
                public void changed(ObservableValue<? extends Pagamento> observable, Pagamento oldValue, Pagamento selectedValue) {
                    if(selectedValue != null){
                        System.out.println(selectedValue.getCodigo());
                        selectedPagamento = selectedValue;
                        btnEliminar.setDisable(false);
                    }
                }
            }
        );
    }
    
}

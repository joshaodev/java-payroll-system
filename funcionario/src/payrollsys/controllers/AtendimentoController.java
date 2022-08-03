/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import payrollsys.db.Conexao;
import payrollsys.db.FuncionarioOp;
import payrollsys.db.PresencaOp;
import payrollsys.db.TurnoOp;
import payrollsys.utils.DynamicViews;
import payrollsys.models.Funcionario;
import payrollsys.models.Presenca;
import payrollsys.models.Turno;

/**
 * FXML Controller class
 *
 * @author joshtag096
 */
public class AtendimentoController implements Initializable {
    /* GUI components */
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
    private JFXRadioButton rbEntrada;
    @FXML
    private JFXRadioButton rbSaida;
    @FXML
    private ToggleGroup groupCheckInOut;
    @FXML
    private JFXButton btnMarcarPresenca;
    @FXML
    private JFXButton btnVerFaltas;
    @FXML
    private TableView<Presenca> tabelaPresencas;
    @FXML
    private TableColumn<Presenca, String> colCodigoFuncionario;
    @FXML
    private TableColumn<Presenca, String> colNomeFuncionario;
    @FXML
    private TableColumn<Presenca, String> colTurnoFuncionario;
    @FXML
    private TableColumn<Presenca, Time> colMarcacaoEntrada;
    @FXML
    private TableColumn<Presenca, Time> colMarcacaoSaida;
    @FXML
    private Text labelDataPresença;
    @FXML
    private ImageView imgFuncionario;

    /* Data collections */
    private ObservableList<Presenca> listaPresencas = FXCollections.observableArrayList();
    ObservableList<Turno> listaTurnos = FXCollections.observableArrayList();
    /* Selected */
    private Presenca selectedPresenca = null;
    private static Funcionario searchFuncionario = null;
    /* Conexao Banco Dados */
    private Conexao conexao;
    /* VARs */
    int horasTrabalhadas = 0;
    LocalTime horaActual;
    
       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualizaHoraDoSistema();
        btnMarcarPresenca.setDisable(true);
        
        labelDataPresença.setText(LocalDate.now().toString());
        
        setTableColumns();
        setDataInTable(listaPresencas);
        
        btnVerFaltas.setDisable(true);
    }    

    @FXML
    private void pesquisarFuncionario(ActionEvent event) {
        actualizaHoraDoSistema();
        
        String codigoFuncionario = textFieldCodigoFuncionario.getText();
        conexao = new Conexao();
        searchFuncionario = FuncionarioOp.SelecionarFuncionario(conexao.getConnection(), codigoFuncionario);
        //PresencaOp.SelecionarPresencaData(conexao.getConnection(), Date.valueOf(LocalDate.now()));
        if(searchFuncionario != null){
            btnVerFaltas.setDisable(false);
            System.out.println(searchFuncionario);
            labelNomeFuncionario.setText(searchFuncionario.getNome());
            labelCargoFuncionario.setText(searchFuncionario.getCargo().getNome());
            labelDepartamentoFuncionario.setText(searchFuncionario.getDepartamento().getNome());
            labelTurnoFuncionario.setText(searchFuncionario.getTurno().getNome());
            System.out.println(searchFuncionario.getFoto());
            if(searchFuncionario.getFoto() != null){
                imgFuncionario.setImage(new Image(searchFuncionario.getFoto()));
            }
            else {
                searchFuncionario.setFoto("/payrollsys/images/profile.jpg");
                imgFuncionario.setImage(new Image(searchFuncionario.getFoto()));
            }
            
            //Pesquisa os turnos e coloca na lista de turnos.
            listaTurnos.clear();
            TurnoOp.SelecionarTurnos(conexao.getConnection(), listaTurnos);
            conexao.fecharConexao();
            //verifica qual turno equivale a hora actual
            //percorre a lista para encontrar o turno que equivale a hora actual
            int index = -1;
            for(int i = 0; i < listaTurnos.size(); i++){
                if( listaTurnos.get(i).getEntrada().toLocalTime().getHour() <= LocalTime.now().getHour() && listaTurnos.get(i).getSaida().toLocalTime().getHour() >= LocalTime.now().getHour() ){
                    index = i;
                    System.out.println(index);
                }
            }
            if (index == -1){
                System.out.println("Sistema Fora de Serviço");
            }        
            else{
                String nomeTurnoActual = listaTurnos.get(index).getNome();
                int entradaTurnoActual = listaTurnos.get(index).getEntrada().toLocalTime().getHour();
                int saidaTurnoActual = listaTurnos.get(index).getSaida().toLocalTime().getHour();


                //System.out.println(listaTurnos.get(index).getEntrada().toLocalTime().getHour());
                //System.out.println(listaTurnos.get(index).getSaida().toLocalTime().getHour());
                //System.out.println(listaTurnos.get(index).getNome());
                //verifica de o turno do funcionario é equivalente ao turna actual
                //boolean turno = LocalTime.now().getHour() >= //apagar
                //boolean turnoManha = (LocalTime.now().getHour() >= 7 && LocalTime.now().getHour() <= 12);//apagar
                //boolean turnoTarde = (LocalTime.now().getHour() >12 && LocalTime.now().getHour() <= 17);//apagar

                if(searchFuncionario.getTurno().getNome().equals(nomeTurnoActual)){
                    /* SE O FUNCIONARIO REALMENTE PERTENCER AO TURNO ACTUAL, VERIFICA SE O FUNCIONARIO JÁ TEM A PRESENÇA MARCADA. */
                    estaMarcadaPresenca();
                }
                else {
                    // CASO O FUNCIONÁRIO NÃO PERTENÇA AO HORARIO
                    btnMarcarPresenca.setDisable(true);
                    String title = "Fora do horario";
                    String content = "O funcionário em questão, não pertence a este horario!";
                    String header = "Resultado:";
                    DynamicViews.MessageAlert(title, content, header);
                } 
            }      
        }
        else{
            System.out.println("Funcionario não existe!!!");
            String title = "Pesquisa Indesejada";
            String content = "O campo esta vazio ou o funcionario não existe!";
            String header = "Resultado:";
            DynamicViews.MessageAlert(title, content, header);
            //textFieldCodigoFuncionario.setText("");
        }
        conexao.fecharConexao();     
    }
    
    @FXML
    private void marcarPresenca(ActionEvent event) {
        
        conexao = new Conexao();        
        if(searchFuncionario != null){
            Presenca presenca = PresencaOp.SelecionarPresencaFuncionario(conexao.getConnection(), Date.valueOf(LocalDate.now()), searchFuncionario.getCodigo());
            if(rbEntrada.isSelected()){
                Presenca presencaToInsert = new Presenca();
                presencaToInsert.setDataPresenca(Date.valueOf(LocalDate.now()));
                presencaToInsert.setInicioPresenca(Time.valueOf(LocalTime.now()));
                presencaToInsert.setFimPresenca(Time.valueOf("00:00:00"));
                presencaToInsert.setHorasTrabalhadas(0);
                presencaToInsert.setFuncionarioPresenca(searchFuncionario);

                int result = PresencaOp.InserirPresenca(conexao.getConnection(), presencaToInsert);
                conexao.fecharConexao();

                if (result == 1) {
                    String title = "Registro de presença";
                    String content = "A Entrada do Funcionario foi registrada com exito!";
                    String header = "Resultado:";
                    DynamicViews.MessageAlert(title, content, header);
                    btnMarcarPresenca.setDisable(false);
                }
                setDataInTable(listaPresencas);
                setSearch();
            }
            else if(rbSaida.isSelected()){    
                conexao = new Conexao();
                if(presenca != null && presenca.getFimPresenca().toString().equals("00:00:00")){
                    Presenca presencaToUpdate = PresencaOp.SelecionarPresencaDataFuncionario(conexao.getConnection(), Date.valueOf(LocalDate.now()), searchFuncionario.getIdFuncionario());
                    presencaToUpdate.setFimPresenca(Time.valueOf(LocalTime.now()));
                    System.out.println(""+Time.valueOf(LocalTime.now()));
                    horasTrabalhadas = presencaToUpdate.getHorasTrabalhadas();
                    System.out.println(horasTrabalhadas);
                    presencaToUpdate.setHorasTrabalhadas(horasTrabalhadas);

                    int result = PresencaOp.ActualizarPresenca(conexao.getConnection(), presencaToUpdate);
                    conexao.fecharConexao();

                    if (result == 1) {
                        String title = "Registro de presença";
                        String content = "A Saida do Funcionario foi registrada com exito!";
                        String header = "Resultado:";
                        DynamicViews.MessageAlert(title, content, header);
                    }
                    setDataInTable(listaPresencas);
                    setSearch();
                }   
            }  
        }
    }
    
    private void setSearch(){
        textFieldCodigoFuncionario.setText("");     //limpa o campo de codigo
        searchFuncionario = null;                   //seta o funcioario
    }
    
    //Verificar se o funcionario já marcou a presenca.
    private void estaMarcadaPresenca(){
        conexao = new Conexao();
        Presenca presenca = PresencaOp.SelecionarPresencaFuncionario(conexao.getConnection(), Date.valueOf(LocalDate.now()), searchFuncionario.getCodigo());
        conexao.fecharConexao();
        if(rbEntrada.isSelected()){
            btnMarcarPresenca.setDisable(false);
            if(presenca != null){
                String title = "Registro de presença";
                String content = "O funcionário pesquisado já tem a entrada marcada no dia de hoje!";
                String header = "Resultado:";
                DynamicViews.MessageAlert(title, content, header);
                btnMarcarPresenca.setDisable(true);
            }
        }
        else if(rbSaida.isSelected()){
            btnMarcarPresenca.setDisable(false);
            if(presenca == null){
                String title = "Registro de presença";
                String content = "O funcionário pesquisado não tem a entrada marcada, impossivel marcar saida!";
                String header = "Resultado:";
                DynamicViews.MessageAlert(title, content, header);
                btnMarcarPresenca.setDisable(true);
            }
            else if(presenca != null && !presenca.getFimPresenca().toString().equals("00:00:00")){
                String title = "Registro de presença";
                String content = "O funcionário pesquisado já tem a saida marcada no dia de hoje!";
                String header = "Resultado:";
                DynamicViews.MessageAlert(title, content, header);
                btnMarcarPresenca.setDisable(true);
            }
        }
    }

    @FXML
    private void verFaltas(ActionEvent event) {
        try {
            String path = "/payrollsys/ui/presencas.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource(path).openStream());
            
            PresencasController controller = (PresencasController) loader.getController();
            controller.setUI(searchFuncionario);
            
            Stage stage = new Stage();
            stage.setTitle("Ver Presenças");
            stage.setScene(new Scene(root));
            stage.initModality (Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Falha ao carregar a tela. verifica o caminho!");
        }
    }
    
    /* Metodos Auxiares */
    
    // Settar as colunas a tabela.
    private void setTableColumns(){
       colCodigoFuncionario.setCellValueFactory(new PropertyValueFactory<Presenca, String>("codigoFuncionario"));
       colNomeFuncionario.setCellValueFactory(new PropertyValueFactory<Presenca, String>("nomeFuncionario"));
       colTurnoFuncionario.setCellValueFactory(new PropertyValueFactory<Presenca, String>("turnoFuncionario"));
       colMarcacaoEntrada.setCellValueFactory(new PropertyValueFactory<Presenca, Time>("inicioPresenca"));
       colMarcacaoSaida.setCellValueFactory(new PropertyValueFactory<Presenca, Time>("fimPresenca"));
      
    }
    
    // Preeencher a tabela com os dados das presencas
    private void setDataInTable(ObservableList<Presenca> listaPresencas){
        conexao = new Conexao();
        listaPresencas.clear();
        PresencaOp.SelecionarPresencasData(conexao.getConnection(), Date.valueOf(LocalDate.now()), listaPresencas);
        if(listaPresencas.size() > 0){
            //System.out.println("Turno do primeiro funcionario: "+listaPresencas.get(0).getTurnoFuncionario());
            tabelaPresencas.setItems(listaPresencas);  
        }
        conexao.fecharConexao();
    }
    
    // capturar a hora actual e modificar os radios buttons
    private void actualizaHoraDoSistema(){
        /* Actualiza a hora do sistema. */
        horaActual = LocalTime.now();
        manageRadios();
    }
    
    // Modificar a seleção dos radios buttons de acordo o horario actual.
    private void manageRadios(){
        conexao = new Conexao();
        //Pesquisa os turnos e coloca na lista de turnos.
        listaTurnos.clear();
        TurnoOp.SelecionarTurnos(conexao.getConnection(), listaTurnos);
        conexao.fecharConexao();
        //verifica qual turno equivale a hora actual
        //percorre a lista para encontrar o turno que equivale a hora actual
        int index = -1;
        for(int i = 0; i < listaTurnos.size(); i++){
            if( listaTurnos.get(i).getEntrada().toLocalTime().getHour() <= LocalTime.now().getHour() && listaTurnos.get(i).getSaida().toLocalTime().getHour() >= LocalTime.now().getHour() ){
                index = i;
                System.out.println(index);
            }
        }
        if (index == -1){
            System.out.println("Não existe horario equivalente ao actual");
        } 
        else {
            String nomeTurnoActual = listaTurnos.get(index).getNome();
            int entradaTurnoActual = listaTurnos.get(index).getEntrada().toLocalTime().getHour();
            int saidaTurnoActual = listaTurnos.get(index).getSaida().toLocalTime().getHour();

            System.out.println(listaTurnos.get(index).getEntrada().toLocalTime().getHour());
            System.out.println(listaTurnos.get(index).getSaida().toLocalTime().getHour());
            System.out.println(listaTurnos.get(index).getNome());
            //pesquisa os turnos
            //se a hora actual equivale a de algun turno da lista (entrada e saida)
            if( (horaActual.getHour() >= entradaTurnoActual && horaActual.getHour() < saidaTurnoActual)){
                rbEntrada.setSelected(true);
                rbEntrada.setDisable(false);

                rbSaida.setSelected(false);
                rbSaida.setDisable(true);
            }
            else if(horaActual.getHour() == saidaTurnoActual){
                rbEntrada.setSelected(false);
                rbEntrada.setDisable(true);

                rbSaida.setSelected(true);
                rbSaida.setDisable(false);
            }
            else {
                rbEntrada.setSelected(false);
                rbEntrada.setDisable(true);

                rbSaida.setSelected(false);
                rbSaida.setDisable(true);
            }
        }
        
    }
    
    
    
    
}

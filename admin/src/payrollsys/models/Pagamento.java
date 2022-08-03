/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.models;

import java.sql.Date;
import java.time.LocalDate;
import payrollsys.db.Conexao;
import payrollsys.db.HorasExtrasOp;
import payrollsys.db.PresencaOp;
import payrollsys.db.SaidaOp;


public class Pagamento {
    private Funcionario funcionario;
    private int idPagamento;
    private Date dataPagamento;
    private double valorFaltas;
    private double valorINSS;
    private double valorIRT;
    private double valorDescontoTotal;
    private double valorBonus;
    private double valorBruto;
    private double valorAReceber;
    /* auxiliares para a tabela */
    private String codigo;
    private String nome;
    private String cargo;
    private double salario;
    private double subsidioFerias;
    private double subsidioNatal;
    private double horasTrabalhadas;
    private double horasExtras;
    

    public Pagamento() {
 
    }
    
    public Pagamento(Funcionario funcionario) {
        this.funcionario = funcionario;
        setPagamento();
    }
        
    public Pagamento(Funcionario funcionario, int idPagamento, Date dataPagamento, double faltas, double valorINSS, double valorIRT, double valorDescontoTotal, double valorBonus, double valorAReceber) {
        this.funcionario = funcionario;
        this.idPagamento = idPagamento;
        this.dataPagamento = dataPagamento;
        this.valorFaltas = faltas;
        this.valorINSS = valorINSS;
        this.valorIRT = valorIRT;
        this.valorDescontoTotal = valorDescontoTotal;
        this.valorBonus = valorBonus;
        this.valorAReceber = valorAReceber;
        
        setPagamento();
    }
    
    /* GETTERS E SETTERS */
    //funcionario
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    //IdPagamento
    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }
    
    //Data Pagamento
    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    //Nome Funcionario
    public String getNome() {
        return funcionario.getNome();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    //Codigo funcionario
    public String getCodigo() {
        return funcionario.getCodigo();
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    //Cargo Funcionario
    public String getCargo() {
        return funcionario.getCargo().getNome();
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    //Salario Funcionario
    public double getSalario() {
        return funcionario.getSalarioBase();
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    
    // Horas Extras

    public double getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(Funcionario funcionario) {
        //Pegar as Horas Extras acomuladas
        Conexao conexao = new Conexao();
        HorasExtras totalHorasExtras = HorasExtrasOp.SelecionarTotalHorasExtras(conexao.getConnection(), funcionario.getIdFuncionario(), LocalDate.now().getMonthValue());
        this.horasExtras = totalHorasExtras.getPagamentoHorasExtras();
    }
    
    
    //Faltas funcionario
    public double getValorFaltas() {
        return valorFaltas;
    }

    public void setValorFaltas(double valorFaltas) {
        this.valorFaltas = valorFaltas;
    }

    //INSS Funcionario
    public double getValorINSS() {
        return valorINSS;
    }

    public void setValorINSS(double valorINSS) {
        this.valorINSS = valorINSS;
    }

    //IRT Funcionario
    public double getValorIRT() {
        return valorIRT;
    }

    public void setValorIRT(double valorIRT) {
        this.valorIRT = valorIRT;
    }

    //TOTAL DESCONTO Funcionario
    public double getValorDescontoTotal() {
        return valorDescontoTotal;
    }

    public void setValorDescontoTotal(double valorDescontoTotal) {
        this.valorDescontoTotal = valorDescontoTotal;
    }

    //TOTAL BONUS Funcionario
    public double getValorBonus() {
        return valorBonus;
    }

    public void setValorBonus(double valorBonus) {
        this.valorBonus = valorBonus;
    }

    // PAGAMENTO BRUTO

    public double getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(double valorBruto) {
        this.valorBruto = valorBruto;
    }
    
    
    //PAGAMENTO FINAL funcioario
    public double getValorAReceber() {
        return valorAReceber;
    }

    public void setValorAReceber(double valorAReceber) {
        this.valorAReceber = valorAReceber;
    }

    //SUBSIDIO DE FERIAS Funcionario
    public double getSubsidioFerias() {
        return subsidioFerias;
    }

    public void setSubsidioFerias() {
        //CALCULO DO SUBSIDIO DE FERIAS
        Conexao conexao = new Conexao();
        Saida saidaFeria = SaidaOp.SelecionarSaidaFeria(conexao.getConnection(), funcionario.getIdFuncionario());
        conexao.fecharConexao();
        if(saidaFeria != null){
            this.subsidioFerias = salario;
        }
        else {
            this.subsidioFerias = 0;
        }
    }

    //SUBSIDIO DE NATAL Funcionario
    public double getSubsidioNatal() {
        return subsidioNatal;
    }

    public void setSubsidioNatal() {
        //CALCULO DO SUBSIDIO DE NATAL.
        if(LocalDate.now().getMonthValue() == 12){
            this.subsidioNatal = (salario * 0.5 );
        }else{
            this.subsidioNatal = 0;
        }
    }

    @Override
    public String toString() {
        return "Pagamento{" + "dataPagamento=" + dataPagamento + ", valorAReceber=" + valorAReceber + '}';
    }
    
    /* Obter valores aparte */
    private void setPagamento(){
        //Horas extras
        this.setHorasExtras(funcionario);
        
        // Definir os bonus e descontos.
        this.setSubsidioFerias();
        this.setSubsidioNatal();
        
        Conexao conexao = new Conexao();
        // DESCONTOS
        Presenca totalPresenca = PresencaOp.SelecionarTotalHorasTrabalhadas(conexao.getConnection(), funcionario.getIdFuncionario(), LocalDate.now().getMonthValue());
        horasTrabalhadas = totalPresenca.getHorasTrabalhadas();
        valorFaltas = (160 - horasTrabalhadas) * (funcionario.getSalarioBase()/160);
        
        //CALCULO DO SUBSIDIO DE NATAL.
        if(LocalDate.now().getMonthValue() == 12){
            subsidioNatal = (funcionario.getSalarioBase() * 0.5 );
        }else{
            subsidioNatal = 0;
        }
        
        //CALCULO DO SUBSIDIO DE FERIAS
        Saida saidaFeria = SaidaOp.SelecionarSaidaFeria(conexao.getConnection(), funcionario.getIdFuncionario());
        if(saidaFeria != null){
            subsidioFerias = funcionario.getSalarioBase() * 0.5;
        }
        else {
            subsidioFerias = 0;
        }
        
        //INSS
        valorINSS = funcionario.getSalarioBase() * 0.03; 
        
        //IRT
        double materiaColetavel = funcionario.getSalarioBase() - valorINSS;
        if(funcionario.getSalarioBase() < 75000){
            valorIRT = 0;
        }
        else if(funcionario.getSalarioBase() > 70001 && funcionario.getSalarioBase() < 100000){
            valorIRT = 3000 + (materiaColetavel - 70000) * 0.1;
        }
        else if(funcionario.getSalarioBase() >100001 && funcionario.getSalarioBase() < 150000){
            valorIRT = 6000 + (materiaColetavel - 100000) * 0.13;
        }
        else if(funcionario.getSalarioBase() > 150001 && funcionario.getSalarioBase() < 200000){
            valorIRT = 12500 + (materiaColetavel - 150000) * 0.16;
        }
        
        //Total de descontos
        valorDescontoTotal = valorFaltas + valorINSS + valorIRT;
        
        // BONUS
        HorasExtras totalHorasExtras = HorasExtrasOp.SelecionarTotalHorasExtras(conexao.getConnection(), funcionario.getIdFuncionario(), LocalDate.now().getMonthValue());
        horasExtras = totalHorasExtras.getPagamentoHorasExtras();
        valorBonus = horasExtras + subsidioNatal;
        
        // PAGAMENTO BRUTO
        valorBruto = ( funcionario.getSalarioBase() + this.horasExtras + this.subsidioFerias + this.subsidioNatal);
        
        // PAGAMENTO FINAL
        valorAReceber = (funcionario.getSalarioBase() + valorBonus + subsidioFerias) - valorDescontoTotal;
        
        conexao.fecharConexao();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.models;

import java.sql.Date;

/**
 *
 * @author joshtag096
 */
public class Saida {
    private int idSaida;
    private String tipoSaida;
    private Date dataInicioSaida;
    private Date dataFimSaida;
    private String motivoSaida;
    private Funcionario funcionarioSaida;
    
    //Atributos para a tabela...
    private int diasSaida;
    private String codigoFuncionario;
    private String nomeFuncionario;

    public Saida() {
    }

    public Saida(int idSaida, String tipoSaida, Date dataInicioSaida, Date dataFimSaida, String motivoSaida, Funcionario funcionarioSaida) {
        this.idSaida = idSaida;
        this.tipoSaida = tipoSaida;
        this.dataInicioSaida = dataInicioSaida;
        this.dataFimSaida = dataFimSaida;
        this.motivoSaida = motivoSaida;
        this.funcionarioSaida = funcionarioSaida;
    }

    public Saida(String tipoSaida, Date dataInicioSaida, Date dataFimSaida, String motivoSaida, Funcionario funcionarioSaida) {
        this.tipoSaida = tipoSaida;
        this.dataInicioSaida = dataInicioSaida;
        this.dataFimSaida = dataFimSaida;
        this.motivoSaida = motivoSaida;
        this.funcionarioSaida = funcionarioSaida;
    }
    
    /* ======================= */
    /*    GETTERS e SETTERS    */
    /* ======================= */
    public int getIdSaida() {
        return idSaida;
    }

    public void setIdSaida(int idSaida) {
        this.idSaida = idSaida;
    }

    public String getTipoSaida() {
        return tipoSaida;
    }

    public void setTipoSaida(String tipoSaida) {
        this.tipoSaida = tipoSaida;
    }

    public Date getDataInicioSaida() {
        return dataInicioSaida;
    }

    public void setDataInicioSaida(Date dataInicioSaida) {
        this.dataInicioSaida = dataInicioSaida;
    }

    public Date getDataFimSaida() {
        return dataFimSaida;
    }

    public void setDataFimSaida(Date dataFimSaida) {
        this.dataFimSaida = dataFimSaida;
    }

    public String getMotivoSaida() {
        return motivoSaida;
    }

    public void setMotivoSaida(String motivoSaida) {
        this.motivoSaida = motivoSaida;
    }

    public Funcionario getFuncionarioSaida() {
        return funcionarioSaida;
    }

    public void setFuncionarioSaida(Funcionario funcionarioSaida) {
        this.funcionarioSaida = funcionarioSaida;
    }

    public int getDiasSaida() {
        diasSaida = dataFimSaida.toLocalDate().getDayOfYear() - dataInicioSaida.toLocalDate().getDayOfYear() ;
        return diasSaida;
    }

    public void setDiasSaida(int diasSaida) {
        this.diasSaida = diasSaida;
    }

    public String getCodigoFuncionario() {
        return this.funcionarioSaida.getCodigo();
    }

    public void setCodigoFuncionario(String codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public String getNomeFuncionario() {
        return this.funcionarioSaida.getNome();
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    
    
    @Override
    public String toString() {
        return tipoSaida;
    }
    
}

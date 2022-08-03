/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.models;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author joshtag096
 */
public class HorasExtras {
    private int idHorasExtras;
    private Date dataHorasExtras;
    private Time entradaHorasExtras;
    private Time saidaHorasExtras;
    private double pagamentoHorasExtras;
    private int horasExtrasFeita;
    private Funcionario funcionario;

    public HorasExtras() {
    }

    public HorasExtras(int idHorasExtras, Date dataHorasExtras, Time entradaHorasExtras, Time saidaHorasExtras, double pagamentoHorasExtras, int horasExtrasFeita, Funcionario funcionario) {
        this.idHorasExtras = idHorasExtras;
        this.dataHorasExtras = dataHorasExtras;
        this.entradaHorasExtras = entradaHorasExtras;
        this.saidaHorasExtras = saidaHorasExtras;
        this.pagamentoHorasExtras = pagamentoHorasExtras;
        this.horasExtrasFeita = horasExtrasFeita;
        this.funcionario = funcionario;
    }

    public HorasExtras(Date dataHorasExtras, Time entradaHorasExtras, Time saidaHorasExtras, double pagamentoHorasExtras, int horasExtrasFeita, Funcionario funcionario) {
        this.dataHorasExtras = dataHorasExtras;
        this.entradaHorasExtras = entradaHorasExtras;
        this.saidaHorasExtras = saidaHorasExtras;
        this.pagamentoHorasExtras = pagamentoHorasExtras;
        this.horasExtrasFeita = horasExtrasFeita;
        this.funcionario = funcionario;
    }

    /* GETTERS and SETTERS */
    public int getIdHorasExtras() {
        return idHorasExtras;
    }

    public void setIdHorasExtras(int idHorasExtras) {
        this.idHorasExtras = idHorasExtras;
    }

    public Date getDataHorasExtras() {
        return dataHorasExtras;
    }

    public void setDataHorasExtras(Date dataHorasExtras) {
        this.dataHorasExtras = dataHorasExtras;
    }

    public Time getEntradaHorasExtras() {
        return entradaHorasExtras;
    }

    public void setEntradaHorasExtras(Time entradaHorasExtras) {
        this.entradaHorasExtras = entradaHorasExtras;
    }

    public Time getSaidaHorasExtras() {
        return saidaHorasExtras;
    }

    public void setSaidaHorasExtras(Time saidaHorasExtras) {
        this.saidaHorasExtras = saidaHorasExtras;
    }

    public double getPagamentoHorasExtras() {
        return pagamentoHorasExtras;
    }

    public void setPagamentoHorasExtras(double pagamentoHorasExtras) {
        this.pagamentoHorasExtras = pagamentoHorasExtras;
    }

    public int getHorasExtrasFeita() {
        int minutosEntrada = entradaHorasExtras.toLocalTime().toSecondOfDay() / 60;
        int minutosSaida = saidaHorasExtras.toLocalTime().toSecondOfDay() / 60;
        
        int horasEntrada = minutosEntrada / 60;
        int horasSaida = minutosSaida / 60;
               
        int horas = horasSaida - horasEntrada;
        if(horas > 0) {
            return horas;
        }
        return 0;
    }

    public void setHorasExtrasFeita(int horasExtrasFeita) {
        this.horasExtrasFeita = horasExtrasFeita;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    
    @Override
    public String toString() {
        return this.dataHorasExtras.toString();
    }
    
       
}

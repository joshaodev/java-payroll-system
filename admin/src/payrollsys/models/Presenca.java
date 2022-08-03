/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.models;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

/**
 *
 * @author joshtag096
 */
public class Presenca {
    private int idPresenca;
    private Date dataPresenca;
    private Time inicioPresenca;
    private Time fimPresenca;
    private int horasTrabalhadas;
    private Funcionario funcionarioPresenca;
    
    // Atributo para mostrar n a tabela
    private int horasTrabalhadasToShow;

    public Presenca() {
    }

    public Presenca(int idPresenca, Date dataPresenca, Time inicioPresenca, Time fimPresenca, int horasTrabalhadas, Funcionario funcionarioPresenca) {
        this.idPresenca = idPresenca;
        this.dataPresenca = dataPresenca;
        this.inicioPresenca = inicioPresenca;
        this.fimPresenca = fimPresenca;
        this.horasTrabalhadas = horasTrabalhadas;
        this.funcionarioPresenca = funcionarioPresenca;
    }

    public Presenca(Date dataPresenca, Time inicioPresenca, Time fimPresenca, int horasTrabalhadas, Funcionario funcionarioPresenca) {
        this.dataPresenca = dataPresenca;
        this.inicioPresenca = inicioPresenca;
        this.fimPresenca = fimPresenca;
        this.horasTrabalhadas = horasTrabalhadas;
        this.funcionarioPresenca = funcionarioPresenca;
    }

    
    /* GETTERS and SETTERS */
    public int getIdPresenca() {
        return idPresenca;
    }

    public void setIdPresenca(int idPresenca) {
        this.idPresenca = idPresenca;
    }

    public Date getDataPresenca() {
        return dataPresenca;
    }

    public void setDataPresenca(Date dataPresenca) {
        this.dataPresenca = dataPresenca;
    }

    public Time getInicioPresenca() {
        if(inicioPresenca == null){
            return Time.valueOf("00:00:00");
        }
        return inicioPresenca;
    }

    public void setInicioPresenca(Time inicioPresenca) {
        this.inicioPresenca = inicioPresenca;
    }

    public Time getFimPresenca() {
        if(fimPresenca == null){
            return Time.valueOf("00:00:00");
        }
        return fimPresenca;
    }

    public void setFimPresenca(Time fimPresenca) {
        this.fimPresenca = fimPresenca;
    }

    public int getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public Funcionario getFuncionarioPresenca() {
        return funcionarioPresenca;
    }

    public void setFuncionarioPresenca(Funcionario idFuncionario) {
        this.funcionarioPresenca = idFuncionario;
    }

    // Metodo dos atributos para mostrar na tabela.
    public int getHorasTrabalhadasToShow() {
        int minutosEntrada = inicioPresenca.toLocalTime().toSecondOfDay() / 60;
        int minutosSaida = fimPresenca.toLocalTime().toSecondOfDay() / 60;
        
        int horasEntrada = minutosEntrada / 60;
        int horasSaida = minutosSaida / 60;
               
        int horas = horasSaida - horasEntrada;
        if(horas > 0) {
            return horas;
        }
        return 0;
    }

    public void setHorasTrabalhadasToShow(int horasTrabalhadasToShow) {
        this.horasTrabalhadasToShow = horasTrabalhadasToShow;
    }
    
    
    
    @Override
    public String toString() {
        return this.dataPresenca.toString();
    }
    
    
}

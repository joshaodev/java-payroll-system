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
public class Presenca {
    private int idPresenca;
    private Date dataPresenca;
    private Time inicioPresenca;
    private Time fimPresenca;
    private int horasTrabalhadas;
    private Funcionario funcionarioPresenca;
    
    // Atributo para mostrar n a tabela
    private int horasTrabalhadasToShow;
    private String codigoFuncionario;
    private String nomeFuncionario;
    private String turnoFuncionario;

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

    // Contructor adicionando os atributos a mostrar na tabela
    public Presenca(int idPresenca, Date dataPresenca, Time inicioPresenca, Time fimPresenca, int horasTrabalhadas, Funcionario funcionarioPresenca, int horasTrabalhadasToShow, String codigoFuncionario, String nomeFuncionario, String turnoFuncionario) {
        this.idPresenca = idPresenca;
        this.dataPresenca = dataPresenca;
        this.inicioPresenca = inicioPresenca;
        this.fimPresenca = fimPresenca;
        this.horasTrabalhadas = horasTrabalhadas;
        this.funcionarioPresenca = funcionarioPresenca;
        this.horasTrabalhadasToShow = horasTrabalhadasToShow;
        this.codigoFuncionario = codigoFuncionario;
        this.nomeFuncionario = nomeFuncionario;
        this.turnoFuncionario = turnoFuncionario;
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
        int minutosEntrada = inicioPresenca.toLocalTime().toSecondOfDay() / 60;
        int minutosSaida = fimPresenca.toLocalTime().toSecondOfDay() / 60;
        
        int horasEntrada = minutosEntrada / 60;
        int horasSaida = minutosSaida / 60;
               
        int horas = horasSaida - horasEntrada;
        if(horas > 0) {
            horasTrabalhadas = horas;
        }
        else{
            horasTrabalhadas =  0;
        }
        
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
    
    /* Metodos dos atributos a mostrar nas tabelas */
    public String getCodigoFuncionario() {
        codigoFuncionario = funcionarioPresenca.getCodigo();
        return codigoFuncionario;
    }

    public String getNomeFuncionario() {
        nomeFuncionario = funcionarioPresenca.getNome();
        return nomeFuncionario;
    }

    public String getTurnoFuncionario() {
        turnoFuncionario = funcionarioPresenca.getTurno().getNome();
        return turnoFuncionario;
    }
    
    @Override
    public String toString() {
        return this.dataPresenca.toString();
    }
    
    
}

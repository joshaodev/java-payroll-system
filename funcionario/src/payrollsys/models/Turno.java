
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.models;

import java.sql.Time;

/**
 *
 * @author joshtag096
 */
public class Turno {
    private int id;
    private String nome;
    private Time entrada;
    private Time saida;
    
    public Turno(){
        
    }
    
    public Turno(int id, String nome, Time entrada, Time saida){
        this.id = id;
        this.nome = nome;
        this.entrada = entrada;
        this.saida = saida;  
    }
    
    public Turno(int id, String nome){
        this.id = id;
        this.nome = nome; 
    }
    
    public Turno(String nome, Time entrada, Time saida){
        this.nome = nome;
        this.entrada = entrada;
        this.saida = saida;
    }
    
    /* SETTERS E GETTERS */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Time getEntrada() {
        return entrada;
    }

    public void setEntrada(Time entrada) {
        this.entrada = entrada;
    }

    public Time getSaida() {
        return saida;
    }

    public void setSaida(Time saida) {
        this.saida = saida;
    }
    
    /* ToString */
    public String toString(){
        return this.nome;
    }   
    
    
}

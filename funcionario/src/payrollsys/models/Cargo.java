/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.models;
/**
 *
 * @author joshtag096
 */
public class Cargo {
    private int idCargo;
    private String nome;
    private String descricao;
    
    public Cargo(){
        
    }
    
    public Cargo(int id, String nome, String descricao){
        this.idCargo = id;
        this.nome = nome;
        this.descricao = descricao;
    }
    
    public Cargo(String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;
    }
    
    /* GETTERS and SETTERS */
    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.nome;
    }

}

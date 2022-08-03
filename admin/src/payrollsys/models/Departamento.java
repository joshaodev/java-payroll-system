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
public class Departamento {
    private int idDept;
    private String nome;
    
    public Departamento(){
        
    }
    
    public Departamento(int id, String nome){
        this.idDept = id;
        this.nome = nome;
    }
    
    public Departamento(String nome){
        this.nome = nome;
    }
    
    /* GETTERS and SETTERS */
    public int getIdDept() {
        return idDept;
    }

    public void setIdDept(int idDept) {
        this.idDept = idDept;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
    
    
    
}

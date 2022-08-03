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
public class Usuario {
    private int idUsuario;
    private String nomeUsuario;
    private String senhaUsuario;
    private int admin;
    private Funcionario funcionarioUsuario;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nomeUsuario, String senhaUsuario, int admin, Funcionario funcionarioUsuario) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.senhaUsuario = senhaUsuario;
        this.admin = admin;
        this.funcionarioUsuario = funcionarioUsuario;
    }

    public Usuario(String nomeUsuario, String senhaUsuario, int admin, Funcionario funcionarioUsuario) {
        this.nomeUsuario = nomeUsuario;
        this.senhaUsuario = senhaUsuario;
        this.admin = admin;
        this.funcionarioUsuario = funcionarioUsuario;
    }
    
    /* GETTERS E SETTERS */
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public Funcionario getFuncionarioUsuario() {
        return funcionarioUsuario;
    }

    public void setFuncionarioUsuario(Funcionario funcionarioUsuario) {
        this.funcionarioUsuario = funcionarioUsuario;
    }

    @Override
    public String toString() {
        return this.nomeUsuario;
    }
    
    
    
}

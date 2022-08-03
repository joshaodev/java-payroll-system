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
public class Funcionario {
    private int idFuncionario;
    private String codigo;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private Date dataNascimento;
    private String genero;
    private String estadoCivil;
    private Departamento departamento;
    private Cargo cargo;
    private Turno turno;
    private Date dataContrato;
    private String foto;
    private double salarioBase;
    private int active;

    public Funcionario() {
    }

    public Funcionario(int idFuncionario, String codigo, String nome, String endereco, Date dataNascimento, String telefone, String email, String genero, String estadoCivil, Departamento departamento, Cargo cargo, Turno turno, Date dataContrato, String foto, double salarioBase,  int active) {
        this.idFuncionario = idFuncionario;
        this.codigo = codigo;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.estadoCivil = estadoCivil;
        this.departamento = departamento;
        this.cargo = cargo;
        this.turno = turno;
        this.dataContrato = dataContrato;
        this.foto = foto;
        this.salarioBase = salarioBase;
        this.active = active;
    }

    public Funcionario(String codigo, String nome, String endereco,  Date dataNascimento, String telefone, String email, String genero, String estadoCivil, Departamento departamento, Cargo cargo, Turno turno, Date dataContrato, String foto, double salarioBase, int active) {
        this.codigo = codigo;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.estadoCivil = estadoCivil;
        this.departamento = departamento;
        this.cargo = cargo;
        this.turno = turno;
        this.dataContrato = dataContrato;
        this.foto = foto;
        this.salarioBase = salarioBase;
        this.active = active;
    }
    
    /* SETTERS and GETTERS */
    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
    
    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Date getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(Date dataContrato) {
        this.dataContrato = dataContrato;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
     
    
    
    @Override
    public String toString() {
        return this.nome;
    }
       
}

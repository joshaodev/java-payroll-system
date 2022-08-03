/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.ObservableList;
import payrollsys.models.Cargo;
import payrollsys.models.Departamento;
import payrollsys.models.Funcionario;
import payrollsys.models.Turno;

/**
 *
 * @author joshtag096
 */
public class FuncionarioOp {
    
    public static void SelecionarFuncionarios(Connection con, ObservableList<Funcionario> listaFuncionarios){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT F.idFuncionario, "
                    + "F.codigo, "
                    + "F.nome, "
                    + "F.endereco, "
                    + "F.dataNascimento, "
                    + "F.genero, "
                    + "F.estadoCivil, "
                    + "F.telefone, "
                    + "F.email, "
                    + "F.dataCadastro, "
                    + "F.salarioBase, "
                    + "F.imagem, "
                    + "F.activo, "
                    + "T.idTurno, "
                        + "T.nomeTurno, "
                        + "T.entrada, "
                        + "T.saida, "
                    + "C.idCargo, "
                        + "C.nomeCargo, "
                        + "C.descricaoCargo, "
                    + "D.idDepartamento, "
                        + "D.nomeDepartamento "
                    + "FROM funcionario F "
                    + "INNER JOIN turno T ON (F.idTurno = T.idTurno) "
                    + "INNER JOIN cargo C ON (F.idCargo = C.idCargo) "
                    + "INNER JOIN departamento D ON (F.idDepartamento = D.idDepartamento)"
            );
            
            while(rs.next()){
                listaFuncionarios.add(new Funcionario(
                    rs.getInt("idFuncionario"),
                    rs.getString("codigo"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getDate("dataNascimento"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("genero"),
                    rs.getString("estadoCivil"),
                    
                    new Departamento(
                        rs.getInt("idDepartamento"),
                        rs.getString("nomeDepartamento")
                    ),
                    new Cargo(
                        rs.getInt("idCargo"),
                        rs.getString("nomeCargo"),
                        rs.getString("descricaoCargo")
                    ),
                    new Turno(
                        rs.getInt("idTurno"),
                        rs.getString("nomeTurno"),
                        rs.getTime("entrada"),
                        rs.getTime("saida")
                    ),
                    rs.getDate("dataCadastro"),
                    rs.getString("imagem"),
                    rs.getDouble("salarioBase"),
                    rs.getInt("activo")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static Funcionario SelecionarFuncionario(Connection con, String codigo){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT F.idFuncionario, "
                    + "F.codigo, "
                    + "F.nome, "
                    + "F.endereco, "
                    + "F.dataNascimento, "
                    + "F.genero, "
                    + "F.estadoCivil, "
                    + "F.telefone, "
                    + "F.email, "
                    + "F.dataCadastro, "
                    + "F.salarioBase, "
                    + "F.imagem, "
                    + "F.activo, "
                    + "T.idTurno, "
                        + "T.nomeTurno, "
                        + "T.entrada, "
                        + "T.saida, "
                    + "C.idCargo, "
                        + "C.nomeCargo, "
                        + "C.descricaoCargo, "
                    + "D.idDepartamento, "
                        + "D.nomeDepartamento "
                    + "FROM funcionario F "
                    + "INNER JOIN turno T ON (F.idTurno = T.idTurno) "
                    + "INNER JOIN cargo C ON (F.idCargo = C.idCargo) "
                    + "INNER JOIN departamento D ON (F.idDepartamento = D.idDepartamento) "
                    + "WHERE F.codigo = '"+codigo+"' "
            );
            
            while(rs.next()){
                return new Funcionario(
                    rs.getInt("idFuncionario"),
                    rs.getString("codigo"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getDate("dataNascimento"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("genero"),
                    rs.getString("estadoCivil"),
                    new Departamento(
                        rs.getInt("idDepartamento"),
                        rs.getString("nomeDepartamento")
                    ),
                    new Cargo(
                        rs.getInt("idCargo"),
                        rs.getString("nomeCargo"),
                        rs.getString("descricaoCargo")
                    ),
                    new Turno(
                        rs.getInt("idTurno"),
                        rs.getString("nomeTurno"),
                        rs.getTime("entrada"),
                        rs.getTime("saida")
                    ),
                    rs.getDate("dataCadastro"),
                    rs.getString("imagem"),
                    rs.getDouble("salarioBase"),
                    rs.getInt("activo")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static int numeroFuncionarios(Connection con){
        int numFuncionarios = 0;
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT COUNT(F.idFuncionario) as num_funcs "
                +"FROM funcionario F "
            
            );
            
            while(rs.next()){
                numFuncionarios = rs.getInt("num_funcs");
                return numFuncionarios;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    public static int numeroFuncionariosHomens(Connection con){
        int numFuncionariosHomens = 0;
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT COUNT(F.idFuncionario) as num_funcs_homens "
                +"FROM funcionario F "
                +"WHERE F.genero = 'Masculino'"
            
            );
            
            while(rs.next()){
                numFuncionariosHomens = rs.getInt("num_funcs_homens");
                return numFuncionariosHomens;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    public static int numeroFuncionariosMulheres(Connection con){
        int numFuncionariosMulheres = 0;
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT COUNT(F.idFuncionario) as num_funcs_mulheres "
                +"FROM funcionario F "
                +"WHERE F.genero = 'Feminino'"
            
            );
            
            while(rs.next()){
                numFuncionariosMulheres = rs.getInt("num_funcs_mulheres");
                return numFuncionariosMulheres;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    
    
    public static int InserirFuncionario(Connection con, Funcionario funcionario){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO funcionario ("
                        + "idFuncionario, "
                        + "codigo, "
                        + "nome, "
                        + "endereco, "
                        + "dataNascimento, "
                        + "genero, "
                        + "estadoCivil, "
                        + "telefone, "
                        + "email, " 
                        + "dataCadastro, "
                        + "salarioBase, "
                        + "imagem, "
                        + "idTurno, "
                        + "idCargo, "
                        + "idDepartamento) "
                + "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" 
            );
         
            pstm.setString(1,funcionario.getCodigo());
            pstm.setString(2,funcionario.getNome());
            pstm.setString(3,funcionario.getEndereco());
            pstm.setDate(4,funcionario.getDataNascimento());
            pstm.setString(5,funcionario.getGenero());
            pstm.setString(6,funcionario.getEstadoCivil());
            pstm.setString(7,funcionario.getTelefone());
            pstm.setString(8,funcionario.getEmail());
            pstm.setDate(9,funcionario.getDataContrato());
            pstm.setDouble(10,funcionario.getSalarioBase());
            pstm.setString(11,funcionario.getFoto());
            pstm.setInt(12,funcionario.getTurno().getId());
            pstm.setInt(13,funcionario.getCargo().getIdCargo());
            pstm.setInt(14,funcionario.getDepartamento().getIdDept());
           
            
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int ActualizarFuncionario(Connection con, Funcionario funcionario){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "UPDATE funcionario "
                    + "SET codigo = ? ,"              //1
                    + "nome = ? ,"
                    + "endereco = ? ,"
                    + "dataNascimento = ? ,"
                    + "genero = ? ,"
                    + "estadoCivil = ? ,"
                    + "telefone = ? ,"
                    + "email = ? ," 
                    + "dataCadastro = ? ,"
                    + "salarioBase = ? ,"
                    + "imagem = ? ,"
                    + "idTurno = ? ,"
                    + "idCargo = ? ,"
                    + "idDepartamento = ? ,"  
                    + "activo = ? "//3
                    + "WHERE funcionario.idFuncionario = ?"     //4
            );
            
            pstm.setString(1,funcionario.getCodigo());
            pstm.setString(2,funcionario.getNome());
            pstm.setString(3,funcionario.getEndereco());
            pstm.setDate(4,funcionario.getDataNascimento());
            pstm.setString(5,funcionario.getGenero());
            pstm.setString(6,funcionario.getEstadoCivil());
            pstm.setString(7,funcionario.getTelefone());
            pstm.setString(8,funcionario.getEmail());
            pstm.setDate(9,funcionario.getDataContrato());
            pstm.setDouble(10,funcionario.getSalarioBase());
            pstm.setString(11,funcionario.getFoto());
            pstm.setInt(12,funcionario.getTurno().getId());
            pstm.setInt(13,funcionario.getCargo().getIdCargo());
            pstm.setInt(14,funcionario.getDepartamento().getIdDept());
            pstm.setInt(15,funcionario.getActive());
            pstm.setInt(16,funcionario.getIdFuncionario());
            
            
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int EliminarFuncionario(Connection con, Funcionario funcionario){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "DELETE FROM funcionario "
                    + "WHERE funcionario.idFuncionario = ?"  //
            );
            
            pstm.setInt(1, funcionario.getIdFuncionario());
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}

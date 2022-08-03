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
import payrollsys.models.Departamento;


/**
 *
 * @author joshtag096
 */
public class DepartamentoOp {
    
    public static void SelecionarDados(Connection con, ObservableList<Departamento> listaDept){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT idDepartamento, "
                + "nomeDepartamento "
                + "FROM departamento"
            );
            
            while(rs.next()){
                listaDept.add(new Departamento(
                        rs.getInt("idDepartamento"), 
                        rs.getString("nomeDepartamento")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int numeroDepartamentos(Connection con){
        int numDepartamentos = 0;
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT COUNT(D.idDepartamento) as num_depts "
                +"FROM departamento D "
            );
            
            while(rs.next()){
                numDepartamentos = rs.getInt("num_depts");
                return numDepartamentos;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    public static int InserirDados(Connection con, Departamento dept){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO departamento (idDepartamento, nomeDepartamento) "
                + "VALUES (NULL, ?)" //1
            );
         
            pstm.setString(1, dept.getNome());
            
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int ActualizarDados(Connection con, Departamento dept){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "UPDATE departamento "
                    + "SET nomeDepartamento = ? "            //1
                    + "WHERE departamento.idDepartamento = ?"  //2
            );
            
            pstm.setString(1, dept.getNome());
            pstm.setInt(2, dept.getIdDept());
            
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int EliminarDados(Connection con, Departamento dept){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "DELETE FROM departamento "
                    + "WHERE departamento.idDepartamento = ?"  //
            );
            
            pstm.setInt(1, dept.getIdDept());
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
}

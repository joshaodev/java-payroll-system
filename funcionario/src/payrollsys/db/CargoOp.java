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

/**
 *
 * @author joshtag096
 */
public class CargoOp {
    
    public static void SelecionarCargos(Connection con, ObservableList<Cargo> listaCargo){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT idCargo, "
                + "nomeCargo, "
                + "descricaoCargo "
                + "FROM cargo"
            );
            
            while(rs.next()){
                listaCargo.add(new Cargo(
                        rs.getInt("idCargo"),
                        rs.getString("nomeCargo"), 
                        rs.getString("descricaoCargo")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int InserirCargo(Connection con, Cargo cargo){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO cargo (idCargo, nomeCargo, descricaoCargo) "
                + "VALUES (NULL, ?, ?)" //1, 2
            );
         
            pstm.setString(1, cargo.getNome());
            pstm.setString(2, cargo.getDescricao());
            
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int ActualizarCargo(Connection con, Cargo cargo){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "UPDATE cargo "
                    + "SET nomeCargo = ? ,"
                    + "descricaoCargo = ?"            //1
                    + "WHERE cargo.idCargo = ?"  //2
            );
            
            pstm.setString(1, cargo.getNome());
            pstm.setString(2, cargo.getDescricao());
            pstm.setInt(3, cargo.getIdCargo());
            
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int EliminarCargo(Connection con, Cargo cargo){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "DELETE FROM cargo "
                    + "WHERE cargo.idCargo = ?"  //
            );
            
            pstm.setInt(1, cargo.getIdCargo());
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}

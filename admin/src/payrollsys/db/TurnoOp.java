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
import payrollsys.models.Turno;

/**
 *
 * @author joshtag096
 */
public class TurnoOp {
    
    public static void SelecionarTurnos(Connection con, ObservableList<Turno> listaTurnos){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT idTurno, "
                    + "nomeTurno, "
                    + "entrada, "
                    + "saida "
                    + "FROM turno"
            );
            
            while(rs.next()){
                listaTurnos.add(new Turno(
                    rs.getInt("idTurno"),
                    rs.getString("nomeTurno"),
                    rs.getTime("entrada"),
                    rs.getTime("saida")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int InserirTurno(Connection con, Turno turno){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO turno (idTurno, nomeTurno, entrada, saida) "
                + "VALUES (NULL, ?, ?, ?)" //1, 2
            );
         
            pstm.setString(1, turno.getNome());
            pstm.setTime(2, turno.getEntrada());
            pstm.setTime(3, turno.getSaida());
            
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int ActualizarTurno(Connection con, Turno turno){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "UPDATE turno "
                    + "SET nomeTurno = ? ,"              //1
                    + "entrada = ? ,"               //2
                    + "saida = ? "                   //3
                    + "WHERE turno.idTurno = ?"     //4
            );
            
            pstm.setString(1, turno.getNome());
            pstm.setTime(2, turno.getEntrada());
            pstm.setTime(3, turno.getSaida());
            pstm.setInt(4, turno.getId());
            
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int EliminarTurno(Connection con, Turno turno){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "DELETE FROM turno "
                    + "WHERE turno.idTurno = ?"  //
            );
            
            pstm.setInt(1, turno.getId());
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    /* OTHRES QUERIES */
  
}

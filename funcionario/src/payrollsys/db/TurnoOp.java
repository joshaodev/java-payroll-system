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
    
}

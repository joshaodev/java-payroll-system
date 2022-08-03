/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.ObservableList;
import payrollsys.models.Funcionario;
import payrollsys.models.Presenca;
import payrollsys.models.Turno;

/**
 *
 * @author joshtag096
 */
public class PresencaOp {
     
    public static void SelecionarPresencasData(Connection con, Date data, ObservableList<Presenca> listaPresencas){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT " +
                "P.idPresenca, " +
                "P.dataPresenca, " +
                "P.entradaPresenca, " +
                "P.saidaPresenca, " +
                "P.horasTrabalhada, " +
                "P.idFuncionario, " +
                    "F.idFuncionario, " +
                    "F.codigo, " +
                    "F.nome, " +
                    "F.imagem, " +
                    "F.idTurno, " +
                        "T.idTurno, " +
                        "T.nomeTurno " +
                "FROM " +
                "funcionario F INNER JOIN presenca P ON F.idFuncionario = P.idFuncionario " +
                "INNER JOIN turno T ON F.idTurno = T.idTurno "+
                "WHERE P.dataPresenca = '"+data+"' " +
                "ORDER BY P.entradaPresenca ASC"
            );
            
            while(rs.next()){
                listaPresencas.add(new Presenca(
                    rs.getInt("idPresenca"),
                    rs.getDate("dataPresenca"),
                    rs.getTime("entradaPresenca"),
                    rs.getTime("saidaPresenca"),
                    rs.getInt("horasTrabalhada"),
                    new Funcionario(
                        rs.getInt("idFuncionario"),
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getString("imagem"),
                            new Turno(
                                rs.getInt("idTurno"),
                                rs.getString("nomeTurno")
                            )
                    )
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void SelecionarPresencasFuncionario(Connection con, int mes, int idFuncionario, ObservableList<Presenca> listaPresencas){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT " +
                "P.idPresenca, " +
                "P.dataPresenca, " +
                "P.entradaPresenca, " +
                "P.saidaPresenca, " +
                "P.horasTrabalhada, " +
                "P.idFuncionario, " +
                    "F.idFuncionario, " +
                    "F.codigo, " +
                    "F.nome, " +
                    "F.imagem, " +
                    "F.idTurno, " +
                        "T.idTurno, " +
                        "T.nomeTurno " +
                "FROM " +
                "funcionario F INNER JOIN presenca P ON F.idFuncionario = P.idFuncionario " +
                "INNER JOIN turno T ON F.idTurno = T.idTurno "+
                "WHERE EXTRACT(month from P.dataPresenca) = '"+mes+"' and P.idFuncionario = '"+idFuncionario+"' "
            );
            
            while(rs.next()){
                listaPresencas.add(new Presenca(
                    rs.getInt("idPresenca"),
                    rs.getDate("dataPresenca"),
                    rs.getTime("entradaPresenca"),
                    rs.getTime("saidaPresenca"),
                    rs.getInt("horasTrabalhada"),
                    new Funcionario(
                        rs.getInt("idFuncionario"),
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getString("imagem"),
                            new Turno(
                                rs.getInt("idTurno"),
                                rs.getString("nomeTurno")
                            )
                    )
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static Presenca SelecionarPresencaFuncionario(Connection con, Date data, String codigoFuncionario){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT " +
                "P.idPresenca, " +
                "P.dataPresenca, " +
                "P.entradaPresenca, " +
                "P.saidaPresenca, " +
                "P.horasTrabalhada, " +
                "P.idFuncionario, " +
                    "F.idFuncionario, " +
                    "F.codigo, " +
                    "F.nome, " +
                    "F.imagem, " +
                    "F.idTurno, " +
                        "T.idTurno, " +
                        "T.nomeTurno " +
                "FROM " +
                "funcionario F INNER JOIN presenca P ON F.idFuncionario = P.idFuncionario " +
                "INNER JOIN turno T ON F.idTurno = T.idTurno "+
                "WHERE P.dataPresenca = '"+data+"' and F.codigo = '"+codigoFuncionario+"' "
            );
            
            while(rs.next()){
                return (new Presenca(
                    rs.getInt("idPresenca"),
                    rs.getDate("dataPresenca"),
                    rs.getTime("entradaPresenca"),
                    rs.getTime("saidaPresenca"),
                    rs.getInt("horasTrabalhada"),
                    new Funcionario(
                        rs.getInt("idFuncionario"),
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getString("imagem"),
                            new Turno(
                                rs.getInt("idTurno"),
                                rs.getString("nomeTurno")
                            )
                    )
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static Presenca SelecionarPresencaDataFuncionario(Connection con, Date data, int idFuncionario){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT " +
                "P.idPresenca, " +
                "P.dataPresenca, " +
                "P.entradaPresenca, " +
                "P.saidaPresenca, " +
                "P.horasTrabalhada, " +
                "P.idFuncionario, " +
                    "F.idFuncionario, " +
                    "F.codigo, " +
                    "F.nome, " +
                    "F.imagem, " +
                    "F.idTurno, " +
                        "T.idTurno, " +
                        "T.nomeTurno " +
                "FROM " +
                "funcionario F INNER JOIN presenca P ON F.idFuncionario = P.idFuncionario " +
                "INNER JOIN turno T ON F.idTurno = T.idTurno "+
                "WHERE P.dataPresenca = '"+data+"' and F.idFuncionario = '"+idFuncionario+"' "
            );
            
            while(rs.next()){
                return (new Presenca(
                    rs.getInt("idPresenca"),
                    rs.getDate("dataPresenca"),
                    rs.getTime("entradaPresenca"),
                    rs.getTime("saidaPresenca"),
                    rs.getInt("horasTrabalhada"),
                    new Funcionario(
                        rs.getInt("idFuncionario"),
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getString("imagem"),
                            new Turno(
                                rs.getInt("idTurno"),
                                rs.getString("nomeTurno")
                            )
                    )
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    
    public static int SelecionarTotalHorasTrabalhadas(Connection con, int idFuncionario, int mesDoAno){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT P.idPresenca, "
                    + "P.dataPresenca, "
                    + "P.entradaPresenca, "
                    + "P.saidaPresenca, "
                    + "SUM(P.horasTrabalhada) AS totalHorasTrabalhada, "
                    + "F.idFuncionario, "
                        + "F.codigo, "
                        + "F.nome, "
                        + "F.imagem "
                + "FROM presenca P "
                + "INNER JOIN funcionario F ON (P.idFuncionario = F.idFuncionario) "
                + "WHERE P.idFuncionario = "+idFuncionario+" and MONTH(P.dataPresenca) = "+mesDoAno+" "
            );
            
            while(rs.next()){
                return rs.getInt("totalHorasTrabalhada");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    
    
    
    
    
    
    
    
    
    public static Presenca SelecionarPresenca(Connection con, int data){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT P.idPresenca, "
                    + "P.dataPresenca, "
                    + "P.entradaPresenca, "
                    + "P.saidaPresenca, "
                    + "P.horasTrabalhada, "
                    + "F.idFuncionario, "
                        + "F.codigo, "
                        + "F.nome, "
                        + "F.imagem "
                + "FROM presenca P "
                + "INNER JOIN funcionario F ON (P.idFuncionario = F.idFuncionario) "
                + "WHERE P.dataPresenca = '"+data+"' "
            );
            
            while(rs.next()){
                return new Presenca(
                    rs.getInt("idPresenca"),
                    rs.getDate("dataPresenca"),
                    rs.getTime("entradaPresenca"),
                    rs.getTime("saidaPresenca"),
                    rs.getInt("horasTrabalhada"),
                    new Funcionario(
                        rs.getInt("idFuncionario"),
                            rs.getString("codigo"),
                            rs.getString("nome"),
                            rs.getString("imagem"),
                            new Turno(
                                rs.getInt("idTurno"),
                                rs.getString("nomeTurno")
                            )
                    )
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static int InserirPresenca(Connection con, Presenca presenca){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO presenca "
                        + "(idPresenca, "
                        + "dataPresenca, "
                        + "entradaPresenca, "
                        + "saidaPresenca, "
                        + "horasTrabalhada, "
                        + "idFuncionario) "
                + "VALUES (NULL, ?, ?, ?, ?, ?)" 
            );
         
            pstm.setDate(1,presenca.getDataPresenca());
            pstm.setTime(2,presenca.getInicioPresenca());
            pstm.setTime(3,presenca.getFimPresenca());
            pstm.setInt(4,presenca.getHorasTrabalhadas());
            pstm.setInt(5,presenca.getFuncionarioPresenca().getIdFuncionario());
                       
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int ActualizarPresenca(Connection con, Presenca presenca){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "UPDATE presenca SET "
                    + "dataPresenca = ? ," 
                    + "entradaPresenca = ? ," 
                    + "saidaPresenca = ? ,"  
                    + "horasTrabalhada = ? "  //1
                    + "WHERE presenca.idPresenca = ?"     //2
            );
            
            pstm.setDate(1,presenca.getDataPresenca());
            pstm.setTime(2,presenca.getInicioPresenca());
            pstm.setTime(3,presenca.getFimPresenca());
            pstm.setInt(4,presenca.getHorasTrabalhadas());
            pstm.setInt(5,presenca.getIdPresenca());
            
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

}

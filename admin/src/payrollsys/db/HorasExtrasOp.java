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
import payrollsys.models.Cargo;
import payrollsys.models.Departamento;
import payrollsys.models.Funcionario;
import payrollsys.models.HorasExtras;
import payrollsys.models.Turno;

/**
 *
 * @author joshtag096
 */
public class HorasExtrasOp {
    
    public static void SelecionarHorasExtras(Connection con, ObservableList<HorasExtras> listaHorasExtras){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT H.idHorasExtras, "+
                    "H.dataHorasExtras, "+
                    "H.entradaHorasExtras, "+
                    "H.saidaHorasExtras, "+
                    "H.pagametoHorasExtras, "+
                    "H.horasFeita, "   +
                    "F.idFuncionario, "+
                        "F.codigo, "+
                        "F.nome, "+
                        "F.endereco, "+
                        "F.dataNascimento, "+
                        "F.genero, "+
                        "F.estadoCivil, "+
                        "F.telefone, "+
                        "F.email, "+
                        "F.dataCadastro, "+
                        "F.salarioBase, "+
                        "F.imagem, "+
                        "F.activo, "+
                        "T.idTurno, "+
                            "T.nomeTurno, "+
                            "T.entrada, "+
                            "T.saida, "+
                        "C.idCargo, "+
                            "C.nomeCargo, "+
                            "C.descricaoCargo, "+
                        "D.idDepartamento, "+
                            "D.nomeDepartamento "+       
                "FROM horasextras H "+
                "INNER JOIN funcionario F ON (H.idFuncionario = F.idFuncionario) "+
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " 
            );
            
            while(rs.next()){
                listaHorasExtras.add(new HorasExtras(
                    rs.getInt("idHorasExtras"),
                    rs.getDate("dataHorasExtras"),
                    rs.getTime("entradaHorasExtras"),
                    rs.getTime("saidaHorasExtras"),
                    rs.getInt("pagametoHorasExtras"),
                    rs.getInt("horasFeita"),    
                    new Funcionario(
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
                    )
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void SelecionarHorasExtrasData(Connection con, Date data, ObservableList<HorasExtras> listaHorasExtras){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT H.idHorasExtras, "+
                    "H.dataHorasExtras, "+
                    "H.entradaHorasExtras, "+
                    "H.saidaHorasExtras, "+
                    "H.pagametoHorasExtras, "+
                    "H.horasFeita, "   +
                    "F.idFuncionario, "+
                        "F.codigo, "+
                        "F.nome, "+
                        "F.endereco, "+
                        "F.dataNascimento, "+
                        "F.genero, "+
                        "F.estadoCivil, "+
                        "F.telefone, "+
                        "F.email, "+
                        "F.dataCadastro, "+
                        "F.salarioBase, "+
                        "F.imagem, "+
                        "F.activo, "+
                        "T.idTurno, "+
                            "T.nomeTurno, "+
                            "T.entrada, "+
                            "T.saida, "+
                        "C.idCargo, "+
                            "C.nomeCargo, "+
                            "C.descricaoCargo, "+
                        "D.idDepartamento, "+
                            "D.nomeDepartamento "+       
                "FROM horasextras H "+
                "INNER JOIN funcionario F ON (H.idFuncionario = F.idFuncionario) "+
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " +
                "WHERE H.dataHorasExtras >= '"+data+"' "+
                "ORDER BY entradaHorasExtras ASC"
            );
            
            while(rs.next()){
                listaHorasExtras.add(new HorasExtras(
                    rs.getInt("idHorasExtras"),
                    rs.getDate("dataHorasExtras"),
                    rs.getTime("entradaHorasExtras"),
                    rs.getTime("saidaHorasExtras"),
                    rs.getInt("pagametoHorasExtras"),
                    rs.getInt("horasFeita"),    
                    new Funcionario(
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
                    )
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static HorasExtras SelecionarTotalHorasExtras(Connection con, int idFuncionario, int mesDoAno){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT H.idHorasExtras, "+
                    "H.dataHorasExtras, "+
                    "H.entradaHorasExtras, "+
                    "H.saidaHorasExtras, "+
                    "SUM(H.pagametoHorasExtras) AS totalPagamentoHorasExtras, "+
                    "H.horasFeita, "   +
                    "F.idFuncionario, "+
                        "F.codigo, "+
                        "F.nome, "+
                        "F.endereco, "+
                        "F.dataNascimento, "+
                        "F.genero, "+
                        "F.estadoCivil, "+
                        "F.telefone, "+
                        "F.email, "+
                        "F.dataCadastro, "+
                        "F.salarioBase, "+
                        "F.imagem, "+
                        "F.activo, "+
                        "T.idTurno, "+
                            "T.nomeTurno, "+
                            "T.entrada, "+
                            "T.saida, "+
                        "C.idCargo, "+
                            "C.nomeCargo, "+
                            "C.descricaoCargo, "+
                        "D.idDepartamento, "+
                            "D.nomeDepartamento "+       
                "FROM horasextras H "+
                "INNER JOIN funcionario F ON (H.idFuncionario = F.idFuncionario) "+
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " +
                "WHERE H.idFuncionario = "+idFuncionario+" and MONTH(H.dataHorasExtras) = "+mesDoAno+" "
            );
            
            while(rs.next()){
                return new HorasExtras(
                    rs.getInt("idHorasExtras"),
                    rs.getDate("dataHorasExtras"),
                    rs.getTime("entradaHorasExtras"),
                    rs.getTime("saidaHorasExtras"),
                    rs.getDouble("totalPagamentoHorasExtras"),
                    rs.getInt("horasFeita"),
                    new Funcionario(
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
                    )
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static int InserirHorasExtras(Connection con, HorasExtras horaExtra){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO horasextras "
                        + "(idHorasExtras, "
                        + "dataHorasExtras, "
                        + "entradaHorasExtras, "
                        + "saidaHorasExtras, "
                        + "pagametoHorasExtras, "
                        + "horasFeita, "
                        + "idFuncionario) "
                + "VALUES (NULL, ?, ?, ?, ?, ?, ?)" 
            );
         
            pstm.setDate(1,horaExtra.getDataHorasExtras());
            pstm.setTime(2,horaExtra.getEntradaHorasExtras());
            pstm.setTime(3,horaExtra.getSaidaHorasExtras());
            pstm.setDouble(4,horaExtra.getPagamentoHorasExtras());
            pstm.setInt(5,horaExtra.getHorasExtrasFeita());
            pstm.setInt(6,horaExtra.getFuncionario().getIdFuncionario());
                       
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int ActualizarHorasExtras(Connection con, HorasExtras horaExtra){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "UPDATE horasextras SET "
                    + "dataHorasExtras = ? ," 
                    + "entradaHorasExtras = ? ,"
                    + "saidaHorasExtras = ? ,"
                    + "pagametoHorasExtras = ? ,"
                    + "horasFeita= ? "  
                    + "WHERE horasextras.idHorasExtras = ?"     //2
            );
            
            pstm.setDate(1,horaExtra.getDataHorasExtras());
            pstm.setTime(2,horaExtra.getEntradaHorasExtras());
            pstm.setTime(3,horaExtra.getSaidaHorasExtras());
            pstm.setDouble(4,horaExtra.getPagamentoHorasExtras());
            pstm.setInt(5,horaExtra.getHorasExtrasFeita());
            pstm.setInt(6,horaExtra.getIdHorasExtras());
            
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int EliminarHorasExtras(Connection con, HorasExtras HoraExtra){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "DELETE FROM horasextras "
                    + "WHERE horasextras.idHorasExtras  = ?"  //
            );
            
            pstm.setInt(1, HoraExtra.getIdHorasExtras());
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    
    /* Auxiliares */
    
    public static HorasExtras SelecionarTotalPagamentoHorasExtras(Connection con, int idFuncionario, int mesDoAno){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT H.idHorasExtras, "+
                    "H.dataHorasExtras, "+
                    "H.entradaHorasExtras, "+
                    "H.saidaHorasExtras, "+
                    "SUM(H.pagametoHorasExtras) AS totalPagamentoHorasExtras, "+
                    "SUM(H.horasFeita) AS totalHorasExtrasTrabalhada, "  +  
                    "F.idFuncionario, "+
                        "F.codigo, "+
                        "F.nome, "+
                        "F.endereco, "+
                        "F.dataNascimento, "+
                        "F.genero, "+
                        "F.estadoCivil, "+
                        "F.telefone, "+
                        "F.email, "+
                        "F.dataCadastro, "+
                        "F.salarioBase, "+
                        "F.imagem, "+
                        "F.activo, "+
                        "T.idTurno, "+
                            "T.nomeTurno, "+
                            "T.entrada, "+
                            "T.saida, "+
                        "C.idCargo, "+
                            "C.nomeCargo, "+
                            "C.descricaoCargo, "+
                        "D.idDepartamento, "+
                            "D.nomeDepartamento, "+       
                "FROM horasextras H "+
                "INNER JOIN funcionario F ON (H.idFuncionario = F.idFuncionario) "+
                "WHERE H.idFuncionario = "+idFuncionario+" and MONTH(P.dataHorasExtras) = "+mesDoAno+" "
            );
            
            while(rs.next()){
                return new HorasExtras(
                    rs.getInt("idHorasExtras"),
                    rs.getDate("dataHorasExtras"),
                    rs.getTime("entradaHorasExtras"),
                    rs.getTime("saidaHorasExtras"),
                    rs.getInt("totalPagamentoHorasExtras"),
                    rs.getInt("totalHorasExtrasTrabalhada"),
                    new Funcionario(
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
                    )
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

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
import payrollsys.models.Saida;
import payrollsys.models.Turno;

/**
 *
 * @author joshtag096
 */
public class SaidaOp {
    
    public static void SelecionarSaidas(Connection con, ObservableList<Saida> listaSaidas){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT S.idSaida, "+
                    "S.tipoSaida, "+
                    "S.dataInicio, "+
                    "S.dataFim, "+
                    "S.motivoSaida, " +
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
                "FROM saida S "+
                "INNER JOIN funcionario F ON (S.idFuncionario = F.idFuncionario) "+
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " 
            );
            
            while(rs.next()){
                listaSaidas.add(new Saida(
                    rs.getInt("idSaida"),
                    rs.getString("tipoSaida"),
                    rs.getDate("dataInicio"),
                    rs.getDate("dataFim"),
                    rs.getString("motivoSaida"),  
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
    
    public static int InserirSaida(Connection con, Saida saida){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO saida "
                        + "(idSaida, "
                        + "tipoSaida, "
                        + "dataInicio, "
                        + "dataFim, "
                        + "motivoSaida, "
                        + "idFuncionario) "
                + "VALUES (NULL, ?, ?, ?, ?, ?)" 
            );
         
            
            pstm.setString(1,saida.getTipoSaida());
            pstm.setDate(2,saida.getDataInicioSaida());
            pstm.setDate(3,saida.getDataFimSaida());
            pstm.setString(4,saida.getMotivoSaida());
            pstm.setInt(5,saida.getFuncionarioSaida().getIdFuncionario());
                       
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int ActualizarSaida(Connection con, Saida saida){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "UPDATE saida SET "
                    + "tipoSaida = ? ," 
                    + "dataInicio = ? ,"
                    + "dataFim = ? ,"
                    + "motivoSaida = ? " 
                    + "WHERE saida.idSaida = ?"     //2
            );
            
            pstm.setString(1,saida.getTipoSaida());
            pstm.setDate(2,saida.getDataInicioSaida());
            pstm.setDate(3,saida.getDataFimSaida());
            pstm.setString(4,saida.getMotivoSaida());
            pstm.setInt(5,saida.getIdSaida());
            
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int EliminarSaida(Connection con, Saida saida){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "DELETE FROM saida "
                    + "WHERE saida.idSaida  = ?"  //
            );
            
            pstm.setInt(1, saida.getIdSaida());
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    /* OUTRAS QUERIES */
    
    public static Saida SelecionarSaidaFeria(Connection con, int idFuncionario){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT S.idSaida, "+
                    "S.tipoSaida, "+
                    "S.dataInicio, "+
                    "S.dataFim, "+
                    "S.motivoSaida, " +
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
                "FROM saida S "+
                "INNER JOIN funcionario F ON (S.idFuncionario = F.idFuncionario) "+
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " +
                "WHERE S.idFuncionario = "+idFuncionario+" and S.motivoSaida = 'Ferias'"
            );
            
            while(rs.next()){
                return new Saida(
                    rs.getInt("idSaida"),
                    rs.getString("tipoSaida"),
                    rs.getDate("dataInicio"),
                    rs.getDate("dataFim"),
                    rs.getString("motivoSaida"),  
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
    
    public static void SelecionarSaidasActivas(Connection con, ObservableList<Saida> listaSaidas, Date dataActual){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT S.idSaida, "+
                    "S.tipoSaida, "+
                    "S.dataInicio, "+
                    "S.dataFim, "+
                    "S.motivoSaida, " +
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
                "FROM saida S "+
                "INNER JOIN funcionario F ON (S.idFuncionario = F.idFuncionario) "+
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " +
                "WHERE S.dataFim >= '"+dataActual+"'"
            );
            
            while(rs.next()){
                listaSaidas.add(new Saida(
                    rs.getInt("idSaida"),
                    rs.getString("tipoSaida"),
                    rs.getDate("dataInicio"),
                    rs.getDate("dataFim"),
                    rs.getString("motivoSaida"),  
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
    
}

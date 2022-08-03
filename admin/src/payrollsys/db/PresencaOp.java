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
import payrollsys.models.Presenca;
import payrollsys.models.Turno;

/**
 *
 * @author joshtag096
 */
public class PresencaOp {
    
    public static void SelecionarPresencas(Connection con, ObservableList<Presenca> listaPresencas){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT P.idPresenca, "+
                    "P.dataPresenca, "+
                    "P.entradaPresenca, "+
                    "P.saidaPresenca, "+
                    "P.horasTrabalhada, "+
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
                "FROM presenca P "+
                "INNER JOIN funcionario F ON (P.idFuncionario = F.idFuncionario) "+
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " 
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
    
    public static void SelecionarPresencasData(Connection con, Date data, ObservableList<Presenca> listaPresencas){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT P.idPresenca, "+
                    "P.dataPresenca, "+
                    "P.entradaPresenca, "+
                    "P.saidaPresenca, "+
                    "P.horasTrabalhada, "+
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
                "FROM presenca P "+
                "INNER JOIN funcionario F ON (P.idFuncionario = F.idFuncionario) "+
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " +
                "WHERE P.dataPresenca= '"+data+"' "+
                "ORDER BY entradaPresenca ASC"
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
    
    public static Presenca SelecionarPresenca(Connection con, Date data){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT P.idPresenca, "+
                    "P.dataPresenca, "+
                    "P.entradaPresenca, "+
                    "P.saidaPresenca, "+
                    "P.horasTrabalhada, "+
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
                "FROM presenca P "+
                "INNER JOIN funcionario F ON (P.idFuncionario = F.idFuncionario) "+
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " +
                "WHERE P.dataPresenca = '"+data+"' "
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
            pstm.setTime(3,presenca.getInicioPresenca());
            pstm.setTime(3,presenca.getFimPresenca());
            pstm.setInt(3,presenca.getHorasTrabalhadas());
            pstm.setInt(4,presenca.getFuncionarioPresenca().getIdFuncionario());
                       
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
                    + "saidaPresenca = ? "  
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
    
    public static int EliminarPresenca(Connection con, Presenca presenca){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "DELETE FROM presenca "
                    + "WHERE presenca.idPresenca = ?"  //
            );
            
            pstm.setInt(1, presenca.getIdPresenca());
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    
    /* Auxiliares */
    public static Presenca SelecionarTotalHorasTrabalhadas(Connection con, int idFuncionario, int mesDoAno){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT P.idPresenca, "+
                    "P.dataPresenca, "+
                    "P.entradaPresenca, "+
                    "P.saidaPresenca, "+
                    "SUM(P.horasTrabalhada) AS totalHorasTrabalhada, "+
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
                "FROM presenca P "+
                "INNER JOIN funcionario F ON (P.idFuncionario = F.idFuncionario) "+
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " +
                "WHERE P.idFuncionario = "+idFuncionario+" and MONTH(P.dataPresenca) = "+mesDoAno+" "
            );
            
            while(rs.next()){
                return new Presenca(
                    rs.getInt("idPresenca"),
                    rs.getDate("dataPresenca"),
                    rs.getTime("entradaPresenca"),
                    rs.getTime("saidaPresenca"),
                    rs.getInt("totalHorasTrabalhada"),
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

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
import payrollsys.models.Pagamento;
import payrollsys.models.Presenca;
import payrollsys.models.Turno;

/**
 *
 * @author joshtag096
 */
public class PagamentoOp {
    
    public static void SelecionarPagamentos(Connection con, int mes, int ano, ObservableList<Pagamento> listaPagamentos){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT "+
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
                "P.idPagamento, " +
                "P.dataPagamento, " +
                "P.valorFaltas, " +
                "P.valorInss, " +
                "P.valorIrt, " +
                "P.totalDesconto, " +
                "P.bonus, " +
                "P.valorAReceber " +
                "FROM pagamento P " +
                "INNER JOIN funcionario F ON (F.idFuncionario = P.idFuncionario) "+     
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " +
                "WHERE MONTH(P.dataPagamento) = '"+mes+"' and YEAR(P.dataPagamento) = '"+ano+"'" 
                
            );
       
            while(rs.next()){
                listaPagamentos.add(new Pagamento(
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
                    ),
                    rs.getInt("idPagamento"),
                    rs.getDate("dataPagamento"),
                    rs.getDouble("valorFaltas"),
                    rs.getDouble("valorInss"),
                    rs.getDouble("valorIrt"),
                    rs.getDouble("totalDesconto"),
                    rs.getDouble("bonus"),
                    rs.getDouble("valorAReceber")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /* SELECIONAR O PAGAMENTO DE UM FUNCIONARIO PASSADO COMO PARAMETRO, SE O PAGAMENTO DO FUNCIONARIO PASSADO EXISTIR, RETORNA, SE NÂO RETORNA NULL.*/
    public static Pagamento SelecionarPagamentoFuncionario(Connection con, Funcionario funcionario, int mes){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT "+
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
                "P.idPagamento, " +
                "P.dataPagamento, " +
                "P.valorFaltas, " +
                "P.valorInss, " +
                "P.valorIrt, " +
                "P.totalDesconto, " +
                "P.bonus, " +
                "P.valorAReceber " +
                "FROM pagamento P " +
                "INNER JOIN funcionario F ON (F.idFuncionario = P.idFuncionario) "+     
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) "+
                "WHERE P.idFuncionario = "+funcionario.getIdFuncionario()+" "  
            );
       
            while(rs.next()){
                return new Pagamento(
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
                    ),
                    rs.getInt("idPagamento"),
                    rs.getDate("dataPagamento"),
                    rs.getDouble("valorFaltas"),
                    rs.getDouble("valorInss"),
                    rs.getDouble("valorIrt"),
                    rs.getDouble("totalDesconto"),
                    rs.getDouble("bonus"),
                    rs.getDouble("valorAReceber")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    
    
    public static int InserirPagamento(Connection con, Pagamento pagamento){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO pagamento "
                        + "(idPagamento, "
                        + "dataPagamento, "
                        + "valorFaltas, "
                        + "valorInss, "
                        + "valorIrt, "
                        + "totalDesconto, "
                        + "bonus, "
                        + "valorAReceber, "
                        + "idFuncionario) "
                + "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)" 
            );
         
            pstm.setDate(1,pagamento.getDataPagamento());
            pstm.setDouble(2,pagamento.getValorFaltas());
            pstm.setDouble(3,pagamento.getValorINSS());
            pstm.setDouble(4,pagamento.getValorIRT());
            pstm.setDouble(5,pagamento.getValorDescontoTotal());
            pstm.setDouble(6,pagamento.getValorBonus());
            pstm.setDouble(7,pagamento.getValorAReceber());
            pstm.setInt(8,pagamento.getFuncionario().getIdFuncionario());
                       
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    
    public static int EliminarPagamento(Connection con, Pagamento pagamento){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "DELETE FROM pagamento "
                    + "WHERE pagamento.idPagamento = ?"  //
            );
            
            pstm.setInt(1, pagamento.getIdPagamento());
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}

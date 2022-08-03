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
import payrollsys.models.Usuario;

/**
 *
 * @author joshtag096
 */
public class UsuarioOp {
    
    public static void SelecionarUsuario(Connection con, ObservableList<Usuario> listaUsuarios){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT U.idUsuario, "+
                    "U.nomeUsuario, "+
                    "U.senha, "+
                    "U.isAdmin, "+
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
                "FROM usuario U "+
                "INNER JOIN funcionario F ON (U.idFuncionario = F.idFuncionario) "+
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " 
            );
            
            while(rs.next()){
                listaUsuarios.add(new Usuario(
                    rs.getInt("idUsuario"),
                    rs.getString("nomeUsuario"),
                    rs.getString("senha"),
                    rs.getInt("isAdmin"),
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
    
    public static Usuario SelecionarUsuario(Connection con, String username){
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(
                "SELECT U.idUsuario, "+
                    "U.nomeUsuario, "+
                    "U.senha, "+
                    "U.isAdmin, "+
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
                "FROM usuario U "+
                "INNER JOIN funcionario F ON (U.idFuncionario = F.idFuncionario) "+
                "INNER JOIN cargo C ON (C.idCargo = F.idCargo) " +
                "INNER JOIN Turno T ON (T.idTurno = F.idTurno) " +
                "INNER JOIN Departamento D ON (D.idDepartamento = F.idDepartamento) " +
                "WHERE U.nomeUsuario = '"+username+"' "
            );
            
            while(rs.next()){
                return new Usuario(
                    rs.getInt("idUsuario"),
                    rs.getString("nomeUsuario"),
                    rs.getString("senha"),
                    rs.getInt("isAdmin"),
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
    
    public static int InserirUsusario(Connection con, Usuario usuario){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO usuario "
                        + "(idUsuario, "
                        + "nomeUsuario, "
                        + "senha, "
                        + "isAdmin, "
                        + "idFuncionario) "
                + "VALUES (NULL, ?, ?, ?, ?)" 
            );
         
            pstm.setString(1,usuario.getNomeUsuario());
            pstm.setString(2,usuario.getSenhaUsuario());
            pstm.setInt(3,usuario.getAdmin());
            pstm.setInt(4,usuario.getFuncionarioUsuario().getIdFuncionario());
                       
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int ActualizarUsuario(Connection con, Usuario usuario){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "UPDATE usuario SET "
                    + "nomeUsuario = ? ," 
                    + "senha = ? ," 
                    + "isAdmin = ? "
                    + "WHERE usuario.idUsuario = ?"
            );
            
            pstm.setString(1,usuario.getNomeUsuario());
            pstm.setString(2,usuario.getSenhaUsuario());
            pstm.setInt(3,usuario.getAdmin());
            pstm.setInt(4,usuario.getIdUsuario());
            
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public static int EliminarUsuario(Connection con, Usuario usuario){
        try {
            PreparedStatement pstm = con.prepareStatement(
                "DELETE FROM usuario "
                    + "WHERE usuario.idUsuario = ?"  //
            );
            
            pstm.setInt(1, usuario.getIdUsuario());
            return pstm.executeUpdate(); // Caso sucesso retorna 1
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}

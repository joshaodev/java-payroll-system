/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joshtag096
 */
public class Conexao {
    private Connection connection;
    String url = "jdbc:mysql://localhost:3306/payrollsys";
    String usuario = "root";
    String senha = "";
    
    public Conexao(){
        this.estabelecerConexao();
    }
    
    public void estabelecerConexao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Não foi possivel estabelecer a conexão");
            ex.printStackTrace();
        }
    }
    //////
    public ResultSet executSQL(String sql){
        Statement stm;
        ResultSet rs;
        try {
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    ////////
    public void fecharConexao(){
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Não foi possivel fechar a conexão");
            ex.printStackTrace();
        }
    }
   
   public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

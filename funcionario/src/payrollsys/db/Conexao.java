/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payrollsys.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import payrollsys.utils.DynamicViews;

/**
 *
 * @author joshtag096
 */
public class Conexao {
    private Connection connection;
    String url = "jdbc:mysql://localhost/payrollsys";
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
            /*String title = "Banco de dados!";
            String content = "Problema de Conexão, verifica se o servidor está ligado!";
            String header = "Resultado:";*
            DynamicViews.MessageAlert(title, content, header);*/
            System.out.println("Não foi possivel estabelecer a conexão");
            ex.printStackTrace();
        }
    }
    
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

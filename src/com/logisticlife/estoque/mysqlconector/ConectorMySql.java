package com.logisticlife.estoque.mysqlconector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectorMySql {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/estoque";
    private static final String user = "root";
    private static final String senha = "192119";


    public static Connection getConexao (){

        try {
            return DriverManager.getConnection(url, user, senha);
        } catch (SQLException e) {
            System.out.println("Falha na conexão " + e.getMessage());
            return null;
        }


    }

}

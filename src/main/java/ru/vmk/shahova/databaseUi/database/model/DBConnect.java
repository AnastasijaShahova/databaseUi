package ru.vmk.shahova.databaseUi.database.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static Connection conn;
    private static final String url = "jdbc:postgresql://127.0.0.1:5433/postgres";
    private static final String user = "postgres";
    private static final String pass = "postgres";

    public static Connection connect() throws SQLException {
        try{
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException cnfe){
            System.err.println("Error: "+cnfe.getMessage());
        }

        conn = DriverManager.getConnection(url,user,pass);
        return conn;
    }
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(conn !=null && !conn.isClosed())
            return conn;
        connect();
        return conn;

    }
}

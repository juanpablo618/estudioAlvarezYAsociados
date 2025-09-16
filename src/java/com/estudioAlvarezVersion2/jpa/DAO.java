package com.estudioAlvarezVersion2.jpa;

import java.sql.*;

public class DAO {

private Connection cn;

private static final String COMMYSQLJDBC_DRIVER = "com.mysql.jdbc.Driver";
private static final String PASSWORD_SERVER = "BIOeqi72215";
private static final String PASSWORD_SERVER_VACIO = "";
private static final String URL_CONN_DB = "jdbc:mysql://10.100.23.234:3306/estudioAlvarez?useSSL=false";
private static final String URL_CONN_DB_LOCAL = "jdbc:mysql://localhost:3306/estudioAlvarez?useSSL=false";

    public Connection getCn() {
        return cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }
       
    public void Conectar() throws ClassNotFoundException, SQLException{
    
    try {
        
            Class.forName(COMMYSQLJDBC_DRIVER);
            cn = DriverManager.getConnection(URL_CONN_DB);
            
        } catch (ClassNotFoundException | SQLException e) {
        throw e;
            }
    }

    public void Cerrar() throws SQLException{
        if(cn != null){
            if(cn.isClosed() == false){
                cn.close();
            }
        }
    }
    
    public static Connection getConnection() {
		try {
			Class.forName(COMMYSQLJDBC_DRIVER);
			Connection con = DriverManager.getConnection(URL_CONN_DB, "root", PASSWORD_SERVER);
			return con;
		} catch (ClassNotFoundException | SQLException ex) {
			System.out.println("Database.getConnection() Error --> en la clase DAO."
					+ ex.getMessage());
			return null;
		}
    }

	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException ex) {
		}
	}
}
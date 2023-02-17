package com.estudioAlvarezVersion2.jpa;

/**
 *
 * @author cuello.juanpablo@gmail.com
 * http://www.journaldev.com/7252/jsf-authentication-login-logout-database-example
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    private static String rol;

    public static String getRol() {
        return rol;
    }

    public static void setRol(String rol) {
        LoginDAO.rol = rol;
    }
    
	public static boolean validate(String nombre, String password) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DAO.getConnection();
			ps = con.prepareStatement("SELECT  Nombre, password FROM Empleado WHERE Nombre = ? and password = ?;");
			ps.setString(1, nombre);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
                                return true;

			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		} finally {
			DAO.close(con);
		}
		return false;
	}

}


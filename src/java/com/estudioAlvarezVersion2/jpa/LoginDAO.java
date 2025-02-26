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
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
		PreparedStatement ps;

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

     public static void registrarSesionEnBD(String usuario, String sessionId) {
            Connection con = null;
            PreparedStatement ps = null;

            try {
                con = DAO.getConnection();
                String sql = "INSERT INTO sesiones_activas (usuario, session_id, activo) VALUES (?, ?, ?) "
                           + "ON DUPLICATE KEY UPDATE session_id = VALUES(session_id), activo = VALUES(activo)";

                ps = con.prepareStatement(sql);
                ps.setString(1, usuario);
                ps.setString(2, sessionId);
                ps.setBoolean(3, true);
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) ps.close();
                    if (con != null) con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

     public static void marcarSesionComoInactiva(String sessionId) {
                Connection con = null;
                PreparedStatement ps;

                        try {
                                con = DAO.getConnection();

                String sql = "UPDATE sesiones_activas SET activo = false WHERE session_id = ?";
                 ps = con.prepareStatement(sql);
                ps.setString(1, sessionId);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
     
     public static List<Integer> obtenerUsuariosLogueados() {
        List<Integer> usuarios = new ArrayList<>();
        
        Connection con = null;
        PreparedStatement ps;

        try {
                con = DAO.getConnection();

            
            String sql = "SELECT usuario FROM sesiones_activas WHERE activo = true";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuarios.add(rs.getInt("usuario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
     
     public static boolean tieneSesionActiva(String usuario) {
        boolean existe = false;
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT COUNT(*) FROM sesiones_activas WHERE usuario = ? AND activo = true")) {
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                existe = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existe;
    }

    public static void cerrarOtrasSesiones(String usuario ) {
        try (Connection con = DAO.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "DELETE FROM sesiones_activas WHERE usuario = ?")) {
            ps.setString(1, usuario);
            ps.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Se han cerrado las otras sesiones.", 
                "Ahora puedes iniciar sesión."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}


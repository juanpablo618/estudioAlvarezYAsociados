/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String sessionId = event.getSession().getId();
        marcarSesionComoInactiva(sessionId);
        
        
    }

    private void marcarSesionComoInactiva(String sessionId) {
        try (Connection con = DAO.getConnection()) {
            String sql = "UPDATE sesiones_activas SET activo = false WHERE session_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sessionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

   

    
}

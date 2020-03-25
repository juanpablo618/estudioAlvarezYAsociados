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

public class ExpedienteDAO {

    public int devolverUltimoIdDeExpediente() {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DAO.getConnection();
			ps = con.prepareStatement("select * from Expediente order by idExpediente desc limit 1");
			
			ResultSet rs = ps.executeQuery();
                        
                        int id = 0;
                        
			if (rs.next()) {
                                id = rs.getInt(1);
			}
                        
                        rs.close();
                        return id;
                    
		} catch (SQLException ex) {
			System.out.println("devolverUltimoIdDeExpediente error -->" + ex.getMessage());
			
		} finally {
			DAO.close(con);
		}
		return 0;
	}

}








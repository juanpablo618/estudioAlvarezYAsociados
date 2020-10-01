package com.estudioAlvarezVersion2.jpacontroller.util;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */

import com.estudioAlvarezVersion2.jpa.DAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class FileUploadBean implements Serializable{
	
	private static final long serialVersionUID = 4352236420460919694L;
	private UploadedFile file;  
        
    public UploadedFile getFile() {  
        return file;  
    }  
  
    public void setFile(UploadedFile file) {  
        this.file = file;  
    }  
  
    public void upload(int orden) {  
                    System.out.println("llego hasta aca.");
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(file != null){
                    System.out.println("llego hasta aca.2");                
                    if(file.getContentType().equalsIgnoreCase("application/pdf")){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentos (documento, nroDeOrden) " +
                        "VALUES (?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden)");

                        ps.setBinaryStream(1, file.getInputstream());
                        ps.setInt(2, orden);
                        
                        ps.executeUpdate();
                                            System.out.println("llego hasta aca.3");
                        con.close();
                                            System.out.println("llego hasta aca.4");
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + file.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage("Error", "Fichero " + file.getFileName() + " no es un archivo PDF");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    
            }
            System.out.println("llego hasta aca sin pasar por el if.");
            
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + file.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);

        }
        
    }  
    
}
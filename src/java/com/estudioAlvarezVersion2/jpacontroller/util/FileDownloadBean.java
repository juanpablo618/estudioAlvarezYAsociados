package com.estudioAlvarezVersion2.jpacontroller.util;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */

import com.estudioAlvarezVersion2.jpa.DAO;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class FileDownloadBean  implements Serializable{
 
    private static final long serialVersionUID = 626953318628565453L;
    
    private StreamedContent file;
    private int codigo;

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public void download(){
        Connection con = null;
	PreparedStatement ps = null;

        try {
            con = DAO.getConnection();
			ps = con.prepareStatement("SELECT documento FROM documentos WHERE nroDeOrden = (?)");
			ps.setInt(1, codigo);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
                                InputStream stream = rs.getBinaryStream("documento");
                                file = new DefaultStreamedContent(stream, "application/pdf", "documento.pdf");
			}
                        con.close();
                        FacesMessage msg = new FacesMessage("Exito", "archivo descargado exitosamente.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
                   FacesMessage msg = new FacesMessage("Mal", "Fichero no descargado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void download(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        try {
            if(orden != 0){
            con = DAO.getConnection();
			ps = con.prepareStatement("SELECT documento FROM documentos WHERE nroDeOrden = (?) ;");
			ps.setInt(1, orden);
			ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                                InputStream stream = rs.getBinaryStream("documento");
                                file = new DefaultStreamedContent(stream, "application/pdf", "documento.pdf");
			
                        }
                        
                        con.close();
                        if(file != null){
                            FacesMessage msg = new FacesMessage("Exito", "archivo descargado exitosamente.");
                             FacesContext.getCurrentInstance().addMessage(null, msg);

                        }else{
                            FacesMessage msg = new FacesMessage("Mal", "No existe archivo para este nro de orden: "+orden);
                             FacesContext.getCurrentInstance().addMessage(null, msg);

                        }
                        
			
            }else{
                FacesMessage msg = new FacesMessage("Mal", "no se encontro documento pdf con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
        } catch (Exception e) {
                   FacesMessage msg = new FacesMessage("Mal", "Fichero no descargado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
}

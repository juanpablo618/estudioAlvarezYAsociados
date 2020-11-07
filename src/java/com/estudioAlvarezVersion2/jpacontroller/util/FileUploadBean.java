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
        private UploadedFile filePdf;  
        private UploadedFile filePdfDos;  
        private UploadedFile filePdfTres;  
        private UploadedFile filePdfCuatro;  
        private UploadedFile filePdfCinco;  
        
        private UploadedFile file;  
        private UploadedFile fileDos;  
        private UploadedFile fileTres;  
        private UploadedFile fileCuatro;  
        private UploadedFile fileCinco;  
        
        
        
        
    public UploadedFile getFile() {  
        return file;  
    }  
  
    public void setFile(UploadedFile file) {  
        this.file = file;  
    }  

    public UploadedFile getFilePdf() {
        return filePdf;
    }

    public void setFilePdf(UploadedFile filePdf) {
        this.filePdf = filePdf;
    }

    public UploadedFile getFilePdfDos() {
        return filePdfDos;
    }

    public void setFilePdfDos(UploadedFile filePdfDos) {
        this.filePdfDos = filePdfDos;
    }

    public UploadedFile getFilePdfTres() {
        return filePdfTres;
    }

    public void setFilePdfTres(UploadedFile filePdfTres) {
        this.filePdfTres = filePdfTres;
    }

    public UploadedFile getFilePdfCuatro() {
        return filePdfCuatro;
    }

    public void setFilePdfCuatro(UploadedFile filePdfCuatro) {
        this.filePdfCuatro = filePdfCuatro;
    }

    public UploadedFile getFilePdfCinco() {
        return filePdfCinco;
    }

    public void setFilePdfCinco(UploadedFile filePdfCinco) {
        this.filePdfCinco = filePdfCinco;
    }

    public UploadedFile getFileDos() {
        return fileDos;
    }

    public void setFileDos(UploadedFile fileDos) {
        this.fileDos = fileDos;
    }

    public UploadedFile getFileTres() {
        return fileTres;
    }

    public void setFileTres(UploadedFile fileTres) {
        this.fileTres = fileTres;
    }

    public UploadedFile getFileCuatro() {
        return fileCuatro;
    }

    public void setFileCuatro(UploadedFile fileCuatro) {
        this.fileCuatro = fileCuatro;
    }

    public UploadedFile getFileCinco() {
        return fileCinco;
    }

    public void setFileCinco(UploadedFile fileCinco) {
        this.fileCinco = fileCinco;
    }
    
    
    

    public void uploadPDF(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(filePdf != null){
                    
                    if(filePdf.getContentType().equalsIgnoreCase("application/pdf")){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentos (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) " 
                                );

                        ps.setBinaryStream(1, filePdf.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, filePdf.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + filePdf.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage("Error", "Fichero " + filePdf.getFileName() + " no es un archivo PDF o no seleccionó un archivo");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + filePdf.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);

        }
        
    }  

    public void uploadPDFDos(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(filePdfDos != null){
                    
                    if(filePdfDos.getContentType().equalsIgnoreCase("application/pdf")){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentos2 (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) " 
                                );

                        ps.setBinaryStream(1, filePdfDos.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, filePdfDos.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + filePdfDos.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage("Error", "Fichero " + filePdfDos.getFileName() + " no es un archivo PDF o no seleccionó un archivo");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + filePdfDos.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }  

    public void uploadPDFTres(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(filePdfTres != null){
                    
                    if(filePdfTres.getContentType().equalsIgnoreCase("application/pdf")){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentos3 (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) " 
                                );

                        ps.setBinaryStream(1, filePdfTres.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, filePdfTres.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + filePdfTres.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage("Error", "Fichero " + filePdfTres.getFileName() + " no es un archivo PDF o no seleccionó un archivo");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + filePdfTres.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }  

    public void uploadPDFCuatro(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(filePdfCuatro != null){
                    
                    if(filePdfCuatro.getContentType().equalsIgnoreCase("application/pdf")){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentos4 (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) " 
                                );

                        ps.setBinaryStream(1, filePdfCuatro.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, filePdfCuatro.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + filePdfCuatro.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage("Error", "Fichero " + filePdfCuatro.getFileName() + " no es un archivo PDF o no seleccionó un archivo");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + filePdfCuatro.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }  

    public void uploadPDFCinco(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(filePdfCinco != null){
                    
                    if(filePdfCinco.getContentType().equalsIgnoreCase("application/pdf")){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentos5 (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) " 
                                );

                        ps.setBinaryStream(1, filePdfCinco.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, filePdfCinco.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + filePdfCinco.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage("Error", "Fichero " + filePdfCinco.getFileName() + " no es un archivo PDF o no seleccionó un archivo");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + filePdfCinco.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }  
    
    
    public void uploadJPG(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(file != null){
                    
                    if(file.getContentType().equalsIgnoreCase("image/jpeg")){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosjpg (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, file.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, file.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + file.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(file.getFileName())){
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + file.getFileName() + " no es un archivo JPG");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + file.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadJPGDos(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(fileDos != null){
                    
                    if(fileDos.getContentType().equalsIgnoreCase("image/jpeg")){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosjpg2 (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileDos.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileDos.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + fileDos.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileDos.getFileName())){
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + fileDos.getFileName() + " no es un archivo JPG");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + fileDos.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadJPGTres(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(fileTres != null){
                    
                    if(fileTres.getContentType().equalsIgnoreCase("image/jpeg")){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosjpg3 (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileTres.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileTres.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + fileTres.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileTres.getFileName())){
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + fileTres.getFileName() + " no es un archivo JPG");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + fileTres.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadJPGCuatro(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(fileCuatro != null){
                    
                    if(fileCuatro.getContentType().equalsIgnoreCase("image/jpeg")){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosjpg4 (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileCuatro.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileCuatro.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + fileCuatro.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileCuatro.getFileName())){
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + fileCuatro.getFileName() + " no es un archivo JPG");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + fileCuatro.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadJPGCinco(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(fileCinco != null){
                    
                    if(fileCinco.getContentType().equalsIgnoreCase("image/jpeg")){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosjpg5 (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileCinco.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileCinco.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + fileCinco.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileCinco.getFileName())){
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + fileCinco.getFileName() + " no es un archivo JPG");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + fileCinco.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
}
package com.estudioAlvarezVersion2.jpacontroller.util;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */

import com.estudioAlvarezVersion2.jpa.DAO;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class FileUploadBean implements Serializable{
	
	private static final long serialVersionUID = 4352236420460919694L;
        private static final String APPLICATION_PDF = "application/pdf";
        private static final String IMAGE_JPEG = "image/jpeg";
    
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
        
        private UploadedFile fileFrenteDni;  
        private UploadedFile fileDorsoDni;  
        private UploadedFile fileOtraDocumentacion;  
        private UploadedFile fileCronoDeAportes;  
        private UploadedFile frenteDniExpSinCarpeta;  
        private UploadedFile dorsoDniExpSinCarpeta;  

        private StreamedContent frenteDniExpSinCarpetaTransfer;
        private StreamedContent dorsoDniExpSinCarpetaTransfer;

    public StreamedContent getFrenteDniExpSinCarpetaTransfer() {
        return frenteDniExpSinCarpetaTransfer;
    }

    public void setFrenteDniExpSinCarpetaTransfer(StreamedContent frenteDniExpSinCarpetaTransfer) {
        this.frenteDniExpSinCarpetaTransfer = frenteDniExpSinCarpetaTransfer;
    }

    public StreamedContent getDorsoDniExpSinCarpetaTransfer() {
        return dorsoDniExpSinCarpetaTransfer;
    }

    public void setDorsoDniExpSinCarpetaTransfer(StreamedContent dorsoDniExpSinCarpetaTransfer) {
        this.dorsoDniExpSinCarpetaTransfer = dorsoDniExpSinCarpetaTransfer;
    }
    
        
        
        
    public UploadedFile getFrenteDniExpSinCarpeta() {
        return frenteDniExpSinCarpeta;
    }

    public void setFrenteDniExpSinCarpeta(UploadedFile frenteDniExpSinCarpeta) {
        this.frenteDniExpSinCarpeta = frenteDniExpSinCarpeta;
    }

    public UploadedFile getDorsoDniExpSinCarpeta() {
        return dorsoDniExpSinCarpeta;
    }

    public void setDorsoDniExpSinCarpeta(UploadedFile dorsoDniExpSinCarpeta) {
        this.dorsoDniExpSinCarpeta = dorsoDniExpSinCarpeta;
    }

    
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

    public UploadedFile getFileFrenteDni() {
        return fileFrenteDni;
    }

    public void setFileFrenteDni(UploadedFile fileFrenteDni) {
        this.fileFrenteDni = fileFrenteDni;
    }

    public UploadedFile getFileDorsoDni() {
        return fileDorsoDni;
    }

    public void setFileDorsoDni(UploadedFile fileDorsoDni) {
        this.fileDorsoDni = fileDorsoDni;
    }

    public UploadedFile getFileOtraDocumentacion() {
        return fileOtraDocumentacion;
    }

    public void setFileOtraDocumentacion(UploadedFile fileOtraDocumentacion) {
        this.fileOtraDocumentacion = fileOtraDocumentacion;
    }

    public UploadedFile getFileCronoDeAportes() {
        return fileCronoDeAportes;
    }

    public void setFileCronoDeAportes(UploadedFile fileCronoDeAportes) {
        this.fileCronoDeAportes = fileCronoDeAportes;
    }
    
    
    
// El nuevo requerimiento indica que se puedan subir JPG o PDF indistintamente 
// desde cualquier parte , las tablas de la DB NO van a cambiar los nombres.    
// Todas las tablas van a guardar jpg o pdf.
    
    public void uploadCronoDeAportes(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(fileCronoDeAportes != null){
                    System.out.println("ACAAA: "+fileCronoDeAportes.getContentType());
                    if(fileCronoDeAportes.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") || fileCronoDeAportes.getContentType().equalsIgnoreCase("application/vnd.ms-excel")){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentoscronodeaportes (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) " 
                                );

                        ps.setBinaryStream(1, fileCronoDeAportes.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileCronoDeAportes.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + fileCronoDeAportes.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage("Error", "Fichero " + fileCronoDeAportes.getFileName() + " no es un archivo xls/xlsx o no seleccionó un archivo");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + fileCronoDeAportes.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);

        }
        
    }  

    
    public void uploadPDF(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(filePdf != null){
                    
                    if(filePdf.getContentType().equalsIgnoreCase(APPLICATION_PDF) || filePdf.getContentType().equalsIgnoreCase(IMAGE_JPEG)){
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
                    
                    if(filePdfDos.getContentType().equalsIgnoreCase(APPLICATION_PDF) || filePdfDos.getContentType().equalsIgnoreCase(IMAGE_JPEG)){
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
                    
                    if(filePdfTres.getContentType().equalsIgnoreCase(APPLICATION_PDF) || filePdfTres.getContentType().equalsIgnoreCase(IMAGE_JPEG)){
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
                    
                    if(filePdfCuatro.getContentType().equalsIgnoreCase(APPLICATION_PDF) || filePdfCuatro.getContentType().equalsIgnoreCase(IMAGE_JPEG)){
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
                    
                    if(filePdfCinco.getContentType().equalsIgnoreCase(APPLICATION_PDF) || filePdfCinco.getContentType().equalsIgnoreCase(IMAGE_JPEG)){
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
    
    
    public void uploadDorsoDni(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(fileDorsoDni != null){
                    
                    if(fileDorsoDni.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileDorsoDni.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosDorsoDni (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileDorsoDni.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileDorsoDni.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + fileDorsoDni.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileDorsoDni.getFileName())){
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + fileDorsoDni.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + fileDorsoDni.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadFrenteDni(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(fileFrenteDni != null){
                    
                    if(fileFrenteDni.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileFrenteDni.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosFrenteDni (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileFrenteDni.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileFrenteDni.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + fileFrenteDni.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileFrenteDni.getFileName())){
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG o PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + fileFrenteDni.getFileName() + " no es un archivo JPG o PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + fileFrenteDni.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadFrenteDni(int orden, String cuit ) {  
                    
        try {
            
            long cuitLong = 0;
            
            if (!"0".equals(cuit) && cuit != null && !"".equals(cuit))  {
                cuitLong = Long.parseLong(cuit);
            } 
            
            Connection con = null;
		PreparedStatement ps = null;

                        con = DAO.getConnection();
                        
                        ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM frenteDniExpSinCarpeta WHERE nroDeCuit = (?);");
                ps.setLong(1, cuitLong);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        frenteDniExpSinCarpetaTransfer = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        frenteDniExpSinCarpetaTransfer = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                        ps = con.prepareStatement("INSERT INTO documentosFrenteDni (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, frenteDniExpSinCarpetaTransfer.getStream());
                        ps.setInt(2, orden);
                        ps.setString(3, frenteDniExpSinCarpetaTransfer.getName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                    
        } catch (SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + frenteDniExpSinCarpetaTransfer.getName()+ " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    
    public void uploadOtraDocumentacion(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(fileOtraDocumentacion != null){
                    
                    if(fileOtraDocumentacion.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileOtraDocumentacion.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosOtraDocumentacion (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileOtraDocumentacion.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileOtraDocumentacion.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + fileOtraDocumentacion.getFileName() + " subido correctamente. Con nro de Orden: "+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileOtraDocumentacion.getFileName())){
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + fileOtraDocumentacion.getFileName() + " no es un archivo JPG O PDF");
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
    
    public void uploadFrenteDniParaExpSinCarpeta(String cuit) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

                
            long cuitLong = 0;
            
            if (!"0".equals(cuit) && cuit != null && !"".equals(cuit))  {
                cuitLong = Long.parseLong(cuit);
            }  
                
            if(frenteDniExpSinCarpeta != null){
                    
                    if(frenteDniExpSinCarpeta.getContentType().equalsIgnoreCase(IMAGE_JPEG) || frenteDniExpSinCarpeta.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO frenteDniExpSinCarpeta (documento, nroDeCuit, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeCuit = LAST_INSERT_ID(nroDeCuit),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, frenteDniExpSinCarpeta.getInputstream());
                        ps.setLong(2, cuitLong);
                        ps.setString(3, frenteDniExpSinCarpeta.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + frenteDniExpSinCarpeta.getFileName() + " subido correctamente. Con nro de cuit: "+cuitLong);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(frenteDniExpSinCarpeta.getFileName())){
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + frenteDniExpSinCarpeta.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + frenteDniExpSinCarpeta.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadDorsoDniParaExpSinCarpeta(String cuit) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

                
            long cuitLong = 0;
            
            if (!"0".equals(cuit) && cuit != null && !"".equals(cuit))  {
                cuitLong = Long.parseLong(cuit);
            }  
                
            if(dorsoDniExpSinCarpeta != null){
                    
                    if(dorsoDniExpSinCarpeta.getContentType().equalsIgnoreCase(IMAGE_JPEG) || dorsoDniExpSinCarpeta.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO dorsoDniExpSinCarpeta (documento, nroDeCuit, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeCuit = LAST_INSERT_ID(nroDeCuit),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, dorsoDniExpSinCarpeta.getInputstream());
                        ps.setLong(2, cuitLong);
                        ps.setString(3, dorsoDniExpSinCarpeta.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage("Ok", "Fichero " + dorsoDniExpSinCarpeta.getFileName() + " subido correctamente. Con nro de cuit: "+cuitLong);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(dorsoDniExpSinCarpeta.getFileName())){
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + dorsoDniExpSinCarpeta.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
            System.out.println("ERROR: "+e.toString());
                        FacesMessage msg = new FacesMessage("Error", " Fichero demasiado grande " + dorsoDniExpSinCarpeta.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
   
    public void uploadJPG(int orden) {  
                    
        try {
            Connection con = null;
		PreparedStatement ps = null;

            if(file != null){
                    
                    if(file.getContentType().equalsIgnoreCase(IMAGE_JPEG) || file.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
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
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + file.getFileName() + " no es un archivo JPG O PDF");
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
                    
                    if(fileDos.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileDos.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
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
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + fileDos.getFileName() + " no es un archivo JPG O PDF");
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
                    
                    if(fileTres.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileTres.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
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
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + fileTres.getFileName() + " no es un archivo JPG O PDF");
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
                    
                    if(fileCuatro.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileCuatro.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
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
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + fileCuatro.getFileName() + " no es un archivo JPG O PDF");
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
                    
                    if(fileCinco.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileCinco.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
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
                            FacesMessage msg = new FacesMessage("Error", "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "Fichero " + fileCinco.getFileName() + " no es un archivo JPG O PDF");
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

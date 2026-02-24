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
        private static final String ERROR = "Error";
        private static final String OK = "Ok";
        private static final String FICHERO_DEMASIADO_GRANDE = " Fichero demasiado grande ";
        private static final String SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN = " subido correctamente. Con nro de Orden: ";

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
        private UploadedFile fileDemanda;
        private UploadedFile fileDemandaDos;
        
        private UploadedFile fileOtraDocumentacion; 
        private UploadedFile fileOtraDocumentacionDos; 
        private UploadedFile fileOtraDocumentacionTres; 
        private UploadedFile fileOtraDocumentacionCuatro; 
        private UploadedFile fileOtraDocumentacionCinco; 
        private UploadedFile fileOtraDocumentacionSeis;
        
        private UploadedFile fileCartaPoder;  
        private UploadedFile fileExpAdministrativo;
        private UploadedFile fileExpAdministrativoDos;
        
        private UploadedFile fileRecibos;
        private UploadedFile fileRecibosDos;
        private UploadedFile fileRecibosTres;
        private UploadedFile fileRecibosCuatro;
        
        private UploadedFile fileLiquidacionBlueCorp;
        private UploadedFile fileLiquidacionBlueCorpDos;
        private UploadedFile fileLiquidacionBlueCorpTres;
        
        private UploadedFile fileResolucionDenegatoria;
        private UploadedFile fileConvenioDeHonorarios;
        private UploadedFile fileConstanciaDeCbu;
        
        private UploadedFile fileCronoDeAportes;
        private UploadedFile fileCronoDeAportesDos;
        
        private UploadedFile frenteDniExpSinCarpeta;  
        private UploadedFile dorsoDniExpSinCarpeta;  

        private StreamedContent frenteDniExpSinCarpetaTransfer;
        private StreamedContent dorsoDniExpSinCarpetaTransfer;
        
        private UploadedFile fileHistorialLaboralAnses ;
        
        private UploadedFile fileHistorialLaboralOtrasCajas;
        private UploadedFile fileHistorialLaboralOtrasCajasDos;
        
        private UploadedFile fileBoletaDeAportes;
        private UploadedFile fileBoletaDeAportesDos;
        
        private UploadedFile fileAfipDatosPersonalesPagosOtros ;
        private UploadedFile fileAfipDatosPersonalesPagosOtrosDos ;
        private UploadedFile fileAfipDatosPersonalesPagosOtrosTres ;
        

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

    public UploadedFile getFileRecibos() {
        return fileRecibos;
    }

    public void setFileRecibos(UploadedFile fileRecibos) {
        this.fileRecibos = fileRecibos;
    }

    public UploadedFile getFileRecibosDos() {
        return fileRecibosDos;
    }

    public void setFileRecibosDos(UploadedFile fileRecibosDos) {
        this.fileRecibosDos = fileRecibosDos;
    }

    public UploadedFile getFileRecibosTres() {
        return fileRecibosTres;
    }

    public void setFileRecibosTres(UploadedFile fileRecibosTres) {
        this.fileRecibosTres = fileRecibosTres;
    }

    public UploadedFile getFileRecibosCuatro() {
        return fileRecibosCuatro;
    }

    public void setFileRecibosCuatro(UploadedFile fileRecibosCuatro) {
        this.fileRecibosCuatro = fileRecibosCuatro;
    }
    
    

    public UploadedFile getFileLiquidacionBlueCorp() {
        return fileLiquidacionBlueCorp;
    }

    public void setFileLiquidacionBlueCorp(UploadedFile fileLiquidacionBlueCorp) {
        this.fileLiquidacionBlueCorp = fileLiquidacionBlueCorp;
    }

    public UploadedFile getFileResolucionDenegatoria() {
        return fileResolucionDenegatoria;
    }

    public void setFileResolucionDenegatoria(UploadedFile fileResolucionDenegatoria) {
        this.fileResolucionDenegatoria = fileResolucionDenegatoria;
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

    public UploadedFile getFileDemanda() {
        return fileDemanda;
    }

    public void setFileDemanda(UploadedFile fileDemanda) {
        this.fileDemanda = fileDemanda;
    }

    public UploadedFile getFileDemandaDos() {
        return fileDemandaDos;
    }

    public void setFileDemandaDos(UploadedFile fileDemandaDos) {
        this.fileDemandaDos = fileDemandaDos;
    }

    public UploadedFile getFileOtraDocumentacion() {
        return fileOtraDocumentacion;
    }

    public void setFileOtraDocumentacion(UploadedFile fileOtraDocumentacion) {
        this.fileOtraDocumentacion = fileOtraDocumentacion;
    }

    public UploadedFile getFileCartaPoder() {
        return fileCartaPoder;
    }

    public void setFileCartaPoder(UploadedFile fileCartaPoder) {
        this.fileCartaPoder = fileCartaPoder;
    }

    public UploadedFile getFileExpAdministrativo() {
        return fileExpAdministrativo;
    }

    public void setFileExpAdministrativo(UploadedFile fileExpAdministrativo) {
        this.fileExpAdministrativo = fileExpAdministrativo;
    }

    public UploadedFile getFileExpAdministrativoDos() {
        return fileExpAdministrativoDos;
    }

    public void setFileExpAdministrativoDos(UploadedFile fileExpAdministrativoDos) {
        this.fileExpAdministrativoDos = fileExpAdministrativoDos;
    }
    
    
    
    public UploadedFile getFileCronoDeAportes() {
        return fileCronoDeAportes;
    }

    public void setFileCronoDeAportes(UploadedFile fileCronoDeAportes) {
        this.fileCronoDeAportes = fileCronoDeAportes;
    }

    public UploadedFile getFileCronoDeAportesDos() {
        return fileCronoDeAportesDos;
    }

    public void setFileCronoDeAportesDos(UploadedFile fileCronoDeAportesDos) {
        this.fileCronoDeAportesDos = fileCronoDeAportesDos;
    }
    
    

    public UploadedFile getFileOtraDocumentacionDos() {
        return fileOtraDocumentacionDos;
    }

    public void setFileOtraDocumentacionDos(UploadedFile fileOtraDocumentacionDos) {
        this.fileOtraDocumentacionDos = fileOtraDocumentacionDos;
    }

    public UploadedFile getFileOtraDocumentacionTres() {
        return fileOtraDocumentacionTres;
    }

    public void setFileOtraDocumentacionTres(UploadedFile fileOtraDocumentacionTres) {
        this.fileOtraDocumentacionTres = fileOtraDocumentacionTres;
    }

    public UploadedFile getFileOtraDocumentacionCuatro() {
        return fileOtraDocumentacionCuatro;
    }

    public void setFileOtraDocumentacionCuatro(UploadedFile fileOtraDocumentacionCuatro) {
        this.fileOtraDocumentacionCuatro = fileOtraDocumentacionCuatro;
    }

    public UploadedFile getFileOtraDocumentacionCinco() {
        return fileOtraDocumentacionCinco;
    }

    public void setFileOtraDocumentacionCinco(UploadedFile fileOtraDocumentacionCinco) {
        this.fileOtraDocumentacionCinco = fileOtraDocumentacionCinco;
    }

    public UploadedFile getFileOtraDocumentacionSeis() {
        return fileOtraDocumentacionSeis;
    }

    public void setFileOtraDocumentacionSeis(UploadedFile fileOtraDocumentacionSeis) {
        this.fileOtraDocumentacionSeis = fileOtraDocumentacionSeis;
    }
    
    

    public UploadedFile getFileConvenioDeHonorarios() {
        return fileConvenioDeHonorarios;
    }

    public void setFileConvenioDeHonorarios(UploadedFile fileConvenioDeHonorarios) {
        this.fileConvenioDeHonorarios = fileConvenioDeHonorarios;
    }

    public UploadedFile getFileConstanciaDeCbu() {
        return fileConstanciaDeCbu;
    }

    public void setFileConstanciaDeCbu(UploadedFile fileConstanciaDeCbu) {
        this.fileConstanciaDeCbu = fileConstanciaDeCbu;
    }

    public UploadedFile getFileLiquidacionBlueCorpDos() {
        return fileLiquidacionBlueCorpDos;
    }

    public void setFileLiquidacionBlueCorpDos(UploadedFile fileLiquidacionBlueCorpDos) {
        this.fileLiquidacionBlueCorpDos = fileLiquidacionBlueCorpDos;
    }

    public UploadedFile getFileLiquidacionBlueCorpTres() {
        return fileLiquidacionBlueCorpTres;
    }

    public void setFileLiquidacionBlueCorpTres(UploadedFile fileLiquidacionBlueCorpTres) {
        this.fileLiquidacionBlueCorpTres = fileLiquidacionBlueCorpTres;
    }

    public UploadedFile getFileHistorialLaboralAnses() {
        return fileHistorialLaboralAnses;
    }

    public void setFileHistorialLaboralAnses(UploadedFile fileHistorialLaboralAnses) {
        this.fileHistorialLaboralAnses = fileHistorialLaboralAnses;
    }

    public UploadedFile getFileHistorialLaboralOtrasCajas() {
        return fileHistorialLaboralOtrasCajas;
    }

    public void setFileHistorialLaboralOtrasCajas(UploadedFile fileHistorialLaboralOtrasCajas) {
        this.fileHistorialLaboralOtrasCajas = fileHistorialLaboralOtrasCajas;
    }

    public UploadedFile getFileHistorialLaboralOtrasCajasDos() {
        return fileHistorialLaboralOtrasCajasDos;
    }

    public void setFileHistorialLaboralOtrasCajasDos(UploadedFile fileHistorialLaboralOtrasCajasDos) {
        this.fileHistorialLaboralOtrasCajasDos = fileHistorialLaboralOtrasCajasDos;
    }

    public UploadedFile getFileBoletaDeAportes() {
        return fileBoletaDeAportes;
    }

    public void setFileBoletaDeAportes(UploadedFile fileBoletaDeAportes) {
        this.fileBoletaDeAportes = fileBoletaDeAportes;
    }

    public UploadedFile getFileBoletaDeAportesDos() {
        return fileBoletaDeAportesDos;
    }

    public void setFileBoletaDeAportesDos(UploadedFile fileBoletaDeAportesDos) {
        this.fileBoletaDeAportesDos = fileBoletaDeAportesDos;
    }
    
    public UploadedFile getFileAfipDatosPersonalesPagosOtros() {
        return fileAfipDatosPersonalesPagosOtros;
    }

    public void setFileAfipDatosPersonalesPagosOtros(UploadedFile fileAfipDatosPersonalesPagosOtros) {
        this.fileAfipDatosPersonalesPagosOtros = fileAfipDatosPersonalesPagosOtros;
    }

    public UploadedFile getFileAfipDatosPersonalesPagosOtrosDos() {
        return fileAfipDatosPersonalesPagosOtrosDos;
    }

    public void setFileAfipDatosPersonalesPagosOtrosDos(UploadedFile fileAfipDatosPersonalesPagosOtrosDos) {
        this.fileAfipDatosPersonalesPagosOtrosDos = fileAfipDatosPersonalesPagosOtrosDos;
    }

    public UploadedFile getFileAfipDatosPersonalesPagosOtrosTres() {
        return fileAfipDatosPersonalesPagosOtrosTres;
    }

    public void setFileAfipDatosPersonalesPagosOtrosTres(UploadedFile fileAfipDatosPersonalesPagosOtrosTres) {
        this.fileAfipDatosPersonalesPagosOtrosTres = fileAfipDatosPersonalesPagosOtrosTres;
    }

    
    
    
// El nuevo requerimiento indica que se puedan subir JPG o PDF indistintamente 
// desde cualquier parte , las tablas de la DB NO van a cambiar los nombres.    
// Todas las tablas van a guardar jpg o pdf.
    
    public void uploadCronoDeAportes(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

                    if(fileCronoDeAportes != null){
                    if (fileCronoDeAportes.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                        fileCronoDeAportes.getContentType().equalsIgnoreCase("application/vnd.ms-excel") ||
                        fileCronoDeAportes.getContentType().equalsIgnoreCase("application/vnd.ms-excel.sheet.macroEnabled.12") ||
                        fileCronoDeAportes.getContentType().equalsIgnoreCase("application/octet-stream")) {

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileCronoDeAportes.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileCronoDeAportes.getFileName() + " no es un archivo xls/xlsx o no seleccionó un archivo");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileCronoDeAportes.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);

        }
        
    }  

    public void uploadCronoDeAportesDos(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileCronoDeAportesDos != null){
               if (fileCronoDeAportesDos.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                        fileCronoDeAportesDos.getContentType().equalsIgnoreCase("application/vnd.ms-excel") ||
                        fileCronoDeAportesDos.getContentType().equalsIgnoreCase("application/vnd.ms-excel.sheet.macroEnabled.12") ||
                        fileCronoDeAportesDos.getContentType().equalsIgnoreCase("application/octet-stream")) {

                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentoscronodeaportesdos (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) " 
                                );

                        ps.setBinaryStream(1, fileCronoDeAportesDos.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileCronoDeAportesDos.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileCronoDeAportesDos.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileCronoDeAportesDos.getFileName().concat(" no es un archivo xls/xlsx o no seleccionó un archivo"));
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileCronoDeAportesDos.getFileName().concat(" por favor seleccione otro."));
                        FacesContext.getCurrentInstance().addMessage(null, msg);

        }
        
    }  

    
    public void uploadPDF(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + filePdf.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage(ERROR, "Fichero " + filePdf.getFileName() + " no es un archivo PDF o no seleccionó un archivo");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + filePdf.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);

        }
        
    }  

    public void uploadPDFDos(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + filePdfDos.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage(ERROR, "Fichero " + filePdfDos.getFileName() + " no es un archivo PDF o no seleccionó un archivo");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + filePdfDos.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }  

    public void uploadPDFTres(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + filePdfTres.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage(ERROR, "Fichero " + filePdfTres.getFileName() + " no es un archivo PDF o no seleccionó un archivo");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + filePdfTres.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }  

    public void uploadPDFCuatro(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + filePdfCuatro.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage(ERROR, "Fichero " + filePdfCuatro.getFileName() + " no es un archivo PDF o no seleccionó un archivo");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + filePdfCuatro.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }  

    public void uploadPDFCinco(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + filePdfCinco.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        
                    
                    }else{
                        FacesMessage msg = new FacesMessage(ERROR, "Fichero " + filePdfCinco.getFileName() + " no es un archivo PDF o no seleccionó un archivo");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + filePdfCinco.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }  
    
    public void uploadDorsoDni(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileDorsoDni.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileDorsoDni.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileDorsoDni.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileDorsoDni.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadFrenteDni(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileFrenteDni.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileFrenteDni.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG o PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileFrenteDni.getFileName() + " no es un archivo JPG o PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileFrenteDni.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadFrenteDni(int orden, String cuit ) {  
                    
        try {
            
            long cuitLong = 0;
            
            if (!"0".equals(cuit) && cuit != null && !"".equals(cuit))  {
                cuitLong = Long.parseLong(cuit);
            } 
            
            Connection con;
            PreparedStatement ps;

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
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + frenteDniExpSinCarpetaTransfer.getName()+ " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  


    private String obtenerTablaDemandas(Connection con) {
        String tabla = "documentosDemandas";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT table_name FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name IN ('documentosDemandas','documentosDemanda') ORDER BY CASE table_name WHEN 'documentosDemandas' THEN 1 ELSE 2 END LIMIT 1");
            rs = ps.executeQuery();
            if (rs.next()) {
                tabla = rs.getString(1);
            }
        } catch (SQLException e) {
            tabla = "documentosDemandas";
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                // ignore
            }
        }
        return tabla;
    }

    public void uploadDemanda(int orden) {

        try {
            Connection con;
            PreparedStatement ps;

            if (fileDemanda != null) {

                if (fileDemanda.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileDemanda.getContentType().equalsIgnoreCase(APPLICATION_PDF)) {
                    con = DAO.getConnection();
                    String tablaDemandas = obtenerTablaDemandas(con);
                    ps = con.prepareStatement("INSERT INTO " + tablaDemandas + " (documento, nroDeOrden, nombreDelDocumento, numeroDocumento) "
                            + "VALUES (?, ?, ?, ?) "
                            + "ON DUPLICATE KEY UPDATE "
                            + "documento = VALUES(documento), "
                            + "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"
                            + "nombreDelDocumento = VALUES(nombreDelDocumento) ");

                    ps.setBinaryStream(1, fileDemanda.getInputstream());
                    ps.setInt(2, orden);
                    ps.setString(3, fileDemanda.getFileName());
                    ps.setInt(4, 1);

                    ps.executeUpdate();
                    con.close();

                    FacesMessage msg = new FacesMessage(OK, "Fichero " + fileDemanda.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    if (!"".equals(fileDemanda.getFileName())) {
                        FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG o PDF");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileDemanda.getFileName() + " no es un archivo JPG o PDF");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }

            }

        } catch (IOException | SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileDemanda.getFileName() + " por favor seleccione otro.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void uploadDemandaDos(int orden) {

        try {
            Connection con;
            PreparedStatement ps;

            if (fileDemandaDos != null) {

                if (fileDemandaDos.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileDemandaDos.getContentType().equalsIgnoreCase(APPLICATION_PDF)) {
                    con = DAO.getConnection();
                    String tablaDemandas = obtenerTablaDemandas(con);
                    ps = con.prepareStatement("INSERT INTO " + tablaDemandas + " (documento, nroDeOrden, nombreDelDocumento, numeroDocumento) "
                            + "VALUES (?, ?, ?, ?) "
                            + "ON DUPLICATE KEY UPDATE "
                            + "documento = VALUES(documento), "
                            + "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"
                            + "nombreDelDocumento = VALUES(nombreDelDocumento) ");

                    ps.setBinaryStream(1, fileDemandaDos.getInputstream());
                    ps.setInt(2, orden);
                    ps.setString(3, fileDemandaDos.getFileName());
                    ps.setInt(4, 2);

                    ps.executeUpdate();
                    con.close();

                    FacesMessage msg = new FacesMessage(OK, "Fichero " + fileDemandaDos.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    if (!"".equals(fileDemandaDos.getFileName())) {
                        FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG o PDF");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileDemandaDos.getFileName() + " no es un archivo JPG o PDF");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }

            }

        } catch (IOException | SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileDemandaDos.getFileName() + " por favor seleccione otro.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }
    
    public void uploadOtraDocumentacion(int orden, int numeroDocumento) {
    Connection con = null;
    PreparedStatement ps = null;
    UploadedFile currentFile = null ;

    try {
        
        
        switch (numeroDocumento) {
            case 1:
                currentFile = fileOtraDocumentacion;
                break;
            case 2:
                currentFile = fileOtraDocumentacionDos;
                break;
            case 3:
                currentFile = fileOtraDocumentacionTres;
                break;
            default:
                // Manejar el caso en el que el número de documento no sea válido
                FacesMessage msg = new FacesMessage(ERROR, "Número de documento no válido: " + numeroDocumento);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
        }
        
        if (currentFile != null && numeroDocumento >= 1 && numeroDocumento <= 3) {
            if (currentFile.getContentType().equalsIgnoreCase(IMAGE_JPEG) || currentFile.getContentType().equalsIgnoreCase(APPLICATION_PDF)) {
                con = DAO.getConnection();
                ps = con.prepareStatement("INSERT INTO documentosOtraDocumentacion (documento, nroDeOrden, nombreDelDocumento, numeroDocumento) " +
                        "VALUES (?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "documento = VALUES(documento), " +
                        "nroDeOrden = LAST_INSERT_ID(nroDeOrden)," +
                        "nombreDelDocumento = VALUES(nombreDelDocumento), " +
                        "numeroDocumento = VALUES(numeroDocumento)"
                );

                ps.setBinaryStream(1, currentFile.getInputstream());
                ps.setInt(2, orden);
                ps.setString(3, currentFile.getFileName());
                ps.setInt(4, numeroDocumento);

                ps.executeUpdate();
                FacesMessage msg = new FacesMessage(OK, "Fichero " + currentFile.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN + orden + " y Número de Documento " + numeroDocumento);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                if (!"".equals(currentFile.getFileName())) {
                    FacesMessage msg = new FacesMessage(ERROR, "No seleccionó un archivo JPG o PDF para el documento " + numeroDocumento);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "Fichero " + currentFile.getFileName() + " no es un archivo JPG o PDF para el documento " + numeroDocumento);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    } catch (IOException | SQLException e) {
        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + currentFile.getFileName() + " por favor seleccione otro.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            // Manejar la excepción al cerrar la conexión
            e.printStackTrace();
        }
    }
}

    /*public void uploadBlueCorp(int orden, int numeroDocumento) {
    Connection con = null;
    PreparedStatement ps = null;
    UploadedFile currentFile = null;

    try {
        
         
        switch (numeroDocumento) {
            case 1:
                currentFile = fileLiquidacionBlueCorp;
                break;
            case 2:
                currentFile = fileLiquidacionBlueCorpDos;
                break;
            case 3:
                currentFile = fileLiquidacionBlueCorpTres;
                break;
            default:
                // Manejar el caso en el que el número de documento no sea válido
                FacesMessage msg = new FacesMessage(ERROR, "Número de documento no válido: " + numeroDocumento);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
        }
        
        if (currentFile != null && numeroDocumento >= 1 && numeroDocumento <= 3) {
            if (currentFile.getContentType().equalsIgnoreCase(IMAGE_JPEG) || currentFile.getContentType().equalsIgnoreCase(APPLICATION_PDF)) {
                con = DAO.getConnection();
                ps = con.prepareStatement("INSERT INTO documentosliquidacionbluecorp (documento, nroDeOrden, nombreDelDocumento, numeroDocumento) " +
                        "VALUES (?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "documento = VALUES(documento), " +
                        "nroDeOrden = LAST_INSERT_ID(nroDeOrden)," +
                        "nombreDelDocumento = VALUES(nombreDelDocumento), " +
                        "numeroDocumento = VALUES(numeroDocumento)"
                );

                ps.setBinaryStream(1, currentFile.getInputstream());
                ps.setInt(2, orden);
                ps.setString(3, currentFile.getFileName());
                ps.setInt(4, numeroDocumento);

                ps.executeUpdate();
                FacesMessage msg = new FacesMessage(OK, "Fichero " + currentFile.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN + orden + " y Número de Documento " + numeroDocumento);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                if (!"".equals(currentFile.getFileName())) {
                    FacesMessage msg = new FacesMessage(ERROR, "No seleccionó un archivo JPG o PDF para el documento " + numeroDocumento);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "Fichero " + currentFile.getFileName() + " no es un archivo JPG o PDF para el documento " + numeroDocumento);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    } catch (IOException | SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + currentFile.getFileName() + " por favor seleccione otro.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            // Manejar la excepción al cerrar la conexión
            e.printStackTrace();
        }
    }
}*/

    
    public void uploadOtraDocumentacion(int orden) {  
                    
        try {
            Connection con;
            PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileOtraDocumentacion.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileOtraDocumentacion.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileOtraDocumentacion.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileOtraDocumentacion.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadOtraDocumentacionDos(int orden) {  
                    
        try {
            Connection con;
            PreparedStatement ps;

            if(fileOtraDocumentacionDos != null){
                    
                    if(fileOtraDocumentacionDos.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileOtraDocumentacionDos.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosOtraDocumentacionDos (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileOtraDocumentacionDos.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileOtraDocumentacionDos.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileOtraDocumentacionDos.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileOtraDocumentacionDos.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileOtraDocumentacionDos.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileOtraDocumentacionDos.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadOtraDocumentacionTres(int orden) {  
                    
        try {
            Connection con;
            PreparedStatement ps;

            if(fileOtraDocumentacionTres != null){
                    
                    if(fileOtraDocumentacionTres.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileOtraDocumentacionTres.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosOtraDocumentacionTres (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileOtraDocumentacionTres.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileOtraDocumentacionTres.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileOtraDocumentacionTres.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileOtraDocumentacionTres.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileOtraDocumentacionTres.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileOtraDocumentacionTres.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadOtraDocumentacionCuatro(int orden) {  
                    
        try {
            Connection con;
            PreparedStatement ps;

            if(fileOtraDocumentacionCuatro != null){
                    
                    if(fileOtraDocumentacionCuatro.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileOtraDocumentacionCuatro.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosOtraDocumentacionCuatro (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileOtraDocumentacionCuatro.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileOtraDocumentacionCuatro.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileOtraDocumentacionCuatro.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileOtraDocumentacionCuatro.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileOtraDocumentacionCuatro.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileOtraDocumentacionCuatro.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public void uploadOtraDocumentacionCinco(int orden) {  
                    
        try {
            Connection con;
            PreparedStatement ps;

            if(fileOtraDocumentacionCinco != null){
                    
                    if(fileOtraDocumentacionCinco.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileOtraDocumentacionCinco.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosOtraDocumentacionCinco (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileOtraDocumentacionCinco.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileOtraDocumentacionCinco.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileOtraDocumentacionCinco.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileOtraDocumentacionCinco.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileOtraDocumentacionCinco.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileOtraDocumentacionCinco.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public void uploadOtraDocumentacionSeis(int orden) {  
                    
        try {
            Connection con;
            PreparedStatement ps;

            if(fileOtraDocumentacionSeis != null){
                    
                    if(fileOtraDocumentacionSeis.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileOtraDocumentacionSeis.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosOtraDocumentacionSeis (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileOtraDocumentacionSeis.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileOtraDocumentacionSeis.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileOtraDocumentacionSeis.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileOtraDocumentacionSeis.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileOtraDocumentacionSeis.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileOtraDocumentacionSeis.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    
    public void uploadHistorialLaboralAnses(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileHistorialLaboralAnses != null){
                    
                    if(fileHistorialLaboralAnses.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileHistorialLaboralAnses.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosHistorialLaboralAnses (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileHistorialLaboralAnses.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileHistorialLaboralAnses.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileHistorialLaboralAnses.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileHistorialLaboralAnses.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileHistorialLaboralAnses.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileHistorialLaboralAnses.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    
    public void uploadHistorialLaboralOtrasCajas(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileHistorialLaboralOtrasCajas != null){
                    
                    if(fileHistorialLaboralOtrasCajas.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileHistorialLaboralOtrasCajas.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosHistorialLaboralOtrasCajas (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileHistorialLaboralOtrasCajas.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileHistorialLaboralOtrasCajas.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileHistorialLaboralOtrasCajas.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileHistorialLaboralOtrasCajas.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileHistorialLaboralOtrasCajas.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileHistorialLaboralOtrasCajas.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadHistorialLaboralOtrasCajasDos(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileHistorialLaboralOtrasCajasDos != null){
                    
                    if(fileHistorialLaboralOtrasCajasDos.getContentType().equalsIgnoreCase(IMAGE_JPEG) ||
                            fileHistorialLaboralOtrasCajasDos.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosHistorialLaboralOtrasCajasDos (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileHistorialLaboralOtrasCajasDos.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileHistorialLaboralOtrasCajasDos.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileHistorialLaboralOtrasCajasDos.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileHistorialLaboralOtrasCajasDos.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileHistorialLaboralOtrasCajasDos.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileHistorialLaboralOtrasCajasDos.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public void uploadBoletaDeAportes(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileBoletaDeAportes != null){
                    
                    if(fileBoletaDeAportes.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileBoletaDeAportes.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosBoletaDeAportes (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileBoletaDeAportes.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileBoletaDeAportes.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileBoletaDeAportes.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileBoletaDeAportes.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileBoletaDeAportes.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileBoletaDeAportes.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadBoletaDeAportesDos(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileBoletaDeAportesDos != null){
                    
                    if(fileBoletaDeAportesDos.getContentType().equalsIgnoreCase(IMAGE_JPEG) ||
                            fileBoletaDeAportesDos.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosBoletaDeAportesDos (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileBoletaDeAportesDos.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileBoletaDeAportesDos.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileBoletaDeAportesDos.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileBoletaDeAportesDos.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileBoletaDeAportesDos.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileBoletaDeAportesDos.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public void uploadAfipDatosPersonalesPagosOtros(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileAfipDatosPersonalesPagosOtros != null){
                    
                    if(fileAfipDatosPersonalesPagosOtros.getContentType().equalsIgnoreCase(IMAGE_JPEG) ||
                            fileAfipDatosPersonalesPagosOtros.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosAfipDatosPersonalesPagosOtros (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileAfipDatosPersonalesPagosOtros.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileAfipDatosPersonalesPagosOtros.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileAfipDatosPersonalesPagosOtros.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileAfipDatosPersonalesPagosOtros.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileAfipDatosPersonalesPagosOtros.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileAfipDatosPersonalesPagosOtros.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadAfipDatosPersonalesPagosOtrosDos(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileAfipDatosPersonalesPagosOtrosDos != null){
                    
                    if(fileAfipDatosPersonalesPagosOtrosDos.getContentType().equalsIgnoreCase(IMAGE_JPEG) ||
                            fileAfipDatosPersonalesPagosOtrosDos.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosAfipDatosPersonalesPagosOtrosDos (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileAfipDatosPersonalesPagosOtrosDos.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileAfipDatosPersonalesPagosOtrosDos.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileAfipDatosPersonalesPagosOtrosDos.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileAfipDatosPersonalesPagosOtrosDos.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileAfipDatosPersonalesPagosOtrosDos.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileAfipDatosPersonalesPagosOtrosDos.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
        
    public void uploadAfipDatosPersonalesPagosOtrosTres(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileAfipDatosPersonalesPagosOtrosTres != null){
                    
                    if(fileAfipDatosPersonalesPagosOtrosTres.getContentType().equalsIgnoreCase(IMAGE_JPEG) ||
                            fileAfipDatosPersonalesPagosOtrosTres.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosAfipDatosPersonalesPagosOtrosTres (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileAfipDatosPersonalesPagosOtrosTres.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileAfipDatosPersonalesPagosOtrosTres.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileAfipDatosPersonalesPagosOtrosTres.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileAfipDatosPersonalesPagosOtrosTres.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileAfipDatosPersonalesPagosOtrosTres.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileAfipDatosPersonalesPagosOtrosTres.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
        
    public void uploadCartaPoder(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileCartaPoder != null){
                    
                    if(fileCartaPoder.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileCartaPoder.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosCartaPoder (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileCartaPoder.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileCartaPoder.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileCartaPoder.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileCartaPoder.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileCartaPoder.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileCartaPoder.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadExpAdministrativo(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileExpAdministrativo != null){
                    
                    if(fileExpAdministrativo.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileExpAdministrativo.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosExpAdministrativo (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileExpAdministrativo.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileExpAdministrativo.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileExpAdministrativo.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileExpAdministrativo.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileExpAdministrativo.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileExpAdministrativo.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public void uploadExpAdministrativoDos(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileExpAdministrativoDos != null){
                    
                    if(fileExpAdministrativoDos.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileExpAdministrativoDos.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosExpAdministrativoDos (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileExpAdministrativoDos.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileExpAdministrativoDos.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileExpAdministrativoDos.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileExpAdministrativo.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileExpAdministrativoDos.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileExpAdministrativoDos.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public void uploadDocumentoPorNombre(int orden, String nombre) {  
         UploadedFile fileParaSubir = null;
                
         switch(nombre){
             case "Recibos":
                 fileParaSubir = fileRecibos;
                 break;
            
             case "RecibosDos":
                 fileParaSubir = fileRecibosDos;
                 break;
            
             case "RecibosTres":
                 fileParaSubir = fileRecibosTres;
                 break;
            
             case "RecibosCuatro":
                 fileParaSubir = fileRecibosCuatro;
                 break;     
                 
            case "LiquidacionBlueCorp":
                 fileParaSubir = fileLiquidacionBlueCorp;
                 break;     
            case "ResolucionDenegatoria":
                 fileParaSubir = fileResolucionDenegatoria;
                 break;     
            case "ConvenioDeHonorarios":
                 fileParaSubir = fileConvenioDeHonorarios;
                 break;     
            case "ConstanciaDeCbu":
                 fileParaSubir = fileConstanciaDeCbu;
                 break;     
                 
         }
        
        try {
            Connection con;
		PreparedStatement ps;
                    
            if(fileParaSubir != null){
                    
                    if(fileParaSubir.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileParaSubir.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentos"+nombre+" (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileParaSubir.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileParaSubir.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileParaSubir.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileParaSubir.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo de tipo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileParaSubir.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, " Fichero demasiado grande por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public void uploadFrenteDniParaExpSinCarpeta(String cuit) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

                
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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + frenteDniExpSinCarpeta.getFileName() + " subido correctamente. Con nro de cuit: "+cuitLong);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(frenteDniExpSinCarpeta.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + frenteDniExpSinCarpeta.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + frenteDniExpSinCarpeta.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  

    
    public void uploadDorsoDniParaExpSinCarpeta(String cuit) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

                
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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + dorsoDniExpSinCarpeta.getFileName() + " subido correctamente. Con nro de cuit: "+cuitLong);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(dorsoDniExpSinCarpeta.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + dorsoDniExpSinCarpeta.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + dorsoDniExpSinCarpeta.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
   
    public void uploadJPG(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + file.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(file.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + file.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + file.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadJPGDos(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileDos.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileDos.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileDos.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileDos.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadJPGTres(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileTres.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileTres.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileTres.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileTres.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadJPGCuatro(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileCuatro.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileCuatro.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileCuatro.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileCuatro.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadJPGCinco(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

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
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileCinco.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileCinco.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileCinco.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileCinco.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadBlueCorp(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileLiquidacionBlueCorp != null){
                    
                    if(fileLiquidacionBlueCorp.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileLiquidacionBlueCorp.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosLiquidacionBlueCorp (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileLiquidacionBlueCorp.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileLiquidacionBlueCorp.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileLiquidacionBlueCorp.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileLiquidacionBlueCorp.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileLiquidacionBlueCorp.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileLiquidacionBlueCorp.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadBlueCorpDos(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileLiquidacionBlueCorpDos != null){
                    
                    if(fileLiquidacionBlueCorpDos.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileLiquidacionBlueCorpDos.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosLiquidacionBlueCorpDos (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileLiquidacionBlueCorpDos.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileLiquidacionBlueCorpDos.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileLiquidacionBlueCorpDos.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileLiquidacionBlueCorpDos.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileLiquidacionBlueCorpDos.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileLiquidacionBlueCorpDos.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
    public void uploadBlueCorpTres(int orden) {  
                    
        try {
            Connection con;
		PreparedStatement ps;

            if(fileLiquidacionBlueCorpTres != null){
                    
                    if(fileLiquidacionBlueCorpTres.getContentType().equalsIgnoreCase(IMAGE_JPEG) || fileLiquidacionBlueCorpTres.getContentType().equalsIgnoreCase(APPLICATION_PDF)){
                        con = DAO.getConnection();
                        ps = con.prepareStatement("INSERT INTO documentosLiquidacionBlueCorpTres (documento, nroDeOrden, nombreDelDocumento) " +
                        "VALUES (?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "documento = VALUES(documento), " +
                            "nroDeOrden = LAST_INSERT_ID(nroDeOrden),"+
                            "nombreDelDocumento = VALUES(nombreDelDocumento) "
                                );

                        ps.setBinaryStream(1, fileLiquidacionBlueCorpTres.getInputstream());
                        ps.setInt(2, orden);
                        ps.setString(3, fileLiquidacionBlueCorpTres.getFileName());
                        
                        ps.executeUpdate();
                        con.close();
                                            
                        FacesMessage msg = new FacesMessage(OK, "Fichero " + fileLiquidacionBlueCorpTres.getFileName() + SUBIDO_CORRECTAMENTE__CON_NRO_DE__ORDEN+orden);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    
                    }else{
                        if(!"".equals(fileLiquidacionBlueCorpTres.getFileName())){
                            FacesMessage msg = new FacesMessage(ERROR, "no selecciono un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage(ERROR, "Fichero " + fileLiquidacionBlueCorpTres.getFileName() + " no es un archivo JPG O PDF");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                    
            }
            
        } catch (IOException | SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, FICHERO_DEMASIADO_GRANDE + fileLiquidacionBlueCorpTres.getFileName() + " por favor seleccione otro.");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }  
    
 }

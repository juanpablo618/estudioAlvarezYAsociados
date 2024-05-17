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
import java.sql.SQLException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class FileDownloadBean implements Serializable {

    private static final long serialVersionUID = 626953318628565453L;
    private static final String APPLICATION_PDF = "application/pdf";
    private static final String IMAGE_JPEG = "image/jpeg";
    private static final String ERROR = "Error";
    
    private StreamedContent filePdf;
    private StreamedContent filePdfDos;
    private StreamedContent filePdfTres;
    private StreamedContent filePdfCuatro;
    private StreamedContent filePdfCinco;

    private StreamedContent file;
    private StreamedContent fileDos;
    private StreamedContent fileTres;
    private StreamedContent fileCuatro;
    private StreamedContent fileCinco;

    private StreamedContent fileFrenteDni;
    private StreamedContent fileDorsoDni;
    private StreamedContent fileOtraDocumentacion;
    private StreamedContent fileOtraDocumentacionDos;
    private StreamedContent fileOtraDocumentacionTres;
    
    private StreamedContent fileCartaPoder;
    private StreamedContent fileHistorialLaboralAnses;
    private StreamedContent fileHistorialLaboralOtrasCajas;
    private StreamedContent fileHistorialLaboralOtrasCajasDos;
    
    private StreamedContent fileAfipDatosPersonalesPagosOtros;
    private StreamedContent fileAfipDatosPersonalesPagosOtrosDos;
    private StreamedContent fileAfipDatosPersonalesPagosOtrosTres;
    
    private StreamedContent fileExpAdministrativo;
    private StreamedContent fileRecibos;
    private StreamedContent fileLiquidacionBlueCorp;
    private StreamedContent fileLiquidacionBlueCorpDos;
    private StreamedContent fileLiquidacionBlueCorpTres;
    
    private StreamedContent fileResolucionDenegatoria;
    private StreamedContent fileConvenioDeHonorarios;
    private StreamedContent fileConstanciaDeCbu;
    
    private StreamedContent fileCronoDeAportes;
    private StreamedContent fileCronoDeAportesDos;
    
    private StreamedContent frenteDniExpSinCarpeta;
    private StreamedContent dorsoDniExpSinCarpeta;
    

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

    public StreamedContent getFilePdf() {
        return filePdf;
    }

    public void setFilePdf(StreamedContent filePdf) {
        this.filePdf = filePdf;
    }

    public StreamedContent getFilePdfDos() {
        return filePdfDos;
    }

    public void setFilePdfDos(StreamedContent filePdfDos) {
        this.filePdfDos = filePdfDos;
    }

    public StreamedContent getFilePdfTres() {
        return filePdfTres;
    }

    public void setFilePdfTres(StreamedContent filePdfTres) {
        this.filePdfTres = filePdfTres;
    }

    public StreamedContent getFilePdfCuatro() {
        return filePdfCuatro;
    }

    public void setFilePdfCuatro(StreamedContent filePdfCuatro) {
        this.filePdfCuatro = filePdfCuatro;
    }

    public StreamedContent getFilePdfCinco() {
        return filePdfCinco;
    }

    public void setFilePdfCinco(StreamedContent filePdfCinco) {
        this.filePdfCinco = filePdfCinco;
    }

    public StreamedContent getFileDos() {
        return fileDos;
    }

    public void setFileDos(StreamedContent fileDos) {
        this.fileDos = fileDos;
    }

    public StreamedContent getFileTres() {
        return fileTres;
    }

    public void setFileTres(StreamedContent fileTres) {
        this.fileTres = fileTres;
    }

    public StreamedContent getFileCuatro() {
        return fileCuatro;
    }

    public void setFileCuatro(StreamedContent fileCuatro) {
        this.fileCuatro = fileCuatro;
    }

    public StreamedContent getFileCinco() {
        return fileCinco;
    }

    public void setFileCinco(StreamedContent fileCinco) {
        this.fileCinco = fileCinco;
    }

    public StreamedContent getFileFrenteDni() {
        return fileFrenteDni;
    }

    public void setFileFrenteDni(StreamedContent fileFrenteDni) {
        this.fileFrenteDni = fileFrenteDni;
    }

    public StreamedContent getFileDorsoDni() {
        return fileDorsoDni;
    }

    public void setFileDorsoDni(StreamedContent fileDorsoDni) {
        this.fileDorsoDni = fileDorsoDni;
    }

    public StreamedContent getFileOtraDocumentacion() {
        return fileOtraDocumentacion;
    }

    public void setFileOtraDocumentacion(StreamedContent fileOtraDocumentacion) {
        this.fileOtraDocumentacion = fileOtraDocumentacion;
    }

    public StreamedContent getFileCronoDeAportes() {
        return fileCronoDeAportes;
    }

    public void setFileCronoDeAportes(StreamedContent fileCronoDeAportes) {
        this.fileCronoDeAportes = fileCronoDeAportes;
    }

    public StreamedContent getFileCronoDeAportesDos() {
        return fileCronoDeAportesDos;
    }

    public void setFileCronoDeAportesDos(StreamedContent fileCronoDeAportesDos) {
        this.fileCronoDeAportesDos = fileCronoDeAportesDos;
    }
    
    public StreamedContent getFrenteDniExpSinCarpeta() {
        return frenteDniExpSinCarpeta;
    }

    public void setFrenteDniExpSinCarpeta(StreamedContent frenteDniExpSinCarpeta) {
        this.frenteDniExpSinCarpeta = frenteDniExpSinCarpeta;
    }

    public StreamedContent getDorsoDniExpSinCarpeta() {
        return dorsoDniExpSinCarpeta;
    }

    public void setDorsoDniExpSinCarpeta(StreamedContent dorsoDniExpSinCarpeta) {
        this.dorsoDniExpSinCarpeta = dorsoDniExpSinCarpeta;
    }

    public StreamedContent getFileCartaPoder() {
        return fileCartaPoder;
    }

    public void setFileCartaPoder(StreamedContent fileCartaPoder) {
        this.fileCartaPoder = fileCartaPoder;
    }

    public StreamedContent getFileExpAdministrativo() {
        return fileExpAdministrativo;
    }

    public void setFileExpAdministrativo(StreamedContent fileExpAdministrativo) {
        this.fileExpAdministrativo = fileExpAdministrativo;
    }

    public StreamedContent getFileRecibos() {
        return fileRecibos;
    }

    public void setFileRecibos(StreamedContent fileRecibos) {
        this.fileRecibos = fileRecibos;
    }

    public StreamedContent getFileLiquidacionBlueCorp() {
        return fileLiquidacionBlueCorp;
    }

    public void setFileLiquidacionBlueCorp(StreamedContent fileLiquidacionBlueCorp) {
        this.fileLiquidacionBlueCorp = fileLiquidacionBlueCorp;
    }

    public StreamedContent getFileResolucionDenegatoria() {
        return fileResolucionDenegatoria;
    }

    public void setFileResolucionDenegatoria(StreamedContent fileResolucionDenegatoria) {
        this.fileResolucionDenegatoria = fileResolucionDenegatoria;
    }

    public StreamedContent getFileOtraDocumentacionDos() {
        return fileOtraDocumentacionDos;
    }

    public void setFileOtraDocumentacionDos(StreamedContent fileOtraDocumentacionDos) {
        this.fileOtraDocumentacionDos = fileOtraDocumentacionDos;
    }

    public StreamedContent getFileOtraDocumentacionTres() {
        return fileOtraDocumentacionTres;
    }

    public void setFileOtraDocumentacionTres(StreamedContent fileOtraDocumentacionTres) {
        this.fileOtraDocumentacionTres = fileOtraDocumentacionTres;
    }

    public StreamedContent getFileConvenioDeHonorarios() {
        return fileConvenioDeHonorarios;
    }

    public void setFileConvenioDeHonorarios(StreamedContent fileConvenioDeHonorarios) {
        this.fileConvenioDeHonorarios = fileConvenioDeHonorarios;
    }

    public StreamedContent getFileConstanciaDeCbu() {
        return fileConstanciaDeCbu;
    }

    public void setFileConstanciaDeCbu(StreamedContent fileConstanciaDeCbu) {
        this.fileConstanciaDeCbu = fileConstanciaDeCbu;
    }

    public StreamedContent getFileLiquidacionBlueCorpDos() {
        return fileLiquidacionBlueCorpDos;
    }

    public void setFileLiquidacionBlueCorpDos(StreamedContent fileLiquidacionBlueCorpDos) {
        this.fileLiquidacionBlueCorpDos = fileLiquidacionBlueCorpDos;
    }

    public StreamedContent getFileLiquidacionBlueCorpTres() {
        return fileLiquidacionBlueCorpTres;
    }

    public void setFileLiquidacionBlueCorpTres(StreamedContent fileLiquidacionBlueCorpTres) {
        this.fileLiquidacionBlueCorpTres = fileLiquidacionBlueCorpTres;
    }

    public StreamedContent getFileHistorialLaboralAnses() {
        return fileHistorialLaboralAnses;
    }

    public void setFileHistorialLaboralAnses(StreamedContent fileHistorialLaboralAnses) {
        this.fileHistorialLaboralAnses = fileHistorialLaboralAnses;
    }

    public StreamedContent getFileHistorialLaboralOtrasCajas() {
        return fileHistorialLaboralOtrasCajas;
    }

    public void setFileHistorialLaboralOtrasCajas(StreamedContent fileHistorialLaboralOtrasCajas) {
        this.fileHistorialLaboralOtrasCajas = fileHistorialLaboralOtrasCajas;
    }

    public StreamedContent getFileHistorialLaboralOtrasCajasDos() {
        return fileHistorialLaboralOtrasCajasDos;
    }

    public void setFileHistorialLaboralOtrasCajasDos(StreamedContent fileHistorialLaboralOtrasCajasDos) {
        this.fileHistorialLaboralOtrasCajasDos = fileHistorialLaboralOtrasCajasDos;
    }

    public StreamedContent getFileAfipDatosPersonalesPagosOtros() {
        return fileAfipDatosPersonalesPagosOtros;
    }

    public void setFileAfipDatosPersonalesPagosOtros(StreamedContent fileAfipDatosPersonalesPagosOtros) {
        this.fileAfipDatosPersonalesPagosOtros = fileAfipDatosPersonalesPagosOtros;
    }

    public StreamedContent getFileAfipDatosPersonalesPagosOtrosDos() {
        return fileAfipDatosPersonalesPagosOtrosDos;
    }

    public void setFileAfipDatosPersonalesPagosOtrosDos(StreamedContent fileAfipDatosPersonalesPagosOtrosDos) {
        this.fileAfipDatosPersonalesPagosOtrosDos = fileAfipDatosPersonalesPagosOtrosDos;
    }

    public StreamedContent getFileAfipDatosPersonalesPagosOtrosTres() {
        return fileAfipDatosPersonalesPagosOtrosTres;
    }

    public void setFileAfipDatosPersonalesPagosOtrosTres(StreamedContent fileAfipDatosPersonalesPagosOtrosTres) {
        this.fileAfipDatosPersonalesPagosOtrosTres = fileAfipDatosPersonalesPagosOtrosTres;
    }
    
    
    public void downloadPDF(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentos WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");

                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        filePdf = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        filePdf = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }
                con.close();
                if (filePdf != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo PDF 1 descargado exitosamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo PDF 1 para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
        }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro documento pdf 1 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero JPG O PDF 1 no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadPDFDos(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentos2 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        filePdfDos = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        filePdfDos = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }
                con.close();
                if (filePdfDos != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo PDF 2 descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo PDF 2 para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro documento pdf 2 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero JPG O PDF 2 no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadPDFTres(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentos3 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        filePdfTres = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        filePdfTres = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (filePdfTres != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo PDF 3 descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo PDF 3 para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro documento JPG O PDF 3 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero JPG O PDF 3 no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadPDFCuatro(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentos4 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        filePdfCuatro = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        filePdfCuatro = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (filePdfCuatro != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo JPG O PDF 4 descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo JPG O PDF 4 para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro documento JPG o PDF 4 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero PDF/JPG 4 no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadPDFCinco(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentos5 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        filePdfCinco = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        filePdfCinco = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }
                }
                con.close();
                if (filePdfCinco != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo PDF 5 descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo PDF 5 para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro documento pdf 5 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero PDF 5 no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public String buscarNombreDeArchivoPDF(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentos WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage("ERROR", "Nombre del archivo pdf no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo 1 para este expediente";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoPDFDos(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentos2 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage("ERROR", "Nombre del archivo pdf 2 no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo 2 para este expediente";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoPDFTres(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentos3 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage("ERROR", "Nombre del archivo pdf 3 no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo 3 para este expediente";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoPDFCuatro(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentos4 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo 4 no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo 4 para este expediente";
        } else {
            return nombre;
        }
    }

    public String buscarNombreDeArchivoPDFCinco(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentos5 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo pdf cinco no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo 5 para este expediente";
        } else {
            return nombre;
        }
    }

    public void downloadCronoDeAportes(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentoscronodeaportes WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                   InputStream stream = rs.getBinaryStream("documento");
                    String nombreDelDocumento = rs.getString("nombreDelDocumento");
                    if (nombreDelDocumento.contains(".xls")) {
                        fileCronoDeAportes = new DefaultStreamedContent(stream, "application/vnd.ms-excel", nombreDelDocumento);
                    } else if (nombreDelDocumento.contains(".xlsm")) {
                        fileCronoDeAportes = new DefaultStreamedContent(stream, "application/vnd.ms-excel.sheet.macroEnabled.12", nombreDelDocumento);
                    } else {
                        fileCronoDeAportes = new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", nombreDelDocumento);
                    }
                }

                con.close();
                if (fileCronoDeAportes != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo Crono. de aportes descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo Crono. de aportes para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro Crono. de aportes con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero Crono. de aportes no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadCronoDeAportesDos(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentoscronodeaportesdos WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    String nombreDelDocumento = rs.getString("nombreDelDocumento");
                    if (nombreDelDocumento.contains(".xls")) {
                        fileCronoDeAportesDos = new DefaultStreamedContent(stream, "application/vnd.ms-excel", nombreDelDocumento);
                    } else if (nombreDelDocumento.contains(".xlsm")) {
                        fileCronoDeAportesDos = new DefaultStreamedContent(stream, "application/vnd.ms-excel.sheet.macroEnabled.12", nombreDelDocumento);
                    } else {
                        fileCronoDeAportesDos = new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", nombreDelDocumento);
                    }

                }

                con.close();
                if (fileCronoDeAportesDos != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo Crono. de aportes 2 descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo Crono. de aportes 2 para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro Crono. de aportes 2 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero Crono. de aportes 2 no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    
    public void downloadFrenteDnideExpSinCarpeta(String cuit) {
        Connection con;
        PreparedStatement ps;
        try {
            long cuitLong = 0;
            
            if (!"0".equals(cuit) && cuit != null && !"".equals(cuit))  {
                cuitLong = Long.parseLong(cuit);
            }  
        
            
            if (cuitLong != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM frenteDniExpSinCarpeta WHERE nroDeCuit = (?);");
                ps.setLong(1, cuitLong);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        frenteDniExpSinCarpeta = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        frenteDniExpSinCarpeta = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (frenteDniExpSinCarpeta != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo frente dni de exp sin carpeta descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo frente dni de exp. sin carpeta para este nro de cuit: " + cuitLong);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro imagen frente dni(de exp. sin carpeta) con ese nro de cuit.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero frente dni(de exp. sin carpeta) uno no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadDorsoDnideExpSinCarpeta(String cuit) {
        Connection con;
        PreparedStatement ps;
        try {
            long cuitLong = 0;
            
            if (!"0".equals(cuit) && cuit != null && !"".equals(cuit))  {
                cuitLong = Long.parseLong(cuit);
            }  
        
            
            if (cuitLong != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM dorsoDniExpSinCarpeta WHERE nroDeCuit = (?);");
                ps.setLong(1, cuitLong);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        dorsoDniExpSinCarpeta = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        dorsoDniExpSinCarpeta = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (dorsoDniExpSinCarpeta != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo dorso dni de exp sin carpeta descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo dorso dni de exp. sin carpeta para este nro de cuit: " + cuitLong);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro imagen dorso dni(de exp. sin carpeta) con ese nro de cuit.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero dorso dni(de exp. sin carpeta) uno no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadJPG(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosjpg WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        file = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        file = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (file != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo JPG uno descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo JPG uno para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro imagen JPG(1) con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero JPG(1) uno no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadJPGDos(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosjpg2 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        fileDos = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        fileDos = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (fileDos != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo JPG dos descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo JPG dos para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro imagen JPG(2) con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero JPG(2) uno no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadJPGTres(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosjpg3 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        fileTres = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        fileTres = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (fileTres != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo JPG tres descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo JPG tres para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro imagen JPG(3) con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero JPG(3) uno no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadJPGCuatro(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosjpg4 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        fileCuatro = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        fileCuatro = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }
                }

                con.close();
                if (fileCuatro != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo cuatro descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe Archivo cuatro para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro Archivo JPG O PDF 4 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero JPG O PDF 4 no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadJPGCinco(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosjpg5 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        fileCinco = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        fileCinco = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }
                }

                con.close();
                if (fileCinco != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo JPG cinco descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo JPG cinco para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro imagen JPG(5) con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero JPG cinco no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadFrenteDni(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosFrenteDni WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        fileFrenteDni = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        fileFrenteDni = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }
                }

                con.close();
                if (fileFrenteDni != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo JPG uno descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo JPG uno para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro imagen JPG(1) con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero JPG(1) uno no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadDorsoDni(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosDorsoDni WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        fileDorsoDni = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        fileDorsoDni = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (fileDorsoDni != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo Dorso DNI descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo Dorso DNI para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro Dorso DNI con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero Dorso DNI no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void downloadOtraDocumentacion(int orden, int numeroDocumento) {
    Connection con = null;
    PreparedStatement ps = null;
    
    try {
        if (orden != 0 && numeroDocumento >= 1 && numeroDocumento <= 3) {
            con = DAO.getConnection();
            ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosOtraDocumentacion WHERE nroDeOrden = (?) AND numeroDocumento = (?);");
            ps.setInt(1, orden);
            ps.setInt(2, numeroDocumento);

                     
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InputStream stream = rs.getBinaryStream("documento");
                if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                    
                    switch (numeroDocumento) {
                        case 1:
                            fileOtraDocumentacion = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));;
                            break;
                        case 2:
                            fileOtraDocumentacionDos = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));;
                            break;
                        case 3:
                            fileOtraDocumentacionTres = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));;
                            break;

                    }
                     
                } else {
                    switch (numeroDocumento) {
                        case 1:
                            fileOtraDocumentacion = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                            break;
                        case 2:
                            fileOtraDocumentacionDos = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                            break;
                        case 3:
                            fileOtraDocumentacionTres = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                            break;

                    }
                }
            }
            
             StreamedContent currentFile = null;

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

            if (currentFile != null) {
                FacesMessage msg = new FacesMessage("Éxito", "Archivo Otra Documentación " + numeroDocumento + " descargado exitosamente.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(ERROR, "No existe archivo Otra Documentación " + numeroDocumento + " para este nro de orden: " + orden);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(ERROR, "No se encontró Otra Documentación " + numeroDocumento + " con ese nro de orden.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    } catch (SQLException e) {
        FacesMessage msg = new FacesMessage(ERROR, "Fichero Otra Documentación " + numeroDocumento + " no descargado");
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

    
    /*public void downloadBluecorp(int orden, int numeroDocumento) {
    Connection con = null;
    PreparedStatement ps = null;
    
    try {
        if (orden != 0 && numeroDocumento >= 1 && numeroDocumento <= 3) {
            con = DAO.getConnection();
            ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosliquidacionbluecorp WHERE nroDeOrden = (?) AND numeroDocumento = (?);");
            ps.setInt(1, orden);
            ps.setInt(2, numeroDocumento);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InputStream stream = rs.getBinaryStream("documento");
                if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                    
                    switch (numeroDocumento) {
                        case 1:
                            fileLiquidacionBlueCorp = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));;
                            break;
                        case 2:
                            fileLiquidacionBlueCorpDos = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));;
                            break;
                        case 3:
                            fileLiquidacionBlueCorpTres = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));;
                            break;

                    }
                     
                } else {
                    switch (numeroDocumento) {
                        case 1:
                            fileLiquidacionBlueCorp = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                            break;
                        case 2:
                            fileLiquidacionBlueCorpDos = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                            break;
                        case 3:
                            fileLiquidacionBlueCorpTres = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                            break;

                    }
                }
            }
            
             StreamedContent currentFile = null;

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

            if (currentFile != null) {
                FacesMessage msg = new FacesMessage("Éxito", "Archivo Bluecorp " + numeroDocumento + " descargado exitosamente.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage(ERROR, "No existe archivo Blue corp " + numeroDocumento + " para este nro de orden: " + orden);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesMessage msg = new FacesMessage(ERROR, "No se encontró bluecorp " + numeroDocumento + " con ese nro de orden.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    } catch (SQLException e) {
        FacesMessage msg = new FacesMessage(ERROR, "Fichero Bluecorp " + numeroDocumento + " no descargado");
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

    public void downloadBlueCorp(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosLiquidacionBlueCorp WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        fileLiquidacionBlueCorp = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        fileLiquidacionBlueCorp = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (fileLiquidacionBlueCorp != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo BlueCorp 1 descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo BlueCorp 1 para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro BlueCorp con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero BlueCorp no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadBlueCorpDos(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosLiquidacionBlueCorpDos WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        fileLiquidacionBlueCorpDos = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        fileLiquidacionBlueCorpDos = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (fileLiquidacionBlueCorpDos != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo BlueCorp 2 descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo BlueCorp 2 para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro BlueCorp 2 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero BlueCorp 2 no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadBlueCorpTres(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosLiquidacionBlueCorpTres WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        fileLiquidacionBlueCorpTres = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        fileLiquidacionBlueCorpTres = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (fileLiquidacionBlueCorpTres != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo BlueCorp 3 descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo BlueCorp 3 para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro BlueCorp 3 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero BlueCorp 3 no descargado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadOtraDocumentacion(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosOtraDocumentacion WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        fileOtraDocumentacion = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        fileOtraDocumentacion = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (fileOtraDocumentacion != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo Otra Documentación descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo Otra Documentación para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro Otra Documentación con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero Otra Documentación no descargada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadOtraDocumentacionDos(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosOtraDocumentacionDos WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        fileOtraDocumentacionDos = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        fileOtraDocumentacionDos = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (fileOtraDocumentacionDos != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo Otra Documentación 2 descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo Otra Documentación 2 para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro Otra Documentación 2 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero Otra Documentación 2 no descargada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadOtraDocumentacionTres(int orden) {
        Connection con;
        PreparedStatement ps;
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentosOtraDocumentacionTres WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        fileOtraDocumentacionTres = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                    } else {
                        fileOtraDocumentacionTres = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                    }

                }

                con.close();
                if (fileOtraDocumentacionTres != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo Otra Documentación 3 descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage(ERROR, "No existe archivo Otra Documentación 3 para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage(ERROR, "no se encontro Otra Documentación 3 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero Otra Documentación 3 no descargada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    
    public void downloadDocumentoPorNombre(int orden, String nombre) {
        Connection con;
        PreparedStatement ps;
        StreamedContent fileAlmacenado = null;
                       
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT documento, nombreDelDocumento FROM documentos"+nombre+" WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InputStream stream = rs.getBinaryStream("documento");
                    if (rs.getString("nombreDelDocumento").contains(".jpg")) {
                        
                         
                        switch(nombre){
                            case "CartaPoder":
                                fileCartaPoder = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileCartaPoder;
                                break;
                            case "ExpAdministrativo":
                                fileExpAdministrativo = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileExpAdministrativo;
                                break;
                            case "Recibos":
                                fileRecibos = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileRecibos;
                                break;
                            case "LiquidacionBlueCorp":
                                fileLiquidacionBlueCorp = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileLiquidacionBlueCorp;
                                break;
                            case "ResolucionDenegatoria":
                                fileResolucionDenegatoria = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileResolucionDenegatoria;
                                break;
                            case "ConvenioDeHonorarios":
                                fileConvenioDeHonorarios = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileConvenioDeHonorarios;
                                break;
                            case "ConstanciaDeCbu":
                                fileConstanciaDeCbu = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileConstanciaDeCbu;
                                break;
                            case "HistorialLaboralAnses":
                                fileHistorialLaboralAnses = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileHistorialLaboralAnses;
                                break;
                            case "HistorialLaboralOtrasCajas":
                                fileHistorialLaboralOtrasCajas = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileHistorialLaboralOtrasCajas;
                                break;
                            case "HistorialLaboralOtrasCajasDos":
                                fileHistorialLaboralOtrasCajasDos = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileHistorialLaboralOtrasCajasDos;
                                break;   
                            case "AfipDatosPersonalesPagosOtros":
                                fileAfipDatosPersonalesPagosOtros = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileAfipDatosPersonalesPagosOtros;
                                break;   
                            case "AfipDatosPersonalesPagosOtrosDos":
                                fileAfipDatosPersonalesPagosOtrosDos = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileAfipDatosPersonalesPagosOtrosDos;
                                break;   
                            case "AfipDatosPersonalesPagosOtrosTres":
                                fileAfipDatosPersonalesPagosOtrosTres = new DefaultStreamedContent(stream, IMAGE_JPEG, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileAfipDatosPersonalesPagosOtrosTres;
                                break;   
                                
                                
                        }
                        
                    } else {
                        
                        switch(nombre){
                            case "CartaPoder":
                                fileCartaPoder = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileCartaPoder;
                                break;
                            case "ExpAdministrativo":
                                fileExpAdministrativo = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileExpAdministrativo;
                                break;
                            case "Recibos":
                                fileRecibos = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileRecibos;
                                break;
                            case "LiquidacionBlueCorp":
                                fileLiquidacionBlueCorp = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileLiquidacionBlueCorp;
                                break;
                            case "ResolucionDenegatoria":
                                fileResolucionDenegatoria = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileResolucionDenegatoria;
                                break;
                                
                            case "ConvenioDeHonorarios":
                                fileConvenioDeHonorarios = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileConvenioDeHonorarios;
                                break;
                                
                            case "ConstanciaDeCbu":
                                fileConstanciaDeCbu = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileConstanciaDeCbu;
                                break;
                            case "HistorialLaboralAnses":
                                fileHistorialLaboralAnses = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileHistorialLaboralAnses;
                                break;  
                            case "HistorialLaboralOtrasCajas":
                                fileHistorialLaboralOtrasCajas = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileHistorialLaboralOtrasCajas;
                                break;
                            case "HistorialLaboralOtrasCajasDos":
                                fileHistorialLaboralOtrasCajasDos = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileHistorialLaboralOtrasCajasDos;
                                break;    
                            case "AfipDatosPersonalesPagosOtros":
                                fileAfipDatosPersonalesPagosOtros = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileAfipDatosPersonalesPagosOtros;
                                break;    
                            case "AfipDatosPersonalesPagosOtrosDos":
                                fileAfipDatosPersonalesPagosOtrosDos = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileAfipDatosPersonalesPagosOtrosDos;
                                break;    
                            case "AfipDatosPersonalesPagosOtrosTres":
                                fileAfipDatosPersonalesPagosOtrosTres = new DefaultStreamedContent(stream, APPLICATION_PDF, rs.getString("nombreDelDocumento"));
                                fileAlmacenado = fileAfipDatosPersonalesPagosOtrosTres;
                                break;    
                                
                        }
                        
                    }

                }

                con.close();
                if (fileAlmacenado != null) {
                    FacesMessage msg = new FacesMessage("Exito", "archivo "+nombre+" descargado exitosamente.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {
                    FacesMessage msg = new FacesMessage("ERROR", "No existe archivo "+nombre+" para este nro de orden: " + orden);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                FacesMessage msg = new FacesMessage("ERROR", "no se encontro "+nombre+" con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Fichero "+nombre+" no descargada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public String buscarNombreDeArchivoCronoDeAportes(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentoscronodeaportes WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo Crono. de aportes no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo Crono. de aportes para este expediente";
        } else {
            return nombre;
        }
    }
    
    public String buscarNombreDeArchivoCronoDeAportesDos(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentoscronodeaportesdos WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo Crono. de aportes dos no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo Crono. de aportes dos para este expediente";
        } else {
            return nombre;
        }
    }
    
    public String buscarNombreDeArchivoJPG(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosjpg WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage("ERROR", "Nombre del archivo JPG uno no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo uno para este expediente";
        } else {
            return nombre;

        }
    }

    public String buscarFrenteDniParaExpSinCarpeta(String cuit) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        
        long cuitLong = 0;
            
            if (!"0".equals(cuit) && cuit != null && !"".equals(cuit))  {
                cuitLong = Long.parseLong(cuit);
            }  
            
        
        try {
            if (cuitLong != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM frenteDniExpSinCarpeta WHERE nroDeCuit = (?);");
                ps.setLong(1, cuitLong);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre frente dni para exp. sin carpeta no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe frente dni para este expediente sin carpeta";
        } else {
            return nombre;

        }
    }

    public String buscarDorsoDniParaExpSinCarpeta(String cuit) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        
        long cuitLong = 0;
            
            if (!"0".equals(cuit) && cuit != null && !"".equals(cuit))  {
                cuitLong = Long.parseLong(cuit);
            }  
            
        
        try {
            if (cuitLong != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM dorsoDniExpSinCarpeta WHERE nroDeCuit = (?);");
                ps.setLong(1, cuitLong);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo dorso dni para exp. sin carpeta no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe dorso dni para este expediente sin carpeta";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoJPGDos(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosjpg2 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo JPG Dos no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo 2 para este expediente";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoJPGTres(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosjpg3 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo JPG Tres no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo 3 para este expediente";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoJPGCuatro(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosjpg4 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo JPG Cuatro no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo 4 para este expediente";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoJPGCinco(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosjpg5 WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo JPG Cinco no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo 5 para este expediente";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoFrenteDni(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosFrenteDni WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo JPG uno no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo frente de dni";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoDorsoDni(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosDorsoDni WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo JPG uno no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo dorso de dni";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoOtraDocumentacion(int orden, int numeroDocumento) {
    Connection con = null;
    PreparedStatement ps = null;
    String nombre = "";

    try {
        if (orden != 0 && numeroDocumento >= 1 && numeroDocumento <= 3) {
            con = DAO.getConnection();
            ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosOtraDocumentacion WHERE nroDeOrden = (?) AND numeroDocumento = (?);");
            ps.setInt(1, orden);
            ps.setInt(2, numeroDocumento);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nombre = rs.getString(1);
            }
        }
    } catch (SQLException e) {
        FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo otra documentación " + numeroDocumento + " no encontrado");
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

    if ("".equals(nombre)) {
        return "No existe archivo para Otra Documentación " + numeroDocumento;
    } else {
        return nombre;
    }
}

    public String buscarNombreDeArchivoBluecorp(int orden, int numeroDocumento) {
    Connection con = null;
    PreparedStatement ps = null;
    String nombre = "";

    try {
        if (orden != 0 && numeroDocumento >= 1 && numeroDocumento <= 3) {
            con = DAO.getConnection();
            ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosliquidacionbluecorp WHERE nroDeOrden = (?) AND numeroDocumento = (?);");
            ps.setInt(1, orden);
            ps.setInt(2, numeroDocumento);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nombre = rs.getString(1);
            }
        }
    } catch (SQLException e) {
        FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo otra documentación " + numeroDocumento + " no encontrado");
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

    if ("".equals(nombre)) {
        return "No existe archivo para BlueCorp " + numeroDocumento;
    } else {
        return nombre;
    }
}

    public String buscarNombreDeArchivoBlueCorp(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosLiquidacionBlueCorp WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo liquidacionbluecorp uno no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo para liquidacionbluecorp 1";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoBlueCorpDos(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosLiquidacionBlueCorpDos WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo liquidacionbluecorp 2 no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo para liquidacionbluecorp 2";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoBlueCorpTres(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosLiquidacionBlueCorpTres WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo liquidacionbluecorp 3 no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo para liquidacionbluecorp 3";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoOtraDocumentacion(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosOtraDocumentacion WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo otra documentación uno no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo para Otra Documentación";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoOtraDocumentacionDos(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosOtraDocumentacionDos WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo otra documentación Dos no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo para Otra Documentación dos";
        } else {
            return nombre;

        }
    }

    public String buscarNombreDeArchivoOtraDocumentacionTres(int orden) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
        try {
            if (orden != 0) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentosOtraDocumentacionTres WHERE nroDeOrden = (?);");
                ps.setInt(1, orden);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo otra documentación Tres no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo para Otra Documentación Tres";
        } else {
            return nombre;

        }
    }
    
    public String buscarNombreFrenteDniParaExpSinCarpeta(String cuit) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
         long cuitLong = 0;
            
            if (!"0".equals(cuit) && cuit != null && !"".equals(cuit))  {
                cuitLong = Long.parseLong(cuit);
            } 

        try {       
            if (!"0".equals(cuit)) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM frenteDniExpSinCarpeta WHERE nroDeCuit = (?);");
                ps.setLong(1, cuitLong);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo frente dni exp. sin Carpeta no enontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo para exp. sin carpeta frente dni";
        } else {
            return nombre;

        }
    
    }

    public String buscarNombreDorsoDniParaExpSinCarpeta(String cuit) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
         long cuitLong = 0;
            
            if (!"0".equals(cuit) && cuit != null && !"".equals(cuit))  {
                cuitLong = Long.parseLong(cuit);
            } 

        try {       
            if (!"0".equals(cuit)) {
                con = DAO.getConnection();
                ps = con.prepareStatement("SELECT nombreDelDocumento FROM dorsoDniExpSinCarpeta WHERE nroDeCuit = (?);");
                ps.setLong(1, cuitLong);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    nombre = rs.getString(1);
                }
                con.close();
            }
        } catch (SQLException e) {
            FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo dorso dni exp. sin Carpeta no encontrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if ("".equals(nombre)) {
            return "no existe archivo para dorso dni de exp. sin carpeta";
        } else {
            return nombre;

        }
    
    }
    
    public String buscarNombreDeArchivo(int orden, String nombreDeArchivo) {
        Connection con;
        PreparedStatement ps;
        String nombre = "";
       
                    try {
                        if (orden != 0) {
                            con = DAO.getConnection();
                            ps = con.prepareStatement("SELECT nombreDelDocumento FROM documentos"+nombreDeArchivo+" WHERE nroDeOrden = (?);");
                            ps.setInt(1, orden);

                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                nombre = rs.getString(1);
                            }
                            con.close();
                        }
                    } catch (SQLException e) {
                        FacesMessage msg = new FacesMessage(ERROR, "Nombre del archivo "+nombreDeArchivo+" no encontrado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    if ("".equals(nombre)) {
                        return "no existe archivo para "+nombreDeArchivo;
                    } else {
                        return nombre;

                    }
        
    }

}

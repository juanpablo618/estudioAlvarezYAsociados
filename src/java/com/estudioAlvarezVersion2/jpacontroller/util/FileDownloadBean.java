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
public class FileDownloadBean  implements Serializable{
 
    private static final long serialVersionUID = 626953318628565453L;

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
    
    
    
    
    /*
        no estamos usando este metodo luego borrar.
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
    }*/

    public void downloadPDF(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        try {
            if(orden != 0){
            con = DAO.getConnection();
			ps = con.prepareStatement("SELECT documento FROM documentos WHERE nroDeOrden = (?);");
			ps.setInt(1, orden);
                        
			ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                                InputStream stream = rs.getBinaryStream("documento");
                                filePdf = new DefaultStreamedContent(stream, "application/pdf", "documento.pdf");
                        }
                        con.close();
                        if(filePdf != null){
                            FacesMessage msg = new FacesMessage("Exito", "archivo PDF 1 descargado exitosamente.");
                             FacesContext.getCurrentInstance().addMessage(null, msg);

                        }else{
                            FacesMessage msg = new FacesMessage("Error", "No existe archivo PDF 1 para este nro de orden: "+orden);
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
			
            }else{
                FacesMessage msg = new FacesMessage("Error", "no se encontro documento pdf 1 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
        } catch (SQLException e) {
                   FacesMessage msg = new FacesMessage("Error", "Fichero PDF 1 no descargado");
                   FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadPDFDos(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        try {
            if(orden != 0){
            con = DAO.getConnection();
			ps = con.prepareStatement("SELECT documento FROM documentos2 WHERE nroDeOrden = (?);");
			ps.setInt(1, orden);
                        
			ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                                InputStream stream = rs.getBinaryStream("documento");
                                filePdfDos = new DefaultStreamedContent(stream, "application/pdf", "documento.pdf");
                        }
                        con.close();
                        if(filePdfDos != null){
                            FacesMessage msg = new FacesMessage("Exito", "archivo PDF 2 descargado exitosamente.");
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "No existe archivo PDF 2 para este nro de orden: "+orden);
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
            }else{
                FacesMessage msg = new FacesMessage("Error", "no se encontro documento pdf 2 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (SQLException e) {
                   FacesMessage msg = new FacesMessage("Error", "Fichero PDF 2 no descargado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadPDFTres(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        try {
            if(orden != 0){
            con = DAO.getConnection();
			ps = con.prepareStatement("SELECT documento FROM documentos3 WHERE nroDeOrden = (?);");
			ps.setInt(1, orden);
                        
			ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                                InputStream stream = rs.getBinaryStream("documento");
                                filePdfTres = new DefaultStreamedContent(stream, "application/pdf", "documento.pdf");
                        }
                        
                        con.close();
                        if(filePdfTres != null){
                            FacesMessage msg = new FacesMessage("Exito", "archivo PDF 3 descargado exitosamente.");
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "No existe archivo PDF 3 para este nro de orden: "+orden);
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
            }else{
                FacesMessage msg = new FacesMessage("Error", "no se encontro documento pdf 3 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (SQLException e) {
                   FacesMessage msg = new FacesMessage("Error", "Fichero PDF 3 no descargado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadPDFCuatro(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        try {
            if(orden != 0){
            con = DAO.getConnection();
			ps = con.prepareStatement("SELECT documento FROM documentos4 WHERE nroDeOrden = (?);");
			ps.setInt(1, orden);
                        
			ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                                InputStream stream = rs.getBinaryStream("documento");
                                filePdfCuatro = new DefaultStreamedContent(stream, "application/pdf", "documento.pdf");
                        }
                        
                        con.close();
                        if(filePdfCuatro != null){
                            FacesMessage msg = new FacesMessage("Exito", "archivo PDF 4 descargado exitosamente.");
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "No existe archivo PDF 4 para este nro de orden: "+orden);
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
            }else{
                FacesMessage msg = new FacesMessage("Error", "no se encontro documento pdf 4 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (SQLException e) {
                   FacesMessage msg = new FacesMessage("Error", "Fichero PDF 4 no descargado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadPDFCinco(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        try {
            if(orden != 0){
            con = DAO.getConnection();
			ps = con.prepareStatement("SELECT documento FROM documentos5 WHERE nroDeOrden = (?);");
			ps.setInt(1, orden);
                        
			ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                                InputStream stream = rs.getBinaryStream("documento");
                                filePdfCinco = new DefaultStreamedContent(stream, "application/pdf", "documento.pdf");
                        }
                        con.close();
                        if(filePdfCinco != null){
                            FacesMessage msg = new FacesMessage("Exito", "archivo PDF 5 descargado exitosamente.");
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }else{
                            FacesMessage msg = new FacesMessage("Error", "No existe archivo PDF 5 para este nro de orden: "+orden);
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
            }else{
                FacesMessage msg = new FacesMessage("Error", "no se encontro documento pdf 5 con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (SQLException e) {
                   FacesMessage msg = new FacesMessage("Error", "Fichero PDF 5 no descargado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public String buscarNombreDeArchivoPDF(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        String nombre = "";
        try {
            if(orden != 0){
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
        if("".equals(nombre)){
            return "no existe pdf Uno para este expediente";
        }else{
        return nombre;
    
        }
    }
    
    public String buscarNombreDeArchivoPDFDos(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        String nombre = "";
        try {
            if(orden != 0){
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
                   FacesMessage msg = new FacesMessage("ERROR", "Nombre del archivo pdf dos no encontrado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if("".equals(nombre)){
            return "no existe pdf Dos para este expediente";
        }else{
        return nombre;
    
        }
    }
    
    public String buscarNombreDeArchivoPDFTres(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        String nombre = "";
        try {
            if(orden != 0){
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
                   FacesMessage msg = new FacesMessage("ERROR", "Nombre del archivo pdf tres no encontrado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if("".equals(nombre)){
            return "no existe pdf Tres para este expediente";
        }else{
        return nombre;
    
        }
    }
    
    public String buscarNombreDeArchivoPDFCuatro(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        String nombre = "";
        try {
            if(orden != 0){
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
                   FacesMessage msg = new FacesMessage("ERROR", "Nombre del archivo pdf cuatro no encontrado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if("".equals(nombre)){
            return "no existe pdf Cuatro para este expediente";
        }else{
        return nombre;
        }
    }
    
    public String buscarNombreDeArchivoPDFCinco(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        String nombre = "";
        try {
            if(orden != 0){
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
                   FacesMessage msg = new FacesMessage("ERROR", "Nombre del archivo pdf cinco no encontrado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if("".equals(nombre)){
            return "no existe pdf Cinco para este expediente";
        }else{
        return nombre;
        }
    }
    
    
    
    public void downloadJPG(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        try {
            if(orden != 0){
            con = DAO.getConnection();
			ps = con.prepareStatement("SELECT documento FROM documentosjpg WHERE nroDeOrden = (?);");
			ps.setInt(1, orden);
                        
			ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                                InputStream stream = rs.getBinaryStream("documento");
                                file = new DefaultStreamedContent(stream, "image/jpeg", "imagen.jpg");
                        }
                        
                        con.close();
                        if(file != null){
                            FacesMessage msg = new FacesMessage("Exito", "archivo JPG uno descargado exitosamente.");
                             FacesContext.getCurrentInstance().addMessage(null, msg);

                        }else{
                            FacesMessage msg = new FacesMessage("ERROR", "No existe archivo JPG uno para este nro de orden: "+orden);
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
			
            }else{
                FacesMessage msg = new FacesMessage("ERROR", "no se encontro imagen JPG(1) con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
        } catch (SQLException e) {
                   FacesMessage msg = new FacesMessage("ERROR", "Fichero JPG(1) uno no descargado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadJPGDos(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        try {
            if(orden != 0){
            con = DAO.getConnection();
			ps = con.prepareStatement("SELECT documento FROM documentosjpg2 WHERE nroDeOrden = (?);");
			ps.setInt(1, orden);
                        
			ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                                InputStream stream = rs.getBinaryStream("documento");
                                fileDos = new DefaultStreamedContent(stream, "image/jpeg", "imagen.jpg");
                        }
                        
                        con.close();
                        if(fileDos != null){
                            FacesMessage msg = new FacesMessage("Exito", "archivo JPG dos descargado exitosamente.");
                             FacesContext.getCurrentInstance().addMessage(null, msg);

                        }else{
                            FacesMessage msg = new FacesMessage("ERROR", "No existe archivo JPG dos para este nro de orden: "+orden);
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
			
            }else{
                FacesMessage msg = new FacesMessage("ERROR", "no se encontro imagen JPG(2) con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
        } catch (SQLException e) {
                   FacesMessage msg = new FacesMessage("ERROR", "Fichero JPG(2) uno no descargado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadJPGTres(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        try {
            if(orden != 0){
            con = DAO.getConnection();
			ps = con.prepareStatement("SELECT documento FROM documentosjpg3 WHERE nroDeOrden = (?);");
			ps.setInt(1, orden);
                        
			ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                                InputStream stream = rs.getBinaryStream("documento");
                                fileTres = new DefaultStreamedContent(stream, "image/jpeg", "imagen.jpg");
                        }
                        
                        con.close();
                        if(fileTres != null){
                            FacesMessage msg = new FacesMessage("Exito", "archivo JPG tres descargado exitosamente.");
                             FacesContext.getCurrentInstance().addMessage(null, msg);

                        }else{
                            FacesMessage msg = new FacesMessage("ERROR", "No existe archivo JPG tres para este nro de orden: "+orden);
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
			
            }else{
                FacesMessage msg = new FacesMessage("ERROR", "no se encontro imagen JPG(3) con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
        } catch (SQLException e) {
                   FacesMessage msg = new FacesMessage("ERROR", "Fichero JPG(3) uno no descargado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadJPGCuatro(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        try {
            if(orden != 0){
            con = DAO.getConnection();
			ps = con.prepareStatement("SELECT documento FROM documentosjpg4 WHERE nroDeOrden = (?);");
			ps.setInt(1, orden);
                        
			ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                                InputStream stream = rs.getBinaryStream("documento");
                                fileCuatro = new DefaultStreamedContent(stream, "image/jpeg", "imagen.jpg");
                        }
                        
                        con.close();
                        if(fileCuatro != null){
                            FacesMessage msg = new FacesMessage("Exito", "archivo JPG cuatro descargado exitosamente.");
                             FacesContext.getCurrentInstance().addMessage(null, msg);

                        }else{
                            FacesMessage msg = new FacesMessage("ERROR", "No existe archivo JPG cuatro para este nro de orden: "+orden);
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
			
            }else{
                FacesMessage msg = new FacesMessage("ERROR", "no se encontro imagen JPG(4) con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
        } catch (SQLException e) {
                   FacesMessage msg = new FacesMessage("ERROR", "Fichero JPG(4) uno no descargado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void downloadJPGCinco(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        try {
            if(orden != 0){
            con = DAO.getConnection();
			ps = con.prepareStatement("SELECT documento FROM documentosjpg5 WHERE nroDeOrden = (?);");
			ps.setInt(1, orden);
                        
			ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                                InputStream stream = rs.getBinaryStream("documento");
                                fileCinco = new DefaultStreamedContent(stream, "image/jpeg", "imagen.jpg");
                        }
                        
                        con.close();
                        if(fileCinco != null){
                            FacesMessage msg = new FacesMessage("Exito", "archivo JPG cinco descargado exitosamente.");
                             FacesContext.getCurrentInstance().addMessage(null, msg);

                        }else{
                            FacesMessage msg = new FacesMessage("ERROR", "No existe archivo JPG cinco para este nro de orden: "+orden);
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
			
            }else{
                FacesMessage msg = new FacesMessage("ERROR", "no se encontro imagen JPG(5) con ese nro de orden.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
        } catch (SQLException e) {
                   FacesMessage msg = new FacesMessage("ERROR", "Fichero JPG cinco no descargado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    
    public String buscarNombreDeArchivoJPG(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        String nombre = "";
        try {
            if(orden != 0){
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
        if("".equals(nombre)){
            return "no existe imagen uno para este expediente";
        }else{
        return nombre;
    
        }
    }
    
    public String buscarNombreDeArchivoJPGDos(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        String nombre = "";
        try {
            if(orden != 0){
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
                   FacesMessage msg = new FacesMessage("ERROR", "Nombre del archivo JPG Dos no encontrado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if("".equals(nombre)){
            return "no existe imagen Dos para este expediente";
        }else{
        return nombre;
    
        }
    }
    
    public String buscarNombreDeArchivoJPGTres(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        String nombre = "";
        try {
            if(orden != 0){
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
                   FacesMessage msg = new FacesMessage("ERROR", "Nombre del archivo JPG Tres no encontrado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if("".equals(nombre)){
            return "no existe imagen Tres para este expediente";
        }else{
        return nombre;
    
        }
    }
    
    public String buscarNombreDeArchivoJPGCuatro(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        String nombre = "";
        try {
            if(orden != 0){
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
                   FacesMessage msg = new FacesMessage("ERROR", "Nombre del archivo JPG Cuatro no encontrado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if("".equals(nombre)){
            return "no existe imagen Cuatro para este expediente";
        }else{
        return nombre;
    
        }
    }
    
    public String buscarNombreDeArchivoJPGCinco(int orden){
        Connection con = null;
	PreparedStatement ps = null;
        String nombre = "";
        try {
            if(orden != 0){
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
                   FacesMessage msg = new FacesMessage("ERROR", "Nombre del archivo JPG Cinco no encontrado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        if("".equals(nombre)){
            return "no existe imagen Cinco para este expediente";
        }else{
        return nombre;
    
        }
    }
    
}

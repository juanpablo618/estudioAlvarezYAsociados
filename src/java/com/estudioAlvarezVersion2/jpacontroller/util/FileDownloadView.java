/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpacontroller.util;

import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "fileBean")
@RequestScoped

public class FileDownloadView {

    private StreamedContent file;
    private StreamedContent file2;
    private StreamedContent file3; 
    public FileDownloadView() {
        file = new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/documentos/Carta_Poder.pdf"), "application/pdf", "Carta_Poder.pdf");
       
        file2 = new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/documentos/Solicitud_de_Prestaciones_Previsionales_INTERACTIVO.pdf"), "application/pdf", "Prestaciones_Interactivo.pdf");
        
        file3 = new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/documentos/cronologioDeAportes.xls"), "application/vnd.ms-excel", "cronologioDeAportes.xls");
       
    }
 
    public StreamedContent getFile() {
        return file;
    }
    
    public StreamedContent getFile2() {
        return file2;
    }
    
    public StreamedContent getFile3() {
        return file3;
    }
}
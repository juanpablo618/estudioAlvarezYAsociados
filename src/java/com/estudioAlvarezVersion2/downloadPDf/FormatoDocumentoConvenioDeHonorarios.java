/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.downloadPDf;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import javax.faces.context.FacesContext;

/**
 *
 * @author developer
 */
public class FormatoDocumentoConvenioDeHonorarios extends PdfPageEventHelper
{    
    private Image imagen;
    PdfPTable table = new PdfPTable(2);
        
    /**
     * Constructor de la clase, inicializa la imagen que se utilizara en el membrete
     */
    public FormatoDocumentoConvenioDeHonorarios()
    {
        try
        {
             FacesContext context = FacesContext.getCurrentInstance();
            ConfiguracionesGeneralesController configuracionesGeneralesController = context.getApplication().evaluateExpressionGet(context, "#{configuracionesGeneralesController}", ConfiguracionesGeneralesController.class);
                              
            imagen = Image.getInstance(configuracionesGeneralesController.getConfiguracionesGenerales(1).getUrlDeLogoMundoLimpieza());
            imagen.setAbsolutePosition(10, 650f);            
            
            
            table.setTotalWidth(350f);            
            
        }catch(Exception r)
        {
            System.err.println("Error al leer la imagen");
        }    
    }
    
    /**
     * Manejador del evento onEndPage, usado para generar el encabezado
     */
    public void onEndPage(PdfWriter writer, Document document) {

        try{            
            document.add(imagen);
            table.writeSelectedRows(0, -1, 140f, 700f, writer.getDirectContent());
            
         }catch(Exception doc)
         {
             doc.printStackTrace();
         }         
     }
}
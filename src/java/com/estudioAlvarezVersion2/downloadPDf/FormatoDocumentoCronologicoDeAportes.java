package com.estudioAlvarezVersion2.downloadPDf;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import javax.faces.context.FacesContext;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */
public class FormatoDocumentoCronologicoDeAportes extends PdfPageEventHelper
{    
    private Image imagen;
    PdfPTable table = new PdfPTable(2);
        
    /**
     * Constructor de la clase, inicializa la imagen que se utilizara en el membrete
     */
    public FormatoDocumentoCronologicoDeAportes()
    {
        try
        {
             FacesContext context = FacesContext.getCurrentInstance();
            ConfiguracionesGeneralesController configuracionesGeneralesController = context.getApplication().evaluateExpressionGet(context, "#{configuracionesGeneralesController}", ConfiguracionesGeneralesController.class);
      //TODO terminar esto no dejarlo así
            
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
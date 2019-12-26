/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.downloadPDf;

import com.estudioAlvarezVersion2.jpa.Agenda;
import com.estudioAlvarezVersion2.jpa.Turno;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.faces.context.FacesContext;

/**
 *
 * @author developer
 */

public class MembretePresupuesto {

    /**
     * Crea un documento con encabezado
     *
     * @param filename Nombre del archivo
     * @param agendasFiltradas
     * @param turnosFiltrados
     *
     */
    public void createPdf(String filename , ArrayList<Agenda> agendasFiltradas , ArrayList<Turno> turnosFiltrados ) throws IOException, DocumentException {
        
        
        Document document = new Document(PageSize.LETTER, 36, 36, 140, 36);
        
        // Date fechaDiaria = Calendar.getInstance().getTime();

       FacesContext context = FacesContext.getCurrentInstance();
       ConfiguracionesGeneralesController configuracionesGeneralesController = context.getApplication().evaluateExpressionGet(context, "#{configuracionesGeneralesController}", ConfiguracionesGeneralesController.class);
       
       PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(configuracionesGeneralesController.getConfiguracionesGenerales(1).getCarpetaDePresupuestos().concat(filename).concat(".pdf")));

       FormatoDocumentoPresupuesto encabezado = new FormatoDocumentoPresupuesto();
       Paragraph parrafo;
       int i = 0;

        // indicamos que objecto manejara los eventos al escribir el Pdf
        writer.setPageEvent(encabezado);

        document.open();

        parrafo = new Paragraph("Estudio Alvarez y Asociados");
        parrafo.setAlignment(Element.ALIGN_CENTER);

        document.add(parrafo);

        document.add(Chunk.NEWLINE);

        Paragraph parrafo4 = new Paragraph("AGENDAS:");
        document.add(parrafo4);



        
        String formato="dd-MM-yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
                
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(3);
        
        float[] medidaCeldas = {0.55f, 0.55f, 2.00f};

        // ASIGNAS LAS MEDIDAS A LA TABLA (ANCHO)
        table.setWidths(medidaCeldas);
        
        table.addCell("ORDEN");
        table.addCell("Nombre y Apellido");
        table.addCell("Descripción:");
        
        for (Agenda agenda : agendasFiltradas) {

            PdfPCell cell = new PdfPCell(new Paragraph(agenda.getOrden()));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            table.addCell(cell);
            
            PdfPCell cell2 = new PdfPCell(new Paragraph(agenda.getApellido().concat(" ").concat(agenda.getNombre())));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            table.addCell(cell2);
            
            PdfPCell cell3 = new PdfPCell(new Paragraph(agenda.getDescripcion()));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            table.addCell(cell3);
            
            
            
        }
        
        PdfPTable table2 = new PdfPTable(2);
        
        float[] medidaCeldas2 = {0.55f, 2.25f};

        // ASIGNAS LAS MEDIDAS A LA TABLA (ANCHO)
        table2.setWidths(medidaCeldas2);
        

        table2.addCell("Nombre y Apellido");
        table2.addCell("Observación:");
        
        
        for (Turno turno : turnosFiltrados) {

            PdfPCell cell = new PdfPCell(new Paragraph(turno.getApellido().concat(" ").concat(turno.getApellido())));
            
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            table2.addCell(cell);
            

            PdfPCell cell2 = new PdfPCell(new Paragraph(turno.getObservacion()));
            
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            table2.addCell(cell2);

            
        }

        
        document.add(table);

        Paragraph parrafo6 = new Paragraph("TURNOS:");
        document.add(parrafo6);

        document.add(table2);

        document.close();
        
        }
}
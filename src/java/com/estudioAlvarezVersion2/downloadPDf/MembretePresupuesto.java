package com.estudioAlvarezVersion2.downloadPDf;

import com.estudioAlvarezVersion2.jpa.Agenda;
import com.estudioAlvarezVersion2.jpa.Turno;
import com.estudioAlvarezVersion2.jsf.TurnoController;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.faces.context.FacesContext;

/**
 *
 * @author cuello.juanpablo@gmail.com
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
    public void createPdf(String filename  ) throws IOException, DocumentException {
        System.out.println("Entro al createPdf !!!");
        Document document = new Document(PageSize.LETTER, 36, 36, 140, 36);
        
        // Date fechaDiaria = Calendar.getInstance().getTime();

       FacesContext context = FacesContext.getCurrentInstance();
       ConfiguracionesGeneralesController configuracionesGeneralesController = 
               context.getApplication().evaluateExpressionGet(context, 
                       "#{configuracionesGeneralesController}", ConfiguracionesGeneralesController.class);
       
       
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

        PdfPTable table = new PdfPTable(1);
        
        float[] medidaCeldas = {0.95f};

        // ASIGNAS LAS MEDIDAS A LA TABLA (ANCHO)
        table.setWidths(medidaCeldas);
        
        //table.addCell("ORDEN");
        //table.addCell("Nombre y Apellido");
        //table.addCell("Descripción:");
        
        /*for (Agenda agenda : agendasFiltradas) {
        
            Font font1 = new Font(Font.TIMES_ROMAN, 10, Font.BOLD);
            Font boldFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
        
                Phrase firstLine = null;
                
            if (agenda.getApellido() != null && agenda.getNombre() != null) {
                 firstLine = new Phrase(" "+agenda.getApellido().concat(" "+agenda.getNombre()), boldFont );
            }
            
            Paragraph orden = new Paragraph("orden: "+agenda.getOrden());
            
            
            if (agenda.getApellido() != null && agenda.getNombre() != null) {
                Paragraph nombreYApellidoEnBold = new Paragraph(agenda.getApellido().concat(" "+agenda.getNombre()), boldFont );
                Paragraph ObservacionYResponsable = new Paragraph(agenda.getApellido().concat(agenda.getNombre()), boldFont );
            }

    
            
            if(agenda.getApellido() != null && agenda.getApellido() != null && 
               agenda.getNombre() != null && agenda.getResponsable() != null && 
               agenda.getDescripcion() != null){
            
            PdfPCell cell = new PdfPCell(new Paragraph(" orden: "+agenda.getOrden()
                    + firstLine
                    + "\n Observación: "+ agenda.getDescripcion() + " Responsable: " +agenda.getResponsable() ));
            
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            }
                       
        }*/
        
        PdfPTable table2 = new PdfPTable(31);
        
        float[] medidaCeldas2 = {0.55f,0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f};

        // ASIGNAS LAS MEDIDAS A LA TABLA (ANCHO)
        table2.setWidths(medidaCeldas2);
        
        table2.addCell("Responsable");
        
        table2.addCell("dia 1");
        table2.addCell("día 2");
        table2.addCell("día 3");
        table2.addCell("día 4");
        table2.addCell("día 5");
        table2.addCell("día 6");
        table2.addCell("día 7");
        table2.addCell("día 8");
        table2.addCell("día 9");
        table2.addCell("día 10");
        table2.addCell("día 11");
        table2.addCell("día 12");
        table2.addCell("día 13");
        table2.addCell("día 14");
        table2.addCell("día 15");
        
        table2.addCell("dia 16");
        table2.addCell("día 17");
        table2.addCell("día 18");
        table2.addCell("día 19");
        table2.addCell("día 20");
        table2.addCell("día 21");
        table2.addCell("día 22");
        table2.addCell("día 23");
        table2.addCell("día 24");
        table2.addCell("día 25");
        table2.addCell("día 26");
        table2.addCell("día 27");
        table2.addCell("día 28");
        table2.addCell("día 29");
        table2.addCell("día 30");
        table2.addCell("día 31");
        
       TurnoController turnoController = 
               context.getApplication().evaluateExpressionGet(context, 
                       "#{turnoController}", TurnoController.class);
       
        
        
        for (Turno turno : turnoController.getItems()) {
        
             PdfPCell cell3 = new PdfPCell(new Paragraph(turno.getResponsable()));

            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            table2.addCell(cell3);
           
            
            PdfPCell cell = new PdfPCell(new Paragraph(turno.getApellido()
                    .concat(" ").concat(turno.getApellido())));

            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            table2.addCell(cell);
            

            PdfPCell cell2 = new PdfPCell(new Paragraph(turno.getObservacion()));
            
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            table2.addCell(cell2);
            break;
        }

        document.add(table);

        Paragraph parrafo6 = new Paragraph("TURNOS:");
        document.add(parrafo6);

        document.add(table2);

        document.close();
        
        }
    
    
    public void createPdfConvenioDeHonorarios(String filename , String nombre , String apellido, String dni, String direccion, String nroDeAltura, String barrio) throws IOException, DocumentException {
        
        Document document = new Document(PageSize.LETTER, 36, 36, 140, 36);
        
        // Date fechaDiaria = Calendar.getInstance().getTime();

       FacesContext context = FacesContext.getCurrentInstance();
       ConfiguracionesGeneralesController configuracionesGeneralesController = 
               context.getApplication().evaluateExpressionGet(context, 
                       "#{configuracionesGeneralesController}", ConfiguracionesGeneralesController.class);
       
       PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(configuracionesGeneralesController.getConfiguracionesGenerales(1).getCarpetaDePresupuestos().concat(filename).concat(".pdf")));

       FormatoDocumentoConvenioDeHonorarios encabezado = new FormatoDocumentoConvenioDeHonorarios();
       Paragraph parrafo;
       int i = 0;

        // indicamos que objecto manejara los eventos al escribir el Pdf
        writer.setPageEvent(encabezado);

        document.open();

        Paragraph parrafo4 = new Paragraph("CONVENIO DE HONORARIOS:");
        parrafo4.setAlignment(Element.ALIGN_CENTER);

        document.add(parrafo4);
        
        String formato="dd-MM-yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
                
        document.add(Chunk.NEWLINE);
        

        Paragraph parrafo6 = new Paragraph("Entre la Sr/Sra. "+apellido+" "+nombre+" – D.N.I. Nº "+dni+", en adelante\n" +
"EL CLIENTE, con domicilio en "+direccion+" Nº "+nroDeAltura+" – B°"+barrio+" CÓRDOBA y el Estudio Jurídico\n" +
"ALVAREZ Y ASOCIADOS, representado por el Dr. Mateo Francisco Álvarez; DNI. Nº\n" +
"32.682.328, y la Dra. María Emilia Campos; DNI. Nº 31.318.721, con domicilio en calle\n" +
"San José de Calasanz 43, Segundo piso, Dto. “B”; ambos de la ciudad de Córdoba, se\n" +
"conviene:\n" +
"1. Que el Estudio Jurídico ALVAREZ Y ASOCIADOS toma a su cargo la gestión y\n" +
"diligenciamiento de los trámites administrativos para efectuar la confección de Plan\n" +
"SICAM y Reconocimiento de Servicios ante A.N.Se.S. del CLIENTE, poniendo para ello la\n" +
"dedicación y diligencia necesarios.-\n" +
"2. Que por este trámite EL CLIENTE, abonará al Estudio Jurídico ALVAREZ Y ASOCIADOS\n" +
"el importe equivalente a un (1) mes de haberes jubilatorios calculados al momento de\n" +
"la entrega del Reconocimiento de Servicios por ANSES, con un mínimo que se\n" +
"conviene en la suma de pesos catorce mil sesenta y ocho ($14.068).-\n" +
"3. Que el pago se realizará al momento en que el trámite sea resuelto\n" +
"favorablemente.-\n" +
"4. Que las partes someten todo diferendo, controversia o reclamo que surja entre ellas\n" +
"con motivo del presente convenio o que esté relacionado con el mismo, a la\n" +
"jurisdicción exclusiva de los Tribunales Ordinarios de la Ciudad de Córdoba,\n" +
"renunciando a cualquier otro fuero o jurisdicción que pudiere corresponder, inclusive\n" +
"la Federal.-\n" +
"Las partes constituyen domicilio en los mencionados supra, suscribiéndose dos\n" +
"ejemplares de un mismo tenor y a un solo efecto en la Ciudad de Córdoba, a los\n" +
"días del mes de Junio del año 2019.-");
        document.add(parrafo6);

        document.add(Chunk.NEWLINE);
                
                Paragraph parrafo7 = new Paragraph(" . . . . . . . . . . . . . . . . . . . . . . . . . \n FIRMA");
                parrafo7.setAlignment(Element.ALIGN_RIGHT);

                document.add(parrafo7);
        
                Paragraph parrafo8 = new Paragraph(" . . . . . . . . . . . . . . . . . . . . . . . . . \n ACLARACIÓN");
                parrafo8.setAlignment(Element.ALIGN_RIGHT);

                document.add(parrafo8);

        document.close();
        
        }
    
     public void createPdfCronologicoDeAportes(String filename , String nombre ) throws IOException, DocumentException {
        
          float left = 0;
        float right = 0;
        float top = 0;
        float bottom = 0;
         
        Document document = new Document(PageSize.LARGE_CROWN_OCTAVO, left, right, top, bottom);
        
        document.setPageSize(PageSize.LARGE_CROWN_OCTAVO.rotate());

       FacesContext context = FacesContext.getCurrentInstance();
       ConfiguracionesGeneralesController configuracionesGeneralesController = 
               context.getApplication().evaluateExpressionGet(context, 
                       "#{configuracionesGeneralesController}", ConfiguracionesGeneralesController.class);
       
       PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(configuracionesGeneralesController.getConfiguracionesGenerales(1).getCarpetaDePresupuestos().concat(filename).concat(".xls")));

       FormatoDocumentoCronologicoDeAportes encabezado = new FormatoDocumentoCronologicoDeAportes();
       Paragraph parrafo;
       int i = 0;

        // indicamos que objecto manejara los eventos al escribir el Pdf
        writer.setPageEvent(encabezado);

        document.open();
 
			// Create a Simple table
			PdfPTable table = new PdfPTable(2);
 
			// Set First row as header
			table.setHeaderRows(1);
			// Add header details

                        // Create a new Table
			PdfPTable childTable1 = new PdfPTable(3);
			childTable1.addCell("Empresa");
			childTable1.addCell("desde");
			childTable1.addCell("hasta");
			childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        childTable1.addCell("");
                        
			// Add the data
			table.addCell(childTable1);
 
                        PdfPTable childTable2 = new PdfPTable(13);
			childTable2.addCell("EMPRESA");
			childTable2.addCell("D");
			childTable2.addCell("M");
			childTable2.addCell("A");

                        childTable2.addCell("D");
                        childTable2.addCell("M");
                        childTable2.addCell("A");
                        childTable2.addCell("A");
                        childTable2.addCell("M");
                        childTable2.addCell("D");
                        childTable2.addCell("A");
                        childTable2.addCell("M");
                        childTable2.addCell("D");
                                        
			// Add the new table to the Cell of parent table
			table.addCell(childTable2);
			
			// Add more data
			table.addCell("");
			table.addCell("");
			table.addCell("");
 
			document.add(table);
 
			// close the document
			document.close();
        
        }
}
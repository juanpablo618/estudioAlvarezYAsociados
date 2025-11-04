package com.estudioAlvarezVersion2.downloadPDf;

import com.estudioAlvarezVersion2.jpa.Turno;
import com.estudioAlvarezVersion2.jsf.TurnoController;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.ExternalContext;
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
     * @throws java.io.IOException
     * @throws com.lowagie.text.DocumentException
     *
     */
    public void createPdf(String filename) throws IOException, DocumentException {
        Document document = new Document(PageSize.LETTER, 36, 36, 140, 36);

        // Date fechaDiaria = Calendar.getInstance().getTime();
        FacesContext context = FacesContext.getCurrentInstance();
        ConfiguracionesGeneralesController configuracionesGeneralesController
                = context.getApplication().evaluateExpressionGet(context,
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

        String formato = "dd-MM-yyyy";
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

        float[] medidaCeldas2 = {0.55f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f, 0.55f, 0.55f, 2.25f};

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

        TurnoController turnoController
                = context.getApplication().evaluateExpressionGet(context,
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

    /**
     *
     * @param filename
     * @param nombre
     * @param apellido
     * @param dni
     * @param direccion
     * @param nroDeAltura
     * @param barrio
     * @throws IOException
     * @throws DocumentException
     */
    public void createPdfConvenioDeHonorarios(String filename, String nombre, String apellido, String dni, String direccion, String nroDeAltura, String barrio) throws IOException, DocumentException {

        Document document = new Document(PageSize.LETTER, 36, 36, 140, 36);

        // Date fechaDiaria = Calendar.getInstance().getTime();
        FacesContext context = FacesContext.getCurrentInstance();
        ConfiguracionesGeneralesController configuracionesGeneralesController
                = context.getApplication().evaluateExpressionGet(context,
                        "#{configuracionesGeneralesController}", ConfiguracionesGeneralesController.class);

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(configuracionesGeneralesController.getConfiguracionesGenerales(1).getCarpetaDePresupuestos().concat(filename).concat(".pdf")));

        FormatoDocumentoConvenioDeHonorarios encabezado = new FormatoDocumentoConvenioDeHonorarios();
        Paragraph parrafo;
        int i = 0;

        // indicamos que objecto manejara los eventos al escribir el Pdf
        writer.setPageEvent(encabezado);

        document.open();

        Paragraph parrafo4 = new Paragraph("JUBILACIÓN CON TAREAS CUIDADO:");
        parrafo4.setAlignment(Element.ALIGN_CENTER);

        document.add(parrafo4);

        String formato = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);

        document.add(Chunk.NEWLINE);

        Paragraph parrafo6 = new Paragraph("Entre la Sr/Sra. " + apellido + " " + nombre + " – D.N.I. Nº " + dni + ", en adelante\n"
                + "EL CLIENTE, con domicilio en " + direccion + " Nº " + nroDeAltura + " – B°" + barrio + " CÓRDOBA y el Estudio Jurídico\n"
                + "ALVAREZ Y ASOCIADOS, representado por el Dr. Mateo Francisco Álvarez; DNI. Nº\n"
                + "32.682.328, y la Dra. María Emilia Campos; DNI. Nº 31.318.721, con domicilio en calle\n"
                + "San José de Calasanz 43, Segundo piso, Dto. “B”; ambos de la ciudad de Córdoba, se\n"
                + "conviene:\n"
                + "1. Que el Estudio Jurídico ALVAREZ Y ASOCIADOS toma a su cargo la gestión y\n"
                + "diligenciamiento de los trámites administrativos para efectuar la confección de Plan\n"
                + "SICAM y Reconocimiento de Servicios ante A.N.Se.S. del CLIENTE, poniendo para ello la\n"
                + "dedicación y diligencia necesarios.-\n"
                + "2. Que por este trámite EL CLIENTE, abonará al Estudio Jurídico ALVAREZ Y ASOCIADOS\n"
                + "el importe equivalente a un (1) mes de haberes jubilatorios calculados al momento de\n"
                + "la entrega del Reconocimiento de Servicios por ANSES, con un mínimo que se\n"
                + "conviene en la suma de pesos catorce mil sesenta y ocho ($14.068).-\n"
                + "3. Que el pago se realizará al momento en que el trámite sea resuelto\n"
                + "favorablemente.-\n"
                + "4. Que las partes someten todo diferendo, controversia o reclamo que surja entre ellas\n"
                + "con motivo del presente convenio o que esté relacionado con el mismo, a la\n"
                + "jurisdicción exclusiva de los Tribunales Ordinarios de la Ciudad de Córdoba,\n"
                + "renunciando a cualquier otro fuero o jurisdicción que pudiere corresponder, inclusive\n"
                + "la Federal.-\n"
                + "Las partes constituyen domicilio en los mencionados supra, suscribiéndose dos\n"
                + "ejemplares de un mismo tenor y a un solo efecto en la Ciudad de Córdoba, a los\n"
                + "días del mes de Junio del año 2019.-");
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

    public void createPdfDeCaratula(String filename, String nombre, String apellido, String dni, String direccion,
            String nroDeAltura, String barrio, String apoderado, Date fechaDeAtencion, String sexo,
            String telefono, String cuit, Date fechaDeNacimiento, int edad,
            String cobraBeneficio, String tipoDeBeneficio, String aportes, String trabajando, String inscripcionAut, String estadoCivil,
            String cantidadDeHijos, String obraSocial, String claveCidi, String claveFiscal, String claveSeguridadSocial
    ) throws IOException, DocumentException {

        //Document document = new Document(PageSize.LETTER, 36, 36, 140, 36);
        Document document = new Document(PageSize.A4);

        FacesContext context = FacesContext.getCurrentInstance();
        ConfiguracionesGeneralesController configuracionesGeneralesController
                = context.getApplication().evaluateExpressionGet(context,
                        "#{configuracionesGeneralesController}", ConfiguracionesGeneralesController.class);

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(configuracionesGeneralesController.getConfiguracionesGenerales(1).getCarpetaDePresupuestos().concat(filename).concat(".pdf")));

        FormatoDocumentoConvenioDeHonorarios encabezado = new FormatoDocumentoConvenioDeHonorarios();
        Paragraph parrafo;
        int i = 0;

        // indicamos que objecto manejara los eventos al escribir el Pdf
        writer.setPageEvent(encabezado);

        document.open();

        Font font = new Font(Font.NORMAL, 10, Font.BOLD);
        

        Font font1 = new Font(Font.NORMAL, 8);

        Format formatter = new SimpleDateFormat("dd-MM-yyyy");

        String stringFechaDeAtencion;
        if (fechaDeAtencion != null) {
            stringFechaDeAtencion = formatter.format(fechaDeAtencion);
        } else {
            stringFechaDeAtencion = "........................";
        }

        String stringFechaDeNacimiento = formatter.format(fechaDeNacimiento);
        apoderado = validateNullOrEmpty(apoderado);
        
        Paragraph opciones = new Paragraph();
        opciones.setAlignment(Element.ALIGN_RIGHT);

        Paragraph opciones2 = new Paragraph();
        opciones2.setAlignment(Element.ALIGN_LEFT);

        
        opciones2.add(new Chunk("APODERADO: ",font));
        opciones2.add(new Chunk(apoderado));
        opciones2.add("\n");
        opciones2.add("\n");
        
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String absolutePath = externalContext.getRealPath("/resources/images/D&A_Logo-09.jpg");
        Image image = Image.getInstance(absolutePath);
        image.scaleAbsolute(48, 48);

        image.setAlignment(Image.ALIGN_CENTER);
        document.add(image);

        document.add(opciones);
        document.add(opciones2);
        

        // Crear una tabla con una sola celda
        PdfPTable table = new PdfPTable(1);

        // Establecer el ancho de la celda al ancho de la página
        table.setWidthPercentage(100);

        // Crear una celda con una caja hecha con líneas negras y una palabra centrada dentro
        PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPadding(10);

        // Obtener el objeto PdfContentByte para dibujar la caja
        PdfContentByte canvas = writer.getDirectContent();
        canvas.setColorStroke(Color.BLACK);
        canvas.setLineWidth(1f);
        float x = document.left();
        float y = document.top() - cell.getHeight();
        float width = document.right() - document.left();
        float height = cell.getHeight();
        canvas.rectangle(x, y, width, height);

        // Agregar la palabra centrada dentro de la caja
        Paragraph tituloGeneral = new Paragraph(new Chunk("JUBILACIÓN CON TAREAS CUIDADO    Fecha de atención:" + stringFechaDeAtencion, font));
        tituloGeneral.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(tituloGeneral);

        // Agregar la celda a la tabla y la tabla al documento
        table.addCell(cell);
        document.add(table);

        document.add(Chunk.NEWLINE);


        telefono = validateNullOrEmpty(telefono);
        nombre = validateNullOrEmpty(nombre);
        apellido = validateNullOrEmpty(apellido);
        sexo = validateNullOrEmpty(sexo);

        direccion = validateNullOrEmpty(direccion);
        nroDeAltura = validateNullOrEmpty(nroDeAltura);
        barrio = validateNullOrEmpty(barrio);

        cuit = validateNullOrEmpty(cuit);
        
        claveCidi= validateNullOrEmpty(claveCidi);
        claveFiscal= validateNullOrEmpty(claveFiscal);
        claveSeguridadSocial = validateNullOrEmpty(claveSeguridadSocial);

        stringFechaDeNacimiento = validateNullOrEmpty(stringFechaDeNacimiento);

        
        

        Paragraph parrafo11 = new Paragraph();
        parrafo11.setAlignment(Element.ALIGN_LEFT);

        Chunk chunk4 = new Chunk("NOMBRE Y APELLIDO: ", font);
        Chunk chunk5 = new Chunk(nombre + " " + apellido);
        chunk5.setUnderline(0.1f, -2f);
        Chunk chunk6 = new Chunk(" SEXO: ", font);
        Chunk chunk7 = new Chunk(sexo);

        parrafo11.add(chunk4);
        parrafo11.add(chunk5);
        parrafo11.add("                                         "); // agregar varios espacios en blanco

        parrafo11.add(chunk6);

        chunk7.setUnderline(0.1f, -2f); // agregar una línea debajo del texto
        StringBuilder dotsBuilder = new StringBuilder();
        for (int h = 0; h < chunk7.getContent().length(); h++) {
            dotsBuilder.append(".");
        }
        parrafo11.add(chunk7);

        document.add(parrafo11);

        Paragraph parrafo12 = new Paragraph();
        parrafo12.setAlignment(Element.ALIGN_LEFT);
        Chunk chunk8 = new Chunk("DOMICILIO: ", font);
        Chunk chunk9 = new Chunk(direccion + " " + nroDeAltura + " " + barrio);
        chunk9.setUnderline(0.1f, -2f);
        parrafo12.add(chunk8);
        parrafo12.add(chunk9);
        document.add(parrafo12);

        Paragraph parrafo13 = new Paragraph();
        parrafo13.setAlignment(Element.ALIGN_LEFT);

        Chunk chunk10 = new Chunk("TELÉFONO: ", font);
        Chunk chunk11 = new Chunk(telefono);
        chunk11.setUnderline(0.1f, -2f);
        Chunk chunk12 = new Chunk("          CUIL: ", font);
        Chunk chunk13 = new Chunk(cuit);
        chunk13.setUnderline(0.1f, -2f);

        parrafo13.add(chunk10);
        parrafo13.add(chunk11);
        parrafo13.add(chunk12);
        parrafo13.add(chunk13);
        document.add(parrafo13);

        Paragraph parrafo14 = new Paragraph();
        parrafo14.setAlignment(Element.ALIGN_LEFT);
        parrafo14.add(new Chunk("FECHA DE NACIMIENTO: ", font));
        parrafo14.add(new Chunk(stringFechaDeNacimiento).setUnderline(0.1f, -2f));
        parrafo14.add(new Chunk(" EDAD: ", font));
        parrafo14.add(new Chunk(String.valueOf(edad)).setUnderline(0.1f, -2f));
        parrafo14.add(new Chunk("                     AÑOS:........MESES ", font));

        document.add(parrafo14);

        cobraBeneficio = validateNullOrEmpty(cobraBeneficio);
        tipoDeBeneficio = validateNullOrEmpty(tipoDeBeneficio);

        Paragraph parrafo15 = new Paragraph();
        parrafo15.setAlignment(Element.ALIGN_LEFT);

        parrafo15.add(new Chunk(" ¿COBRA ALGÚN BENEFICIO ? : ", font));
        parrafo15.add(new Chunk(cobraBeneficio).setUnderline(0.1f, -2f));
        parrafo15.add(new Chunk("                          ¿CUAL?..................  tipo de beneficio : ", font));
        parrafo15.add(new Chunk(tipoDeBeneficio).setUnderline(0.1f, -2f));

        document.add(parrafo15);

        aportes = validateNullOrEmpty(aportes);
        trabajando = validateNullOrEmpty(trabajando);
        inscripcionAut = validateNullOrEmpty(inscripcionAut);

        Paragraph parrafo16 = new Paragraph();
        parrafo16.setAlignment(Element.ALIGN_LEFT);
        parrafo16.add(new Chunk(" ¿TIENE APORTES?: ", font));
        parrafo16.add(new Chunk(aportes).setUnderline(0.1f, -2f));
        parrafo16.add(new Chunk(" ¿ESTA TRABAJANDO?: ", font));
        parrafo16.add(new Chunk(trabajando).setUnderline(0.1f, -2f));
        parrafo16.add(new Chunk(" INSC MT/AUT : ", font));
        parrafo16.add(new Chunk(inscripcionAut).setUnderline(0.1f, -2f));

        document.add(parrafo16);

        Paragraph parrafo17 = new Paragraph();
        parrafo17.setAlignment(Element.ALIGN_LEFT);
        parrafo17.add(new Chunk("FECHAS/EMPLEADORES/TIPOS DE APORTES:..........................................................", font));
        document.add(parrafo17);

        estadoCivil = validateNullOrEmpty(estadoCivil);
        cantidadDeHijos = validateNullOrEmpty(cantidadDeHijos);

        Paragraph parrafo18 = new Paragraph();
        parrafo18.setAlignment(Element.ALIGN_LEFT);
        parrafo18.add(new Chunk(" ESTADO CIVIL : ", font));
        parrafo18.add(new Chunk(estadoCivil).setUnderline(0.1f, -2f));
        parrafo18.add(new Chunk(" CANTIDAD DE HIJOS: ", font));
        parrafo18.add(new Chunk(cantidadDeHijos).setUnderline(0.1f, -2f));
        document.add(parrafo18);

        obraSocial = validateNullOrEmpty(obraSocial);

        Paragraph parrafo19 = new Paragraph();
        parrafo19.setAlignment(Element.ALIGN_LEFT);
        parrafo19.add(new Chunk(" ¿TIENE OBRA SOCIAL? : ", font));

        parrafo19.add(new Chunk(obraSocial).setUnderline(0.1f, -2f));
        parrafo19.add(Chunk.NEWLINE);
        document.add(parrafo19);

        LineSeparator ls = new LineSeparator();
        ls.setLineColor(new Color(0, 0, 0));
        ls.setLineWidth(1f);
        ls.setAlignment(Element.ALIGN_CENTER);
        ls.setPercentage(100f);
        Chunk linebreak = new Chunk(ls);

        Paragraph parrafo20 = new Paragraph();
        parrafo20.setAlignment(Element.ALIGN_CENTER);
        parrafo20.add(linebreak);
        parrafo20.add(Chunk.NEWLINE);
        parrafo20.add(new Chunk(" SEGUIMIENTO ", font));

        document.add(parrafo20);

        Paragraph options = new Paragraph();
        options.setFont(font);
        options.add(generarLinea(" PROCEDENCIA",82));
        options.add("\n");
        options.add(generarLinea(" APODERADO/RESPONSABLE",82));
        options.add("\n");
        options.add(" CLAVE FISCAL: "+claveFiscal);
        options.add("\n");
        options.add(" CLAVE SEGURIDAD SOCIAL: "+claveSeguridadSocial);
        options.add("\n");
        options.add(" CLAVE CIDI: "+claveCidi);
        options.add("\n");
        options.add(generarLinea(" DNI DIGITALIZADO",82));
        options.add("\n");
        options.add(generarLinea(" PARTIDAS ORIGINALES",82));
        options.add("\n");
        options.add(generarLinea(" FORMULARIOS",82));
        options.add("\n");
        options.add(generarLinea(" CONVENIO",82));
        options.add("\n");
        options.add(generarLinea(" SICAM",82));
        options.add("\n");
        options.add(generarLinea(" SOCIOECONOMICO",82));
        options.add("\n");
        options.add(generarLinea("OBSERVACIONES",82));
        options.add(" ............................................................................................................................................................................................ \n");
        options.add(Chunk.NEWLINE);
        document.add(options);

        /*    PdfContentByte cb = writer.getDirectContent();
                cb.setLineWidth(2f); // establecer el ancho de la línea en 2 puntos
                cb.setRGBColorStroke(0, 0, 0); // establecer el color de la línea en negro
                cb.moveTo(36, 36); // mover el cursor a la posición inicial de la línea
                cb.lineTo(document.getPageSize().getWidth() - 36, 36); // dibujar la línea hasta el final de la página
                cb.stroke();*/
        document.close();

    }

    private String validateNullOrEmpty(String variable) {
        if (variable == null || "".equals(variable)) {
            variable = ".................................";
        }
        return variable;
    }

    public String generarLinea(String palabra, int max_line_length) {
    final char DOT_CHAR = '.'; // carácter utilizado para crear los puntos
    
    int totalDots = max_line_length - palabra.length();
    StringBuilder sb = new StringBuilder();
    
    sb.append(palabra.toUpperCase()).append(": ");
    for (int i = 0; i < totalDots ; i++) {
        sb.append(DOT_CHAR);
    }
    sb.append("\n");
    return sb.toString();
}
    
    
    public void createPdfCronologicoDeAportes(String filename, String nombre) throws IOException, DocumentException {

        float left = 0;
        float right = 0;
        float top = 0;
        float bottom = 0;

        Document document = new Document(PageSize.LARGE_CROWN_OCTAVO, left, right, top, bottom);

        document.setPageSize(PageSize.LARGE_CROWN_OCTAVO.rotate());

        FacesContext context = FacesContext.getCurrentInstance();
        ConfiguracionesGeneralesController configuracionesGeneralesController
                = context.getApplication().evaluateExpressionGet(context,
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

    public void createPdfReporteFinal(String filename, String nombre, String apellido, String reporte)
            throws IOException, DocumentException {

        Document document = new Document(PageSize.A4, 36, 36, 140, 36);

        FacesContext context = FacesContext.getCurrentInstance();
        ConfiguracionesGeneralesController configuracionesGeneralesController
                = context.getApplication().evaluateExpressionGet(context,
                        "#{configuracionesGeneralesController}", ConfiguracionesGeneralesController.class);

        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(configuracionesGeneralesController.getConfiguracionesGenerales(1)
                        .getCarpetaDePresupuestos().concat(filename).concat(".pdf")));

        FormatoDocumentoPresupuesto encabezado = new FormatoDocumentoPresupuesto();

        writer.setPageEvent(encabezado);

        document.open();

        Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED, 14f);
        Font detalleFont = FontFactory.getFont(FontFactory.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED, 11f);
        Font contenidoFont = FontFactory.getFont(FontFactory.COURIER, BaseFont.CP1252, BaseFont.EMBEDDED, 10f);

        Paragraph titulo = new Paragraph("Reporte final de situación previsional", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(Chunk.NEWLINE);

        if ((nombre != null && !nombre.trim().isEmpty()) || (apellido != null && !apellido.trim().isEmpty())) {
            StringBuilder titular = new StringBuilder("Titular: ");

            if (apellido != null && !apellido.trim().isEmpty()) {
                titular.append(apellido.trim());
                if (nombre != null && !nombre.trim().isEmpty()) {
                    titular.append(", ");
                }
            }

            if (nombre != null && !nombre.trim().isEmpty()) {
                titular.append(nombre.trim());
            }

            Paragraph datosTitular = new Paragraph(titular.toString(), detalleFont);
            datosTitular.setAlignment(Element.ALIGN_LEFT);
            document.add(datosTitular);
            document.add(Chunk.NEWLINE);
        }

        if (reporte != null && !reporte.trim().isEmpty()) {
            String[] lineas = reporte.split("\r?\n");
            for (String linea : lineas) {
                Paragraph parrafo = new Paragraph(linea, contenidoFont);
                parrafo.setAlignment(Element.ALIGN_LEFT);
                document.add(parrafo);
            }
        } else {
            Paragraph sinDatos = new Paragraph("Sin contenido disponible para el reporte.", contenidoFont);
            sinDatos.setAlignment(Element.ALIGN_LEFT);
            document.add(sinDatos);
        }

        document.close();

    }

}

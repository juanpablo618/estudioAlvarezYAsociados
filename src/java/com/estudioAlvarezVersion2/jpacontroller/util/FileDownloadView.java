/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpacontroller.util;

import com.estudioAlvarezVersion2.downloadPDf.ConfiguracionesGeneralesController;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.activation.MimetypesFileTypeMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "fileBean")
@RequestScoped

public class FileDownloadView {
    
    private StreamedContent file;
    private StreamedContent file2;
    private StreamedContent file3;
    
     public static final String SAMPLE_XLSX_FILE_PATH = "../faces/resources/documentos/cronologioDeAportesJuan.xlsx";
    
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

    public StreamedContent createExcel(Date fechaDeNacimiento, String sexo, String nroDeExpediente, String nombre, String apellido) throws FileNotFoundException, IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
    
        String fecha = "##/##/####";
        String sexoParaReporte = "NN";
        
        String nroDeExpedienteFinal = "NroDeExpediente no encontrado";
        String nombreFinal = "nombre no encontrado";
        String apellidoFinal = "apellido no encontrado";
        
        if(apellido != null){
            apellidoFinal = apellido;
        }
        
        if(nombre !=null){
            nombreFinal = nombre;
        }
        
        if(nroDeExpediente !=null){
        nroDeExpedienteFinal = nroDeExpediente;
        }
        
        if(fechaDeNacimiento != null){
        
            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");  
            String strDate = dateFormat.format(fechaDeNacimiento); 
            fecha = strDate;
        }
 
        if(sexo != null){
            sexoParaReporte = sexo;
        }
            
        InputStream inputStream = FacesContext.getCurrentInstance()
        .getExternalContext().getResourceAsStream("/resources/documentos/cronologioDeAportesJuan.xlsx");
        XSSFWorkbook workbook1 = new XSSFWorkbook(inputStream);
        
        workbook1.setActiveSheet(0);
         
        Object[][] bookData = {
                {"Fecha Nacimiento", fecha, "SEXO", sexoParaReporte,"EDAD al Inicio","##"},
        };
 
        int rowCount = 0;
    
        if(rowCount != 0 && rowCount !=2 && rowCount != 4){
            
        for (Object[] aBook : bookData) {
            Row row = workbook1.getSheetAt(workbook1.getActiveSheetIndex()).createRow(rowCount++);
             
            int columnCount = 0;
            
                for (Object field : aBook) {
                    Cell cell = row.createCell(columnCount++);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }
            }
        }
        
        for (Row row : workbook1.getSheetAt(workbook1.getActiveSheetIndex())) {
                for (Cell cell : row) {
                   System.out.println(cell.toString());
                   if(cell.toString().equals("NROEXPEDIENTE - NOMBRE - NUEVA MORATORIA")){
                         cell.setCellValue((String) nroDeExpedienteFinal.concat(" - ")
                                 .concat(nombreFinal).concat(" ").concat(apellidoFinal)
                                 .concat(" - NUEVA MORATORIA"));
                   }
                }
        }

        String nombreDelDocumento = "CronologicoDeAportesDe".concat(apellidoFinal);
        
        
        FacesContext context = FacesContext.getCurrentInstance();
 ConfiguracionesGeneralesController configuracionesGeneralesController = context.getApplication().evaluateExpressionGet(context, "#{configuracionesGeneralesController}", ConfiguracionesGeneralesController.class);
 
        
        //try (FileOutputStream outputStream1 = new FileOutputStream("C:\\Users\\juan.cuello\\Downloads\\"+nombreDelDocumento+".xlsx")) {
      try (FileOutputStream outputStream1 = new FileOutputStream(configuracionesGeneralesController.getConfiguracionesGenerales(1).getCarpetaDePresupuestos()+nombreDelDocumento+".xlsx")) {
        
            workbook1.write(outputStream1);
        }
        
        
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    workbook1.write(outputStream);
 
    InputStream inputStream1 = new ByteArrayInputStream(outputStream.toByteArray());
    
    StreamedContent streamedContent =  new DefaultStreamedContent(inputStream1, "application/xlsx", nombreDelDocumento+".xlsx");
        
        
        //return  new DefaultStreamedContent(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("C:\\Users\\juan.cuello\\Downloads\\"+nombreDelDocumento+".xlsx"), "application/vnd.ms-excel", nombreDelDocumento+".xlsx");
    return streamedContent;
    
       // HSSFWorkbook libro = new HSSFWorkbook();
       // FileOutputStream elFichero = new FileOutputStream("c:/Temp/MyExcel.xls");
       // libro.write(elFichero);
      //  elFichero.close();
       // libro.close();
        //File fil = new File("c:/Temp/MyExcel.xls");
        //StreamedContent excel = new DefaultStreamedContent(new FileInputStream(fil), new MimetypesFileTypeMap().getContentType(fil), "MyExcel.xls"));
        
      }
}

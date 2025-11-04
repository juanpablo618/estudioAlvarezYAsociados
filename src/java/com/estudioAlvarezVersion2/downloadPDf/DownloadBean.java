package com.estudioAlvarezVersion2.downloadPDf;

import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cuello.juanpablo@gmail.com
 */


@ManagedBean
public class DownloadBean implements Serializable {

private static final long serialVersionUID = 626953318628565053L;
private static final Logger LOGGER = Logger.getLogger(DownloadBean.class.getName());

//private final  String PDF_URL = ConfiguracionesGenerales.getPDF_URL();

public void crearDocumento( ) throws IOException, DocumentException, InterruptedException{
    
        Date date = new Date();
        //Caso 1: obtener la hora y salida por pantalla con formato:
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        //Caso 2: obtener la fecha y salida por pantalla con formato:
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Caso 3: obtenerhora y fecha y salida por pantalla con formato:
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    
            String fechaYHoraActual = hourdateFormat.format(date);

                fechaYHoraActual = fechaYHoraActual.replace(" ","");
                fechaYHoraActual = fechaYHoraActual.replace(":","");
                
                String nombreDelDocumento = new String();

                nombreDelDocumento = "Agendasyturnos".concat(fechaYHoraActual);
                nombreDelDocumento = nombreDelDocumento.replace(" ","");
                nombreDelDocumento = nombreDelDocumento.replace(":","");
                nombreDelDocumento = nombreDelDocumento.replace("/","");
                
                  MembretePresupuesto doc = new MembretePresupuesto();
                  
                  doc.createPdf(nombreDelDocumento);
                  
                  downloadPdf(nombreDelDocumento);
                  
                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impresion exitosa"));
    }
        
    

public void crearConvenioDeHonorarios(String nombre, String apellido, String dni, String direccion, String nroDeAltura, String barrio) throws IOException, DocumentException, InterruptedException{

    if(nombre == null) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no hay nombre para imprimir"));
    }else{
        if(apellido == null ){
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no hay Apellido para imprimir"));
        }else{   
            if(dni == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no hay dni para imprimir"));
            }else{
                if(direccion == null){
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no hay dni para imprimir"));
                }
                else{
                   if(nroDeAltura == null){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no hay nro De Altura de la dirección para imprimir"));
                   }else{ 
                       if(barrio == null){
                             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no hay barrio de la dirección para imprimir"));
                       }else{
                        Date date = new Date();
                        //Caso 1: obtener la hora y salida por pantalla con formato:
                        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                        //Caso 2: obtener la fecha y salida por pantalla con formato:
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                        //Caso 3: obtenerhora y fecha y salida por pantalla con formato:
                        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

                            String fechaYHoraActual = hourdateFormat.format(date);

                                fechaYHoraActual = fechaYHoraActual.replace(" ","");
                                fechaYHoraActual = fechaYHoraActual.replace(":","");

                                String nombreDelDocumento = new String();

                                nombreDelDocumento = "ConvenioDeHonorariosPara_".concat(nombre).concat(fechaYHoraActual);
                                nombreDelDocumento = nombreDelDocumento.replace(" ","");
                                nombreDelDocumento = nombreDelDocumento.replace(":","");
                                nombreDelDocumento = nombreDelDocumento.replace("/","");

                                MembretePresupuesto doc = new MembretePresupuesto();

                                 doc.createPdfConvenioDeHonorarios(nombreDelDocumento, nombre, apellido, dni, direccion, nroDeAltura, barrio);

                                downloadPdf(nombreDelDocumento);

                                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impresion exitosa del convenio de Honorarios"));
                            }      
                        }
                    }
                }
            }
        }
    }

public void crearPdfDeCaratula(String nombre, String apellido, String dni, String direccion, String nroDeAltura, String barrio, String apoderado,
        Date fechaDeAtencion, String sexo, String telefono, String cuit, Date fechaDeNacimiento, int edad,
        String cobraBeneficio, String tipoDeBeneficio,String aportes, String trabajando, String inscripcionAut,
        String estadoCivil, String cantidadDeHijos, String obraSocial, String claveCidi, String claveFiscal, String claveSeguridadSocial
                
        ) throws IOException, DocumentException, InterruptedException{

    if(nombre == null) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no hay nombre para imprimir"));
    }else{
        if(apellido == null ){
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no hay Apellido para imprimir"));
        }else{   
            if(dni == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no hay dni para imprimir"));
            }else{
                if(direccion == null){
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no hay dni para imprimir"));
                }
                else{
                   if(nroDeAltura == null){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no hay nro De Altura de la dirección para imprimir"));
                   }else{ 
                       if(barrio == null){
                             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no hay barrio de la dirección para imprimir"));
                       }else{
                        Date date = new Date();
                        //Caso 1: obtener la hora y salida por pantalla con formato:
                        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
                        //Caso 2: obtener la fecha y salida por pantalla con formato:
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                        //Caso 3: obtenerhora y fecha y salida por pantalla con formato:
                        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

                            String fechaYHoraActual = hourdateFormat.format(date);

                                fechaYHoraActual = fechaYHoraActual.replace(" ","");
                                fechaYHoraActual = fechaYHoraActual.replace(":","");

                                String nombreDelDocumento = new String();

                                nombreDelDocumento = "PdfPara_".concat(nombre).concat(fechaYHoraActual);
                                nombreDelDocumento = nombreDelDocumento.replace(" ","");
                                nombreDelDocumento = nombreDelDocumento.replace(":","");
                                nombreDelDocumento = nombreDelDocumento.replace("/","");

                                MembretePresupuesto doc = new MembretePresupuesto();

                                 doc.createPdfDeCaratula(nombreDelDocumento, nombre, apellido, dni, direccion, nroDeAltura,
                                        barrio, apoderado, fechaDeAtencion, sexo, telefono, cuit, fechaDeNacimiento, edad,
                                        cobraBeneficio,  tipoDeBeneficio,  aportes,  trabajando,  inscripcionAut,  estadoCivil,
                                        cantidadDeHijos, obraSocial, claveCidi, claveFiscal, claveSeguridadSocial
                                 );

                                downloadPdf(nombreDelDocumento);

                                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impresion exitosa delPdf"));
                            }      
                        }
                    }
                }
            }
        }
    }


public void crearCronologicoDeAportes(String nombre ) throws IOException, DocumentException, InterruptedException{

    if(nombre == null) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("no nombre asociado"));
    }else{
            
        Date date = new Date();
        //Caso 1: obtener la hora y salida por pantalla con formato:
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        //Caso 2: obtener la fecha y salida por pantalla con formato:
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Caso 3: obtenerhora y fecha y salida por pantalla con formato:
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    
            String fechaYHoraActual = hourdateFormat.format(date);

                fechaYHoraActual = fechaYHoraActual.replace(" ","");
                fechaYHoraActual = fechaYHoraActual.replace(":","");
                
                String nombreDelDocumento = new String();

                nombreDelDocumento = "convenioDeHonorarios".concat(fechaYHoraActual);
                nombreDelDocumento = nombreDelDocumento.replace(" ","");
                nombreDelDocumento = nombreDelDocumento.replace(":","");
                nombreDelDocumento = nombreDelDocumento.replace("/","");
                
                  MembretePresupuesto doc = new MembretePresupuesto();
                  
                  doc.createPdfCronologicoDeAportes(nombreDelDocumento, nombre);

                  downloadCsv(nombreDelDocumento);

                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Impresion exitosa"));
        }
        }

public void imprimirReporteFinalSituacionPrevisional(String nombre, String apellido, String reporte)
        throws IOException, DocumentException, InterruptedException {

    if (reporte == null || reporte.trim().isEmpty()) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay reporte final para imprimir."));
        return;
    }

    Date date = new Date();
    DateFormat hourdateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
    String fechaYHoraActual = hourdateFormat.format(date);

    String nombreSeguro = nombre != null ? nombre.trim() : "";
    String apellidoSeguro = apellido != null ? apellido.trim() : "";
    StringBuilder nombreDocumento = new StringBuilder("ReporteSituacionPrevisional_");

    if (!apellidoSeguro.isEmpty()) {
        nombreDocumento.append(apellidoSeguro.replaceAll("\\s+", ""));
    }

    if (!nombreSeguro.isEmpty()) {
        if (!apellidoSeguro.isEmpty()) {
            nombreDocumento.append("_");
        }
        nombreDocumento.append(nombreSeguro.replaceAll("\\s+", ""));
    }

    nombreDocumento.append("_").append(fechaYHoraActual);

    MembretePresupuesto doc = new MembretePresupuesto();
    doc.createPdfReporteFinal(nombreDocumento.toString(), nombre, apellido, reporte);

    downloadPdf(nombreDocumento.toString());

    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Reporte final generado correctamente."));
}

/**
     * This method reads PDF from the URL and writes it back as a response.     * @throws IOException  
     * @param nombreDelDocumento 
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException*/
public void downloadPdf(String nombreDelDocumento) throws IOException, InterruptedException {
    
    Thread.sleep(2000);
    
// Get the FacesContext
FacesContext facesContext = FacesContext.getCurrentInstance();

// Get HTTP response
HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

// Set response headers
response.reset();
// Reset the response in the first place
response.setHeader("Content-Type", "application/pdf"); 
// Set only the content type

// Open response output stream
OutputStream responseOutputStream = response.getOutputStream();

//create facecontext to set the data PDF_URL retrieved to database
 FacesContext context = FacesContext.getCurrentInstance();
 ConfiguracionesGeneralesController configuracionesGeneralesController = context.getApplication().evaluateExpressionGet(context, "#{configuracionesGeneralesController}", ConfiguracionesGeneralesController.class);
      
 
// Read PDF contents
URL url = new URL(configuracionesGeneralesController.getConfiguracionesGenerales(1).getPdfUrl().concat(nombreDelDocumento).concat(".pdf"));
InputStream pdfInputStream = url.openStream();

// Read PDF contents and write them to the output
byte[] bytesBuffer = new byte[2048];
int bytesRead;
while ((bytesRead = pdfInputStream.read(bytesBuffer)) > 0) {
responseOutputStream.write(bytesBuffer, 0, bytesRead);
}

// Make sure that everything is out
responseOutputStream.flush();

// Close both streams
pdfInputStream.close();
responseOutputStream.close();

// JSF doc: 
// Signal the JavaServer Faces implementation that the HTTP response for this request has already been generated 
// (such as an HTTP redirect), and that the request processing lifecycle should be terminated
// as soon as the current phase is completed.
facesContext.responseComplete();

//FacesContext context = FacesContext.getCurrentInstance();

}

public void downloadCsv(String nombreDelDocumento) throws IOException, InterruptedException {
    Thread.sleep(2000);
    
// Get the FacesContext
FacesContext facesContext = FacesContext.getCurrentInstance();

// Get HTTP response
HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

// Set response headers
response.reset();
// Reset the response in the first place
response.setHeader("Content-Type", "application/xls"); 
// Set only the content type

// Open response output stream
OutputStream responseOutputStream = response.getOutputStream();

//create facecontext to set the data PDF_URL retrieved to database
 FacesContext context = FacesContext.getCurrentInstance();
 ConfiguracionesGeneralesController configuracionesGeneralesController = context.getApplication().evaluateExpressionGet(context, "#{configuracionesGeneralesController}", ConfiguracionesGeneralesController.class);
      
// Read PDF contents
URL url = new URL(configuracionesGeneralesController.getConfiguracionesGenerales(1).getPdfUrl().concat(nombreDelDocumento).concat(".xls"));
InputStream pdfInputStream = url.openStream();

// Read PDF contents and write them to the output
byte[] bytesBuffer = new byte[2048];
int bytesRead;
while ((bytesRead = pdfInputStream.read(bytesBuffer)) > 0) {
responseOutputStream.write(bytesBuffer, 0, bytesRead);
}

// Make sure that everything is out
responseOutputStream.flush();

// Close both streams
pdfInputStream.close();
responseOutputStream.close();

facesContext.responseComplete();


}

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jsf.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.IOException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import javax.el.MethodExpression;

public class CustomPDFExporter extends Exporter {

    public void export(FacesContext context, UIComponent component, String fileName,
                       boolean pageOnly, boolean selectionOnly, String encodingType,
                       boolean preProcessor) {

        try {
            DataTable table = (DataTable) component;

            // 1. Crear el documento con orientación landscape
            Document document = new Document(PageSize.A4.rotate());
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".pdf\"");
            OutputStream out = response.getOutputStream();

            PdfWriter.getInstance(document, out);
            document.open();

            // 2. Crear la tabla PDF
            int numColumns = table.getColumns().size();
            PdfPTable pdfTable = new PdfPTable(numColumns);
            pdfTable.setWidthPercentage(100);

            // 3. Ancho personalizado por columna (modificá según cuántas tengas)
            float[] columnWidths = new float[]{12f, 6f, 12f, 18f, 44f, 8f, 1f}; // Fecha, Orden, Nombre, Responsable, Descripción
            pdfTable.setWidths(columnWidths);

            // 4. Cabeceras
            for (UIComponent col : table.getChildren()) {
                if (col instanceof org.primefaces.component.column.Column) {
                    String header = ((org.primefaces.component.column.Column) col).getHeaderText();
                    pdfTable.addCell(new PdfPCell(new Phrase(header)));
                }
            }

            // 5. Filas
            int rowIndex = 0;

            List<?> data = (List<?>) table.getValue();
            for (Object row : data) {
                    table.setRowIndex(rowIndex++);

                for (UIComponent col : table.getChildren()) {
                    if (col instanceof org.primefaces.component.column.Column) {
                        UIComponent output = col.getChildren().get(0);
                        String value = resolveValue(context, output);
                        pdfTable.addCell(new Phrase(value != null ? value : ""));
                    }
                }
            }
            table.setRowIndex(-1); // Resetear por seguridad


            document.add(pdfTable);
            document.close();
            out.flush();
            out.close();
            context.responseComplete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String resolveValue(FacesContext context, UIComponent component) {
        if (component instanceof javax.faces.component.UIOutput) {
            ValueExpression ve = component.getValueExpression("value");
            if (ve != null) {
                Object value = ve.getValue(context.getELContext());
                if (value != null) {
                    if (value instanceof java.util.Date) {
                        // 🔥 Formateo manual de fechas
                        return new java.text.SimpleDateFormat("dd/MM/yyyy").format((java.util.Date) value);
                    } else {
                        return value.toString();
                    }
                }
            }
        }
        return "";
    }


    @Override
    public void export(FacesContext fc, DataTable dt, String string, boolean bln, boolean bln1, String string1, MethodExpression me, MethodExpression me1) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void exportCells(DataTable dt, Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

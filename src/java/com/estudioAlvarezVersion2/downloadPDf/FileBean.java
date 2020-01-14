package com.estudioAlvarezVersion2.downloadPDf;


import java.io.InputStream;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class FileBean implements Serializable {
  private StreamedContent file;

  public FileBean() {
    InputStream stream = this.getClass().
      getResourceAsStream("/yourfile.txt");
    file = new DefaultStreamedContent(stream,
      "application/txt", "yourfile.txt");
  }

  public StreamedContent getFile() {
    return file;
  }

  
}
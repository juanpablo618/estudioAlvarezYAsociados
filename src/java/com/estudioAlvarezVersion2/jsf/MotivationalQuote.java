package com.estudioAlvarezVersion2.jsf;

/**
 *
 * @author juanpablo618@hotmail.com
 */
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ManagedBean(name = "motivationalQuote")
@SessionScoped
public class MotivationalQuote implements Serializable {

    private List<String> quotes;
    private String selectedQuote;

    @PostConstruct
    public void init() {
        quotes = new ArrayList<>();
        // Agregar las frases motivadoras
        quotes.add("El éxito no es la clave de la felicidad. La felicidad es la clave del éxito.");
        quotes.add("El único lugar donde el éxito viene antes que el trabajo es en el diccionario.");
        quotes.add("No dejes que lo que no puedes hacer interfiera con lo que puedes hacer.");
        // ... Agregar todas las demás frases motivadoras aquí ...

        // Seleccionar una frase aleatoria
        selectRandomQuote();
    }

    private void selectRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(quotes.size());
        selectedQuote = quotes.get(index);
    }

    public String getSelectedQuote() {
        return selectedQuote;
    }

    // Evitar que la frase se muestre más de una vez por sesión
    public void showQuote() {
        selectedQuote = null; // o puedes limpiar el mensaje después de mostrarlo
    }
}

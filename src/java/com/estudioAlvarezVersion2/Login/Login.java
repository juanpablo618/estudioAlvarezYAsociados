package com.estudioAlvarezVersion2.Login;

import com.estudioAlvarezVersion2.jpa.Empleado;
import com.estudioAlvarezVersion2.jpa.LoginDAO;
import com.estudioAlvarezVersion2.jsf.EmpleadoController;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private String pwd;
    private String msg;
    private String user;
    private String rol;
    private Boolean tieneSesionActiva;
    
    private List<String> quotes;
    private String selectedQuote;

    @PostConstruct
public void init() {
    quotes = new ArrayList<>();
    quotes.add("El éxito no es la clave de la felicidad. La felicidad es la clave del éxito.");
    quotes.add("El único lugar donde el éxito viene antes que el trabajo es en el diccionario.");
    quotes.add("No dejes que lo que no puedes hacer interfiera con lo que puedes hacer.");
    quotes.add("El fracaso es solo la oportunidad de comenzar de nuevo con más experiencia.");
    quotes.add("La única manera de hacer un gran trabajo es amar lo que haces.");
    quotes.add("No cuentes los días, haz que los días cuenten.");
    quotes.add("No te rindas, cada fracaso es una lección aprendida.");
    quotes.add("El éxito es la suma de pequeños esfuerzos repetidos día tras día.");
    quotes.add("La vida es 10% lo que te sucede y 90% cómo reaccionas a ello.");
    quotes.add("El éxito no consiste en nunca cometer errores, sino en no cometer nunca el mismo dos veces.");
    quotes.add("Tu actitud, no tu aptitud, determinará tu altitud.");
    quotes.add("No se trata de ser el mejor, se trata de ser mejor que ayer.");
    quotes.add("El coraje no siempre ruge. A veces el coraje es la voz tranquila al final del día que dice: 'Lo intentaré de nuevo mañana'.");
    quotes.add("No puedes cambiar el viento, pero puedes ajustar las velas para llegar a tu destino.");
    quotes.add("La única limitación es aquella que te impones a ti mismo.");
    quotes.add("No sueñes con el éxito. Trabaja para alcanzarlo.");
    quotes.add("El único lugar donde el éxito viene antes que el trabajo es en el diccionario.");
    quotes.add("El camino hacia el éxito es tomar acción masiva y determinada.");
    quotes.add("El éxito es caer siete veces y levantarse ocho.");
    quotes.add("Haz de cada día tu obra maestra.");
    quotes.add("El éxito no es definitivo, el fracaso no es fatal: lo que cuenta es el valor para continuar.");
    quotes.add("No busques ser exitoso, busca ser valioso.");
    quotes.add("La vida es demasiado corta para esperar.");
    quotes.add("El éxito es la capacidad de ir de un fracaso a otro sin perder el entusiasmo.");
    quotes.add("Nunca es demasiado tarde para ser lo que podrías haber sido.");
    quotes.add("No se mide el éxito por el dinero que tienes, sino por las diferencias que haces en la vida de otros.");
    quotes.add("El éxito es la realización progresiva de un objetivo digno.");
    quotes.add("Tú eres más fuerte de lo que piensas.");
    quotes.add("La disciplina es el puente entre metas y logros.");
    quotes.add("No tengas miedo de renunciar a lo bueno para ir por lo grandioso.");
    quotes.add("El éxito es la suma de pequeños esfuerzos, repetidos día tras día.");
    quotes.add("Cree en ti mismo y todo será posible.");
    quotes.add("El éxito es la habilidad de ir de fracaso en fracaso sin perder el entusiasmo.");
    quotes.add("El éxito no se mide por lo que logras, sino por la oposición que has encontrado y el coraje con el que has mantenido la lucha contra abrumadoras probabilidades.");
    quotes.add("El camino al éxito está siempre en construcción.");
    quotes.add("La vida no se trata de encontrarte a ti mismo, sino de crearte a ti mismo.");
    quotes.add("No hay atajos para llegar a cualquier lugar que valga la pena ir.");
    quotes.add("Las grandes obras son hechas no con la fuerza, sino con la perseverancia.");
    quotes.add("No esperes. El tiempo nunca será el adecuado.");
    quotes.add("La clave del éxito es enfocarte en las metas, no en los obstáculos.");
    quotes.add("Si no puedes volar, corre. Si no puedes correr, camina. Si no puedes caminar, arrástrate, pero hagas lo que hagas, sigue adelante.");
    quotes.add("El éxito no depende de las circunstancias, sino de tus decisiones.");
    quotes.add("El éxito no es el final, el fracaso no es fatal. Es el coraje para continuar lo que cuenta.");
    quotes.add("La vida es un 10% lo que me ocurre y un 90% cómo reacciono a ello.");
    quotes.add("La diferencia entre ordinario y extraordinario es ese pequeño extra.");
    quotes.add("El verdadero éxito no está en vencer siempre sino en nunca desanimarse.");
    quotes.add("Cada día es una nueva oportunidad para cambiar tu vida.");
    quotes.add("Las personas que están lo suficientemente locas como para pensar que pueden cambiar el mundo, son las que lo hacen.");
    quotes.add("No hay nada imposible para un corazón dispuesto.");
    quotes.add("La mejor manera de predecir el futuro es crearlo.");
    quotes.add("No importa qué tan lento vayas, siempre y cuando no te detengas.");
    quotes.add("El único lugar donde los sueños se vuelven imposibles es en tu propia mente.");
    quotes.add("El éxito no se mide en lo que has logrado, sino en los obstáculos que has superado.");
    quotes.add("La excelencia no es un acto, sino un hábito.");
    quotes.add("Cree que puedes y estarás a medio camino.");
    quotes.add("El secreto del éxito está en la constancia del propósito.");
    quotes.add("El futuro pertenece a aquellos que creen en la belleza de sus sueños.");
    quotes.add("No se trata de si te derriban, sino de si te levantas.");
    quotes.add("El éxito no es la ausencia de fracasos, sino la persistencia ante el fracaso.");
    quotes.add("El éxito es un viaje, no un destino.");
    quotes.add("La única forma de hacer un gran trabajo es amar lo que haces.");
    quotes.add("Nunca dejes que nadie te diga que no puedes hacer algo.");
    quotes.add("Nunca te rindas en algo en lo que no puedes pasar un día sin pensar.");
    quotes.add("Los sueños no tienen fecha de caducidad.");
    quotes.add("La única forma de lograr lo imposible es creer que es posible.");
    quotes.add("No cuentes los días, haz que los días cuenten.");
    quotes.add("El éxito no se trata de cuán alto llegas, sino de cuántas veces te levantas cuando caes.");
    quotes.add("Si puedes soñarlo, puedes hacerlo.");
    quotes.add("No es el más fuerte de las especies el que sobrevive, ni el más inteligente, sino el que mejor se adapta al cambio.");
    quotes.add("El éxito es la suma de detalles pequeños.");
    quotes.add("La acción es la clave fundamental para todo éxito.");
    quotes.add("La única diferencia entre un sueño y una meta es una fecha.");
    quotes.add("El éxito es alcanzar tus propios sueños, no los de otra persona.");
    quotes.add("El único límite para nuestra realización del mañana serán nuestras dudas de hoy.");
    quotes.add("El fracaso es la oportunidad de comenzar de nuevo, pero esta vez con más inteligencia.");
    quotes.add("El éxito es un proceso, no un destino.");
    quotes.add("El camino al éxito es siempre difícil, pero es el único camino que vale la pena seguir.");
    quotes.add("El éxito no está en vencer siempre, sino en nunca darse por vencido.");
    quotes.add("El éxito es la realización progresiva de un ideal digno.");
    quotes.add("El éxito es el resultado de la preparación, el trabajo duro y aprender del fracaso.");
    quotes.add("El único lugar donde el éxito viene antes que el trabajo es en el diccionario.");
    quotes.add("El éxito no consiste en nunca cometer errores, sino en no cometer nunca el mismo dos veces.");
    quotes.add("El éxito es la suma de pequeños esfuerzos repetidos día tras día.");
    quotes.add("La vida es 10% lo que te sucede y 90% cómo reaccionas a ello.");
    quotes.add("Tu actitud, no tu aptitud, determinará tu altitud.");
    quotes.add("No se trata de ser el mejor, se trata de ser mejor que ayer.");
    quotes.add("La excelencia no es una habilidad, es una actitud.");
    quotes.add("Nunca es demasiado tarde para ser lo que podrías haber sido.");
    quotes.add("El éxito es la suma de pequeños esfuerzos, repetidos día tras día.");
    quotes.add("El éxito es caer siete veces y levantarse ocho.");
    quotes.add("El éxito es la capacidad de ir de un fracaso a otro sin perder el entusiasmo.");
    quotes.add("El éxito no se mide por lo que logras, sino por la oposición que has encontrado y el coraje con el que has mantenido la lucha contra abrumadoras probabilidades.");
    quotes.add("El éxito es la habilidad de ir de fracaso en fracaso sin perder el entusiasmo.");
    quotes.add("El éxito es caer siete veces y levantarse ocho.");
    quotes.add("El éxito es alcanzar tus propios sueños, no los de otra persona.");
    quotes.add("El éxito no depende de las circunstancias, sino de tus decisiones.");
    quotes.add("El éxito es un viaje, no un destino.");
    quotes.add("El éxito es la suma de detalles pequeños.");
    quotes.add("El éxito es alcanzar tus propios sueños, no los de otra persona.");
    quotes.add("El éxito es el resultado de la preparación, el trabajo duro y aprender del fracaso.");

    // Seleccionar una frase aleatoria
    selectRandomQuote();
}

    public Boolean getTieneSesionActiva() {
        return tieneSesionActiva;
    }

    public void setTieneSesionActiva(Boolean tieneSesionActiva) {
        this.tieneSesionActiva = tieneSesionActiva;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void cerrarOtrasSesiones(){
        LoginDAO.cerrarOtrasSesiones(user);
    }
    
    public void tieneSesionActiva(){
        LoginDAO.tieneSesionActiva(user);
    }
    
    //validate login
    public String validateUsernamePassword() {
    String userName = "";

    FacesContext context = FacesContext.getCurrentInstance();
    EmpleadoController empleadoController = context.getApplication().evaluateExpressionGet(context, "#{empleadoController}", EmpleadoController.class);

    for (Empleado empleado : empleadoController.getItems()) {
            // Supongo que `user` es una cadena que representa el ID del empleado
        if (empleado.getIdEmpleado().equals(Integer.parseInt(user))) {
            userName = empleado.getNombre();
            break; // Salir del bucle una vez encontrado el empleado
        }
    }

    boolean valid = LoginDAO.validate(userName, pwd);
    if (valid) {
        
        if (LoginDAO.tieneSesionActiva(user)) {
            tieneSesionActiva = true;
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, 
                "Ya tienes una sesión activa.", 
                "Por favor, cierra otras sesiones"));
            return "login"; // Mantiene al usuario en el login
        }
                    

            HttpSession session = SessionUtils.getSession();
            
            for (Empleado empleado : empleadoController.getItems()) {
                if (empleado.getNombre() == null ? user == null : empleado.getNombre().equals(userName)) {
                    session.setAttribute("userNombreCompleto", empleado.getNombre() + " " + empleado.getApellido());
                }
            }

            LocalDate fechaHoy = LocalDate.now();

            // Formatear la fecha como una cadena en formato dd/MM/YYYY
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = fechaHoy.format(formato);

            
            session.setAttribute("dateToday", fechaFormateada);

            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "", getSelectedQuote()));
        
            LoginDAO.registrarSesionEnBD(user, session.getId());


        return "agenda/List_AgendasTurnosWithSession.xhtml";
    } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Password o Usuario incorrecto",
                "Por favor ingrese bien su usuario y password"));
        return "login";
    }
}

    //logout event, invalidate session
    //TODO mejorar este metodo que devuelva un string con "login" el nombre del archivo que quiero redireccionar 
    public void logout() throws IOException {
    HttpSession session = SessionUtils.getSession();
        if (session != null) {
            String sessionId = session.getId();
            LoginDAO.marcarSesionComoInactiva(sessionId); // Actualiza la BD
            session.invalidate(); // Invalida la sesión en JSF
        }

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "/faces/login.xhtml");
    }

    public List<String>  usuariosLogueados() throws IOException {
        
        List<String> usuarios = new ArrayList<>();
        
        FacesContext context = FacesContext.getCurrentInstance();
    
        EmpleadoController empleadoController = context.getApplication().evaluateExpressionGet(context, "#{empleadoController}", EmpleadoController.class);

        for(Integer idEmpleado : LoginDAO.obtenerUsuariosLogueados()){
                usuarios.add(empleadoController.getEmpleado(idEmpleado).getNombreyApellido());
        }
        
            return usuarios;
    }
    
    public void onIdle() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "No detectamos actividad.", "Volveras a iniciar sesión"));
        try {
            this.logout();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex + "ERROR DE SESIÓN");
        }
    }
    
    private void selectRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(quotes.size());
        selectedQuote = quotes.get(index);
        
    }
    
    public String getSelectedQuote() {
        return selectedQuote;
    }

 }

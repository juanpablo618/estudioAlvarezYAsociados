package com.estudioAlvarezVersion2.Login;

import com.estudioAlvarezVersion2.jpa.Agenda;
import com.estudioAlvarezVersion2.jpa.Empleado;
import com.estudioAlvarezVersion2.jpa.LoginDAO;
import com.estudioAlvarezVersion2.jsf.AgendaController;
import com.estudioAlvarezVersion2.jsf.EmpleadoController;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    quotes.add("Hoy puede ser el día en que avances un paso más de lo que creías posible.");
    quotes.add("Lo importante no es hacerlo perfecto, sino empezar con decisión y mejorar en el camino.");
    quotes.add("Cada tarea terminada es una prueba de tu compromiso con tus metas.");
    quotes.add("Tu constancia de hoy construye la tranquilidad de mañana.");
    quotes.add("Cuando el día se ponga difícil, recordá por qué empezaste.");
    quotes.add("Un pequeño avance sostenido vale más que una gran intención postergada.");
    quotes.add("La actitud con la que enfrentás el trabajo puede cambiar todo el resultado.");
    quotes.add("No subestimes el poder de ordenar una prioridad y cumplirla.");
    quotes.add("La energía vuelve cuando te enfocás en lo que sí podés resolver.");
    quotes.add("Hacer lo correcto, incluso en silencio, también es una forma de liderazgo.");
    quotes.add("Cada desafío trae una oportunidad para aprender algo útil.");
    quotes.add("La paciencia y la acción son una gran combinación para lograr resultados.");
    quotes.add("Un día productivo empieza con una decisión clara.");
    quotes.add("Lo que parece grande se vuelve posible cuando lo dividís en pasos simples.");
    quotes.add("Tu esfuerzo tiene valor aunque el resultado tarde en verse.");
    quotes.add("La disciplina es elegir lo que te acerca a tu objetivo, incluso cuando cuesta.");
    quotes.add("No necesitás tener todas las respuestas para dar el próximo paso.");
    quotes.add("La mejora real aparece cuando repetís buenos hábitos con intención.");
    quotes.add("Resolver una cosa pendiente también libera espacio para pensar mejor.");
    quotes.add("La confianza crece cuando cumplís lo que te prometés.");
    quotes.add("Cada mañana es una invitación a hacer las cosas un poco mejor.");
    quotes.add("Si algo no salió como esperabas, todavía podés convertirlo en aprendizaje.");
    quotes.add("El progreso se nota cuando mirás hacia atrás y ves todo lo que ya superaste.");
    quotes.add("Trabajar con calma no es ir lento; es avanzar con claridad.");
    quotes.add("Tu mejor versión se entrena en las decisiones pequeñas de cada día.");
    quotes.add("Hoy elegí enfocarte en soluciones, no en obstáculos.");
    quotes.add("La perseverancia no hace ruido, pero deja huellas profundas.");
    quotes.add("Una mente ordenada convierte problemas complejos en próximos pasos.");
    quotes.add("El compromiso se demuestra especialmente cuando nadie está mirando.");
    quotes.add("Cada logro empezó alguna vez como una tarea pendiente.");
    quotes.add("No cargues con todo a la vez: empezá por lo más importante.");
    quotes.add("La motivación ayuda, pero la organización sostiene.");
    quotes.add("Un buen resultado también nace de escuchar, revisar y corregir.");
    quotes.add("Tu capacidad crece cada vez que atravesás una dificultad con responsabilidad.");
    quotes.add("A veces avanzar es simplemente no abandonar lo que importa.");
    quotes.add("El tiempo invertido con propósito siempre deja aprendizaje.");
    quotes.add("La claridad aparece cuando dejás de pensar en todo y empezás por algo.");
    quotes.add("Ser constante es confiar en que cada paso cuenta.");
    quotes.add("No esperes un día ideal: hacé que este día tenga sentido.");
    quotes.add("Las metas se acercan cuando tus acciones diarias apuntan en la misma dirección.");
    quotes.add("Cuidar los detalles es una manera concreta de cuidar el resultado.");
    quotes.add("La serenidad también es una herramienta de trabajo.");
    quotes.add("Podés transformar la presión en enfoque si respirás y priorizás.");
    quotes.add("Cada expediente, cada llamada y cada gestión pueden ser una oportunidad de ayudar.");
    quotes.add("La excelencia se construye en lo cotidiano, no solo en los momentos importantes.");
    quotes.add("Hoy no hace falta hacerlo todo: hace falta hacer bien el próximo paso.");
    quotes.add("Un equipo crece cuando cada persona aporta con responsabilidad y respeto.");
    quotes.add("La voluntad se fortalece cuando la ponés en movimiento.");
    quotes.add("El aprendizaje de hoy puede ser la ventaja de mañana.");
    quotes.add("No midas tu día solo por lo pendiente, también por lo que lograste resolver.");
    quotes.add("La concentración convierte horas comunes en resultados valiosos.");
    quotes.add("La confianza del cliente se gana con presencia, seguimiento y claridad.");
    quotes.add("Cada avance ordenado reduce la distancia entre la idea y el resultado.");
    quotes.add("El cansancio puede aparecer, pero tu propósito puede guiarte igual.");
    quotes.add("Hacer una pausa breve también puede ayudarte a volver con más foco.");
    quotes.add("La responsabilidad bien asumida abre caminos.");
    quotes.add("No necesitás competir con nadie: necesitás superar tu forma de trabajar de ayer.");
    quotes.add("La calidad se nota en cómo tratás lo simple.");
    quotes.add("Hoy elegí sumar calma, claridad y acción.");
    quotes.add("Un problema bien entendido ya está más cerca de resolverse.");
    quotes.add("Las grandes mejoras nacen de ajustes pequeños sostenidos en el tiempo.");
    quotes.add("Tu profesionalismo se ve en cada respuesta, cada detalle y cada seguimiento.");
    quotes.add("La constancia convierte tareas repetidas en resultados confiables.");
    quotes.add("Si el camino se complica, ordená prioridades y seguí de a una cosa por vez.");
    quotes.add("La determinación no elimina los obstáculos, pero te ayuda a atravesarlos.");
    quotes.add("Un buen día de trabajo no siempre es fácil, pero sí puede ser valioso.");
    quotes.add("Cada vez que cumplís, fortalecés tu reputación y tu tranquilidad.");
    quotes.add("El foco es decirle sí a lo importante y no a la dispersión.");
    quotes.add("Aprender a resolver también es aprender a preguntar a tiempo.");
    quotes.add("La perseverancia diaria vale más que el impulso ocasional.");
    quotes.add("Cuando trabajás con intención, incluso lo pequeño suma.");
    quotes.add("La excelencia no exige apuro; exige atención.");
    quotes.add("Todo avance empieza cuando dejás de postergar el primer paso.");
    quotes.add("La confianza se construye con hechos, no con promesas.");
    quotes.add("Hoy podés convertir una dificultad en una decisión útil.");
    quotes.add("La organización es una forma de cuidar tu energía.");
    quotes.add("Cada objetivo se vuelve más claro cuando lo acompañás con acción.");
    quotes.add("No confundas un día difícil con un mal camino.");
    quotes.add("La calma permite ver opciones que la urgencia esconde.");
    quotes.add("Tu dedicación de hoy puede hacer más simple el trabajo de mañana.");
    quotes.add("La mejor manera de avanzar es empezar por lo que depende de vos.");
    quotes.add("Cada cierre pendiente resuelto es un paso hacia más orden.");
    quotes.add("La responsabilidad compartida hace más fuerte al equipo.");
    quotes.add("Lo que hacés con constancia termina hablando por vos.");
    quotes.add("Un paso firme vale más que muchas vueltas sin decisión.");
    quotes.add("La energía se renueva cuando ves progreso, aunque sea pequeño.");
    quotes.add("Trabajar bien también es cuidar la forma en que acompañás a los demás.");
    quotes.add("La perseverancia transforma experiencias difíciles en criterio.");
    quotes.add("Hoy podés elegir responder con paciencia y actuar con firmeza.");
    quotes.add("El orden no limita la creatividad; le da un camino.");
    quotes.add("Cada gestión hecha con atención acerca una solución.");
    quotes.add("La mejora comienza cuando revisás lo que puede hacerse mejor.");
    quotes.add("Tu actitud puede convertir una jornada común en una jornada valiosa.");
    quotes.add("La claridad en la comunicación evita problemas y genera confianza.");
    quotes.add("No todo se resuelve rápido, pero todo mejora cuando avanzás con método.");
    quotes.add("Un equipo motivado se construye con respeto, compromiso y colaboración.");
    quotes.add("El esfuerzo sostenido siempre deja más que el resultado inmediato.");
    quotes.add("Hoy enfocate en avanzar, cerrar pendientes y cuidar los detalles.");
    quotes.add("La próxima solución puede estar en el próximo intento.");
    quotes.add("Cada día trae una nueva oportunidad para trabajar con propósito.");

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
            
            String empleadoNombreCompleto = "";
            
            for (Empleado empleado : empleadoController.getItems()) {
                if (empleado.getNombre() == null ? user == null : empleado.getNombre().equals(userName)) {
                    empleadoNombreCompleto = empleado.getNombre() + " " + empleado.getApellido();
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
        
            

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "", getAgendasNoRealizadasEnDiaAnterior(fechaFormateada, empleadoNombreCompleto) ));
            
            
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
     FacesContext.getCurrentInstance().addMessage("messages1", new FacesMessage(FacesMessage.SEVERITY_WARN,//    aqui se agrego messages1 para que muestre el mensaje en un growl especifico con esto corregimos doble notificacion al inicio de sesion al dejar q muestre todo en un solo growl 
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
    
    public String getAgendasNoRealizadasEnDiaAnterior(String fechaDelDia, String nombreEmpleado) {
        FacesContext context = FacesContext.getCurrentInstance();
        AgendaController agendaController = context.getApplication().evaluateExpressionGet(context, "#{agendaController}", AgendaController.class);
        
        if(nombreEmpleado.equals("")){
            return "Ocurrió un error al obtener las agendas no realizadas.";
        }
        
        try {
            // 1. Convertir fechaDelDia (String) a Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaActual = sdf.parse(fechaDelDia);

            // 2. Calcular la fecha del último día laboral anterior
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaActual);

            int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);

            if (diaSemana == Calendar.MONDAY) {
                calendar.add(Calendar.DATE, -3); // Lunes -> Viernes
            } else if (diaSemana == Calendar.SUNDAY) {
                calendar.add(Calendar.DATE, -2); // Domingo -> Viernes
            } else {
                calendar.add(Calendar.DATE, -1); // Otro día -> día anterior
            }

            Date fechaAnterior = calendar.getTime();

            // 3. Formatear fecha anterior en formato dd-MM-yyyy para mostrar
            SimpleDateFormat sdfMostrar = new SimpleDateFormat("dd/MM/yyyy");
            String fechaAnteriorFormateada = sdfMostrar.format(fechaAnterior);

            // 4. Buscar agendas no realizadas en esa fecha para ese empleado
            List<Agenda> agendasNoRealizadas = agendaController.obtenerAgendasNoRealizadasPorFechaYEmpleado(fechaAnterior, nombreEmpleado);
            int cantidad = agendasNoRealizadas.size();

            // 5. Devolver mensaje
            return "Te quedaron " + cantidad + " agendas sin realizar del día " + fechaAnteriorFormateada + ".";
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocurrió un error al obtener las agendas no realizadas.";
        }
    }


 }

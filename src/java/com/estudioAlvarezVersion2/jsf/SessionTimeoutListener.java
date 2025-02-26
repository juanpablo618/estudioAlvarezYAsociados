package com.estudioAlvarezVersion2.jsf;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionTimeoutListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session == null || session.getAttribute("userNombreCompleto") == null) {
            // Si la sesión ha expirado
            String currentPage = ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRequestURI();

            // Si estamos en la página de login, no hacemos nada
            if (currentPage.contains("login.xhtml")) {
                return; // No necesitamos hacer nada, ya que estamos en login
            }

            // Redirigir a la página de login si la sesión ha expirado
            try {
                facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/faces/login.xhtml");
                facesContext.responseComplete(); // Evita que JSF continúe procesando
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    @Override
    public void beforePhase(PhaseEvent event) {
        // No es necesario implementar nada aquí
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW; // Se ejecuta al restaurar la vista
    }

}

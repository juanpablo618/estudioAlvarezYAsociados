package com.estudioAlvarezVersion2.Login;

import com.estudioAlvarezVersion2.jpa.LoginDAO;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    //validate login
    public String validateUsernamePassword() {
        boolean valid = LoginDAO.validate(user, pwd);
        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user);
            return "expediente/List.xhtml";
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
        session.invalidate();

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "/faces/login.xhtml");

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
}

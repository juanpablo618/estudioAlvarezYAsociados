package com.estudioAlvarezVersion2.jsf;

import com.estudioAlvarezVersion2.jpa.DAO;
import com.estudioAlvarezVersion2.jpa.Empleado;
import com.estudioAlvarezVersion2.jpa.Equipo;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil;
import com.estudioAlvarezVersion2.jsf.util.JsfUtil.PersistAction;
import com.estudioAlvarezVersion2.jpacontroller.EmpleadoFacade;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("empleadoController")
@SessionScoped
public class EmpleadoController implements Serializable {

    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.EmpleadoFacade ejbFacade;
    
    @EJB
    private com.estudioAlvarezVersion2.jpacontroller.EquipoFacade equipoFacade;
    
    private List<Empleado> items = null;
    private Empleado selected;
    private List<Equipo> selectedEquipos;
    
    private Empleado selectedDesdeOnlyAdminUsers;
    
    public EmpleadoController() {
    }

    public Empleado getSelected() {
        return selected;
    }

    public Empleado getSelectedDesdeOnlyAdminUsers() {
        return selectedDesdeOnlyAdminUsers;
    }

    public void setSelectedDesdeOnlyAdminUsers(Empleado selectedDesdeOnlyAdminUsers) {
        this.selectedDesdeOnlyAdminUsers = selectedDesdeOnlyAdminUsers;
    }
    
    

    /*public void prepareEdit() {
        if (selected != null) {
            // Forzar la carga de los equipos si es Lazy
            if (selected.getEquipos() == null) {
                selectedEquipos = new ArrayList<>();
            } else {
                selectedEquipos = new ArrayList<>(selected.getEquipos());
            }
        } else {
            selectedEquipos = new ArrayList<>();
        }
    }*/
    
    public void prepareEdit() {
        if (selected != null) {
            selectedEquipos = new ArrayList<>();

            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                con = DAO.getConnection();

                // Consulta para obtener los equipos asociados al empleado
                String getEquiposSql = "SELECT idEquipo FROM empleados_equipos WHERE idEmpleado = ?";
                ps = con.prepareStatement(getEquiposSql);
                ps.setInt(1, selected.getIdEmpleado());
                rs = ps.executeQuery();

                FacesContext context = FacesContext.getCurrentInstance();
                EquipoController equipoControllerBean = context.getApplication().evaluateExpressionGet(context, "#{equipoController}", EquipoController.class);

                while (rs.next()) {
                    int equipoId = rs.getInt("idEquipo");
                    selectedEquipos.add(equipoControllerBean.getEquipo(equipoId));
                }

                if (selectedEquipos.isEmpty()) {
                       selectedEquipos = new ArrayList<>();
                }

            } catch (SQLException ex) {
                JsfUtil.addErrorMessage("Error al recuperar los equipos: " + ex.getMessage());
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            selectedEquipos = new ArrayList<>();
        }
    }


    
    public void setSelected(Empleado selected) {
        this.selected = selected;
    }

    public List<Equipo> getSelectedEquipos() {
        return selectedEquipos;
    }

    public void setSelectedEquipos(List<Equipo> selectedEquipos) {
        this.selectedEquipos = selectedEquipos;
    }

   public List<Equipo> getAllEquipos() {
        return equipoFacade.findAll();
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EmpleadoFacade getFacade() {
        return ejbFacade;
    }

    public Empleado prepareCreate() {
        selected = new Empleado();
        selectedEquipos = new ArrayList<>(); // Inicializar equipos seleccionados
        initializeEmbeddableKey();
        return selected;
    }

    public void create() throws SQLException {
        
        // Persistir el empleado primero
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EmpleadoCreated"));

             selected.setEquipos(selectedEquipos);
       
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                con = DAO.getConnection();

                // Consulta para obtener el ID del empleado recién creado
                String getIdSql = "SELECT idEmpleado FROM Empleado WHERE Nombre = ? AND Apellido = ? ORDER BY idEmpleado DESC LIMIT 1";
                ps = con.prepareStatement(getIdSql);
                ps.setString(1, selected.getNombre());
                ps.setString(2, selected.getApellido());
                rs = ps.executeQuery();

                if (rs.next()) {
                    int empleadoId = rs.getInt("idEmpleado");

                    // Crear las relaciones con los equipos
                    String insertSql = "INSERT INTO empleados_equipos (idEmpleado, idEquipo) VALUES (?, ?)";
                    ps = con.prepareStatement(insertSql);
                    
                    for(Object obj : selectedEquipos){    
          
                        ps.setInt(1, empleadoId);
                        ps.setInt(2, Integer.parseInt(obj.toString())); // Utiliza el id del equipo
                        ps.execute();
                    }

                } else {
                    JsfUtil.addErrorMessage("Error: No se pudo encontrar el ID del empleado.");
                }

            } catch (SQLException ex) {
                JsfUtil.addErrorMessage("Error al crear las relaciones: " + ex.getMessage());
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }

            // Invalidar la lista de empleados para refrescar los datos
            if (!JsfUtil.isValidationFailed()) {
                items = null;
            }
        
    }




    
    
    //public void update() {
    //    if (selectedEquipos != null) {
    //        selected.setEquipos(selectedEquipos);
    //    }
    //    persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EmpleadoUpdated"));
    //}
    
    /*public void update() {
        if (selectedEquipos != null) {
            selected.setEquipos(selectedEquipos);
        }
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EmpleadoUpdated"));
    }*/
    
    

    /*public void update() {
            if (selected != null) {
                if (selectedEquipos != null) {
                //selected.setEquipos(selectedEquipos);
            }
                System.out.println("update de empleado ");
                System.out.println(selectedEquipos.size());
                System.out.println(selectedEquipos.toString());


            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EmpleadoUpdated"));
        } else {
            // Manejo de error: `selected` es `null`
            JsfUtil.addErrorMessage("Empleado no seleccionado.");
        }
    }*/

    public void update() {
        if (selected != null) {
            Connection con = null;
            PreparedStatement ps = null;

            try {
                con = DAO.getConnection();

                // Eliminar relaciones existentes en empleados_equipos para el empleado seleccionado
                String deleteSql = "DELETE FROM empleados_equipos WHERE idEmpleado = ?";
                ps = con.prepareStatement(deleteSql);
                ps.setInt(1, selected.getIdEmpleado());
                ps.executeUpdate();
                ps.close();

                // Insertar las nuevas relaciones con los equipos seleccionados
                if (selectedEquipos != null && !selectedEquipos.isEmpty()) {
                    String insertSql = "INSERT INTO empleados_equipos (idEmpleado, idEquipo) VALUES (?, ?)";
                    ps = con.prepareStatement(insertSql);

                    for (Object obj : selectedEquipos) {
                        ps.setInt(1, selected.getIdEmpleado());
                        ps.setInt(2, Integer.parseInt(obj.toString())); // Utiliza el id del equipo
                        ps.execute();
                    }
                }

                // Persistir el empleado
                persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EmpleadoUpdated"));

            } catch (SQLException ex) {
                JsfUtil.addErrorMessage("Error al actualizar las relaciones: " + ex.getMessage());
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    JsfUtil.addErrorMessage("Error al cerrar recursos: " + ex.getMessage());
                }
            }

            // Invalidar la lista de empleados para refrescar los datos
            if (!JsfUtil.isValidationFailed()) {
                items = null;
            }
        } else {
            // Manejo de error: `selected` es `null`
            JsfUtil.addErrorMessage("Empleado no seleccionado.");
        }
    }

    
    

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EmpleadoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Empleado> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }
    
    public Empleado getEmpleado(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Empleado> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Empleado> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Empleado.class)
    public static class EmpleadoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmpleadoController controller = (EmpleadoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "empleadoController");
            return controller.getEmpleado(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Empleado) {
                Empleado o = (Empleado) object;
                return getStringKey(o.getIdEmpleado());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Empleado.class.getName()});
                return null;
            }
        }

    }
    
    public boolean isLeader(String empleadoNombreCompleto){
        if(empleadoNombreCompleto.equalsIgnoreCase("Mateo Francisco Alvarez") ||
                empleadoNombreCompleto.equalsIgnoreCase("María Emilia Campos") ||
                empleadoNombreCompleto.equalsIgnoreCase("Paula Alvarez") ||
                empleadoNombreCompleto.equalsIgnoreCase("Paola Maldonado") ||
                empleadoNombreCompleto.equalsIgnoreCase("Ayelen Brizzio"))
            return true;
            
        return false;
    }
    
    public Equipo getEquipos(int id) {
    // Implementar la lógica para obtener el equipo por ID
    return equipoFacade.find(id);
}
    
     public Equipo findEquipoById(Integer id) {
        return equipoFacade.find(id);
    }

     public String buscarEquiposALosQuePertenece(int empleadoId) {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<String> nombresDeEquipos = new ArrayList<>();

    try {
        con = DAO.getConnection();

        // Consulta para obtener los equipos asociados al empleado
        String getEquiposSql = "SELECT idEquipo FROM empleados_equipos WHERE idEmpleado = ?";
        ps = con.prepareStatement(getEquiposSql);
        ps.setInt(1, empleadoId);
        rs = ps.executeQuery();

        FacesContext context = FacesContext.getCurrentInstance();
        EquipoController equipoControllerBean = context.getApplication().evaluateExpressionGet(context, "#{equipoController}", EquipoController.class);

        while (rs.next()) {
            int equipoId = rs.getInt("idEquipo");
            Equipo equipo = equipoControllerBean.getEquipo(equipoId);
            if (equipo != null) {
                nombresDeEquipos.add(equipo.getNombre()); // Asumiendo que `getNombre()` devuelve el nombre del equipo
            }
        }

    } catch (SQLException ex) {
        JsfUtil.addErrorMessage("Error al recuperar los equipos: " + ex.getMessage());
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    if (nombresDeEquipos.isEmpty()) {
        return "No posee equipos";
    }

    // Unir los nombres de los equipos en una sola cadena, separados por comas
    return String.join(", ", nombresDeEquipos);
}

     
}

package de.hska.ibsys.beans;

import java.io.Serializable;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * 
 * @author p01
 */
@Named
@SessionScoped
public class LanguageBean implements Serializable{
    
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    
    public Locale getLocale() {
        return locale;
    }
    
    public String getLanguage() {
        return locale.getLanguage();
    }
    
    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
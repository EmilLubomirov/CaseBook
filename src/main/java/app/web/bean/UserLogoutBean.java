package app.web.bean;

import app.web.bean.base.BaseBean;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class UserLogoutBean extends BaseBean
{
    public void logout()
    {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.redirect("/index");
    }
}

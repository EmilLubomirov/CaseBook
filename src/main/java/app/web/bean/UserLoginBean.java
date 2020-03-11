package app.web.bean;

import app.domain.entities.User;
import app.domain.models.binding.UserLoginBindingModel;
import app.domain.models.service.UserServiceModel;
import app.service.UserService;
import app.web.bean.base.BaseBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import java.util.Map;

@Named
@RequestScoped
public class UserLoginBean extends BaseBean
{
    private UserLoginBindingModel model;
    private UserService userService;
    private ModelMapper modelMapper;

    public UserLoginBean() {
    }

    @Inject
    public UserLoginBean(UserService userService, ModelMapper modelMapper)
    {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void  init()
    {
        this.model = new UserLoginBindingModel();
    }

    public UserLoginBindingModel getModel() {
        return model;
    }

    public void setModel(UserLoginBindingModel model) {
        this.model = model;
    }

   public void login()
    {
        UserServiceModel user = null;

        try {
            user = userService
                    .findUserByUAndP(model.getUsername(), DigestUtils.sha256Hex(model.getPassword()));
        }

        catch (NoResultException e)
        {
            this.redirect("/login");
        }

        if (user != null)
        {
            Map<String, Object> session = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();

            session.put("username", model.getUsername());
            session.put("userId", user.getId());

            this.redirect("/home");
        }
    }
}

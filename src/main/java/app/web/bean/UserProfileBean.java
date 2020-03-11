package app.web.bean;

import app.domain.models.view.ProfileViewModel;
import app.service.UserService;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class UserProfileBean
{
    private ProfileViewModel model;
    private UserService userService;
    private ModelMapper modelMapper;

    public UserProfileBean() {
    }

    @Inject
    public UserProfileBean(UserService userService, ModelMapper modelMapper)
    {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init()
    {
        String id = ((HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequest()).getParameter("id");

        this.model = modelMapper.map(userService.getById(id), ProfileViewModel.class);
    }

    public ProfileViewModel getModel() {
        return model;
    }

    public void setModel(ProfileViewModel model) {
        this.model = model;
    }
}

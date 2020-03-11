package app.web.bean;

import app.domain.entities.User;
import app.domain.models.service.UserServiceModel;
import app.domain.models.view.UserHomeViewModel;
import app.service.UserService;
import app.web.bean.base.BaseBean;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class UserHomeBean extends BaseBean
{
    private List<UserHomeViewModel> models;
    private UserService userService;
    private ModelMapper modelMapper;

    public UserHomeBean() {
    }

    @Inject
    public UserHomeBean(UserService userService, ModelMapper modelMapper)
    {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init()
    {
        String username = (String) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSession(false)).getAttribute("username");


        this.setModels(userService.findAll()
                                .stream()
                                .filter(u -> !u.getUsername().equals(username) && !(u.getFriends().stream()
                                        .map(UserServiceModel::getUsername)
                                        .collect(Collectors.toList()).contains(username)))
                .map(u -> modelMapper.map(u, UserHomeViewModel.class))
                .collect(Collectors.toList()));
    }

    public List<UserHomeViewModel> getModels() {
        return models;
    }

    public void setModels(List<UserHomeViewModel> models) {
        this.models = models;
    }

     public void addFriend(String friendId)
     {
         String userId = (String) ((HttpSession) FacesContext.getCurrentInstance()
                 .getExternalContext()
                 .getSession(false))
                 .getAttribute("userId");

         UserServiceModel loggedIn = userService.getById(userId);
         UserServiceModel friend = userService.getById(friendId);

         loggedIn.getFriends().add(friend);
         friend.getFriends().add(loggedIn);

         userService.addFriend(loggedIn);
         userService.addFriend(friend);

         this.redirect("/home");
     }
}

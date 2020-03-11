package app.web.bean;


import app.domain.models.view.FriendViewModel;
import app.service.UserService;
import app.web.bean.base.BaseBean;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class FriendBean extends BaseBean
{
    private List<FriendViewModel> friends;
    private UserService userService;
    private ModelMapper modelMapper;

    public FriendBean() {
    }

    @Inject
    public FriendBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init(){

        String id = (String) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSession(false))
                .getAttribute("userId");

        this.setFriends(userService.getById(id)
                .getFriends()
                .stream()
                .map(u -> modelMapper.map(u, FriendViewModel.class))
                .collect(Collectors.toList()));
    }

    public List<FriendViewModel> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendViewModel> friends) {
        this.friends = friends;
    }

    public void unfriend(String friendId)
    {
        String userId = (String) ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSession(false))
                .getAttribute("userId");


        userService.unfriend(userId, friendId);
        this.redirect("/friends");

    }
}

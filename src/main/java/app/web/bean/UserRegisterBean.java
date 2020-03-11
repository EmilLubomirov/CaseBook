package app.web.bean;

import app.Gender;
import app.domain.models.binding.UserRegisterBindingModel;
import app.domain.models.service.UserServiceModel;
import app.service.UserService;
import app.web.bean.base.BaseBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.RollbackException;

@Named
@RequestScoped
public class UserRegisterBean extends BaseBean
{
    private UserRegisterBindingModel model;
    private UserService userService;
    private ModelMapper modelMapper;

    public UserRegisterBean() {
    }

    @Inject
    public UserRegisterBean(UserService userService, ModelMapper modelMapper)
    {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init()
    {
        this.model = new UserRegisterBindingModel();
    }

    public void register()
    {
        boolean isGenderValid = true;

        try {

             Gender gender = Gender.valueOf(model.getGender().toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            isGenderValid = false;
        }


        if (!this.model.getPassword().equals(this.model.getConfirmPassword()) || !isGenderValid )
        {
            this.redirect("/register");
        }

        else {

            try {

                model.setPassword(DigestUtils.sha256Hex(model.getPassword()));
                model.setGender(model.getGender().toLowerCase());

                UserServiceModel userServiceModel = modelMapper.map(model, UserServiceModel.class);

                userService.register(userServiceModel);

                this.redirect("/login");
            }

            catch (RollbackException e)
            {
                this.redirect("/register");
            }
        }
    }

    public UserRegisterBindingModel getModel()
    {
        return model;
    }

    public void setModel(UserRegisterBindingModel model)
    {
        this.model = model;
    }
}

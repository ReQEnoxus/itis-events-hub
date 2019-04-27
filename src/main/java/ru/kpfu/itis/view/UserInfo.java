package ru.kpfu.itis.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.UserService;

@Route("user")
public class UserInfo extends AbstractWindow implements HasUrlParameter<String> {
    UserService userService = new UserService();

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        try {
            User desiredUser = userService.get(s);
            setContent(new Text(desiredUser.toString()));
        } catch (IllegalStateException e) {
            beforeEvent.rerouteTo("404");
        }
    }
}

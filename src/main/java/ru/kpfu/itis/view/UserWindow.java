package ru.kpfu.itis.view;

import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.kpfu.itis.service.UserService;

@Route("user")
public class UserWindow extends AbstractWindow implements HasUrlParameter<String> {
    private UserService userService = new UserService();

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        if (userService.get(s) == null) {
            beforeEvent.rerouteTo("404");
        }
    }
}

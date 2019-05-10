package ru.kpfu.itis.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.factory.ComponentFactory;
import ru.kpfu.itis.factory.ComponentFactoryEvent;
import ru.kpfu.itis.factory.ComponentFactoryUser;
import ru.kpfu.itis.service.EventService;
import ru.kpfu.itis.service.UserService;

import java.util.List;

@Route("admin")
public class AdminWindow extends VerticalLayout implements HasUrlParameter<String> {
    private UserService userService = new UserService();
    private EventService eventService = new EventService();
    private ComponentFactory<Event> eventFactory = new ComponentFactoryEvent();
    private ComponentFactory<User> userFactory = new ComponentFactoryUser();

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        if (!AuthManager.getCurrentUser().getRole().equals(Role.ADMIN)) {
            beforeEvent.rerouteTo("404");
        } else if (!(s.equals("users") || s.equals("events"))) {
            beforeEvent.rerouteTo("404");
        } else {
            buildUI(s);
        }
    }

    private void buildUI(String itemType) {
        if (itemType.equals("users")) {
            List<User> users = userService.getAll();
            for (User user : users) {
                add(userFactory.create(user));
            }
        } else if (itemType.equals("events")) {
            List<Event> events = eventService.getAll();
            for (Event event : events) {
                add(eventFactory.create(event));
            }
        }
    }
}

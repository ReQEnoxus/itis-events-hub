package ru.kpfu.itis.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.factory.ComponentFactoryEventPage;
import ru.kpfu.itis.service.EventService;

import java.util.List;

public class MainWindow extends AbstractWindow {

    public MainWindow() {
        if (AuthManager.getCurrentUser().getRole() == Role.VERIFIED
                || AuthManager.getCurrentUser().getRole() == Role.ADMIN) {
            Button button = new Button(new Icon(VaadinIcon.PLUS));
            setContent(button);
        }

        EventService eventService = new EventService();
        List<Event> eventList = eventService.getActive();


        ComponentFactoryEventPage componentFactoryEventPage = new ComponentFactoryEventPage();
        for (int i = 0; i < eventList.size(); i++) {
            setContent(componentFactoryEventPage.create(eventList.get(i)));
        }

    }
}

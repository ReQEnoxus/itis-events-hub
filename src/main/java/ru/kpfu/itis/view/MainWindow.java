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
    public MainWindow(){
        if(AuthManager.getCurrentUser().getRole() == Role.ADMIN || AuthManager.getCurrentUser().getRole() == Role.VERIFIED){
            Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
            setContent(plusButton);
        }
        EventService eventService = new EventService();
        List<Event> eventList = eventService.getActive();

        ComponentFactoryEventPage eventPage = new ComponentFactoryEventPage();
        for (int i = 0; i < eventList.size(); i++) {
            add(eventPage.create(eventList.get(i)));
        }
    }
}

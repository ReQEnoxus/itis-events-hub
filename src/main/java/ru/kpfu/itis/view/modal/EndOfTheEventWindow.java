package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.factory.ComponentFactoryUserImpl;
import ru.kpfu.itis.service.EventService;
import ru.kpfu.itis.service.UserService;

import java.util.ArrayList;

public class EndOfTheEventWindow {
    private Dialog dialog;
    private ComponentFactoryUserImpl factory;
    private EventService eventService = new EventService();
    private UserService userService = new UserService();

    public Dialog getDialog() {
        return dialog;
    }

    public static void openEndOfTheEventWindow(Event event) {
        EndOfTheEventWindow endOfTheEventWindow = new EndOfTheEventWindow(event);
        endOfTheEventWindow.getDialog().open();
    }

    public EndOfTheEventWindow(Event event) {
        this.dialog = new Dialog();
        this.dialog.add(createEndOfTheEventComponents(event));
        this.dialog.setCloseOnOutsideClick(false);
    }

    private VerticalLayout createEndOfTheEventComponents(Event event) {
        factory = new ComponentFactoryUserImpl();
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        Div div = new Div();
        div.setText("Проверьте присутствие участников на мероприятии");
        formLayout.add(div);
        if (event.getParticipants() != null && event.getParticipants().size() != 0) {
            for (User user : event.getParticipants()) {
                formLayout.add(factory.create(user));
            }
        } else {
            Div d = new Div();
            d.setText("На этом мероприятии не присутствовало ни одного участника");
            formLayout.add(d);
        }
        Button end = new Button("Завершить");
        end.getStyle().set("color", "red");
        formLayout.add(end);
        end.addClickListener(evt -> {
            if (event.getParticipants() != null && event.getParticipants().size() != 0) {
                addPointsToParticipants(event);
            }
            event.setActive(false);
            eventService.update(event.getId(), event);
            dialog.close();
            UI.getCurrent().getPage().reload();
        });
        return formLayout;
    }

    private void addPointsToParticipants(Event event) {
        for (int i = 0; i < event.getParticipants().size(); i++) {
            User user = event.getParticipants().get(i);
            if (factory.getMap().get(factory.getCheckboxes().get(i))) {
                user.setPoints(user.getPoints() + event.getPrize());
                if (user.getAccomplishedEvents() == null) {
                    user.setAccomplishedEvents(new ArrayList<>());
                }
                user.getAccomplishedEvents().add(event.getId());
                user.getCurrentEvents().remove(Integer.valueOf(event.getId()));
                userService.update(user.getLogin(), user);
            }
        }
    }
}

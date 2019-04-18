package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.factory.ComponentFactoryUserImpl;

import java.util.ArrayList;

public class EndOfTheEventWindow {
    private Dialog dialog;
    private ComponentFactoryUserImpl factory;

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
        this.dialog.setWidth("300px");
        this.dialog.setHeight("150px");
        this.dialog.setCloseOnOutsideClick(false);
    }

    private FormLayout createEndOfTheEventComponents(Event event) {
        factory = new ComponentFactoryUserImpl();
        FormLayout formLayout = new FormLayout();
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
        formLayout.add(end);
        end.addClickListener(evt -> {
            if (event.getParticipants() != null && event.getParticipants().size() != 0) {
                addPointsToParticipants(event);
            }
            event.setActive(false);
            dialog.close();
        });
        return formLayout;
    }

    private void addPointsToParticipants(Event event) {
        for (int i = 0; i < event.getParticipants().size(); i++) {
            User user = event.getParticipants().get(i);
            if (factory.getMap().get(factory.getCheckboxes().get(i))) {
                user.setPoints(user.getPoints() + event.getPrize());
                if (user.getAccomplishedEvents() == null) {
                    user.setAccomplishedEvents(new ArrayList<Event>());
                }
                user.getAccomplishedEvents().add(event);
            }
        }
    }
}

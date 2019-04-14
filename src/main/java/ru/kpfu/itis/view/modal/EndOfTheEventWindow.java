package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.factory.ComponentFactory;
import ru.kpfu.itis.factory.ComponentFactoryUserImpl;

public class EndOfTheEventWindow {
    private Dialog dialog;

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
        FormLayout formLayout = new FormLayout();
        Div div = new Div();
        div.setText("Проверьте присутствие участников на мероприятии");
        formLayout.add(div);
        if (event.getParticipants() != null && event.getParticipants().size() != 0) {
            ComponentFactory<User> factory = new ComponentFactoryUserImpl();
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
        end.addClickListener(evt -> dialog.close());
        return formLayout;
    }
}

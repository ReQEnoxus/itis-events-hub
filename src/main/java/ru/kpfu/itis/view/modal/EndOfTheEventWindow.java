package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.factory.ComponentFactory;

public class EndOfTheEventWindow implements ComponentFactory<User> {
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
        formLayout.add("Проверьте присутствие участников на мероприятии");
        if (event.getParticipants() != null) {
            for (User user : event.getParticipants()) {
                formLayout.add(create(user));
            }
            Button end = new Button("Завершить");
            formLayout.add(end);
            end.addClickListener(evt -> dialog.close());
            return formLayout;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public HorizontalLayout create(User user) {
        HorizontalLayout userLayout = new HorizontalLayout();
        userLayout.setAlignItems(FlexComponent.Alignment.END);
        userLayout.setWidth("100%");
        TextField userData = new TextField();
        userData.setValue(user.getName() + " " + user.getLastname());
        userData.setEnabled(false);
        Checkbox confirmUser = new Checkbox();
        confirmUser.setValue(true);
        userLayout.add(userData, confirmUser);
        userLayout.expand(userData);
        return userLayout;
    }
}

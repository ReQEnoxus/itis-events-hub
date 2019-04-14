package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.factory.ComponentFactory;
import ru.kpfu.itis.factory.ComponentFactoryEditEventImpl;
import ru.kpfu.itis.factory.ComponentFactoryEditUserImpl;

public class EditElementOfDatabaseWindow {
    private Dialog dialog;
    private ComponentFactory<Event> factoryEvent = new ComponentFactoryEditEventImpl();
    private ComponentFactory<User> factoryUser = new ComponentFactoryEditUserImpl();

    public Dialog getDialog() {
        return dialog;
    }

    public static void openEditElementOfDatabaseWindow(Event event) {
        EditElementOfDatabaseWindow element = new EditElementOfDatabaseWindow(event);
        element.getDialog().open();
    }

    public EditElementOfDatabaseWindow(Event event) {
        this.dialog = new Dialog();
        VerticalLayout formLayout = (VerticalLayout) factoryEvent.create(event);
        Button end = new Button("Сохранить");
        formLayout.add(end);
        end.addClickListener(evt -> dialog.close());
        this.dialog.add(formLayout);
        this.dialog.setWidth("300px");
        this.dialog.setHeight("500px");
        this.dialog.setCloseOnOutsideClick(false);
    }

    public static void openEditElementOfDatabaseWindow(User user) {
        EditElementOfDatabaseWindow element = new EditElementOfDatabaseWindow(user);
        element.getDialog().open();
    }

    public EditElementOfDatabaseWindow(User user) {
        this.dialog = new Dialog();
        VerticalLayout formLayout = (VerticalLayout) factoryUser.create(user);
        Button end = new Button("Сохранить");
        formLayout.add(end);
        end.addClickListener(evt -> dialog.close());
        this.dialog.add(formLayout);
        this.dialog.setWidth("300px");
        this.dialog.setHeight("650px");
        this.dialog.setCloseOnOutsideClick(false);
    }
}

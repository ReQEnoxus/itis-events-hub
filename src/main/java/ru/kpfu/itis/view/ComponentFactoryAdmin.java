package ru.kpfu.itis.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.factory.ComponentFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ComponentFactoryAdmin<T> implements ComponentFactory {
    @Override
    public Component create(Object entity) {

        HorizontalLayout layout = new HorizontalLayout();

        if (entity.getClass() == User.class) {
            Class<User> aClass = User.class;
            Method nameSetter = null;
            try {
                nameSetter = aClass.getDeclaredMethod("getName");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            Label userName = null;
            try {
                userName = new Label((String) nameSetter.invoke(entity));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            Method lastnameSetter = null;
            try {
                lastnameSetter = aClass.getDeclaredMethod("getLastname");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            Label userLastname = null;
            try {
                userLastname = new Label((String) lastnameSetter.invoke(entity));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            Method patronymicSetter = null;
            try {
                patronymicSetter = aClass.getDeclaredMethod("getPatronymic");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            Label userPatronymic = null;
            try {
                userPatronymic = new Label((String) patronymicSetter.invoke(entity));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            HorizontalLayout LNP = new HorizontalLayout();
            HorizontalLayout buttons = new HorizontalLayout();
            Button button = new Button("Изменить");
            button.getStyle().set("cursor", "pointer");
            LNP.add(userLastname.getText() + " " + userName.getText() + " " + userPatronymic.getText());
            LNP.getStyle().set("align-self", "center");
            LNP.getStyle().set("font-size", "large");
            LNP.getStyle().set("width", "400px");
            buttons.add(button);
            layout.add(LNP, buttons);
        }
        else if (entity.getClass() == Event.class) {
            Class<Event> bClass = Event.class;
            Method eventSetter = null;
            try {
                eventSetter = bClass.getDeclaredMethod("getName");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            Label eventName = null;
            try {
                eventName = new Label((String) eventSetter.invoke(entity));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            HorizontalLayout events = new HorizontalLayout();
            HorizontalLayout buttons = new HorizontalLayout();
            eventName.getStyle().set("color", "c0c1c1");
            eventName.getStyle().set("align-self", "center");
            eventName.getStyle().set("font-weight", "bold");
            eventName.getStyle().set("font-size", "larger");
            eventName.getStyle().set("width", "400pc");
            events.add(eventName);
            Button button = new Button("Изменить");
            button.getStyle().set("cursor", "pointer");
            buttons.add(button);
            layout.add(events, buttons);
        }



//            Label lastname = new Label(entity.getClass().get());
//            Label name = new Label(entity.getClass().getName());
//            Label patronymic = new Label(entity.getClass().getName());

        return layout;
    }
}

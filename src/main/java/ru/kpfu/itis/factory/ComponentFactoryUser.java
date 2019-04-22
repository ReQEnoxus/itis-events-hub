package ru.kpfu.itis.factory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import ru.kpfu.itis.entity.User;

public class ComponentFactoryUser implements ComponentFactory<User> {

    @Override
    public Component create(User entity) {
        HorizontalLayout nameButton = new HorizontalLayout();
        Label LNP = new Label(entity.getLastname() + " " + entity.getName() + " " + entity.getPatronymic());
        LNP.getStyle().set("align-self", "center");
        LNP.getStyle().set("font-size", "larger");
        LNP.getStyle().set("width", "20pc");
        LNP.getStyle().set("font-weight", "bold");
        Button button = new Button("Изменить");
        button.getStyle().set("cursor" , "pointer");
        nameButton.add(LNP, button);
        return nameButton;
    }
}

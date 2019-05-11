package ru.kpfu.itis.factory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.UserService;
import ru.kpfu.itis.view.modal.EditElementOfDatabaseWindow;

public class ComponentFactoryUser implements ComponentFactory<User> {

    @Override
    public Component create(User entity) {
        UserService us = new UserService();
        Dialog dialog = new Dialog();
        dialog.add(new Label("Вы уверены?"));
        Button dialogYes = new Button("Да");
        dialogYes.getStyle().set("cursor", "pointer");
        dialogYes.getStyle().set("color", "red");
        dialogYes.addClickListener(evt -> {
            us.delete(entity.getLogin());
            dialog.close();
            UI.getCurrent().getPage().reload();
        });
        Button dialogNo = new Button("Нет");
        dialogNo.getStyle().set("cursor", "pointer");
        dialogNo.addClickListener(evt -> {
            dialog.close();
        });
        dialog.add(new HorizontalLayout(dialogYes, dialogNo));

        HorizontalLayout nameButton = new HorizontalLayout();
        Label LNP = new Label(entity.getLastname() + " " + entity.getName() + " " + entity.getPatronymic());
        LNP.getStyle().set("align-self", "center");
        LNP.getStyle().set("font-size", "larger");
        LNP.getStyle().set("width", "20pc");
        LNP.getStyle().set("font-weight", "bold");
        Button button = new Button("Изменить");
        button.getStyle().set("cursor" , "pointer");
        button.addClickListener(evt -> EditElementOfDatabaseWindow.show(entity));

        Button delete = new Button("Удалить");
        delete.getStyle().set("color", "red");
        delete.getStyle().set("cursor", "pointer");
        delete.addClickListener(evt -> {
            dialog.setOpened(true);
        });
        nameButton.add(LNP, button, delete);
        return nameButton;
    }
}

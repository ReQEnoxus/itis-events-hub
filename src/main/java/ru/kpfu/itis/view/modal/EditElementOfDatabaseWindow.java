package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.EventService;
import ru.kpfu.itis.service.UserService;

public class EditElementOfDatabaseWindow {
    private static EventService eventService = new EventService();
    private static UserService userService = new UserService();

    public static void show(User element) {
        Dialog dialog = new Dialog();
        dialog.setWidth("65vw");
        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField();
        nameField.setValue(element.getName());
        formLayout.addFormItem(nameField, "Имя");

        TextField lastNameField = new TextField();
        lastNameField.setValue(element.getLastname());
        formLayout.addFormItem(lastNameField, "Фамилия");

        TextField patronymicField = new TextField();
        patronymicField.setValue(element.getPatronymic());
        formLayout.addFormItem(patronymicField, "Отчество");

        NumberField pointsField = new NumberField();
        pointsField.setValue(((double) element.getPoints()));
        formLayout.addFormItem(pointsField, "Очки");

        TextField emailField = new TextField();
        emailField.setValue(element.getEmail() == null ? "" : element.getEmail());
        formLayout.addFormItem(emailField, "E-mail");

        TextArea descField = new TextArea();
        descField.setValue(element.getDescription() == null ? "" : element.getDescription());
        formLayout.addFormItem(descField, "Описание");

        Checkbox subscribed = new Checkbox();
        subscribed.setValue(element.isSubscribed());
        formLayout.addFormItem(subscribed, "Подписка на оповещения");

        ComboBox<String> roles = new ComboBox<>();
        roles.setItems("REGULAR", "VERIFIED", "ADMIN");
        roles.setValue(element.getRole().toString());
        formLayout.addFormItem(roles, "Роль");

        dialog.add(formLayout);

        Button saveButton = new Button();
        saveButton.setText("Сохранить изменения");
        saveButton.addClickListener(evt -> {
            boolean changed = false;
            if (!nameField.getValue().equals(element.getName())) {
                element.setName(nameField.getValue());
                changed = true;
            }
            if (!lastNameField.getValue().equals(element.getLastname())) {
                element.setLastname(lastNameField.getValue());
                changed = true;
            }
            if (!patronymicField.getValue().equals(element.getPatronymic())) {
                element.setPatronymic(patronymicField.getValue());
                changed = true;
            }
            if (pointsField.getValue() != element.getPoints()) {
                element.setPoints(pointsField.getValue().intValue());
                changed = true;
            }
            if (!emailField.getValue().equals(element.getEmail())) {
                element.setEmail(emailField.getValue());
                changed = true;
            }
            if (!descField.getValue().equals(element.getDescription())) {
                element.setDescription(descField.getValue());
                changed = true;
            }
            if (subscribed.getValue() != element.isSubscribed()) {
                element.setSubscribed(subscribed.getValue());
                changed = true;
            }
            if (!roles.getValue().equals(element.getRole().toString())) {
                element.setRole(Role.valueOf(roles.getValue()));
                changed = true;
            }

            if (changed) {
                userService.update(element.getLogin(), element);
                Notification.show("Изменения сохранены", 3000, Notification.Position.TOP_END);
            }
            dialog.close();
        });
        dialog.add(saveButton);
        dialog.setOpened(true);
    }

    public static void show(Event element) {

    }
}

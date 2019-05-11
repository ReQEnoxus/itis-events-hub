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

        TextField groupField = new TextField();
        groupField.setValue(element.getGroup() == null ? "" : element.getGroup());
        formLayout.addFormItem(groupField, "Группа");

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
        Dialog dialog = new Dialog();
        dialog.setWidth("65vw");
        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField();
        nameField.setValue(element.getName());
        formLayout.addFormItem(nameField, "Название");

        TextField placeField = new TextField();
        placeField.setValue(element.getPlace());
        formLayout.addFormItem(placeField, "Местоположение");

        TextField dateStartField = new TextField();
        dateStartField.setValue(element.getDateStart());
        formLayout.addFormItem(dateStartField, "Дата начала");

        TextField dateEndField = new TextField();
        dateEndField.setValue(element.getDateEnd());
        formLayout.addFormItem(dateEndField, "Дата окончания");

        TextField timeStartField = new TextField();
        timeStartField.setValue(element.getTimeStart());
        formLayout.addFormItem(timeStartField, "Время начала");

        TextField timeEndField = new TextField();
        timeEndField.setValue(element.getTimeEnd());
        formLayout.addFormItem(timeEndField, "Время окончания");

        NumberField volCountField = new NumberField();
        volCountField.setValue(((double) element.getCapacity()));
        formLayout.addFormItem(volCountField, "Количество волонтеров");

        NumberField prizeField = new NumberField();
        prizeField.setValue(((double) element.getPrize()));
        formLayout.addFormItem(prizeField, "Количество баллов");

        TextArea descriptionField = new TextArea();
        descriptionField.setValue(element.getDescription());
        formLayout.addFormItem(descriptionField, "Описание");

        Checkbox active = new Checkbox();
        active.setValue(element.isActive());
        formLayout.addFormItem(active, "Активно");

        dialog.add(formLayout);

        Button saveButton = new Button();
        saveButton.setText("Сохранить изменения");
        saveButton.addClickListener(evt -> {
            boolean changed = false;
            if (!nameField.getValue().equals(element.getName())) {
                element.setName(nameField.getValue());
                changed = true;
            }
            if (!placeField.getValue().equals(element.getPlace())) {
                element.setPlace(placeField.getValue());
                changed = true;
            }
            if (!dateStartField.getValue().equals(element.getDateStart())) {
                element.setDateStart(dateStartField.getValue());
                changed = true;
            }
            if (prizeField.getValue() != element.getPrize()) {
                element.setPrize(prizeField.getValue().intValue());
                changed = true;
            }
            if (!dateEndField.getValue().equals(element.getDateEnd())) {
                element.setDateEnd(dateEndField.getValue());
                changed = true;
            }
            if (!descriptionField.getValue().equals(element.getDescription())) {
                element.setDescription(descriptionField.getValue());
                changed = true;
            }
            if (volCountField.getValue() != element.getCapacity()) {
                element.setCapacity(volCountField.getValue().intValue());
                changed = true;
            }
            if (timeStartField.getValue().equals(element.getTimeStart())) {
                element.setTimeStart(timeStartField.getValue());
                changed = true;
            }
            if (timeEndField.getValue().equals(element.getTimeEnd())) {
                element.setTimeEnd(timeEndField.getValue());
                changed = true;
            }
            if (active.getValue() != element.isActive()) {
                element.setActive(active.getValue());
                changed = true;
            }

            if (changed) {
                eventService.update(element.getId(), element);
                Notification.show("Изменения сохранены", 3000, Notification.Position.TOP_END);
            }
            dialog.close();
        });
        dialog.add(saveButton);
        dialog.setOpened(true);
    }
}

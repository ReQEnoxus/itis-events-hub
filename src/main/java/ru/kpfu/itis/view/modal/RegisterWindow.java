package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;


public class RegisterWindow {
    public static void show() {
        Dialog dialog = new Dialog();
        dialog.setHeightFull();
        //dialog.setWidth("250px");
        VerticalLayout layout = new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        VerticalLayout title = new VerticalLayout();
        H2 text = new H2("Регистрация");
        text.getStyle().set("color", "#FFFFFF");
        title.setAlignItems(FlexComponent.Alignment.CENTER);
        title.setHeight("200px");
        title.add(text);
        title.setWidth("200%");
        title.getStyle().set("background-color", "#1676F3");
        title.getStyle().set("position", "relative");
        title.getStyle().set("bottom", "40px");
        text.getStyle().set("position", "relative");
        text.getStyle().set("top", "45px");

        VerticalLayout leftColumn = new VerticalLayout();
        VerticalLayout rightColumn = new VerticalLayout();
        HorizontalLayout columns = new HorizontalLayout();
        columns.setSpacing(false);
        columns.getStyle().set("position", "relative");
        columns.getStyle().set("bottom", "35px");

        TextField loginField = new TextField();
        loginField.setRequired(true);
        loginField.setLabel("Логин: ");
        loginField.setErrorMessage("Логин не может быть пустым");

        TextField emailField = new TextField();
        emailField.setRequired(true);
        emailField.setLabel("E-mail: ");
        emailField.setErrorMessage("E-mail не может быть пустым");

        PasswordField passwordField = new PasswordField();
        passwordField.setRequired(true);
        passwordField.setLabel("Пароль: ");
        passwordField.setErrorMessage("Пароль не может быть пустым");

        leftColumn.add(loginField, passwordField, emailField);

        TextField nameField = new TextField();
        nameField.setRequired(true);
        nameField.setLabel("Имя: ");
        nameField.setErrorMessage("Имя не может быть пустым");

        TextField lastNameField = new TextField();
        lastNameField.setRequired(true);
        lastNameField.setLabel("Фамилия: ");
        lastNameField.setErrorMessage("Фамилия не может быть пустой");

        TextField patronymicField = new TextField();
        patronymicField.setLabel("Отчество (при наличии): ");

        Checkbox subscribeCheck = new Checkbox();
        subscribeCheck.setLabel("Подписаться на уведомления о мероприятиях");
        subscribeCheck.getStyle().set("position", "relative");
        subscribeCheck.getStyle().set("bottom", "45px");

        rightColumn.add(nameField, lastNameField, patronymicField);

        columns.add(leftColumn, rightColumn);
        columns.getStyle().set("align-self", "baseline");

        Button register = new Button();
        register.setText("Зарегистрироваться");
        register.getStyle().set("background-color", "#486AE0");
        register.getStyle().set("color", "white");
        register.getStyle().set("cursor", "pointer");
        register.getStyle().set("position", "relative");
        register.getStyle().set("bottom", "35px");
        register.addClickListener(evt -> {
            String loginValue = loginField.getValue();
            String passValue = passwordField.getValue();
            String nameValue = nameField.getValue();
            String lastNameValue = lastNameField.getValue();
            String patronymicValue = patronymicField.getValue();
            String emailValue = emailField.getValue();
            boolean checkboxValue = subscribeCheck.getValue();

            if (loginValue.equals("")) {
                loginField.setErrorMessage("Логин не может быть пустым");
                loginField.setInvalid(true);
            } else {
                loginField.setInvalid(false);
            }

            if (emailValue.equals("")) {
                emailField.setInvalid(true);
            } else {
                emailField.setInvalid(false);
            }

            if (passValue.equals("")) {
                passwordField.setInvalid(true);
            } else {
                passwordField.setInvalid(false);
            }

            if (nameValue.equals("")) {
                nameField.setInvalid(true);
            } else {
                passwordField.setInvalid(false);
            }

            if (lastNameValue.equals("")) {
                lastNameField.setInvalid(true);
            } else {
                lastNameField.setInvalid(false);
            }

            if (!loginField.isInvalid()
                    && !passwordField.isInvalid()
                    && !nameField.isInvalid()
                    && !lastNameField.isInvalid()
                    && !emailField.isInvalid()) {
                try {
                    User user = new User(nameValue, lastNameValue, patronymicValue, 0, loginValue, passValue, Role.REGULAR);
                    user.setEmail(emailValue);
                    user.setSubscribed(checkboxValue);
                    AuthManager.registerUser(user);
                    dialog.close();
                } catch (IllegalStateException e) {
                    loginField.setErrorMessage("Пользователь с таким логином уже зарегистрирован");
                    loginField.setInvalid(true);
                }
            }
        });

        layout.add(title);
        layout.add(columns);
        layout.add(subscribeCheck);
        layout.add(register);
        //layout.add(subscribeCheck);
        dialog.add(layout);
        dialog.setOpened(true);
    }
}

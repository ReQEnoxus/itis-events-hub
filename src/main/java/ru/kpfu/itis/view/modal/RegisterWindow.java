package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;


public class RegisterWindow {
    public static void show() {
        Dialog dialog = new Dialog();
        dialog.setWidth("250px");
        VerticalLayout layout = new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        VerticalLayout title = new VerticalLayout();
        H2 text = new H2("Регистрация");
        text.getStyle().set("color", "#FFFFFF");
        title.setAlignItems(FlexComponent.Alignment.CENTER);
        title.setHeight("150px");
        title.add(text);

        Span span = new Span();
        span.setHeight("25px");

        TextField loginField = new TextField();
        loginField.setRequired(true);
        loginField.setLabel("Логин: ");
        loginField.setErrorMessage("Логин не может быть пустым");

        PasswordField passwordField = new PasswordField();
        passwordField.setRequired(true);
        passwordField.setLabel("Пароль: ");
        passwordField.setErrorMessage("Пароль не может быть пустым");

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

        Button register = new Button();
        register.setText("Зарегистрироваться");
        register.getStyle().set("background-color", "#486AE0");
        register.getStyle().set("color", "white");
        register.getStyle().set("cursor", "pointer");
        register.addClickListener(evt -> {
            String loginValue = loginField.getValue();
            String passValue = passwordField.getValue();
            String nameValue = nameField.getValue();
            String lastNameValue = lastNameField.getValue();
            String patronymicValue = patronymicField.getValue();

            if (loginValue.equals("")) {
                loginField.setErrorMessage("Логин не может быть пустым");
                loginField.setInvalid(true);
            } else {
                loginField.setInvalid(false);
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
                    && !lastNameField.isInvalid()) {
                try {
                    User user = new User(nameValue, lastNameValue, patronymicValue, 0, loginValue, passValue, Role.REGULAR);
                    AuthManager.registerUser(user);
                    dialog.close();
                } catch (IllegalStateException e) {
                    loginField.setErrorMessage("Пользователь с таким логином уже зарегистрирован");
                    loginField.setInvalid(true);
                }
            }
        });
        layout.add(title);
        layout.add(loginField);
        layout.add(passwordField);
        layout.add(lastNameField);
        layout.add(nameField);
        layout.add(patronymicField);
        layout.add(span);
        layout.add(register);
        // красим оверлей)))
        UI.getCurrent().getPage().executeJavaScript("document.querySelector('#overlay').shadowRoot.querySelector('#overlay').style.backgroundImage = \"linear-gradient(180deg, #1676F3 180px, #FFFFFF 0)\"");
        dialog.add(layout);
        dialog.setOpened(true);
    }
}

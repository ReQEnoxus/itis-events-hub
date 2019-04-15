package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class LoginWindow {
    private Dialog dialog;

    public static void openLoginWindow() {
        new LoginWindow().dialog.open();
    }

    public LoginWindow() {
        this.dialog = new Dialog();
        this.dialog.add(createLoginComponents());
        this.dialog.setWidth("200px");
        this.dialog.setHeight("250px");
        this.dialog.setCloseOnOutsideClick(false);
    }

    private FormLayout createLoginComponents() {
        FormLayout formLayout = new FormLayout();
        formLayout.add("Войти в систему");
        TextField login = new TextField();
        login.setWidth("100%");
        formLayout.addFormItem(login, "Логин");
        PasswordField passwordField = new PasswordField();
        passwordField.setWidth("100%");
        formLayout.addFormItem(passwordField, "Пароль");
        Button button = new Button("Войти");
        formLayout.add(button);
        button.addClickListener(evt -> dialog.close());
        formLayout.setSizeFull();
        return formLayout;
    }
}

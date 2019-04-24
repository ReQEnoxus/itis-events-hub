package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import ru.kpfu.itis.auth.AuthManager;

public class LoginWindow {
    public static void show() {
        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setTitle("ITIS Events Hub");
        loginOverlay.setDescription("Войдите в систему");
        loginOverlay.setForgotPasswordButtonVisible(true);

        LoginI18n login = LoginI18n.createDefault();
        login.getErrorMessage().setTitle("Неверное имя пользователя или пароль");
        login.getErrorMessage().setMessage("Проверьте введенные данные");
        login.getForm().setPassword("Пароль");
        login.getForm().setSubmit("Войти");
        login.getForm().setTitle(null);
        login.getForm().setUsername("Логин");
        login.getForm().setForgotPassword("Регистрация");

        loginOverlay.setI18n(login);
        loginOverlay.addLoginListener(evt -> {
            try {
                AuthManager.loginUser(evt.getUsername(), evt.getPassword());
                loginOverlay.close();
                UI.getCurrent().getPage().reload();
            }
            catch (IllegalArgumentException e) {
                loginOverlay.setError(true);
            }
        });
        loginOverlay.addForgotPasswordListener(evt -> {
            loginOverlay.close();
            RegisterWindow.show();
        });
        loginOverlay.setOpened(true);
    }
}

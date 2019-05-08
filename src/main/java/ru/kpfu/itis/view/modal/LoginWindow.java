package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.service.security.MD5Util;

public class LoginWindow {
    public static void show() {
        LoginOverlay loginOverlay = new LoginOverlay();

        VerticalLayout titleLayout = new VerticalLayout();
        H1 title = new H1("ITIS Events HUB");
        title.getStyle().set("color", "#FFFFFF");
        title.getStyle().set("position", "relative");
        title.getStyle().set("right", "17px");
        title.getStyle().set("bottom", "50px");
        title.getStyle().set("width", "300px");
        Button exitButton = new Button(VaadinIcon.CLOSE.create());
        exitButton.addClickShortcut(Key.ESCAPE);
        exitButton.getStyle().set("color", "#FFFFFF");
        exitButton.getStyle().set("position", "relative");
        exitButton.getStyle().set("bottom", "35px");
        exitButton.getStyle().set("right", "35px");
        exitButton.addClickListener(evt -> loginOverlay.close());
        titleLayout.getStyle().set("height", "100px");
        titleLayout.add(exitButton);
        titleLayout.add(title);
        titleLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.START);

        loginOverlay.setTitle(titleLayout);
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
                AuthManager.loginUser(evt.getUsername(), MD5Util.md5Custom(evt.getPassword()));
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

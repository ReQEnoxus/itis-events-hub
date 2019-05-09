package ru.kpfu.itis.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.dao.jdbc.JdbcUserDaoImpl;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.EventService;
import ru.kpfu.itis.service.UserService;

import java.awt.*;
import java.io.File;

@Route("user")
public class UserWindow extends AbstractWindow implements HasUrlParameter<String> {
    private UserService userService = new UserService();

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        User userToShow = userService.get(s);

        HorizontalLayout userInfo = new HorizontalLayout();
        VerticalLayout userImage = new VerticalLayout();
        VerticalLayout userText = new VerticalLayout();
        HorizontalLayout nameVerif = new HorizontalLayout();

        Label userName = new Label(userToShow.getName() + " " + userToShow.getLastname());
        nameVerif.add(userName);
        if (userToShow.getRole() == Role.ADMIN || userToShow.getRole() == Role.VERIFIED) {
            Icon verified = new Icon(VaadinIcon.CHECK_CIRCLE);
            verified.setColor("#2BD317");
            verified.setSize("2pc");
            nameVerif.add(verified);
        }
        userName.getStyle().set("font-family", "Blogger Sans");
        userName.getStyle().set("font-style", "Italic");
        userName.getStyle().set("font-size", "3pc");
        userName.getStyle().set("font-weight", "bold");
        Label points = new Label("Баллы: " + userToShow.getPoints());
        points.getStyle().set("font-family", "Blogger Sans");
        points.getStyle().set("font-style", "Italic");
        points.getStyle().set("font-size", "2pc");
        userText.add(nameVerif, points);
        userToShow.setDescription("РАздватри");
        if (userToShow.getDescription() != null) {
            Label about = new Label("О себе: ");
            about.getStyle().set("font-family", "Blogger Sans");
            about.getStyle().set("font-style", "Italic");
            about.getStyle().set("font-size", "2pc");
            Label userDescription = new Label(userToShow.getDescription());
            userDescription.getStyle().set("margin-top", "1px");
            userDescription.getStyle().set("margin-left", "1vw");
            userDescription.getStyle().set("font-family", "Blogger Sans");
            userDescription.getStyle().set("font-style", "Italic");
            userDescription.getStyle().set("font-size", "2pc");
            userText.add(about, userDescription);
        }

        Image image = new Image("https://interior-dream.com.ua/files/products/ELD21081_1.1000x1000.jpg?cd3b616bc713172cb94f924e2e123fe7", "");
        image.setHeight("500px");
        image.setWidth("400px");
        userText.getStyle().set("min-width", "45vw");

        if (AuthManager.getCurrentUser().getLogin() != null) {
            if (AuthManager.getCurrentUser().getLogin().equals(userToShow.getLogin())) {
                if (userToShow.getDescription() == null) {
                    TextArea tf = new TextArea();
                    tf.setPlaceholder("Полезная информация");
                    tf.setWidth("30vw");
                    Button submit = new Button("Сохранить");
                    submit.addClickListener(f -> {
                        userToShow.setDescription(tf.getValue());
                        UI.getCurrent().getPage().reload();
                    });
                    userText.add(tf, submit);
                }
                else {
                    Button change = new Button("Изменить");
                    change.addClickListener(g -> {
                        //fixme (?)
                    });
                    userText.add(change);
                }
            }
        }

        userImage.add(image);
        userInfo.add(userImage, userText);
        userInfo.getStyle().set("align-self", "center");

        Label accomplishedEvents = new Label("Завершенные мероприятия: ");
        accomplishedEvents.getStyle().set("font-family", "Blogger Sans");
        accomplishedEvents.getStyle().set("font-style", "italic");
        accomplishedEvents.getStyle().set("font-size", "2pc");
        accomplishedEvents.getStyle().set("align-self", "center");

        EventService eventService = new EventService();


        add(userInfo);
        add(accomplishedEvents);
        for (Integer event:
                userToShow.getAccomplishedEvents()) {
            Label theEvent = new Label(eventService.get(event).getName());
            theEvent.getStyle().set("font-family", "Blogger Sans");
            theEvent.getStyle().set("font-size", "2pc");
            theEvent.getStyle().set("font-style", "italic");
            theEvent.getStyle().set("align-self", "center");
            add(theEvent);
        }

    }
}
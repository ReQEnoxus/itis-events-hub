package ru.kpfu.itis.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.EventService;
import ru.kpfu.itis.service.UserService;

@Route("user")
public class UserWindow extends AbstractWindow implements HasUrlParameter<String> {
    private UserService userService = new UserService();

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        User userToShow = userService.get(s);
        if (userToShow == null) {
            beforeEvent.rerouteTo("404");
            return;
        }
        UI.getCurrent().getPage().setTitle("Профиль пользователя " + s + " - ITIS Events HUB");
        tabs.setSelectedTab(tab4);
        HorizontalLayout userInfo = new HorizontalLayout();
        VerticalLayout userImage = new VerticalLayout();
        VerticalLayout userText = new VerticalLayout();
        HorizontalLayout nameVerif = new HorizontalLayout();

        Label userName = new Label(userToShow.getName() + " " + userToShow.getLastname());
        nameVerif.add(userName);
        if (userToShow.getRole() == Role.ADMIN || userToShow.getRole() == Role.VERIFIED) {
            Icon verified = new Icon(VaadinIcon.CHECK);
            verified.setColor("#2BD317");
            verified.setSize("1,5pc");
            nameVerif.add(verified);
        }
        userName.getStyle().set("font-family", "Blogger Sans");
        userName.getStyle().set("font-style", "Italic");
        userName.getStyle().set("font-size", "2pc");
        userName.getStyle().set("font-weight", "bold");
        Label points = new Label("Баллы: " + userToShow.getPoints());
        points.getStyle().set("font-family", "Blogger Sans");
        points.getStyle().set("font-style", "Italic");
        points.getStyle().set("font-size", "2pc");
        userText.add(nameVerif, points);

        if (userToShow.getDescription() != null) {
            Label about = new Label("О себе: ");
            about.getStyle().set("font-family", "Blogger Sans");
            about.getStyle().set("font-style", "Italic");
            about.getStyle().set("font-size", "2pc");
            Label userDescription = new Label(userToShow.getDescription());
            userDescription.getStyle().set("margin-top", "0px");
            userDescription.getStyle().set("margin-left", "1vw");
            userDescription.getStyle().set("font-family", "Blogger Sans");
            userDescription.getStyle().set("font-style", "Italic");
            userDescription.getStyle().set("font-size", "2pc");
            userText.add(about, userDescription);
        }

        Image image = new Image();
        image.setHeight("400px");
        image.setWidth("300px");

        if (AuthManager.getCurrentUser().getLogin() != null) {
            if (AuthManager.getCurrentUser().getLogin().equals(userToShow.getLogin())) {
                if (userToShow.getDescription() == null) {
                    TextArea tf = new TextArea();
                    tf.setPlaceholder("Полезная информация");
                    tf.setWidth("20vw");
                    Button submit = new Button("Сохранить");
                    submit.addClickListener(f -> {
                        userToShow.setDescription(tf.getValue());
                        UI.getCurrent().getPage().reload();
                    });
                    userText.add(tf, submit);
                }
                else {
                    Button change = new Button("Изменить");
                    TextArea ta = new TextArea();
                    Button submit = new Button("Сохранить");
                    submit.setVisible(false);
                    submit.addClickListener(f -> {
                        userToShow.setDescription(ta.getValue());
                        UI.getCurrent().getPage().reload();
                    });
                    ta.setVisible(false);
                    ta.setWidth("30vw");
                    change.addClickListener(g -> {
                        change.setVisible(false);
                        ta.setVisible(true);
                        submit.setVisible(true);
                    });
                    userText.add(change, ta, submit);
                }
            }
        }

        VerticalLayout eventsLayout = new VerticalLayout();

        Label accomplishedEvents = new Label("Завершенные мероприятия: ");
        accomplishedEvents.getStyle().set("margin-top", "0px");
        accomplishedEvents.getStyle().set("font-family", "Blogger Sans");
        accomplishedEvents.getStyle().set("font-style", "italic");
        accomplishedEvents.getStyle().set("font-size", "2pc");

        EventService eventService = new EventService();

        eventsLayout.add(accomplishedEvents);


        if (userToShow.getAccomplishedEvents().size() > 0) {
            for (Integer event :
                    userToShow.getAccomplishedEvents()) {
                Label theEvent = new Label(eventService.get(event).getName());
                theEvent.getStyle().set("font-family", "Blogger Sans");
                theEvent.getStyle().set("font-size", "2pc");
                theEvent.getStyle().set("font-style", "italic");
                theEvent.getStyle().set("margin-left", "1vw");
                theEvent.getStyle().set("margin-top", "0px");
                eventsLayout.add(theEvent);
            }
        }
        else {
            Label noEvents = new Label("Нет завершенных мероприятий");
            noEvents.getStyle().set("align-self", "center");
            noEvents.getStyle().set("font-size", "1pc");
            noEvents.getStyle().set("font-family", "Blogger Sans");
            noEvents.getStyle().set("font-style", "italic");
            noEvents.getStyle().set("color", "#c0c1c1");
            eventsLayout.add(noEvents);
        }

        eventsLayout.getStyle().set("min-width", "30vw");
        eventsLayout.getStyle().set("margin-top", "0px");
        userText.getStyle().set("padding", "0px");
        userImage.add(image);
        userInfo.setWidth("80vw");
        userInfo.add(userImage, userText, eventsLayout);

        add(userInfo);
    }
}
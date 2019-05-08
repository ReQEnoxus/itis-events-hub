package ru.kpfu.itis.factory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.service.EventService;
import ru.kpfu.itis.view.modal.EventInfo;
import ru.kpfu.itis.view.modal.LoginWindow;

public class ComponentFactoryEventPage implements ComponentFactory<ru.kpfu.itis.entity.Event> {

    @Override
    public Component create(Event entity) {

        HorizontalLayout mainLayout = new HorizontalLayout();
        VerticalLayout textLayout = new VerticalLayout();
        textLayout.setSpacing(false);
        Label nameDate = new Label(entity.getName());
        nameDate.getStyle().set("margin-right", "2vm");
        nameDate.getStyle().set("font-weight", "bold");
        nameDate.getStyle().set("font-style", "italic");
        nameDate.getStyle().set("font-size", "2pc");
        nameDate.getStyle().set("color", "#429A9B");
        Label dateName = new Label(entity.getDateStart() + " - " + entity.getDateEnd());
        dateName.getStyle().set("font-style", "italic");
        dateName.getStyle().set("margin-top", "1.2pc");
        dateName.getStyle().set("color", "#A9A9A9");
        dateName.getStyle().set("font-weight", "500");
        Label participantsLabel = new Label(entity.getParticipants().size() + "/" + entity.getCapacity());
        participantsLabel.getStyle().set("font-style", "italic");
        participantsLabel.getStyle().set("margin-top", "1.2pc");
        participantsLabel.getStyle().set("color", "#A9A9A9");
        participantsLabel.getStyle().set("font-wight", "500");
        HorizontalLayout nameDateLayout = new HorizontalLayout(nameDate, dateName, participantsLabel);
        Label description = new Label(entity.getDescription());

        description.getStyle().set("color", "#A9A9A9");
        description.getStyle().set("min-width", "50vm");
        description.getStyle().set("display", "-webkit-box");
        description.getStyle().set("-webkit-line-clamp", "3");
        description.getStyle().set("-webkit-box-orient", "vertical");
        description.getStyle().set("overflow", "hidden");
        Button information = new Button("Информация");
        information.getStyle().set("cursor", "pointer");
        information.getStyle().set("color", "#486AE0");
        information.getStyle().set("font-style", "italic");
        Button participate = new Button("Записаться");
        EventService eventService = new EventService();
        participate.addClickListener(evt -> {
            if (AuthManager.getCurrentUser().getLogin() != null) {
                if (!entity.getParticipants().contains(AuthManager.getCurrentUser())) {
                    if (entity.getParticipants().size() < entity.getCapacity()) {
                        entity.getParticipants().add(AuthManager.getCurrentUser());
                        Notification.show("Вы записались на мероприятие", 3000, Notification.Position.TOP_END);
                        eventService.update(entity.getId(), entity);
                        participantsLabel.setText(entity.getParticipants().size() + "/" + entity.getCapacity());
                    } else {
                        Notification.show("На это мероприятие не осталось свободных мест", 3000, Notification.Position.TOP_END);
                    }
                } else {
                    Notification.show("Вы уже записаны на это мероприятие", 3000, Notification.Position.TOP_END);
                }
            } else {
                LoginWindow.show();
            }
        });
        participate.getStyle().set("font-style", "italic");
        participate.getStyle().set("background-color", "#486AE0");
        participate.getStyle().set("color", "white");
        participate.getStyle().set("cursor", "pointer");
        HorizontalLayout buttons = new HorizontalLayout(information, participate);
        buttons.getStyle().set("margin-left", "10vm");
        buttons.getStyle().set("align-self","end");
        buttons.getStyle().set("margin-right", "20vm");
        information.addClickListener(e -> {
            EventInfo.openWindow(entity);
        });
        HorizontalLayout descriptionLayout = new HorizontalLayout(description);
        textLayout.add(nameDateLayout, descriptionLayout);
        textLayout.getStyle().set("width", "70vw");
//        buttons.setVerticalComponentAlignment(FlexComponent.Alignment.END);
        buttons.getStyle().set("margin-top", "5vw");
        mainLayout.add(textLayout, buttons);
        mainLayout.setWidth("96vw");
        mainLayout.setSpacing(false);
        return mainLayout;


    }
}
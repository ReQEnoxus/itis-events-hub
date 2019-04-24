package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.service.EventService;

import java.util.ArrayList;

public class EventCreateWindow {
    private static EventService eventService = new EventService();

    public static void openWindow() {
        Dialog mainWindow = new Dialog();
        mainWindow.setWidth("50vw");
        FormLayout eventLayout = new FormLayout();
        VerticalLayout mainLayout = new VerticalLayout();

        H1 label = new H1("Создание мероприятия");
        label.getStyle().set("font-style", "italic");
        label.getStyle().set("text-align", "center");
        label.getStyle().set("margin-top", "0.5pc");

        TextField name = new TextField();
        name.setRequired(true);
        name.setErrorMessage("Мероприятие должно иметь название");

        TextField place = new TextField();
        place.setRequired(true);
        place.setErrorMessage("Мероприятие должно иметь место проведения");

        DatePicker dateStart = new DatePicker();
        dateStart.setRequired(true);
        dateStart.setErrorMessage("Мероприятие должно иметь дату начала");

        DatePicker dateEnd = new DatePicker();
        dateEnd.setRequired(true);
        dateEnd.setErrorMessage("Мероприятие должно иметь дату окончания");

        TimePicker timeStart = new TimePicker();
        timeStart.setRequired(true);
        timeStart.setErrorMessage("Мероприятие должно иметь время начала");

        TimePicker timeEnd = new TimePicker();
        timeEnd.setRequired(true);
        timeEnd.setErrorMessage("Мероприятие должно иметь время конца");

        NumberField volunteers = new NumberField();
        volunteers.setErrorMessage("Мероприятие должно иметь определенное число волонтёров");

        NumberField prizes = new NumberField();
        prizes.setErrorMessage("Мероприятие должно стоить определенное количество баллов");

        Button createButton = new Button("Создать");
        createButton.getStyle().set("font-style", "italic");

        mainWindow.add(label);
        eventLayout.addFormItem(name, "Название мероприятия");
        eventLayout.addFormItem(place, "Местоположение");
        eventLayout.addFormItem(dateStart, "Дата начала");
        eventLayout.addFormItem(dateEnd, "Дата окончания");
        eventLayout.addFormItem(timeStart, "Время начала");
        eventLayout.addFormItem(timeEnd, "Время окончания");
        eventLayout.addFormItem(volunteers, "Количество волонтеров");
        eventLayout.addFormItem(prizes, "Количество баллов");
        TextArea desc = new TextArea();
        desc.setRequired(true);
        desc.setErrorMessage("Мероприятие должно иметь описание");
        desc.getStyle().set("width", "100%");
        desc.setLabel("Описание: ");
        //eventLayout.addFormItem(desc, "Описание");
        createButton.addClickListener(e -> {
            if (name.isEmpty()) {
                name.setInvalid(true);
            }
            if (place.isEmpty()) {
                place.setInvalid(true);
            }
            if (dateStart.isEmpty()) {
                dateStart.setInvalid(true);
            }
            if (dateEnd.isEmpty()) {
                dateEnd.setInvalid(true);
            }
            if (timeStart.isEmpty()) {
                timeStart.setInvalid(true);
            }
            if (timeEnd.isEmpty()) {
                timeEnd.setInvalid(true);
            }
            if (desc.isEmpty()) {
                desc.setInvalid(true);
            }
            if (volunteers.isEmpty()) {
                volunteers.setInvalid(true);
            }
            if (prizes.isEmpty()) {
                prizes.setInvalid(true);
            }
            if (!dateStart.isEmpty() && !dateEnd.isEmpty() && dateStart.getValue().isAfter(dateEnd.getValue())) {
                timeStart.setErrorMessage("Дата начала не может быть раньше даты конца мероприятия");
                timeEnd.setErrorMessage("Дата начала не может быть раньше даты конца мероприятия");
                timeStart.setInvalid(true);
                timeEnd.setInvalid(true);
                return;
            }
            if (!volunteers.isEmpty() && volunteers.getValue() < 0) {
                volunteers.setErrorMessage("Волонтеров не может быть отрицательное количество");
                volunteers.setInvalid(true);
                return;
            }
            if (!name.isEmpty() && !place.isEmpty() && !dateStart.isEmpty() && !dateEnd.isEmpty() && !timeEnd.isEmpty() && !timeStart.isEmpty() && !desc.isEmpty() && !volunteers.isEmpty() && !prizes.isEmpty()) {
                Event eventBeingCreated = new Event();
                eventBeingCreated.setActive(true);
                eventBeingCreated.setName(name.getValue());
                eventBeingCreated.setTimeStart(timeStart.getValue().toString());
                eventBeingCreated.setPlace(place.getValue());
                eventBeingCreated.setCapacity(volunteers.getValue().intValue());
                eventBeingCreated.setPrize(prizes.getValue().intValue());
                eventBeingCreated.setTimeEnd(timeEnd.getValue().toString());
                eventBeingCreated.setDateStart(dateStart.getValue().toString());
                eventBeingCreated.setDateEnd(dateEnd.getValue().toString());
                eventBeingCreated.setDescription(desc.getValue());
                eventBeingCreated.setParticipants(new ArrayList<>());
                eventBeingCreated.setHost(AuthManager.getCurrentUser().getLogin());
                eventService.create(eventBeingCreated);
                Notification.show("Мероприятие успешно создано", 3000, Notification.Position.TOP_END);
                mainWindow.close();
                UI.getCurrent().getPage().reload();
            }
        });

        mainLayout.add(eventLayout);
        mainLayout.add(desc);

        mainLayout.add(createButton);
        mainLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, createButton);
        mainWindow.add(mainLayout);
        mainWindow.open();
    }
}

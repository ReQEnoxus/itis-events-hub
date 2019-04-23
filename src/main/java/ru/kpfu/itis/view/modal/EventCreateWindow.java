package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.value.ValueChangeMode;
import ru.kpfu.itis.entity.Event;

import java.util.regex.Pattern;

public class EventCreateWindow {
    public static void openWindow() {
        Dialog mainWindow = new Dialog();
        mainWindow.setWidth("51pc");
        FormLayout eventLayout = new FormLayout();

        H1 label = new H1("Создание мероприятия");
        label.getStyle().set("font-style", "italic");
        label.getStyle().set("font-family", "Blogger Sans");
        label.getStyle().set("text-align", "center");
        label.getStyle().set("margin-top", "0.5pc");

        TextField name = new TextField();
        name.setRequired(true);
        TextField place = new TextField();
        place.setRequired(true);
        DatePicker dateStart = new DatePicker();
        dateStart.setRequired(true);
        DatePicker dateEnd = new DatePicker();
        dateEnd.setRequired(true);
        TimePicker timeStart = new TimePicker();
        timeStart.setRequired(true);
        TimePicker timeEnd = new TimePicker();
        timeEnd.setRequired(true);
        NumberField volunteers = new NumberField();
        NumberField prizes = new NumberField();
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
        desc.getStyle().set("width", "500px");
        eventLayout.addFormItem(desc, "Описание");
        createButton.addClickListener(e -> {
            if (!Pattern.matches("[a-zA-Z]+", name.getValue()) || !Pattern.matches("[а-яА-Я]+", name.getValue())) {
                Notification.show("В названии должны присутствовать буквы");
            }
            else if (dateStart.getValue().isAfter(dateEnd.getValue())) {
                Notification.show("Дата начала не может быть позже, чем окончания");
            }
            else if (timeStart.getValue().isAfter((timeStart.getValue()))) {
                Notification.show("Время начала не может быть позже, чем окончания");
            }
            else if (!name.isEmpty() && !place.isEmpty() && !dateStart.isEmpty() && !dateEnd.isEmpty() && !timeEnd.isEmpty() && !timeStart.isEmpty() && !desc.isEmpty() && !volunteers.isEmpty() && !prizes.isEmpty()) {
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
                Notification.show("Мероприятие успешно создано");
                mainWindow.close();
            }
            else {
                Notification.show("Заполните все поля");
            }
        });

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(createButton);
        buttonLayout.getStyle().set("place-content", "center");
        buttonLayout.getStyle().set("margin-top", "1pc");
        mainWindow.add(eventLayout);
        mainWindow.add(buttonLayout);
        mainWindow.open();
    }
}

package ru.kpfu.itis.factory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.User;

public class ComponentFactoryEditEventImpl implements ComponentFactory<Event> {

    @Override
    public Component create(Event entity) {
        VerticalLayout eventLayout = new VerticalLayout();
        eventLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        eventLayout.setWidth("100%");
        TextField nameField = new TextField("Название");
        nameField.setValue(entity.getName() + "");
        nameField.setAutoselect(true);
        nameField.setWidth("100%");
        eventLayout.add(nameField);
        TextField descriptionField = new TextField("Описание");
        descriptionField.setValue(entity.getDescription() + "");
        descriptionField.setAutoselect(true);
        descriptionField.setWidth("100%");
        eventLayout.add(descriptionField);
        Div div = new Div();
        div.setText("Список участников");
        eventLayout.add(div);
        if (entity.getParticipants() != null && entity.getParticipants().size() != 0) {
            for (User user : entity.getParticipants()) {
                TextField userData = new TextField();
                userData.setValue(user.getName() + " " + user.getLastname());
                userData.setAutoselect(true);
                userData.setWidth("100%");
                eventLayout.add(userData);
            }
        } else {
            Div d = new Div();
            d.setText("У этого мероприятия нет участников");
            eventLayout.add(d);
        }
        TextField prizeField = new TextField("Стоимость");
        prizeField.setValue(Integer.toString(entity.getPrize()));
        prizeField.setAutoselect(true);
        prizeField.setWidth("100%");
        eventLayout.add(prizeField);
        TextField capacityField = new TextField("Количество возможных участников");
        capacityField.setValue(Integer.toString(entity.getCapacity()));
        capacityField.setAutoselect(true);
        capacityField.setWidth("100%");
        eventLayout.add(capacityField);
        TextField hostField = new TextField("Создатель");
        hostField.setValue(entity.getHost().getName() + " " + entity.getHost().getLastname());
        hostField.setAutoselect(true);
        hostField.setWidth("100%");
        eventLayout.add(hostField);
        TextField idField = new TextField("Идентификационный номер");
        idField.setValue(Integer.toString(entity.getId()));
        idField.setAutoselect(true);
        idField.setWidth("100%");
        eventLayout.add(idField);
        TextField activeField = new TextField("Активно ли мероприятие");
        if (entity.isActive()) {
            activeField.setValue("Да");
        } else {
            activeField.setValue("Нет");
        }
        activeField.setAutoselect(true);
        activeField.setWidth("100%");
        eventLayout.add(activeField);
        TextField placeField = new TextField("Место проведения");
        placeField.setValue(entity.getPlace() + "");
        placeField.setAutoselect(true);
        placeField.setWidth("100%");
        eventLayout.add(placeField);
        TextField timeStartField = new TextField("Время начала");
        timeStartField.setValue(entity.getTimeStart() + "");
        timeStartField.setAutoselect(true);
        timeStartField.setWidth("100%");
        eventLayout.add(timeStartField);
        TextField timeEndField = new TextField("Время завершения");
        timeEndField.setValue(entity.getTimeEnd() + "");
        timeEndField.setAutoselect(true);
        timeEndField.setWidth("100%");
        eventLayout.add(timeEndField);
        TextField dateStartField = new TextField("Дата начала");
        dateStartField.setValue(entity.getDateStart() + "");
        dateStartField.setAutoselect(true);
        dateStartField.setWidth("100%");
        eventLayout.add(dateStartField);
        TextField dateEndField = new TextField("Дата завершения");
        dateEndField.setValue(entity.getDateEnd() + "");
        dateEndField.setAutoselect(true);
        dateEndField.setWidth("100%");
        eventLayout.add(dateEndField);
        return eventLayout;
    }
}

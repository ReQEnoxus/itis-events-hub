package ru.kpfu.itis.factory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.User;

public class ComponentFactoryEditUserImpl implements ComponentFactory<User> {
    @Override
    public Component create(User entity) {
        VerticalLayout userLayout = new VerticalLayout();
        userLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        userLayout.setWidth("100%");
        TextField nameField = new TextField("Имя");
        nameField.setValue(entity.getName() + "");
        nameField.setAutoselect(true);
        nameField.setWidth("100%");
        userLayout.add(nameField);
        TextField lastNameField = new TextField("Фамилия");
        lastNameField.setValue(entity.getLastname() + "");
        lastNameField.setAutoselect(true);
        lastNameField.setWidth("100%");
        userLayout.add(lastNameField);
        TextField patronymicField = new TextField("Отчество");
        patronymicField.setValue(entity.getPatronymic() + "");
        patronymicField.setAutoselect(true);
        patronymicField.setWidth("100%");
        userLayout.add(patronymicField);
        TextField idField = new TextField("Идентификационный номер");
        idField.setValue(Integer.toString(entity.getId()));
        idField.setAutoselect(true);
        idField.setWidth("100%");
        userLayout.add(idField);
        TextField pointsField = new TextField("Количество очков");
        pointsField.setValue(Integer.toString(entity.getPoints()));
        pointsField.setAutoselect(true);
        pointsField.setWidth("100%");
        userLayout.add(pointsField);
        TextField loginField = new TextField("Логин");
        loginField.setValue(entity.getLogin() + "");
        loginField.setAutoselect(true);
        loginField.setWidth("100%");
        userLayout.add(loginField);
        TextField passwordField = new TextField("Пароль");
        passwordField.setValue(entity.getPassword() + "");
        passwordField.setAutoselect(true);
        passwordField.setWidth("100%");
        userLayout.add(passwordField);
        Div div = new Div();
        div.setText("Посещенные мероприятия");
        userLayout.add(div);
        if (entity.getAccomplishedEvents() != null && entity.getAccomplishedEvents().size() != 0) {
            for (Event event : entity.getAccomplishedEvents()) {
                TextField eventField = new TextField();
                eventField.setValue(event.getName() + "");
                eventField.setAutoselect(true);
                eventField.setWidth("100%");
                userLayout.add(eventField);
            }
        } else {
            Div d = new Div();
            d.setText("У этого волонтера нет посещенных мероприятий");
            userLayout.add(d);
        }
        TextField roleField = new TextField("Статус");
        roleField.setValue(entity.getRole().toString());
        roleField.setAutoselect(true);
        roleField.setWidth("100%");
        userLayout.add(roleField);
        return userLayout;
    }
}

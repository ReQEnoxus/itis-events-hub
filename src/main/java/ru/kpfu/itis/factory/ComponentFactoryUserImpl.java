package ru.kpfu.itis.factory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import ru.kpfu.itis.entity.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ComponentFactoryUserImpl implements ComponentFactory<User> {
    private HashMap<Checkbox, Boolean> map = new HashMap<>();
    private ArrayList<Checkbox> checkboxes = new ArrayList<>();

    public HashMap<Checkbox, Boolean> getMap() {
        return map;
    }

    public ArrayList<Checkbox> getCheckboxes() {
        return checkboxes;
    }

    @Override
    public Component create(User user) {
        HorizontalLayout userLayout = new HorizontalLayout();
        userLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        userLayout.setWidth("100%");
        TextField userData = new TextField();
        userData.setValue(user.getName() + " " + user.getLastname() + " " + user.getPatronymic());
        userData.setReadOnly(true);
        Checkbox confirmUser = new Checkbox();
        confirmUser.setValue(true);
        map.put(confirmUser, confirmUser.getValue());
        confirmUser.addValueChangeListener(event -> map.put(confirmUser, !map.get(confirmUser)));
        checkboxes.add(confirmUser);
        userLayout.add(userData, confirmUser);
        userLayout.expand(userData);
        return userLayout;
    }
}

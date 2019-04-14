package ru.kpfu.itis.factory;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import ru.kpfu.itis.entity.User;

public class ComponentFactoryUserImpl implements ComponentFactory<User> {
    @Override
    public HorizontalLayout create(User user) {
        HorizontalLayout userLayout = new HorizontalLayout();
        userLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        userLayout.setWidth("100%");
        TextField userData = new TextField();
        userData.setValue(user.getName() + " " + user.getLastname());
        userData.setReadOnly(true);
        Checkbox confirmUser = new Checkbox();
        confirmUser.setValue(true);
        userLayout.add(userData, confirmUser);
        userLayout.expand(userData);
        return userLayout;
    }
}

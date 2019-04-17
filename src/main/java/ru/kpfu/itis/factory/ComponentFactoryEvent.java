package ru.kpfu.itis.factory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import ru.kpfu.itis.entity.Event;

import javax.swing.*;

public class ComponentFactoryEvent implements ComponentFactory<Event> {
    @Override
    public Component create(Event entity) {
        HorizontalLayout hl = new HorizontalLayout();
        Label eventName = new Label(entity.getName());
        eventName.getStyle().set("color", "c0c1c1");
        eventName.getStyle().set("align-self", "center");
        eventName.getStyle().set("font-weight", "bold");
        eventName.getStyle().set("font-size", "larger");
        eventName.getStyle().set("width", "20pc");
        Button button = new Button("Изменить");
        button.getStyle().set("cursor", "pointer");
        hl.add(eventName, button);
        return hl;
    }
}

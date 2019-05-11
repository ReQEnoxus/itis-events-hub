package ru.kpfu.itis.factory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.service.EventService;
import ru.kpfu.itis.view.modal.EditElementOfDatabaseWindow;

public class ComponentFactoryEvent implements ComponentFactory<Event> {
    @Override
    public Component create(Event entity) {
        EventService eventService = new EventService();
        Dialog dialog = new Dialog();
        dialog.add(new Label("Вы уверены?"));
        Button dialogYes = new Button("Да");
        dialogYes.getStyle().set("cursor", "pointer");
        dialogYes.getStyle().set("color", "red");
        dialogYes.addClickListener(evt -> {
            eventService.delete(entity.getId());
            dialog.close();
            UI.getCurrent().getPage().reload();
        });
        Button dialogNo = new Button("Нет");
        dialogNo.getStyle().set("cursor", "pointer");
        dialogNo.addClickListener(evt -> {
            dialog.close();
        });
        dialog.add(new HorizontalLayout(dialogYes, dialogNo));

        HorizontalLayout hl = new HorizontalLayout();
        Label eventName = new Label(entity.getName());
        eventName.getStyle().set("color", "c0c1c1");
        eventName.getStyle().set("align-self", "center");
        eventName.getStyle().set("font-weight", "bold");
        eventName.getStyle().set("font-size", "larger");
        eventName.getStyle().set("width", "20pc");
        Button button = new Button("Изменить");
        button.getStyle().set("cursor", "pointer");
        button.addClickListener(evt -> EditElementOfDatabaseWindow.show(entity));

        Button delete = new Button("Удалить");
        delete.getStyle().set("color", "red");
        delete.getStyle().set("cursor", "pointer");
        delete.addClickListener(evt -> {
            dialog.setOpened(true);
        });

        hl.add(eventName, button, delete);
        return hl;
    }
}

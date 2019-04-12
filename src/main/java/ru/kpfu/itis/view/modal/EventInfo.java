package ru.kpfu.itis.view.modal;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;

import java.util.ArrayList;
import java.util.List;

public class EventInfo {

    private Event event;

    public EventInfo(Event event) {
        this.event = event;
    }

    public void openWindow() {
        if (event.isActive()) {
            openWindowActive();
        } else openWindowEnded();
    }

    private void openWindowEnded() {

        H1 label = new H1("Мероприятие завершено");
        label.getStyle().set("font-size", "-webkit-xxx-large");
        label.getStyle().set("color", "red");
        label.getStyle().set("text-align", "center");
        Dialog dialog = new Dialog();
        dialog.setWidth("600px");
        dialog.setHeight("150px");
        dialog.add(label);

        dialog.open();

    }

    private void openWindowActive() {

        VerticalLayout panelContent = new VerticalLayout();
        Text description = new Text(event.getDescription());
        panelContent.addComponentAsFirst(description);

        Label label = new Label(event.getName());

        VerticalLayout labelLayout = new VerticalLayout(label);

        label.getStyle().set("font-family", "Impact");
        label.getStyle().set("align-self", "center");
        label.getStyle().set("fontSize", "32px");


        Dialog dialog = new Dialog();

        Button exitButton = new Button("Закрыть");
        exitButton.addClickListener(e -> {
            dialog.close();
        });
        HorizontalLayout placeInfo = new HorizontalLayout();
        HorizontalLayout timeInfo = new HorizontalLayout();
        HorizontalLayout dateInfo = new HorizontalLayout();
        HorizontalLayout volunteersInfo = new HorizontalLayout();
        HorizontalLayout prizesInfo = new HorizontalLayout();
        Icon location = new Icon(VaadinIcon.LOCATION_ARROW);
        Icon date = new Icon(VaadinIcon.CALENDAR);
        Icon time = new Icon(VaadinIcon.CLOCK);
        Icon volunteers = new Icon(VaadinIcon.USERS);
        Icon prizes = new Icon(VaadinIcon.CHECK);
        location.getStyle().set("margin-right", "5px");
        time.getStyle().set("margin-right", "5px");
        date.getStyle().set("margin-right", "5px");
        volunteers.getStyle().set("margin-right", "5px");
        prizes.getStyle().set("margin-right", "5px");
        location.setColor("#c0c1c1");
        date.setColor("#c0c1c1");
        time.setColor("#c0c1c1");
        volunteers.setColor("#c0c1c1");
        prizes.setColor("#c0c1c1");
        placeInfo.getStyle().set("margin-bottom", "5px");
        placeInfo.getStyle().set("margin-top", "12px");
        timeInfo.getStyle().set("margin-bottom", "5px");
        volunteers.getStyle().set("margin-bottom", "5px");
        dateInfo.getStyle().set("margin-bottom", "5px");
        prizes.getStyle().set("margin-bottom", "12px");
        placeInfo.add(location);
        placeInfo.add(event.getPlace());
        dateInfo.add(date);
        dateInfo.add(event.getDateStart() + " - " + event.getDateEnd());
        timeInfo.add(time);
        timeInfo.add(event.getTimeStart() + " - " + event.getTimeEnd());
        volunteersInfo.add(volunteers);
        volunteersInfo.add(event.getCapacity() + " волонтера");
        prizesInfo.add(prizes);
        prizesInfo.add(event.getPrize() + " балла");
        dialog.add(labelLayout);
        dialog.add(placeInfo, dateInfo, timeInfo, volunteersInfo, prizesInfo);
        dialog.add(new HorizontalLayout(panelContent));
        dialog.setWidth("600px");
        dialog.setHeight("650px");

        User user1 = new User();
        user1.setName("Азат");
        user1.setLastname("Валиев");
        user1.setPatronymic("Дамирович");

        User user2 = new User();
        user2.setName("Дмитрий");
        user2.setLastname("Беляков");
        user2.setPatronymic("Олегович");

        List<User> volunteerList = new ArrayList<>();
        volunteerList.add(user1);
        volunteerList.add(user2);

        Grid<User> volunteerGrid = new Grid<>(User.class);
        volunteerGrid.setColumns("lastname", "name", "patronymic");
        volunteerGrid.setItems(volunteerList);

        volunteerGrid.setHeight("200px");

        HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.add(volunteerGrid);
        dialog.add(gridLayout);
        VerticalLayout buttonLayout = new VerticalLayout();

        if (AuthManager.getCurrentUser().getRole().equals(Role.ADMIN) || AuthManager.getCurrentUser().getRole().equals(Role.VERIFIED)) {
            Button endButton = new Button("Завершить");
            endButton.getStyle().set("color", "red");
            endButton.addClickListener(e ->
                    {
                        dialog.close();
                        event.setActive(false);
                    }
            );
            buttonLayout.add(endButton);
            dialog.add(buttonLayout);
        }

        dialog.open();
    }

}

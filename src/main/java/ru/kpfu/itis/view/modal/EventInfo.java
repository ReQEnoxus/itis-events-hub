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

    private static Event event;

    public static void openWindow(Event eventToShow) {
        event = eventToShow;
        if (event.isActive()) {
            openWindowActive();
        } else openWindowEnded();
    }

    private static void openWindowEnded() {

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

    private static void openWindowActive() {

        VerticalLayout panelContent = new VerticalLayout();
        Text description = new Text(event.getDescription());
        panelContent.addComponentAsFirst(description);

        Label label = new Label(event.getName());

        VerticalLayout labelLayout = new VerticalLayout(label);

        label.getStyle().set("font-family", "Blogger Sans");
        label.getStyle().set("font-weight", "bold");
        label.getStyle().set("fontSize", "32px");


        Dialog dialog = new Dialog();

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
        if (event.getCapacity() == 1) {
            volunteersInfo.add(event.getCapacity() + " волонтер");
        }
        else if (event.getCapacity() > 1 && event.getCapacity() < 5) {
            volunteersInfo.add(event.getCapacity()+ " волонтера");
        }
        else volunteersInfo.add(event.getCapacity() + " волонтеров");
        prizesInfo.add(prizes);
        if (event.getPrize() == 1) {
            prizesInfo.add(event.getPrize() + " балл");
        }
        else if (event.getPrize() >1 && event.getPrize() < 5) {
            prizesInfo.add(event.getPrize() + " балла");
        }
        else prizesInfo.add(event.getPrize() + " баллов");
        dialog.add(labelLayout);
        dialog.add(placeInfo, dateInfo, timeInfo, volunteersInfo, prizesInfo);
        dialog.add(new HorizontalLayout(panelContent));
        dialog.setWidth("600px");
        dialog.setHeight("650px");

        List<User> volunteerList = event.getParticipants();

        Grid<User> volunteerGrid = new Grid<>(User.class);
        volunteerGrid.setColumns("lastname", "name", "patronymic");
        volunteerGrid.setItems(volunteerList);

        volunteerGrid.setHeight("200px");

        HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.add(volunteerGrid);
        dialog.add(gridLayout);
        VerticalLayout buttonLayout = new VerticalLayout();

        if (AuthManager.getCurrentUser().getRole().equals(Role.ADMIN) || AuthManager.getCurrentUser().equals(event.getHost())) {
            Button endButton = new Button("Завершить");
            endButton.getStyle().set("color", "red");
            buttonLayout.add(endButton);
            dialog.add(buttonLayout);
        }

        dialog.open();
    }

}

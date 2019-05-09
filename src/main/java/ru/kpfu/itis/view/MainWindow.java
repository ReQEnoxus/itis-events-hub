package ru.kpfu.itis.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.factory.ComponentFactoryEventPage;
import ru.kpfu.itis.service.EventService;
import ru.kpfu.itis.view.modal.EventCreateWindow;

import java.util.List;

@Route("")
public class MainWindow extends AbstractWindow {
    public MainWindow(){
        tabs.setSelectedTab(tab2);
        if(AuthManager.getCurrentUser().getRole() == Role.ADMIN || AuthManager.getCurrentUser().getRole() == Role.VERIFIED){
            Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
            plusButton.getStyle().set("background-color", "rgb(17, 173, 79)");
            plusButton.getStyle().set("color", "white");
            plusButton.getStyle().set("cursor", "pointer");
            plusButton.addClickListener(evt -> EventCreateWindow.openWindow());
            setContent(plusButton);
        }
        EventService eventService = new EventService();
        List<Event> eventList = eventService.getActive();

        if (eventList.size() == 0) {
            Label label = new Label();
            label.setText("Нет ни одного активного мероприятия");
            label.getStyle().set("color", "#47494c");
            label.getStyle().set("align-self", "center");
            setContent(label);
        } else {
            ComponentFactoryEventPage eventPage = new ComponentFactoryEventPage();
            for (int i = 0; i < eventList.size(); i++) {
                setContent(eventPage.create(eventList.get(i)));
                Label hr = new Label();
                hr.getElement().setProperty("innerHTML", "<hr>");
                hr.getStyle().set("width", "100%");
                setContent(hr);
            }
        }
    }
}

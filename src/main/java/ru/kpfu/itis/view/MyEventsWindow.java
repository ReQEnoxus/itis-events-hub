package ru.kpfu.itis.view;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.factory.ComponentFactoryEventUnsub;
import ru.kpfu.itis.service.EventService;

@PageTitle("Мои мероприятия - ITIS Events HUB")
@Route("participating")
public class MyEventsWindow extends AbstractWindow {
    public MyEventsWindow() {
        tabs.setSelectedTab(tab3);
        if (AuthManager.getCurrentUser().getCurrentEvents().isEmpty()) {
            Label label = new Label();
            label.setText("Вы не участвуете ни в одном мероприятии");
            label.getStyle().set("color", "#47494c");
            label.getStyle().set("align-self", "center");
            setContent(label);
        } else {
            EventService es = new EventService();
            ComponentFactoryEventUnsub unsub = new ComponentFactoryEventUnsub();
            for (int id : AuthManager.getCurrentUser().getCurrentEvents()) {
                Event event = es.get(id);
                if (event.isActive()) {
                    setContent(unsub.create(event));
                    Label hr = new Label();
                    hr.getElement().setProperty("innerHTML", "<hr>");
                    hr.getStyle().set("width", "100%");
                    setContent(hr);
                }
            }
        }
    }
}

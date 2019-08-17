package ru.kpfu.itis.service.api;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;

@Route("events")
public class EventsUI extends UI {
    @Override
    protected void init(VaadinRequest request) {
        getSession().addRequestHandler(new EventAPIRequestHandler());
    }
}

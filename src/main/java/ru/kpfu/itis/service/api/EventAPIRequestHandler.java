package ru.kpfu.itis.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vaadin.flow.server.RequestHandler;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinSession;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.service.EventService;

import java.io.IOException;
import java.util.List;

public class EventAPIRequestHandler implements RequestHandler {
    @Override
    public boolean handleRequest(VaadinSession vaadinSession, VaadinRequest vaadinRequest, VaadinResponse vaadinResponse) throws IOException {

        List<Event> events;
        EventService es = new EventService();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        events = es.getActive();

        try {
            String json = mapper.writeValueAsString(events);
            vaadinResponse.setContentType("text/plain");
            vaadinResponse.getWriter().write(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return true;
    }
}

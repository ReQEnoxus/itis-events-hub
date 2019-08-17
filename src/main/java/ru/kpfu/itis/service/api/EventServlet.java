package ru.kpfu.itis.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vaadin.flow.server.VaadinServlet;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/events", name = "EventServlet")
public class EventServlet extends VaadinServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Event> events;
        EventService es = new EventService();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        events = es.getActive();

        try {
            String json = mapper.writeValueAsString(events);
            resp.getWriter().write(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

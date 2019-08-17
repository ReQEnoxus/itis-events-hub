package ru.kpfu.itis.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.service.EventService;

import java.util.ArrayList;
import java.util.List;

@Route("events")
public class EventAPITest extends VerticalLayout implements HasUrlParameter<String> {
    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        List<Event> events = new ArrayList<>();
        EventService es = new EventService();

        if (s.equals("active")) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            ObjectWriter writer = mapper.writer().withoutAttribute("participants");
            events = es.getActive();

            try {
                String json = writer.writeValueAsString(events);
                add(json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}

package ru.kpfu.itis.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import javafx.scene.shape.Line;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.view.modal.EventInfo;

import javax.xml.ws.handler.HandlerResolver;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.kpfu.itis.entity.Role.VERIFIED;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {

    public MainView() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {


        User user1 = new User();
        user1.setName("Иван");
        user1.setLastname("Иванов");
        user1.setPatronymic("Иванович");
        User user2 = new User();
        user2.setName("Петр");
        user2.setLastname("Петров");
        user2.setPatronymic("Петрович");

        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);

        Event world = new Event();
        world.setDescription("Международное некоммерческое движение, целью которого является повышение престижа рабочих профессий и развитие навыков мастерства");
        world.setName("World Skills Russia");
        world.setDateStart("21.06.2019");
        world.setDateEnd("27.06.2019");
        world.setParticipants(list);
        world.setCapacity(6);

        Event studSpring = new Event();
        studSpring.setName("StudVesna");
        studSpring.setDescription("Ежегодный фестиваль, проводимый в вузах и ссузах, регионах и на Всероссийском уровне, а также крупнейшая площадка для обмена и реализации творческих идей. ");
        studSpring.setDateStart("22.07.2019");
        studSpring.setDateEnd("28.07.2019");
        studSpring.setParticipants(list);
        studSpring.setCapacity(8);
//        ComponentFactoryAdmin<Event> eventFactory = new ComponentFactoryAdmin<>();
//        add(eventFactory.create(world));
//        add(eventFactory.create(studSpring));

        ComponentFactoryPage cfp = new ComponentFactoryPage();
        add(cfp.create(world));
        add(cfp.create(studSpring));


    }
}

package ru.kpfu.itis.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.factory.ComponentFactoryEvent;
import ru.kpfu.itis.factory.ComponentFactoryEventPage;
import ru.kpfu.itis.factory.ComponentFactoryUser;

import java.util.ArrayList;
import java.util.List;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {

    public MainView() {

        User user1 = new User();
        user1.setName("Ivan");
        user1.setPatronymic("Ivanovich");
        user1.setLastname("Ivanov");
        user1.setRole(Role.ADMIN);

        VaadinSession.getCurrent().setAttribute("user", user1);

        User user2 = new User();
        user2.setName("Ivan");
        user2.setPatronymic("Ivanovich");
        user2.setLastname("Ivanov");

        List list = new ArrayList();
        list.add(user1);
        list.add(user2);

        Event world = new Event();
        world.setName("World Skills Russia");
        world.setCapacity(4);
        world.setDescription("WorldSkills International (WSI, от англ. skills — «умения») — международная некоммерческая ассоциация, целью которой является повышение статуса и стандартов профессиональной подготовки и квалификации по всему миру, популяризация рабочих профессий через проведение международных соревнований по всему миру. Основана в 1953 году. На сегодняшний день в деятельности организации принимают участие 77 стран. Своей миссией WSI называет привлечение внимания к рабочим профессиям и создание условий для развития высоких профессиональных стандартов. Её основная деятельность — организация и проведение профессиональных соревнований различного уровня для молодых людей в возрасте до 22 лет. Раз в два года проходит мировой чемпионат рабочих профессий WorldSkills, который также называют «Олимпиадой для рабочих рук». В настоящее время это крупнейшее соревнование подобного рода.");
        world.setDateStart("21.06.2019");
        world.setDateEnd("29.06.2019");
        world.setParticipants(list);
        world.setPlace("Кремлевская");
        world.setTimeStart("10-00");
        world.setTimeEnd("10-00");
        world.setActive(true);
        world.setHost(user1);

        Event studVesna = new Event();
        studVesna.setPlace("Баумана");
        studVesna.setCapacity(10);
        studVesna.setParticipants(list);
        studVesna.setDateStart("22.09.2011");
        studVesna.setDateEnd("23.11.2012");
        studVesna.setDescription("Ежегодный фестиваль, проводимый в вузах и ссузах, регионах и на Всероссийском уровне, а также крупнейшая площадка для обмена и реализации творческих идей.");
        studVesna.setName("StudSpring");
        studVesna.setActive(true);
        studVesna.setTimeStart("10-00");
        studVesna.setTimeEnd("10-00");


        ComponentFactoryEventPage cmp = new ComponentFactoryEventPage();
        add(cmp.create(world));
        add(cmp.create(studVesna));

    }
}

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

    }
}

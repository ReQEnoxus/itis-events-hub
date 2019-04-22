package ru.kpfu.itis.view;

import com.flowingcode.vaadin.addons.carousel.Carousel;
import com.flowingcode.vaadin.addons.carousel.Slide;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWindow extends VerticalLayout {
    private List<Slide> list = new ArrayList<>();
    private boolean bool = false;
    private final String COLOR = "#439acf";
    private final String COLORWHITE = "#ffffff";
    private Carousel carousel;
    private VerticalLayout verticalLayout = new VerticalLayout();
    private VerticalLayout contentLayout = new VerticalLayout();
    Tabs tabs;


    public AbstractWindow() {
        setPadding(false);
        setSpacing(false);
        verticalLayout.setSpacing(false);
        verticalLayout.setPadding(false);
        contentLayout.setPadding(true);
        add(verticalLayout);
        add(contentLayout);
    }

    public void setContent(Component component){
        contentLayout.add(component);
    }

    public void getTabs(){
        Tab tab1 = new Tab("Рейтинг");
        Tab tab2 = new Tab("Мероприятия");
        Tab tab3 = new Tab("Мои мероприятия");
        Tab tab4 = new Tab("Профиль");
        Tab tab5 = new Tab("Выйти");
        tabs = new Tabs(tab1, tab2, tab3, tab4, tab5);

        tabs.getStyle().set("-webkit-text-fill-color", COLORWHITE);
        tabs.setFlexGrowForEnclosedTabs(1.0);
        tabs.setWidth("100%");
        tabs.getStyle().set("background-color", COLOR);

        verticalLayout.add(tabs);
    }
    public void addComponent(String string, String color) {
        list.add(new Slide(createSlideContent(string, color)));
        Slide[] arrList = new Slide[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arrList[i] = list.get(i);
        }
        /*Carousel*/
        carousel = new Carousel(arrList);
        carousel.setWidth("100%");
        carousel.setHeight("350px");
        carousel.setSlideDuration(10);
        carousel.withAutoProgress();
        if (bool) {
            verticalLayout.addComponentAsFirst(carousel);
        }
        bool = true;
    }

    private Component createSlideContent(String string, String color) {
        H1 label = new H1(string);
        label.getStyle().set("margin-top", "auto");
        label.getStyle().set("margin-bottom", "auto");
        VerticalLayout vl = new VerticalLayout(label);
        vl.setAlignItems(FlexComponent.Alignment.CENTER);
        vl.setSizeFull();
        vl.getStyle().set("background-color", color);
        return vl;
    }

}

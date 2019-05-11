package ru.kpfu.itis.view;

import com.flowingcode.vaadin.addons.carousel.Carousel;
import com.flowingcode.vaadin.addons.carousel.Slide;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.view.modal.LoginWindow;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWindow extends VerticalLayout {
    private List<Slide> list = new ArrayList<>();
    private boolean bool = false;
    private final String COLOR_GREY = "rgb(150, 163, 171)";
    private final String COLOR_BLUE = "#1676F3";
    private final String COLOR_WHITE = "#ffffff";
    private Carousel carousel;
    private VerticalLayout verticalLayout = new VerticalLayout();
    private VerticalLayout contentLayout = new VerticalLayout();
    protected Tabs tabs;
    protected Tab tab1 = new Tab("Рейтинг");
    protected Tab tab2 = new Tab("Мероприятия");
    protected Tab tab3 = new Tab("Мои мероприятия");
    protected Tab tab4 = new Tab("Профиль");
    protected Tab tab5 = new Tab("Выйти");
    protected Tab tab6 = new Tab("Войти");

    private class PreviousTabContainer {
        Tab prevSelected;
    }


    public AbstractWindow() {
        setPadding(false);
        setSpacing(false);
        verticalLayout.setSpacing(false);
        verticalLayout.setPadding(false);
        contentLayout.setPadding(true);
        tabs = new Tabs(tab1, tab2, tab3, tab4, tab5, tab6);
        if (AuthManager.getCurrentUser().getLogin() != null) {
            tab1.setVisible(true);
            tab2.setVisible(true);
            tab3.setVisible(true);
            tab4.setVisible(true);
            tab5.setVisible(true);
            tab6.setVisible(false);
        } else {
            tab1.setVisible(true);
            tab2.setVisible(true);
            tab3.setVisible(false);
            tab4.setVisible(false);
            tab5.setVisible(false);
            tab6.setVisible(true);
        }
        PreviousTabContainer previouslySelectedContainer = new PreviousTabContainer();
        previouslySelectedContainer.prevSelected = tabs.getSelectedTab();

        tabs.addSelectedChangeListener(selectedChangeEvent -> {
            if (tabs.getSelectedTab().equals(tab1)) {
                UI.getCurrent().navigate("rating");
            } else if (tabs.getSelectedTab().equals(tab2)) {
                UI.getCurrent().navigate("");
            } else if (tabs.getSelectedTab().equals(tab3)) {
                UI.getCurrent().navigate("participating");
            } else if (tabs.getSelectedTab().equals(tab4)) {
                UI.getCurrent().navigate("user/" + AuthManager.getCurrentUser().getLogin());
            } else if (tabs.getSelectedTab().equals(tab5)) {
                AuthManager.logoutUser();
                tabs.setSelectedTab(previouslySelectedContainer.prevSelected);
                UI.getCurrent().navigate("");
            } else {
                LoginWindow.show();
                tabs.setSelectedTab(previouslySelectedContainer.prevSelected);
            }
            previouslySelectedContainer.prevSelected = tabs.getSelectedTab();
        });

        tabs.getStyle().set("-webkit-text-fill-color", COLOR_WHITE);
        tabs.setFlexGrowForEnclosedTabs(1.0);
        tabs.setWidth("100%");
        tabs.getStyle().set("background-color", COLOR_BLUE);

        addCarouselComponent("ITIS EVENTS HUB");
        addCarouselComponent("Единая площадка для проведения мероприятий");
        verticalLayout.add(tabs);
        add(verticalLayout);
        add(contentLayout);
    }

    public void setContent(Component component){
        contentLayout.add(component);
    }

    private void addCarouselComponent(String string) {
        list.add(new Slide(createSlideContent(string)));
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

    private Component createSlideContent(String string) {
        H1 label = new H1(string);
        label.getStyle().set("margin-top", "auto");
        label.getStyle().set("margin-bottom", "auto");
        label.getStyle().set("color", "white");
        label.getStyle().set("font-size", "3vw");
        VerticalLayout vl = new VerticalLayout(label);
        vl.setAlignItems(FlexComponent.Alignment.CENTER);
        vl.setSizeFull();
        vl.getStyle().set("background-image", "linear-gradient(to top, #1676F3, #021b79)");
        return vl;
    }

}

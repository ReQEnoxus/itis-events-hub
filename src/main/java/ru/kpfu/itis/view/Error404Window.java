package ru.kpfu.itis.view;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("404")
public class Error404Window extends VerticalLayout {

    public Error404Window() {
        H1 h1 = new H1();
        h1.setText("404 - Страница не найдена");
        Anchor anchor = new Anchor("", "Вернуться на главную страницу");
        add(h1, anchor);
    }

}

package ru.kpfu.itis.view;

import com.vaadin.flow.component.html.H1;

public class Error404Window extends AbstractWindow{
    public Error404Window() {
        H1 h1 = new H1();
        h1.setText("404 - Страница не найдена");
        setContent(h1);
    }
}

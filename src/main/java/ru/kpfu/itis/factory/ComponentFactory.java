package ru.kpfu.itis.factory;

import com.vaadin.flow.component.Component;

public interface ComponentFactory<T> {
    Component create(T entity);
}

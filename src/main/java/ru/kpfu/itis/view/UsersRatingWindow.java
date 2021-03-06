package ru.kpfu.itis.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.haijian.Exporter;
import ru.kpfu.itis.auth.AuthManager;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.UserService;

import java.util.List;
@PageTitle("Рейтинг волонтеров - ITIS Events HUB")
@Route("rating")
public class UsersRatingWindow extends AbstractWindow {
    public UsersRatingWindow() {
        UserService userService = new UserService();
        List<User> userList = userService.getAll();
        Grid<User> userGrid = new Grid<>();
        ListDataProvider<User> dataProvider = new ListDataProvider<>(userList);
        dataProvider.setSortOrder(User::getPoints, SortDirection.DESCENDING);

        userGrid.setDataProvider(dataProvider);

        Grid.Column<User> lastname = userGrid.addColumn(User::getLastname).setHeader("Фамилия");
        lastname.setSortable(true);
        lastname.setKey("lastname");
        Grid.Column<User> name = userGrid.addColumn(User::getName).setHeader("Имя");
        name.setSortable(true);
        name.setKey("name");
        Grid.Column<User> patronymic = userGrid.addColumn(User::getPatronymic).setHeader("Отчество");
        patronymic.setSortable(true);
        patronymic.setKey("patronymic");
        Grid.Column<User> points = userGrid.addColumn(User::getPoints).setHeader("Баллы");
        points.setSortable(true);
        points.setKey("points");
        Grid.Column<User> group = userGrid.addColumn(User::getGroup).setHeader("Группа");
        group.setSortable(true);
        group.setKey("group");

        HeaderRow filterRow = userGrid.appendHeaderRow();

        //lastname filter
        TextField lastnameField = new TextField();
        lastnameField.addValueChangeListener(event -> dataProvider.addFilter(user ->
                StringUtils.containsIgnoreCase(user.getLastname(), lastnameField.getValue())));
        lastnameField.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(lastname).setComponent(lastnameField);
        lastnameField.setSizeFull();
        lastnameField.setPlaceholder("Фамилия");

        //name filter
        TextField nameField = new TextField();
        nameField.addValueChangeListener(event -> dataProvider.addFilter(user ->
                StringUtils.containsIgnoreCase(user.getName(), nameField.getValue())));
        nameField.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(name).setComponent(nameField);
        nameField.setSizeFull();
        nameField.setPlaceholder("Имя");

        //patronymic filter
        TextField patronymicField = new TextField();
        patronymicField.addValueChangeListener(event -> dataProvider.addFilter(user ->
                StringUtils.containsIgnoreCase(user.getPatronymic(), patronymicField.getValue())));
        patronymicField.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(patronymic).setComponent(patronymicField);
        patronymicField.setSizeFull();
        patronymicField.setPlaceholder("Отчество");

        //group filter
        TextField groupField = new TextField();
        groupField.addValueChangeListener(event ->
                dataProvider.addFilter(user -> StringUtils.containsIgnoreCase(user.getGroup(), groupField.getValue())));
        groupField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(group).setComponent(groupField);
        groupField.setSizeFull();
        groupField.setPlaceholder("Группа");


        //points filter
        TextField pointsFiled = new TextField();
        pointsFiled.addValueChangeListener(event -> dataProvider.addFilter(user ->
                StringUtils.containsIgnoreCase(String.valueOf(user.getPoints()), pointsFiled.getValue())));
        pointsFiled.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(points).setComponent(pointsFiled);
        pointsFiled.setSizeFull();
        pointsFiled.setPlaceholder("Баллы");


        userGrid.addItemDoubleClickListener(user -> UI.getCurrent().navigate("user/" + user.getItem().getLogin()));

        //add
        if (AuthManager.getCurrentUser().getLogin() != null) {
            if (AuthManager.getCurrentUser().getRole().equals(Role.VERIFIED) || AuthManager.getCurrentUser().getRole().equals(Role.ADMIN)) {
                Anchor anchor = new Anchor(new StreamResource("stats.xls", Exporter.exportAsXls(userGrid)), "Экспортировать как документ Excel");
                setContent(anchor);
            }
        }
        add(userGrid);
    }
}
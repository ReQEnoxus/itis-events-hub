package ru.kpfu.itis.auth;

import com.vaadin.flow.server.VaadinSession;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.UserService;

public class AuthManager {
    private static User emptyUser = new User(null, null, null, 0, null, null, Role.GUEST);
    private static UserService userService = new UserService();

    public static User getCurrentUser() {
        if (VaadinSession.getCurrent().getAttribute("user") != null) {
            return (User) VaadinSession.getCurrent().getAttribute("user");
        } else {
            return emptyUser;
        }
    }

    public static void registerUser(User user) {
        userService.create(user);
    }

    public static void loginUser(String login, String password) {
        User requestedUser = userService.get(login);

        if (requestedUser == null) {
            throw new IllegalArgumentException("Invalid login");
        }

        if (requestedUser.getPassword().equals(password)) {
            VaadinSession.getCurrent().setAttribute("user", requestedUser);
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public static void logoutUser() {
        if (VaadinSession.getCurrent().getAttribute("user") != null) {
            VaadinSession.getCurrent().close();
        }
    }
}

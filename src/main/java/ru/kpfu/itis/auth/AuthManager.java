package ru.kpfu.itis.auth;

import com.vaadin.flow.server.VaadinSession;
import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;

public class AuthManager {
    private static User emptyUser = new User();
    private static UserDao userDao = null;

    static {
        emptyUser.setRole(Role.GUEST);
    }

    public static User getCurrentUser() {
        if (VaadinSession.getCurrent().getAttribute("user") != null) {
            return (User) VaadinSession.getCurrent().getAttribute("user");
        } else {
            return emptyUser;
        }
    }

    public static void registerUser(User user) {
        userDao.create(user);
    }

    public static void loginUser(String login, String password) {
        User requestedUser = userDao.get(login);

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

package ru.kpfu.itis.service.email;

import ru.kpfu.itis.entity.Event;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.UserService;

import java.util.List;

public class EmailModule {

    public static void notifyUsers(Event event) {
        UserService service = new UserService();
        List<User> list = service.getSubscribed();
        String message = "Создано новое мероприятие: " + event.getName();
        for (User user : list) {
            sendMessage(user.getEmail(), message);
        }
    }

    private static void sendMessage(String email, String message) {
        GMailSender sender = new GMailSender("itis.events.no.reply@gmail.com ", "noreply123");
        try {
            sender.sendMail("Новое мероприятие", message, "itis.events.no.reply@gmail.com ", email);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
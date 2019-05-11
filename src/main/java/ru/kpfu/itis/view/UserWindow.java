package ru.kpfu.itis.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.kpfu.itis.entity.Role;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.EventService;
import ru.kpfu.itis.service.UserService;

@Route("user")
public class UserWindow extends AbstractWindow implements HasUrlParameter<String> {
    private UserService userService = new UserService();
    private EventService eventService = new EventService();

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        User userToShow = userService.get(s);
        if (userToShow == null) {
            beforeEvent.rerouteTo("404");
            return;
        }
        UI.getCurrent().getPage().setTitle("Профиль пользователя " + s + " - ITIS Events HUB");
        tabs.setSelectedTab(tab4);
        HorizontalLayout pictureAndInfoLayout = new HorizontalLayout();
        pictureAndInfoLayout.setWidth("100%");
        pictureAndInfoLayout.setHeight("200px");
        Image avi = new Image();
        avi.setHeight("200px");
        avi.setWidth("200px");
        avi.setSrc("frontend/avatars/default.png");

        pictureAndInfoLayout.add(avi);


        avi.setAlt("Profile picture");
        VerticalLayout nameAndMisc = new VerticalLayout();
        Label name = new Label();
        name.setText(userToShow.getName() + " " + userToShow.getLastname() + " ");
        if (userToShow.getRole().equals(Role.VERIFIED) || userToShow.getRole().equals(Role.ADMIN)) {
            Icon icon = VaadinIcon.CHECK.create();
            icon.setColor("#282828");
            icon.setSize("10px");
            name.add(icon);
        }
        Label points = new Label();
        points.setText("Баллы пользователя: " + userToShow.getPoints() + "");

        Label accomplishedEventsNumber = new Label();
        accomplishedEventsNumber.setText("Завершено мероприятий: " + userToShow.getAccomplishedEvents().size());

        nameAndMisc.add(name, points, accomplishedEventsNumber);

        if (userToShow.getRole().equals(Role.ADMIN)) {
            Label admin = new Label();
            admin.setText("Администратор");
            admin.getStyle().set("color", "#282828");
            nameAndMisc.add(admin);
        }

        pictureAndInfoLayout.add(nameAndMisc);
        pictureAndInfoLayout.getStyle().set("overflow-y", "scroll");


        setContent(pictureAndInfoLayout);
        Label hr = new Label();
        hr.getElement().setProperty("innerHTML", "<hr>");
        hr.getStyle().set("width", "100%");
        setContent(hr);

        Label accomplished = new Label();
        accomplished.setText("Завершенные мероприятия: ");
        setContent(accomplished);

        for (int id : userToShow.getAccomplishedEvents()) {
            setContent(new Label(eventService.get(id).getName()));
        }

    }
    /*
    private void showUploader(String login) {
        Dialog dialog = new Dialog();
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        upload.setId("i18n-upload");

        upload.addSucceededListener(event -> {
            try {
                BufferedImage inputImage = ImageIO.read(buffer.getInputStream());
                File file = new File("resources://" + login + ".png");
                ImageIO.write(inputImage, "png", file);
                dialog.close();
                UI.getCurrent().getPage().reload();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        UploadI18N i18n = new UploadI18N();
        i18n.setDropFiles(
                new UploadI18N.DropFiles().setOne("Перетащите файл сюда...")
                        .setMany("Перетащите файлы сюда..."))
                .setAddFiles(new UploadI18N.AddFiles()
                        .setOne("Выбрать файл").setMany("Добавить файлы"))
                .setCancel("Отменить")
                .setError(new UploadI18N.Error()
                        .setTooManyFiles("Слишком много файлов.")
                        .setFileIsTooBig("Слишком большой файл.")
                        .setIncorrectFileType("Некорректный тип файла."))
                .setUploading(new UploadI18N.Uploading()
                        .setStatus(new UploadI18N.Uploading.Status()
                                .setConnecting("Соединение...")
                                .setStalled("Загрузка застопорилась.")
                                .setProcessing("Обработка файла..."))
                        .setRemainingTime(
                                new UploadI18N.Uploading.RemainingTime()
                                        .setPrefix("оставшееся время: ")
                                        .setUnknown(
                                                "оставшееся время неизвестно"))
                        .setError(new UploadI18N.Uploading.Error()
                                .setServerUnavailable("Сервер недоступен")
                                .setUnexpectedServerError(
                                        "Неожиданная ошибка сервера")
                                .setForbidden("Загрузка запрещена")))
                .setUnits(Stream
                        .of("Б", "Кбайт", "Мбайт", "Гбайт", "Тбайт", "Пбайт",
                                "Эбайт", "Збайт", "Ибайт")
                        .collect(Collectors.toList()));

        upload.setI18n(i18n);
        dialog.add(upload);
        dialog.setOpened(true);
    }
    */
}
package ru.kpfu.itis.service.api;

import com.vaadin.flow.server.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class APIServlet extends VaadinServlet {
    private final RequestHandler requestHandler = new EventAPIRequestHandler();

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        getService().addSessionInitListener(new SessionInitListener() {
            @Override
            public void sessionInit(SessionInitEvent event)
                    throws ServiceException {
                event.getSession().addRequestHandler(requestHandler);
            }
        });
    }
}

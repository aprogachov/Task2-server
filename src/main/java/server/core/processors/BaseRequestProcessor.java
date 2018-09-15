package server.core.processors;

import server.core.BeanSleeper;
import server.core.RequestProcessor;
import server.dto.Request;
import server.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Order(3)
public class BaseRequestProcessor implements RequestProcessor {

    private final BeanSleeper sleeper;

    @Autowired
    public BaseRequestProcessor(BeanSleeper sleeper) {
        this.sleeper = sleeper;
    }

    @Override
    public boolean accept(Request request) {
        return true;
    }

    @Override
    public String process(Request request) {
        String message = request.getMessage();
        String name = request.getName();

        sleeper.sleep(1000);

        System.out.println(String.format("From: %s, message: %s", name, message));
        return "Hello, " + name;
    }
}

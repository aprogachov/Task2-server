package server.core.processors;

import server.core.RequestProcessor;
import server.dto.Request;
import server.dto.Response;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class AnotherOneRequestProcessor implements RequestProcessor {

    @Override
    public String process(Request request) {
        return null;
    }

    @Override
    public boolean accept(Request request) {
        return false;
    }

}

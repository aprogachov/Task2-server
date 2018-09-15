package server.core.processors;

import server.core.RequestProcessor;
import server.dto.Request;
import server.dto.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@Profile("!moo")
public class MooRequestProcessor implements RequestProcessor {

    @Value("${server.moo.superpower}")
    private Boolean hasMooSuperpower;

    @Override
    public String process(Request request) {
        return "this server doesn't  have moo superpower";
    }

    @Override
    public boolean accept(Request request) {
        return !hasMooSuperpower && request != null && "moo".equals(request.getMessage());
    }
}

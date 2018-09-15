package server.core.processors;

import server.core.RequestProcessor;
import server.dto.Request;
import server.dto.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Profile("moo")
public class SuperMooRequestProcessor implements RequestProcessor {

    @Value("${server.moo.superpower}")
    private Boolean hasMooSuperpower;

    @Override
    public String process(Request request) {
        return "Yeah!\n" +
                "         (__) \n" +
                "         (oo) \n" +
                "   /------\\/ \n" +
                "  / |    ||   \n" +
                " *  /\\---/\\ \n" +
                "    ~~   ~~ ";
    }

    @Override
    public boolean accept(Request request) {
        return hasMooSuperpower && request != null && "moo".equals(request.getMessage()) ;
    }
}

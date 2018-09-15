package server;

import server.core.RootEndpoint;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
        context.registerShutdownHook();

        RootEndpoint endpoint = context.getBean(RootEndpoint.class);
        while (true) {
            endpoint.run();
        }
    }
}

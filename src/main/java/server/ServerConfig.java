package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import server.core.RequestProcessor;
import server.core.RootEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import javax.annotation.PostConstruct;
import javax.inject.Provider;
import java.util.List;

@Configuration
@ComponentScan("server")
@PropertySource(value = "classpath:server.properties")
public class ServerConfig {

    @Autowired
    private ConfigurableEnvironment environment;

    @Value("${server.thread.count}")
    private Integer threadCount;

    @Value("${spring.profiles.active}")
    private String profiles;

    @PostConstruct
    public void initProfiles() {
        environment.setActiveProfiles(profiles.split(","));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RootEndpoint rootEndpoint(
            ObjectMapper mapper,
            Provider<List<RequestProcessor>> processor) {
        return new RootEndpoint(
                environment.getProperty("server.port", Integer.class),
                threadCount,
                mapper,
                processor);
    }
}

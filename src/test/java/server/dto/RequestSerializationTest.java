package server.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class RequestSerializationTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public static void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        Request request = new Request("testName", "testMessage");
        String requestString = objectMapper.writeValueAsString(request);

        assertEquals("{\"name\":\"testName\",\"message\":\"testMessage\"}", requestString);
    }

    @Test
    public void testDeserialize() throws IOException {
        String requestString = "{\"name\":\"testName\",\"message\":\"testMessage\"}";
        Request request = objectMapper.readValue(requestString, Request.class);

        assertNotNull(request);
        assertEquals("testName", request.getName());
        assertEquals("testMessage", request.getMessage());
    }
}

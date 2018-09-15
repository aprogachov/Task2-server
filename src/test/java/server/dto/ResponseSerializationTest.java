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
public class ResponseSerializationTest {

    private static ObjectMapper objectMapper;

    @BeforeClass
    public static void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        Response request = new Response("testMessage");
        String responseString = objectMapper.writeValueAsString(request);

        assertEquals("{\"message\":\"testMessage\"}", responseString);
    }

    @Test
    public void testDeserialize() throws IOException {
        String responseString = "{\"message\":\"testMessage\"}";
        Response response = objectMapper.readValue(responseString, Response.class);

        assertNotNull(response);
        assertEquals("testMessage", response.getMessage());
    }
}

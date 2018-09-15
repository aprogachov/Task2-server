package server.core;

import server.core.processors.BaseRequestProcessor;
import server.dto.Request;
import server.dto.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestProcessorTest {

    @Mock
    private BeanSleeper sleeper;

    @InjectMocks
    private BaseRequestProcessor testee;

    @Before
    public void init() {
        sleeper.sleep(anyLong());
    }

    @Test
    public void process() {
        String response =
                testee.process(new Request("someName", "My message"));


        assertNotNull(response);
        assertEquals("Hello, someName", response);

        verify(sleeper, times(2))
                .sleep(anyLong());
    }
}

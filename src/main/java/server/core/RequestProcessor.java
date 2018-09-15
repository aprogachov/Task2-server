package server.core;

import server.dto.Request;
import server.dto.Response;

public interface RequestProcessor {
    String process(Request request);

    boolean accept(Request request);
}

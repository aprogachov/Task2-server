package server.core;

import server.dto.Request;
import server.dto.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RootEndpoint implements Runnable {

    private final int port;
    private final int threadCount;
    private final ObjectMapper objectMapper;
    private final Provider<List<RequestProcessor>> requestProcessorProvider;
    private ServerSocket serverSocket;
    private ExecutorService executorService;

    public RootEndpoint(
            int port,
            int threadCount,
            ObjectMapper objectMapper,
            Provider<List<RequestProcessor>> requestProcessorProvider) {
        this.port = port;
        this.threadCount = threadCount;
        this.objectMapper = objectMapper;
        this.requestProcessorProvider = requestProcessorProvider;
    }

    @PostConstruct
    public void init() throws IOException {
        serverSocket = new ServerSocket(port);
        executorService = Executors.newFixedThreadPool(threadCount);   //создать пул с threadCount потоками

        System.out.println("Waiting for a client...");
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdownNow();
    }

    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();
            accept(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void accept(Socket socket) {
        executorService.submit(() -> {
            try {
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();

                DataInputStream reader = new DataInputStream(is);
                DataOutputStream writer = new DataOutputStream(os);

                String requestString = reader.readUTF();   // ожидаем пока клиент пришлет текст
                Request request = objectMapper.readValue(requestString, Request.class);
    //            Thread.sleep(1000);

                List<RequestProcessor> processors = requestProcessorProvider.get();

                String response = null;

                for (RequestProcessor processor : processors) {
                    if (processor.accept(request)) {
                        response = processor.process(request);
                        break;
                    }
                }

                String responseString = objectMapper.writeValueAsString(response);

                writer.writeUTF(responseString);   // отсылаем клиенту обратно
                writer.flush();

                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}

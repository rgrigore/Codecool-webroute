package com.codecool.webroute;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {

    private static final Routes ROUTES = new Routes();
    private static final Map<String, Method> getRoutes = new HashMap<>();
    private static final Map<String, Method> postRoutes = new HashMap<>();

    public static void main(String[] args) throws Exception {
        mapRoutes();

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    private static void mapRoutes() {
        for (Method method : Routes.class.getMethods()) {
            if (method.isAnnotationPresent(WebRoute.class)) {
                WebRoute webRoute = method.getAnnotation(WebRoute.class);
                switch (webRoute.method()) {
                    case GET: getRoutes.put(webRoute.route(), method); break;
                    case POST: postRoutes.put(webRoute.route(), method); break;
                    default: System.out.printf("Unknown method <%s>%n", webRoute.method());
                }
            }
        }
    }

    private static Map<String, Method> selectRoutes(String requestMethod) {
        if (WebRoute.Methods.GET.equals(requestMethod)) {
            return getRoutes;
        } else if (WebRoute.Methods.POST.equals(requestMethod)) {
            return postRoutes;
        }

        return null;
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) {

            Optional<Map<String, Method>> routes = Optional.ofNullable(selectRoutes(t.getRequestMethod()));

            routes.ifPresent(map -> {
                try {
                    String response = (String) map.get(t.getRequestURI().toString()).invoke(ROUTES);

                    t.sendResponseHeaders(200, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } catch (IllegalAccessException | InvocationTargetException | IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

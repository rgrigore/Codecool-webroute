package com.codecool.webroute;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class Routes {
    @WebRoute(route = "/")
    public void indexGet(HttpExchange t) throws IOException {

            String response = "This is the home page";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
    }

    @WebRoute(route = "/", method = WebRoute.Methods.SET)
    public void indexSet() {

    }

    @WebRoute(route = "/abc")
    public void abcGet(HttpExchange t) throws IOException {

        String response = "This is the abc page";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

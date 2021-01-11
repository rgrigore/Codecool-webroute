package com.codecool.webroute;

public class Routes {
    @WebRoute(route = "/")
    public String indexGet() {
        return "This is the home page";
    }

    @WebRoute(route = "/", method = WebRoute.Methods.POST)
    public String indexSet() {
        return "";
    }

    @WebRoute(route = "/abc")
    public String abcGet() {
        return "This is the abc page";
    }
}

package com.example.entity;

import randy.framework.annotation.Controller;
import randy.framework.annotation.UrlMapping;

@Controller
public class Test {
    @UrlMapping("/")
    public String accueil() {
        return "Résultat de la méthode GET pour /";
    }

    @UrlMapping(value = "/test/get", method = {"GET"})
    public String test() {
        return "Résultat de la méthode GET pour /test/get";
    }
    
    @UrlMapping(value = "/test/post", method = {"POST"})
    public String testPost() {
        return "Résultat de la méthode POST pour /test/post";
    }
}

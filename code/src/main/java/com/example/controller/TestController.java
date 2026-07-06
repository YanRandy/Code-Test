package com.example.controller;

import randy.framework.annotation.Controller;
import randy.framework.annotation.UrlMapping;
import randy.framework.model.ModelAndView;

@Controller
public class TestController {

    @UrlMapping(value = "/test", method = "GET")
    public ModelAndView afficherFormulaire() {
        ModelAndView mv = new ModelAndView();
        mv.setView("test");
        // String[] listMessage = {"Bonjour", "Salut", "Hello", "Hi"};
        mv.setAttribute("message", "Bonjour, je suis Randy, le createur de Randy Framework");
        return mv;
    }

    @UrlMapping(value = "/test", method = "POST")
    public String soumettreFormulaire() {
        System.out.println(" -> [CONTROLLER] Exécution de la méthode POST pour /test");
        return "/test POST";
    }
}
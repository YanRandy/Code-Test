package com.example.controller;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.example.entity.Livre;
import com.example.service.LivreService;

import randy.framework.annotation.Controller;
import randy.framework.annotation.UrlMapping;
import randy.framework.model.ModelAndView;

@Controller
public class TestController {

    // @UrlMapping(value = "/test", method = "GET")
    // public ModelAndView afficherFormulaire() {
    // ModelAndView mv = new ModelAndView();
    // mv.setView("test");
    // String[] listMessage = { "Bonjour", "Salut", "Hello", "Hi" };
    // mv.setAttribute("message", "Bonjour");
    // mv.setAttribute("message", listMessage);
    // return mv;
    // }

    @UrlMapping(value = "/test", method = "POST")
    public String soumettreFormulaire() {
        System.out.println(" -> [CONTROLLER] Exécution de la méthode POST pour /test");
        return "/test POST";
    }

    @UrlMapping(value = "/test", method = "GET")
    public ModelAndView afficherMessages(ApplicationContext ctx) {
        LivreService service = ctx.getBean(LivreService.class);
        List<Livre> livres = service.getTousLesLivres();

        ModelAndView mv = new ModelAndView();
        mv.setView("test");
        mv.setAttribute("livres", livres.toArray());
        return mv;
    }

    @UrlMapping(value = "/accueil", method = "GET")
    public String accueil() {
        return "accueil"; // → cherche /WEB-INF/views/accueil.jsp (ou .html)
    }
}
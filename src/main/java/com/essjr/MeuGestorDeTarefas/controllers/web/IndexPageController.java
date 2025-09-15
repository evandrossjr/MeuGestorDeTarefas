package com.essjr.MeuGestorDeTarefas.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexPageController {
    @GetMapping("/") // 2. Mapeia a URL raiz do site (ex: http://localhost:8080/)
    public String home() {
        return "index"; // 3. Retorna o NOME do arquivo HTML (sem o .html) que está em /templates
    }

}

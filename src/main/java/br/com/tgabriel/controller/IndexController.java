package br.com.tgabriel.controller;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class IndexController implements Serializable {

    private static final long serialVersionUID = -784519597996507487L;

    public String redirectUsusario() {
        return "/usuario/list.xhtml";
    }

    public String redirectMeme() {
        return "/meme/list.xhtml";
    }

    public String redirectCategoriaMeme() {
        return "/memescate/list.xhtml";
    }
}
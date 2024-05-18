package br.com.tgabriel.controller;

import br.com.tgabriel.domain.Meme;
import br.com.tgabriel.service.IMemeService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@ViewScoped
public class MemeController implements Serializable {

    private static final long serialVersionUID = 367088063926303823L;

    private Meme meme;

    private Collection<Meme> memes;

    @Inject
    private IMemeService memeService;

    private Boolean isUpdate;

    @PostConstruct
    public void init() {
        try {
            this.isUpdate = false;
            this.meme = new Meme();
            this.memes = memeService.buscarTodos();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar listar os memes"));
        }
    }

    public void cancel() {
        try {
            this.isUpdate = false;
            this.meme = new Meme();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cancelar ação"));
        }

    }

    public void edit(Meme meme) {
        try {
            this.isUpdate = true;
            this.meme = meme;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar editar o meme"));
        }

    }

    public void delete(Meme meme) {
        try {
            memeService.excluir(meme);
            memes.remove(meme);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar excluir o meme"));
        }

    }

    public void add() {
        try {
            memeService.cadastrar(meme);
            this.memes = memeService.buscarTodos();
            this.meme = new Meme();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar criar o meme"));
        }


    }

    public void update() {
        try {
            memeService.alterar(this.meme);
            cancel();
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Meme Atualiado com sucesso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar atualizar o meme"));
        }

    }

    public String voltarTelaInicial() {
        return "/index.xhtml";
    }

    public Meme getMeme() {
        return meme;
    }

    public void setMeme(Meme meme) {
        this.meme = meme;
    }

    public Collection<Meme> getMemes() {
        return memes;
    }

    public void setMemes(Collection<Meme> memes) {
        this.memes = memes;
    }

    public IMemeService getMemeService() {
        return memeService;
    }

    public void setMemeService(IMemeService memeService) {
        this.memeService = memeService;
    }

    public Boolean getUpdate() {
        return isUpdate;
    }

    public void setUpdate(Boolean update) {
        isUpdate = update;
    }
}


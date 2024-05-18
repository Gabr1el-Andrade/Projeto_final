package br.com.tgabriel.controller;


import br.com.tgabriel.domain.Usuario;
import br.com.tgabriel.service.IUsuarioService;
import br.com.tgabriel.utils.ReplaceUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.view.ViewScoped;

@Named
@ViewScoped
public class UsuarioController implements Serializable {

    private static final long serialVersionUID = 8030245985235567808L;

    private Usuario usuario;

    private Collection<Usuario>usuarios;

    @Inject
    private IUsuarioService usuarioService;

    private Boolean isUpdate;

    private String dataMask;

    @PostConstruct
    public void init() {
        try {
            this.isUpdate = false;
            this.usuario = new Usuario();
            this.usuarios = usuarioService.buscarTodos();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar listar os usuarios"));
        }
    }

    public void cancel() {
        try {
            this.isUpdate = false;
            this.usuario = new Usuario();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar cancelar ação"));
        }

    }

    public void edit(Usuario usuario) {
        try {
            this.isUpdate = true;
            this.usuario = usuario;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar excluir o usuario"));
        }

    }

    public void delete(Usuario usuario) {
        try {
            usuarioService.excluir(usuario);
            usuarios.remove(usuario);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar excluir o usuario"));
        }

    }

    public void add() {
        try {
            removerCaracteresInvalidos();
            limparCampos();
            usuarioService.cadastrar(usuario);
            this.usuarios = usuarioService.buscarTodos();
            this.usuario = new Usuario();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar criar o Usuario"));
        }


    }

    private void removerCaracteresInvalidos() {
        Long data = Long.valueOf(ReplaceUtils.replace((getDataMask()), "/", "/"));
        this.usuario.setId(data);

    }

    private void limparCampos() {
        setDataMask(null);
    }

    public void update() {
        try {
            removerCaracteresInvalidos();
            usuarioService.alterar(this.usuario);
            cancel();
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Usuario Atualiado com sucesso"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar atualizar o Usuario"));
        }

    }

    public String voltarTelaInicial() {
        return "/index.xhtml";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Collection<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public IUsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Boolean getUpdate() {
        return isUpdate;
    }

    public void setUpdate(Boolean update) {
        isUpdate = update;
    }

    public String getDataMask() {
        return dataMask;
    }

    public void setDataMask(String dataMask) {
        this.dataMask = dataMask;
    }
}


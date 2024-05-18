package br.com.tgabriel.service;

import br.com.tgabriel.dao.IUsuarioDAO;
import br.com.tgabriel.domain.Usuario;
import br.com.tgabriel.exceptions.DAOException;
import br.com.tgabriel.exceptions.MaisUmMeme;
import br.com.tgabriel.exceptions.TabelaExeption;
import jakarta.inject.Inject;

import java.util.List;

public class UsuarioService extends GenericService<Usuario, Long> implements IUsuarioService{
    private IUsuarioDAO iUsuarioDAO;

    @Inject
    public UsuarioService(IUsuarioDAO iUsuarioDAO) {
        super(iUsuarioDAO);
        this.iUsuarioDAO = iUsuarioDAO;
    }
    @Override
    public Usuario buscarPorID(Long id) throws DAOException {
        try {
            return this.dao.consultar(id);
        } catch (MaisUmMeme | TabelaExeption e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Usuario> filtrarUsuarios(String query) {
        return iUsuarioDAO.filtrarUsuarios(query);
    }

}

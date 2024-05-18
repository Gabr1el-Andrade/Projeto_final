package br.com.tgabriel.service;

import br.com.tgabriel.dao.ICategoriaMemeDAO;
import br.com.tgabriel.domain.CategoriaMeme;
import br.com.tgabriel.exceptions.DAOException;
import br.com.tgabriel.exceptions.TipoChaveNaoEncontradaException;
import jakarta.inject.Inject;

public class CategoriaMemeService extends GenericService<CategoriaMeme, Long> implements ICategoriaMemeService {

    ICategoriaMemeDAO dao;

    @Inject
    public CategoriaMemeService(ICategoriaMemeDAO dao) {
        super(dao);
        this.dao = dao;}

    @Override
    public void finalizarInteracao(CategoriaMeme categoriaMeme) throws TipoChaveNaoEncontradaException, DAOException {
        categoriaMeme.setStatus(CategoriaMeme.Status.PUBLICADO);
        dao.finalizarInteracao(categoriaMeme);
    }

    @Override
    public void cancelarInteracao(CategoriaMeme categoriaMeme) throws TipoChaveNaoEncontradaException, DAOException {
        categoriaMeme.setStatus(CategoriaMeme.Status.CANCELADO);
        dao.cancelarInteracao(categoriaMeme);
    }

    @Override
    public CategoriaMeme consultarComCollection(Long id) {
        return dao.consultarComCollection(id);
    }

    public CategoriaMeme cadastrar(CategoriaMeme entity) throws TipoChaveNaoEncontradaException, DAOException {
        entity.setStatus(CategoriaMeme.Status.INICIADO);
        return super.cadastrar(entity);
    }

}

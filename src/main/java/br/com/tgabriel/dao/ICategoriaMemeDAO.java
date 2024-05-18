package br.com.tgabriel.dao;

import br.com.tgabriel.dao.Generic.IGenericDAO;
import br.com.tgabriel.domain.CategoriaMeme;
import br.com.tgabriel.exceptions.DAOException;
import br.com.tgabriel.exceptions.TipoChaveNaoEncontradaException;

public interface ICategoriaMemeDAO extends IGenericDAO<CategoriaMeme, Long> {

    public void finalizarInteracao(CategoriaMeme memecat) throws TipoChaveNaoEncontradaException, DAOException;

    public void cancelarInteracao(CategoriaMeme memecat) throws TipoChaveNaoEncontradaException, DAOException;

    public CategoriaMeme consultarComCollection(Long id);

}

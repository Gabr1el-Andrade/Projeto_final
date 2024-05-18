package br.com.tgabriel.service;

import br.com.tgabriel.dao.Generic.IGenericDAO;
import br.com.tgabriel.domain.CategoriaMeme;
import br.com.tgabriel.exceptions.DAOException;
import br.com.tgabriel.exceptions.TipoChaveNaoEncontradaException;

public interface ICategoriaMemeService extends IGenericDAO<CategoriaMeme, Long> {

        void finalizarInteracao(CategoriaMeme categoriaMeme) throws TipoChaveNaoEncontradaException, DAOException;

        void cancelarInteracao(CategoriaMeme categoriaMeme) throws TipoChaveNaoEncontradaException, DAOException;


public CategoriaMeme consultarComCollection(Long id);
}

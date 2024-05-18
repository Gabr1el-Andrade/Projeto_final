package br.com.tgabriel.dao.Generic;


import br.com.tgabriel.domain.Persistente;
import br.com.tgabriel.exceptions.DAOException;
import br.com.tgabriel.exceptions.MaisUmMeme;
import br.com.tgabriel.exceptions.TabelaExeption;
import br.com.tgabriel.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericDAO <T extends Persistente, E extends Serializable> {


    public T cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException;


    public void excluir(T entity) throws DAOException;


    public T alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException;


    public T consultar(E id) throws MaisUmMeme, TabelaExeption, DAOException;



    public Collection<T> buscarTodos() throws DAOException;
}


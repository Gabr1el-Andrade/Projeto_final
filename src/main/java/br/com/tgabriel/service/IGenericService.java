package br.com.tgabriel.service;

import br.com.tgabriel.domain.Persistente;
import br.com.tgabriel.exceptions.DAOException;
import br.com.tgabriel.exceptions.MaisUmMeme;
import br.com.tgabriel.exceptions.TabelaExeption;
import br.com.tgabriel.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericService <T extends Persistente, E extends Serializable> {
    /**
     * Método para cadastrar novos registro no banco de dados
     *
     * @param entity a ser cadastrado
     * @return retorna o objeto salvo
     * @throws TipoChaveNaoEncontradaException
     * @throws DAOException
     */

    public T cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException;
    /**
     * Método para excluir um registro do banco de dados
     *
     * @param entity a ser cadastrado
     * @throws DAOException
     */
    public void excluir(T entity) throws DAOException;
    /**
     *Método para alterar um registro no bando de dados.
     *
     * @param entity a ser atualizado
     * @return retorna o objeto salvo
     * @throws TipoChaveNaoEncontradaException
     * @throws DAOException
     */
    public T alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException;
    /**
     * Método para consultar um registro no banco de dados
     *
     * @param valor chave única do dado a ser consultado
     * @return
     * @throws MaisUmMeme
     * @throws TabelaExeption
     */
    public T consultar(E valor) throws MaisUmMeme, TabelaExeption, DAOException;
    /**
     * Método que irá retornar todos os registros do banco de dados de uma determinado dado ou tabela
     *
     * @return Registros encontrados
     * @throws DAOException
     */
    public Collection<T> buscarTodos() throws DAOException;
}

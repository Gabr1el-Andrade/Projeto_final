package br.com.tgabriel.service;

import br.com.tgabriel.domain.Usuario;
import br.com.tgabriel.exceptions.DAOException;

import java.util.List;

public interface IUsuarioService extends IGenericService<Usuario, Long> {

    Usuario buscarPorID(Long id) throws DAOException;

    List<Usuario> filtrarUsuarios(String query);
}

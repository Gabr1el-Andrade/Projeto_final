package br.com.tgabriel.dao;

import br.com.tgabriel.dao.Generic.IGenericDAO;
import br.com.tgabriel.domain.Usuario;

import java.util.List;

public interface IUsuarioDAO extends IGenericDAO<Usuario, Long> {

        List<Usuario> filtrarUsuarios(String query);
}

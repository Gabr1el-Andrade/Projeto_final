package br.com.tgabriel.dao;

import br.com.tgabriel.dao.Generic.GenericDAO;
import br.com.tgabriel.domain.Usuario;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UsuarioDAO extends GenericDAO<Usuario, Long> implements IUsuarioDAO {

public UsuarioDAO() {
        super(Usuario.class);
        }

@Override
public List<Usuario> filtrarUsuarios(String query) {
        TypedQuery<Usuario> tpQuery =
        this.entityManager.createNamedQuery("Usuario.findByNome", this.persistenteClass);
        tpQuery.setParameter("nome", "%" + query + "%");
        return tpQuery.getResultList();

        }
        }

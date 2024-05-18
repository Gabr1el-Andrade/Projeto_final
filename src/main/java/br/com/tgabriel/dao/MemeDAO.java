package br.com.tgabriel.dao;


import br.com.tgabriel.dao.Generic.GenericDAO;
import br.com.tgabriel.domain.Meme;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MemeDAO extends GenericDAO<Meme, String> implements IMemeDAO {

    public MemeDAO() {
        super(Meme.class);

    }

    @Override
    public List<Meme> filtrarMemes(String query) {

        TypedQuery<Meme> tpQuery =
                this.entityManager.createNamedQuery("Meme.findByNome", this.persistenteClass);
        tpQuery.setParameter("nome", "%" + query + "%");
        return tpQuery.getResultList();

    }
}

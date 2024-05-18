package br.com.tgabriel.dao;


import br.com.tgabriel.dao.Generic.GenericDAO;
import br.com.tgabriel.domain.CategoriaMeme;
import br.com.tgabriel.domain.Meme;
import br.com.tgabriel.domain.Usuario;
import br.com.tgabriel.exceptions.DAOException;
import br.com.tgabriel.exceptions.TipoChaveNaoEncontradaException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class CategoriaMemeDAO extends GenericDAO<CategoriaMeme, Long> implements ICategoriaMemeDAO {

    public CategoriaMemeDAO() {
        super(CategoriaMeme.class);
    }

    @Override
    public void finalizarInteracao(CategoriaMeme memecat) throws TipoChaveNaoEncontradaException, DAOException {
        super.alterar(memecat);
    }

    @Override
    public void cancelarInteracao(CategoriaMeme memecat) throws TipoChaveNaoEncontradaException, DAOException {
        super.alterar(memecat);
    }

    public void excluir(CategoriaMeme entity) throws DAOException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    public CategoriaMeme cadastrar(CategoriaMeme entity) throws TipoChaveNaoEncontradaException, DAOException {
        try {
            entity.getMemeCheckers().forEach(mms -> {
                Meme memeJpa = entityManager.merge(mms.getMeme());
                mms.setMeme(memeJpa);
            });
            Usuario usuario = entityManager.merge(entity.getUsuario());
            entity.setUsuario(usuario);
            entityManager.persist(entity);
//			entityManager.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            throw new DAOException("ERRO AO CONCLUIR A PUBLICAÇÂO ", e);
        }

    }
    @Override
    public CategoriaMeme consultarComCollection(Long id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CategoriaMeme> query = builder.createQuery(CategoriaMeme.class);
        Root<CategoriaMeme> root = query.from(CategoriaMeme.class);
        root.fetch("usuario");
        root.fetch("memes");
        query.select(root).where(builder.equal(root.get("id"), id));
        TypedQuery<CategoriaMeme> tpQuery =
                entityManager.createQuery(query);
        CategoriaMeme categoriaMeme = tpQuery.getSingleResult();
        return categoriaMeme;
    }
}


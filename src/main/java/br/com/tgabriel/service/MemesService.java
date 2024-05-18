package br.com.tgabriel.service;

import br.com.tgabriel.dao.IMemeDAO;
import br.com.tgabriel.domain.Meme;
import jakarta.inject.Inject;

import java.util.List;

public class MemesService extends GenericService<Meme, String> implements IMemeService {

    private IMemeDAO memeDAO;

    @Inject
    public MemesService(IMemeDAO memeDAO) {
        super(memeDAO);
        this.memeDAO = memeDAO;
    }

    @Override
    public List<Meme> filtrarMemes(String query) {
        return memeDAO.filtrarMemes(query);
    }

}

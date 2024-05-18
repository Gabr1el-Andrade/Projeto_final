package br.com.tgabriel.dao;

import br.com.tgabriel.dao.Generic.IGenericDAO;
import br.com.tgabriel.domain.Meme;

import java.util.List;

public interface IMemeDAO extends IGenericDAO<Meme, String> {

    List<Meme> filtrarMemes(String query);

}

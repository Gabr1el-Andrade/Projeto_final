package br.com.tgabriel.service;

import br.com.tgabriel.domain.Meme;

import java.util.List;

public interface IMemeService extends IGenericService<Meme, String> {

    List<Meme> filtrarMemes(String query);


}

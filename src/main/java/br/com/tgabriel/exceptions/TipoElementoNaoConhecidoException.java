package br.com.tgabriel.exceptions;

public class TipoElementoNaoConhecidoException extends Exception {


    private static final long serialVersionUID = -2268140970978666251L;

    public TipoElementoNaoConhecidoException(String msg, Throwable e) {
        super(msg, e);
    }

}

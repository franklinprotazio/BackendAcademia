package com.example.academia.core.exception;

public class LimiteTurmasExcedidoException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LimiteTurmasExcedidoException(String mensagem) {
        super(mensagem);
    }
}

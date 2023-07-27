package com.example.academia.core.exception;

public class EntidadeNaoEncontradaException extends RuntimeException  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5562328648351241726L;

	/**
	 * Construtor com a mensagem
	 * 
	 * @param menssagem
	 */

	public EntidadeNaoEncontradaException(String menssagem) {
		super(menssagem);
	}

	/**
	 * Construtor com a causa
	 * 
	 * @param causa
	 */
	public EntidadeNaoEncontradaException(Exception causa) {
		super(causa);
	}

	/**
	 * Construtor com a mensagem e a causa da excecao
	 * 
	 * @par public EntidadeNaoEncontradaException(Exception causa) { super(causa); }
	 * 
	 *      /** Construtor com a mensagem e a causa da excecao
	 * @param menssagem
	 * @param causa
	 */
	public EntidadeNaoEncontradaException(String menssagem, Exception causa) {
		super(menssagem, causa);
	}
	
}

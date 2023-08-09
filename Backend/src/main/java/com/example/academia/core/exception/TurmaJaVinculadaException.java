package com.example.academia.core.exception;

public class TurmaJaVinculadaException extends Exception {
	
	private static final long serialVersionUID = -5562328648351241726L;

	/**
	 * Construtor com a mensagem
	 * 
	 * @param menssagem
	 */

	public TurmaJaVinculadaException(String menssagem) {
		super(menssagem);
	}

	/**
	 * Construtor com a causa
	 * 
	 * @param causa
	 */
	public TurmaJaVinculadaException(Exception causa) {
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
	public TurmaJaVinculadaException(String menssagem, Exception causa) {
		super(menssagem, causa);
	}

}

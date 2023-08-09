package com.example.academia.core.enums;

public enum StatusTurmaeNUM {

	INATIVA(0, "TURMA INATIVA"),
	ATIVA(1, "TURMA ATIVA"),
	INDISPONIVEL(2, "TURMA INDISPONIVEL");

	private Integer codigo;
	private String descricao;

	private StatusTurmaeNUM(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public static String retornarDescricao(Integer codigo) {
		String descricao = null;
		for (StatusTurmaeNUM item : StatusTurmaeNUM.values()) {
			if (item.codigo.equals(codigo)) {
				descricao = item.descricao;
			}
		}

		return descricao;
	}

}

package com.zeta.util;

/**
 * Enum com todas as Unidades da Federação do Brasil. Contém o nome da Unidade, a sigla e a capital
 * da Unidade da Federação. As unidades estão listadas por ordem alfabética no enum.
 *
 * @author Ricardo Giaviti
 * @version 1.1.1
 * @since 1.0.0
 */
public enum UnidadeFederacao {

  AMAZONAS("Amazonas", "AM", "13"),
  ALAGOAS("Alagoas", "AL", "27"),
  ACRE("Acre", "AC", "12"),
  AMAPA("Amapá", "AP", "16"),
  BAHIA("Bahia", "BA", "29"),
  PARA("Pará", "PA", "15"),
  MATO_GROSSO("Mato Grosso", "MT", "51"),
  MINAS_GERAIS("Minas Gerais", "MG", "31"),
  MATO_GROSSO_DO_SUL("Mato Grosso do Sul", "MS", "50"),
  GOIAS("Goiás", "GO", "52"),
  MARANHAO("Maranhão", "MA", "21"),
  RIO_GRANDE_DO_SUL("Rio Grande do Sul", "RS", "43"),
  TOCANTINS("Tocantins", "TO", "17"),
  PIAUI("Piauí", "PI", "22"),
  SAO_PAULO("São Paulo", "SP", "35"),
  RONDONIA("Rondônia", "RO", "11"),
  RORAIMA("Roraima", "RR", "14"),
  PARANA("Paraná", "PR", "41"),
  CEARA("Ceará", "CE", "23"),
  PERNAMBUCO("Pernambuco", "PE", "26"),
  SANTA_CATARINA("Santa Catarina", "SC", "42"),
  PARAIBA("Paraíba", "PB", "25"),
  RIO_GRANDE_DO_NORTE("Rio Grande do Norte", "RN", "24"),
  ESPIRITO_SANTO("Espírito Santo", "ES", "32"),
  RIO_DE_JANEIRO("Rio de Janeiro", "RJ", "33"),
  SERGIPE("Sergipe", "SE", "28"),
  DISTRITO_FEDERAL("Distrito Federal", "DF", "53");

  private final String nome;
  private final String sigla;
  private final String codigo;

  /**
   * Construtor do enum
   *
   * @param nome    nome da unidade da federação completo
   * @param sigla   sigla da unidade da federação
   * @param capital nome da capital da unidade da federação
   */
  UnidadeFederacao(final String nome, final String sigla, final String codigo) {
    this.nome = nome;
    this.sigla = sigla;
    this.codigo = codigo;
  }

  /**
   * Converte a partir do nome da Unidade da Federacao, para o respectivo enum.
   *
   * @param nomeUf o nome da Unidade da Federação. Exemplo: "São Paulo"
   * @return o enum da Unidade da Federação
   * @throws IllegalArgumentException caso não ache o enum pelo nome da UF
   */
  public static UnidadeFederacao fromUF(final String nomeUf) {
    for (final UnidadeFederacao uf : UnidadeFederacao.values()) {
      if (uf.nome.equalsIgnoreCase(nomeUf)) {
        return uf;
      }
    }

    throw new IllegalArgumentException(nomeUf);
  }

  /**
   * Converte a partir da Sigla da UF no parâmetro, para o enum da Unidade da Federação.
   *
   * @param sigla da Unidade da Federação. Exemplo: "MG"
   * @return a Unidade da Federação
   * @throws IllegalArgumentException caso a sigla da UF não exista
   */
  public static UnidadeFederacao fromSigla(final String sigla) {
    for (final UnidadeFederacao uf : UnidadeFederacao.values()) {
      if (uf.sigla.equalsIgnoreCase(sigla)) {
        return uf;
      }
    }

    throw new IllegalArgumentException(sigla);
  }

  /**
   * Converte, a partir do nome da capital da UF, para o Enum.
   *
   * @param capital da Unidade da Federação. Exemplo: "Porto Alegre"
   * @return a Unidade da Federacao com a capital passada no parâmetro
   * @throws IllegalArgumentException caso o nome da capital não exista
   */
  public static UnidadeFederacao fromCodigo(final String codigo) {
    for (final UnidadeFederacao uf : UnidadeFederacao.values()) {
      if (uf.codigo.equalsIgnoreCase(codigo)) {
        return uf;
      }
    }

    throw new IllegalArgumentException(codigo);
  }

  /**
   * Obtém a sigla da UF
   *
   * @return a sigla da UF
   */
  public String sigla() {
    return this.sigla;
  }

  /**
   * Nome completo da UF
   *
   * @return nome completo da UF
   */
  public String nome() {
    return this.nome;
  }

  /**
   * Nome da capital da UF
   *
   * @return nome da capital da UF
   */
  public String capital() {
    return this.codigo;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UnidadeFederacao{");
    sb.append("nome='").append(nome).append('\'');
    sb.append(", sigla='").append(sigla).append('\'');
    sb.append(", codigo='").append(codigo).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
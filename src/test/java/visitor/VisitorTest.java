package visitor;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VisitorTest {
    private final Livro livro = new Livro("Dom Casmurro", "Machado de Assis", new BigDecimal("39.90"));
    private final Revista revista = new Revista("Ciencia Hoje", 42, new BigDecimal("12.50"));

    @Test
    void deveSelecionarMetodoDeLivroMesmoComReferenciaDePublicacao() {
        Publicacao publicacao = livro;

        assertEquals("Livro: Dom Casmurro | Autor: Machado de Assis",
                publicacao.aceitar(new VisitanteCatalogo()));
    }

    @Test
    void deveSelecionarMetodoDeRevistaMesmoComReferenciaDePublicacao() {
        Publicacao publicacao = revista;

        assertEquals("Revista: Ciencia Hoje | Edicao: 42",
                publicacao.aceitar(new VisitanteCatalogo()));
    }

    @Test
    void deveConsultarPrecoDeCadaTipoDePublicacao() {
        VisitantePreco visitante = new VisitantePreco();

        assertEquals(new BigDecimal("39.90"), livro.aceitar(visitante));
        assertEquals(new BigDecimal("12.50"), revista.aceitar(visitante));
    }

    @Test
    void deveAplicarVisitantesDiferentesAoMesmoAcervo() {
        Acervo acervo = new Acervo();
        acervo.adicionar(livro);
        acervo.adicionar(revista);

        BigDecimal total = acervo.aceitar(new VisitantePreco()).stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals(new BigDecimal("52.40"), total);
        assertEquals(Arrays.asList(
                "Livro: Dom Casmurro | Autor: Machado de Assis",
                "Revista: Ciencia Hoje | Edicao: 42"),
                acervo.aceitar(new VisitanteCatalogo()));
    }

    @Test
    void devePermitirNovaOperacaoSemModificarPublicacoes() {
        VisitantePublicacao<String> visitante = new VisitantePublicacao<String>() {
            @Override
            public String visitar(Livro livro) {
                return livro.getAutor();
            }

            @Override
            public String visitar(Revista revista) {
                return "Edicao " + revista.getEdicao();
            }
        };

        assertEquals("Machado de Assis", livro.aceitar(visitante));
        assertEquals("Edicao 42", revista.aceitar(visitante));
    }

    @Test
    void deveRetornarListaVaziaParaAcervoVazio() {
        Acervo acervo = new Acervo();

        assertEquals(Collections.emptyList(), acervo.aceitar(new VisitanteCatalogo()));
        assertEquals(BigDecimal.ZERO, acervo.aceitar(new VisitantePreco()).stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    @Test
    void devePermitirReutilizarVisitanteSemAcumularResultados() {
        Acervo acervo = new Acervo();
        acervo.adicionar(livro);
        VisitantePreco visitante = new VisitantePreco();

        assertEquals(Collections.singletonList(new BigDecimal("39.90")), acervo.aceitar(visitante));
        assertEquals(Collections.singletonList(new BigDecimal("39.90")), acervo.aceitar(visitante));
    }

    @Test
    void deveRejeitarPrecoNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Livro("Livro", "Autor", new BigDecimal("-0.01")));
        assertThrows(IllegalArgumentException.class,
                () -> new Revista("Revista", 1, new BigDecimal("-0.01")));
    }

    @Test
    void deveAceitarPublicacoesGratuitas() {
        VisitantePreco visitante = new VisitantePreco();

        assertEquals(BigDecimal.ZERO, new Livro("Livro", "Autor", BigDecimal.ZERO).aceitar(visitante));
        assertEquals(BigDecimal.ZERO, new Revista("Revista", 1, BigDecimal.ZERO).aceitar(visitante));
    }

    @Test
    void deveRejeitarEdicaoNaoPositiva() {
        assertThrows(IllegalArgumentException.class, () -> new Revista("Revista", 0, BigDecimal.ONE));
        assertThrows(IllegalArgumentException.class, () -> new Revista("Revista", -1, BigDecimal.ONE));
    }

    @Test
    void deveRejeitarDadosNulos() {
        assertThrows(NullPointerException.class, () -> new Livro(null, "Autor", BigDecimal.ONE));
        assertThrows(NullPointerException.class, () -> new Livro("Livro", null, BigDecimal.ONE));
        assertThrows(NullPointerException.class, () -> new Livro("Livro", "Autor", null));
        assertThrows(NullPointerException.class, () -> new Revista(null, 1, BigDecimal.ONE));
        assertThrows(NullPointerException.class, () -> new Revista("Revista", 1, null));
    }

    @Test
    void deveRejeitarPublicacaoOuVisitanteNulo() {
        Acervo acervo = new Acervo();

        assertThrows(NullPointerException.class, () -> acervo.adicionar(null));
        assertThrows(NullPointerException.class, () -> acervo.aceitar(null));
        assertThrows(NullPointerException.class, () -> livro.aceitar(null));
        assertThrows(NullPointerException.class, () -> revista.aceitar(null));
    }
}

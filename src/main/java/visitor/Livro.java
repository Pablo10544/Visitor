package visitor;

import java.math.BigDecimal;
import java.util.Objects;

public final class Livro implements Publicacao {
    private final String titulo;
    private final String autor;
    private final BigDecimal preco;

    public Livro(String titulo, String autor, BigDecimal preco) {
        this.titulo = Objects.requireNonNull(titulo, "titulo");
        this.autor = Objects.requireNonNull(autor, "autor");
        this.preco = Objects.requireNonNull(preco, "preco");
        if (preco.signum() < 0) {
            throw new IllegalArgumentException("O preco nao pode ser negativo");
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    @Override
    public <R> R aceitar(VisitantePublicacao<R> visitante) {
        return Objects.requireNonNull(visitante, "visitante").visitar(this);
    }
}

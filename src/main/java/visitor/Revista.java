package visitor;

import java.math.BigDecimal;
import java.util.Objects;

public final class Revista implements Publicacao {
    private final String titulo;
    private final int edicao;
    private final BigDecimal preco;

    public Revista(String titulo, int edicao, BigDecimal preco) {
        this.titulo = Objects.requireNonNull(titulo, "titulo");
        if (edicao <= 0) {
            throw new IllegalArgumentException("A edicao deve ser positiva");
        }
        this.edicao = edicao;
        this.preco = Objects.requireNonNull(preco, "preco");
        if (preco.signum() < 0) {
            throw new IllegalArgumentException("O preco nao pode ser negativo");
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public int getEdicao() {
        return edicao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    @Override
    public <R> R aceitar(VisitantePublicacao<R> visitante) {
        return Objects.requireNonNull(visitante, "visitante").visitar(this);
    }
}

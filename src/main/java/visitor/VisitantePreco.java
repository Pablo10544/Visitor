package visitor;

import java.math.BigDecimal;

public final class VisitantePreco implements VisitantePublicacao<BigDecimal> {
    @Override
    public BigDecimal visitar(Livro livro) {
        return livro.getPreco();
    }

    @Override
    public BigDecimal visitar(Revista revista) {
        return revista.getPreco();
    }
}

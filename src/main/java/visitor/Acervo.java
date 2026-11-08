package visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Acervo {
    private final List<Publicacao> publicacoes = new ArrayList<>();

    public void adicionar(Publicacao publicacao) {
        publicacoes.add(Objects.requireNonNull(publicacao, "publicacao"));
    }

    public <R> List<R> aceitar(VisitantePublicacao<R> visitante) {
        Objects.requireNonNull(visitante, "visitante");
        List<R> resultados = new ArrayList<>();
        for (Publicacao publicacao : publicacoes) {
            resultados.add(publicacao.aceitar(visitante));
        }
        return resultados;
    }
}

package visitor;

public interface VisitantePublicacao<R> {
    R visitar(Livro livro);

    R visitar(Revista revista);
}

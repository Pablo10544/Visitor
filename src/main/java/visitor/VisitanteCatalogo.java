package visitor;

public final class VisitanteCatalogo implements VisitantePublicacao<String> {
    @Override
    public String visitar(Livro livro) {
        return "Livro: " + livro.getTitulo() + " | Autor: " + livro.getAutor();
    }

    @Override
    public String visitar(Revista revista) {
        return "Revista: " + revista.getTitulo() + " | Edicao: " + revista.getEdicao();
    }
}

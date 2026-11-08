package visitor;

public interface Publicacao {
    <R> R aceitar(VisitantePublicacao<R> visitante);
}

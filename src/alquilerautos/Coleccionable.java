package alquilerautos;

import java.util.Collection;
import java.util.List;

 /**
* @param <T>
* @param <H>
*/
public interface Coleccionable<T, H>{

    //alta(T e); //todos los métodos de una interface son abstract(está implícto), entonces
    // public abstract void bajar(T e); es lo mismo a void bajar(T e)
    public abstract boolean verificarSiEsta(H v);

    public abstract void agregarSiEs(H tipoColeccion, Collection<T> listadoColeccionables);

    public abstract int mostrar(H x);

    boolean buscar(H s);
}
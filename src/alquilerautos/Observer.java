package alquilerautos;

import java.util.Collection;

public interface Observer<Auto, H> {

    public abstract boolean verificarSiEsta(H v);//H va a representar un String

    public abstract void agregarSiEs(H tipoColeccion, Collection<Auto> listadoColeccionables);

    public abstract void mostrar();

    boolean buscar(H s);

    public abstract void actualizar(Oficina ofO);
    
   public abstract void actualizarOficinaDestino(Oficina ofO);

}

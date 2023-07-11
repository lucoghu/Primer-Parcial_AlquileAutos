package alquilerautos;

import java.util.Collection;


   public interface Observer<Auto,H> extends Coleccionable<Auto,H> {
    //una clase puede implementar una interface(implements)
    //pero si se quiere que una interface implemente otra interface se usa la palabra reservada "extends" en lugar
    //de "implements"
    @Override
    public abstract boolean verificarSiEsta(H v);//H va a representar a un String

    @Override
    public abstract void agregarSiEs(H tipoColeccion, Collection<Auto> listadoColeccionables);
    
    @Override
    boolean buscar(H s);

    public abstract void actualizar(Oficina ofO);

    public abstract void actualizarOficinaDestino(Oficina ofD);

    public abstract void actualizarOficinaReserva(Oficina ofR);
    
    public abstract int mostrar(String tipoObservador);
    
    public abstract void actualizarOficinaDestinoEnNull();

}

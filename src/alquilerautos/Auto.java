package alquilerautos;

import java.util.Collection;

//public class Auto implements Coleccionable<Auto,String>, Observer{
public class Auto implements Observer<Auto, String> {

    private String patente;
    private String modelo;
    private Color color;
    private Marca marca;
    private int ltsCombus; //litros combustible inicial
    private String codReserva = null;

    //subjeto: oficinaOrigen, para el patron observer
    private Oficina oficinaOrigen;  //Oficina donde pertenece el Auto, se estable cuando se da de alta un Auto
    private Oficina oficinaReserva; //Oficina donde se rerira el Auto reservado, se estable en la reserva
    private Oficina oficinaDestino; //Oficina donde se devuelve el Auto luego de ser usado, se estable en la reserva
    private int precio;

    //Al crear Auto, no se especifica una oficina de origen, dejandola que pueda cambiarse por setter
    //public Auto(String patente, String modelo, Color color, Marca marca, int ltsCombus,Oficina oficinaOrigen, int precio){
    public Auto(String patente, String modelo, Color color, Marca marca, int ltsCombus, int precio) {
        this.patente = patente;
        this.modelo = modelo;
        this.color = color;
        this.marca = marca;
        this.ltsCombus = ltsCombus;
        //this.oficinaOrigen=oficinaOrigen;
        this.oficinaOrigen = new Oficina();
        this.precio = precio;
    }

    // privado sólo se puede modificar cuando una oficina lo agrega a su lista
    private void setOficinaOrigen(Oficina oficinaOrigen) {
        this.oficinaOrigen = oficinaOrigen;
    }

    public void setOficinaReserva(Oficina oficinaReserva) {
        this.oficinaReserva = oficinaReserva;
    }

    public void setOficinaDestino(Oficina oficinaDestino) {
        this.oficinaDestino = oficinaDestino;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getPrecio() {
        return precio;
    }

    public String getCodReserva() {
        return codReserva;
    }

    public String getPatente() {
        return patente;
    }

    //El setter se activa cuando una auto se agrega a una Reserva, usando el Patron Observer
    public void setCodReserva(String codReserva) {
        this.codReserva = codReserva;
    }

    @Override
    public boolean verificarSiEsta(String patente) {
        return (this.patente == patente);
    }

    @Override
    public void agregarSiEs(String tipo, Collection<Auto> listadoAutos) {
        if ("Auto".equals(tipo)) {
            listadoAutos.add(this);
        }
    }

    @Override
    public void mostrar() {
        System.out.println(this);
    }

    @Override
    public void actualizar(Oficina ofO) { //implementa a Observer, setea Oficina de Origen
        setOficinaOrigen(ofO);
    }

    @Override
    public boolean buscar(String codigo) {
        boolean esEste = false;
        if (codigo.equals(patente)) {
            esEste = true;
        }
        return esEste;
    }

    public void actualizarOficinaDestino(Oficina ofO) { //implementa a Observer, setea Oficina de Destino
        setOficinaDestino(ofO);
    }

}

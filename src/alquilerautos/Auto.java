package alquilerautos;

import java.io.Serializable;
import java.util.Collection;

//public class Auto implements Coleccionable<Auto,String>, Observer{
public class Auto implements Observer<Auto, String>, Serializable{

    private String patente;
    private String modelo;
    private Color color;
    private Marca marca;
    private int ltsCombus; //litros combustible inicial
    private String codReserva=null;
    
    //subjeto: oficinaOrigen, para el patron observer
    private Oficina oficinaOrigen;  //Oficina donde pertenece el Auto, se estable cuando el administrador lo da de Alta
    private Oficina oficinaReserva; //Oficina donde se rerira el Auto reservado, se estable en la reserva
    private Oficina oficinaDestino; //Oficina donde se devuelve el Auto luego de ser usado, se estable en la reserva
    private int precio;

    
    //Al crear Auto, no se especifica una oficina de origen, dejandola que pueda cambiarse por setter
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

    
    public void setOficinaOrigen(Oficina oficinaOrigen) {
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

    public Oficina getOficinaOrigen() {
        return oficinaOrigen;
    }

    public Oficina getOficinaReserva() {
        return oficinaReserva;
    }

    public Oficina getOficinaDestino() {
        return oficinaDestino;
    }
    

    public String getModelo() {
        return modelo;
    }

    public Color getColor() {
        return color;
    }

    public Marca getMarca() {
        return marca;
    }

    public int getLtsCombus() {
        return ltsCombus;
    }

    
    
   //El setter se activa cuando una auto se agrega a una Reserva, usando el Patron Observer
    public void setCodReserva(String codReserva) {
        this.codReserva = codReserva;
    }
   
    
    
     @Override
    public boolean verificarSiEsta(String patente){
         return (this.patente == patente);
    }
    
   
    @Override
    public void agregarSiEs(String tipo, Collection<Auto> listadoAutos) {
        if ("Auto".equals(tipo)) {
            listadoAutos.add(this);
        }
    }
    
 
    @Override
    public int mostrar(String tipoAuto) {
        int i = 0;
        if ("Auto".equals(tipoAuto)) {
            // System.out.println(this);
            System.out.print(toString() + "\n");
            i++;
        }
        return i;
    }
    
    
    @Override
     public void actualizar(Oficina ofO){ //implementa a Observer, setea Oficina de Origen
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
 
    
    @Override
    public void actualizarOficinaDestino(Oficina ofO) { //implementa a Observer, setea Oficina de Destino
        setOficinaDestino(ofO);
    }
 
    
    @Override
    public void actualizarOficinaReserva(Oficina ofO) { //implementa a Observer, setea Oficina de Reserva
        setOficinaReserva(ofO);
    }

    
    @Override
    public void actualizarOficinaDestinoEnNull() {
        setOficinaDestino(null);
    }
  
    @Override
    public String toString() { 
        return "\nPatente : " + patente + "\nModelo : " + modelo + "\nColor :" + color + "\nMarca : "
                + marca + "\nReserva en tanque de combustible : " + ltsCombus
                + "\nOficina de Origen : " + oficinaOrigen + "\nOficina de Reserva : " + oficinaReserva
                + "\nOficina de Destino : " + oficinaDestino + "\nPrecio : " + precio + "\n\n";
    }
}
  


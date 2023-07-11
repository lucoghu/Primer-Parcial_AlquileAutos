package alquilerautos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;


public class Oficina implements Coleccionable<Oficina,String>, Serializable { 
     private String codOficina;  //Of1, Of2, ......  
     private int telefono;
     private String correo;
     private String domicilio;
     private List<Observer> observadores;
     private List<Reserva> reservas;// se asignan la/las reservas a la oficina, NO cuando se crea una oficina, Setter
    public Oficina(String codOficina, int telefono, String correo,String domicilio) {
        this.codOficina = codOficina;
        this.telefono = telefono;
        this.correo = correo;
        this.domicilio = domicilio;
       this.observadores= new ArrayList<>();
    }

    
    public Oficina() {
    }
    
    
    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    } 

    public String getCodOficina() {
        return codOficina;
    }
    
    
    
     @Override
    public boolean verificarSiEsta(String codOficina){
         return (this.codOficina == codOficina);
    }
    
    @Override
    public void agregarSiEs(String tipo, Collection<Oficina> listadoOficinas) {
        if ("Oficina".equals(tipo)) {
            listadoOficinas.add(this);
        }
    }
    
    
    @Override
    public int mostrar(String tipoOficina) {
        int i = 0;
        if ("Oficina".equals(tipoOficina)) {
            //  System.out.println(this);
            System.out.print(toString() + "\n");
            i++;
        }
        return i;
    }

    
    @Override
    public String toString() {
        return "\ncodOficina: " + codOficina + "\ntelefono : " + telefono + "\ncorreo :" + correo + "\ndomicilio :" + domicilio+"\n";
    }
    
    
    
    public void agregarObservador(Observer o){
     observadores.add(o);
    }
    
     public void sacarObservador(Observer o){
     observadores.remove(o);
    }
     
 
    public void avisarObservadores(){//avisa a los observadores(autos) que la oficina lo agrego en su lista de autos
        for (Observer obs : observadores) {
            obs.actualizar(this);
        }
    }
    
    
    public void avisarCambioOficinaDestino(){//avisa a los observadores(autos) cual es la oficina Destino de los autos reservados
        for (Observer obs : observadores) {
            obs.actualizarOficinaDestino(this);
        }
    }
    
     public void avisarCambioOficinaReserva(){//avisa a los observadores(autos) cual es la oficina Reserva de los autos reservados
        for (Observer obs : observadores) {
            obs.actualizarOficinaReserva(this);
        }
    }
     
     public void avisarObservadoresBaja(){//avisa a los observadores(autos) que la oficina sera dada de baja, entonces OfOrigen=null
        for (Observer obs : observadores) {
            obs.actualizar(null);
        }
    }
     
      public void avisarObservadoresSinOficinaDestino(){//avisa a los observadores(autos) que la oficina sera dada de baja, entonces OfOrigen=null
        for (Observer obs : observadores) {
            obs.actualizarOficinaDestinoEnNull();
        }
    }
    
    @Override
    public boolean buscar(String codigo){
         boolean esEste=false;
        if (codigo.equals(codOficina)) {
             esEste=true;
        }
        return esEste;
    }
  

    public List<Observer> getObservadores() {
        return observadores;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }
    
    
    public boolean verificarSiTenesObservadores() {
        try {
            int i = 0;
            int cant = 0;
            boolean hayAutos = false;
            while (i < observadores.size()) {
                if (observadores.get(i) != null) {
                    cant = cant + i;
                }
                i++;
            }

            if (i >= 1) {
                hayAutos = true;
            }

            return hayAutos;
        } catch (NullPointerException e) {
            Consola.mostrarCadena("No hay Autos.\n");
            return false;
        }
    }
    
    
    public ArrayList<Observer> obtenerObservadoresSinReserva() {
        ArrayList<Observer> elegidos1;
        elegidos1 = new ArrayList<>();
        try {
            for (Observer observador : observadores) {
                if (((Auto) observador).getCodReserva() == null) {
                    elegidos1.add(observador);
                }
            }
        } catch (NullPointerException e) {
            Consola.mostrarCadena("No hay Autos reservados en esta Oficina\n");
        }
        return elegidos1;
    }
    
    
    //son observadores con reserva, que pueden ser reservados a partir de una fecha posterior a su fecha final de reserva
    public ArrayList<Observer> obtenerObservadoresConReserva(LocalDate fecha) {
        ArrayList<Observer> elegidos2;
        elegidos2 = new ArrayList<>();
        try {
            for (Reserva r : reservas) {//por defecto LocalDate es null, y entra null a DAYS.between(f1,f2) DA ERROR
                if (r != null) {
                    if (DAYS.between(fecha, r.getFechaFin()) > 0) { //dif entre fecha, devuelve long( o int?)
                        for (Auto a : r.getAutos()) {
                            Consola.mostrarCadena("Patente :" + a.getPatente() + "  Precio : $" + a.getPatente() + "\n");
                            elegidos2.add(a);
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            Consola.mostrarCadena("No hay Reservas en esta Oficina\n");
        }
        return elegidos2;
    }
    
    
    
    public ArrayList<Observer> AutosElegidos(ArrayList<Observer> elegidos1, ArrayList<Observer> elegidos2) {
        ArrayList<Observer> elegidos;
        elegidos = new ArrayList<>();
        for (Observer e : elegidos1) {
            elegidos.add(e);
        }
        for (Observer e : elegidos2) {
            elegidos.add(e);
        }
        return elegidos;
    }
 
}
    

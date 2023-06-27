package alquilerautos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Oficina implements Coleccionable<Oficina, String> {

    private String codOficina;  //Of1, Of2, ......  
    private int telefono;
    private String correo;
    private String domicilio;
    private List<Observer> observadores;
    private List<Reserva> reservas;// se asignan la/las reservas a la oficina, no cuando se crea una oficina, Setter

    public Oficina(String codOficina, int telefono, String correo, String domicilio) {
        this.codOficina = codOficina;
        this.telefono = telefono;
        this.correo = correo;
        this.domicilio = domicilio;
        this.observadores = new ArrayList<>();
    }

    public Oficina() {
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    @Override
    public boolean verificarSiEsta(String codOficina) {
        return (this.codOficina == codOficina);
    }

    @Override
    public void agregarSiEs(String tipo, Collection<Oficina> listadoOficinas) {
        if ("Oficina".equals(tipo)) {
            listadoOficinas.add(this);
        }
    }

    @Override
    public void mostrar() {
        System.out.println(this);
    }

    public void agregarObservador(Observer o) {
        observadores.add(o);
    }

    public void sacarObservador(Observer o) {
        observadores.remove(o);
    }

    public void avisarObservadores() {//avisa a los observadores(autos) que la oficina lo agrego en su lista de autos
        for (Observer obs : observadores) {
            obs.actualizar(this);
        }
    }

    public void avisarCambioOfinaDestino() {//avisa a los observadores(autos) cual es la oficina de los autos reservados
        for (Observer obs : observadores) {
            obs.actualizarOficinaDestino(this);
        }
    }

    @Override
    public boolean buscar(String codigo) {
        boolean esEste = false;
        if (codigo.equals(codOficina)) {
            esEste = true;
        }
        return esEste;
    }

    public List<Observer> getObservadores() {
        return observadores;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

}
    

package alquilerautos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Cliente extends Usuario<Cliente> {

    private int codUnico;
    private List<Reserva> reservas; //En un momento dado, un determinado cliente puede tener varias reservas.
    // private Reserva reserva; // 

    public Cliente(int dni, String nombre, String direccion, int telefono, String usuario, String clave) {
        super(dni, nombre, direccion, telefono, usuario, clave);
        this.codUnico = 0;
        this.reservas = new ArrayList<>();
    }

    @Override
    public boolean ingresar() {
        return true; // el cliente no puede salir del sistema, no accede al Sistema para hacer una Reserva
    }

    @Override
    public void agregarSiEs(String tipoUsuario, Collection<Cliente> listadoClientes) {
        if (tipoUsuario.equals("Cliente")) {
            listadoClientes.add(this);
        }
    }

    @Override
    public void mostrar() {
        System.out.println(this);
    }

    @Override
    public boolean buscar(String codigo) {
        boolean esEste = false;
        if (codigo.equals(super.getUsuario())) {
            esEste = true;
        }
        return esEste;
    }

    public int getCodUnico() {
        return codUnico;
    }

    public void setCodUnico(int codUnico) {
        this.codUnico = codUnico;
    }

    /* //ver, falta hacer
     public boolean tomoReserva(){ // para saber si el cliente tiene o no reservas
        return reservas.isEmpty();
    } */
    public void mostrarReservaPorCod() {
        Consola.mostrarCadena("El Cliente " + super.getNombre() + " con código único: " + codUnico + " tiene estas Reservas: \n\n");
        Consola.mostrarCadena("Código de Reserva                   Fecha de incio                   Fecha de fin\n");
        for (Reserva r : reservas) {
            Consola.mostrarCadena(r.getCodReserva() + "                   ");
            Consola.mostrarCadena(r.getFechaIni() + "                   ");
            Consola.mostrarCadena(r.getFechaFin() + "\n\n");
        }
    }

    public boolean buscarPorCodReserva(String codReserva) {
        int i = 0;
        boolean encontrado = false;
        while (i < reservas.size() && !encontrado) {
            if (reservas.get(i).getCodReserva().equals(codReserva)) {
                encontrado = true;
            }
            i++;
        }
        return encontrado;
    }

    public void agregarReserva(Reserva r) {
        reservas.add(r);
    }

}

package alquilerautos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Cliente extends Usuario<Cliente> implements Serializable{
    
    private int codUnico;
    private List<Reserva> reservas; //En un momento dado, un determinado cliente puede tener varias reservas.
  
    
    public Cliente(int dni, String nombre, String direccion, int telefono, String usuario, String clave) {
        super(dni, nombre, direccion, telefono, usuario, clave);
        this.codUnico = 0;
        this.reservas = new ArrayList<>();
    }

    
    public Cliente() {//se necesita para usar Factory con reflection
    }
    

     @Override
    public boolean ingresar(Sistema s) { // el cliente no puede salir del sistema, no accede al Sistema para hacer una Reserva
 
        boolean continuar=true;  //para salir de este menú
        boolean salir=false;
        int op;
        while (continuar) {
            Consola.mostrarCadena("\n**** Menú del Cliente *****");
            Consola.mostrarCadena("[1] Consulta de Reservas");
            Consola.mostrarCadena("[2] Consulta de Autosm reservados");
            Consola.mostrarCadena("[3] Consulta por cliente");
            Consola.mostrarCadena("[4] Salir de este Menú\n");
            op = Consola.leerEntero();

            switch (op) {
                case 1:
                    consultarReservas(s);
                    break;
                case 2:
                    consultarAutosReservados(s);
                    break;
                case 3:
                    continuar = false;
                    salir=true;
                    break;
                default:
                    Consola.mostrarCadena("Opción no válida");
                    break;
            }
            if(1<=op && op<3){
            continuar=true;
            }
        }
        // el Cliente no puede salir del sistema, por eso se retorna true
        Consola.limpiarBuffer();
        return salir;
    }
    
    
    
   private void consultarReservas(Sistema s) {
   String codReserva=""; 
   Reserva reservaCliente;
   codReserva=Consola.leerCadena("Ingrese su código de Reserva");
   reservaCliente=(Reserva) s.obtenerColeccionablePorCodigo("Reserva", codReserva);
   if(reservaCliente !=null){
    reservaCliente.mostrar(codReserva);
   }else{Consola.mostrarCadena("Usted no tiene Reservas!!");}
   }
    
    
    private void consultarAutosReservados(Sistema s){
    
        ////////
    }
    
    
    @Override
    public void agregarSiEs(String tipoUsuario, Collection<Cliente> listadoClientes) {
        if (tipoUsuario.equals("Cliente")) {
            listadoClientes.add(this);
        }
    }
    
  
    @Override
    public int mostrar(String tipoCliente) {
        int i = 0;
        if ("Cliente".equals(tipoCliente)) {
            System.out.print(super.toString("Cliente"));
            System.out.print("\rCódigo único de Cliente : "+codUnico+"\n\n");
            i++;
        }
        return i;
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
        int i=0;
        Consola.mostrarCadena("\nEl Cliente "+super.getNombre()+" con código único: "+codUnico+" tiene estas Reservas: \n");
        Consola.mostrarCadena("Código de Reserva                   Fecha de incio                   Fecha de fin\n");
        try{
           for (Reserva r : reservas) {
           Consola.mostrarCadena(r.getCodReserva()+"                   ");
           Consola.mostrarCadena(r.getFechaIni()+"                   ");
           Consola.mostrarCadena(r.getFechaFin()+"\n\n");
           i++;
           }
         if(i==0){Consola.mostrarCadena("El cliente no tiene reservas.\n\n");}
        }catch(NullPointerException e){
            Consola.mostrarCadena("El cliente no tiene reservas.\n\n");
        }
       }
    
    
    public boolean buscarPorCodReserva(String codReserva) {
        try {
            int i = 0;
            boolean encontrado = false;
            while (i < reservas.size() && !encontrado) {
                if (reservas.get(i).getCodReserva().equals(codReserva)) {
                    encontrado = true;
                }
                i++;
            }
            return encontrado;
        } catch (NullPointerException e) {
            Consola.mostrarCadena("No hay Reservas con este código.\n");
            return false;
        }
    }
    
    
    public void agregarReserva(Reserva r) {
        try{
        reservas.add(r);
         } catch (NullPointerException e) {
            Consola.mostrarCadena("No hay reservas para este cliente\n");
        }
    }
    
}

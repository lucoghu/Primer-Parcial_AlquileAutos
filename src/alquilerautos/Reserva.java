package alquilerautos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Reserva implements Coleccionable<Reserva,String>, Serializable{
  // por defecto LocalDate es null
  private LocalDate fechaIni; //fecha inicial
  private LocalDate fechaFin; //fecha final
  private Boolean entrega; //indica si el o los vehiculos reservados fueron entregados una vez finalizado el alquiler
  private List<Auto> autos; //lista de auto o autos a ser reservados
  private String codReserva;
  private int precioTotal;

    public Reserva(LocalDate fechaIni, LocalDate fechaFin, Boolean entrega, String codReserva, int precioTotal) {
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.entrega = entrega;
        this.autos = new ArrayList<>();
        this.codReserva = codReserva;
        this.precioTotal = precioTotal;
    }

   
    @Override
    public boolean verificarSiEsta(String codigoReserva){
         return (this.codReserva == codReserva);
    }
    
    
    @Override
    public void agregarSiEs(String tipo, Collection<Reserva> listadoReservas) {
        if ("Reserva".equals(tipo)) {
            listadoReservas.add(this);
        }
    }
    
  
    @Override
    public int mostrar(String tipoReserva) {
        int i = 0;
            if ("Reserva".equals(tipoReserva)) {
                // System.out.println(this);
                System.out.print(toString() + "\n");
                i++;
            }
        return i;
    }

    
    
    @Override
    public boolean buscar(String codigo) {
        boolean esEste = false;
        if (codigo.equals(codReserva)) {
            esEste = true;
        }
        return esEste;
    }

    public String getCodReserva() {
        return codReserva;
    }

    public LocalDate getFechaIni() {
        return fechaIni;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public List<Auto> getAutos() {
        return autos;
    }   

     public int getPrecioTotal() {
        return precioTotal;
    }
    
    public void setEntrega(Boolean entrega) {
        this.entrega = entrega;
    }
    
     public void agregarAuto(Auto a){
     autos.add(a);
    }
    
    @Override
    public String toString() {
        return "\nfechaIni : " + fechaIni + "\nfechaFin : " + fechaFin + "\nentregado : " + entrega + "\nautos : " + autos + "\ncodReserva : " + codReserva + "\nprecioTotal : " + precioTotal +"\n";
    }
   
}

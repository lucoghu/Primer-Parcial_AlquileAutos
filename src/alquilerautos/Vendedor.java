package alquilerautos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Vendedor extends Gestor<Vendedor> {

    public Vendedor(int dni, String nombre, String direccion, int telefono, String usuario, String clave) {
        super(dni, nombre, direccion, telefono, usuario, clave);
    }

    
    @Override
    public boolean ingresar() { //Los vendeores administran a las reservas y a los clientes
 
        boolean continuar=true;  //para salir de este menú
        int op;
        while (continuar) {
            Consola.mostrarCadena("**** Menú del Vendedor *****");
            Consola.mostrarCadena("[1] Dar de Alta Cliente");
            Consola.mostrarCadena("[2] Dar de Baja Cliente");
            Consola.mostrarCadena("[3] Consulta por cliente");
            Consola.mostrarCadena("[4] Listar Clientes");
            Consola.mostrarCadena("[5] Hacer una Reserva");
            Consola.mostrarCadena("[6] Finalizar una Reserva");
            Consola.mostrarCadena("[7] Listar Reservas");
            Consola.mostrarCadena("[8] Salir de este Menú");
            op = Consola.leerEntero();

            switch (op) {
                case 1:
                    altaCliente();
                    break;
                case 2:
                    bajaCliente();
                    break;
                case 3:
                    consultaPorCliente();
                    break;
                case 4:
                    listarClientes();
                    break;
                case 5:
                    hacerReserva();
                    break;
                case 6:
                    finalizarReserva(); //CUANDO EL CLIENTE DEVUELVE EL AUTO EN LA OFICINA DESTINO !!!!
                    break;
                 case 7:
                    listarReservas();
                    break;
                 case 8:
                    cancelarReserva();
                    break;
                case 9:
                    continuar = false;
                    break;
                default:
                    Consola.mostrarCadena("Opción no válida");
                    break;
            }
            if(1<=op && op<9){
            continuar=true;
            }
        }
        return true; // el vendedor no puede salir del sistema, por eso se retorna true
    }
 
    
   
    
    @Override
    public void agregarSiEs(String tipoUsuario, Collection<Vendedor> listadoVendedores) {
        if ("Vendedor".equals(tipoUsuario)) {
            listadoVendedores.add(this);
        }
    }
    

     @Override
    public void mostrar(){
        System.out.println(this);
    }
   
    
    @Override
    public boolean buscar(String codigo){
         boolean esEste=false;
        if (codigo.equals(super.getUsuario())) {
             esEste=true;
        }
        return esEste;
    }
    
    
    void altaCliente() {
        Sistema s = new Sistema();
        boolean salirMenu = false;
        String tipoUsuario = "";
        Consola.mostrarCadena("[1] Alta Cliente\n [2] Salir\n\n");
        int op = Consola.leerEntero();
        while (!salirMenu) {
            switch (op) {
                case 1:
                    tipoUsuario = "Cliente";
                    break;
                case 2:
                    salirMenu = true;
                    break;
                default:
                    Consola.mostrarCadena("Opción no válida\n\n");
                    salirMenu = true;
                    break;
            }

            if (op == 1) {
                //Vendedor solo puede dar de alta a un cliente, por eso altaUsuario("Cliente")
                // altaUsuario("Cliente") devuele un cliente visto como usuario, por eso Casteo a Cliente
                Cliente cliente = (Cliente) super.altaUsuario("Cliente");
                if (cliente != null) {
                    int codVerificar = Consola.leerEntero("Asignar código a este Cliente, debe ser un ENTERO MAYOR A CERO\n\n");
                    super.asignarCodigoAlCliente(cliente,codVerificar);
                } else {
                    Consola.mostrarCadena("No se pudo dar de alta al Cliente!!\n\n");
                }
            }
            salirMenu = true;
           //al salir del while, sigue con continuar=true del menú del Vededor, entonces vuelve a mostrar menú Vendedor
        }
    }
 
    
   private void bajaCliente() {
        Sistema s = new Sistema();
        boolean salirMenu = false;
        String tipoUsuario = "";
        Consola.mostrarCadena("[1] Dar de baja Cliente\n [2] Salir");
        int op = Consola.leerEntero();
        while (!salirMenu) {
            switch (op) {
                case 1:
                    tipoUsuario = "Cliente";
                    break;
                case 2:
                    salirMenu = true;
                    break;
                default:
                    Consola.mostrarCadena("Opción no válida");
                    salirMenu = true;
                    break;
            }
        }

        if (op == 1) {
            s.mostrar1(tipoUsuario);
            String usu = Consola.leerCadena("Intruduza el nombre de Usuario del Cliente a dar de baja\n");
            // if (s.verSiExisteUsuario(dni) != null) {
            if (s.obtenerColeccionablePorCodigo("Usuario", usu) != null) { //verifica que se encuentre registrado   
                if( s.bajar(s.obtenerColeccionablePorCodigo("Usuario", usu)))  //eliminar de la lista de usuarios
                {  Consola.mostrarCadena("Cliente fue dado de baja");
                  }else{Consola.mostrarCadena("El Cliente no pudo ser dado de baja");}
                
            } else {
                Consola.mostrarCadena("El Cliente con nombre de usuario "+usu+" al que quiere dar de baja no existe\n");
            }
        }
    }
     
   
    void consultaPorCliente(){ //consulta las reservas que tiene un cliente determinado
     int consulta=Consola.leerEntero("ingrese el código único del Cliente para ver sus Reservas");
     ////////if(consulta==0) "el cliente esta registrado pero no tiene cod"
    }
    
     void listarClientes(){
    ////////
    }
     
     
     
    //La fecha de inicio y fecha final es por reserva, pero cada Reserva puede tener varios Autos.
    //El Cliente hace la reserva, esta tiene fecha de incio: cuando el auto empieza a ser alquilado,
    //y fecha final, es cuando el Auto se devuelve a una Oficina.
    //UN CLIENTE SOLO PUEDE TENER UNA RESERVA, Y ESTA PUEDE TENER VARIOS AUTOS.
    //Si hay mas de 1 Auto incluido en la Reserva,este o estos Autos son usados con la misma 
    //fechas de inicio y fecha de final, y todos los Autos se retiran y se entregan en las mismas Oficina.
    //Teniendo las fecha de inicio y fecha de fin definidas se ofrecen los Autos disponibles por cada Oficina.
    //Se considera que un fecha ocupa todo un día, es decir no se considera horas, sólo días completos.
    void hacerReserva() {
        //los vendedores pueden estar en cualquier oficina, un cliente llama a una oficina y lo atiende un vendedor,
        //este le hace la reserava y le asigna una oficina de retiro y una oficina de entrega.

        Sistema s = new Sistema();
        boolean volver = true;
        Consola.mostrarCadena("Iniciar Reserva\n\n");
        char registrado = Consola.siNo("El cliente está registrado?  Si(S)  No(N) \n\n");
        if (registrado == 'S' || registrado == 's') // alt +124: ||
        { //debería mostrar un listado de Clientes con su código único
            int cod = Consola.leerEntero("Ingrese el código del Cliente, debe ser mayor o igual a 0");
            //Verifico si hay Cliente registrado con ese código
            if (s.buscarClientePorCod("Cliente", cod) == -1) {
                Consola.mostrarCadena("El código " + cod + " no es válido \n\n");
            } //tiene que volver al menu de vendedor, lo hago al final, a la espera de una ENTER        
            
            if (s.buscarClientePorCod("Cliente", cod) == -2) {
                Consola.mostrarCadena("No hay Cliente registrado con código único : " + cod + " \n\n");
            } //tiene que volver al menu de vendedor, lo hago al final, a la espera de una ENTER 
  
            if (s.buscarClientePorCod("Cliente", cod) == cod) { //cliente registrado
                    //Si esta registrado hay que pedir datos sobre la reserva
                    cargarReserva(cod);
                }        
        }
        Consola.mostrarCadenaEnter("Presinar ENTER para volver al menú del Vendedor\n\n");
    }
    
    
  
    
    
    
     //****************************************************************IMPORTANTE*************************
      //UN CLIENTE HACE 1 RESERVA Y RESERVA 1 AUTO EN UN OFICINA DE RESERVA
      //CONSIDERO A CADA RESERVA COMO UN PAQUETE, DONDE SE PUEDEN IR PONIEDO AUTOS QUE PERTENECEN A ESE PAQUETE
      //OSEA QUE TODO AUTO PERTENECIENTE A ESTA RESERVA TENDRA EL MISMO CODIGO DE RESERVA, ENTONCES TODOS ESTOS AUTOS
      //SE RETIRAN EN FECHA INICIAL Y DE LA MISMA OFICIA, Y SE ENTREGAN EN LA MISMA FECHA Y EN LA MISMA OFICINA.
      //SI UNA AUTO TIENE codReserva != 0 pertenece a una Resrva y esta ocupado, en cambio si tiene
      //codReserva=0 esta desocupado o libre.
     //************************************************************************** 
    void cargarReserva(int cod) {
        Sistema s = new Sistema();
        String codReservaVericar = "";
        int precioParcial = 0;
        boolean masAutos = false;
        ArrayList<String> patentesReservadas = null;
        //PRIMERO HAY QUE VERIFICAR SI ESTE CLIENTE TIENE O NO RESERAVA GENERADA
        //SI TIENE HAY QUE OBTENER EL codReserva, y VERIFICAR QUE LAS FECHAS DE RESERVAS NO CHOQUEN
        Cliente cliente = s.buscarClientePorCodAsignado("Cliente", cod);
        cliente.mostrarReservaPorCod(); //lista todas la reservas de este cliente

        String codReserva = Consola.leerCadena("Asignar código a esta Reserva: Reserva01, Reserva02, etc:\n\n");
        boolean encontrado = verificarSiExisteReserva(codReserva);

        while (encontrado) {
            codReserva = Consola.leerCadena("Ya existe una reserva con este código, ingrese otro código para asignar\n");
            encontrado = verificarSiExisteReserva(codReserva);
        }

        if (encontrado == false) { //hay que validar String a un formato, falta hacer en Consola: codReserva01,...
            Consola.mostrarCadena("Codigo de Reserva " + codReserva + " generado con exito\n\n");
            //hay que cargarle este nuevo cod de reserva a este cliente, EN REALIDAD EL OBJETO RESERVA COMPLETO
            //ENTONCES SIGO CON LA CARGA DE LA RESERA, UNA VEZ SE TENGA TODOS LOS ATRIBUTOS DE LA RESERVA
            //SE CONTRUYE UNA RESERVA Y SE HACE ESTO:  cliente.agregarReserva(r);
            LocalDate fechaInicio;
            LocalDate fechaFin;

            //falta validar que fecha se ingrese en el formato indicado, supongo que se entra con el formato correcto
            String fechaI = Consola.leerCadena("ingrese la fecha inicio de la Reserva, con formato DD/MM/YYYY \n");
            String fechaF = Consola.leerCadena("ingrese la fecha inicio de la Reserva, con formato DD/MM/YYYY \n");

            //si NO se ingresa en el formato indicado se rompe!!
            fechaInicio = LocalDate.parse(fechaI, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            fechaFin = LocalDate.parse(fechaF, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            String codOficinaReserva = Consola.leerCadena("Asignar Oficina de Reserva,'Of1','Of2', etc\n\n");
            //mostrar listado de oficinas y verificar que la oficina exista
            Coleccionable oficina = s.obtenerColeccionablePorCodigo("Oficina", codOficinaReserva);
            while (oficina == null) {
                codOficinaReserva = Consola.leerCadena("La Oficina ingresada no existe, ingresar otra:'Of1','Of2', etc\n\n");
                oficina = s.obtenerColeccionablePorCodigo("Oficina", codOficinaReserva);
            }

            if (oficina != null) {
                //mostrar Autos disponible para es Oficina , es decir hay que considerar fechas de Resrvas para estos Autos.
                //Por cada Oficina hay que mostrar todos sus observadores(Autos),y a estos filtrar por fechas.
                //disponible caso 1: cuando su atributo codReserva=null, osea al momento de reservar esta libre.
                //disponible caso 2: con codReserva pero la FechaInicio nueva reserva > FerchaFinal anterior Reserva

                //Listar disponibles y traer: Autos con FechaIniNuevaReserva > FechaFinAnteriorReseerva si tienen Reserva, y los que no tienen Reserva 
                ArrayList<Observer> listaAutosDisponibles = listarAutosDisponiblesPorOficina(codOficinaReserva, fechaInicio);
                char continua = Consola.siNo("Quiere continuar con Reserva?  Si(S)  No(N) \n\n");
                if (continua == 'S' || continua == 's') {
                    while (!masAutos) {
                        String patenteReserva = Consola.leerCadena("Ingrese la patente del Auto a Reservas: ");
                        Auto auto = (Auto) s.obtenerColeccionablePorCodigo("Auto", patenteReserva);
                        int i = 0;
                        boolean libre = false;
                        while (i < listaAutosDisponibles.size() && !libre) {
                            if (auto.getPatente().equals(((Auto) listaAutosDisponibles.get(i)).getPatente())) {
                                libre = true; //patente de Auto ingresada coincide con patente de auto de la lita de elegidos
                            } else {
                                patenteReserva = Consola.leerCadena("Auto no disponible, Ingrese otra patente del Auto a Reservar: ");
                                libre = false;
                            }
                            i++;
                        }

                        if (libre) {
                            //por cada auto agregado se necesita su precio, para luego sumar todo al precioTotal de la Reserva
                            precioParcial = auto.getPrecio() + precioParcial;
                            //se van guardando las patentes de los autos reservados
                            patentesReservadas.add(patenteReserva); //para poder setear atrributos de auto reservado
                        } else {
                            Consola.mostrarCadenaEnter("El Auto con patente : " + patenteReserva + " no existe o no estadisponible, [ENTER] para continuar");
                        }
                        char seguir = Consola.siNo("Quiere agregar otro Auto a la Reserva : \n\n");
                        if (seguir == 'S' || seguir == 's') {
                            masAutos = false;
                        } else {
                            masAutos = true;
                            continua = 'N';
                        }

                    }
                    int precioTotal = precioParcial;
                    s.altaReservaa(fechaInicio, fechaFin, false, codReserva, precioTotal);
                    //hay que Setter los atributos de los Autos Reservados, como "codReserva",  "oficinaReserva", "oficinaDestino"
                    // y a la Reserva hay que setearle el atributo entrega=false en el caso de Alta Reserva, entraga=true al bajar Reserva
                    String codOficinaDestino = Consola.leerCadena("Ingrese el código de la oficina de Destino(Of1, Of2, ect :\n\n");
                    Oficina oficinaDestino = (Oficina) s.obtenerColeccionablePorCodigo("Oficina", codOficinaDestino);
                    for (String p : patentesReservadas) {
                        s.obtenerColeccionablePorCodigo("Auto", p); //Auto reservado
                        ((Oficina) oficinaDestino).avisarCambioOfinaDestino();//setea Oficina Destino en el Auto
                        //FALTA SETEAR ATRIBUTOS DE LOS AUTOS
                    }
                }
            }

        }
    }
   
        
     
   //por cada cliente busco en sus reservas si exite este codReserva
   //sale del ciclo ni bien encuentra a codReserva     
    boolean verificarSiExisteReserva(String codReserva) {
        Sistema s = new Sistema(); //clientes vistos como coleccionables
        ArrayList<Coleccionable> listaCliente = s.separarEnTipoColeccion("Cliente");
        boolean encontrado = false;
        int i = 0;
        while (!encontrado) {
            encontrado = ((Cliente) listaCliente.get(i)).buscarPorCodReserva(codReserva);
            i++;
        }
        return encontrado;
    }
        
    
     
    //disponible: cuando su atributo codReserva=null o cuando FechaIncialNuevaReserva > FechaFinalAnteriorReserva
    //si no hay reservas en una oficina se debería listar todos los autos de esa Oficina como dsiponibles
     //1° se listan todos los Autos sin reservas, y si tienen reserva se verifica fecha y se listan
    ArrayList<Observer> listarAutosDisponiblesPorOficina(String codOficina, LocalDate fecha) {//disponible: cuando su atributo codReserva=null
        Sistema s = new Sistema();
        Oficina oficina = (Oficina) s.obtenerColeccionablePorCodigo("Oficina", codOficina);
        List<Reserva> reservas = oficina.getReservas();
        ArrayList<Observer> elegidos = null;
        ArrayList<Observer> elegidos1 = null;
        ArrayList<Observer> elegidos2 = null;

        for (Observer observador : oficina.getObservadores()) {
            if (((Auto) observador).getCodReserva() == null) {
                elegidos1.add(observador);
                Consola.mostrarCadena("Patente :" + ((Auto) observador).getPatente() + "  Precio : $" + ((Auto) observador).getPatente() + "\n");
            } else {  //si tienen reserva se verifica fecha y se listan
                for (Reserva r : reservas) {//por defecto LocalDate es null, y entra null a DAYS.between(f1,f2) DA ERROR
                    if (r != null) {
                        if (DAYS.between(fecha, r.getFechaFin()) > 0) { //if( DAYS.between(fechaInicio, fechaFin)>0){//dif entre fecha, devuelve int
                            for (Auto a : r.getAutos()) {
                                Consola.mostrarCadena("Patente :" + a.getPatente() + "  Precio : $" + a.getPatente() + "\n");
                                elegidos2.add(observador);
                            }
                        }
                    }
                }
            }
        }
        for (Observer e : elegidos) {
            for (Observer e1 : elegidos1) {
                elegidos.add(e1);
            }
            for (Observer e2 : elegidos2) {
                elegidos.add(e2);
            }
        }
        return elegidos; //lista Observadores(Autos)     
    }

      
      
     void finalizarReserva(){
    ////////
    }
     
     
     void listarReservas(){
    ////////
    }
     
     
     void cancelarReserva(){
     /////
     }
     
}
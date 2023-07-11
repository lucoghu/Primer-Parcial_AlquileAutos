package alquilerautos;

import java.io.Serializable;
import static java.time.Duration.between;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Vendedor extends Gestor<Vendedor> implements Serializable {

    public Vendedor(int dni, String nombre, String direccion, int telefono, String usuario, String clave) {
        super(dni, nombre, direccion, telefono, usuario, clave);
    }

    public Vendedor() { //se necesita para usar Factory con reflection
    }

    
    @Override
    public boolean ingresar(Sistema s) { //Los vendeores administran a las reservas y a los clientes
 
        boolean continuar=true;  //para salir de este menú
        boolean salir=false;
        int op;
        while (continuar) {
            Consola.mostrarCadena("\n**** Menú del Vendedor *****");
            Consola.mostrarCadena("[1] Dar de Alta Cliente");
            Consola.mostrarCadena("[2] Dar de Baja Cliente");
            Consola.mostrarCadena("[3] Consulta por cliente");
            Consola.mostrarCadena("[4] Listar Clientes");
            Consola.mostrarCadena("[5] Hacer una Reserva");
            Consola.mostrarCadena("[6] Finalizar una Reserva");
            Consola.mostrarCadena("[7] Listar Reservas");
           // Consola.mostrarCadena("[8] Cancelar Reserva"); //falta
            Consola.mostrarCadena("[8] Salir de este Menú\n");
            op = Consola.leerEntero();

            switch (op) {
                case 1:
                    altaCliente(s);
                    break;
                case 2:
                    bajaCliente(s);
                    break;
                case 3:
                    consultaPorCliente(s);
                    break;
                case 4:
                    listarClientes(s);
                    break;
                case 5:
                    hacerReserva(s);
                    break;
                case 6:
                    finalizarReserva(s);
                    break;
                 case 7:
                    listarReservas(s);
                    break;
                 case 8:
                    continuar = false;
                    salir=true;
                    break;
                default:
                    Consola.mostrarCadena("Opción no válida");
                    break;
            }
            if(1<=op && op<8){
            continuar=true;
            }
        }
        // el vendedor no puede salir del sistema, por eso se retorna true
        Consola.limpiarBuffer();
        return salir;
    }
 
    
    @Override
    public void agregarSiEs(String tipoUsuario, Collection<Vendedor> listadoVendedores) {
        if ("Vendedor".equals(tipoUsuario)) {
            listadoVendedores.add(this);
        }
    }
    
    
    @Override
    public int mostrar(String tipoVendedor) {
        int i = 0;
        if ("Vendedor".equals(tipoVendedor)) {
            System.out.print(super.toString("Vendedor") + "\n");
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
    
    private void altaCliente(Sistema s) {
        boolean salirMenu = false;
        String tipoUsuario = "";
        Consola.mostrarCadena("[1] Continuar con Alta Cliente\n [2] Salir\n\n");
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
                Cliente cliente = (Cliente) super.altaUsuario("Cliente", s);
                if (cliente != null) {
                    int codVerificar = Consola.leerEntero("Asignar código a este Cliente, debe ser un ENTERO MAYOR A CERO");
                    if (super.asignarCodigoAlCliente(cliente, codVerificar)) {
                        s.agregarAColeccion(cliente);
                        s.mostrar1("Cliente");
                        Consola.mostrarCadenaEnter("Cliente dado de alta exitosamnete!! Presionar [Enter] para volver al Menú\n\n");
                    } else {
                        Consola.mostrarCadena("No se pudo asignar código único al Cliente!!n");
                    }
                } else {
                    Consola.mostrarCadena("No se pudo dar de alta al Cliente!!\n\n");
                }
            }
            salirMenu = true;
            //al salir del while, sigue con continuar=true del menú del Vededor, entonces vuelve a mostrar menú Vendedor
        }
    }
    
    
    
    private void bajaCliente(Sistema s) {
        boolean salirMenu = false;
        String tipoUsuario = "";
        Consola.mostrarCadena("[1] Continua con baja Cliente\n [2] Salir\n");
        int op = Consola.leerEntero();
        Consola.limpiarBuffer();
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

            if (op == 1) {
                s.mostrar1(tipoUsuario);
                String usu = Consola.leerCadena("Intruduza el nombre de Usuario del Cliente a dar de baja");
                Coleccionable c;
                c = s.obtenerColeccionablePorCodigo(tipoUsuario, usu);
                if (c != null) { //verifica que se encuentre registrado
                    if (s.bajar(c)) //eliminar de la lista de usuarios
                    {
                        Consola.mostrarCadena("Cliente fue dado de baja");
                        Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Vendedor\n");
                    } else {
                        Consola.mostrarCadena("El Cliente no pudo ser dado de baja");
                    }

                } else {
                    Consola.mostrarCadena("El Cliente con nombre de usuario " + usu + " al que quiere dar de baja no existe\n");
                }
            }
            salirMenu = true;
        }
    }
     
    
   
    private void consultaPorCliente(Sistema s) { //consulta las reservas que tiene un cliente determinado
        int consulta = Consola.leerEntero("ingrese el código único del Cliente para ver sus Reservas");
        if (consulta > 0) {
            Cliente cliente = s.buscarClientePorCodAsignado("Cliente", consulta);
            if (cliente != null) {
                cliente.mostrarReservaPorCod();
                Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Vendedor\n");
            }
        } else {
            Consola.mostrarCadena("Código único de Cliente no válido!!! , debe ser entero mayor a cero\n");
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Vendedor\n");
        }
    }
    
    
   
    private void listarClientes(Sistema s) {
        Consola.limpiarBuffer();
        if (s.mostrarPorTipoDeColeccion("Cliente")) {
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Vendedor\n");
        } else {
            Consola.mostrarCadena("No hay Clientes registrados en el Sistema!!!");
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Vendedor\n");
        }
    }
     
     
     
    //La fecha de inicio y fecha final es por reserva, pero cada Reserva puede tener varios Autos. El Cliente hace la 
    //reserva, esta tiene fecha de incio: cuando el auto empieza a ser alquilado, y fecha final, es cuando el Auto 
    //se devuelve a una Oficina. UN CLIENTE SOLO PUEDE TENER UNA RESERVA, Y ESTA PUEDE TENER VARIOS AUTOS.
    //Si hay mas de 1 Auto incluido en la Reserva,este o estos Autos son usados con la misma fechas de inicio y 
    //fecha de final, y todos los Autos se retiran y se entregan en las mismas Oficina.
    //Teniendo las fecha de inicio y fecha de fin definidas se ofrecen los Autos disponibles por cada Oficina.
    //Se considera que un fecha ocupa todo un día, es decir no se considera horas, sólo días completos.
    private void hacerReserva(Sistema s) {
        //los vendedores pueden estar en cualquier oficina, un cliente llama a una oficina y lo atiende un vendedor,
        //este le hace la reserava y le asigna una oficina de retiro y una oficina de entrega.
        Consola.mostrarCadena("Iniciar Reserva\n");
        Consola.limpiarBuffer();
        char registrado = Consola.siNo("El cliente está registrado?  Si(S)  No(N)");
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
                cargarReserva(s, cod);
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
    private void cargarReserva(Sistema s, int cod) {
        //Sistema s = new Sistema();
        String codReservaVericar = "";
        int precioParcial = 0;
        boolean continuar = true;
        LocalDate fechaInicio;
        LocalDate fechaFin;
        boolean masAutos = false;
        ArrayList<String> patentesReservadas;
        patentesReservadas = new ArrayList<>();
        //PRIMERO HAY QUE VERIFICAR SI ESTE CLIENTE TIENE O NO RESERAVA GENERADA
        //SI TIENE HAY QUE OBTENER EL codReserva, y VERIFICAR QUE LAS FECHAS DE RESERVAS NO CHOQUEN
        Cliente cliente = s.buscarClientePorCodAsignado("Cliente", cod);
        cliente.mostrarReservaPorCod(); //lista todas la reservas de este cliente

        if (s.mostrarPorTipoDeColeccion("Oficina")) { //se verifica que hay al menos un Oficina creada por el Admin, y se muestran
            String codOficinaReserva = Consola.leerCadena("Asignar Oficina de Reserva,'Of1','Of2', etc:");
            Coleccionable oficina = s.obtenerColeccionablePorCodigo("Oficina", codOficinaReserva);
            char salir = 'S';
            while (oficina == null && (salir == 'S' || salir == 's')) { //vuelve a pedir codOficina hasta que coincida o se sale de Reserva
                Consola.mostrarCadena("La Oficina ingresada no existe");
                salir = Consola.siNo("Quiere continuar con la Reserva? Si(S)  No(N)");
                if ((salir == 'S' || salir == 's')) {
                    codOficinaReserva = Consola.leerCadena("Ingresar otra Oficina:'Of1','Of2', etc:");
                    oficina = s.obtenerColeccionablePorCodigo("Oficina", codOficinaReserva);
                } else {
                    continuar = false;
                }
            }
            if (continuar) {//en este punto se eligio una oficina del listado, osea disponible, ahora hay que ver que esta oficina tenga autos
                //se verifica que la Oficina selecciona tenga Autos
                // Consola.mostrarCadena("Autos de la Oficina ");
                if (verificarSiHayAutosPorOficina(s, codOficinaReserva)) {

                    //Listar disponibles y traer: Autos con FechaIniNuevaReserva > FechaFinAnteriorReseerva si tienen Reserva, y los que no tienen Reserva
                    //falta validar que fecha se ingrese en el formato indicado, supongo que se entra con el formato correcto
                    String fechaI = Consola.leerCadena("ingrese la fecha inicio de la Reserva, con formato DD/MM/YYYY \n");
                    String fechaF = Consola.leerCadena("ingrese la fecha de fin de la Reserva, con formato DD/MM/YYYY \n");

                    //si NO se ingresa en el formato indicado se rompe!!
                    fechaInicio = LocalDate.parse(fechaI, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    fechaFin = LocalDate.parse(fechaF, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    ArrayList<Observer> listaAutosDisponibles = listarAutosDisponiblesPorOficina(s, codOficinaReserva, fechaInicio);

                    int k = 0;
                    Consola.mostrarCadena("Autos disponibles para la oficina: ");
                    //cuando se hace una reserva y se listan las autos de la oficina, excluir a los que tengan
                    //oficina de reserva !=null, ya que son autos alquilado y devuelvo en una oficina que es su oficina de reserva
                    for (Observer a : listaAutosDisponibles) {
                        if (((Auto) a).getOficinaReserva() == null || ((Auto) a).getOficinaReserva().getCodOficina().equals(codOficinaReserva)) {
                            k = a.mostrar("Auto");
                            // a.mostrar("Auto");
                        } else {
                            Consola.mostrarCadena("La Oficina no tiene Autos disponibles!!!"); //VER sale donde no tiene que salir
                        }
                    }
                    if (k > 0) { //***
                        char continua = Consola.siNo("Quiere continuar con Reserva?  Si(S)  No(N):");
                        if (continua == 'S' || continua == 's') {
                            while (!masAutos) {
                                String patenteReserva = Consola.leerCadena("Ingrese la patente del Auto a Reservar: ");
                                Auto auto = (Auto) s.obtenerColeccionablePorCodigo("Auto", patenteReserva);
                                int i = 0;
                                boolean libre = false;
                                while (i < listaAutosDisponibles.size() && !libre) {
                                    if (auto.getPatente().equals(((Auto) listaAutosDisponibles.get(i)).getPatente())) {
                                        //Consola.mostrarCadena("Coincide ############");
                                        libre = true; //patente de Auto ingresada coincide con patente de auto de la lita de elegidos
                                        listaAutosDisponibles.remove(i);//Auto a ser reservado ya esta disponible si se quiere agregar + autos
                                    } else {
                                        patenteReserva = Consola.leerCadena("Auto no disponible, Ingrese otra patente del Auto a Reservar: ");
                                        libre = false;
                                    }
                                    i++;
                                }

                                if (libre) {
                                    //por cada auto agregado se necesita su precio, para luego sumar todo al precioTotal de la Reserva
                                    precioParcial = auto.getPrecio() * cantidadDias(fechaInicio, fechaFin) + precioParcial;
                                    //se van guardando las patentes de los autos reservados
                                    patentesReservadas.add(patenteReserva); //para poder setear atrributos de auto reservado
                                } else {
                                    Consola.mostrarCadenaEnter("El Auto con patente : " + patenteReserva + " no existe o no esta disponible, [ENTER] para continuar");
                                }
                                char seguir = Consola.siNo("Quiere agregar otro Auto a la Reserva , Si(S)  No(N):");
                                if (seguir == 'S' || seguir == 's') {
                                    masAutos = false;
                                } else {
                                    masAutos = true;
                                    continua = 'N';
                                }

                            }

                            String codReserva = Consola.leerCadena("Asignar código a esta Reserva: Reserva01, Reserva02, etc:");
                            boolean encontrado = verificarSiExisteReserva(s, codReserva);

                            while (encontrado) {
                                codReserva = Consola.leerCadena("Ya existe una reserva con este código, ingrese otro código para asignar\n");
                                encontrado = verificarSiExisteReserva(s, codReserva);
                            }
                            int precioTotal = precioParcial;

                            // se crea la reserva y agrega a la colección
                            boolean creada = s.altaReserva(fechaInicio, fechaFin, false, codReserva, precioTotal);
                            if (creada) {
                                Consola.mostrarCadena("#######  RESERVA CREADA #######");
                            }

                            //hay que Setter los atributos de los Autos Reservados, como "codReserva",  "oficinaReserva", "oficinaDestino"
                            // y a la Reserva hay que setearle el atributo entrega=false en el caso de Alta Reserva, entraga=true al bajar Reserva
                            String codOficinaDestino = Consola.leerCadena("\nIngrese el código de la oficina de Destino(Of1, Of2, ect :\n");
                            Oficina oficinaDestino = (Oficina) s.obtenerColeccionablePorCodigo("Oficina", codOficinaDestino);
                            Auto autoReservado = null;
                            for (String p : patentesReservadas) {//SETEO ATRIBUTOS DE LOS AUTOS
                                autoReservado = ((Auto) s.obtenerColeccionablePorCodigo("Auto", p)); //Auto reservado
                                autoReservado.setCodReserva(codReserva);

                                //SE AGREGAR A ESTE AUTO COMO OBSERVADOR DE LA OFICINA DESTINO, ASI ESTA LE AVISA.....
                                oficinaDestino.agregarObservador((Auto) s.obtenerColeccionablePorCodigo("Auto", p));
                                //es oficina destino le avisa al auto reservado que ella(this) es su oficina destino, y para avisarle
                                //primero debe agregarlo com observador

                                ((Oficina) oficinaDestino).avisarCambioOficinaDestino();//setea Oficina Destino en el Auto
                                ((Oficina) oficina).avisarCambioOficinaReserva();//setea Oficina Reserva en el Auto    
                            }

                            //se muestran los datos de la reserva realizada
                            Consola.mostrarCadena("\nReserva realizada correctamente:\n");
                            Reserva reserva = (Reserva) s.obtenerColeccionablePorCodigo("Reserva", codReserva);

                            for (String p : patentesReservadas) { //carga de Autos a la Reserva
                                reserva.agregarAuto((Auto) s.obtenerColeccionablePorCodigo("Auto", p));
                            }

                            /*   //agregar Reserva a cliente generra error, ya que no se carga la reserva del cliente
                          //Se le asigna la Reserva al Cliente
                       cliente.agregarReserva(reserva);
                       
                             */
                            Consola.mostrarCadena("El código de Reserva es : " + codReserva + "\nFecha de inicio: " + reserva.getFechaIni() + "\nFecha de fin: " + reserva.getFechaFin());
                            Consola.mostrarCadena("Patente    Marca    Modelo    Color      Combustible     Oficina de Origen"
                                    + "     Oficina de Reserva     Oficina de Destino      Precio\n");

                            for (Auto a : reserva.getAutos()) {
                                Consola.mostrarCadena(a.getPatente() + "      " + a.getMarca() + "     " + a.getModelo() + "      " + a.getColor() + "         " + a.getLtsCombus()
                                        + "               " + a.getOficinaOrigen().getCodOficina() + "                   " + a.getOficinaReserva().getCodOficina() + "                    "
                                        + a.getOficinaDestino().getCodOficina() + "                   " + a.getPrecio());
                            }
                            Consola.mostrarCadena("------------------------------------------------------------------------------------------------------------------------"
                                    + "--------------------------------------------------\n\n");
                            Consola.mostrarCadena("Precio total $" + reserva.getPrecioTotal());
                        }
                    }//*****
                } else {
                    Consola.mostrarCadena("La Oficina seleccionada no tiene ningún Auto, el administrador debe agrega autos a esta Oficina!! ");
                    continuar = false;
                }
            }

        } else {
            Consola.mostrarCadena("Aún no existen Oficinas, el Administrador debe dar de Alta una Oficina");
        }//aca debería terminat cargarRserva !!!!!!
    }
        
        
   
   //por cada cliente busco en sus reservas si exite este codReserva
   //sale del ciclo ni bien encuentra a codReserva     
    private boolean verificarSiExisteReserva(Sistema s, String codReserva) {
        ArrayList<Coleccionable> listaCliente = s.separarEnTipoColeccion("Cliente");
        // ArrayList<Coleccionable> listaCliente=s.separarColeccion("Cliente");
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < listaCliente.size()) {
            encontrado = ((Cliente) listaCliente.get(i)).buscarPorCodReserva(codReserva);
            i++;
        }
        return encontrado;
    }
        
    
     //disponible: cuando su atributo codReserva=null o cuando FechaIncialNuevaReserva > FechaFinalAnteriorReserva
    //si no hay reservas en una oficina se debería listar todos los autos de esa Oficina como dsiponibles
     //1° se listan todos los Autos sin reservas, y si tienen reserva se verifica fecha y se listan
     ArrayList<Observer> listarAutosDisponiblesPorOficina(Sistema s, String codOficina, LocalDate fecha) {//disponible: cuando su atributo codReserva=null
        Oficina oficina = (Oficina) s.obtenerColeccionablePorCodigo("Oficina", codOficina);
        ArrayList<Observer> elegidos1;
        elegidos1 = oficina.obtenerObservadoresSinReserva();
        ArrayList<Observer> elegidos2;
        elegidos2 = oficina.obtenerObservadoresConReserva(fecha);
        ArrayList<Observer> elegidos;
        elegidos = oficina.AutosElegidos(elegidos1, elegidos2);
        return elegidos; //lista Observadores(Autos) 
    }
     
      
    private boolean verificarSiHayAutosPorOficina(Sistema s, String codOficina) {
        Oficina oficina = (Oficina) s.obtenerColeccionablePorCodigo("Oficina", codOficina);
        boolean conAutos = false;
        conAutos = oficina.verificarSiTenesObservadores();

        for (Observer o : oficina.getObservadores()) {
            o.mostrar("Auto");
        }
        return conAutos;
    }

    int cantidadDias(LocalDate fechaI, LocalDate fechaF) {
        int dias = 0;
        dias = (int) DAYS.between(fechaI, fechaF) + 1;
        return dias;
    }
        
      
      
    private void finalizarReserva(Sistema s) {
        // CUANDO EL CLIENTE DEVUELVE EL AUTO EN LA OFICINA DESTINO
        // se setean los setters de los autos reservado
        //se elimina de la lista de reserva de la oficina de reserva , la reseva con codReserva
        Consola.limpiarBuffer();
        String codReserva = Consola.leerCadena("Ingrese el codReserva a dar de baja:\n\n");
        Reserva reserva = (Reserva) s.obtenerColeccionablePorCodigo("Reserva", codReserva);
        if (reserva != null) {
            for (Auto a : reserva.getAutos()) {
                a.setCodReserva(null);
                //UNA VEZ FINALIZADA LA RESERVA, EL AUTO QUEDA EN LA OFICINA DESTINO, ESTA OFICINA PASA A SER LA OFICINA DE RESERVA
                //Y SE TIENE QUE SETEAR OFICINA DESTINO EN NULL. 
                a.getOficinaDestino().avisarCambioOficinaReserva(); //se setea oficina de destino como oficina reserva

                //El Auto pasa a ser observador de la Oficina Destino, y deja de ser para la Oficina de Origen
                //sino no se va a poder reserva en la oficina destino donde quedo libre
                a.getOficinaDestino().agregarObservador(a);
                //s.agregarAutoEnOficina(a.getOficinaDestino().getCodOficina(), a.getPatente());

                a.getOficinaDestino().avisarObservadoresSinOficinaDestino(); //oficina de destino se setea en null

                a.getOficinaOrigen().sacarObservador(a);

                //ADEMAS CUANDO SE HACE UNA RESERVA HAY QUE PREGUNTAR SI OFICINA DE RESERVA ES NULL O NO, YA QUE PUEDE SER UN AUTO
                //ALQUILADO EN UNA OFICINA QUE ES DE ORIGEN , OSEA ALQUILADO EN UNA OFICINA QUE FUE SUS DESTINO
                //FALTARIA HACER UNA FUNCION PARA CUANDO EL EL AUTO SE DEVUELVA SU OFICINA DE ORIGEN
            }
            if (s.bajar(reserva)) {
                Consola.mostrarCadena("Reserva finalizada correctamente!!\n\n");
                Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Vendedor\n");
            }
        } else {
            Consola.mostrarCadena("No existe reservas con código :" + codReserva);
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Vendedor\n");
        }
    }
     
     
    private void listarReservas(Sistema s) {
        Consola.limpiarBuffer();
        if (s.mostrarPorTipoDeColeccion("Reserva")) {
            Consola.limpiarBuffer();
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Vendedor\n");
        } else {
            Consola.mostrarCadena("No hay Reservas registradas en el Sistema!!!");
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Vendedor\n");
        }
    }
     
     
    private void cancelarReserva(Sistema s){
     /////
     }   
     
}
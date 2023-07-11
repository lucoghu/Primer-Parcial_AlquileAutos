package alquilerautos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Administrador extends Gestor<Administrador> implements Serializable {

    public Administrador(int dni, String nombre, String direccion, int telefono, String usuario, String clave) {
        super(dni, nombre, direccion, telefono, usuario, clave);
    }

    public Administrador() {//se necesita para usar Factory con reflection
    }

    
    @Override
    public boolean ingresar(Sistema s) { //Sale del Sistema por este menú, y no por un submenú
        boolean continuar = false;
        boolean salir = false;
        int op;
        while (!salir) {
            Consola.mostrarCadena("\n**** Menú del Administrador *****\n\n");
            Consola.mostrarCadena("[1] Dar de Alta Usuario");
            Consola.mostrarCadena("[2] Dar de Baja Usuario");
            Consola.mostrarCadena("[3] Dar de Alta Auto");
            Consola.mostrarCadena("[4] Dar de Baja Auto");
            Consola.mostrarCadena("[5] Alta Oficina");
            Consola.mostrarCadena("[6] Baja Oficina");
            Consola.mostrarCadena("[7] Agregar Auto a Oficina Origen");
            Consola.mostrarCadena("[8] Listar Autos");
            Consola.mostrarCadena("[9] Listar Usuarios");
            Consola.mostrarCadena("[10] Listar Oficinas");
            Consola.mostrarCadena("[11] Devolver Oficina de Origen");
            Consola.mostrarCadena("[12] Salir");
            Consola.mostrarCadena("[13] Salir del Sistema\n");
            // Consola.mostrarCadena("[x] Baja Oficina");
            op = Consola.leerEntero();

            switch (op) {
                case 1:
                    altaUsuario(s);
                    break;
                case 2:
                    bajaUsuario(s);
                    break;
                case 3:
                    altaAuto(s);
                    break;
                case 4:
                    bajaAuto(s);
                    break;
                case 5:
                    altaOficina(s);
                    break;
                case 6:
                    bajarOficina(s);
                    break;
                case 7:
                    agregarAutoEnOficina(s);
                    break;
                case 8:
                    listarAutos(s);
                    break;
                case 9:
                    listarUsuarios(s);
                    break;
                case 10:
                    listarOficinas(s);
                    break; 
                 case 11:
                    devolverAutoOficinaOrigen(s);
                    break;
                case 12:
                    continuar = true;
                    salir = true;
                    break;
                case 13:
                    continuar = false;
                    salir = true;
                    break;
                default:
                    Consola.mostrarCadena("Opción no válida");
                    break;
            }
            if (op >= 1 && op < 12) {
                salir = false;
            }
        }
        Consola.limpiarBuffer();
        return continuar; // el administrador es el único que puede salir del sistema
    }

    
    public void altaUsuario(Sistema s) { //no puede ir en Gestor, porque Admin da de alta/baja usuarios, y Vendedor clientes
        boolean salirMenu = false;
        String tipoUsuario = "";
        Consola.mostrarCadena("Ingrese que tipo de tipo de Usuario quiere dar de alta: ?");
        Consola.mostrarCadena("[1] Cliente\n [2] Vendedor\n [3] Administrador\n [4] Salir de Alta de Usuario");
        int op = Consola.leerEntero();
        while (!salirMenu) {
            switch (op) {
                case 1:
                    tipoUsuario = "Cliente";
                    break;
                case 2:
                    tipoUsuario = "Vendedor";
                    break;
                case 3:
                    tipoUsuario = "Administrador";
                    break;
                case 4:
                    salirMenu = true;
                    break;
                default:
                    Consola.mostrarCadena("Opción no válida");
                    break;
            }
            if (1 < op && op < 4) {
                Usuario u = super.altaUsuario(tipoUsuario, s);
                if (u != null) {
                    s.agregarAColeccion(u);
                    Consola.mostrarCadena("Usuario dado de alta exitosamnete!!\n\n");
                } else {
                    Consola.mostrarCadena("No se pudo dar de alta al Usuario!!\n\n");
                }
            }
            if (op == 1) {
                Cliente cliente = (Cliente) super.altaUsuario("Cliente", s);//dentro altaUsuario llama a: asignarCodUni(u)
                if (cliente != null) {
                    int codVerificar = Consola.leerEntero("Asignar código a este Cliente, debe ser un ENTERO MAYOR A CERO\n\n");
                    if (super.asignarCodigoAlCliente(cliente, codVerificar)) {
                        s.agregarAColeccion(cliente);
                    }
                } else {
                    Consola.mostrarCadena("No se pudo dar de alta al Cliente!!\n\n");
                }
            }
            salirMenu = true;
        }
    }

    
    
    public void bajaUsuario(Sistema s) {//no puede ir en Gestor, porque Admin da de alta/baja usuarios, y Vendedor clientes
        boolean salirMenu = false;
        String tipoUsuario = "";
        Consola.mostrarCadena("¿Qué tipo de usuario quiere dar de baja ?:\n");
        Consola.mostrarCadena(" [1] Cliente\n [2] Vendedor\n [3] Administrador\n [4] Salir de Baja de Usuario\n");
        int op = Consola.leerEntero();

        while (!salirMenu) {
            switch (op) {
                case 1:
                    tipoUsuario = "Cliente";
                    break;
                case 2:
                    tipoUsuario = "Vendedor";
                    break;
                case 3:
                    tipoUsuario = "Administrador";
                    break;
                case 4:
                    salirMenu = true;
                    break;
                default:
                    Consola.mostrarCadena("Opción no válida\n");
                    break;
            }

            if (1 <= op && op < 4) {
                boolean volver = false;
                if (s.mostrar1(tipoUsuario)) {
                    Consola.limpiarBuffer();
                    String usu = Consola.leerCadena("\nIntroduza el usuario a dar de baja");
                    Coleccionable c;
                    c = s.obtenerColeccionablePorCodigo(tipoUsuario, usu);
                    if (c != null) { //verifica que se encuentre registrado
                        if (s.bajar(c)) {  //eliminar de la lista de usuarios
                            Consola.mostrarCadena("El Usuario fue dado de baja\n");
                            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
                        } else {
                            Consola.mostrarCadena("El Usuario no pudo ser dado de baja\n");

                        }
                    } else {
                        Consola.mostrarCadena("El usuario al que quiere dar de baja no existe\n");
                        Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
                    }
                } else {
                    Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
                }
            }
            salirMenu = true;
        }
    }
    
    
    @Override
    public void agregarSiEs(String tipoUsuario, Collection<Administrador> listadoAdministradores) {
        if ("Administrador".equals(tipoUsuario)) {
            listadoAdministradores.add(this);
        }
    }

    
    @Override
    public int mostrar(String tipoAdministrador) {
        int i=0;
        if ("Administrador".equals(tipoAdministrador)) {
            System.out.print(super.toString("Administrador")+"\n");
            i++;
        }
        return i;
    }
    
 
    
    @Override
    public boolean buscar(String codigo){
         boolean esEste=false;
        if (codigo.equals(super.getUsuario())) {
             esEste=true;
        }
        return esEste;
    }
    
  
    //Al dar de alta un Auto no se le asigna OficinaDeOrigen, se le asigna cuando se crea la Oficina
    //El vendedor le asigna el código de oficina de reserva y de destino al hacer la reserva
    public void altaAuto(Sistema s) {
        boolean salirMenu = false;
        boolean pedirDato = true;
        boolean altaOk = false;
        String modelo = "";
        String colorAuto = "";
        String marcaAuto = "";
        String oficinaOrigen = "";
        int litros = 0;
        int precio = 0;
        Consola.limpiarBuffer();
        String p = Consola.leerCadena("Ingrese la Patente del Auto quiere dar de alta :");
        //verifico si existe el auto con esa patente
        if (s.obtenerColeccionablePorCodigo("Auto", p) == null) {
            modelo = Consola.leerCadena("Ingrese el modelo: ");
            Consola.mostrarCadena("\nIngrese el color del auto: \n");
            Consola.mostrarCadena("[1] Blanco\n [2] Negro\n [3] Gris\n [4] Rojo\n [5] Azul\n");
            int op = Consola.leerEntero();
            while (pedirDato) {
                switch (op) {
                    case 1:
                        colorAuto = "BLANCO";
                        break;
                    case 2:
                        colorAuto = "NEGRO";
                        break;
                    case 3:
                        colorAuto = "GRIS";
                        break;
                    case 4:
                        colorAuto = "ROJO";
                        break;
                    case 5:
                        colorAuto = "AZUL";
                        break;
                    default:
                        Consola.mostrarCadena("Opción no válida");
                        pedirDato = true;
                        break;
                }

                if (1 <= op && op < 6) {
                    pedirDato = false;
                }
            }
            Consola.mostrarCadena("Ingrese la marca del auto: \n");
            Consola.mostrarCadena("[1] FORD\n [2] CHEVROLET\n [3] FIAT\n [4] VOLKSWAGEN\n");
            pedirDato = true;
            int op1 = Consola.leerEntero();
            while (pedirDato) {
                switch (op1) {
                    case 1:
                        marcaAuto = "FORD";
                        break;
                    case 2:
                        marcaAuto = "CHEVROLET";
                        break;
                    case 3:
                        marcaAuto = "FIAT";
                        break;
                    case 4:
                        marcaAuto = "VOLKSWAGEN";
                        break;
                    default:
                        Consola.mostrarCadena("Opción no válida");
                        pedirDato = true;
                        break;
                }

                if (1 <= op1 && op1 < 5) {
                    pedirDato = false;
                }

            }
            litros = Consola.leerEntero("Ingrese la cantidad litros inciaciales de combustible :");
            precio = Consola.leerEntero("Ingrese el precio de Alquiler del Auto : ");

        } else {
            Consola.mostrarCadena("Este auto ya se encuentra registrado ");
        }
        Auto autoCreado = s.altaAuto(p, modelo, colorAuto, marcaAuto, litros, precio);
        autoCreado.setOficinaOrigen(null);
        if (autoCreado != null) {
            s.mostrarPorTipoDeColeccion("Auto");
            Consola.mostrarCadena("Auto registrado correctamente!!! \n");
            Consola.mostrarCadena("Recuerde que al Auto hay que agregarlo a una Oficina para se alquilado!!! \n\n");
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
        }
    }
    
    
    
    
    private void bajaAuto(Sistema s) {
        String patente = "";
        boolean bajaOk = false;
        if (s.mostrar1("Auto")) {
            Consola.limpiarBuffer();
            patente = Consola.leerCadena("Ingrese la patente del Auto que quiere dar de baja :");
            if (s.obtenerColeccionablePorCodigo("Auto", patente) != null) { //verifica que se encuentre registrado
                bajaOk = s.bajar(s.obtenerColeccionablePorCodigo("Auto", patente));  //eliminar de la lista de autos
            } else {
                Consola.mostrarCadena("El Auto al que quiere dar de baja no existe*****\n");
                bajaOk = false;
                Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
            }
            if (bajaOk) {
                Consola.mostrarCadena("Auto dado de baja correctamente!!! \n");
                Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
            }
        } else {
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
        }
    } //vuelve al menú del adminstrador
    
    
    //la gestion de la oficinas la hace el administrador
    private void altaOficina(Sistema s) {
        boolean salirMenu = false;
        boolean pedirDato = true;
        boolean altaOk = false;
        int telefono = 0;
        String correo = "";
        String domicilio = "";
        Consola.limpiarBuffer();
        String codOficina = Consola.leerCadena("Ingrese el código de la Oficina que quiere dar de alta : ");
        //verifico si existe una Oficina con ese con código
        // s.verSiExisteEnColeccion(codOficina); //trae un objeto Oficina visto como un coleccionable
        if (s.obtenerColeccionablePorCodigo("Oficina", codOficina) == null) { //falta validar datos
            telefono = Consola.leerEntero("Ingrese el teléfono de la Oficina " + codOficina);
            correo = Consola.leerCadena("Ingrese el correo de la Oficina " + codOficina);
            domicilio = Consola.leerCadena("Ingrese el domicilio de la Oficina " + codOficina);

            //Se usa el Patron Observer, que ante el evento agregar Auto a una oficina, este Auto Setee su
            // atributo oficinaOrigen a esta oficina que lo está agregando a su lista de Autos         
        } else {
            Consola.mostrarCadena("Esta Oficina ya existe!!\n\n");
            salirMenu = true;
        }
        if (!salirMenu) {
            altaOk = s.altaOficina(codOficina, telefono, correo, domicilio);
            if (altaOk) {
                Consola.mostrarCadena("\nOficina dada de Alta correctamente!!!\n");
                s.mostrar1("Oficina");
                Consola.mostrarCadena("Recuerde que hay que agregar Autos a esta Oficina!!! \n\n");
                Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
            }
        }
    }

     
    private void agregarAutoEnOficina(Sistema s) {// ver que pasa si se agrega un auto con oficina origen ya asignada{
        if (s.mostrarPorTipoDeColeccion("Oficina")) {
            Consola.limpiarBuffer();
            if (s.verificarSiHayColeccionable("Auto")) {//1° hay que verificar que haya autos dados de alta para agreagar, y validar en caso de que no hayan autos
                String codOficina = Consola.leerCadena("Ingrese el código de la Oficina para agregar un Auto:");
                if (s.obtenerColeccionablePorCodigo("Oficina", codOficina) == null) {
                    Consola.mostrarCadena("No existe Oficina con el código " + codOficina);
                    Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
                } else {
                    s.mostrarPorTipoDeColeccion("Auto");
                    String p = Consola.leerCadena("Ingrese la Patente del Auto quiere agregar a la Oficina " + codOficina + ": ");
                    if (verificarExisteAutoSinOficina(s, p)) {
                        s.agregarAutoEnOficina(codOficina, p);
                        Consola.mostrarCadena("El Auto con patente "+p+" fue agregado a la Oficina "+codOficina);
                        Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
                    } else {
                        Consola.mostrarCadena("No existe Autos con esta Patente o el Auto ya está asignado a una Oficina");
                        Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
                    }
                }
            } else {
                Consola.mostrarCadena("No existe Autos para agregar a una Oficina, primero dar de alta un Auto");
                Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
            }
        } else {
            Consola.limpiarBuffer();
            Consola.mostrarCadena("No existen Oficinas!!!");
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
        }
    }
    
    
    private void bajarOficina(Sistema s) { //falto asegurar que el auto no este reservado al bajar una oficina
        String codOficina = "";
        boolean bajaOk = false;
        if (s.mostrar1("Oficina")) {
            Consola.limpiarBuffer();
            codOficina = Consola.leerCadena("Ingrese el código de la Oficina que quiere dar de baja :");
            if (s.obtenerColeccionablePorCodigo("Oficina", codOficina) != null) { //verifica que se encuentre registrado
                //la oficina debe avisarles a sus observadores que ya no va a existir, es decir su oficinaOrigen= null
                //buscar todos los autos perteneciente a la oficina a bajar
                if (s.sacarAutosOficina(codOficina)) {
                    bajaOk = s.bajar(s.obtenerColeccionablePorCodigo("Oficina", codOficina));  //eliminar de la lista de Oficinas
                } else {
                    Consola.mostrarCadena("No se pudo retirar autos de la oficina " + codOficina);
                }
            } else {
                Consola.mostrarCadena("La Oficina a la que quiere dar de baja no existe*****\n");
                bajaOk = false;
                Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
            }
            if (bajaOk) {
                Consola.mostrarCadena("Oficina dada de baja correctamente!!! \n");
                Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
            }
        } else {
            Consola.mostrarCadenaEnter("No existen Oficinas en el Sistema\n");
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
        }
    }
    
    
  private boolean verificarExisteAutoSinOficina(Sistema s, String patente) {
        boolean valido = false;
        Auto a;
        ArrayList<Coleccionable> lista = s.separarColeccion("Auto");
        for (Coleccionable c : lista) {
            a = (Auto) c;
            if ((a.getPatente()).equals(patente) && a.getOficinaOrigen() == null) {
                valido = true;
            }
        }
        return valido;
    }
    
    
    private void listarAutos(Sistema s) {
        Consola.limpiarBuffer();
        if (s.mostrarPorTipoDeColeccion("Auto")) {
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
        } else {
            Consola.mostrarCadena("No hay Autos registrados en el Sistema!!!");
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
        }
    }
    
    
    private void listarOficinas(Sistema s) {
        Consola.limpiarBuffer();
        if (s.mostrarPorTipoDeColeccion("Oficina")) {
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
        } else {
            Consola.mostrarCadena("No hay Oficinas registradas en el Sistema!!!");
            Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
        }
    }
     
     
    private void listarUsuarios(Sistema s) {
        Consola.mostrarCadena("***Listado de Clientes***\n");
        if(!s.mostrarPorTipoDeColeccion("Cliente")){
           Consola.mostrarCadena("No hay Clientes registrados en el Sistema!!!\n");
        }
        Consola.mostrarCadena("***Listado de Vendedores***\n");
        if(!s.mostrarPorTipoDeColeccion("Vendedor")){
         Consola.mostrarCadena("No hay Vendedores registrados en el Sistema!!!\n");
        }
        Consola.mostrarCadena("***Listado de Administradores***\n");
        if(!s.mostrarPorTipoDeColeccion("Administrador")){
         Consola.mostrarCadena("No hay Adnministradores registrados en el Sistema!!!\n");
        }
            
        Consola.mostrarCadenaEnter("Presione ENTER para volver al Menú del Administrador\n");
        Consola.limpiarBuffer();
    }
    
    
    private void devolverAutoOficinaOrigen(Sistema s) {
        /////
        //hay que hacer que sea otra vez observador de la oficina de origen
        //y que deje de serlo de la oficina reserva(destino del alquiler anterior) donde se devolvio luego de ser alquilado
    }
}

package alquilerautos;

import java.util.Collection;

public class Administrador extends Gestor<Administrador> {

    public Administrador(int dni, String nombre, String direccion, int telefono, String usuario, String clave) {
        super(dni, nombre, direccion, telefono, usuario, clave);
    }

    @Override
    public boolean ingresar() { //Sale del Sistema por este menú, y no por un submenú
        boolean noContinuar = false;
        boolean salir = false;
        int op;
        while (!salir) {
            Consola.mostrarCadena("\n**** Menú del Administrador *****\n\n");
            Consola.mostrarCadena("[1] Dar de Alta Usuario\n\n");
            Consola.mostrarCadena("[2] Dar de Baja Usuario\n\n");
            Consola.mostrarCadena("[3] Dar de Alta Auto\n\n");
            Consola.mostrarCadena("[4] Dar de Baja Auto\n\n");
            Consola.mostrarCadena("[5] Alta Oficina\n\n");
            Consola.mostrarCadena("[6] Agregar Auto a Oficina Origen");
            Consola.mostrarCadena("[7] Salir del Sistema\n\n");
            // Consola.mostrarCadena("[x] Baja Oficina");
            // Consola.mostrarCadena("[x] Agregar Auto a Oficina Origen");
            op = Consola.leerEntero();

            switch (op) {
                case 1:
                    altaUsuario();
                    break;
                case 2:
                    bajaUsuario();
                    break;
                case 3:
                    altaAuto();
                    break;
                case 4:
                    bajaAuto();
                    break;
                case 5:
                    altaOficina();
                    break;
                case 6:
                    agregarAutoEnOficina();
                    break;
                case 7:
                    noContinuar = true;
                    salir = true;
                    break;
                default:
                    Consola.mostrarCadena("Opción no válida");
                    break;
            }
            if(1<=op && op<7){
            salir=false;
            }
        }
        return noContinuar; // el administrador es el único que puede salir del sistema
    }

    public void altaUsuario() { //no puede ir en Gestor, porque Admin da de alta/baja usuarios, y Vendedor clientes
        Sistema s = new Sistema();
        boolean salirMenu = false;
        String tipousuario = Consola.leerCadena("Ingrese que tipo de tipo de Usuario quiere dar de alta: ?\n\n");
        String tipoUsuario = "";
        Consola.mostrarCadena("[1] Cliente\n [2] Vendedor\n [3] Administrador\n [4] Salir de Alta de Usuario\n");
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
                if (super.altaUsuario(tipoUsuario) != null) {
                    Consola.mostrarCadena("Usuario dado de alta exitosamnete!!\n\n");
                }else{Consola.mostrarCadena("No se pudo dar de alta al Usuario!!\n\n");}
            }
            if (op == 1) {
                Cliente cliente = (Cliente) super.altaUsuario("Cliente");
                if (cliente != null) {
                    int codVerificar = Consola.leerEntero("Asignar código a este Cliente, debe ser un ENTERO MAYOR A CERO\n\n");
                    super.asignarCodigoAlCliente(cliente,codVerificar);
                }else{Consola.mostrarCadena("No se pudo dar de alta al Cliente!!\n\n");}
            }
            salirMenu = true;
        }
    }

    
    public void bajaUsuario() {//no puede ir en Gestor, porque Admin da de alta/baja usuarios, y Vendedor clientes
        Sistema s = new Sistema();
        boolean salirMenu = false;
        String tipoUsuario = "";
        Consola.mostrarCadena("¿Qué tipo de usuario quiere dar de baja ?\n\n: ");
        Consola.mostrarCadena("[1] Cliente\n [2] Vendedor\n [3] Administrador\n [4] Salir de Baja de Usuario\n\n");
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
        }

        if (op != 4) {
            s.mostrar1(tipoUsuario);
            String usu = Consola.leerCadena("Intruduza el usuario a dar de baja\n");
            if (s.obtenerColeccionablePorCodigo("Usuario", usu) != null) { //verifica que se encuentre registrado
                if (s.bajar(s.obtenerColeccionablePorCodigo("Usuario", usu))) {  //eliminar de la lista de usuarios
                    Consola.mostrarCadena("El Usuario fue dado de baja\n");
                } else {
                    Consola.mostrarCadena("El Usuario no pudo ser dado de baja\n");
                }
            } else {
                Consola.mostrarCadena("El usuario al que quiere dar de baja no existe\n");
            }
        }
    }

    
    
    @Override
    public void agregarSiEs(String tipoUsuario, Collection<Administrador> listadoAdministradores) {
        if ("Administrador".equals(tipoUsuario)) {
            listadoAdministradores.add(this);
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
    
   
    //Al dar de alta no tiene oficinaOrigen, se le asigna cuando el Administrador crea la Oficina
   //el vendedor le asigna el código de oficina de Reserva y de Destino al hacer la reserva
    public void altaAuto() {//falta implementa while para seguir o salir del menu
        Sistema s = new Sistema();
        boolean salirMenu = false;
        boolean pedirDato = true;
        boolean altaOk = false;
        String modelo = "";
        String colorAuto = "";
        String marcaAuto = "";
        int litros = 0;
        int precio = 0;

        String p = Consola.leerCadena("Ingrese la Patente del Auto quiere dar de alta \n\n: ");
        //verifico si existe el auto con esa patente
        if (s.obtenerColeccionablePorCodigo("Auto", p) == null) { //falta validar datos
            modelo = Consola.leerCadena("Ingrese el modelo: \n\n");
            Consola.mostrarCadena("Ingrese el color del auto: \n\n");
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
                    case 6:
                        salirMenu = true;
                        break;
                    default:
                        Consola.mostrarCadena("Opción no válida");
                        pedirDato = true;
                        break;
                }

                if (op <= 1 && op < 6) {
                    pedirDato = false;
                }

                Consola.mostrarCadena("Ingrese la marca del auto: \n\n");
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

                    if (op1 <= 1 && op1 < 4) {
                        pedirDato = false;
                    }
                }
                litros = Consola.leerEntero("Ingrese la cantidad litros inciaciales de combustible:\n");
                precio = Consola.leerEntero("ingrese el precio de Alquiler del Auto\n\n");
            }

        } else {
            Consola.mostrarCadena("Este auto ya se encuentra registrado ");
        }
        altaOk = s.altaAuto(p, modelo, colorAuto, marcaAuto, litros, precio);
        if (altaOk) {
            Consola.mostrarCadena("Auto registrado correctamente ");
        }
    }
    
    
    
    public void bajaAuto() {
       Sistema s = new Sistema();
       String patente="";
       boolean bajaOk=false;
       s.mostrar1("Auto");
       patente=Consola.leerCadena("Ingrese la patente del Auto que quiere dar de baja : \n");
        if (s.obtenerColeccionablePorCodigo("Auto", patente) != null) { //verifica que se encuentre registrado
                bajaOk=s.bajar(s.obtenerColeccionablePorCodigo("Auto", patente));  //eliminar de la lista de autos
            } else {
                Consola.mostrarCadena("El Auto al que quiere dar de baja no existe\n");
            }
        if(bajaOk){
         Consola.mostrarCadena("Auto dado de baja correctamente \n\n");
        }
    } //vuelve al menú del adminstrador
    
    
    
     public void altaOficina() {//falta implementa while para seguir o salir del menu
        Sistema s = new Sistema();
        boolean salirMenu = false;
        boolean pedirDato = true;
        boolean altaOk = false;
        int telefono = 0;
        String correo = "";
        String domicilio = "";
        String codOficina = Consola.leerCadena("Ingrese el código de la Oficina que quiere dar de alta \n\n: ");
        //verifico si existe una Oficina con ese con código
        // s.verSiExisteEnColeccion(codOficina); //trae un objeto Oficina visto como un coleccionable
        if (s.obtenerColeccionablePorCodigo("Oficina", codOficina) == null) { //falta validar datos
            telefono = Consola.leerEntero("Ingrese el teléfono de la Oficina :" + codOficina + " \n\n");
            correo = Consola.leerCadena("Ingrese el correo de la Oficina :" + codOficina + " \n\n");
            domicilio = Consola.leerCadena("Ingrese el domicilio de la Oficina :" + codOficina + " \n\n");

            //Se puede usar el Patron Observe, que ante el evento agregar Auto a una oficina, este Auto Setee su
            // atributo oficinaOrigen a esta oficina que lo está agregando a su lista de Autos
        } else {
            Consola.mostrarCadena("Esta Oficina ya existe!!\n\n");
        }
        altaOk = s.altaOficina(codOficina, telefono, correo, domicilio);
        if (altaOk) {
            Consola.mostrarCadena("Oficina dada de Alta correctamente \n\n");
            Consola.mostrarCadena("Recuerde que hay que agregar Autos a esta Oficina!!! \n\n");
        }
    }

     
    private void agregarAutoEnOficina() {//falta validar errores y salida
        Sistema s = new Sistema();
        String codOficina = Consola.leerCadena("Ingrese el código de la Oficina para agregar un Auto \n\n: ");
        String p = Consola.leerCadena("Ingrese la Patente del Auto quiere agregar a la Oficina " + codOficina + "\n\n: ");
        s.agregarAutoEnOficina(codOficina, p);
    }
}

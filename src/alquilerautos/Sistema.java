package alquilerautos;

import java.time.LocalDate;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;


public class Sistema<T, H> {

    private List<Coleccionable> coleccionables;

    public Sistema() {
        this.coleccionables = new ArrayList<>();
    }

    public void iniciar() {
        boolean salir = false; //El Administrador es único autorizado a salir del Sistema, con cada ingreso a un
        // menún en particular, al terminar de ejecutarlo devuelvo un boolean para seguir en el sistema
        // por eso uso salir=u.ingresar();
        while (!salir) { //!salir==no salir
            Usuario u = null;
            int k = 0; //cantidad de intentos para loguearse
            while (u == null && k <= 5) {
                String usuario = Consola.leerCadena("Ingrese su nombre de usuario:");
                String clave = Consola.leerCadena("Ingrese su clave:");
                int i = 0;
                k++;

                while (i < coleccionables.size() && u == null) {
                    if (this.coleccionables.get(i) instanceof Usuario) { //ver que pasa si no ecuentra instancia de Usuario
                        Usuario us = (Usuario) this.coleccionables.get(i);
                        if (us.verificarUsuario(usuario, clave)) {
                            u = us;
                        }
                    }
                    i++;
                }
                if (i == coleccionables.size() - 1) {
                    Consola.mostrarCadena("usuario y/o clave no validos");
                }
            }
            if (u != null) {
                salir = u.ingresar(); // se ingresa al menú de algún usuario
            } else {
                Consola.mostrarCadena("usuario inexistente, comuniquese con el Administrador");
            }
        }
        Consola.mostrarCadena("Salida del sistema exitosa!!!");
    }

    //devuelve true si lo encuentra, es casi idético al while de inciciar()
    public boolean usuarioRegistrado(String usuario, String clave) {//es una busqueda en la coleccion por 2 String
        int i = 0;  //ver si sirve para otra colección, en ese caso generalizar mediante la Clase Coleccionable
        boolean registrado = false;
        while (i < coleccionables.size() && !registrado) {
            if (this.coleccionables.get(i) instanceof Usuario) {
                Usuario us = (Usuario) this.coleccionables.get(i);
                if (us.verificarUsuario(usuario, clave)) {//comentario idem "usuarioRegistrado"
                    registrado = true;
                }
            }
            i++;
        }
        return registrado;
    }

    public Usuario altaUsuario(String tipo, int dni, String nombre, String direccion, int telefono, String usuario, String clave) {
        //usuarios.add(u);
        FactoriaDeUsuarios fu = FactoriaDeUsuarios.obtenerInstancia();
        Usuario u = fu.crearUsuario(tipo, dni, nombre, direccion, telefono, usuario, clave);
        asignarCodUni(u);
        coleccionables.add(u);
        return u;
    }

    //ver si hace falta, comparar verficarCodUnico de la clase Vendedor
    private void asignarCodUni(Usuario u) {
        int cod = 0;
        if (u instanceof Cliente) {
            Cliente cliente = (Cliente) u;

            while (cod <= 0) {
                cod = Consola.leerEntero("Ingrese un código único de cliente, deber ser mayor a cero");
                if (buscarClientePorCod("Cliente", cod) == -1) {
                    Consola.mostrarCadena("Código no válido!");
                }

                if (buscarClientePorCod("Cliente", cod) == cod) {
                    Consola.mostrarCadena("Código no disponible, ya está asignado a otro Cliente");
                    cod = 0;
                }
            }
            cliente.setCodUnico(cod);
        }
    }

    public void mostrar1(String tipo) { //verificar si muestra listado de usuarios registrados
        ArrayList<Coleccionable> lista = new ArrayList<>();
        for (Coleccionable c : coleccionables) {
            c.agregarSiEs(tipo, lista);
        }
        if (lista.isEmpty()) //System.out.println("El usuario a dar de baja no existe"); 
        {
            Consola.mostrarCadena("El " + tipo + " a dar de baja no existe\n");
        } else {
            for (Coleccionable ce : lista) { //muestra el colecionable elegido
                ce.mostrar();
            }
        }

    }

    //falta implementar
    public boolean actualizarUsuario(Usuario u) {
        System.out.println("Datos del usuario " + u + " actualizado");
        /////////////////////////////////////////////
        return true;
    }

    public boolean altaAuto(String patente, String modelo, String color, String marca, int litros, int precio) {
        return coleccionables.add((Coleccionable) new Auto(patente, modelo, Color.valueOf(color), Marca.valueOf(marca), litros, precio));
    }

    public boolean bajar(Coleccionable T) {
        return coleccionables.remove(T);
    }

    public int buscarClientePorCod(String tipoCliente, int cod) {
        int valor = -1;
        if (cod > 0) {
            ArrayList<Coleccionable> lista = new ArrayList<>(); // en este caso es la lista de clientes, con o sin cod
            //sin cod es cod=0, para ver si tiene codigo hay otro método
            ArrayList<Cliente> listaCliente = new ArrayList<>();
            for (Coleccionable c : coleccionables) {
                c.agregarSiEs(tipoCliente, lista);
            }
            if (!lista.isEmpty()) {
                for (Coleccionable ce : lista) { //ce es un Coleccioanble (Cliente)
                    listaCliente.add((Cliente) ce); //los guardo en una lista de Clientes, "Casteo"
                }

                for (Cliente cliente : listaCliente) {//en realidad puede haber varios con cod=0, este for devuelve le ultimo
                    if (cliente.getCodUnico() == cod) // inicalmente el código es 0, según constructor Cliente
                    {
                        valor = cod; //código de cliente es único, no esta repetido
                    } else {
                        valor = -2; //es mayor que cero pero no coincide con código registrado
                    }
                }
            }
        }
        return valor;
    }

    //Lo uso en la Clase Vendedor, cargarReserva
    public Cliente buscarClientePorCodAsignado(String tipoCliente, int cod) {
        Cliente cliente = null; // se instancia en el Array
        ArrayList<Coleccionable> lista = new ArrayList<>(); // en este caso es la lista de clientes, con o sin cod
        //sin cod es cod=0, para ver si tiene codigo hay otro método
        ArrayList<Cliente> listaCliente = new ArrayList<>();
        for (Coleccionable c : coleccionables) {
            c.agregarSiEs(tipoCliente, lista);
        }

        if (!lista.isEmpty()) {
            for (Coleccionable ce : lista) { //ce es un Coleccioanble (Cliente)
                listaCliente.add((Cliente) ce); //los guardo en una lista de Clientes, "Casteo"
            }

            for (Cliente clie : listaCliente) {
                if (clie.getCodUnico() == cod) // inicalmente el código es 0, según constructor Cliente
                {
                    cliente = clie; //código de cliente es único, no esta repetido, y se lo asigna un vendedor                   
                }
            }
        }

        return cliente;
    }

    //Hace CASI lo mismo que verSiExisteEnColeccion, 
    //verSiExisteEnColeccion: le falta decir cual es el subtipo de coleccion en donde se debe buscar
    //****************************************************************************************
    //los autos se agregan por patente, las oficinas por codOficina, las reservas por codReserva, y los
    //usuarios(clientes, vendedores y adminstradores) por su nombre de usuario
    //NO SE PUEDE USAR PARA TRAER UN CLIENTE POR SU codUnico, ya que este es un ENTERO
    public Coleccionable obtenerColeccionablePorCodigo(String tipo, String codigo) {//retorna un elemento tipo de coleccionable, pero visto como coleccionable
        ArrayList<Coleccionable> lista = new ArrayList<>(); //luego hacer el Casteo al tipo que corresponda la coleccionable
        Coleccionable buscado = null;
        for (Coleccionable c : coleccionables) {
            c.agregarSiEs(tipo, lista);
        }
        if (lista.isEmpty()) {
            Consola.mostrarCadena("No existe una lista de " + tipo + "s \n\n");
        } else {
            for (Coleccionable ce : lista) {
                if (ce.buscar(codigo)) {
                    buscado = ce;
                }
            }
        }
        return buscado;
    }

    public boolean altaOficina(String codOficina, int tel, String correo, String domi) {
        return coleccionables.add(new Oficina(codOficina, tel, correo, domi));

    }

    public void agregarAutoEnOficina(String codOficina, String patente) {//Las Clases que tienen lista de Autos son Reverva y Oficina
        //que son coleccionables, pero otros coleccionables no tienen lista para agregar objetos, no se puede generalizar

        //verSiExisteEnColeccion(patente); : Observador(Auto) pero vista como coleccionable
        //verSiExisteEnColeccion(codigo)) : Oficina pero vista como coleccionable, donde se va agregar el Auto con patente(parametro)
        Oficina oficina = (Oficina) obtenerColeccionablePorCodigo("Oficina", codOficina);
        oficina.agregarObservador((Auto) obtenerColeccionablePorCodigo("Auto", patente));

        //se le envia el mensaje a los observadores que se actualizen, osea setteen su atributo oficinaOrigen
        oficina.avisarObservadores();
    }

    public ArrayList<Coleccionable> separarEnTipoColeccion(String tipo) {
        ArrayList<Coleccionable> lista = new ArrayList<>(); //luego hacer el Casteo al tipo que corresponda el coleccionable
        for (Coleccionable c : coleccionables) {
            c.agregarSiEs(tipo, lista);
        }
        return lista;
    }

    public boolean altaReservaa(LocalDate fechaI, LocalDate fechaF, boolean entrega, String codReserva, int precioTotal) {
        return coleccionables.add(new Reserva(fechaI, fechaF, entrega, codReserva, precioTotal));

    }

    public void asisgnarAuto(int cod) {//cod: código único de un cliente válido y registrado
        ////////////////////////////
        ///////////////////////////////

    }

    //revisar este metodo
    public <T> ArrayList<T> separarColeccion(String tipo) {
        ArrayList<Coleccionable> lista = new ArrayList<>();

        ArrayList<T> listaTipo = new ArrayList<>();

        for (Coleccionable c : coleccionables) {
            c.agregarSiEs(tipo, lista);
        }

        if (!lista.isEmpty()) {
            for (Coleccionable ce : lista) {
                if (ce.getClass().getSimpleName().equals(tipo)) {
                    listaTipo.add((T) ce); //los guardo en una lista de T, "Casteo" 
                }
            }

        } else {
            Consola.mostrarCadenaEnter("Lista sin elementos");
        }

        return listaTipo;
    }

}

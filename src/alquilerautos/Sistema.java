package alquilerautos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sistema<T,H> implements Serializable {
  
     private List<Coleccionable> coleccionables;

    public Sistema() {
        this.coleccionables = new ArrayList<>();
    }
     
    public void iniciar(Sistema s) {
        boolean salir = false; //El Administrador es único autorizado a salir del Sistema
        boolean continua=true;
        Consola.mostrarCadena("******************SISTEMA DE ALQUILER DE AUTOS*****************\n\n");
        while (!salir) { //!salir==no salir
            while(continua){
            Usuario u = null;
            int k = 0; //cantidad de intentos para loguearse
            while (u == null && k <= 5) {
                String usuario = Consola.leerCadena("Ingrese su nombre de usuario:");
                String clave = Consola.leerCadena("Ingrese su clave:");
                int i = 0;
                k++;            
                while (i < coleccionables.size() && u == null) {
                    if(this.coleccionables.get(i) instanceof Usuario){ 
                       Usuario us = (Usuario) this.coleccionables.get(i);
                       if (us.verificarUsuario(usuario, clave)) {
                        u = us;
                        }
                    }
                    i++;
                }
                if (i == coleccionables.size() - 2) { // if (i == coleccionables.size() - 1) { // sin Serializacion
                    Consola.mostrarCadena("usuario y/o clave no validos");
                }
            }
            if (u != null) {
                continua = u.ingresar(s); // se ingresa al menú de algún usuario
            } else {
                Consola.mostrarCadena("usuario inexistente, comuniquese con el Administrador");
            }
            } salir=true;
        }
        Consola.mostrarCadena("Salida del sistema exitosa!!!");
    }

   
    //devuelve true si lo encuentra
    public boolean usuarioRegistrado(String usuario, String clave) {//es una busqueda en la coleccion por 2 String
        int i = 0;
        boolean registrado = false;
        while (i < coleccionables.size() && !registrado) {
            if (this.coleccionables.get(i) instanceof Usuario) {
                Usuario us = (Usuario) this.coleccionables.get(i);
                if (us.verificarUsuario(usuario, clave)) {
                    registrado = true;
                }
            }
            i++;
        }
        return registrado;
    }

    
    public Usuario altaUsuario(String tipo, int dni, String nombre, String direccion, int telefono, String usuario, String clave) {
        FactoriaDeUsuarios fu = FactoriaDeUsuarios.obtenerInstancia();
        Usuario u = fu.crearUsuario(tipo, dni, nombre, direccion, telefono, usuario, clave);
        return u;
    }
    
   
    public void agregarAColeccion(Coleccionable c){
    this.coleccionables.add(c);
   }
    
    
    public void mostrarColeccionables() { 
            for (Coleccionable c : this.coleccionables) {
                System.out.println(c.toString());  
            }
      }
     
    
    public boolean mostrarPorTipoDeColeccion(String tipo) {
        boolean conElementos = false;
        int i = 0;
        for (Coleccionable c : this.coleccionables) {
            i = c.mostrar(tipo) + i;
        }
        if (i >= 1) {
            conElementos = true;
        }
        return conElementos;
    }
   
    
     public boolean mostrar1(String tipo) {
        ArrayList<Coleccionable> lista = new ArrayList<>();
        boolean volver = false;
        if (!volver) {
            for (Coleccionable c : coleccionables) {
                c.agregarSiEs(tipo, lista);
            }
            if (lista.isEmpty()) {
                if (tipo.equals("Oficina") || tipo.equals("Reserva")) {
                    Consola.mostrarCadena("La " + tipo + " a dar de baja no existe !!!");
                } else {
                    Consola.mostrarCadena("El " + tipo + " a dar de baja no existe !!!");
                }
                Consola.limpiarBuffer();
                volver = false;
            } else {
                if (tipo.equals("Auto") || tipo.equals("Oficina") || tipo.equals("Reserva") || tipo.equals("Cliente")) {
                    Consola.mostrarCadena("Listado de " + tipo + "s :");
                } else {
                    Consola.mostrarCadena("Listado de " + tipo + "es :n");
                }
                try {
                    for (Coleccionable ce : lista) { //muestra el colecionable elegido
                        ce.mostrar(tipo);
                    }
                } catch (Exception ex) {
                    System.out.println("Error al mostrar Coleccionable!!!");
                }
                volver = true;
            }
        }
        return volver;
    }
    
    
      //falta implementar
    public boolean actualizarUsuario(Usuario u){
        System.out.println("Datos del usuario " + u +" actualizado");
       /////////////////////////////////////////////
        return true;
    }
    
    

     public Auto altaAuto(String patente,String modelo, String color, String marca, int litros, int precio) {   
         Auto autoCreado=new Auto(patente,modelo, Color.valueOf(color), Marca.valueOf(marca),litros, precio);
          coleccionables.add(autoCreado);
         return autoCreado;
    }
    
    
    
    public boolean bajar(Coleccionable T){
    return coleccionables.remove(T);
    }
    
    
    public int buscarClientePorCod(String tipoCliente, int cod) {
        int valor = -1;
        if (cod > 0) {
            ArrayList<Coleccionable> lista = new ArrayList<>(); // en este caso es la lista de clientes, con o sin cod
            ArrayList<Cliente> listaCliente = new ArrayList<>();
            for (Coleccionable c : this.coleccionables) {
                c.agregarSiEs(tipoCliente, lista);
            }
            if (!lista.isEmpty()) {
                for (Coleccionable ce : lista) { //ce es un Coleccioanble (Cliente)
                    listaCliente.add((Cliente) ce); //los guardo en una lista de Clientes, "Casteo"
                }

             int j = 0;
               // Cliente cliente1 = null;
               boolean encontrado=false;
                while (j < listaCliente.size() && !encontrado) {
                   if(listaCliente.get(j).getCodUnico() == cod){
                        valor = cod;
                        encontrado=true;
                    } else {
                        valor = -2;
                        encontrado=false;
                    }
                    j++;
                }  
            } else {
                valor = -2;
            } //aca la lista está vacia, es el primer cliente
        }
        return valor;
    }
     
    
    //Lo uso en la Clase Vendedor, cargarReserva
    public Cliente buscarClientePorCodAsignado(String tipoCliente, int cod) {
        Cliente cliente = null; // se instancia en el Array
        ArrayList<Coleccionable> lista = new ArrayList<>(); // en este caso es la lista de clientes, con o sin cod
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
     
   
    public Coleccionable obtenerColeccionablePorCodigo(String tipo, String codigo) {//retorna un elemento tipo de coleccionable, pero visto como coleccionable
        ArrayList<Coleccionable> lista = new ArrayList<>(); //luego hacer el Casteo al tipo que corresponda la coleccionable
        Coleccionable buscado = null;
        for (Coleccionable c : this.coleccionables) {
            c.agregarSiEs(tipo, lista);
        }
        for (Coleccionable ce : lista) {
            if (ce.buscar(codigo)) {
                buscado = ce;
            }
        }
        return (Coleccionable) buscado;
    }
    
    
    
    public boolean altaOficina(String codOficina,int tel, String correo, String domi) {
     return coleccionables.add(new Oficina(codOficina, tel, correo, domi));
    
    }
    
    
    public void agregarAutoEnOficina(String codOficina, String patente) {//Las Clases que tienen lista de Autos son Reserva y Oficina
    //que son coleccionables, pero otros coleccionables no tienen lista en donde agregar objetos, no se puede generalizar.
        //Oficina pero vista como coleccionable, donde se va agregar el Auto con patente(parametro)
        Oficina oficina = (Oficina) obtenerColeccionablePorCodigo("Oficina", codOficina);
        oficina.agregarObservador((Auto) obtenerColeccionablePorCodigo("Auto", patente));

        //se le envia el mensaje a los observadores que se actualizen, osea setteen su atributo oficinaOrigen
        oficina.avisarObservadores();
    }
    
    
    
    public boolean sacarAutosOficina(String codOficina) {
        boolean eliminados = false;
        ArrayList<Coleccionable> observadores;
        Auto auto;
        Oficina oficina = (Oficina) obtenerColeccionablePorCodigo("Oficina", codOficina);
        observadores = separarEnTipoColeccion("Auto");
        if (observadores != null) {
            for (Coleccionable ob : observadores) {
                auto = (Auto) ob;
                if (auto.getOficinaOrigen().getCodOficina().equals(codOficina)) {
                    oficina.avisarObservadoresBaja();
                    Consola.mostrarCadena("El Auto con patente  "+auto.getPatente()+" fue retirado de la Oficina "+codOficina);
                    eliminados = true;
                }else{
                  Consola.mostrarCadena("La Oficina "+codOficina+" no tiene Autos para ser retirados!!!");
                  eliminados = false;
                }
            }
        }else{
          Consola.mostrarCadena("El Sistema no tiene Autos");
          eliminados=false;
        }
        return eliminados;
    }
    
  
    public ArrayList<Coleccionable> separarEnTipoColeccion(String tipo){
      ArrayList<Coleccionable> lista = new ArrayList<>(); //luego hacer el Casteo al tipo que corresponda el coleccionable
        try{
      for (Coleccionable c : coleccionables) {
            c.agregarSiEs(tipo, lista);
        }
        }catch(NullPointerException e){
            Consola.mostrarCadena("Se creara la primer Reeserva del Sistema.\n");
        }
      return lista;
    }
        
 
    public boolean altaReserva(LocalDate fechaI, LocalDate fechaF,boolean entrega, String codReserva, int precioTotal) {
     return coleccionables.add(new Reserva(fechaI, fechaF, entrega, codReserva, precioTotal));
    
    }
    
 
    public boolean verificarSiHayColeccionable(String tipo) {
        boolean hay = false;
        ArrayList<Coleccionable> lista = new ArrayList<>();
        for (Coleccionable c : this.coleccionables) {
            c.agregarSiEs(tipo, lista);
        }
        if (lista.isEmpty()) {
            hay = false;
        } else {
            hay = true;
        }
        return hay;
    }
    
  
   
    public ArrayList<T> separarColeccion(String tipo) {
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
    
      
     

    public void serializar(String a) throws IOException {
        FileOutputStream f = new FileOutputStream(a);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(this);
        o.close();
        f.close();
    }

    
     public Sistema deSerializar(String a) throws IOException, ClassNotFoundException {
        FileInputStream f = new FileInputStream(a);
        ObjectInputStream o = new ObjectInputStream(f);
        Sistema s = (Sistema) o.readObject();
        o.close();
        f.close();
        return s;
    }
     
   }
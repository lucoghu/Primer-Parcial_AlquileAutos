package alquilerautos;

import java.io.Serializable;
import java.util.Collection;

public abstract class Gestor<T> extends Usuario<T> implements Serializable{

    @Override
    public abstract boolean ingresar(Sistema s);

    public Gestor(int dni, String nombre, String direccion, int telefono, String usuario, String clave) {
        super(dni, nombre, direccion, telefono, usuario, clave);
    }

    public Gestor() { //se necesita para usar Factory con reflection
    }
    
    
    public Usuario altaUsuario(String tipo, Sistema s) {
        boolean salirMenu = false;
        String tipoUsuario = tipo;
        Usuario u = null;
        int dni = Consola.leerEntero("Ingrese DNI:"); //hay que validar la entrada de todos estos datos
        String nom = Consola.leerCadena("Ingrese el nombre: ");
        String dir = Consola.leerCadena("Ingrese el domicilio: ");
        int tel = Consola.leerEntero("Ingrese el teléfono: ");
        String us = Consola.leerCadena("Ingrese un nombre de usuario: ");
        String cl = Consola.leerCadena("Ingrese una contraseña: ");
        if (!s.usuarioRegistrado(us, cl)) {
            u = s.altaUsuario(tipoUsuario, dni, nom, dir, tel, us, cl);
        } else {
            Consola.mostrarCadena("El " + tipoUsuario + " que intenta dar de alta ya se encuentra registrado");
        }
        return u;
    }
 
      
    public boolean asignarCodigoAlCliente(Cliente cliente, int codVerificar) {
        Sistema s = new Sistema();
        boolean asignado = false;
        int valor = s.buscarClientePorCod("Cliente", codVerificar);
        while (valor == -1 || valor == codVerificar) {
            Consola.mostrarCadena("Ya hay Cliente registrado con código único :" + codVerificar + "\n");
            Consola.mostrarCadena("O el código único ingreso no es válido\n\n");
            Consola.mostrarCadena("Intenta nuevamente:\n");
            codVerificar = Consola.leerEntero("Asignar código a este Cliente, debe ser un ENTERO MAYOR A CERO");
            valor = s.buscarClientePorCod("Cliente", codVerificar);
        }
        if (s.buscarClientePorCod("Cliente", codVerificar) == -2) {
            cliente.setCodUnico(codVerificar);
            asignado = true;
            Consola.mostrarCadena("Codigo asignado !!!\n\n");
        }
        return asignado;
    }
      
    
        
    @Override
     public abstract void agregarSiEs(String tipoUsuario,Collection<T> listadoUsuarios);
      
    
    @Override
     public abstract int mostrar(String tipo);
     
     
    @Override 
    public abstract boolean buscar(String codigo);
    
}
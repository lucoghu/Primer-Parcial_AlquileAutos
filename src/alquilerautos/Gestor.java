package alquilerautos;

import java.util.Collection;
import java.util.List;

public abstract class Gestor<T> extends Usuario<T> {

    @Override
    public abstract boolean ingresar();

    public Gestor(int dni, String nombre, String direccion, int telefono, String usuario, String clave) {
        super(dni, nombre, direccion, telefono, usuario, clave);
    }

    public Usuario altaUsuario(String tipo) {
        Sistema s = new Sistema();
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
            u = s.altaUsuario(tipoUsuario, dni, nom, dir, tel, us, cl); //pero si ingreso a este menú ya esta registrado
        } else {
            Consola.mostrarCadena("El " + tipoUsuario + " que intenta dar de alta ya se encuentra registrado");
        }
        return u;
    }

    public void asignarCodigoAlCliente(Cliente cliente, int codVerificar) {
        Sistema s = new Sistema();
        int valor = s.buscarClientePorCod("Cliente", codVerificar);
        while (valor == -1 || valor == codVerificar) {
            Consola.mostrarCadena("Ya hay Cliente registrado con código único :" + codVerificar + "\n");
            Consola.mostrarCadena("O el código único ingreso no es válido\n\n");
            Consola.mostrarCadena("Intenta nuevamente:\n");
            codVerificar = Consola.leerEntero("Asignar código a este Cliente, debe ser un ENTERO MAYOR A CERO\n\n");
            valor = s.buscarClientePorCod("Cliente", codVerificar);
        }
        if (s.buscarClientePorCod("Cliente", codVerificar) == -2) {
            cliente.setCodUnico(codVerificar);
            Consola.mostrarCadena("Codigo asignado !!!\n\n");
            Consola.mostrarCadenaEnter("Cliente dado de alta exitosamnete!! Presionar [Enter] para volver al Menú\n\n");
        }
    }

    @Override
    public abstract void agregarSiEs(String tipoUsuario, Collection<T> listadoUsuarios);

    public abstract void mostrar();

    // public abstract void mostrar(T tipoColeccion);
    @Override // Gestor es abstracta, no se puede tener una listada de gestores concretos.
    public abstract boolean buscar(String codigo);

}

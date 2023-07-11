package alquilerautos;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException  {
       // Sistema s = new Sistema();
       // s.iniciar(s);
    
    
     Sistema s = new Sistema();
        try {
            s = s.deSerializar("alquiler.txt");
            Consola.mostrarCadena("Inicio del Sistema Alquiler de Autos");
        } catch (IOException | ClassNotFoundException e) {
            Consola.mostrarCadena("El Sistema se inicia por primera vez");
            int dni = Consola.leerEntero("Ingrese DNI:");
            String nom = Consola.leerCadena("Ingrese su nombre: ");
            String dir = Consola.leerCadena("Ingrese su domicilio: ");
            int tel = Consola.leerEntero("Ingrese su teléfono: ");
            String us = Consola.leerCadena("Ingrese su nombre de Usuario como Administrador");
            String cl = Consola.leerCadena("Ingrese una contraseña: ");
            Administrador a = new Administrador(dni, nom, dir, tel, us, cl);
            s.agregarAColeccion(a);
            Consola.mostrarCadena("Ahora logearse para ingresar al Sistema!!!");
        }
        s.iniciar(s);
        s.serializar("alquiler.txt");
    }
}

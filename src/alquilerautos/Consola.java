package alquilerautos;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Consola {

    private static Scanner entrada = new Scanner(System.in);

    public static String leerCadena(String cad) {
        System.out.println(cad);
        String ent = entrada.nextLine();
        return ent;

    }

    public static int leerEntero(String cad) { //para ingresar opciones o litros
        Boolean esEntero;
        esEntero = false;
        int ent = 0;
        do {
            try {
                System.out.println(cad);
                ent = entrada.nextInt();
                esEntero = true;
            } catch (InputMismatchException ei) {
                System.out.println("El valor ingresado no es un entero, intentelo nuevamente");
            }
        } while (!esEntero);
        return ent;
    }

    public static int leerEntero() {
        Boolean esEntero;
        esEntero = false;
        int ent = 0;
        do {
            try {
                ent = entrada.nextInt();
                esEntero = true;
            } catch (InputMismatchException ei) {
                System.out.println("El valor ingresado no es un entero, intentelo nuevamente");
            }
        } while (!esEntero);
        return ent;
    }

    public static float leerFloat(String cad) { //para ingresar precio
        Boolean esFloat;
        esFloat = false;
        float flo = 0;
        do {
            try {
                System.out.println(cad);
                flo = entrada.nextFloat();
                esFloat = true;
            } catch (InputMismatchException ei) {
                System.out.println("El valor ingresado no es un entero válido o no es un decimal, utilece la coma, intentelo nuevamente");
            }
        } while (!esFloat);
        return flo;
    }

    public static char siNo(String cad) {

        System.out.println(cad);
        String ent = entrada.nextLine();
        while (!(ent.equals("s") || ent.equals("S") || ent.equals("n") || ent.equals("N"))) {
            System.out.println("Debe responder 'S' (o 's') o 'N' (o 'n'): ");
            ent = entrada.nextLine();
        }
        char c;
        c = ent.charAt(0);
        return c;
    }

    public static boolean convertirBoolean(char c) { //para convertir a boolean el dato SI o No
        boolean b = false;                                 //por ejemplo para preguntar si el Auto se entrego o no
        if (c == 's' || c == 'S') {
            b = true;
        }
        return b;
    }

    public static void mostrarCadena(String cad) {
        System.out.println(cad);
    }

    public static void mostrarCadenaEnter(String cad) { //muestra un mensaje, esperando que ingrese un ENTER para continuar
        System.out.println(cad);
        String ent = entrada.nextLine();
    }
}




package com.company;

import com.company.model.Juego;

import java.util.Scanner;

public class Main {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    Juego juego = new Juego();

    public static void main(String[] args) {
        Main programa = new Main();
        programa.inicio();
    }

    public void inicio() { //Inicio Programa--------------------------

        Scanner input = new Scanner(System.in);
        boolean salir = false;

        System.out.println("****** BIENVENIDO A LA SOPA DE LETRAS ******");
        System.out.println("*********** ESCRIBA PALABRAS ***************");
        System.out.println("Cinco palabras como máximo ------------------");
        System.out.println("Al acabar escriba 'end' y pulse intro ------");

        do {    //-------------------------Pedir palabras para crear los objetos Palabra
            String palabraUsur = input.nextLine();
            if (!palabraUsur.equals("end")) {
                juego.pedirPalabra(palabraUsur);
            } else {
                salir = true;
            }
        } while (!salir) ;

        System.out.println("--- Registro completado ---");
        System.out.println("Encuentra las palabras en la sopa de letras:\n");

        juego.tablero();      //--------------Se genera el tablero de la Sopa de Letras

        System.out.println("Introduce una palabra:");

        do {              //------------------Buscar las palabras en el tablero de la Sopa de Letras
            String palabraBuscar = input.nextLine();
            juego.buscarPalabra(palabraBuscar);
        } while (!juego.fin());

        System.out.println("¡Felicidades! ¡Has completado la sopa de letras!");
    }
}
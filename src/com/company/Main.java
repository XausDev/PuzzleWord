package com.company;

import com.company.model.Palabra;
import com.company.model.TableroLetras;

import java.util.Scanner;

public class Main {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    TableroLetras tableroLetras = new TableroLetras();

    public static void main(String[] args) {
        Main programa = new Main();
        programa.inicio();
    }
    public void inicio(){//Inicio Programa--------------------------

        pedirPalabra();
        System.out.println("--- Registro completado ---");

        tableroLetras.generarTablero();
        System.out.println("Encuentra las palabras en la sopa de letras:\n");

        tableroLetras.imprimirTablero();
        System.out.println();//Salto de linea
        do {

            Scanner input = new Scanner(System.in);
            String palabraBuscar = input.nextLine();
            if(!tableroLetras.buscarPalabra(palabraBuscar)){
                System.out.print("La palabra ");
                System.out.print(ANSI_RED);
                System.out.print(palabraBuscar.toUpperCase());
                System.out.print(ANSI_RESET);
                System.out.println(" no está en la sopa de letras.");
                System.out.println("Vuelve a intentarlo:\n");
            }else {
                System.out.print("¡Has encontrado la palabra ");
                System.out.print(ANSI_GREEN);
                System.out.print(palabraBuscar.toUpperCase());
                System.out.print(ANSI_RESET);
                System.out.println(" en la sopa de letras!\n");
            }

            tableroLetras.imprimirTablero();
            System.out.println();//Salto de linea
        }while(!tableroLetras.finJuego());

        System.out.println("¡Felicidades! ¡Has completado la sopa de letras!");

    }

    //metodos-----
    private void pedirPalabra(){
        Scanner input = new Scanner(System.in);
        String palabraUsur;
        boolean salir = false;
        System.out.println("Ingrese palabras:");
        do {
            palabraUsur = input.nextLine();
            String palabraUsurMayus = palabraUsur.toUpperCase(); //Lo pasamos a mayusculas
            if(!palabraUsur.equals("end")) {
                if (palabraUsur.length() > 10) {
                    System.out.println("Máximo 10 caracteres por palabra");
                } else if(palabraUsur.contains(" ")) {
                    System.out.println("Solo una palabra cada vez, no pueden haber espacios");
                }else{
                    Palabra nuevaPalabra = new Palabra(0, 0, 0, 0, palabraUsurMayus);
                    tableroLetras.addPalabra(nuevaPalabra);
                }
            }else{
                salir = true;
            }
        }while(!salir);
    }
}
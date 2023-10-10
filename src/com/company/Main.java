package com.company;

import com.company.model.Palabra;
import com.company.model.PuzzelItem;
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

        System.out.println("Ingrese palabras:");
        pedirPalabra();
        System.out.println("--- Registro completado ---");

        System.out.println("Encuentra las palabras en la sopa de letras:\n");

        tableroLetras.generarTablero();
        tableroLetras.imprimirTablero();
        System.out.println("Introduce una palabra:");

        do {//CREO QUE ESTO PUEDE SER UN METODO EN LA CLASE JUEGO

            Scanner input = new Scanner(System.in);
            String palabraBuscar = input.nextLine();

            boolean encontrada = false;

            for(Palabra palabra: tableroLetras.getPalabras()){ //Por cada palabra del ArrayList se compara con los objetos Palabra
                if(palabra.tryGuess(palabraBuscar)){ //Para cada objeto palabra entra en el metodo tryGuess y comprueba si existe como objeto Palabra
                    tableroLetras.sumarPalabra(palabra); //Suma la palabra siempre que discovered = 0
                    palabra.setDiscovered(); //Discovered pasa a valer 1
                    System.out.print("¡Has encontrado la palabra "
                            +ANSI_GREEN+palabraBuscar.toUpperCase()+ANSI_RESET
                            +" en la sopa de letras!\n");
                    tableroLetras.imprimirTableroColor(palabra);//En este punto ya sabes que esta palabra ha sido encontrada, discovered = true.
                    System.out.println();//Salto de linea
                    encontrada =true;
                    break;
                }
            }

            if(!encontrada){
                System.out.print("La palabra "
                        +ANSI_RED+palabraBuscar.toUpperCase()+ANSI_RESET
                        +" no está en la sopa de letras.");
                System.out.println("Vuelve a intentarlo:\n");
            }

        }while(!tableroLetras.finJuego());

        System.out.println("¡Felicidades! ¡Has completado la sopa de letras!");

    }

    //metodos-----
    private void pedirPalabra(){
        Scanner input = new Scanner(System.in);
        String palabraUsur;
        boolean salir = false;

        do {
            palabraUsur = input.nextLine();
            String palabraUsurMayus = palabraUsur.toUpperCase(); //Lo pasamos a mayusculas
            if(!palabraUsur.equals("end")) {
                if (palabraUsur.length() > 10) {
                    System.out.println("Máximo 10 caracteres por palabra.");
                } else if(palabraUsur.contains(" ")) {
                    System.out.println("No pueden haber espacios.");
                } else if (palabraUsur.length() <= 2) {
                    System.out.println("Tienen que ser palabras de más de 2 letras.");
                } else{
                    Palabra nuevaPalabra = new Palabra(0, 0, 0, 0, palabraUsurMayus);
                    tableroLetras.addPalabra(nuevaPalabra);
                }
            }else{
                salir = true;
            }
        }while(!salir);
    }
}
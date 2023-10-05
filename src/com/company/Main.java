package com.company;

import com.company.model.Palabra;
import com.company.model.TableroLetras;

import java.util.Scanner;

public class Main {
    TableroLetras tableroLetras = new TableroLetras();

    public static void main(String[] args) {
        Main programa = new Main();
        programa.inicio();
    }
    public void inicio(){//Inicio Programa--------------------------

        pedirPalabra();
        System.out.println("Registro completado");

        tableroLetras.generarTablero();
        tableroLetras.imprimirTablero();


    }

    //metodos-----
    private void pedirPalabra(){
        Scanner input = new Scanner(System.in);
        String palabraUsur = null;
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
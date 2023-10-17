package com.company.model;

import static com.company.Main.*;

public class Juego {

    boolean encontrada = false;
    TableroLetras tableroLetras = new TableroLetras();
    public void tablero(){
        tableroLetras.generarTablero();
        tableroLetras.imprimirTablero();
    }

    public boolean fin(){
        return tableroLetras.finJuego();
    }

    /**
     * Metodo para introducir las palabras escogidas por el usuario en el tablero.
     * Creamos los objetos palabra.
     * @param palabraUsur palabra introducida por consola.
     */
    public void pedirPalabra(String palabraUsur) {

        if (tableroLetras.getPalabras().size() <= 4) {  //----------Acepta máximo cinco palabras

            String palabraUsurMayus = palabraUsur.toUpperCase();     //--------Lo pasamos a mayusculas

            if (palabraUsur.length() > 10) {
                System.out.println("Máximo 10 caracteres por palabra.");
            } else if (palabraUsur.contains(" ")) {
                System.out.println("No pueden haber espacios.");
            } else if (palabraUsur.length() <= 2) {
                System.out.println("Tienen que ser palabras de más de 2 letras.");
            } else {
                Palabra nuevaPalabra = new Palabra(0, 0, 0, 0, palabraUsurMayus);
                tableroLetras.addPalabra(nuevaPalabra);   //----------Añadimos la palabra
            }
        } else {
            System.out.println("Has llegado al máximo de palabras. La palabra "
                    +ANSI_RED+palabraUsur.toUpperCase()+ANSI_RESET
                    +" no ha entrado en la Sopa de Letras.");
            System.out.println("Escribe end y pulsa intro:");
        }
    }

    /**
     * Método para buscar la palabra en el tablero.
     * @param palabraBuscar palabra a buscar.
     */
    public void buscarPalabra(String palabraBuscar){
        encontrada = false;
        for(Palabra palabra: tableroLetras.getPalabras()){ //------Por cada palabra del ArrayList se compara con los objetos Palabra
            if(palabra.tryGuess(palabraBuscar)){           //------Para cada objeto palabra entra en el metodo tryGuess y comprueba si existe como objeto Palabra
                tableroLetras.sumarPalabra(palabra);       //------Suma la palabra siempre que discovered = 0
                palabra.setDiscovered();                   //------Discovered pasa a valer 1
                System.out.print("¡Has encontrado la palabra "
                        +ANSI_GREEN+palabraBuscar.toUpperCase()+ANSI_RESET
                        +" en la sopa de letras!\n");
                System.out.println();//Salto de linea
                tableroLetras.imprimirTableroColor();      //------En este punto ya sabes que esta palabra ha sido encontrada, discovered = true.
                System.out.println();//Salto de linea
                encontrada =true;

                if(!tableroLetras.finJuego()){
                    System.out.println("Introduce otra palabra:");
                }
            }
        }
        if(!encontrada){
            System.out.println("La palabra "
                    +ANSI_RED+palabraBuscar.toUpperCase()+ANSI_RESET
                    +" no está en la sopa de letras.");
            System.out.println("Vuelve a intentarlo:\n");
        }
    }
}

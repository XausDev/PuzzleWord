package com.company;

import com.company.model.Palabra;
import com.company.model.TableroLetras;
import java.util.ArrayList;

import static com.company.Main.ANSI_GREEN;
import static com.company.Main.ANSI_RESET;

public class ImprimirTablero {

    /**
     * Imprimir la matriz tablero por consola
     */
    public static void imprimir(TableroLetras tableroLetras, char[][] tablero) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                System.out.print(tablero[i][j] + " ");
            }
            System.out.println(); // Salto de línea después de cada fila
        }
    }

    /**
     * Imprimir la matriz tablero por consola cuando se tiene que imprimir en color
     * pq el usuario ha acertado una palabra.
     * Por cada palabra comprueba si esta descubierta, compara la letra del tablero con la letra de la palabra con
     * el metodo coordsOfMatch. Si coincide el metodo coordsOfMatch devuelve los indices y si coindicen con la
     * casilla que estamos mirando la pinta en verde.
     * ERROR A RESOLVER: Si una letra coincide con otra y tiene que pintar las dos, se duplican las letras.
     */
    public static void imprimirTableroColor(TableroLetras tableroLetras) {

        char[][] tablero = tableroLetras.getTablero();
        ArrayList<Palabra> palabras = tableroLetras.getPalabras();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                boolean pintar = false;

                for(Palabra p : palabras) {
                    if (p.isDiscovered()) {
                        Object letra = tablero[i][j];
                        int[] coords = p.coordsOfMatch(letra);
                        int row = coords[0];
                        int col = coords[1];

                        if (i == row && j == col) {
                            System.out.print(ANSI_GREEN + tablero[i][j] + " " + ANSI_RESET);
                            pintar = true;
                            int indice = p.getIndiceLetra();
                            p.setIndiceLetra(indice+1);
                            if(p.getIndiceLetra()>=p.length()){ //--------Reinicia el indice de la palabra para que se vuelva a pintar cuando se intente otra palabra
                                p.setIndiceLetra(0);
                            }
                        }
                    }
                }
                if(!pintar){
                    System.out.print(tablero[i][j] + " ");
                }
            }
            System.out.println();  //Salto de línea después de cada fila
        }
    }


}

package com.company.model;

import java.util.ArrayList;
import java.util.Random;

import static com.company.Main.ANSI_GREEN;
import static com.company.Main.ANSI_RESET;

public class TableroLetras {
    private char[][] tablero;
    private ArrayList<Palabra> palabras;
    private int palabrasEncontradas;
    private char[] letrasPalabra;

    public TableroLetras() {
        palabras = new ArrayList<>();
        this.tablero = new char[10][10];
        this.palabrasEncontradas = 0;
    }

    public ArrayList<Palabra> getPalabras() {
        return palabras;
    }

    /**
     * Añade la palabra introducida por el usuario en el Main y calcula sus indices
     * con el metodo calculoIndex de la clase Palabra
     * @param palabra objeto de la clase Palabra introducido por el usuario
     */
    public void addPalabra(Palabra palabra){
        palabras.add(palabra);
        palabra.calculoIndex();
    }

    /**
    * Colocar aleatoriamente las palabras en el tablero
    * Primero introducimos las letras de las palabras introducidas por el usuario
    * Luego generamos letras aleatorias para rellenar los indices que faltan con el método generarLetras()
    */
    public void generarTablero() {

        for (int i = 0; i < palabras.size(); i++) {
            String palabra = String.valueOf(palabras.get(i));
            letrasPalabra = palabra.toCharArray();
            Palabra palabraArrayList = palabras.get(i);//SE GUARDA CADA PALABRA DEL ARRAYLIST UNA POR UNA Y SE COLOCA EN EL TABLERO CON colocarPalabra----------------------

            colocarPalabra(palabraArrayList);
        }
       generarLetras();
    }

    private void colocarPalabra(Palabra palabraArrayList) {//FALTA POR IMPLEMENTAR EL CONTROL DE SOLAPAMIENTOS-------------------------------
        int indexRowInit = palabraArrayList.getIndexRowInit();
        int indexRowEnd = palabraArrayList.getIndexRowEnd();

        int indexColumInit = palabraArrayList.getIndexColumnInit();
        int indexColumEnd = palabraArrayList.getIndexColumnEnd();

        int indexChar = 0;

        if (indexRowInit == indexRowEnd) { //Horizontal, i ==
            for (int c = indexColumInit; c <= indexColumEnd; c++) {
                    tablero[indexRowInit][c] = letrasPalabra[indexChar];
                    indexChar++;
            }
        } else if (indexColumInit == indexColumEnd) { //Vertical, j ==
            for (int r = indexRowInit; r <= indexRowEnd; r++) {
                    tablero[r][indexColumInit] = letrasPalabra[indexChar];
                    indexChar++;
            }
        }
    }

    private void generarLetras() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero[i][j] == '\u0000') {//El caracter '\u0000' es = a null en char
                    char letra = (char) (random.nextInt(26) + 'A');// Generar una letra aleatoria entre 'A' (ASCII 65) y 'Z' (ASCII 90)
                    tablero[i][j] = letra;
                }
            }
        }
    }

    public void imprimirTablero() {// Imprimir la matriz tablero por consola
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println(); // Salto de línea después de cada fila
        }
    }

    public void imprimirTableroColor() {
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
                        }
                    }
                }
                if(!pintar){
                    System.out.print(tablero[i][j] + " ");
                }
            }
            System.out.println(); // Salto de línea después de cada fila
        }
    }

    public void sumarPalabra(Palabra palabra){
        if(!palabra.isDiscovered()) {
            palabrasEncontradas++;
        }
    }

    public boolean finJuego() {
        return palabrasEncontradas == palabras.size();
    }
}

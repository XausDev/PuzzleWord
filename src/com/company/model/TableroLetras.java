package com.company.model;

import java.util.ArrayList;
import java.util.Random;

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

    /**
     * Añade la palabra introducida por el usuario en el Main y calcula sus indices
     * con el metodo calculoIndex de la clase Palabra
     * @param palabra objeto de la clase Palabra introducido por el usuario
     */
    public void addPalabra(Palabra palabra){
        palabras.add(palabra);
        palabra.calculoIndex();
    }

    //Metodo provisional para visualizar la lista de palabras introducidas
   /* public void listaPalabras(){
        for(Palabra p : palabras){
            System.out.println(p.toString());
        }
    }*/

    public void generarTablero() {
        // Lógica para colocar aleatoriamente las palabras en el tablero
        // evitando superposiciones
        //Primero generamos un tablero con letras aleatorias
       // tablero = new char[10][10];

        for (int i = 0; i < palabras.size(); i++) {
            String palabraChar = String.valueOf(palabras.get(i));
            letrasPalabra = palabraChar.toCharArray();
            Palabra indexPalabra = palabras.get(i);

            int indexRowInit = indexPalabra.getIndexRowInit();
            int indexRowEnd = indexPalabra.getIndexRowEnd();

            int indexColumInit = indexPalabra.getIndexColumnInit();
            int indexColumEnd = indexPalabra.getIndexColumnEnd();

            int indexChar=0;

            if(indexRowInit == indexRowEnd){ //Horizontal, i ==
                for (int c = indexColumInit; c <= indexColumEnd; c++) {
                    tablero[indexRowInit][c] = letrasPalabra[indexChar];
                    indexChar++;
                }
            }else if (indexColumInit == indexColumEnd) { //Vertical, j ==
                for (int r = indexRowInit; r <= indexRowEnd; r++) {
                    tablero[r][indexColumInit] = letrasPalabra[indexChar];
                    indexChar++;
                }
            }
        }
        generarLetras();
    }

    private void generarLetras() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero[i][j] == '\u0000') {//El caracter '\u0000' es = a null en char
                    // Generar una letra aleatoria entre 'A' (ASCII 65) y 'Z' (ASCII 90)
                    char letra = (char) (random.nextInt(26) + 'A');
                    tablero[i][j] = letra;
                }
            }
        }
    }


    public void imprimirTablero() {
        // Imprimir la matriz tablero por consola
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println(); // Salto de línea después de cada fila
        }
    }

    public boolean buscarPalabra(String palabra) {
        // Buscar palabra en tablero
        // Si encontrada, marcarla como descubierta
        // y aumentar contador palabrasEncontradas
        boolean encontrado = false;

        for (Palabra p: palabras) {
            if(p.toString().equalsIgnoreCase(palabra)) { //Utilizamos toString para pasar el objeto Palabra del ArrayList a String y poder compararlo
                encontrado = true;// palabra encontrada
                palabrasEncontradas++;
                break;
            }
        }
        return encontrado;
    }

    public boolean finJuego() {
        return palabrasEncontradas == palabras.size();
    }

}

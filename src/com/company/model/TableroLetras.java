package com.company.model;

import java.util.ArrayList;
import java.util.Random;


public class TableroLetras {
    private char[][] tablero;
    private final ArrayList<Palabra> palabras;
    private int palabrasEncontradas;
    private char[] letrasPalabra;
    private int indexLetra = 0;


    public TableroLetras() {
        palabras = new ArrayList<>();
        this.tablero = new char[10][10];
        this.palabrasEncontradas = 0;
    }
    public char[][] getTablero() {
        return tablero;
    }

    public int getPalabrasEncontradas() {
        return palabrasEncontradas;
    }

    public ArrayList<Palabra> getPalabras() {
        return palabras;
    }

    /**
     * Añade la palabra introducida por el usuario en el Main y calcula sus indices
     * con el metodo calculoIndex de la clase Palabra
     * @param palabra objeto de la clase Palabra introducido por el usuario
     */
    public void addPalabra(Palabra palabra) {
        palabras.add(palabra);
        palabra.calculoIndex();
    }

    /**
     * Colocar aleatoriamente las palabras en el tablero
     * Primero coloca la palabra más larga, intentamos evitar solapamientos.
     * La variable "letrasPalabra" es un array de chars para introducir las letras de las palabras introducidas por el usuario
     * Luego generamos letras aleatorias para rellenar los índices que faltan con el método generarLetras()
     */
    public void generarTablero() {

        int maxLength = 0;
        Palabra palabraLarga = null;

        for (Palabra palabraArrayList : palabras) {
            if (palabraArrayList.toString().length() > maxLength) {
                maxLength = palabraArrayList.toString().length();
                palabraLarga = palabraArrayList;
            }
        }

        if (palabraLarga != null) {
            String palabra = palabraLarga.toString();
            letrasPalabra = palabra.toCharArray();
            colocarPalabra(palabraLarga);
        }

        for (Palabra palabraArrayList : palabras) {
            if (!palabraArrayList.equals(palabraLarga)) {
                String palabra = palabraArrayList.toString();
                letrasPalabra = palabra.toCharArray();
                colocarPalabra(palabraArrayList);
            }
        }
        generarLetras();
    }

    private void colocarPalabra(Palabra palabraArrayList) {
        int indexRowInit = palabraArrayList.getIndexRowInit();
        int indexRowEnd = palabraArrayList.getIndexRowEnd();

        int indexColumInit = palabraArrayList.getIndexColumnInit();
        int indexColumEnd = palabraArrayList.getIndexColumnEnd();

        indexLetra = 0;

        if (indexRowInit == indexRowEnd) { //Horizontal, i ==
            for (int c = indexColumInit; c <= indexColumEnd; c++) {

                //OPCION 1 ------- Se borra solo las letras de la palbra actual y se vuelve a colocar
                if (!comprobarPosicionLetra(indexRowInit, c)) {//Implementacion del CONTROL DE SOLAPAMIENTOS-------------------------------
                    int finColum = c; //Para que se guarde la posición en que coincide con otra letra y podamos usarla en el for de borrado
                    for (c = indexColumInit; c < finColum; c++) {//Borra todas las letras anteriores de esta palabra hasta la posición en que ha coincidido con otra
                        tablero[indexRowInit][c] = '\u0000';
                    }
                    recalcular(palabraArrayList);
                    break;
                }else {
                    tablero[indexRowInit][c] = letrasPalabra[indexLetra];
                    if(indexLetra < letrasPalabra.length-1) {  //----El -1 para que indexLetra no provoque en el else un OutOfBounds Exception
                        indexLetra++;
                    }
                }
            }
        } else if(indexColumInit ==indexColumEnd){ //Vertical, j ==, Palabra en ROW
            for (int r = indexRowInit; r <= indexRowEnd; r++) {

                //OPCION 1 ------- Se borra solo las letras de la palabra actual y se vuelve a colocar
                if (!comprobarPosicionLetra(r, indexColumInit)) {//Implementacion del CONTROL DE SOLAPAMIENTOS-------------------------------
                    int finRow = r;//Para que se guarde la posición en que coincide con otra letra y podamos usarla en el for de borrado
                    for (r = indexRowInit; r < finRow; r++) {//Borra todas las letras anteriores de esta palabra hasta la posición en que ha coincidido con otra
                        tablero[r][indexColumInit] = '\u0000';
                    }
                    recalcular(palabraArrayList);
                    break;
                }else{
                    tablero[r][indexColumInit] = letrasPalabra[indexLetra];
                    if(indexLetra < letrasPalabra.length-1) {         //----El -1 para que indexLetra no provoque en el else un OutOfBounds Exception
                        indexLetra++;
                    }
                }
            }
        }
    }

    public void recalcular(Palabra palabraArrayList){
        palabraArrayList.calculoIndex();//Vuelve a calcular sus indices
        colocarPalabra(palabraArrayList);//AQUI ESTÁ EL ERROR (RECURSION)
    }

    /**
     * Este Metodo es para el CONTROL de SOLAPAMIENTO que al final no he podido implementar.
     * He dejado los intentos al final del código.
     * Comprueba que la casilla donde se va a acolocar la letra esté vacia o sea la misma letra
     * @param r Indice ROW de la casilla
     * @param c Indice COLUMN de la casilla
     * @return true si está disponible la casilla o si es la misma letra que la de la palabra
     */
    private boolean comprobarPosicionLetra(int r, int c){
        boolean disponiple = false;
        if(tablero[r][c] == '\u0000'||tablero[r][c] == letrasPalabra[indexLetra]){
            disponiple = true;
        }
        return disponiple;
    }

    /**
     * Coloca letras aleatorias en las casillas vacias del tablero
     */
    private void generarLetras() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero[i][j] == '\u0000') {   //------El caracter '\u0000' es = a null en char
                    char letra = (char) (random.nextInt(26) + 'A');   //-----Generar una letra aleatoria entre 'A' (ASCII 65) y 'Z' (ASCII 90)
                    tablero[i][j] = letra;
                }
            }
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
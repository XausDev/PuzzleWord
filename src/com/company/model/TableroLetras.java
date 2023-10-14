package com.company.model;

import java.util.ArrayList;
import java.util.Random;

import static com.company.Main.ANSI_GREEN;
import static com.company.Main.ANSI_RESET;

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
     * Primero introducimos las letras de las palabras introducidas por el usuario
     * Luego generamos letras aleatorias para rellenar los indices que faltan con el método generarLetras()
     */
    public void generarTablero() {

        for (Palabra palabraArrayList : palabras) {
            String palabra = palabraArrayList.toString();
            letrasPalabra = palabra.toCharArray();
            colocarPalabra(palabraArrayList);
        }
        generarLetras();
    }


     /**
     * Coloca la palabra en el tablero vacío segun sus índices.
      * No he podido implementar el Control de Solapamiento. He dejado los intentos comentados al final del código.
     * @param palabraArrayList Cada una de las palabras guardadas en el ArrayList
     */
    private void colocarPalabra(Palabra palabraArrayList) {
        int indexRowInit = palabraArrayList.getIndexRowInit();
        int indexRowEnd = palabraArrayList.getIndexRowEnd();

        int indexColumInit = palabraArrayList.getIndexColumnInit();
        int indexColumEnd = palabraArrayList.getIndexColumnEnd();

        indexLetra = 0;

        if (indexRowInit == indexRowEnd) { //Horizontal, i ==
            for (int c = indexColumInit; c <= indexColumEnd; c++) {

                tablero[indexRowInit][c] = letrasPalabra[indexLetra];
                if(indexLetra < letrasPalabra.length-1) {  //----El -1 para que indexLetra no provoque en el else un OutOfBounds Exception
                    indexLetra++;
                }
            }
        } else if(indexColumInit ==indexColumEnd){ //Vertical, j ==
            for (int r = indexRowInit; r <= indexRowEnd; r++) {

                tablero[r][indexColumInit] = letrasPalabra[indexLetra];
                if(indexLetra < letrasPalabra.length-1) {         //----El -1 para que indexLetra no provoque en el else un OutOfBounds Exception
                    indexLetra++;
                }
             }
        }

}

    /**
     * Este Metodo es para el CONTROL de SOLAPAMIENTO que al final no he podido implementar.
     * He dejado los intentos al final del código.
     * Comprueba que la casilla donde se va a acolocar la letra esté vacia o sea la misma letra
     * @param r Indice ROW de la casilla
     * @param c Indice COLUMN de la casilla
     * @return
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

    /**
     * Imprimir la matriz tablero por consola
     */
    public void imprimirTablero() {
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
     */
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

    public void sumarPalabra(Palabra palabra){
        if(!palabra.isDiscovered()) {
            palabrasEncontradas++;
        }
    }

    public boolean finJuego() {
        return palabrasEncontradas == palabras.size();
    }
}



// INTENTOS DE CONTROLAR LOS ERRORES DE SOLAPAMIENTO EN EL MÉTODO colocarPalabra() -------------------------
// LAS DOS OPCIONES TIENEN ERRORES--------------------------------------

    /*private void colocarPalabra(Palabra palabraArrayList) {
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
                    palabraArrayList.calculoIndex();//Vuelve a calcular sus indices
                    colocarPalabra(palabraArrayList);//AQUI ESTÁ EL ERROR (RECURSION)
                }

                //OPCION 2 -------  Borramos todo el tablero
                /*if (!comprobarPosicionLetra(indexRowInit, c)) {//Implementacion del CONTROL DE SOLAPAMIENTOS-------------------------------
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                tablero[i][j] = '\u0000';
                            }
                        }
                        palabraArrayList.calculoIndex();//Vuelve a calcular sus indices
                        generarTablero();//AQUI ESTÁ EL ERROR (RECURSION)
                    } else {
                        tablero[indexRowInit][c] = letrasPalabra[indexLetra];
                        if(indexLetra < letrasPalabra.length-1) {  //----El -1 para que indexLetra no provoque en el else un OutOfBounds Exception
                            indexLetra++;
                    }

                }
            }
        } else if(indexColumInit ==indexColumEnd){ //Vertical, j ==
            for (int r = indexRowInit; r <= indexRowEnd; r++) {

                //OPCION 1 ------- Se borra solo las letras de la palabra actual y se vuelve a colocar

                if (!comprobarPosicionLetra(r, indexColumInit)) {//Implementacion del CONTROL DE SOLAPAMIENTOS-------------------------------
                    int finRow = r;//Para que se guarde la posición en que coincide con otra letra y podamos usarla en el for de borrado
                    for (r = indexRowInit; r < finRow; r++) {//Borra todas las letras anteriores de esta palabra hasta la posición en que ha coincidido con otra
                        tablero[r][indexColumInit] = '\u0000';
                    }
                    palabraArrayList.calculoIndex();//Vuelve a calcular sus indices
                    colocarPalabra(palabraArrayList); //AQUI ESTÁ EL ERROR (RECURSION)
                }

                //OPCION 2 -------  Borramos todo el tablero
                    /*if (!comprobarPosicionLetra(r, indexColumInit)){//Implementacion del CONTROL DE SOLAPAMIENTOS-------------------------------
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                tablero[i][j] = '\u0000';
                            }
                        }
                        palabraArrayList.calculoIndex();//Vuelve a calcular sus indices
                        generarTablero(); //AQUI ESTÁ EL ERROR (RECURSION)
                    }else {
                        tablero[r][indexColumInit] = letrasPalabra[indexLetra];
                        if(indexLetra < letrasPalabra.length-1) {//Para que indexLetra no proboque en el else un OutOfBounds Exception
                            indexLetra++;
                        }
                    }
            }
        }
    }
*/
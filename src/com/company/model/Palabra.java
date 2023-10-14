package com.company.model;

import java.util.Random;

public class Palabra extends PuzzelItem {
    private String palabra;
    private int indiceLetra;

    public Palabra(int indexRowInit, int indexRowEnd, int indexColumnInit, int indexColumneEnd, String palabra) {
        super(indexRowInit, indexRowEnd, indexColumnInit, indexColumneEnd);
        this.palabra = palabra;
        this.indiceLetra = 0;
    }

    public void setIndiceLetra(int indiceLetra) {
        this.indiceLetra = indiceLetra;
    }

    public int getIndiceLetra() {
        return indiceLetra;
    }

    /**
     * Metodo para calcular de forma aleatoria los indices de la palabra.
     * 1º Calculamos un indice aleatorio teniendo en cuenta la
     * length de la palabra y que no se puede salir de la matriz de 10x10,
     * si nos pasamos hacemos un calculo para modificar el indice.
     * 2º Se calcula la direccion (vertical=1 u horizontal=0) con otro número Random.
     * 3º Si es horizontal el indice de la columna incial será el indice calculado anteriormente
     * y el final sera el indice+palabra.lenght menos 1 porque los indices del array empiezan en 0
     * 4º Calculamos otro numero random para el indice vertical, nos colocará la palabra en una fila
     * y el final sera el mismo índice ya que será la misma fila.
     * 5º Si es vertical será el mismo procedimiento pero al contrario
     */
    void calculoIndex(){             //---------Modificador de acceso package private
        Random random = new Random();
        int indice = random.nextInt(10); //--------Calculamos un indice aleatorio

        if(indice+palabra.length()>10){      //-----------Comprobamos no pasarnos de 10 para no salir de la matriz
            int i = (indice+palabra.length())-10;
            indice = indice-i;
        }

        int numeroAleatorio = random.nextInt(2); //-------Aleatoriedad de Horizonal y Vertical (0 o 1)

        if (numeroAleatorio==0){ //Horizontal, i = siempre igual, la misma fila, RowInit = RowEnd
            int indiceVertical = random.nextInt(10);
            setIndexRowInit(indiceVertical);
            setIndexRowEnd(indiceVertical);
            setIndexColumnInit(indice);
            setIndexColumneEnd(indice+palabra.length()-1);

        }else { //Vertical, j = siempre igual, la misma columna, ColumInit = ColumEnd
            int indiceHorizontal = random.nextInt(10);
            setIndexRowInit(indice);
            setIndexRowEnd(indice+palabra.length()-1);
            setIndexColumnInit(indiceHorizontal);
            setIndexColumneEnd(indiceHorizontal);
        }
    }

    @Override
    public int length() {
        return palabra.length();
    }

    /**
     * Compara la letra de la casilla actual (entrada por parametros) con las letras de la palabra que queremos colocar.
     * Si la encuentra devuelve sus indices en el tablero y en la clase TableroLetras los compara la casilla que estamos
     * revisando, si coinciden lo pinta en verde (verificamos letra y posicion).
     * La variable "indiceLetra" es una varible que pertenece a cada palabra que guarda el indice de la última letra
     * que hemos pintado en verde. Es importante cuando hay más de una letra repetida en la palabra.
     * @param letra object to search in the puzzleItem. La letra del tablero que queremos comprobar.
     * @return Los indices de la letra de la palabra en el tablero.
     */
    @Override
    public int[] coordsOfMatch(Object letra) {

        int row = -1;
        int col = -1;

        if (getIndexRowInit() == getIndexRowEnd()) { //Horizontal, Row i ==
            for( int j = indiceLetra; j < palabra.length(); j++){
                if(this.palabra.charAt(j) == (char)letra){
                    row = getIndexRowInit();
                    col = j+getIndexColumnInit();
                    break;
                }
            }

        } else if (getIndexColumnInit() == getIndexColumnEnd()) { //Vertical, Colum j ==
            for(int i = indiceLetra; i < palabra.length(); i++){
                if(this.palabra.charAt(i) == (char)letra){
                    row = i + getIndexRowInit();
                    col = getIndexColumnInit();
                    break;
                }
            }
        }return new int[]{row,col};
    }

    /**
     * Comprueva que la palabra introducida por el usuario sea un objeto Palabra.
     * Significará que la ha encontrado.
     * @param itemToGuess La palabra que prueba el usuario
     * @return False o True segun si la encuentra o no
     */
    @Override
    public boolean tryGuess(Object itemToGuess) {
        if (itemToGuess instanceof String palabraBuscar) {
            return palabraBuscar.equalsIgnoreCase(palabra);
        }
        return false;
    }

    @Override
    public String toString(){
        return palabra;
    }
}

package com.company.model;

import java.util.Random;

public class Palabra extends PuzzelItem {
    private String palabra;

    public Palabra ( int indexRowInit, int indexRowEnd, int indexColumnInit, int indexColumneEnd, String palabra){
        super(indexRowInit, indexRowEnd,indexColumnInit,indexColumneEnd);
        this.palabra = palabra;
    }

    /**
     * Metodo para calcular de forma aleatoria los indices de la palabra.
     * 1º calculamos un indice aleatorio teniendo en cuenta la
     * length de la palabra y que no se puede salir de la matriz de 10x10.
     * 2º Se calcula la direccion (vertical=1 u horizontal=0) con otro número Random.
     * 3º Si es horizontal el indice de la columna incial será el indice calculado anteriormente
     * y el final sera el indice+palabra.lenght menos 1 porque los indices del array empiezan en 0
     * 4º Calculamos otro numero random para el indice vertical, nos colocará la palabra en una fila
     * y el final sera el mismo índice ya que será la misma fila.
     * 5º Si es vertical será el mismo procedimiento pero al contrario
     */
    void calculoIndex(){//Modificador de acceso package private
        Random random = new Random();
        int indice = random.nextInt(10); //Calculamos un indice aleatorio

        if(indice+palabra.length()>10){ //Comprobamos no pasarnos de 10 para no salir de la matriz
            int i = (indice+palabra.length())-10;
            indice = indice-i;
        }

        int numeroAleatorio = random.nextInt(2); //Aleatoriedad de Horizonal y Vertical
        if (numeroAleatorio==0){ //Horizontal, i = siempre igual, la misma fila
            int indiceVertical = random.nextInt(10);
            setIndexRowInit(indiceVertical);
            setIndexRowEnd(indiceVertical);
            setIndexColumnInit(indice);
            setIndexColumneEnd(indice+palabra.length()-1);

        }else { //Vertical, j = siempre igual, la misma columna
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

    @Override
    public int[] coordsOfMatch(Object o) {
        return new int[0];
    }

    @Override
    public boolean tryGuess(Object itemToGuess) {
        return false;
    }
    @Override
    public String toString(){
        return palabra;

    }

}

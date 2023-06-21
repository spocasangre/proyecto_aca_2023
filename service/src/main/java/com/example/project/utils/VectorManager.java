package com.example.project.utils;

import java.util.Random;

public class VectorManager {

    char vector[];
    private Random r;

    public VectorManager(char vector[]) {
        this.vector = vector;
        initRandom();
    }

    private void initRandom() {
        r = new Random();
    }

    /**
     * Método que se encarga de devolver una letra tipo char del vector en
     * cuestión según una posición generada de manera random
     *
     * @return un char con la letra
     */
    public char letraRandom() {
        int numeroPos = r.nextInt(vector.length);
        return vector[numeroPos];
    }
}

/*
 * Copyright 2023 WeGotYou!
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

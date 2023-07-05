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

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

public class GeneradorNombre {

    private VectorManager vocales;
    private VectorManager consonantes;

    private Random r;

    public GeneradorNombre() {
        initVector();
        initRandom();
    }

    private void initRandom() {
        r = new Random();
    }

    private void initVector() {
        char tempv[] = {'a', 'e', 'i', 'o', 'u'};
        char tempc[] = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'ñ', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};
        vocales = new VectorManager(tempv);
        consonantes = new VectorManager(tempc);
    }

    /**
     * Método que genera un número random, según el tamaño máximo
     */
    private int generarTamanno(int maximo) {
        if (maximo <= 3) {
            return 3;
        }
        int tam = r.nextInt(maximo - 3) + 3;
        return tam;
    }

    /**
     * Método que se encarga de formar el nombre alternando entre vocales y
     * consonantes de manera random
     *
     * @param tamannoMax recibe el tamaño máximo del nombre
     * @return el nombre formado
     */
    public String crearNombre(int tamannoMax) {
        String nombre = "";
        tamannoMax = generarTamanno(tamannoMax);
        boolean vocal = r.nextBoolean();
        for (int i = 0; i <= tamannoMax; i++) {
            if (vocal) {
                nombre += vocales.letraRandom();
            } else {
                nombre += consonantes.letraRandom();
            }
            vocal = !vocal;
        }
        return nombre;
    }
    
    /**
     * Método que altera el nombre de la imagen agregandole 4 letras ramdon
     * 
     * @param file recibe el MultipartFile
     * @return el nombreCompleto formado
     */
    public String alteraNombre(MultipartFile file) {
    	String nombreImagen, extensionImagen, sufijoImagen, nombreCompleto;
    	
    	nombreImagen = FilenameUtils.getBaseName(file.getOriginalFilename());
		extensionImagen = FilenameUtils.getExtension(file.getOriginalFilename());
		sufijoImagen = this.crearNombre(4);
		nombreCompleto = nombreImagen+"_"+sufijoImagen+"."+extensionImagen;
		
		return nombreCompleto;
    }
    
    public String generaNombre(MultipartFile file) {
    	String nombre, extension, nombreImagen;
    	nombre = FilenameUtils.getBaseName(file.getOriginalFilename());
		extension = FilenameUtils.getExtension(file.getOriginalFilename());
		nombreImagen = nombre+"."+extension;
		return nombreImagen;
    }
}

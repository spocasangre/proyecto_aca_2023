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
package com.example.project;

import com.example.project.image.FilesStorageService;
import com.example.project.models.entities.Coordinate;
import com.example.project.models.entities.GeoZone;
import com.example.project.models.entities.Rol;
import com.example.project.repositories.CoordenadasRepository;
import com.example.project.repositories.GeozoneRepository;
import com.example.project.repositories.RolRepository;
import com.example.project.repositories.UserRepository;
import freemarker.core.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableTransactionManagement
public class ProjectApplication implements CommandLineRunner {


	@Resource
	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
	}

	@Component
	public static class DataLoader implements ApplicationRunner {

		@Autowired
		private RolRepository rolRepository;

		@Autowired
		private GeozoneRepository geozoneRepository;

		@Autowired
		private CoordenadasRepository coordenadasRepository;

		/*@Autowired
		private UserRepository userRepository;

		@Autowired
		PasswordEncoder encoder;*/

		@Override
		public void run(ApplicationArguments args) throws Exception {
			//Añadiendo Roles:
			rolRepository.save(new Rol(1L,"usuario"));
			rolRepository.save(new Rol(2L,"admin"));

			//Añadiendo geozona
			GeoZone test1 = new GeoZone();
			test1.setIdGeozone(1L);
			test1.setNombre("La ceiba de Guadalupe, Antiguo Cuscatlán");
			test1.setDescripcion("Zona que rodean dicha ubicación");
			Long test1Id = geozoneRepository.save(test1).getIdGeozone();

			//Añadiendo coordenadas a la geozona
			Coordinate c1forTest1 = new Coordinate();
			Coordinate c2forTest1 = new Coordinate();
			Coordinate c3forTest1 = new Coordinate();
			Coordinate c4forTest1 = new Coordinate();
			Coordinate c5forTest1 = new Coordinate();
			Coordinate c6forTest1 = new Coordinate();
			Coordinate c7forTest1 = new Coordinate();
			Coordinate c8forTest1 = new Coordinate();

			c1forTest1.setId_coordenada(1L);
			c1forTest1.setLatitud(13.6812040);
			c1forTest1.setLongitud(-89.2431289);
			c1forTest1.setGeozona(test1);

			c2forTest1.setId_coordenada(2L);
			c2forTest1.setLatitud(13.6810268);
			c2forTest1.setLongitud(-89.2430270);
			c2forTest1.setGeozona(test1);

			c3forTest1.setId_coordenada(3L);
			c3forTest1.setLatitud(13.6811102);
			c3forTest1.setLongitud(-89.2426139);
			c3forTest1.setGeozona(test1);

			c4forTest1.setId_coordenada(4L);
			c4forTest1.setLatitud(13.6811988);
			c4forTest1.setLongitud(-89.2425013);
			c4forTest1.setGeozona(test1);

			c5forTest1.setId_coordenada(5L);
			c5forTest1.setLatitud(13.6815323);
			c5forTest1.setLongitud(-89.2424315);
			c5forTest1.setGeozona(test1);

			c6forTest1.setId_coordenada(6L);
			c6forTest1.setLatitud(13.6819387);
			c6forTest1.setLongitud(-89.2420989);
			c6forTest1.setGeozona(test1);

			c7forTest1.setId_coordenada(7L);
			c7forTest1.setLatitud(13.6821367);
			c7forTest1.setLongitud(-89.2423671);
			c7forTest1.setGeozona(test1);

			c8forTest1.setId_coordenada(8L);
			c8forTest1.setLatitud(13.6812040);
			c8forTest1.setLongitud(-89.2431289);
			c8forTest1.setGeozona(test1);
			coordenadasRepository.save(c1forTest1);
			coordenadasRepository.save(c2forTest1);
			coordenadasRepository.save(c3forTest1);
			coordenadasRepository.save(c4forTest1);
			coordenadasRepository.save(c5forTest1);
			coordenadasRepository.save(c6forTest1);
			coordenadasRepository.save(c7forTest1);
			coordenadasRepository.save(c8forTest1);

		}
	}

}

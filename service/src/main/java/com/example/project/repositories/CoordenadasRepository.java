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
package com.example.project.repositories;

import com.example.project.models.entities.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoordenadasRepository extends JpaRepository<Coordinate, Long> {

    @Query("SELECT c from coordenada c WHERE c.geozona.idGeozone = ?1 AND" +
            " c.geozona.isActive = TRUE")
    List<Coordinate> getGeozoneCoordinates(Long id);
}

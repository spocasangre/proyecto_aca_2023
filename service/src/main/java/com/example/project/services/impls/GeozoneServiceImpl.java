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
package com.example.project.services.impls;

import com.example.project.models.dtos.CreateGeozoneDTO;
import com.example.project.models.entities.GeoZone;
import com.example.project.repositories.GeozoneRepository;
import com.example.project.services.GeozoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeozoneServiceImpl implements GeozoneService {

    @Autowired
    private GeozoneRepository geozoneRepository;
    @Override
    public List<GeoZone> getAll() {
        return geozoneRepository.findAll();
    }

    @Override
    public List<GeoZone> getAllActive() {
        return geozoneRepository.findAllActive();
    }

    @Override
    public GeoZone getOneById(Long id) {
        return geozoneRepository.findById(id).get();
    }

    @Override
    public GeoZone createGeozone(CreateGeozoneDTO createGeozoneDTO) {
        GeoZone nuevaGeo = new GeoZone(createGeozoneDTO.getName(),
                createGeozoneDTO.getDesc());
        return geozoneRepository.save(nuevaGeo);
    }

    @Override
    public GeoZone activateGeozone(Long id) {
        GeoZone foundGeo = geozoneRepository.findById(id).get();
        foundGeo.setActive(true);
        return geozoneRepository.save(foundGeo);
    }

    @Override
    public GeoZone deactivateGeozone(Long id) {
        GeoZone foundGeo = geozoneRepository.findById(id).get();
        foundGeo.setActive(false);
        return geozoneRepository.save(foundGeo);
    }

}

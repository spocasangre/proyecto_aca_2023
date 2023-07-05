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

import com.example.project.models.entities.Location;
import com.example.project.repositories.LocationRepository;
import com.example.project.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public Location getLocationDetail(Long id) {
        return locationRepository.getLocationDetail(id);
    }

    @Override
    public Void saveLocation(Location location) {
        locationRepository.save(location);
        return null;
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location findById(Long id) {
        Location location = locationRepository.findById(id).get();
        return location;
    }

    @Override
    public Location getLocationDetailByMapId(String id) {
        return locationRepository.getLocationDetailByMapId(id);
    }
}

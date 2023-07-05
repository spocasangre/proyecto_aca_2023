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

import com.example.project.models.entities.AdvisorType;
import com.example.project.models.entities.Advisory;
import com.example.project.repositories.AdvisoryRepository;
import com.example.project.services.AdvisoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvisoryServiceImpl implements AdvisoryService {

    @Autowired
    AdvisoryRepository advisoryRepository;

    @Override
    public List<Advisory> getAdvisoriesByAdvisorType(AdvisorType advisorType) {
        List<Advisory> advisoryList = advisoryRepository.getAdvisoriesByAdvisorType(advisorType).get();
        return advisoryList;
    }

    @Override
    public Void save(Advisory advisory) {
        advisoryRepository.save(advisory);
        return null;
    }

    @Override
    public Advisory findById(Long id) {
        return advisoryRepository.findById(id).get();
    }

    @Override
    public Void delete(Advisory advisory) {
        advisoryRepository.delete(advisory);
        return null;
    }
}

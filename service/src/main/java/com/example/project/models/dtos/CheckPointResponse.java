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
package com.example.project.models.dtos;

public class CheckPointResponse {

    private Boolean response;

    private Long idGeozone;

    public CheckPointResponse(){
        super();
    }

    public CheckPointResponse(Boolean response, Long idGeozone) {
        this.response = response;
        this.idGeozone = idGeozone;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public Long getIdGeozone() {
        return idGeozone;
    }

    public void setIdGeozone(Long idGeozone) {
        this.idGeozone = idGeozone;
    }
}

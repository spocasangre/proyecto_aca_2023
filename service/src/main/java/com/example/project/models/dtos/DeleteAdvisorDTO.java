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

import javax.validation.constraints.NotNull;

public class DeleteAdvisorDTO {

    @NotNull
    private Integer id_advisor;

    public DeleteAdvisorDTO(Integer id_advisor) {
        super();
        this.id_advisor = id_advisor;
    }

    public Integer getId_advisor() {
        return id_advisor;
    }

    public void setId_advisor(Integer id_advisor) {
        this.id_advisor = id_advisor;
    }
}

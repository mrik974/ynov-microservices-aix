/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.dto.PetTypeDto;
import org.springframework.samples.petclinic.mapper.PetTypeMapper;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/pettypes")
public class PetTypeRestController {

    private final ClinicService clinicService;
    private final PetTypeMapper petTypeMapper;


    public PetTypeRestController(ClinicService clinicService, PetTypeMapper petTypeMapper) {
        this.clinicService = clinicService;
        this.petTypeMapper = petTypeMapper;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<PetTypeDto>> getAllPetTypes() {
        Collection<PetType> petTypes = new ArrayList<PetType>();
        petTypes.addAll(this.clinicService.findAllPetTypes());
        if (petTypes.isEmpty()) {
            return new ResponseEntity<Collection<PetTypeDto>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<PetTypeDto>>(petTypeMapper.toPetTypeDtos(petTypes), HttpStatus.OK);
    }

    @RequestMapping(value = "/{petTypeId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<PetTypeDto> getPetType(@PathVariable("petTypeId") int petTypeId) {
        PetType petType = this.clinicService.findPetTypeById(petTypeId);
        if (petType == null) {
            return new ResponseEntity<PetTypeDto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PetTypeDto>(petTypeMapper.toPetTypeDto(petType), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<PetTypeDto> addPetType(@RequestBody @Valid PetTypeDto petType, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (petType == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<PetTypeDto>(headers, HttpStatus.BAD_REQUEST);
        }
        final PetType type = petTypeMapper.toPetType(petType);
        this.clinicService.savePetType(type);
        headers.setLocation(ucBuilder.path("/api/pettypes/{id}").buildAndExpand(petType.getId()).toUri());
        return new ResponseEntity<PetTypeDto>(petTypeMapper.toPetTypeDto(type), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{petTypeId}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<PetTypeDto> updatePetType(@PathVariable("petTypeId") int petTypeId, @RequestBody @Valid PetTypeDto petType, BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (petType == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<PetTypeDto>(headers, HttpStatus.BAD_REQUEST);
        }
        PetType currentPetType = this.clinicService.findPetTypeById(petTypeId);
        if (currentPetType == null) {
            return new ResponseEntity<PetTypeDto>(HttpStatus.NOT_FOUND);
        }
        currentPetType.setName(petType.getName());
        this.clinicService.savePetType(currentPetType);
        return new ResponseEntity<PetTypeDto>(petTypeMapper.toPetTypeDto(currentPetType), HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{petTypeId}", method = RequestMethod.DELETE, produces = "application/json")
    @Transactional
    public ResponseEntity<Void> deletePetType(@PathVariable("petTypeId") int petTypeId) {
        PetType petType = this.clinicService.findPetTypeById(petTypeId);
        if (petType == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deletePetType(petType);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
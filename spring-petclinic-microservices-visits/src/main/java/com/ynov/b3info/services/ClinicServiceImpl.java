/*
 * Copyright 2002-2017 the original author or authors.
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
package com.ynov.b3info.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynov.b3info.models.Visit;
import com.ynov.b3info.repositories.VisitRepository;

/**
 * Mostly used as a facade for all Petclinic controllers
 * Also a placeholder for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 */
@Service

public class ClinicServiceImpl implements ClinicService {

    private VisitRepository visitRepository;

    @Autowired
     public ClinicServiceImpl(
    		 VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

	@Override
	@Transactional(readOnly = true)
	public Visit findVisitById(int visitId) throws DataAccessException {
		Visit visit = null;
		try {
			visit = visitRepository.findById(visitId);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return visit;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Visit> findAllVisits() throws DataAccessException {
		return visitRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteVisit(Visit visit) throws DataAccessException {
		visitRepository.delete(visit);
	}

	@Override
	@Transactional
	public void saveVisit(Visit visit) throws DataAccessException {
		visitRepository.save(visit);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}

}

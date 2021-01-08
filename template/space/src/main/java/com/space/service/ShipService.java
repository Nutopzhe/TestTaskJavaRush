package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

public interface ShipService {
    //main methods
    ResponseEntity getAllShips(Specification spec, Pageable pageable);
    ResponseEntity getShipById (Long id);
    ResponseEntity deleteShipById (Long id);
    ResponseEntity updateShipById (Long id, Ship ship);
    ResponseEntity createNewShip (Ship newShip);
    ResponseEntity getShipsCount (Specification spec);
    Double ratingCalculator (Ship ship);

    //validation methods
    Boolean isExistingShip (Long id);
    Boolean isValidName (Ship ship);
    Boolean isValidPlanet (Ship ship);
    Boolean isValidSpeed (Ship ship);
    Boolean isValidCrewSize (Ship ship);
    Boolean isValidProdDate (Ship ship);

    //Specifications for dynamic filtration, see scripts.js
    Specification<Ship> filterByName(String name);
    Specification<Ship> filterByPlanet(String name);
    Specification<Ship> filterByType(ShipType shipType);
    Specification<Ship> filterByProdDate(Long prodYearAfter, Long prodDateBefore);
    Specification<Ship> filterByUsage(Boolean isUsed);
    Specification<Ship> filterBySpeed(Double speedMin, Double speedMax);
    Specification<Ship> filterByCrewSize(Integer crewSizeMin, Integer crewSizeMax);
    Specification<Ship> filterByRating(Double ratingMin, Double ratingMax);

}

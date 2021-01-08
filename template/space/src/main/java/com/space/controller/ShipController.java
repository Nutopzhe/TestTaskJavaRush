package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest")
public class ShipController {
    private ShipService shipService;

    @Autowired
    public void setShipService(ShipService shipService) {
        this.shipService = shipService;
    }

    @RequestMapping(value = "/ships", method = RequestMethod.GET)
    public ResponseEntity getAll(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "planet", required = false) String planet,
                                 @RequestParam(value = "shipType", required = false) ShipType shipType,
                                 @RequestParam(value = "after", required = false) Long prodYearAfter,
                                 @RequestParam(value = "before", required = false) Long prodYearBefore,
                                 @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                 @RequestParam(value = "minSpeed", required = false) Double speedMin,
                                 @RequestParam(value = "maxSpeed", required = false) Double speedMax,
                                 @RequestParam(value = "minCrewSize", required = false) Integer crewSizeMin,
                                 @RequestParam(value = "maxCrewSize", required = false) Integer crewSizeMax,
                                 @RequestParam(value = "minRating", required = false) Double ratingMin,
                                 @RequestParam(value = "maxRating", required = false) Double ratingMax,
                                 @RequestParam(required = false, defaultValue = "ID") ShipOrder order,
                                 @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                 @RequestParam(required = false, defaultValue = "3") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        return shipService.getAllShips(Specification.where(shipService.filterByName(name)
                        .and(shipService.filterByPlanet(planet))
                        .and(shipService.filterByProdDate(prodYearAfter, prodYearBefore))
                        .and(shipService.filterByCrewSize(crewSizeMin, crewSizeMax))
                        .and(shipService.filterByUsage(isUsed))
                        .and(shipService.filterBySpeed(speedMin, speedMax))
                        .and(shipService.filterByRating(ratingMin, ratingMax))
                        .and(shipService.filterByType(shipType))),
                pageable);
    }

    @RequestMapping(value = "/ships/count", method = RequestMethod.GET)
    public ResponseEntity getShipsCount(@RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "planet", required = false) String planet,
                                        @RequestParam(value = "shipType", required = false) ShipType shipType,
                                        @RequestParam(value = "after", required = false) Long prodYearAfter,
                                        @RequestParam(value = "before", required = false) Long prodYearBefore,
                                        @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                        @RequestParam(value = "minSpeed", required = false) Double speedMin,
                                        @RequestParam(value = "maxSpeed", required = false) Double speedMax,
                                        @RequestParam(value = "minCrewSize", required = false) Integer crewSizeMin,
                                        @RequestParam(value = "maxCrewSize", required = false) Integer crewSizeMax,
                                        @RequestParam(value = "minRating", required = false) Double ratingMin,
                                        @RequestParam(value = "maxRating", required = false) Double ratingMax) {
        return shipService.getShipsCount(Specification.where(shipService.filterByName(name)
                .and(shipService.filterByPlanet(planet))
                .and(shipService.filterByProdDate(prodYearAfter, prodYearBefore))
                .and(shipService.filterByCrewSize(crewSizeMin, crewSizeMax))
                .and(shipService.filterByUsage(isUsed))
                .and(shipService.filterBySpeed(speedMin, speedMax))
                .and(shipService.filterByRating(ratingMin, ratingMax))
                .and(shipService.filterByType(shipType))));
    }

    @RequestMapping(value = "/ships/{id}", method = RequestMethod.GET)
    public ResponseEntity getShipById(@PathVariable(value = "id") Long id) {
        if (id <= 0 || id == null) {
            return ResponseEntity.badRequest().build();
        } else if (!shipService.isExistingShip(id)) {
            return ResponseEntity.notFound().build();
        } else return shipService.getShipById(id);
    }

    @RequestMapping(value = "ships", method = RequestMethod.POST)
    public ResponseEntity createNewShip(@RequestBody Ship newShip) {
        return shipService.createNewShip(newShip);
    }

    @RequestMapping(value = "/ships/{id}", method = RequestMethod.POST)
    public ResponseEntity updateShipById(@PathVariable(value = "id") Long id, @RequestBody Ship newShip) {
        return shipService.updateShipById(id, newShip);
    }

    @RequestMapping(value = "/ships/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteShipById(@PathVariable(value = "id") Long id) {
        if (id <= 0 || id == null) {
            return ResponseEntity.badRequest().build();
        } else if (!shipService.isExistingShip(id)) {
            return ResponseEntity.notFound().build();
        } else return shipService.deleteShipById(id);
    }
}

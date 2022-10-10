package com.neoflex.VacationPayCalculator.controller;

import com.neoflex.VacationPayCalculator.entity.VacationEntity;
import com.neoflex.VacationPayCalculator.exception.IncorrectDataVacationException;
import com.neoflex.VacationPayCalculator.service.VacationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/vacation")
public class VacationController {

    private final VacationService service;

    public VacationController(VacationService service) {
        this.service = service;
    }

    @GetMapping("/calculate")
    public ResponseEntity<?> getVacation(@RequestBody VacationEntity entity) {
        try {
            return new ResponseEntity<>(service.getVacation(entity), HttpStatus.OK);
        } catch (IncorrectDataVacationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

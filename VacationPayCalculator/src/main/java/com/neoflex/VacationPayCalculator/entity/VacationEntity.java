package com.neoflex.VacationPayCalculator.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class VacationEntity {

    Integer vacationDays;
    Double salary;
    LocalDate startVacation;
    LocalDate endVacation;

    public VacationEntity(Integer vacationDays, Double salary) {
        this.vacationDays = vacationDays;
        this.salary = salary;
    }

    public VacationEntity(Double salary, LocalDate startVacation, LocalDate endVacation) {
        this.salary = salary;
        this.startVacation = startVacation;
        this.endVacation = endVacation;
    }
}

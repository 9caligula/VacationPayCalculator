package com.neoflex.VacationPayCalculator.service;

import com.neoflex.VacationPayCalculator.client.DayOffFeignClient;
import com.neoflex.VacationPayCalculator.entity.VacationEntity;
import com.neoflex.VacationPayCalculator.exception.IncorrectDataVacationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

class VacationServiceTest {

    @Mock
    private DayOffFeignClient dayOffFeignClient;

    private VacationService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new VacationService(dayOffFeignClient);
    }

    @Test
    void canGetVacationWhenExactDaysAreUnknown() {
        // Given
        VacationEntity entity = new VacationEntity(7, 100000.0);
        // When
        String actual = underTest.getVacation(entity);
        // Then
        assertThat(actual).isEqualTo("23890,78");
    }

    @Test
    void canGetVacationWhenExactDaysAreKnown() {
        // Given
        VacationEntity entity = new VacationEntity(100000.0,
                LocalDate.of(2022, 10, 10),
                LocalDate.of(2022, 10, 15)
        );
        LocalDate day = entity.getStartVacation();
        for (int i = 1; i < 6; i++) {
            given(dayOffFeignClient.getDayOff(day)).willReturn("0");
            day = entity.getStartVacation().plusDays(i);
        }
        // When
        String actual = underTest.getVacation(entity);
        // Then
        assertThat(actual).isEqualTo("17064,85");
    }

    @Test
    void cannotGetVacationWhenVacationEmpty() {
        // Given
        VacationEntity entity = new VacationEntity();
        // When
        Throwable thrown = assertThrows(IncorrectDataVacationException.class, () -> {
            underTest.getVacation(entity);
        });
        // Then
        assertNotNull(thrown.getMessage());
    }
}
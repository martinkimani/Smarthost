/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarthost.service;

import com.smarthost.model.Guest;
import com.smarthost.model.VacancyUsage;
import com.smarthost.repository.GuestsRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author martin
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class BookingServiceTest {
    
    @Autowired
    IBookingService bookingService;
    
    @Autowired
    GuestsRepository guestRepository;
    
    @BeforeAll
    void generateGuestList() {
        List<Guest> guests = Arrays.asList(
                new Guest("guest1", 23),
                new Guest("guest2", 45),
                new Guest("guest3", 155),
                new Guest("guest4", 374),
                new Guest("guest5", 22),
                new Guest("guest6", 99),
                new Guest("guest7", 100),
                new Guest("guest8", 101),
                new Guest("guest9", 115),
                new Guest("guest10", 209));
        guestRepository.saveAll(guests);
    }
    
    @Test
    @DisplayName("Tests for correct occupancy projections")
    void projectionsCorrectnessTest() {
        VacancyUsage vacancyUsage = bookingService.analyzeBookings(3, 3);
        Assertions.assertEquals("Usage Premium: 3 (EUR 738)", vacancyUsage.getPremiumUsage(), "Premium: should return 3 as usage and 738 as amount");
        Assertions.assertEquals("Usage Economy: 3 (EUR 167)", vacancyUsage.getEconomyUsage(), "Economy: should return 3 as usage and 167 as amount");
        
        VacancyUsage vacancyUsage1 = bookingService.analyzeBookings(7, 5);
        Assertions.assertEquals("Usage Premium: 6 (EUR 1054)", vacancyUsage1.getPremiumUsage(), "Premium: should return 6 as usage and 1054 as amount");
        Assertions.assertEquals("Usage Economy: 4 (EUR 189)", vacancyUsage1.getEconomyUsage(), "Economy: should return 4 as usage and 189 as amount");
        
        VacancyUsage vacancyUsage2 = bookingService.analyzeBookings(2, 7);
        Assertions.assertEquals("Usage Premium: 2 (EUR 583)", vacancyUsage2.getPremiumUsage(), "Premium: should return 2 as usage and 583 as amount");
        Assertions.assertEquals("Usage Economy: 4 (EUR 189)", vacancyUsage2.getEconomyUsage(), "Economy: should return 4 as usage and 189 as amount");
        
        VacancyUsage vacancyUsage3 = bookingService.analyzeBookings(7, 1);
        Assertions.assertEquals("Usage Premium: 7 (EUR 1153)", vacancyUsage3.getPremiumUsage(), "Premium: should return 7 as usage and 1153 as amount");
        Assertions.assertEquals("Usage Economy: 1 (EUR 45)", vacancyUsage3.getEconomyUsage(), "Economy: should return 1 as usage and 45 as amount");
    }
    
    @Test
    @DisplayName("Tests for zero and negative occupancy cases")
    void edgeCasesTest() {
        VacancyUsage vacancyUsage = bookingService.analyzeBookings(0, 0);
        Assertions.assertEquals("Usage Premium: 0 (EUR 0)", vacancyUsage.getPremiumUsage(), "Premium: should return 0 as usage and 0 as amount");
        Assertions.assertEquals("Usage Economy: 0 (EUR 0)", vacancyUsage.getEconomyUsage(), "Economy: should return 0 as usage and 0 as amount");
        
        VacancyUsage vacancyUsage1 = bookingService.analyzeBookings(-7, -5);
        Assertions.assertEquals("Usage Premium: 0 (EUR 0)", vacancyUsage1.getPremiumUsage(), "Premium: should return 0 as usage and 0 as amount");
        Assertions.assertEquals("Usage Economy: 0 (EUR 0)", vacancyUsage1.getEconomyUsage(), "Economy: should return 0 as usage and 0 as amount");
    }
}

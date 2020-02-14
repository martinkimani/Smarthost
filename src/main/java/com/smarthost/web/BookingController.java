/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarthost.web;

import com.smarthost.model.Vacancy;
import com.smarthost.service.IBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author martin
 */
@RestController
@RequestMapping("/bookings")
public class BookingController {
    
    private final IBookingService bookingService;
    
    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    @PostMapping("/analyze-bookings")
    public ResponseEntity getBookingAnalysis(@RequestBody Vacancy vacancies) {
        return new ResponseEntity(bookingService.analyzeBookings(vacancies.getPremiumVacancies(), vacancies.getEconomyVacancies()), HttpStatus.OK);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarthost.service;

import com.smarthost.model.Vacancy;
import com.smarthost.model.VacancyUsage;

/**
 *
 * @author martin
 */
public interface IBookingService {
    
    VacancyUsage analyzeBookings(int premiumVacancies, int economyVacancies);
}

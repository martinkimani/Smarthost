/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarthost.service.impl;

import com.smarthost.repository.GuestsRepository;
import com.smarthost.model.Guest;
import com.smarthost.model.VacancyUsage;
import com.smarthost.service.IBookingService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author martin
 */
@Service
public class BookingServiceImpl implements IBookingService {

    private final GuestsRepository guestRepository;

    public BookingServiceImpl(GuestsRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Override
    public VacancyUsage analyzeBookings(int premiumVacancies, int economyVacancies) {
        Integer[] prices = returnUserPrices();
        var premiumUsage = 0;
        var premiumAmount = 0;
        var economyUsage = 0;
        var economyAmount = 0;

        Arrays.sort(prices);
        for (int i = prices.length - 1; i >= 0; i--) {
            if (premiumVacancies > 0 && (prices[i] >= 100 || (prices[i] < 100 && i >= economyVacancies))) {
                premiumUsage++;
                premiumAmount += prices[i];
                premiumVacancies--;
            } else if(premiumVacancies == 0) break;
        }

        int econStartingPoint = prices.length - premiumUsage - 1;
        for (int i = econStartingPoint; i >= 0; i--) {
            if (economyVacancies > 0 && prices[i] < 100) {
                economyUsage++;
                economyAmount += prices[i];
                economyVacancies--;
            } else if (economyVacancies == 0) break;
        }
        var premiumUsageResponse = String.format("Usage Premium: %d (EUR %d)", premiumUsage, premiumAmount);
        var economyUsageResponse = String.format("Usage Economy: %d (EUR %d)", economyUsage, economyAmount);
        return VacancyUsage.builder().premiumUsage(premiumUsageResponse).economyUsage(economyUsageResponse).build();

    }

    public Integer[] returnUserPrices() {
        List<Guest> guests = guestRepository.findAll();
        List<Integer> pricesList = guests.stream().map(guest -> guest.getPrice()).collect(Collectors.toList());
        return pricesList.stream().toArray(Integer[]::new);
    }

}

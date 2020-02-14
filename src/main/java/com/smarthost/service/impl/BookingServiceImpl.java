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
        var premium_usage = 0;
        var premium_amount = 0;
        var economy_usage = 0;
        var economy_amount = 0;

        Arrays.sort(prices);
        for (int i = prices.length - 1; i >= 0; i--) {
            if (premiumVacancies > 0 && (prices[i] >= 100 || (prices[i] < 100 && i >= economyVacancies))) {
                premium_usage++;
                premium_amount += prices[i];
                premiumVacancies--;
            }
            if (premiumVacancies == 0) break;
        }

        int econ_start = prices.length - premium_usage - 1;
        for (int i = econ_start; i >= 0; i--) {
            if (economyVacancies > 0 && prices[i] < 100) {
                economy_usage++;
                economy_amount += prices[i];
                economyVacancies--;
            }
            if (economyVacancies == 0) break;
        }
        var premiumUsage = String.format("Usage Premium: %d (EUR %d)", premium_usage, premium_amount);
        var EconomyUsage = String.format("Usage Economy: %d (EUR %d)", economy_usage, economy_amount);
        return VacancyUsage.builder().premiumUsage(premiumUsage).economyUsage(EconomyUsage).build();

    }

    public Integer[] returnUserPrices() {
        List<Guest> guests = guestRepository.findAll();
        List<Integer> pricesList = guests.stream().map(guest -> guest.getPrice()).collect(Collectors.toList());
        return pricesList.stream().toArray(Integer[]::new);
    }

}

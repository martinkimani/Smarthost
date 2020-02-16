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
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
    public VacancyUsage analyzeBookings(int premiumRooms, int economyRooms) {
        var premiumVacancies = premiumRooms < 0 ? 0 : premiumRooms;
        var economyVacancies = economyRooms < 0 ? 0 : economyRooms;
        List<Integer> prices = returnUserPrices();
        AtomicInteger index = new AtomicInteger();
        index.set(prices.size()-1);
        IntSummaryStatistics premiumSummary = prices.stream()
                .sorted(Comparator.reverseOrder())
                .filter(price-> (index.decrementAndGet()>= economyVacancies && price < 100)  || price >= 100)
                .limit(premiumVacancies)
                .mapToInt(Integer::intValue)
                .summaryStatistics();
        
        var premiumUsage = premiumSummary.getCount();
        IntSummaryStatistics economySummary = prices.stream()
                .sorted(Comparator.reverseOrder())
                .skip(premiumUsage)
                .filter(price -> price < 100)
                .limit(economyVacancies)
                .mapToInt(Integer::intValue)
                .summaryStatistics();
        
        var premiumUsageResponse = String.format("Usage Premium: %d (EUR %d)", premiumSummary.getCount(), premiumSummary.getSum());
        var economyUsageResponse = String.format("Usage Economy: %d (EUR %d)", economySummary.getCount(), economySummary.getSum());
        return VacancyUsage.builder().premiumUsage(premiumUsageResponse).economyUsage(economyUsageResponse).build();

    }

    public List<Integer> returnUserPrices() {
        List<Guest> guests = guestRepository.findAll();
        return guests.stream().map(guest -> guest.getPrice()).collect(Collectors.toList());
    }

}

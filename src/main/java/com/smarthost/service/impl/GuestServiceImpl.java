/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarthost.service.impl;

import com.smarthost.model.Guest;
import com.smarthost.model.dto.NewGuestDto;
import com.smarthost.repository.GuestsRepository;
import com.smarthost.service.IGuestService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author martin
 */
@Service
public class GuestServiceImpl implements IGuestService{
    
    private final GuestsRepository guestRepository;
    
    public GuestServiceImpl(GuestsRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Override
    public Guest saveGuest(NewGuestDto newGuest) {
        Guest guest = new Guest(newGuest.getName(), newGuest.getPrice());
        return guestRepository.save(guest);
    }

    @Override
    public List<Guest> listAllGuests() {
        return guestRepository.findAll();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarthost.service;

import com.smarthost.model.Guest;
import com.smarthost.model.dto.NewGuestDto;
import java.util.List;

/**
 *
 * @author martin
 */
public interface IGuestService {
    
    Guest saveGuest(NewGuestDto newGuest);
    
    List<Guest> listAllGuests();
}

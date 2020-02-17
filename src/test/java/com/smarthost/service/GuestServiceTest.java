/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarthost.service;

import com.smarthost.model.Guest;
import com.smarthost.model.dto.NewGuestDto;
import com.smarthost.repository.GuestsRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author martin
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GuestServiceTest {
    
    @Autowired
    IGuestService guestService;
    
    @MockBean
    GuestsRepository guestRepository;
    
    @Test
    @DisplayName("Test save Guest Success")
    void saveGuestSuccess() throws Exception {
        NewGuestDto newGuest = NewGuestDto.builder().name("test Guest").price(95).build();
        Guest mockGuest = new Guest(1, "test Guest", 95);
        doReturn(mockGuest).when(guestRepository).save(any());
        
        Guest returnedGuest = guestService.saveGuest(newGuest);
        
        Assertions.assertNotNull(returnedGuest, "Guest was not found when they should be");
        Assertions.assertEquals(95, returnedGuest.getPrice(), "Price should be 95");
        Assertions.assertEquals("test Guest", returnedGuest.getName(), "guest name should be test Guest");
    }
    
    @Test
    @DisplayName("Test listing all guests -Success")
    void listAllGuestsSuccessTest() throws Exception {
        Guest mockGuest = new Guest(1, "test Guest", 95);
        Guest mockGuest2 = new Guest(2, "test Guest2", 300);
        doReturn(Arrays.asList(new Guest[]{mockGuest, mockGuest2})).when(guestRepository).findAll();
        
        List<Guest> returnedAllGuests = guestService.listAllGuests();
        
        Assertions.assertEquals(2, returnedAllGuests.size(), "guests found should be 2");
    }
}

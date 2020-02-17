/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarthost.web;

import com.smarthost.model.Guest;
import com.smarthost.model.dto.NewGuestDto;
import com.smarthost.service.IGuestService;
import java.util.Arrays;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.doReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;

/**
 *
 * @author martin
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GuestControllerTest extends AbstractController{
    
    @MockBean
    private IGuestService guestService;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @DisplayName("POST /guests/add -Success")
    void addGuestSuccessTest() throws Exception {
        NewGuestDto postGuest = NewGuestDto.builder().name("test Guest").price(95).build();
        Guest mockGuest = new Guest(1, "test Guest", 95);
        doReturn(mockGuest).when(guestService).saveGuest(any());
        
        mockMvc.perform(post("/guests/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(postGuest)))
                
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("test Guest")))
        .andExpect(jsonPath("$.price", is(95)));
    }
    
    @Test
    @DisplayName("GET /guests/all -Success")
    void listAllGuestsSuccessTest() throws Exception {
        Guest mockGuest = new Guest(1, "test Guest", 95);
        Guest mockGuest2 = new Guest(2, "test Guest2", 300);
        doReturn(Arrays.asList(new Guest[]{mockGuest, mockGuest2})).when(guestService).listAllGuests();
        
        mockMvc.perform(get("/guests/all"))
                
        .andExpect(status().isOk())

        .andExpect(jsonPath("$.[0]id", is(1)))
        .andExpect(jsonPath("$.[0]name", is("test Guest")))
        .andExpect(jsonPath("$.[0]price", is(95)))
        .andExpect(jsonPath("$.[1]id", is(2)))
        .andExpect(jsonPath("$.[1]name", is("test Guest2")))
        .andExpect(jsonPath("$.[1]price", is(300)));
    }
}

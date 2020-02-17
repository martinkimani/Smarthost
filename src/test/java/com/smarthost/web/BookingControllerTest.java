/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarthost.web;

import com.smarthost.model.Vacancy;
import com.smarthost.model.VacancyUsage;
import com.smarthost.service.IBookingService;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyInt;
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

/**
 *
 * @author martin
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest extends AbstractController{
    
    @MockBean
    private IBookingService bookingService;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @DisplayName("POST /bookings/projections -Success")
    void getProjectionsSuccessTest() throws Exception {
        Vacancy postVacancy = Vacancy.builder().economyVacancies(3).premiumVacancies(3).build();
        VacancyUsage mockVacancyUsage = VacancyUsage.builder().economyUsage("Usage Economy: 3 (EUR 167)")
                .premiumUsage("Usage Premium: 3 (EUR 738)").build();
        doReturn(mockVacancyUsage).when(bookingService).analyzeBookings(anyInt(),anyInt());
        
        mockMvc.perform(post("/bookings/projections")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(postVacancy)))
                
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        .andExpect(jsonPath("$.premiumUsage", is("Usage Premium: 3 (EUR 738)")))
        .andExpect(jsonPath("$.economyUsage", is("Usage Economy: 3 (EUR 167)")));
    }
    
}

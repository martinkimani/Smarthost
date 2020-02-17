/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarthost.web;

import com.smarthost.model.dto.NewGuestDto;
import com.smarthost.service.IGuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author martin
 */
@RestController
@RequestMapping("/guests")
public class GuestController {
    
    private final IGuestService guestService;
    
    public GuestController(IGuestService guestService) {
        this.guestService = guestService;
    }
    
    @GetMapping("/all")
    public ResponseEntity listAllGuests() {
        return new ResponseEntity(guestService.listAllGuests(), HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity AddGuest(@RequestBody NewGuestDto newGuest) {
        return new ResponseEntity(guestService.saveGuest(newGuest), HttpStatus.CREATED);
    }
}

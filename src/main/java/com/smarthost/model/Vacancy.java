/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarthost.model;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author martin
 */
@Getter
@Setter
@Builder
public class Vacancy implements Serializable{
    
    private Integer premiumVacancies;
    private Integer economyVacancies;
}

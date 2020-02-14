/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smarthost.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author martin
 */
@Entity
@Table(name = "guests")
@Getter
@Setter
public class Guest implements Serializable{
    
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer price;
    
    public Guest() {
        
    }
    
    public Guest(String name, Integer price){
        this.name = name;
        this.price = price;
    }

}

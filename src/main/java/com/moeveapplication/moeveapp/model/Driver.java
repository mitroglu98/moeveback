package com.moeveapplication.moeveapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    
    @OneToMany(mappedBy = "driver")
    @JsonIgnore
    private List<Tour> tours;  

    public Driver() {
    }

    public Driver(Long id, String name, String surname) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Tour> getTours() {
		return tours;
	}

	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}

	@Override
	public String toString() {
		return "Driver [id=" + id + ", name=" + name + ", surname=" + surname + ", tours=" + tours + "]";
	}

  
    
    
}

package com.enviro.assessment.grad001.bokangmakibinye.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Investor {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String address;
    private String contact;
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy="investor")
    @JsonManagedReference
    private List<Product> products;

    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL)
    private List<WithdrawalNotice> withdrawalNotices;


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        if (dateOfBirth == null) {
            return null;  
        }

        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);

        return period.getYears();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Investor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
                + ", contact=" + contact + ", dateOfBirth=" + dateOfBirth + ", products=" + products
                + ", withdrawalNotices=" + withdrawalNotices + "]";
    }

    
}

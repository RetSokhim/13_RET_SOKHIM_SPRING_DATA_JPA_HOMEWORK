package org.example.spring_data_jpa_homework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.spring_data_jpa_homework.model.response.CustomerResponse;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    @Column(name = "customer_name")
    private String customerName;
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email_id",unique = true)
    private Email email;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Order> orders;

    public void setEmail(Email email) {
        if (this.email != null) {
            this.email.setCustomer(null);
        }
        this.email = email;
        if (email != null) {
            email.setCustomer(this);
        }
    }

    public CustomerResponse toCustomerResponse(){
        return new CustomerResponse(this.id,this.customerName,this.address,this.phoneNumber,this.email.toEmailResponse());
    }
}

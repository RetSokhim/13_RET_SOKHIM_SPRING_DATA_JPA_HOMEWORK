package org.example.spring_data_jpa_homework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.spring_data_jpa_homework.model.response.EmailResponse;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long id;
    private String email;
    @OneToOne(mappedBy = "email",cascade = CascadeType.ALL)
    private Customer customer;

    public EmailResponse toEmailResponse(){
        return new EmailResponse(this.id,this.email);
    }
}

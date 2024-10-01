package org.nidhi.springsecurity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Entity
@Data
public class User {

    @Id
    private long id;
    private String username;
    private String password;

}

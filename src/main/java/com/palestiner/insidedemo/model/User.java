package com.palestiner.insidedemo.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.palestiner.insidedemo.model.view.Views;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "APP_USER")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    @JsonView({Views.Disabled.class})
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    @JsonView(Views.UI.class)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    @JsonView(Views.UI.class)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_DETAILS",
            joinColumns =
                    {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns =
                    {@JoinColumn(name = "DETAILS_ID", referencedColumnName = "ID")})
    private Details details;
}

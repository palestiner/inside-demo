package com.palestiner.insidedemo.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.palestiner.insidedemo.model.view.Views;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DETAILS")
@EqualsAndHashCode(exclude = {"id"})
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    @JsonView({Views.Disabled.class})
    private Long id;

    @Column(name = "AGE", nullable = false)
    @JsonView(Views.UI.class)
    private Integer age;

    @OneToOne(mappedBy = "details")
    private User user;
}

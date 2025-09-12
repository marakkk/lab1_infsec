package com.infosec.lab1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "data_items")
@Setter
@Getter
public class DataItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    public String title;

    @NotBlank
    public String owner;

    @NotBlank
    public String content;


}

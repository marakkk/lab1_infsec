package com.infosec.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DataItemDto {
    private Long id;
    private String title;
    private String owner;
    private String content;
}

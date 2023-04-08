package com.archine.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkDto {

    private String name;

    //审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
    private String status;

    private String logo;

    private String description;

    private String address;

    private Long id;
}

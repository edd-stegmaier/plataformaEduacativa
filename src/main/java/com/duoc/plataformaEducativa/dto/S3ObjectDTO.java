package com.duoc.plataformaEducativa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class S3ObjectDTO {

    private String key;
    private Long size;
    private String lastModified;
    
}

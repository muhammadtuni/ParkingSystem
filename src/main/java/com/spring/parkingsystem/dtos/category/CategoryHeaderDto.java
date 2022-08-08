package com.spring.parkingsystem.dtos.category;

import com.spring.parkingsystem.utility.MapperHelper;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class CategoryHeaderDto {
    private String id;
    private Integer tarif;
    private Integer tariflanjut;

    public CategoryHeaderDto(Object entity) {
        this.id = MapperHelper.getStringField(entity, "id");
        this.tarif = MapperHelper.getIntegerField(entity, "tarif");
        this.tariflanjut = MapperHelper.getIntegerField(entity, "tariflanjut");
    }
}

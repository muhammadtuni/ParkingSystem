package com.spring.parkingsystem.dtos.transaction;

import com.spring.parkingsystem.models.Transaction;
import com.spring.parkingsystem.utility.MapperHelper;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class TransactionDto {
    private Integer id;
    private String keterangan;
    private LocalDateTime jam;
    private LocalDate tanggal;

    public TransactionDto(Object entity) {
        this.id = MapperHelper.getIntegerField(entity, "id", Transaction.class);
        this.keterangan = MapperHelper.getStringField(entity, "keterangan", Transaction.class);
        this.jam = MapperHelper.getLocalDateTimeField(entity, "jamMasuk", Transaction.class);
        this.tanggal = MapperHelper.getLocalDateField(entity, "tanggalMasuk", Transaction.class);
    }
}

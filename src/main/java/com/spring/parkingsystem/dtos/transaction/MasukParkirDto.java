package com.spring.parkingsystem.dtos.transaction;

import com.spring.parkingsystem.models.Category;
import com.spring.parkingsystem.models.Transaction;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class MasukParkirDto {
    private final Integer id;
    private final Category jenisKendaraan;
    private final String keterangan;
    private final LocalTime jamMasuk;
    private final LocalDate tanggalMasuk;

    public Transaction parkir(Transaction transaction, Category category) {
        transaction.setJenisKendaraan(category);
        transaction.setKeterangan(category.getId());
        transaction.setJamMasuk(LocalDateTime.now());
        transaction.setTanggalMasuk(LocalDate.now());
        return transaction;
    }
    public Transaction parkirMotor(Transaction motor, Category categoryMotor) {
        motor.setJenisKendaraan(categoryMotor);
        motor.setKeterangan("Motor");
        motor.setJamMasuk(LocalDateTime.now());
        motor.setTanggalMasuk(LocalDate.now());
        return motor;
    }

    public Transaction parkirMobil(Transaction mobil, Category categoryMobil) {
        mobil.setJenisKendaraan(categoryMobil);
        mobil.setKeterangan("Mobil");
        mobil.setJamMasuk(LocalDateTime.now());
        mobil.setTanggalMasuk(LocalDate.now());
        return mobil;
    }

}

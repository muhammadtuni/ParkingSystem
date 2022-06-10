package com.spring.parkingsystem.dtos.arsip;

import com.spring.parkingsystem.models.Arsip;
import com.spring.parkingsystem.models.Transaction;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class ArsipHeaderDto {
    private Integer TransactionId;
    private LocalDateTime masuk;
    private LocalDateTime keluar;
    private Integer tarif;
    private String keterangan;

    public Arsip keluarParkir(Arsip arsip, Transaction transaction){
        var durasi = Duration.between(transaction.getJamMasuk(), LocalDateTime.now());
        arsip.setId(transaction.getId());
        arsip.setTransaction(transaction);
        arsip.setMasuk(transaction.getJamMasuk());
        arsip.setKeluar(LocalDateTime.now());
        arsip.setTarif(transaction.getJenisKendaraan().getTarif() + (transaction.getJenisKendaraan().getTariflanjut() * durasi.toHoursPart()));
        if (durasi.toDays() > 0){
            arsip.setKeterangan("Kendaraan keluar setelah " + durasi.toDays() + " hari");
        } else {
            arsip.setKeterangan("Kendaraan keluar setelah " + durasi.toHours() + " jam");
        }
        return arsip;
    }


    public static ArsipHeaderDto set(Arsip arsip){
        return new ArsipHeaderDto(arsip.getTransaction().getId(), arsip.getMasuk(), arsip.getKeluar(), arsip.getTarif(), arsip.getKeterangan());
    }

}

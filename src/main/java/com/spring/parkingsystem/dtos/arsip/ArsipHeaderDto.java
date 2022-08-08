package com.spring.parkingsystem.dtos.arsip;

import com.spring.parkingsystem.models.Arsip;
import com.spring.parkingsystem.models.Transaction;
import com.spring.parkingsystem.utility.MapperHelper;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class ArsipHeaderDto {
    private Integer transactionId;
    private LocalDateTime masuk;
    private LocalDateTime keluar;
    private Integer tarif;
    private String keterangan;

    public ArsipHeaderDto(Object entity) {
        this.transactionId = MapperHelper.getIntegerField(entity, "id", Arsip.class);
        this.masuk = MapperHelper.getLocalDateTimeField(entity, "masuk", Arsip.class);
        this.keluar = MapperHelper.getLocalDateTimeField(entity, "keluar", Arsip.class);
        this.tarif = MapperHelper.getIntegerField(entity, "tarif", Arsip.class);
        this.keterangan = MapperHelper.getStringField(entity, "keterangan", Arsip.class);
    }

    public Arsip keluarParkir(Arsip arsip, Transaction transaction){
        var durasi = Duration.between(transaction.getJamMasuk(), LocalDateTime.now());
        arsip.setId(transaction.getId());
        arsip.setTransaction(transaction);
        arsip.setMasuk(transaction.getJamMasuk());
        arsip.setKeluar(LocalDateTime.now());
        if (durasi.toDays() > 0){
            arsip.setTarif(transaction.getJenisKendaraan().getTarif() + ((int)durasi.toDays() * 48000) + (transaction.getJenisKendaraan().getTariflanjut() * LocalDateTime.now().getHour()));
            arsip.setKeterangan("Kendaraan keluar setelah " + durasi.toDays() + " hari");
        } else if (durasi.toHours() > 0){
            arsip.setTarif(transaction.getJenisKendaraan().getTarif() + (transaction.getJenisKendaraan().getTariflanjut() * durasi.toHoursPart()));
            arsip.setKeterangan("Kendaraan keluar setelah " + durasi.toHours() + " jam");
        } else {
            arsip.setTarif(transaction.getJenisKendaraan().getTarif());
            arsip.setKeterangan("Kendaraan keluar setelah " + durasi.toMinutes() + " menit");
        }
        return arsip;
    }


    public static ArsipHeaderDto set(Arsip arsip){
        return new ArsipHeaderDto(arsip.getTransaction().getId(), arsip.getMasuk(), arsip.getKeluar(), arsip.getTarif(), arsip.getKeterangan());
    }

}

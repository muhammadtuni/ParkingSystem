package com.spring.parkingsystem.services.abstraction;

import com.spring.parkingsystem.dtos.arsip.ArsipHeaderDto;
import com.spring.parkingsystem.dtos.transaction.MasukParkirDto;
import com.spring.parkingsystem.dtos.transaction.TransactionHeaderDto;

public interface TransactionService {
    public TransactionHeaderDto masukParkirMotor(MasukParkirDto motor);
    public TransactionHeaderDto masukParkirMobil(MasukParkirDto mobil);
    public TransactionHeaderDto masukParkir(MasukParkirDto transaction, String catId);
    public ArsipHeaderDto keluarParkir(ArsipHeaderDto arsip, Integer id);
}

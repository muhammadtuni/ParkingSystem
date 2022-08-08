package com.spring.parkingsystem.controllers;

import com.spring.parkingsystem.dtos.RestResponse;
import com.spring.parkingsystem.dtos.arsip.ArsipHeaderDto;
import com.spring.parkingsystem.dtos.transaction.*;
import com.spring.parkingsystem.services.abstraction.CrudService;
import com.spring.parkingsystem.services.abstraction.TransactionService;
import com.spring.parkingsystem.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Qualifier("transactionMenu")
    @Autowired
    private CrudService service;

    @Autowired
    private TransactionService transactionService;

    private List<Object> getGridDto(Page<Object> pageCollection){
        Stream<Object> gridStream = pageCollection.toList().stream();
        var dtos = gridStream.map(row -> {
            Object dto = new TransactionGridDto(
                    MapperHelper.getIntegerField(row, 0),
                    MapperHelper.getCategoryField(row, 1),
                    MapperHelper.getStringField(row, 2),
                    MapperHelper.getLocalDateTimeField(row, 3),
                    MapperHelper.getLocalDateField(row, 4)
            );
            return dto;
        }).collect(Collectors.toList());
        return dtos;
    }

    @GetMapping(value={
            "",
            "/page={page}",
            "/keterangan={keterangan}",
            "/page={page}&keterangan={keterangan}"
    })
    public ResponseEntity<RestResponse<Object>> get(@PathVariable(required = false) Integer page, @PathVariable(required = false) String keterangan){
        page = (page == null) ? 1 : page;
        keterangan = (keterangan == null) ? "" : keterangan;
        try{
            Page<Object> pageCollection = service.getHeader(page, new TransactionFilterDto(keterangan), TransactionFilterDto.class);
            List<Object> grid = getGridDto(pageCollection);
            return new ResponseEntity<>(new RestResponse<>((grid), "Success", "200"), HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>(new RestResponse<>(exception.getMessage(), "Error", "500"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<Object>> getUpdate(@PathVariable(required = true) Integer id){
        try{
            var entity = service.getUpdate(id);
            var dto = new TransactionHeadDto(entity);
            return new ResponseEntity<>(new RestResponse<>((dto), "Succes", "200"), HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>(new RestResponse<>(null, exception.getMessage(), "500"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("motor")
    public ResponseEntity<RestResponse<TransactionHeaderDto>> insertMotor(MasukParkirDto motor){
        return new ResponseEntity<>(new RestResponse<>(transactionService.masukParkirMotor(motor), "parkir motor", "201"), HttpStatus.CREATED);
    }

    @PostMapping("mobil")
    public ResponseEntity<RestResponse<TransactionHeaderDto>> insertMobil(MasukParkirDto mobil){
        return new ResponseEntity<>(new RestResponse<>(transactionService.masukParkirMobil(mobil), "parkir mobil", "201"), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<RestResponse<TransactionHeaderDto>> insertParkir(MasukParkirDto parkir, @RequestParam String catId){
        return new ResponseEntity<>(new RestResponse<>(transactionService.masukParkir(parkir, catId), "parkir " + catId, "201"), HttpStatus.CREATED);
    }

    @PostMapping("keluar")
    public ResponseEntity<RestResponse<ArsipHeaderDto>> keluar(ArsipHeaderDto arsip, @RequestParam Integer id){
        return new ResponseEntity<>(new RestResponse<>(transactionService.keluarParkir(arsip, id), "keluar parkir", "201"), HttpStatus.CREATED);
    }
}

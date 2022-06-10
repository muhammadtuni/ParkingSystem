package com.spring.parkingsystem.controllers;

import com.spring.parkingsystem.dtos.category.CategoryFilterDto;
import com.spring.parkingsystem.dtos.category.CategoryHeaderDto;
import com.spring.parkingsystem.dtos.category.UpsertCategoryDto;
import com.spring.parkingsystem.services.abstraction.CrudService;
import com.spring.parkingsystem.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Qualifier("categoryMenu")
    @Autowired
    private CrudService service;

    private List<Object> getHeaderDto(Page<Object> pageCollection){
        Stream<Object> headerStream = pageCollection.toList().stream();
        var dtos = headerStream.map(row -> {
            Object dto = new CategoryHeaderDto(
                    MapperHelper.getStringField(row, 0),
                    MapperHelper.getIntegerField(row, 1),
                    MapperHelper.getIntegerField(row, 2)
            );
            return dto;
        }).collect(Collectors.toList());
        return dtos;
    }

    @GetMapping(value={
            "",
            "/page={page}",
            "/id={id}",
            "/page={page}&id={id}"
    })
    public ResponseEntity<Object> get(@PathVariable(required = false) Integer page, @PathVariable(required = false) String id){
        page = (page == null) ? 1 : page;
        id = (id == null) ? "" : id;
        try{
            Page<Object> pageCollection = service.getHeader(page, new CategoryFilterDto(id), CategoryFilterDto.class);
            List<Object> header = getHeaderDto(pageCollection);
            return ResponseEntity.status(HttpStatus.OK).body(header);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertCategoryDto dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                Object respond = service.save(dto, UpsertCategoryDto.class);
                return ResponseEntity.status(HttpStatus.CREATED).body(respond);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertCategoryDto dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                service.save(dto, UpsertCategoryDto.class);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String id){
        try{
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(id + " is deleted.");
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}

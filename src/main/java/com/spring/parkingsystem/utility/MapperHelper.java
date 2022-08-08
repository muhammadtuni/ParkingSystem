package com.spring.parkingsystem.utility;

import com.spring.parkingsystem.dtos.ErrorDto;
import com.spring.parkingsystem.models.Category;
import org.springframework.validation.ObjectError;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MapperHelper {
    private static Object getFieldValue(Object object, String fieldName, Class<?> classType){
        try{
            Field field = classType.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(object);
            return value;
        } catch (Exception exception){
        }
        return null;
    }

    private static <T> Object getFieldValue(Object object, String fieldName){
        try{
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            var value = field.get(object);
            return value;
        }catch (Exception exception){
        }
        return null;
    }

    public static Integer getIntegerField(Object object, String fieldName, Class<?> classType){
        return (Integer) getFieldValue(object, fieldName, classType);
    }

    public static <T> Integer getIntegerField(T object, String fieldName){
        try{
            return (Integer) getFieldValue(object, fieldName);
        }catch (Exception exception){
            return null;
        }
    }

    public static Integer getIntegerField(Object object, Integer index){
        return (Integer) ((Object[])object)[index];
    }

    public static String getStringField(Object object, String fieldName, Class<?> classType){
        return getFieldValue(object, fieldName, classType).toString();
    }

    public static <T> String getStringField(T object, String fieldName){
        try{
            return getFieldValue(object, fieldName).toString();
        }catch (Exception exception){
            return null;
        }
    }

    public static String getStringField(Object object, Integer index){
        return ((Object[])object)[index].toString();
    }

    public static LocalDateTime getLocalDateTimeField(Object object, String fieldName, Class<?> classType){
        return (LocalDateTime) getFieldValue(object, fieldName, classType);
    }

    public static LocalDateTime getLocalDateTimeField(Object object, Integer index){
        return (LocalDateTime) ((Object[])object)[index];
    }

    public static LocalDate getLocalDateField(Object object, String fieldName, Class<?> classType){
        return (LocalDate) getFieldValue(object, fieldName, classType);
    }

    public static LocalDate getLocalDateField(Object object, Integer index){
        return (LocalDate) ((Object[])object)[index];
    }

    public static Category getCategoryField(Object object, String fieldName, Class<?> classType){
        return (Category) getFieldValue(object, fieldName, classType);
    }

    public static Category getCategoryField(Object object, Integer index){
        return (Category) ((Object[])object)[index];
    }

    public static List<ErrorDto> getErrors(List<ObjectError> errors){
        var dto = new ArrayList<ErrorDto>();
        for(var error : errors){
            var fieldName = getStringField(error.getArguments()[0], "defaultMessage");
            fieldName = (fieldName.equals("")) ? "object" : fieldName;
            dto.add(new ErrorDto(fieldName, error.getDefaultMessage()));
        }
        return dto;
    }

}

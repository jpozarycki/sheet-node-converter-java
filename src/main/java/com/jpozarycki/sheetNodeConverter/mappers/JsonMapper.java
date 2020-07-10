package com.jpozarycki.sheetNodeConverter.mappers;

import com.fasterxml.jackson.databind.JsonNode;
import com.jpozarycki.sheetNodeConverter.util.InvalidJsonException;
import play.libs.Json;

import javax.inject.Singleton;

@Singleton
public class JsonMapper {

    public <T> JsonNode toJson(T data) {
        try {
            return Json.toJson(data);
        } catch (RuntimeException e) {
            throw new InvalidJsonException(e);
        }
    }
}

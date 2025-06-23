package com.stefanycampanhoni.projeto_extensao_fase2.mentor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {

    ADMIN ("A", "Admin"),

    USER ("U", "User");

    private String code;
    private String description;

    @JsonValue
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Role(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    public static Role doValue(String code){
        if (code.equals("A")) {
            return ADMIN;
        } else if (code.equals("U")) {
            return USER;
        } else {
            return null;
        }
    }

}

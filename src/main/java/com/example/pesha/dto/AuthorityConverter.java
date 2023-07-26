package com.example.pesha.dto;

import com.example.pesha.dao.entity.Authority;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthorityConverter implements Converter<String, Authority> {

    @Override
    public Authority convert(String authorityString){
        return new Authority(authorityString);
    }

}

package ru.clevertec.clevertecTaskRest.config;

import ru.clevertec.clevertecTaskRest.dao.entity.BaseEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setSkipNullEnabled(false);
        modelMapper.addMappings(
                new PropertyMap<BaseEntity, BaseEntity>() {
                    @Override
                    protected void configure() {
                        skip(destination.getId());
                        skip(destination.getUpdatedDate());
                        skip(destination.getCreatedDate());
                    }
                });
        return modelMapper;
    }

}

package ru.clevertec.clevertecTaskRest.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import ru.clevertec.clevertecTaskRest.dao.entity.Product;
import ru.clevertec.clevertecTaskRest.dao.entity.SaleCard;

public final class ModelMapperUtils {


    public static ModelMapper productMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setSkipNullEnabled(false);
        mapper.addMappings(
                new PropertyMap<Product, Product>() {
                    @Override
                    protected void configure() {
                        skip(destination.getId());
                        skip(destination.getUpdatedDate());
                        skip(destination.getCreatedDate());
                    }
                });

        return mapper;
    }

    public static ModelMapper saleCardMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setSkipNullEnabled(false);
        mapper.addMappings(
                new PropertyMap<SaleCard, SaleCard>() {
                    @Override
                    protected void configure() {
                        skip(destination.getId());
                        skip(destination.getUpdatedDate());
                        skip(destination.getCreatedDate());
                    }
                });

        return mapper;
    }
}

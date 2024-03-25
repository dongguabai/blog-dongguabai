package io.github.dongguabai.blog.others.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mappings({
        @Mapping(source = "address.street", target = "street"),
        @Mapping(source = "address.city", target = "city"),
        @Mapping(source = "name", target = "name", qualifiedByName = "toUpperCase")
    })
    CustomerDTO customerToCustomerDTO(Customer customer);

    @Named("toUpperCase")
    default String toUpperCase(String name) {
        return name.toUpperCase();
    }
}
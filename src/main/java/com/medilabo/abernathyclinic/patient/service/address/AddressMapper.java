package com.medilabo.abernathyclinic.patient.service.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.medilabo.abernathyclinic.patient.dto.AddressDto;
import com.medilabo.abernathyclinic.patient.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	@Mapping(expression="java(address.getStreet().getName())", target="street")
	@Mapping(expression="java(address.getStreet().getCity().getName())", target="city")
	@Mapping(expression="java(address.getStreet().getCity().getZip())", target="zip")
	public AddressDto addressToAddressDto(Address address);
}

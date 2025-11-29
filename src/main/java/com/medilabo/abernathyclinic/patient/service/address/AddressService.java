package com.medilabo.abernathyclinic.patient.service.address;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.medilabo.abernathyclinic.patient.dto.AddressDto;
import com.medilabo.abernathyclinic.patient.entity.Address;
import com.medilabo.abernathyclinic.patient.entity.City;
import com.medilabo.abernathyclinic.patient.entity.Street;
import com.medilabo.abernathyclinic.patient.repository.AddressRepository;
import com.medilabo.abernathyclinic.patient.repository.CityRepository;
import com.medilabo.abernathyclinic.patient.repository.StreetRepository;

@Service
public class AddressService {
	
	private final AddressRepository addressRepository;
	private final StreetRepository streetRepository;
	private final CityRepository cityRepository;
	
	public AddressService(AddressRepository addressRepository, StreetRepository streetRepository,
			CityRepository cityRepository) {
		this.addressRepository = addressRepository;
		this.streetRepository = streetRepository;
		this.cityRepository = cityRepository;
	}

	public Address addAddress(AddressDto patientAddress) {
		City city = new City(patientAddress.city(), patientAddress.zip());
		City managedCity = addCityIfNotExists(city);
		
		Street street = new Street(patientAddress.street(), managedCity);
		Street managedStreet = addStreetIfNotExists(street);

		Address address = new Address(patientAddress.streetNumber(), managedStreet);
		Address managedAddress = addAddressIfNotExists(address);
		
		return managedAddress;
	}
	
	private City addCityIfNotExists(City city) {
		Optional<City> managedCity = cityRepository.findByNameAndZip(city.getName(), city.getZip()); 
		if (managedCity.isEmpty()) {
			return cityRepository.save(city);
		} else {
			return managedCity.get();
		}
	}
	
	private Street addStreetIfNotExists(Street street) {
		Optional<Street> managedStreet = streetRepository.findByNameAndCity(street.getName(), street.getCity());
		if (managedStreet.isEmpty()) {
			return streetRepository.save(street);
		} else {
			return managedStreet.get();
		}
	}
	
	private Address addAddressIfNotExists(Address address) {
		Optional<Address> managedAddress = addressRepository.findByNumberAndStreet(address.getStreetNumber(), address.getStreet());
		if (managedAddress.isEmpty()) {
			return addressRepository.save(address);
		} else {
			return managedAddress.get();
		}
	}
}

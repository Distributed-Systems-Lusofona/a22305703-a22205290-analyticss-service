package pt.ulusofona.cd.store.mapper;

import pt.ulusofona.cd.store.dto.SupplierRequest;
import pt.ulusofona.cd.store.dto.SupplierResponse;
import pt.ulusofona.cd.store.model.Supplier;

public class SupplierMapper {

    public static Supplier toEntity(SupplierRequest dto) {
        Supplier s = new Supplier();
        s.setCompanyName(dto.getCompanyName());
        s.setEmail(dto.getEmail());
        s.setAddress(dto.getAddress());
        s.setPhoneNumber(dto.getPhoneNumber());
        s.setContactPerson(dto.getContactPerson());
        s.setTaxNumber(dto.getTaxNumber());
        s.setCountry(dto.getCountry());
        s.setCity(dto.getCity());
        s.setEstablishedDate(dto.getEstablishedDate());
        s.setActive(dto.isActive());
        return s;
    }

    public static SupplierResponse toResponse(Supplier entity) {
        SupplierResponse r = new SupplierResponse();
        r.setId(entity.getId());
        r.setCompanyName(entity.getCompanyName());
        r.setEmail(entity.getEmail());
        r.setAddress(entity.getAddress());
        r.setPhoneNumber(entity.getPhoneNumber());
        r.setContactPerson(entity.getContactPerson());
        r.setTaxNumber(entity.getTaxNumber());
        r.setCountry(entity.getCountry());
        r.setCity(entity.getCity());
        r.setEstablishedDate(entity.getEstablishedDate());
        r.setActive(entity.isActive());
        return r;
    }
}
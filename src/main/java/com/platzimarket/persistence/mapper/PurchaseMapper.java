package com.platzimarket.persistence.mapper;

import com.platzimarket.domain.Purchase;
import com.platzimarket.persistence.entity.Compra;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    @Mapping(source = "idCompra",target = "purchaseId")
    @Mapping(source = "idCliente",target = "clientId")
    @Mapping(source = "fecha",target = "date")
    @Mapping(source = "medioPago",target = "paymentMethod")
    @Mapping(source = "comentario",target = "comment")
    @Mapping(source = "estado",target = "active")
    @Mapping(source = "productos",target = "items")
    Purchase toPurchase(Compra compra);
    List<Purchase> toPurchases(List<Compra> compras);

    @InheritInverseConfiguration
    @Mapping(target = "cliente",ignore = true)
    Compra toCompra(Purchase purchase);
}

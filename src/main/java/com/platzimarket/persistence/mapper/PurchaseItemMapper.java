package com.platzimarket.persistence.mapper;

import com.platzimarket.domain.PurchaseItem;
import com.platzimarket.persistence.entity.CompraProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface PurchaseItemMapper {

    @Mapping(source = "id.idProducto",target = "productId")
    @Mapping(source = "cantidad", target = "quantity")
    @Mapping(source = "estado", target = "active")
    PurchaseItem toPurchaseItem(CompraProducto compraProducto);
    @InheritInverseConfiguration
    @Mapping(target = "id.idCompra", ignore = true)
    @Mapping(target = "compra", ignore = true)
    @Mapping(target = "producto", ignore = true)
    CompraProducto toCompraProducto(PurchaseItem purchaseItem);
}

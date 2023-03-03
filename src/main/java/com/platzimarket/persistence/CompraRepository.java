package com.platzimarket.persistence;

import com.platzimarket.domain.Purchase;
import com.platzimarket.domain.repository.PurchaseRepository;
import com.platzimarket.persistence.crud.CompraCrudRepository;
import com.platzimarket.persistence.entity.Compra;
import com.platzimarket.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class CompraRepository implements PurchaseRepository {

    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClientId(String clientId) {
        return compraCrudRepository.getByIdCliente(clientId).
                map(compras -> mapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
       Compra convertToCompra = mapper.toCompra(purchase);
       // * indicamos que este guardado sea en formato de cascada
       convertToCompra.getProductos().forEach(producto -> producto.setCompra(convertToCompra));
       return mapper.toPurchase(compraCrudRepository.save(convertToCompra));
    }
}

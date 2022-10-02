package com.ms.springboot.app.items.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.springboot.app.items.clients.IProductoClienteRest;
import com.ms.springboot.app.items.models.Item;

@Service("serviceFeign")
public class ItemServiceFeignImpl implements IItemService {

	@Autowired
	private IProductoClienteRest productoClienteRest;
	
	@Override
	public List<Item> findAll() {
		// TODO Auto-generated method stub
		return productoClienteRest.listadoProducto()
				.stream().map(p->new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		// TODO Auto-generated method stub
		return new Item(productoClienteRest.obtenerProducto(id), cantidad);
	}
}

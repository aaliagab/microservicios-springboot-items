package com.ms.springboot.app.items.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ms.springboot.app.items.models.Item;
import com.ms.springboot.app.items.models.Producto;

@Service
public class ItemServiceImpl implements IItemService {

	@Autowired
	private RestTemplate clienteRest;
	
	@Override
	public List<Item> findAll() {
		// TODO Auto-generated method stub
		Producto[] productosArr = clienteRest.getForObject("http://localhost:8001/microservicio/productos/", Producto[].class);
		List<Producto> productos = Arrays.asList(productosArr);
		return productos.stream().map(p->new Item(p,1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		// TODO Auto-generated method stub
		Map<String, String> pathVariables = new HashMap<>();
		pathVariables.put("id", id.toString());
		Producto producto = clienteRest.getForObject("http://localhost:8001/microservicio/productos/{id}", Producto.class, pathVariables);
		return new Item(producto,cantidad);
	}

}

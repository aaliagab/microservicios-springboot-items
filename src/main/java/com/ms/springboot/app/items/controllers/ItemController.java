package com.ms.springboot.app.items.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.springboot.app.items.models.Item;
import com.ms.springboot.app.items.services.IItemService;

@RestController
@RequestMapping("/microservicio/items")
public class ItemController {
	
	@Autowired
	@Qualifier("serviceFeign")
	//@Qualifier("serviceRestTemplate")
	private IItemService itemService;
	
	@GetMapping("/")
	public List<Item> listadoItems(){
		return itemService.findAll();
	}
	
	@GetMapping("/id/{id}/cantidad/{cantidad}")
	public Item obtenerItem(@PathVariable Long id, @PathVariable Integer cantidad){
		return itemService.findById(id, cantidad);
	}

}

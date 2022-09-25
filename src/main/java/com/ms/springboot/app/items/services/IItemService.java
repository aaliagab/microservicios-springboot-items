package com.ms.springboot.app.items.services;

import java.util.List;

import com.ms.springboot.app.items.models.Item;

public interface IItemService {

	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);
}

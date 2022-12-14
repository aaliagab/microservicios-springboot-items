package com.ms.springboot.app.items.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.springboot.app.items.models.Item;
import com.ms.springboot.app.items.models.Producto;
import com.ms.springboot.app.items.services.IItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
//@RequestMapping("/microservicio/items")
public class ItemController {
	
	private final Logger log = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	@Autowired
	@Qualifier("serviceFeign")
	//@Qualifier("serviceRestTemplate")
	private IItemService itemService;
	
	@GetMapping("/")
	public List<Item> listadoItems(@RequestParam(name="nombre", required = false) String nombre, 
			@RequestHeader(name="token-request", required = false) String token){
		System.out.println("Nombre: "+nombre);
		System.out.println("Token: "+token);
		return itemService.findAll();
	}
	
	//@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("/id/{id}/cantidad/{cantidad}")
	public Item obtenerItem(@PathVariable Long id, @PathVariable Integer cantidad){
		return cbFactory.create("items")
				.run(() -> itemService.findById(id, cantidad),
						e -> metodoAlternativo(id, cantidad, e));
	}
	
	@CircuitBreaker(name="items", fallbackMethod = "metodoAlternativo")//primero debe ser configurado en el yml o .properties
	@GetMapping("/idvar2/{id}/cantidad/{cantidad}")
	public Item obtenerItemVariante2(@PathVariable Long id, @PathVariable Integer cantidad){
		return itemService.findById(id, cantidad);
	}
	
	@CircuitBreaker(name="items", fallbackMethod = "metodoAlternativo2")//puede combinarse o no con timelimiter
	@TimeLimiter(name="items")//primero debe ser configurado en el yml o .properties
	@GetMapping("/idvar3/{id}/cantidad/{cantidad}")
	public CompletableFuture<Item> obtenerItemVariante3(@PathVariable Long id, @PathVariable Integer cantidad){
		return CompletableFuture.supplyAsync(()->itemService.findById(id, cantidad));
	}
	
	public Item metodoAlternativo(Long id, Integer cantidad, Throwable error){
		log.info(error.getMessage());
		Item item = new Item();
		item.setCantidad(cantidad);
		Producto producto = new Producto();
		producto.setId(id);
		producto.setNombre("Camara Sony");
		producto.setPrecio(500.00);
		
		item.setProducto(producto);
		
		return item;
	}
	
	public CompletableFuture<Item> metodoAlternativo2(Long id, Integer cantidad, Throwable error){
		log.info(error.getMessage());
		Item item = new Item();
		item.setCantidad(cantidad);
		Producto producto = new Producto();
		producto.setId(id);
		producto.setNombre("Camara Sony");
		producto.setPrecio(500.00);
		
		item.setProducto(producto);
		
		return CompletableFuture.supplyAsync(()->item);
	}

}

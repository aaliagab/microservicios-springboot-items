package com.ms.springboot.app.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ms.springboot.app.items.models.Producto;

@FeignClient(name = "servicio-productos")
public interface IProductoClienteRest {

	@GetMapping({"/microservicio/productos/"})
	public List<Producto> listadoProducto();
	
	@GetMapping({"/microservicio/productos/{id}"})
	public Producto obtenerProducto(@PathVariable Long id);
	
	@PostMapping("/microservicio/productos/")
	public Producto guardarProducto(@RequestBody Producto producto);
	
	@DeleteMapping("/microservicio/productos/{id}")
	public void eliminarProducto(@PathVariable Long id);
}

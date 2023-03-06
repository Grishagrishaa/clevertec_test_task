package ru.clevertec.clevertecTaskRest.controllers.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.clevertecTaskRest.controllers.pagination.PageDtos;

@RequestMapping("${app.shopController.path}")
public interface IEntityController<R, C> {

    @PostMapping(produces = MediaType.APPLICATION_XML_VALUE)
    ResponseEntity<R> createProduct(@RequestBody C createDto);


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    ResponseEntity<R> getProduct(@PathVariable Long id);

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    ResponseEntity<PageDtos<R>> getAllProducts(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                               @RequestParam(required = false, defaultValue = "5", name = "size") Integer size);

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    ResponseEntity<R> updateProduct(@PathVariable Long id, @RequestBody C createDto);

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable Long id);

}

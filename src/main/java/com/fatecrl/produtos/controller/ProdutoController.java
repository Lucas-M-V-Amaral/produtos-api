package com.fatecrl.produtos.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecrl.produtos.model.Produto;
import com.fatecrl.produtos.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService service;

    @GetMapping
    public ResponseEntity<List<Produto>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(params = "nome")
    public ResponseEntity<List<Produto>> getByNome(@RequestParam String nome){
        return ResponseEntity.of(Optional.of(service.findByNome(nome)));
    }

    // Se der errado olhar
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable("id") Long id){
        return ResponseEntity.of(Optional.of(service.getById(id)));
    }

    @PostMapping
    public ResponseEntity<Produto> create(@RequestBody Produto produto){
        service.create(produto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path("/{id}")
                                                  .buildAndExpand(produto.getId())
                                                  .toUri();
        return ResponseEntity.created(location).body(produto);
    }

    @PutMapping
    public ResponseEntity<Produto> upodate(@RequestBody Produto produto){
        if (service.update(produto)) {
            return ResponseEntity.ok(produto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Produto> delete(@PathVariable("id") Long id){
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // @PostMapping
    // public ResponseEntity<Receita> create(@RequestBody Receita receita){
    //     service.create(receita);
    //     URI location = ServletUriComponentsBuilder
    //                         .fromCurrentRequest()
    //                         .path("/{id}")
    //                         .buildAndExpand(receita.getId())
    //                         .toUri();
    //     return ResponseEntity.created(location).body(receita);
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<Receita> getById(@PathVariable("id") Long id){
    //     //return ResponseEntity.of(Optional.of(service.getById(id)));
    //     Receita receita = service.getById(id);
    //     if (receita != null){
    //         return ResponseEntity.ok(receita);
    //     }
    //     return ResponseEntity.notFound().build();
    // }

}

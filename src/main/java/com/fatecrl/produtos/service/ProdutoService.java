package com.fatecrl.produtos.service;

import com.fatecrl.produtos.model.Produto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    private static List<Produto> listaDeProdutos = new ArrayList<>();

    private void produtoFake(){
        Produto produtoFake = new Produto();
        produtoFake.setId(1L);
        produtoFake.setNome("Garfo");
        produtoFake.setDescricao("Um garfo de prata.");
        produtoFake.setQuantidade(40);
        produtoFake.setPrecoDoProduto(10.00);
        listaDeProdutos.add(produtoFake);
    }

    public ProdutoService(){
        produtoFake();
    }

    public List<Produto> getAll(){
        return listaDeProdutos;
    }

    public Produto getById(Long id){
        Produto _produto = new Produto(id);
        return listaDeProdutos.stream().filter(p -> p.equals(_produto)).findFirst().orElse(null);

    }

    public Produto get(Produto produto){
        return this.getById(produto.getId());
    }

    public List<Produto> findByNome(String nome){
        return listaDeProdutos.stream()
                              .filter(p -> p.getNome().toLowerCase().indexOf(nome.toLowerCase()) > -1)
                              .toList();
    }

    public Boolean delete(Long id){
        Produto produto = this.getById(id);
        if (produto != null) {
            listaDeProdutos.remove(produto);
            return true;
        }
        return false;
    }

    public Produto create(Produto produto){
        listaDeProdutos.add(produto);
        return produto;
    }

    public Boolean update(Produto produtoParam){
        Produto _produto = this.get(produtoParam);
        if (_produto != null) {
            if (!produtoParam.getNome().isEmpty()) {
                _produto.setNome(produtoParam.getNome());
            }
            if (!produtoParam.getDescricao().isEmpty()) {
                _produto.setDescricao(produtoParam.getDescricao());
            }
            if (produtoParam.getQuantidade() != null && produtoParam.getQuantidade() > 0) {
                _produto.setQuantidade(produtoParam.getQuantidade());
            }
            if (produtoParam.getPrecoDoProduto() != null && produtoParam.getPrecoDoProduto() > 0) {
                _produto.setPrecoDoProduto(produtoParam.getPrecoDoProduto());
            }
            return true;
        }
        return false;
    }

}

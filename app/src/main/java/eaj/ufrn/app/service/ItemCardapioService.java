package eaj.ufrn.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import eaj.ufrn.app.model.ItemCardapio;
import eaj.ufrn.app.repository.ItemCardapioRepositorio;

@Service
public class ItemCardapioService {
    private final ItemCardapioRepositorio repository;
    public ItemCardapioService(ItemCardapioRepositorio repository){
        this.repository = repository;
    }

    public ItemCardapio adicionarCardapio(ItemCardapio c){
        return repository.save(c);
    }

    public void excluirCardapio(Long c){
        repository.deleteById(c);
    }

    public List<ItemCardapio> findAll(){
        return repository.findAll();
    }

    public String nome_item(Long id){
        Optional<ItemCardapio> optItemCardapio = repository.findById(id);
        ItemCardapio item = optItemCardapio.get();
        String nome = item.getNome();
        return nome;
    }

    public ItemCardapio get_item_cardapio(Long id){
        Optional<ItemCardapio> optItemCardapio = repository.findById(id);
        return optItemCardapio.orElse(null);
    }

    public Float consulta_preco(Long id){
        Optional<ItemCardapio> optItemCardapio = repository.findById(id);
        ItemCardapio item = optItemCardapio.get();
        Float preco = item.getPreco();
        return preco;
    }
}
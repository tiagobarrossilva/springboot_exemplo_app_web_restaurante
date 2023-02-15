package eaj.ufrn.app.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import eaj.ufrn.app.model.ItemPedido;
import eaj.ufrn.app.repository.ItemPedidoRepositorio;

@Service
public class ItemPedidoService {
    private final ItemPedidoRepositorio repository;
    
    public ItemPedidoService(ItemPedidoRepositorio repository){
        this.repository = repository;
    }

    public ItemPedido adicionar(ItemPedido p){
        return repository.save(p);
    }

    

    public ItemPedido consultar(Long p){
        Optional<ItemPedido> optItemPedido = repository.findById(p);
        return optItemPedido.orElse(null);
    }

    public List<ItemPedido> consultarTodos(){
        return repository.findAll();
        
    }

    public List<ItemPedido> findAll(){
        return repository.findAll();
    }

    public void excluir_pedido(long id){
        repository.deleteById(id);
    }

    
}
package eaj.ufrn.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import eaj.ufrn.app.model.ItemPedido;

public interface ItemPedidoRepositorio extends JpaRepository<ItemPedido, Long>{
    
}
package eaj.ufrn.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import eaj.ufrn.app.model.ItemCardapio;

public interface ItemCardapioRepositorio extends JpaRepository<ItemCardapio, Long>{
    
}

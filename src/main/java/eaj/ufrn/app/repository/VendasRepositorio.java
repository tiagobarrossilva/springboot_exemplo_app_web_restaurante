package eaj.ufrn.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eaj.ufrn.app.model.Vendas;

public interface VendasRepositorio extends JpaRepository<Vendas, Long>{
    
}

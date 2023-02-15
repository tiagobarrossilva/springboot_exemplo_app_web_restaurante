package eaj.ufrn.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import eaj.ufrn.app.model.Conta;

public interface ContaRepositorio extends JpaRepository<Conta, Integer>{
    
}

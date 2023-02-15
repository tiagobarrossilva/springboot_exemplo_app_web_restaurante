package eaj.ufrn.app.service;

import org.springframework.stereotype.Service;

import eaj.ufrn.app.model.Vendas;
import eaj.ufrn.app.repository.VendasRepositorio;

@Service
public class VendaService {
    private final VendasRepositorio repository;
    public VendaService(VendasRepositorio repository){
        this.repository = repository;
    }

    public Vendas armazenar_venda(Vendas v){
        return repository.save(v);
    }
}

package eaj.ufrn.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import eaj.ufrn.app.model.Conta;
import eaj.ufrn.app.repository.ContaRepositorio;

@Service
public class ContaService {
    private final ContaRepositorio repository;
    public ContaService(ContaRepositorio repository){
        this.repository = repository;
    }

    public Conta adicionar_conta(Conta c){
        return repository.save(c);
    }
    
    public List<Conta> findAll(){
        return repository.findAll();
    }

    public void excluir(int id){
        repository.deleteById(id);
    }

    public Conta get_conta(int id){
        Optional<Conta> opt_conta = repository.findById(id);
        return opt_conta.orElse(null);
    }
}
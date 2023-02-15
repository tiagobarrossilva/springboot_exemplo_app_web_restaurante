package eaj.ufrn.app.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import eaj.ufrn.app.model.Usuario;
import eaj.ufrn.app.repository.UsuarioRepositorio;

@Service
public class UsuarioService {
    private final UsuarioRepositorio repository;
    public UsuarioService(UsuarioRepositorio repository){
        this.repository = repository;
    }

    public Usuario adicionarUsuario(Usuario u){
        return repository.save(u);
    }

    public void excluirUsuario(String u){
        repository.deleteById(u);
    }

    public Usuario verificarUsuario(String u){
       Optional<Usuario> optUsuario = repository.findById(u);
       return optUsuario.orElse(null);
    }

    public List<Usuario> findAll(){
        return repository.findAll();
    }
}
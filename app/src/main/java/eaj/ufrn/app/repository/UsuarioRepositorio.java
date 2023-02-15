package eaj.ufrn.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import eaj.ufrn.app.model.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
    
}

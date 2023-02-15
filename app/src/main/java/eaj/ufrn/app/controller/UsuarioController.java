package eaj.ufrn.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eaj.ufrn.app.model.Usuario;
import eaj.ufrn.app.service.UsuarioService;

@Controller
public class UsuarioController {
    private final UsuarioService service;
    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @GetMapping("/adm_home")
    public String home_adm(){
        return "adm/adm_home";
    }
    @GetMapping("/garcon_home")
    public String home_garcon(){
        return "garcon/garcon_home";
    }

    @GetMapping("/adicionar_usuario")
    public String adicionarUsario(){
        return "adm/cadastrar_funcionario";
    }

    @GetMapping("/adicionar_item")
    public String cadastrarItem(){
        return "adm/cadastrar_item_cardapio";
    }

    @RequestMapping(value = "/add_usuario", method = RequestMethod.POST)
    public String adicionar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var nUsuario = (String) request.getParameter("id_usuario");
        var nSenha = (String) request.getParameter("senha");
        var nNome = (String) request.getParameter("nome");
        var nCargo = (String) request.getParameter("cargo");
        Usuario objUsuario = new Usuario(nUsuario,nSenha,nNome,Integer.parseInt(nCargo));
        service.adicionarUsuario(objUsuario);
        return "redirect:/funcionario";
    }

    @RequestMapping(value = "/del_usuario", method = RequestMethod.POST)
    public String excluir(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var nUsuario = (String) request.getParameter("id_usuario");
        service.excluirUsuario(nUsuario);
        return "adm/adm_home";
    }

    @RequestMapping(value = "/fazer_login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var n_usuario = (String) request.getParameter("id_usuario");
        var n_senha = (String) request.getParameter("senha");
        Usuario objUsuario = service.verificarUsuario(n_usuario);
        if(objUsuario == null){
            return "alertas/falha_login";
        }
        else{
            System.out.println(objUsuario.getNome());//exibindo no console
            System.out.println(objUsuario.getCargo());

            if(objUsuario.getSenha().equals(n_senha)){
                HttpSession session = request.getSession(false);
                if(session == null){
                    HttpSession novaSession = request.getSession();
                    novaSession.setAttribute("usuarioSe", objUsuario.getId_usuario());
                    novaSession.setAttribute("senhaSe", objUsuario.getSenha());
                    novaSession.setAttribute("nomeSe", objUsuario.getNome());
                    novaSession.setAttribute("cargoSe", objUsuario.getCargo());
                    switch((int)novaSession.getAttribute("cargoSe")){
                        case 1:
                            return "adm/adm_home";
                        case 2:
                            return "redirect:/contas";
                        case 3:
                            return "garcon/garcon_home";
                        case 4:
                            return "redirect:/cozinha_home";
                    }
                }
                else{
                    session.invalidate();
                    HttpSession novaSession = request.getSession();
                    novaSession.setAttribute("usuarioSe", objUsuario.getId_usuario());
                    novaSession.setAttribute("senhaSe", objUsuario.getSenha());
                    novaSession.setAttribute("nomeSe", objUsuario.getNome());
                    novaSession.setAttribute("cargoSe", objUsuario.getCargo()); 
                    switch((int)novaSession.getAttribute("cargoSe")){
                        case 1:
                            return "adm/adm_home";
                        case 2:
                            return "redirect:/contas";
                        case 3:
                            return "garcon/garcon_home";
                        case 4:
                            return "cozinha/cozinha_home";
                    }
                }
                return "alertas/falha_login";
            }
            else{
                return "alertas/falha_login";
            }
        }        
    }

    @RequestMapping(value = "/fim_sesao", method = RequestMethod.GET)
    public void fimsesao(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession(false);
        if(session == null){
            response.getWriter().println("a sessao ja tinha sido encerrada");
        }
        else{
            session.invalidate();
            response.getWriter().println("vc finalizou a sessao");
        }
    }

    // pagina adicionar e excluir usuario (usando fragmento)
    @GetMapping("funcionario")
    public String admhome(Model model, HttpServletResponse response){
        List<Usuario> usuario = service.findAll();
        List<Usuario> usuarioUtil = new ArrayList<>();
        usuario.forEach(usuario1 -> {
            usuarioUtil.add(usuario1);
        });
        model.addAttribute("usuario", usuarioUtil);
        return "adm/funcionario";
    }

    //excluir usuario
    @GetMapping("excluir_usuario/{id}")
    public String excluir(Model model, @PathVariable String id, RedirectAttributes redirectAttributes){
        service.excluirUsuario(id);
        return "redirect:/funcionario";
    }
}
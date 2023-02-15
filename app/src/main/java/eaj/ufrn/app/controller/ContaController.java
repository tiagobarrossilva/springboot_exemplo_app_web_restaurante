package eaj.ufrn.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eaj.ufrn.app.model.Conta;
import eaj.ufrn.app.model.Vendas;
import eaj.ufrn.app.service.ContaService;
import eaj.ufrn.app.service.VendaService;

@Controller
public class ContaController {
    private final ContaService service;
    private final VendaService service2;
    public ContaController(ContaService service, VendaService service2){
        this.service = service;
        this.service2 = service2;
    }

    @GetMapping("/contas")    
    public String contas(Model model, HttpServletResponse response,HttpServletRequest request){
        List<Conta> item = service.findAll();
        List<Conta> itemUtil = new ArrayList<>();
        item.forEach(item1 -> {
             itemUtil.add(item1); 
        });
        model.addAttribute("conta", itemUtil);
        return "caixa/caixa_home";
    }

    @GetMapping("finalizar_conta/{id}")
    public String finalizar_conta(Model model, @PathVariable int id, RedirectAttributes redirectAttributes,HttpServletRequest request){
        

        //pegando valores da sessão
        HttpSession session = request.getSession(false);

        String nome_op = (String) session.getAttribute("nomeSe");
        
        Conta obj_conta = service.get_conta(id);
        Float preco = obj_conta.getValor();

        Vendas obj_vendas = new Vendas();
        obj_vendas.setMesa(id);
        obj_vendas.setOperador(nome_op);
        obj_vendas.setValor(preco);

        service2.armazenar_venda(obj_vendas);
        service.excluir(id);

        return "redirect:/contas";
    }
}

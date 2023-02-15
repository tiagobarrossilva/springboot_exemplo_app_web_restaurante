package eaj.ufrn.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eaj.ufrn.app.model.ItemCardapio;
import eaj.ufrn.app.service.ItemCardapioService;

@Controller
public class ItemCardapioController {
    private final ItemCardapioService service;
    public ItemCardapioController (ItemCardapioService service){
        this.service = service;
    }

    @GetMapping("/adicionar_cardapio")
    public String addCardapio(){
        return "adm/cadastrar_item_cardapio";
    }

    // adicionar item ao cardapio
    @RequestMapping(value = "/add_cardapio", method = RequestMethod.POST)
    public String adicionar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var nIdItemCardapio = (String) request.getParameter("id_item_cardapio");
        var nNome = (String) request.getParameter("nome");
        var nPreco = (String) request.getParameter("preco");
        var nTipo = (String) request.getParameter("tipo");
        ItemCardapio objItemCardapio = new ItemCardapio(Integer.parseInt(nIdItemCardapio), nNome, Float.parseFloat(nPreco), Integer.parseInt(nTipo));
        service.adicionarCardapio(objItemCardapio);
        return "redirect:/produtos_cardapio";
    }

    // pagina para adicionar e excluir itemn no cardapio (usando fragmento)
    @GetMapping("produtos_cardapio")
    public String cardapio(Model model, HttpServletResponse response){
        List<ItemCardapio> item = service.findAll();
        List<ItemCardapio> itemUtil = new ArrayList<>();
        item.forEach(item1 -> {
            itemUtil.add(item1);
        });
        model.addAttribute("item_cardapio", itemUtil);
        return "adm/produto";
    }
    // listar cardapio
    //=============
    @GetMapping("pedidos_add")
    public String lista_cardapio(Model model, HttpServletResponse response){
        List<ItemCardapio> item = service.findAll();
        List<ItemCardapio> itemUtil = new ArrayList<>();
        item.forEach(item1 -> {
            itemUtil.add(item1);
        });
        model.addAttribute("item_cardapio", itemUtil);
        return "garcon/adicionar";
    }

    //===========

    // excluir item do cardapio
    @GetMapping("excluir_item_cardapio/{id}")
    public String excluir_item(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){
        service.excluirCardapio(id);
        return "redirect:/produtos_cardapio";
    }
}
package eaj.ufrn.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eaj.ufrn.app.model.Conta;
import eaj.ufrn.app.model.ItemCardapio;
import eaj.ufrn.app.model.ItemPedido;
import eaj.ufrn.app.model.Item_visualizar;
import eaj.ufrn.app.service.ContaService;
import eaj.ufrn.app.service.ItemCardapioService;
import eaj.ufrn.app.service.ItemPedidoService;

@Controller
public class ItemPedidoController {
    private final ItemPedidoService service;
    private final ItemCardapioService service2;
    private final ContaService service3;
    
    public ItemPedidoController(ItemPedidoService service,ItemCardapioService service2,ContaService service3){
        this.service = service;
        this.service2 = service2;
        this.service3 = service3;
    }
    
    @RequestMapping(value = "/adicionar_pedido", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String fazerPedido(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var n_mesa = (String) request.getParameter("mesa");
        var n_item = (String) request.getParameter("item");
        ItemPedido objItemPedido = new ItemPedido();
        objItemPedido.setMesa(Integer.parseInt(n_mesa));
        objItemPedido.setFk_id_item_cardapio(Integer.parseInt(n_item));
        objItemPedido.setPronto(false);
        service.adicionar(objItemPedido);
        return "/garcon/garcon_home";
    }

    // pagina adicionar e excluir pedidos (usando fragmento)
    @GetMapping("pedidos")
    public String admhome(Model model, HttpServletResponse response){
        List<ItemPedido> item = service.findAll();
        List<ItemPedido> itemUtil = new ArrayList<>();
        item.forEach(item1 -> {
            itemUtil.add(item1);
        });
        model.addAttribute("item_pedido", itemUtil);
        return "garcon/visualizar_pedidos";
    }

    //excluir pedido
    @GetMapping("excluir_pedido/{id}")
    public String excluir_pedido(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){
        service.excluir_pedido(id);
        return "redirect:/pedidos";
    }

    //@GetMapping("selecao_mesa")
    @RequestMapping(value = "/selecao_mesa", method = RequestMethod.GET)
    public String selecao_mesa(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var n_mesa = (String) request.getParameter("mesa");
        int mesa = Integer.parseInt(n_mesa);

        HttpSession session = request.getSession(false);
        session.setAttribute("mesa", mesa);
        return "redirect:/pedidos_mesa";
    }

    // lista de pedidos para a mesa selecionada
    @GetMapping("pedidos_mesa")
    public String pedidos_mesa(Model model, HttpServletResponse response,HttpServletRequest request){
        //Item_visualizar obj_item_visualizar = new Item_visualizar();
        HttpSession session = request.getSession(false);
        int m = (int)session.getAttribute("mesa");
        
        //para testes
        System.out.println(m);

        List<ItemPedido> item = service.findAll();
      
        List<ItemPedido> itemUtil = new ArrayList<>();
        item.forEach(item1 -> {
            
            if(item1.getMesa() == m){
        
                itemUtil.add(item1); 
            }
            
        });
        model.addAttribute("item_pedido", itemUtil);
        return "garcon/visualizar_pedidos_selec";
    }

    
      
     
    @GetMapping("finalizar_pedido")
    public String finalizar_pedidos(Model model, HttpServletResponse response,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        int m = (int)session.getAttribute("mesa");

        Float s = (float) 0;
        session.setAttribute("total_pedido", s);

        List<ItemPedido> item = service.findAll();
        List<ItemPedido> itemUtil = new ArrayList<>();
        
        item.forEach(item1 -> {
            
            if(item1.getMesa() == m){
                
                Long x = item1.getId_item_pedido();
                System.out.println("teste"+x);
                
                ItemPedido obj_item = service.consultar(x);

                Long y = obj_item.getFk_id_item_cardapio();
                System.out.println("outro teste"+y);

                Float z = service2.consulta_preco(y);
                System.out.println("preco"+z);

                // calculando o valor total
                Float p = (Float) session.getAttribute("total_pedido");
                p = p + z;
                session.setAttribute("total_pedido", p);
                //====
                //excluindo pedido vada vez que a laço se repetir
                service.excluir_pedido(item1.getId_item_pedido());

                //====


                itemUtil.add(item1); 
            }
            
        });
        Float p2 = (Float)session.getAttribute("total_pedido");
        System.out.println("total"+p2);
        
        Conta obj_conta = new Conta();
        obj_conta.setId_conta(m);
        obj_conta.setValor(p2);
        service3.adicionar_conta(obj_conta);

        model.addAttribute("item_pedido", itemUtil);
        //return "garcon/visualizar_pedidos_selec";
        return "redirect:/garcon_home";
    }



       // lista de todos os pedidos para os funcionarios da cozinha
       @GetMapping("cozinha_home")
       public String lista_pedidos_cozinha(Model model, HttpServletResponse response,HttpServletRequest request){
           List<ItemPedido> item = service.findAll();
           List<ItemPedido> itemUtil = new ArrayList<>();
           item.forEach(item1 -> {
                itemUtil.add(item1); 
           });
           model.addAttribute("item_pedido", itemUtil);
           return "cozinha/cozinha_home";
       }

}
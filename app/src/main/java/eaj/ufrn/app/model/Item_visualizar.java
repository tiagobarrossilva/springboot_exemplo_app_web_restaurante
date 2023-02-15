package eaj.ufrn.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item_visualizar {
    private long id_item_pedido;
    private int mesa;
    private String fk_id_item_cardapio;
    private String pronto;
}

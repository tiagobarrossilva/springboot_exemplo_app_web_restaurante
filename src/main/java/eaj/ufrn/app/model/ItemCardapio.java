package eaj.ufrn.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemCardapio {
    @Id
	private long id_item_cardapio;
	private String nome;
	private float preco;
	private int tipo;
}

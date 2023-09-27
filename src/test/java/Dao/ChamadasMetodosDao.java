package Dao;

import java.util.List;
import java.util.stream.Collectors;

import com.zeta.dao.ItemTotalizadoPorLoteDao;
import com.zeta.model.ItemTotalizadoPorLoteJoinInventarioJoinProduto;

public class ChamadasMetodosDao {

	public static void main(String[] args) {
		
		ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();
		List<ItemTotalizadoPorLoteJoinInventarioJoinProduto> listaProdutosMovimentados =  dao.ItemTotalizadoPorLoteJoinInventarioJoinProduto();
		
		List<ItemTotalizadoPorLoteJoinInventarioJoinProduto> collect = listaProdutosMovimentados.stream()
				  .filter(cod -> cod.getCodItem().equals("461"))
				  .filter(year -> Integer.parseInt(year.getAno()) < Integer.parseInt("2019"))
		          .collect(Collectors.toList());
		for(int i = 0; i < collect.size(); i++){			
			if(i == (collect.size()-1) ){
				System.out.println(i + " = " + collect.get(i).getMes()+ " = " + collect.get(i).getSaldo_acum_qtde());
				
			}
		}
		
	}
}

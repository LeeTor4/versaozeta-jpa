package importacoes;

import java.util.List;
import java.util.stream.Collectors;

import com.zeta.dao.ItemTotalizadoPorLoteDao;
import com.zeta.model.ItemTotalizadoPorLote;

public class LeituraSaldos {
	
	
	public static void main(String[] args) {
		ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();
		
		List<ItemTotalizadoPorLote> lista =  dao.listaTodos().stream()
										            .filter(cnpj -> cnpj.getCnpj().equals("05329222000680"))
										            .filter(ano -> ano.getAno().equals("2017"))
										            .distinct()
		
										            .collect(Collectors.toList());
		System.out.println(lista); 
		
		 for(ItemTotalizadoPorLote tots : lista){	
			 
			 Double qtdeEntJan =  dao.listaTodos().stream()
					                 .filter(op -> op.getOperacao().equals("E"))
									 .filter(c -> c.getCodItem().equals(tots.getCodItem()))
									 .filter(mes -> mes.getMes().equals("1"))
									 .mapToDouble(ItemTotalizadoPorLote::getVlTotQtde)
									 .sum();
		     
			  
			 Double qtdeSaiJan =  dao.listaTodos().stream()
					                  .filter(op -> op.getOperacao().equals("S"))
					                  .filter(c -> c.getCodItem().equals(tots.getCodItem()))
					                  .filter(mes -> mes.getMes().equals("1"))
									  .mapToDouble(ItemTotalizadoPorLote::getVlTotQtde)
									  .sum();
			 
			 System.out.println(tots.getCodItem() + "|" + qtdeEntJan+ "|" + qtdeSaiJan);
			
		 }
		 
		
	}

}

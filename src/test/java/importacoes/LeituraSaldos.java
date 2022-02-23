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
		
		Double qtdeEntJan = dao.buscarTotalizadorComFiltros("22998", "1", "E").getVlTotQtde();
		Double vlEntJan   = dao.buscarTotalizadorComFiltros("22998", "1", "E").getVlTotItem();
		Double qtdeSaiJan = 0.0;				
		//Double vlSaiJan   = dao.buscarTotalizadorComFiltros("22998", "1", "S").getVlTotItem();

		if(dao.buscarTotalizadorComFiltros("22998", "1", "S") != null) {
			qtdeSaiJan = dao.buscarTotalizadorComFiltros("22998", "1", "S").getVlTotQtde();		
		}
		System.out.println(qtdeEntJan + "|" + vlEntJan + "|" + qtdeSaiJan );
		 
		
	}

}

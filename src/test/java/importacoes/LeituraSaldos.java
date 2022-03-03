package importacoes;

import java.util.List;
import java.util.stream.Collectors;

import com.zeta.dao.HistoricoItensDao;
import com.zeta.dao.InventarioDeclaradoDao;
import com.zeta.dao.ItemTotalizadoPorLoteDao;
import com.zeta.dao.ItensInventarioDao;
import com.zeta.model.InventarioDeclarado;
import com.zeta.model.ItemTotalizadoPorLote;
import com.zeta.model.ItensInventario;

public class LeituraSaldos {
	
	
	public static void main(String[] args) {
//		ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();
//		
//		List<ItemTotalizadoPorLote> lista =  dao.listaTodos().stream()
//										            .filter(cnpj -> cnpj.getCnpj().equals("05329222000680"))
//										            .filter(ano -> ano.getAno().equals("2017"))
//										            .distinct()
//		
//										            .collect(Collectors.toList());
//		System.out.println(lista); 
//		
//		Double qtdeEntJan = dao.buscarTotalizadorComFiltros("22998", "1", "E").getVlTotQtde();
//		Double vlEntJan   = dao.buscarTotalizadorComFiltros("22998", "1", "E").getVlTotItem();
//		Double qtdeSaiJan = 0.0;				
//		//Double vlSaiJan   = dao.buscarTotalizadorComFiltros("22998", "1", "S").getVlTotItem();
//
//		if(dao.buscarTotalizadorComFiltros("22998", "1", "S") != null) {
//			qtdeSaiJan = dao.buscarTotalizadorComFiltros("22998", "1", "S").getVlTotQtde();		
//		}
//		System.out.println(qtdeEntJan + "|" + vlEntJan + "|" + qtdeSaiJan );
		 
//		InventarioDeclaradoDao invDec = new InventarioDeclaradoDao();
//		InventarioDeclarado buscaPorId = invDec.buscaPorAnoEmpresaEstab(2018, 1L, 6L);
//		ItensInventarioDao     itemDao = new ItensInventarioDao();		
//		System.out.println(
//				     itemDao.buscaPorIdPai(buscaPorId.getId(), "00001011").getCodItem()
//				+"|"+itemDao.buscaPorIdPai(buscaPorId.getId(), "00001011").getQtde()
//				+"|"+itemDao.buscaPorIdPai(buscaPorId.getId(), "00001011").getVlUnit()
//				+"|"+itemDao.buscaPorIdPai(buscaPorId.getId(), "00001011").getUnd()
//				+"|"+itemDao.buscaPorIdPai(buscaPorId.getId(), "00001011").getVlItem());
		
		
	}

}

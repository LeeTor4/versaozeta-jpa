package com.zeta.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.zeta.dao.InventarioDeclaradoDao;
import com.zeta.dao.ItemTotalizadoPorLoteDao;
import com.zeta.dao.ItensInventarioDao;
import com.zeta.dao.ProdutoDao;
import com.zeta.model.FichaFinanceiroPorItens;
import com.zeta.model.InventarioDeclarado;
import com.zeta.model.ItemTotalizadoPorLote;
import com.zeta.model.ItensInventario;
import com.zeta.util.UtilsEConverters;

public class ExportaTotalizadorFinanceiroAnual {
	
	private Double qtdeMDV = 0.00;
	private Double custoMedioMDV = 0.00;
	private Double custoTotMDV = 0.00;

	private Double qtdeS_EF = 0.00;
	private Double precoMedio = 0.00;
	private Double precoTotal = 0.00;

	private Double qtde_OC = 0.00;
	private Double custoUnit_OC = 0.00;
	private Double vrTotal_OC = 0.00;
	
	private Double qtde_OV = 0.00;
	private Double custoUnit_OV = 0.00;
	private Double vrTotal_OV = 0.00;
	
	public List<CadastroItensPorMovimentacao> listaDeProdutos(ItemTotalizadoPorLoteDao dao,String ano, String cnpj, Long idEmp, Long idEst){
		ProdutoDao prodDao = new ProdutoDao();
		List<CadastroItensPorMovimentacao> retorno = new ArrayList<CadastroItensPorMovimentacao>();
 		Set<String> codigos = new HashSet<String>();
		InventarioDeclaradoDao invDec = new InventarioDeclaradoDao();
		ItensInventarioDao itemInv = new ItensInventarioDao();
		List<InventarioDeclarado> inventarios = invDec.buscaPorAnoEmpresaEstab(Integer.valueOf(ano), idEmp, idEst);
		List<ItemTotalizadoPorLote> listaProdutos = dao.listaTodos().stream().filter(cgc -> cgc.getCnpj().equals(cnpj))
				.filter(year -> year.getAno().equals(String.valueOf(ano))).distinct().collect(Collectors.toList());
		
		for(InventarioDeclarado busca  : inventarios){
			if(busca.getVlTotal() > 0) {
				for(ItensInventario item : itemInv.buscaPorIdPai(busca.getId())){					
					codigos.add(UtilsEConverters.getRemoverZeroAEsquerda(item.getCodItem()));
				}
			}
		}
		
		for(ItemTotalizadoPorLote itens : listaProdutos){
			codigos.add(itens.getCodItem());
		}
		
		for(String codigo : codigos){
			CadastroItensPorMovimentacao obj = new CadastroItensPorMovimentacao();			
			obj.setCodItem(codigo);
			obj.setCodAntItem("");
			obj.setDescricao(prodDao.buscaPorCodigo(codigo).getDescricao());
			obj.setUndMed(prodDao.buscaPorCodigo(codigo).getUnidadedeMedidaPadrao());
			retorno.add(obj);
		}
		return retorno;
	}
	
	public Map<String,ItensInventario> listaInventario (String ano,Long  idEmp, Long idEst){
		Map<String,ItensInventario> retorno = new HashMap<String, ItensInventario>();
		InventarioDeclaradoDao invDec = new InventarioDeclaradoDao();
		ItensInventarioDao itemInv = new ItensInventarioDao();
		List<InventarioDeclarado> inventarios = invDec.buscaPorAnoEmpresaEstab(Integer.valueOf(ano), idEmp, idEst);        
		for(InventarioDeclarado busca  : inventarios){
			if(busca.getVlTotal() > 0) {
				for(ItensInventario item : itemInv.buscaPorIdPai(busca.getId())){
					retorno.put(UtilsEConverters.getRemoverZeroAEsquerda(item.getCodItem()),item);
				}
			}
		}
		return retorno;
	}
	
	
	
	public void exportaTotalizadorFinanceiroEstoque(String caminho, int ano, String cnpj, Long idEmp, Long idEst) {

		ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();
		
		
		 Map<String, List<ItemTotalizadoPorLote>> listaEnt = dao.listaTodos().stream()
					.filter(cgc -> cgc.getCnpj().equals(cnpj))
					.filter(year -> year.getAno().equals(String.valueOf(ano)))
					.filter(oper -> oper.getOperacao().equals("E"))
					.collect(Collectors.groupingBy(codigo -> codigo.getCodItem()));

		 Map<String, List<ItemTotalizadoPorLote>> listaSai = dao.listaTodos().stream()
					.filter(cgc -> cgc.getCnpj().equals(cnpj))
					.filter(year -> year.getAno().equals(String.valueOf(ano)))
					.filter(oper -> oper.getOperacao().equals("S"))
					.collect(Collectors.groupingBy(codigo -> codigo.getCodItem()));


		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(caminho)));
		
			String linha = " ";

			linha = cabecalho();

			writer.write(linha);
			writer.newLine();
		
			for (CadastroItensPorMovimentacao codigo : listaDeProdutos(dao, String.valueOf(ano), cnpj,idEmp,idEst)) {
				FichaFinanceiroPorItens ficha = new FichaFinanceiroPorItens();
				
								
				if(listaInventario(String.valueOf(Integer.valueOf(ano)-1), idEmp, idEst).get(codigo.getCodItem()) != null) {
					ficha.setQtdeEi(listaInventario(String.valueOf(Integer.valueOf(ano)-1), idEmp, idEst).get(codigo.getCodItem()).getQtde());
					ficha.setVrUnitEi(listaInventario(String.valueOf(Integer.valueOf(ano)-1), idEmp, idEst).get(codigo.getCodItem()).getVlUnit());
					ficha.setVrItemEi(listaInventario(String.valueOf(Integer.valueOf(ano)-1), idEmp, idEst).get(codigo.getCodItem()).getVlItem());
					System.out.println("INV INI " + ficha.getQtdeEi()+"|"+ficha.getVrUnitEi()+"|"+ficha.getVrItemEi());
				}else {
					ficha.setQtdeEi(0.0);
					ficha.setVrUnitEi(0.0);
					ficha.setVrItemEi(0.0);
					System.out.println("INV INI " + ficha.getQtdeEi()+"|"+ficha.getVrUnitEi()+"|"+ficha.getVrItemEi());
				}
				
					
				Double qtdeEnt1  = 0.0;
				Double vlTotEnt1 = 0.0;
			    Double vlUnitEnt = 0.0;
			    if(listaEnt.get(codigo.getCodItem()) !=null) {
					for (ItemTotalizadoPorLote cod : listaEnt.get(codigo.getCodItem())) {
						qtdeEnt1 += cod.getVlTotQtde();
						vlTotEnt1 += cod.getVlTotItem();
					}
					vlUnitEnt = (vlTotEnt1 / qtdeEnt1);
					ficha.setQtdeEnt(qtdeEnt1);
					ficha.setVlUnitEnt(vlUnitEnt);
					ficha.setVlItemEnt(vlTotEnt1);
			    }		
			    
			    
				Double qtdeSai1  = 0.0;
				Double vlTotSai1 = 0.0;
				Double vlUnitSai = 0.0;
				if(listaSai.get(codigo.getCodItem()) != null){
				    for(ItemTotalizadoPorLote cod : listaSai.get(codigo.getCodItem())){	 
				    	qtdeSai1  += cod.getVlTotQtde();
				    	vlTotSai1 += cod.getVlTotItem();			 
				    }
				    vlUnitSai = (vlTotSai1/qtdeSai1);
					ficha.setQtdeSai(qtdeSai1);
					ficha.setVlUnitSai(vlUnitSai);
					ficha.setVlItemSai(vlTotSai1);
				}

				if(listaInventario(String.valueOf(Integer.valueOf(ano)), idEmp, idEst).get(codigo.getCodItem()) != null) {
					ficha.setQtdeEf(listaInventario(String.valueOf(Integer.valueOf(ano)), idEmp, idEst).get(codigo.getCodItem()).getQtde());
					ficha.setVrUnitEf(listaInventario(String.valueOf(Integer.valueOf(ano)), idEmp, idEst).get(codigo.getCodItem()).getVlUnit());
					ficha.setVrItemEf(listaInventario(String.valueOf(Integer.valueOf(ano)), idEmp, idEst).get(codigo.getCodItem()).getVlItem());
					System.out.println("INV FIN " + ficha.getQtdeEf()+"|"+ficha.getVrUnitEf()+"|"+ficha.getVrItemEf());
					
				}else {
					ficha.setQtdeEf(0.0);
					ficha.setVrUnitEf(0.0);
					ficha.setVrItemEf(0.0);
					System.out.println("INV FIN " + ficha.getQtdeEf()+"|"+ficha.getVrUnitEf()+"|"+ficha.getVrItemEf());
				}

				ficha.setCodItem(codigo.getCodItem());
				ficha.setCodAntItem("");
		        ficha.setDescricao(codigo.getDescricao());
		        ficha.setUnd(codigo.getUndMed());

		        linha = formatacaoPlanilha(ficha);
				
				writer.write(linha);
				writer.newLine();
				
			}
		
			writer.close();
			System.out.println("Exportado com Sucesso!!!");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	private String formatacaoPlanilha(FichaFinanceiroPorItens totAnual) {
		String linha;
		


		//custoTotMDV = vrItemInicialInv + vlItemEntrada;
		  qtdeMDV = calcMDV((totAnual.getQtdeEi()==null?0.0:totAnual.getQtdeEi()), (totAnual.getQtdeEnt()==null?0.0:totAnual.getQtdeEnt()));
		
		  custoTotMDV = custoTotMDV((totAnual.getVrItemEi()==null?0.0:totAnual.getVrItemEi()),(totAnual.getVlItemEnt()==null?0.0:totAnual.getVlItemEnt()));
		
			
		  custoMedioMDV =  custoTotMDV/qtdeMDV;
		  
		//precoTotal = vrItemFinalInv + vlItemSaida;
		
		   qtdeS_EF   =  qtdeS_EF((totAnual.getQtdeEf()==null?0.0:totAnual.getQtdeEf()),(totAnual.getQtdeSai()==null?0.0:totAnual.getQtdeSai()));
		
		   precoTotal = precoTotal((totAnual.getVrItemEf()==null?0.0:totAnual.getVrItemEf()), (totAnual.getVlItemSai()==null?0.0:totAnual.getVlItemSai()));
		
		   precoMedio = precoTotal/qtdeS_EF;
		   
		//  System.out.println("Calc Ent " + qtdeMDV + "|" + custoMedioMDV + "|"  +custoTotMDV 
		//		                  + " Calc Sai " + qtdeS_EF + "|" + precoTotal + "|" + precoMedio);
		// =========== Omiss�es de Vendas
		

		if (qtdeS_EF >= qtdeMDV) {

			qtde_OC = qtdeS_EF - qtdeMDV;
			custoUnit_OC = custoMedioMDV;
			vrTotal_OC = qtde_OC * custoMedioMDV;

		} else {

			qtde_OC = 0.0;
			custoUnit_OC = 0.0;
			vrTotal_OC = 0.0;

		}

		if (qtdeMDV.equals(0.0)) {

			qtde_OC = qtdeS_EF;
			custoUnit_OC = precoMedio;
			vrTotal_OC = precoTotal;
		}

		if (qtde_OC.equals(0.0)) {

			custoUnit_OC = 0.0;
		}

		// =========== Omiss�es de Vendas
		
		if (qtdeMDV  >= qtdeS_EF) {

			qtde_OV = qtdeMDV - qtdeS_EF;
			custoUnit_OV = precoMedio;
			vrTotal_OV = qtde_OV * custoUnit_OV;

		} else {

			qtde_OV = 0.0;
			custoUnit_OV = 0.0;
			vrTotal_OV = 0.0;

		}
		
		if (qtdeS_EF.equals(0.0)) {

			qtde_OV = qtdeS_EF;
			custoUnit_OV = precoMedio;
			vrTotal_OV = precoTotal;
		}

		if (qtde_OV.equals(0.0)) {

			custoUnit_OV = 0.0;
		}
		
		String invIniQtde = String.format("%.2f", (totAnual.getQtdeEi()==null?0.0:totAnual.getQtdeEi()));
		String vrUnitIniInv = String.format("%.2f",(totAnual.getVrUnitEi()==null?0.0:totAnual.getVrUnitEi()));
		String vrItemIniInv = String.format("%.2f", (totAnual.getVrItemEi()==null?0.0:totAnual.getVrItemEi()));

		String qtdeItnEnt = String.format("%.2f", (totAnual.getQtdeEnt()==null?0.0:totAnual.getQtdeEnt()));
		String vlUnitItnEnt = String.format("%.2f", (totAnual.getVlUnitEnt()==null?0.0:totAnual.getVlUnitEnt()));
		String vlItnEnt = String.format("%.2f", (totAnual.getVlItemEnt()==null?0.0:totAnual.getVlItemEnt()));

		String qtdeItnSai = String.format("%.2f",(totAnual.getQtdeSai()==null?0.0:totAnual.getQtdeSai()));
		String vlUnitItnSai = String.format("%.2f",(totAnual.getVlUnitSai()==null?0.0:totAnual.getVlUnitSai()));
		String vlItnSai = String.format("%.2f", (totAnual.getVlItemSai()==null?0.0:totAnual.getVlItemSai()));

		String invFinQtde = String.format("%.2f", (totAnual.getQtdeEf()==null?0.0:totAnual.getQtdeEf()));
		String vrUnitFinInv = String.format("%.2f",(totAnual.getVrUnitEf()==null?0.0:totAnual.getVrUnitEf()));
		String vrItemFinInv = String.format("%.2f",(totAnual.getVrItemEf()==null?0.0:totAnual.getVrItemEf()));

		String qtdMDV = String.format("%.2f", qtdeMDV);
		String custMedioMDV = String.format("%.2f", (custoMedioMDV.isNaN()?0.0:custoMedioMDV));
		String custTotMDV = String.format("%.2f", custoTotMDV);

		String qtd_S_EF = String.format("%.2f", qtdeS_EF);
		String precoMedio_S_EF = String.format("%.2f", (precoMedio.isNaN()?0.0:precoMedio));
		String precoTot_S_EF = String.format("%.2f", precoTotal);

		String qtd_OC = String.format("%.2f", qtde_OC);
		String custUnit_OC = String.format("%.4f", custoUnit_OC);
		String vlTot_OC = String.format("%.2f", vrTotal_OC);
		
		String qtd_OV = String.format("%.2f", qtde_OV);
		String custUnit_OV = String.format("%.4f", custoUnit_OV);
		String vlTot_OV = String.format("%.2f", vrTotal_OV);

		linha  = totAnual.getCodItem(); 
		linha += ";";
		linha +=  totAnual.getCodAntItem(); //(getMpProdutos().get(id) == null ? "" : getMpProdutos().get(id).getCodAntItem().toString());
		linha += ";";
		linha += totAnual.getDescricao();  //(getMpProdutos().get(id) == null ? "" :getMpProdutos().get(id).getDescricao());
		linha += ";";
		linha += totAnual.getUnd();
		linha += ";";

		linha += ";";
		linha += invIniQtde.replace(".", ",");
		linha += ";";
		linha += vrUnitIniInv.replace(".", ",");
		linha += ";";
		linha += vrItemIniInv.replace(".", ",");
		linha += ";";

		linha += ";";
		linha += qtdeItnEnt.replace(".", ",");
		linha += ";";
		linha += vlUnitItnEnt.replace(".", ",");
		linha += ";";
		linha += vlItnEnt.replace(".", ",");
		linha += ";";

		linha += ";";
		linha += qtdeItnSai.replace(".", ",");
		linha += ";";
		linha += vlUnitItnSai.replace(".", ",");
		linha += ";";
		linha += vlItnSai.replace(".", ",");
		linha += ";";

		linha += ";";
		linha += invFinQtde.replace(".", ",");
		linha += ";";
		linha += vrUnitFinInv.replace(".", ",");
		linha += ";";
		linha += vrItemFinInv.replace(".", ",");
		linha += ";";

		linha += ";";
		linha += qtdMDV.replace(".", ",");
		linha += ";";
		linha += custMedioMDV.replace(".", ",");
		linha += ";";
		linha += custTotMDV.replace(".", ",");
		linha += ";";

		linha += ";";
		linha += qtd_S_EF.replace(".", ",");
		linha += ";";
		linha += precoMedio_S_EF.replace(".", ",");
		linha += ";";
		linha += precoTot_S_EF.replace(".", ",");
		linha += ";";

		linha += ";";
		linha += qtd_OC.replace(".", ",");
		linha += ";";
		linha += custUnit_OC.replace(".", ",");
		linha += ";";
		linha += vlTot_OC.replace(".", ",");
		linha += ";";
		
		linha += ";";
		linha += qtd_OV.replace(".", ",");
		linha += ";";
		linha += custUnit_OV.replace(".", ",");
		linha += ";";
		linha += vlTot_OV.replace(".", ",");
		return linha;
		
		
	}
	
	
	private String cabecalho() {
		String linha;

		linha  = "C�DIGO ITEM";
		linha += ";";
		linha += "C�DIGO ANTERIOR ITEM";
		linha += ";";
		linha += "DESCRI��O";
		linha += ";";
		linha += "UND";
		linha += ";";

		linha += ";";
		linha += "QTDE_EI";
		linha += ";";
		linha += "VR_UNIT_EI";
		linha += ";";
		linha += "VR_ITEM_EI";
		linha += ";";

		linha += ";";
		linha += "QTDE_ENT";
		linha += ";";
		linha += "VL_UNIT_ENT";
		linha += ";";
		linha += "VL_ITEM_ENT";
		linha += ";";

		linha += ";";
		linha += "QTDE_SAI";
		linha += ";";
		linha += "VL_UNIT_SAI";
		linha += ";";
		linha += "VL_ITEM_SAI";
		linha += ";";

		linha += ";";
		linha += "QTDE_EF";
		linha += ";";
		linha += "VR_UNIT_EF";
		linha += ";";
		linha += "VR_ITEM_EF";
		linha += ";";

		linha += ";";
		linha += "QTDE_MDV";
		linha += ";";
		linha += "CUSTO_MEDIO";
		linha += ";";
		linha += "CUSTO TOTAL";
		linha += ";";

		linha += ";";
		linha += "QTDE_(S + EF)";
		linha += ";";
		linha += "PRE�O_MEDIO";
		linha += ";";
		linha += "PRE�O TOTAL";
		linha += ";";

		linha += ";";
		linha += "QTDE_OC";
		linha += ";";
		linha += "CUSTO_UNIT";
		linha += ";";
		linha += "VR_TOTAL";
		linha += ";";
		
		linha += ";";
		linha += "QTDE_OV";
		linha += ";";
		linha += "CUSTO_UNIT";
		linha += ";";
		linha += "VR_TOTAL";
		
		return linha;
	}
	
	private Double calcMDV(Double qteInicialInv , Double qteItemEntrada) {
		
		if(qteInicialInv==null) {
			qteInicialInv = 0.0;
		}
		
		if(qteItemEntrada==null){
			qteItemEntrada =0.0;
		}
		return qteInicialInv + qteItemEntrada;
	}
	
	private Double custoTotMDV(Double vrItemInicialInv , Double vlItemEntrada) {
		if(vrItemInicialInv == null) {
			vrItemInicialInv = 0.0;
		}
		
		if(vlItemEntrada == null) {
			vlItemEntrada = 0.0;
		}
		return vrItemInicialInv + vlItemEntrada;
	}
	
	private Double qtdeS_EF(Double qteFinalInv ,Double qteItemSaida) {
		if(qteFinalInv == null) {
			qteFinalInv = 0.0;
		}
		
		if(qteItemSaida == null){
			qteItemSaida = 0.0;
		}
		return qteFinalInv+qteItemSaida;
	}
	
	private Double precoTotal(Double vrItemFinalInv ,Double vlItemSaida) {
		if(vrItemFinalInv == null){
			vrItemFinalInv = 0.0;
		}
		
		if(vlItemSaida == null){
			vlItemSaida = 0.0;
		}
		return vrItemFinalInv + vlItemSaida;
	}

}

class CadastroItensPorMovimentacao{
	
	private String codItem;
	private String codAntItem;
	private String descricao;
	private String undMed;
	
	public String getCodItem() {
		return codItem;
	}
	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}
	public String getCodAntItem() {
		return codAntItem;
	}
	public void setCodAntItem(String codAntItem) {
		this.codAntItem = codAntItem;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getUndMed() {
		return undMed;
	}
	public void setUndMed(String undMed) {
		this.undMed = undMed;
	}
	
	
}

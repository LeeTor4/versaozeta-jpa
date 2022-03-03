package com.zeta.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.zeta.dao.InventarioDeclaradoDao;
import com.zeta.dao.ItemTotalizadoPorLoteDao;
import com.zeta.dao.ItensInventarioDao;
import com.zeta.model.EstoqueEFDIcms;
import com.zeta.model.FichaFinanceiroPorItens;
import com.zeta.model.InventarioDeclarado;
import com.zeta.model.ItemTotalizadoPorLote;

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
	
	
	protected EstoqueEFDIcms invDeclarado(String codItem,Long idEmp, Long idEst,int ano) {
		EstoqueEFDIcms apur = new EstoqueEFDIcms();
		InventarioDeclaradoDao invDec  = new InventarioDeclaradoDao();
		InventarioDeclarado buscaPorId = invDec.buscaPorAnoEmpresaEstab(ano, idEmp, idEst);
		ItensInventarioDao     itemDao = new ItensInventarioDao();
		apur.setCodItem(itemDao.buscaPorIdPai(buscaPorId.getId(), codItem).getCodItem());
		apur.setQtde(itemDao.buscaPorIdPai(buscaPorId.getId(), codItem).getQtde());
		apur.setVlUnit(itemDao.buscaPorIdPai(buscaPorId.getId(), codItem).getVlUnit());
		apur.setVlItem(itemDao.buscaPorIdPai(buscaPorId.getId(), codItem).getVlItem());
		
		return apur;
	}
	
	public void exportaTotalizadorFinanceiroEstoque(String caminho, int ano, String cnpj,Long idEmp, Long idEst) {
		
		ItemTotalizadoPorLoteDao dao = new ItemTotalizadoPorLoteDao();
		List<ItemTotalizadoPorLote> listaProdutos = dao.listaTodos().stream().filter(cgc -> cgc.getCnpj().equals(cnpj))
				.filter(year -> year.getAno().equals(String.valueOf(ano))).distinct().collect(Collectors.toList());
		
		List<ItemTotalizadoPorLote> listaEnt = dao.listaTodos().stream()
				.filter(cgc -> cgc.getCnpj().equals(cnpj))
				.filter(year -> year.getAno().equals(String.valueOf(ano)))
				.filter(oper -> oper.getOperacao().equals("E"))
				.collect(Collectors.toList());

		List<ItemTotalizadoPorLote> listaSai = dao.listaTodos().stream()
						.filter(cgc -> cgc.getCnpj().equals(cnpj))
						.filter(year -> year.getAno().equals(String.valueOf(ano)))
						.filter(oper -> oper.getOperacao().equals("S"))
						.collect(Collectors.toList());
		
		Map<String , ItemTotalizadoPorLote>   mpEnt = new HashMap<String , ItemTotalizadoPorLote>();
		Map<String , ItemTotalizadoPorLote>   mpSai = new HashMap<String , ItemTotalizadoPorLote>();
		for(ItemTotalizadoPorLote e : listaEnt){
			mpEnt.put(e.getCodItem(), e);
		}
		for(ItemTotalizadoPorLote e : listaSai){
			mpSai.put(e.getCodItem(), e);
		}

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(caminho)));
		
		
			String linha = " ";

			linha = cabecalho();

			writer.write(linha);
			writer.newLine();
		
			for (ItemTotalizadoPorLote lista : listaProdutos) {
				FichaFinanceiroPorItens ficha = new FichaFinanceiroPorItens();
				
				ficha.setQtdeEi(invDeclarado(lista.getCodItem(),idEmp,idEst, ano-1).getQtde());
				ficha.setVrUnitEi(invDeclarado(lista.getCodItem(),idEmp,idEst, ano-1).getVlUnit());
				ficha.setVrItemEi(invDeclarado(lista.getCodItem(),idEmp,idEst, ano-1).getVlItem());
				System.out.println("INV INI " + ficha.getQtdeEi()+"|"+ficha.getVrUnitEi()+"|"+ficha.getVrItemEi());
					
				Double qtdeEnt1  = 0.0;
				Double vlTotEnt1 = 0.0;
			    Double vlUnitEnt = 0.0;
				
				Double qtdeSai1  = 0.0;
				Double vlTotSai1 = 0.0;
				Double vlUnitSai = 0.0;
				
				
				
				
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		// =========== Omissões de Vendas
		

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

		// =========== Omissões de Vendas
		
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

		linha  = "CÓDIGO ITEM";
		linha += ";";
		linha += "CÓDIGO ANTERIOR ITEM";
		linha += ";";
		linha += "DESCRIÇÃO";
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
		linha += "PREÇO_MEDIO";
		linha += ";";
		linha += "PREÇO TOTAL";
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

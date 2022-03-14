package com.zeta.handler;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.zeta.model.PlanilhaHistoricoItem;
import com.zeta.util.UtilsEConverters;

public class ExportaHisoricoItem {

	
	
	public void exportarHistoricoItem(String file, String ano, String cnpj,String codItem,String codAntItem) {
		
		try {
			PlanilhaHistoricoItem hist = new PlanilhaHistoricoItem();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file.concat("FichaEstoque").concat(codItem).concat(".csv")));
			
			String linha = " ";

			linha = cabecalho();
			
			
			//SELECT * FROM tb_historico_item WHERE empresa = '05329222000680' AND codItem IN('8790','') AND YEAR(dtDoc)= '2017' ORDER BY dtDoc,codItem,operacao;
			
		}catch (Exception e) {
			
		}
		
		
	}

	
	private String formatacaoPlanilha(PlanilhaHistoricoItem obj) {
		String linha = "";
		
		
		linha  = UtilsEConverters.getDataParaString2(obj.getData());
		linha += ";";
		linha += obj.getOperacao();
		linha += ";";
		linha += (obj.getEcfCx()==null ? "":obj.getEcfCx());
		linha += ";";
		linha += (obj.getCFOP()==null ? "":obj.getCFOP());
		
		if(obj.getOperacao().equals("S")) {
			obj.setEntQtd(0.0);
			obj.setEntVrUnit(0.0);
			obj.setEntVrTotal(0.0);
		}
		linha += ";";
		linha += (obj.getEntQtd()==null ? 0:obj.getEntQtd().toString().replace(".", ","));
		linha += ";";
		linha += (obj.getEntVrUnit()==null ? 0:obj.getEntVrUnit().toString().replace(".", ","));
		linha += ";";
		linha += (obj.getEntVrTotal()==null ? 0 : obj.getEntVrTotal().toString().replace(".", ","));
		
		if(obj.getOperacao().equals("E")) {
			obj.setSaidaQtd(0.0);
			obj.setSaidaVrUnit(0.0);
			obj.setSaidaVrTotal(0.0);
		}
		
		linha += ";";
		linha += (obj.getSaidaQtd()==null ? 0 : obj.getSaidaQtd().toString().replace(".", ","));
		linha += ";";
		linha += (obj.getSaidaVrUnit() == null ? 0 : obj.getSaidaVrUnit().toString().replace(".", ","));
		linha += ";";
		linha += (obj.getSaidaVrTotal() == null ? 0 : obj.getSaidaVrTotal().toString().replace(".", ","));
		
		linha += ";";
		linha += (obj.getSaldoQtd()== null ? 0 : obj.getSaldoQtd().toString().replace(".", ","));
		linha += ";";
		linha += (obj.getSaldoVrUnit()==null ? 0 : obj.getSaldoVrUnit().toString().replace(".", ","));
		linha += ";";
		linha += (obj.getSaldoVrTotal()==null ? 0 : obj.getSaldoVrTotal().toString().replace(".", ","));
		linha += ";";
		linha += (obj.getModelo()==null ?"":obj.getModelo());
		linha += ";";
		linha += (obj.getDecricaoItem()==null ? "" : obj.getDecricaoItem());
		linha += ";";
		linha += (obj.getNumero()==null ? "" : obj.getNumero());
		linha += ";";
		linha += (obj.getCNPJCPF()== null ? "" :obj.getCNPJCPF());
		linha += ";";
		linha += (obj.getParticipante()==null ? "" : obj.getParticipante());
		linha += ";";
		linha += (obj.getChaveDoc() == null ? "" :obj.getChaveDoc());
		
		
		
		return linha;
	}
	
	private String cabecalho() {
        String linha;
		
		linha = "Data";
		linha += ";";
		linha += "Operacao";
		linha += ";";
		linha += "ECF_CX";
		linha += ";";
		linha += "CFOP";
		linha += ";";
		linha += "Entrada Qtd.";
		linha += ";";
		linha += "Entrada Vr. Unit.";
		linha += ";";
		linha += "Entrada Vr. Total";
		linha += ";";
		linha += "Saida Qtd.";
		linha += ";";
		linha += "Saida Vr. Unit.";
		linha += ";";
		linha += "Saida Vr. Total";
		linha += ";";
		linha += "Saldo Qtd.";
		linha += ";";
		linha += "Saldo Vr. Unit.";
		linha += ";";
		linha += "Saldo Vr. Total";
		linha += ";";
		linha += "Modelo";
		linha += ";";
		linha += "Decricao Item";
		linha += ";";
		linha += "Numero";
		linha += ";";
		linha += "CNPJ/CPF";
		linha += ";";
		linha += "Participante";
		linha += ";";
		linha += "Chave Doc";
		
		return linha;
	}
	
	
	
}

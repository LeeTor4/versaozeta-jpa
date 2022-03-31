package com.zeta.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.zeta.dao.HistoricoItensDao;
import com.zeta.dao.InventarioDeclaradoDao;
import com.zeta.dao.ItensInventarioDao;
import com.zeta.model.HistoricoItens;
import com.zeta.model.InventarioDeclarado;
import com.zeta.model.ItensInventario;
import com.zeta.model.PlanilhaHistoricoItem;
import com.zeta.util.UtilsEConverters;

public class ExportaHistoricoItem {

	private InventarioDeclaradoDao invDec = new InventarioDeclaradoDao();
	
	
	public LocalDate dtInventario(Long idPai,String ano,Long  idEmp, Long idEst) {
		LocalDate retorno = null;
		InventarioDeclarado inventario = invDec.buscaPorId(idPai);				
		retorno = inventario.getDataInv();
		return retorno;
	}
	
	public Map<String,ItensInventario> listaInventario (String ano,Long  idEmp, Long idEst){
		Map<String,ItensInventario> retorno = new HashMap<String, ItensInventario>();
		
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
	
	
	public void exportarHistoricoItem(String file, String ano, String cnpj,String codItem,Long idEmp, Long idEst) {
		HistoricoItensDao daoHist = new HistoricoItensDao();
		try {
			PlanilhaHistoricoItem hist = new PlanilhaHistoricoItem();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file.concat("FichaEstoque").concat(codItem).concat(".csv")));
			
			 String linha = " ";

			 linha = cabecalho();
			
			 writer.write(linha);
			 writer.newLine();
			 Double qtdeSaldo = 0.0;
			
			 
			 if(listaInventario(String.valueOf(Integer.valueOf(ano)-1), idEmp, idEst).get(codItem) != null) {
				
				 hist.setData(dtInventario(listaInventario(String.valueOf(Integer.valueOf(ano)-1), idEmp, idEst).get(codItem).getIdPai(), 
						 ano, idEmp, idEst));	
				 hist.setOperacao("INV");
			     qtdeSaldo  = listaInventario(String.valueOf(Integer.valueOf(ano)-1), idEmp, idEst).get(codItem).getQtde();
			     hist.setSaldoQtd(qtdeSaldo);
			     hist.setSaldoVrUnit(listaInventario(String.valueOf(Integer.valueOf(ano)-1), idEmp, idEst).get(codItem).getVlUnit());
			     hist.setSaldoVrTotal(listaInventario(String.valueOf(Integer.valueOf(ano)-1), idEmp, idEst).get(codItem).getVlItem());
			     
			     linha = formatacaoPlanilha(hist);
		         writer.write(linha);
		         writer.newLine();
			 }

		     List<HistoricoItens> lista = daoHist.buscaHisItemPorCnpjCodigoAno(cnpj, codItem, Integer.valueOf(ano));
 
		     
		    
			 for(HistoricoItens obj : lista){
				 
				hist.setData(obj.getDtDoc());
				hist.setOperacao(obj.getOperacao());
				hist.setEcfCx(obj.getEcfCx());
				hist.setCFOP(obj.getCfop());
				
				if(obj.getOperacao().equals("E")){
					
					hist.setEntQtd(obj.getQtde().doubleValue());
					hist.setEntVrUnit(obj.getVlUnit().doubleValue());
					hist.setEntVrTotal(obj.getVlBruto().doubleValue());
					
					qtdeSaldo += obj.getQtde().doubleValue();
					hist.setSaldoVrUnit(obj.getVlUnit().doubleValue());
					hist.setSaldoQtd(qtdeSaldo);
					hist.setSaldoVrTotal(hist.getSaldoQtd()*hist.getSaldoVrUnit());
					
				}else if(obj.getOperacao().equals("S")){
					hist.setSaidaQtd(obj.getQtde().doubleValue());
					hist.setSaidaVrUnit(obj.getVlUnit().doubleValue());
					hist.setSaidaVrTotal(obj.getVlBruto().doubleValue());
					
					qtdeSaldo += obj.getQtde().doubleValue()*(-1);
					hist.setSaldoVrUnit(obj.getVlUnit().doubleValue());
					hist.setSaldoQtd(qtdeSaldo);
					hist.setSaldoVrTotal(hist.getSaldoQtd()*hist.getSaldoVrUnit());
				}
				
				
				hist.setModelo(obj.getCodMod());
				hist.setDecricaoItem(obj.getDescricao());
				hist.setNumero(obj.getNumDoc());
				hist.setCNPJCPF((obj.getCpfCnpj().isEmpty() ? "" : obj.getCpfCnpj().concat("_")));
				hist.setParticipante(obj.getNome());
				hist.setChaveDoc((obj.getChaveDoc().isEmpty() ? "" :obj.getChaveDoc().concat(".xml")));
					
					if(obj != null){
	 
						 linha = formatacaoPlanilha(hist);
				         writer.write(linha);
				         writer.newLine(); 
					}
				 }

			//SELECT * FROM tb_historico_item WHERE empresa = '05329222000680' AND codItem IN('8790','') AND YEAR(dtDoc)= '2017' ORDER BY dtDoc,codItem,operacao;
			writer.close();	
			
			System.out.println("Histórico Exportado com Sucesso!!!");
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
	
	
	   public List<ListaDeProdutosParaHistLote> importaListaProdutos(String caminho) {
		   List<ListaDeProdutosParaHistLote> retorno = new ArrayList<>();
		   File arquivoCSV = new File(caminho);
		  
		   try {
			
			     String linhaDoArquivo = new String();
				 
				 @SuppressWarnings("resource")
				 Scanner leitor = new Scanner(arquivoCSV);
				 //leitor.nextLine();
				 while(leitor.hasNext()){
					 ListaDeProdutosParaHistLote prod = new ListaDeProdutosParaHistLote();
					 linhaDoArquivo = leitor.nextLine();
					 String[] valoresEntreVirgula = linhaDoArquivo.split("\\;");	 
					 for(int i=0;i<valoresEntreVirgula.length;i++){
						  
						    if(i==0){
						    	 prod.setCodigo(valoresEntreVirgula[0]);
						    	
						    }
						    if(i==1) {
						    	 prod.setCodAntItem(valoresEntreVirgula[1]);
						    }
						    retorno.add(prod);  
					 }

				 }
			   
		   }catch (FileNotFoundException e) {
				
				e.printStackTrace();
		   }
		   
		   return retorno;
	   }
	   
	   public void exportarHistoricoItensComLista(String fileProdutos,String file, String ano, String cnpj,Long idEmp, Long idEst) {
           int id = 0;
		    for(ListaDeProdutosParaHistLote p : importaListaProdutos(fileProdutos)){  
		    	
		    	if(p.getCodigo() != null || p.getCodAntItem()  != null) {
		    		id++;
		    		//System.out.println(id + " - " + p.getCodigo() + " = " + p.getCodAntItem());
		    		exportarHistoricoItem(file , ano, cnpj, p.getCodigo(),idEmp,idEst);
		    	}
		    }
	   }
	   
	   
	public class ListaDeProdutosParaHistLote {
		
		private String codigo;
		private String codAntItem;
		
		
		public String getCodigo() {
			return codigo;
		}
		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}
		public String getCodAntItem() {
			return codAntItem;
		}
		public void setCodAntItem(String codAntItem) {
			this.codAntItem = codAntItem;
		}
		
	}
	
}




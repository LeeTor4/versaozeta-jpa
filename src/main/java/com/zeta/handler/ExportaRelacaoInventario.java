package com.zeta.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.zeta.handler.ExportaHistoricoItem.ListaDeProdutosParaHistLote;


public class ExportaRelacaoInventario {
	
	public class SaldoInicialControleEstoque {
		
		private Long id;
		private String cnpj;
		private String ano;
		private String tipo;
		private String secao;
		private String codItem;
		private String codAntItem;
		private String descricao;
		private Double qtdeInicial;
		
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getCnpj() {
			return cnpj;
		}
		public void setCnpj(String cnpj) {
			this.cnpj = cnpj;
		}
		public String getAno() {
			return ano;
		}
		public void setAno(String ano) {
			this.ano = ano;
		}
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		public String getSecao() {
			return secao;
		}
		public void setSecao(String secao) {
			this.secao = secao;
		}
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
		public Double getQtdeInicial() {
			return qtdeInicial;
		}
		public void setQtdeInicial(Double qtdeInicial) {
			this.qtdeInicial = qtdeInicial;
		}
		
		
	}
	
	
	   public List<SaldoInicialControleEstoque> importaListaProdutos(String caminho) {
		   List<SaldoInicialControleEstoque> retorno = new ArrayList<>();
		   File arquivoCSV = new File(caminho);
		  
		   try {			
			     String linhaDoArquivo = new String();
				 
				 @SuppressWarnings("resource")
				 Scanner leitor = new Scanner(arquivoCSV);
				 //leitor.nextLine();
				 while(leitor.hasNext()){
					 SaldoInicialControleEstoque prod = new SaldoInicialControleEstoque();
					 linhaDoArquivo = leitor.nextLine();
					 String[] valoresEntreVirgula = linhaDoArquivo.split("\\;");	 
					 for(int i=0;i<valoresEntreVirgula.length;i++){
						  
						    if(i==0){
						    	 //prod.setCodigo(valoresEntreVirgula[0]);						    	
						    }
						    if(i==1) {
						    	 //prod.setCodAntItem(valoresEntreVirgula[1]);
						    }
						    retorno.add(prod);  
					 }

				 }
			   
		   }catch (FileNotFoundException e) {
				
				e.printStackTrace();
		   }
		   
		   return retorno;
	   }
	
	public void exportRelacaoInventario(String fileInv,String file,String cnpj, String ano) {
		

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));
		
			String linha = " ";

			linha = cabecalho();

			writer.write(linha);
			writer.newLine();
		
		    for(SaldoInicialControleEstoque mov : importaListaProdutos(fileInv)){
			   
			  
		    }
		
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}

	private String cabecalho() {
        String linha;
		
		linha  = "CNPJ";
		linha += ";";
		linha += "ANO";
		linha += ";";
		linha += "CÓDIGO ITEM";
		linha += ";";
		linha += "DESCRICAO ITEM";
		linha += ";";
		linha += "NCM";
		linha += ";";
		linha += "TIPO ITEM";
		linha += ";";
		linha += "QTDE";
		linha += ";";
		linha += "UND";
		linha += ";";
		linha += "VL_UNIT";
		linha += ";";
		linha += "VL_TOTAL";
		linha += ";";
		
		return linha;
	}
	
	
	
}

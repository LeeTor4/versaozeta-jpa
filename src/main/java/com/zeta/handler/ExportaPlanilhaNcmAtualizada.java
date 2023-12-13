package com.zeta.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.zeta.model.Nomenclatura_Comum_Mercosul;
import com.zeta.util.UtilsEConverters;

public class ExportaPlanilhaNcmAtualizada {

	 // {"Codigo":"01","Descricao":"Animais vivos.","Data_Inicio":"01/04/2022","Data_Fim":"31/12/9999","Tipo_Ato":"Res Camex","Numero_Ato":"272","Ano_Ato":"2021"}
	public static List<Nomenclatura_Comum_Mercosul> listaNcm(String fileAquivoJson){
		List<Nomenclatura_Comum_Mercosul> retorno = new ArrayList<Nomenclatura_Comum_Mercosul>();
		 Gson gson = new Gson();
		 JsonReader reader;
		try {
			
			 reader = new JsonReader(new FileReader(fileAquivoJson));
			 JsonElement element = gson.fromJson(reader, JsonElement.class);
			 JsonObject jsonObj = element.getAsJsonObject();
			 System.out.println(jsonObj.get("Ato").getAsString());
			 JsonArray asJsonArrayNomenclaturas = jsonObj.get("Nomenclaturas").getAsJsonArray();
			 for(int i=0; i < asJsonArrayNomenclaturas.size(); i++) {
				 Nomenclatura_Comum_Mercosul ncm = new Nomenclatura_Comum_Mercosul();	
				 String descricao = asJsonArrayNomenclaturas.get(i).getAsJsonObject().get("Descricao").getAsString().toString().replace("-","").replace(";","");
				 byte[] descricaoBytes = descricao.getBytes();
				 String descricaoUTF8EncodedString = new String(descricaoBytes, StandardCharsets.UTF_8);
				
				 ncm.setCodigo(asJsonArrayNomenclaturas.get(i).getAsJsonObject().get("Codigo").getAsString());
				 ncm.setDescricao(descricaoUTF8EncodedString);
				 ncm.setDataInicio(UtilsEConverters.getStringParaData5(asJsonArrayNomenclaturas.get(i).getAsJsonObject().get("Data_Inicio").getAsString()));
				 ncm.setDataFim(UtilsEConverters.getStringParaData5(asJsonArrayNomenclaturas.get(i).getAsJsonObject().get("Data_Fim").getAsString()));
				 ncm.setTipoAto(asJsonArrayNomenclaturas.get(i).getAsJsonObject().get("Tipo_Ato").getAsString());
				 ncm.setNumeroAto(asJsonArrayNomenclaturas.get(i).getAsJsonObject().get("Numero_Ato").getAsString());
				 ncm.setAnoAto(asJsonArrayNomenclaturas.get(i).getAsJsonObject().get("Ano_Ato").getAsString());
				 
				 System.out.println(
							 asJsonArrayNomenclaturas.get(i).getAsJsonObject().get("Codigo").getAsString() + " = " 
					           +
							 descricaoUTF8EncodedString + " = " 
							   +
							 asJsonArrayNomenclaturas.get(i).getAsJsonObject().get("Data_Inicio").getAsString() + " = " 
				          );
				 retorno.add(ncm);
			 }

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		
		
		return retorno;
	}
	
	private static String cabecalho() {
		
		String linha = "";
	    StringBuilder sb = new StringBuilder();
	    linha = sb.append("Código")
	    		  .append(";")
	    		  .append("Descrição")
	    		  .append(";")
	    		  .append("Data_Inicio")
	    		  .append(";")
	    		  .append("Data_Fim")
	    		  .append(";")
	    		  .append("Tipo_Ato")
	    		  .append(";")
	    		  .append("Numero_Ato")
	    		  .append(";")
	    		  .append("Ano_Ato")
	    		  .toString();
	    return linha;
	}
	
	
	public static void exportaPlanilhaNcm(String file,List<Nomenclatura_Comum_Mercosul> listaNcm) {
		BufferedWriter writer;
		
		try {
            writer = new BufferedWriter(new FileWriter(new File(file)));
			
			String linha = " ";

			linha = cabecalho();

			writer.write(linha);
			writer.newLine();
			
			for(int i =0; i < listaNcm.size(); i++) {
				ModelPlanilhaNcm plan = new ModelPlanilhaNcm();
				
				plan.setCodigo(listaNcm.get(i).getCodigo());
			    plan.setDescricao(listaNcm.get(i).getDescricao());
			    plan.setDataInicio(listaNcm.get(i).getDataInicio());
			    plan.setDataFim(listaNcm.get(i).getDataFim());
			    plan.setTipoAto(listaNcm.get(i).getTipoAto());
			    plan.setNumeroAto(listaNcm.get(i).getNumeroAto());
			    plan.setAnoAto(listaNcm.get(i).getAnoAto());
			
			    linha = formatacaoPlanilha(plan);
				
				writer.write(linha);
				writer.newLine();
				
			}
			
			
			writer.close();
			System.out.println("Exportado com Sucesso!!!");
		} catch (Exception e) {
			
		}
	}
	
	private static String formatacaoPlanilha(ModelPlanilhaNcm plan) {
		String linha = "";
		
		linha  = plan.getCodigo();
		linha += ";";
		linha += plan.getDescricao();
		linha += ";";
		linha += plan.getDataInicio();
		linha += ";";
		linha += plan.getDataFim();
		linha += ";";
		linha += plan.getTipoAto();
		linha += ";";
		linha += plan.getNumeroAto();
		linha += ";";
		linha += plan.getAnoAto();
		linha += ";";
		
		return linha;
	}
}

class ModelPlanilhaNcm{
	
	private String codigo;
	private String descricao;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private String tipoAto;
	private String numeroAto;
	private String anoAto;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDate getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	public String getTipoAto() {
		return tipoAto;
	}
	public void setTipoAto(String tipoAto) {
		this.tipoAto = tipoAto;
	}
	public String getNumeroAto() {
		return numeroAto;
	}
	public void setNumeroAto(String numeroAto) {
		this.numeroAto = numeroAto;
	}
	public String getAnoAto() {
		return anoAto;
	}
	public void setAnoAto(String anoAto) {
		this.anoAto = anoAto;
	}
	
	
}

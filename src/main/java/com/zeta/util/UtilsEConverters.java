package com.zeta.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.text.MaskFormatter;

import com.zeta.model.Cfop;

public class UtilsEConverters {

	private static DateTimeFormatter formatter = 
			  DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	private static DateTimeFormatter formatter2 = 
			  DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	private static DateTimeFormatter formatter3 = 
			  DateTimeFormatter.ofPattern("yyyyMMdd");
	
	private static DateTimeFormatter formatter4 = 
			  DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
    public static String getDataParaString2(LocalDate data) {
		
		String dtFormatada = formatter2.format(data);
		//System.out.println(dtFormatada);
		return dtFormatada;
	}
    
    public static String getDataParaString3(LocalDate data) {
		
		String dtFormatada = formatter3.format(data);
		//System.out.println(dtFormatada);
		return dtFormatada;
	}
    
	public static LocalDate getStringParaData(String data) {
		TemporalAccessor parse = formatter.parse(data.substring(0,10));
		LocalDate from = LocalDate.from(parse);
		//System.out.println(from);
		return from;
	}
	
	public static LocalDate getStringParaData4(String data) {
		TemporalAccessor parse = formatter.parse(data);
		LocalDate from = LocalDate.from(parse);
		//System.out.println(from);
		return from;
	}
	
	public static LocalDate getStringParaData3(String data) {
		
		TemporalAccessor parse = formatter3.parse(data);
		LocalDate from = LocalDate.from(parse);
		//System.out.println(from);
		return from;
	}
	
	public static String convertParaNumeroDecimalBrasil(String formato) {
		String padrao = "###,###.##"; 
        DecimalFormat df = new DecimalFormat(padrao);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt","Brazil"));
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        df = new DecimalFormat(padrao, dfs);
        
        return df.format(formato);
	}
	
	public static LocalDate getSQLParaLocalDate(Date dateToConvert) {	
		 LocalDate localDate = null;
		if(dateToConvert != null) {
			Instant instant = Instant.ofEpochMilli(dateToConvert.getTime());
	        localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		}
		
       
       return localDate;
	}
	
	public static String getRemoverZeroAEsquerda(String str) {
		// Count leading zeros
        int i = 0;
        while (i < str.length() && str.charAt(i) == '0')
            i++;
  
        // Convert str into StringBuffer as Strings
        // are immutable.
        StringBuffer sb = new StringBuffer(str);
  
        // The  StringBuffer replace function removes
        // i characters from given index (0 here)
        sb.replace(0, i, "");
  
        return sb.toString();  // return in String
	}
	
	public static String mascaraCep(String cep) {
	    try {
	        MaskFormatter mask = new MaskFormatter("#####-###");
	        mask.setValueContainsLiteralCharacters(false);
	        System.out.println("CEP : " + mask.valueToString(cep));
	        cep =  mask.valueToString(cep);
	    } catch (Exception ex) {
	       
	    }
	    return cep;
	}
	
	public static String mascaraCnpj(String cnpj) {
	    try {
	        MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
	        mask.setValueContainsLiteralCharacters(false);
	        System.out.println("CNPJ : " + mask.valueToString(cnpj));
	        cnpj =  mask.valueToString(cnpj);
	    } catch (Exception ex) {
	       
	    }
	    return cnpj;
	}
	
	public static String mascaraChaveNfe(String chave) {
	    try {
	        MaskFormatter mask = new MaskFormatter("##-####-##############-##-###-#########-#########-#");
	        mask.setValueContainsLiteralCharacters(false);
	        System.out.println("CNPJ : " + mask.valueToString(chave));
	        chave =  mask.valueToString(chave);
	    } catch (Exception ex) {
	       
	    }
	    return chave;
	}
	
	public static List<Cfop> lerRelacaoCfop(String caminho) {
		List<Cfop> retorno = new ArrayList<Cfop>();
		File arquivoCSV = new File(caminho);
		String linhaDoArquivo = new String();
		
		try {
			@SuppressWarnings("resource")
			Scanner leitor = new Scanner(arquivoCSV);
			leitor.nextLine();
			while (leitor.hasNext()) {
				linhaDoArquivo = leitor.nextLine();
				String[] valoresEntreVirgula = linhaDoArquivo.split("\\;");
				//System.out.println(valoresEntreVirgula[0]+"|"+valoresEntreVirgula[1]+"|"+valoresEntreVirgula[2]+"|"+valoresEntreVirgula[3]+"|"+valoresEntreVirgula[4]);
				retorno.add(new Cfop(valoresEntreVirgula[0],valoresEntreVirgula[1],valoresEntreVirgula[2],valoresEntreVirgula[3],valoresEntreVirgula[4]));
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		
		return retorno;
	}
}

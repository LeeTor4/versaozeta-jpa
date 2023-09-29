package Txt;

import java.text.NumberFormat;
import java.util.Locale;

import com.itextpdf.text.log.SysoCounter;

public class ComparacaoNomes {

	private static String removeCaracteresEspeciais(String str) {
	    return str.replaceAll("[/[^a-zA-Z]/g]","");
	}
	private static String retornaCaracteresNumericos(String str) {
	    return str.replaceAll("[^0-9]+","");
	}
	
    private static String retornaMaiorValor(String desc1,String desc2) {
    	String retorno = null;
    	
    	int max = Math.max(desc1.length(), desc2.length());
    	
    	if(desc1.length() == max) {
    		retorno = removeCaracteresEspeciais(desc1);
    	}else if(desc2.length() == max) {
    		retorno = removeCaracteresEspeciais(desc2);
    	}
    	
    	return retorno;
    }
    
    private static String retornaMenorValor(String desc1,String desc2) {
    	String retorno = null;
    	
    	int min = Math.min(desc1.length(), desc2.length());
    	
    	if(desc1.length() == min) {
    		retorno = removeCaracteresEspeciais(desc1);
    	}else if(desc2.length() == min) {
    		retorno = removeCaracteresEspeciais(desc2);
    	}
    	
    	return retorno;
    }
	public static void main(String[] args) {
		
		
//		String desc1 = "AEROLIN SOL GTS 10ML-REFERENCIA";
//		String desc2 = "AEROLIN SOL 5MG/ML 10M SULFATO DE SALBUTAMOL";
//
//		
//		int cont = 0;
//		char[] charArray = retornaMenorValor(desc1.toUpperCase(),desc2.toUpperCase()).toCharArray();
//		System.out.println("Antes " + cont);
//		for(int i =0; i < retornaMenorValor(desc1.toUpperCase(),desc2.toUpperCase()).length();i++) {
//			System.out.println(charArray[i] + " ");
//			if(retornaMaiorValor(desc1.toUpperCase(),desc2.toUpperCase()).contains(String.valueOf(charArray[i]))) {
//				cont++;
//			}
//		}
//
//		System.out.println("Diferença " + (removeCaracteresEspeciais(desc2).length()-cont));
//		System.out.println(retornaCaracteresNumericos(desc1) + " = " + retornaCaracteresNumericos(desc2));
		double valorReal = 291933.1233;
		Locale localeBR = new Locale("pt","BR");
		NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBR);
		System.out.println("Valor Formatado em Moeda: "+dinheiro.format(valorReal));
	}

}

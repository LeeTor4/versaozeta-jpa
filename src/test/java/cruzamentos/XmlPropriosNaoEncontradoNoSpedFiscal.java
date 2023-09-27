package cruzamentos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.zeta.util.UnidadeFederacao;
import com.zeta.util.UtilsEConverters;

import modulos.efdicms.entidades.RegC100;
import modulos.efdicms.manager.LeitorEfdIcms;

public class XmlPropriosNaoEncontradoNoSpedFiscal {
   
	private static String cabecalho() {
		String linha = "";
	    StringBuilder sb = new StringBuilder();
	    linha = sb.append("Emitente - CNPJ")
	    		  .append(";")
	    		  .append("Razão Social")
	    		  .append(";")
	    		  .append("Chave de Acesso")
	    		  .append(";")
	    		  .append("UF")
	    		  .append(";")
	    		  .append("Data Emissão")
	    		  .append(";")
	    		  .append("Numero NFe")
	    		  .append(";")
	    		  .append("Valor NFe")
	    		  .append(";")
	    		  .append("ICMS NFe")
	    		  .append(";")
	    		  .append("Valor EFD")
	    		  .append(";")
	    		  .append("ICMS EFD")
	    		  .append(";")
	    		  .append("DIFERENÇA")
	    		  .toString();
		return linha;
	}
	
	private static void exportaNFeRecebidasDeclaradasComValoresDiferentesDaEfd(String file,Map<String, NfeRecebidas> mpNFeRecebidas,
			Map<String, RegC100> mpC100EfdIcms) {
		
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(new File(file)));
			
			String linha = " ";

			linha = cabecalho();

			writer.write(linha);
			writer.newLine();
			
			for(String key : mpNFeRecebidas.keySet()){
				PlanilhaDeNFeDeclaradasComValorDifEFD plan = new PlanilhaDeNFeDeclaradasComValorDifEFD();
					
				Double res = 0.0;
				if(mpC100EfdIcms.get(key) != null) {
					res = mpNFeRecebidas.get(key).getVlDoc() - mpC100EfdIcms.get(key).getVlDoc();
				}

				if(res != 0.0) {
					
					plan.setCnpj(mpNFeRecebidas.get(key).getCnpj());
					plan.setRazaoSocial(mpNFeRecebidas.get(key).getRazaoSocial());
					plan.setChaveAcesso(mpNFeRecebidas.get(key).getChaveAcesso());
					plan.setUf(mpNFeRecebidas.get(key).getUf());
					plan.setDtEmissao(mpNFeRecebidas.get(key).getDtEmissao());
					plan.setNumDoc(mpNFeRecebidas.get(key).getNumDoc());
					plan.setVlDoc(mpNFeRecebidas.get(key).getVlDoc());
					plan.setVlIcms(mpNFeRecebidas.get(key).getVlIcms());
					
					if(mpC100EfdIcms.get(key) != null) {
						plan.setVlDocEfd(mpC100EfdIcms.get(key).getVlDoc());
					}else {
						plan.setVlDocEfd(0.0);
					}
					
					if(mpC100EfdIcms.get(key) != null) {
						plan.setVlIcmsEfd(mpC100EfdIcms.get(key).getVlIcms());
					}else {
						plan.setVlIcmsEfd(0.0);
					}
					
					plan.setVlDiferenca(res);
					
					linha = formatacaoPlanilha(plan);
					
					writer.write(linha);
					writer.newLine();
				}
				

				
				

			}

			writer.close();
			System.out.println("Exportado com Sucesso!!!");
		} catch (IOException e) {
		
			e.printStackTrace();
		}

	}
	
	private static String formatacaoPlanilha(PlanilhaDeNFeDeclaradasComValorDifEFD plan) {
		String linha = "";

		String vlDoc  =  String.format("%.2f", plan.getVlDoc());
		String vlIcms =  String.format("%.2f", plan.getVlIcms());
		
		String vlDocEfd  =  String.format("%.2f", plan.getVlDocEfd());
		String vlIcmsEfd    =  String.format("%.2f", plan.getVlIcmsEfd());
		
		String vlDiferenca    =  String.format("%.2f", plan.getVlDiferenca());
		 
		linha  = UtilsEConverters.mascaraCnpj(plan.getCnpj());
		linha += ";";
		linha += plan.getRazaoSocial();
		linha += ";";
		linha += UtilsEConverters.mascaraChaveNfe(plan.getChaveAcesso());
		linha += ";";
		linha += plan.getUf();
		linha += ";";
		linha += UtilsEConverters.getDataParaString2(plan.getDtEmissao());
		linha += ";";
		linha += plan.getNumDoc();
		linha += ";";
		linha += vlDoc.replace(".",",");
		linha += ";";
		linha += vlIcms.replace(".",",");
		linha += ";";
		linha += vlDocEfd.replace(".",",");
		linha += ";";
		linha += vlIcmsEfd.replace(".",",");
		linha += ";";
		linha += vlDiferenca.replace(".",",");
		linha += ";";
		
		return linha;
	}
	
	
	public static void main(String[] args) throws IOException, JAXBException {
		String ano   = "2023";
		String emp   = "ORVED";
		String estab = "MATRIZ";
		String cnpj  = "01629886000108";
		
		String anomes1  = ano.concat("01").concat(".txt");
		String anomesV1_Prop = ano.concat("01_XML_PROPRIOS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV1_Terc = ano.concat("01_XML_TERCEIROS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV1_Canc = ano.concat("01_NOTAS_CANCELADAS").concat(".txt");
		
		String anomes2  = ano.concat("02").concat(".txt");
		String anomesV2_Prop = ano.concat("02_XML_PROPRIOS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV2_Terc = ano.concat("02_XML_TERCEIROS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV2_Canc = ano.concat("02_NOTAS_CANCELADAS").concat(".txt");
		
		String anomes3  = ano.concat("03").concat(".txt");
		String anomesV3_Prop = ano.concat("03_XML_PROPRIOS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV3_Terc = ano.concat("03_XML_TERCEIROS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV3_Canc = ano.concat("03_NOTAS_CANCELADAS").concat(".txt");
		
		String anomes4  = ano.concat("04").concat(".txt");
		String anomesV4_Prop = ano.concat("04_XML_PROPRIOS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV4_Terc = ano.concat("04_XML_TERCEIROS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV4_Canc = ano.concat("04_NOTAS_CANCELADAS").concat(".txt");
		
		String anomes5  = ano.concat("05").concat(".txt");
		String anomesV5_Prop = ano.concat("05_XML_PROPRIOS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV5_Terc = ano.concat("05_XML_TERCEIROS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV5_Canc = ano.concat("05_NOTAS_CANCELADAS").concat(".txt");
		
		String anomes6  = ano.concat("06").concat(".txt");
		String anomesV6_Prop = ano.concat("06_XML_PROPRIOS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV6_Terc = ano.concat("06_XML_TERCEIROS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV6_Canc = ano.concat("06_NOTAS_CANCELADAS").concat(".txt");
		
		String anomes7  = ano.concat("07").concat(".txt");
		String anomesV7_Prop = ano.concat("07_XML_PROPRIOS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV7_Terc = ano.concat("07_XML_TERCEIROS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV7_Canc = ano.concat("07_NOTAS_CANCELADAS").concat(".txt");
		
		String anomes8  = ano.concat("08").concat(".txt");
		String anomesV8_Prop = ano.concat("08_XML_PROPRIOS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV8_Terc = ano.concat("08_XML_TERCEIROS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV8_Canc = ano.concat("08_NOTAS_CANCELADAS").concat(".txt");
		
		String anomes9  = ano.concat("09").concat(".txt");
		String anomesV9_Prop = ano.concat("09_XML_PROPRIOS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV9_Terc = ano.concat("09_XML_TERCEIROS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV9_Canc = ano.concat("09_NOTAS_CANCELADAS").concat(".txt");
		
		String anomes10 = ano.concat("10").concat(".txt");
		String anomesV10_Prop = ano.concat("10_XML_PROPRIOS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV10_Terc = ano.concat("10_XML_TERCEIROS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV10_Canc = ano.concat("10_NOTAS_CANCELADAS").concat(".txt");
		
		String anomes11 = ano.concat("11").concat(".txt");
		String anomesV11_Prop = ano.concat("11_XML_PROPRIOS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV11_Terc = ano.concat("11_XML_TERCEIROS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV11_Canc = ano.concat("11_NOTAS_CANCELADAS").concat(".txt");
		
		String anomes12 = ano.concat("12").concat(".txt");
		String anomesV12_Prop = ano.concat("12_XML_PROPRIOS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV12_Terc = ano.concat("12_XML_TERCEIROS_NAO_ENCONTRADOS").concat(".txt");
		String anomesV12_Canc = ano.concat("12_NOTAS_CANCELADAS").concat(".txt");
		
		Path xP1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\jan"));
	    Path xT1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\jan"));
	    Path p1  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1));	
	    
	    Path pV1_Prop = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV1_Prop));	    
	    Path pV1_Canc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV1_Canc));
	    Path pV1_Terc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV1_Terc));
		
	    Path xP2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\fev"));
	    Path xT2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\fev"));
	    Path p2  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes2));	
	    
	    Path pV2_Prop = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV2_Prop));	    
	    Path pV2_Canc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV2_Canc));
	    Path pV2_Terc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV2_Terc));
	    
	    Path xP3 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\mar"));
	    Path xT3 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\mar"));
	    Path p3  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes3));	
	    
	    Path pV3_Prop = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV3_Prop));	    
	    Path pV3_Canc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV3_Canc));
	    Path pV3_Terc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV3_Terc));
	    
	    Path xP4 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\abr"));
	    Path xT4 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\abr"));
	    Path p4  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes4));	
	    
	    Path pV4_Prop = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV4_Prop));	    
	    Path pV4_Canc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV4_Canc));
	    Path pV4_Terc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV4_Terc));
	    
	    Path xP5 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\mai"));
	    Path xT5 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\mai"));
	    Path p5  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes5));	
	    
	    Path pV5_Prop = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV5_Prop));	    
	    Path pV5_Canc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV5_Canc));
	    Path pV5_Terc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV5_Terc));
	    
	    Path xP6 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\jun"));
	    Path xT6 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\jun"));
	    Path p6  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes6));	
	    
	    Path pV6_Prop = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV6_Prop));	    
	    Path pV6_Canc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV6_Canc));
	    Path pV6_Terc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV6_Terc));
	    
	    Path xP7 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\jul"));
	    Path xT7 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\jul"));
	    Path p7  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes7));	
	    
	    Path pV7_Prop = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV7_Prop));	    
	    Path pV7_Canc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV7_Canc));
	    Path pV7_Terc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV7_Terc));
	    
	    Path xP8 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\ago"));
	    Path xT8 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\ago"));
	    Path p8  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes8));	
	    
	    Path pV8_Prop = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV8_Prop));	    
	    Path pV8_Canc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV8_Canc));
	    Path pV8_Terc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV8_Terc));
	    
	    Path xP9 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\set"));
	    Path xT9 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\set"));
	    Path p9  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes9));	
	    
	    Path pV9_Prop = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV9_Prop));	    
	    Path pV9_Canc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV9_Canc));
	    Path pV9_Terc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV9_Terc));
	    
	    Path xP10 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\out"));
	    Path xT10 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\out"));
	    Path p10  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes10));	
	    
	    Path pV10_Prop = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV10_Prop));	    
	    Path pV10_Canc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV10_Canc));
	    Path pV10_Terc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV10_Terc));
	    
	    
	    Path xP11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\nov"));
	    Path xT11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\nov"));
	    Path p11  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes11));	
	    
	    Path pV11_Prop = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV11_Prop));	    
	    Path pV11_Canc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV11_Canc));
	    Path pV11_Terc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV11_Terc));
	    
	    Path xP12 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\dez"));
	    Path xT12 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\dez"));
	    Path p12  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes12));	
	    
	    Path pV12_Prop = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV12_Prop));	    
	    Path pV12_Canc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV12_Canc));
	    Path pV12_Terc = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomesV12_Terc));


	    //Verificar de criar a pasta de Proprios e Terceiros dentro da Pasta do XML
	    Path p = p2;
		Path xP = xP2;
		Path xT = xT2;
		Path destProprios = pV2_Prop;
		Path destTerceiros = pV2_Terc;
		Path dest_Canc = pV2_Canc;
	   
		LeitorEfdIcms leitor = new LeitorEfdIcms();
		leitor.leitorSpedFiscal(p,0L,
				0L,0L,0L,
				0L, 0L );
		
		
		ParseDocXML xmls = new ParseDocXML();
		List<String> chavesSpedTerceiros = new ArrayList<String>();
		List<String> chavesSpedProprios = new ArrayList<String>();
		List<String> chavesXmlsProprios = new ArrayList<String>();
		List<String> chavesXmlsTerceiros = new ArrayList<String>();
		List<String> chavesCanceladas = new ArrayList<String>();
		
		
	    List<String> linhasNotasProprias =  new ArrayList<String>();  
	    List<String> linhasNotasTerceiros =  new ArrayList<String>();  
	    List<String> lines = Files.readAllLines(p, StandardCharsets.ISO_8859_1); //Aqui seta o Path do Sped
	    
	    Map<String, RegC100> mpC100EfdIcms       = new HashMap<String, RegC100>();
	    Map<String, NfeRecebidas> mpNFeRecebidas = new HashMap<String, NfeRecebidas>();
	    
	    
	    for (int i = 0; i < lines.size(); i++) {
	    	if(lines.get(i).startsWith("|C100|0|1|")){
	    		String[] campoC100 = lines.get(i).split("\\|");
	    		for (int c = 0; c < campoC100.length; c++) {
	    			if (c == 9) {
	    				if(!campoC100[c].isEmpty()) {
	    					chavesSpedTerceiros.add(campoC100[c]);
	    				}
	    			}
	    		}
	    	}
	    	if(lines.get(i).startsWith("|C100|0|0|")  || lines.get(i).startsWith("|C100|1|0|") ){   		
	    		if(lines.get(i).contains("|55|02|")) {
	    			chavesCanceladas.add(lines.get(i));
	    		}	    		
	    		String[] campoC100 = lines.get(i).split("\\|");
	    		for (int c = 0; c < campoC100.length; c++) {
	    			if (c == 9) {
	    				chavesSpedProprios.add(campoC100[c]);
	    			}
	    		}
	    	}
	    }

	    List<DocumentoFiscalEltronico> validaTipoDeParseNfeP = xmls.validaTipoDeParseNFE(xP.toFile()); //Aqui seta o Path do Dir XML Proprios
	    List<DocumentoFiscalEltronico> validaTipoDeParseNfeT = xmls.validaTipoDeParseNFE(xT.toFile()); //Aqui seta o Path do Dir XML Terceiros
	    
	    for(DocumentoFiscalEltronico doc : validaTipoDeParseNfeP){
	    	chavesXmlsProprios.add(doc.getIdent().getChaveeletronica());
	    }
	    
	    for(DocumentoFiscalEltronico doc : validaTipoDeParseNfeT){
	    	chavesXmlsTerceiros.add(doc.getIdent().getChaveeletronica());
	    	
	    	NfeRecebidas nfe = new NfeRecebidas();
	    	nfe.setCnpj(doc.getEmitente().getCnpj());
	    	nfe.setRazaoSocial(doc.getEmitente().getNome());
	    	nfe.setChaveAcesso(doc.getIdent().getChaveeletronica());
	    	nfe.setUf(UnidadeFederacao.fromCodigo(doc.getIdent().getCodigoUF()).sigla());
	    	nfe.setDtEmissao(UtilsEConverters.getStringParaData(doc.getIdent().getDataEmissao()));
	    	nfe.setNumDoc(doc.getIdent().getNumDoc());
	    	nfe.setVlDoc(Double.parseDouble(doc.getTotal().getIcmsTot().getVlNF()));
	    	nfe.setVlIcms(Double.parseDouble(doc.getTotal().getIcmsTot().getVlIcms()));
	    	
	    	mpNFeRecebidas.put(nfe.getChaveAcesso(), nfe);
	    }
	   
	    chavesSpedProprios.removeAll(chavesXmlsProprios);	
	    chavesSpedProprios.forEach(xml -> linhasNotasProprias.add(xml));
	    
	    chavesSpedTerceiros.removeAll(chavesXmlsTerceiros);
	    chavesSpedTerceiros.forEach(xml -> linhasNotasTerceiros.add(xml));
	    
	    
	    if(!linhasNotasProprias.isEmpty()) {
	    	 Files.write(destProprios, linhasNotasProprias, StandardOpenOption.CREATE);
	    }
	    
	    if(!linhasNotasTerceiros.isEmpty()) {
	    	 Files.write(destTerceiros, linhasNotasTerceiros, StandardOpenOption.CREATE);
	    }
	   
	    if(!chavesCanceladas.isEmpty()) {
	    	 Files.write(dest_Canc, chavesCanceladas, StandardOpenOption.CREATE);
	    }
	    
		for (RegC100 nota : leitor.getRegsC100()) {
			mpC100EfdIcms.put(nota.getChvNfe(), nota);
		}
		
		  String dirPlanilha   = "E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\NFeRecebidasDecComVlDifEfd_".concat(cnpj).concat("_").concat(ano).concat("02").concat(".csv"));
		  exportaNFeRecebidasDeclaradasComValoresDiferentesDaEfd(dirPlanilha,mpNFeRecebidas,mpC100EfdIcms);
		
		
	}
}

class PlanilhaDeNFeDeclaradasComValorDifEFD{
	
	private String cnpj;
	private String razaoSocial;
	private String chaveAcesso;
	private String uf;
	private LocalDate dtEmissao;
	private String numDoc;
	private Double vlDoc;
	private Double vlIcms;
	private Double vlDocEfd;
	private Double vlIcmsEfd;
	private Double vlDiferenca;
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getChaveAcesso() {
		return chaveAcesso;
	}
	public void setChaveAcesso(String chaveAcesso) {
		this.chaveAcesso = chaveAcesso;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public LocalDate getDtEmissao() {
		return dtEmissao;
	}
	public void setDtEmissao(LocalDate dtEmissao) {
		this.dtEmissao = dtEmissao;
	}
	public String getNumDoc() {
		return numDoc;
	}
	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}
	public Double getVlDoc() {
		return vlDoc;
	}
	public void setVlDoc(Double vlDoc) {
		this.vlDoc = vlDoc;
	}
	public Double getVlIcms() {
		return vlIcms;
	}
	public void setVlIcms(Double vlIcms) {
		this.vlIcms = vlIcms;
	}
	public Double getVlDocEfd() {
		return vlDocEfd;
	}
	public void setVlDocEfd(Double vlDocEfd) {
		this.vlDocEfd = vlDocEfd;
	}
	public Double getVlIcmsEfd() {
		return vlIcmsEfd;
	}
	public void setVlIcmsEfd(Double vlIcmsEfd) {
		this.vlIcmsEfd = vlIcmsEfd;
	}
	public Double getVlDiferenca() {
		return vlDiferenca;
	}
	public void setVlDiferenca(Double vlDiferenca) {
		this.vlDiferenca = vlDiferenca;
	}
	
	
	
}

class NfeRecebidas{
	
	private String cnpj;
	private String razaoSocial;
	private String chaveAcesso;
	private String uf;
	private LocalDate dtEmissao;
	private String numDoc;
	private Double vlDoc;
	private Double vlIcms;
	
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getChaveAcesso() {
		return chaveAcesso;
	}
	public void setChaveAcesso(String chaveAcesso) {
		this.chaveAcesso = chaveAcesso;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public LocalDate getDtEmissao() {
		return dtEmissao;
	}
	public void setDtEmissao(LocalDate dtEmissao) {
		this.dtEmissao = dtEmissao;
	}
	public String getNumDoc() {
		return numDoc;
	}
	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}
	public Double getVlDoc() {
		return vlDoc;
	}
	public void setVlDoc(Double vlDoc) {
		this.vlDoc = vlDoc;
	}
	public Double getVlIcms() {
		return vlIcms;
	}
	public void setVlIcms(Double vlIcms) {
		this.vlIcms = vlIcms;
	}
	
	
}

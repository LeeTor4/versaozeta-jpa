package txtSped;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GeraRegistrosEFDICMS {
	
	 public static List<EntidadeCsv> getRelacaoInv(String p3) throws FileNotFoundException{
		 File arquivoCSV = new File(p3);
		 String linhaDoArquivo = new String();
		 List<EntidadeCsv> retorno = new ArrayList<EntidadeCsv>();
	     @SuppressWarnings("resource")
		 Scanner leitor = new Scanner(arquivoCSV);
	     leitor.nextLine();
	     while(leitor.hasNext()){
	    	 EntidadeCsv obj = new EntidadeCsv();
	    	 linhaDoArquivo = leitor.nextLine();
			 String[] valoresEntreVirgula = linhaDoArquivo.split("\\;");	
			 for(int i=0;i<valoresEntreVirgula.length;i++){
				 
				    if(i==0){
				    	obj.setCodigo(valoresEntreVirgula[i]);
				    }
				    if(i==1) {
				    	obj.setDescricao(valoresEntreVirgula[i]);
				    }
                    if(i==2) {
				    	obj.setUnd(valoresEntreVirgula[i]);
				    }
                    if(i==3) {
				    	obj.setNcm(valoresEntreVirgula[i]);
				    }
                    if(i==4) {
				    	obj.setTipo(valoresEntreVirgula[i]);
				    }
                    if(i==5) {
				    	obj.setQtde(valoresEntreVirgula[i]);
				    }
                    if(i==6) {
				    	obj.setVrunit(valoresEntreVirgula[i]);
				    }
                    if(i==7) {
				    	obj.setValor(valoresEntreVirgula[i]);
				    }
			 }
			 
			 retorno.add(obj);
	     }
	     
	     return retorno;
	 }
	
	 
	 public static void geraRegistro0200(List<EntidadeCsv> lista, String caminhoOrig, String caminhoDest) {
		 try {
			PrintWriter pw = new PrintWriter(caminhoDest);
		
			for(EntidadeCsv ent : getRelacaoInv(caminhoOrig)){
				
				pw.printf("%1s","|");
				pw.printf("%-4s","0200");
				pw.printf("%1s","|");
				pw.printf("%-7s",ent.getCodigo());
				pw.printf("%1s","|");
				pw.printf("%-1s",ent.getDescricao());
				pw.printf("%1s","|");
				pw.printf("%1s","|");
				pw.printf("%1s","|");
				pw.printf("%1s",ent.getUnd());
				pw.printf("%1s","|");
				pw.printf("%1s",ent.getTipo());
				pw.printf("%1s","|");
				pw.printf("%1s",ent.getNcm());
				pw.printf("%1s","|");
				pw.printf("%1s","|");
				pw.printf("%1s","|");
				pw.printf("%1s","|");
				pw.printf("%1s","|");
				pw.printf("%1s\n","|");
			}
		 
			pw.close();
		    System.out.println("Exportado com Sucesso!!");
		    
		 } catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	 }
	 
	
	 public static void geraRegistroH010(List<EntidadeCsv> lista, String caminhoOrig, String caminhoDest) {
		 try {
			PrintWriter pw = new PrintWriter(caminhoDest);
		
			for(EntidadeCsv ent : getRelacaoInv(caminhoOrig)){
				
				pw.printf("%1s","|");
				pw.printf("%-4s","H010");
				pw.printf("%1s","|");
				pw.printf("%-7s",ent.getCodigo());
				pw.printf("%1s","|");
				pw.printf("%1s",ent.getUnd());
				pw.printf("%1s","|");
				pw.printf("%1s",ent.getQtde());
				pw.printf("%1s","|");
				pw.printf("%1s",ent.getVrunit());
				pw.printf("%1s","|");
				pw.printf("%1s",ent.getValor());
				pw.printf("%1s","|");
				pw.printf("%1s","0");
				pw.printf("%1s","|");
				pw.printf("%1s","|");
				pw.printf("%1s","|");
				pw.printf("%1s","1011501020");
				pw.printf("%1s","|");
				pw.printf("%1s\n","|");
			}
		 
			pw.close();
		    System.out.println("Exportado com Sucesso!!");
		    
		 } catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	 }
	 
	 public static void geraRegistroK200(List<EntidadeCsv> lista, String caminhoOrig, String caminhoDest) {
		 
		 try {
			PrintWriter pw = new PrintWriter(caminhoDest);
		
			for(EntidadeCsv ent : getRelacaoInv(caminhoOrig)){
				
				pw.printf("%1s","|");
				pw.printf("%-4s","K200");
				pw.printf("%1s","|");
				pw.printf("%1s","31012019");
				pw.printf("%1s","|");
				pw.printf("%-7s",ent.getCodigo());
				pw.printf("%1s","|");
				pw.printf("%1s",ent.getQtde());
				pw.printf("%1s","|");
				pw.printf("%1s","0"); //IND_EST
				pw.printf("%1s","|");
				pw.printf("%s",""); // COD_PART
				//pw.printf("%1s","|");
				pw.printf("%1s\n","|");
			}
		 
			pw.close();
		    System.out.println("Exportado com Sucesso!!");
		    
		 } catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		 
	 }
	public static void main(String[] args) throws FileNotFoundException {
		
		String path = "C:\\Users\\chico\\Downloads\\Orved_k28022019.csv";
		String pathDest0200 = "E:\\Orved_registro0200_28022019.txt";
		String pathDestk200 = "E:\\Orved_registroK200_28022019.txt";
		getRelacaoInv(path);
	    //geraRegistro0200(getRelacaoInv(path), path, pathDest0200);
	    //geraRegistroH010(getRelacaoInv(path), path, pathDest);
	      geraRegistroK200(getRelacaoInv(path), path, pathDestk200);
	
	
	}

}

class EntidadeCsv{
	
	private String codigo;	
	private String descricao;	
	private String und;	
	private String ncm;	
	private String tipo;	
	private String qtde;	
	private String vrunit;	
	private String valor;
	
	
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
	public String getUnd() {
		return und;
	}
	public void setUnd(String und) {
		this.und = und;
	}
	public String getNcm() {
		return ncm;
	}
	public void setNcm(String ncm) {
		this.ncm = ncm;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getQtde() {
		return qtde;
	}
	public void setQtde(String qtde) {
		this.qtde = qtde;
	}
	public String getVrunit() {
		return vrunit;
	}
	public void setVrunit(String vrunit) {
		this.vrunit = vrunit;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	

}

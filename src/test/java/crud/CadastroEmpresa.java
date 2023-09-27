package crud;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.cnpj.dominio.AtividadesSecundarias;
import com.cnpj.dominio.Entidade;
import com.cnpj.servico.ServicoCEP;
import com.cnpj.servico.ServicoCnpj;
import com.zeta.dao.EmpresaDao;
import com.zeta.model.Empresa;
import com.zeta.model.Endereco;
import com.zeta.model.EquipamentoECF;
import com.zeta.model.Estabelecimento;
import com.zeta.model.Participante;
import com.zeta.util.UtilsEConverters;

public class CadastroEmpresa {
   
	public static List<RelacaoDosParticipantes> lerRelacaoParticipantes(String caminho) {
		List<RelacaoDosParticipantes> retorno = new ArrayList<RelacaoDosParticipantes>();
		File arquivoCSV = new File(caminho);
		String linhaDoArquivo = new String();

		try {
			@SuppressWarnings("resource")
			Scanner leitor = new Scanner(arquivoCSV);
			leitor.nextLine();
			while (leitor.hasNext()) {
				RelacaoDosParticipantes obj = new RelacaoDosParticipantes();
				linhaDoArquivo = leitor.nextLine();
				String[] valoresEntreVirgula = linhaDoArquivo.split("\\;");
				System.out.println(valoresEntreVirgula[0]+"|"+valoresEntreVirgula[1]+"|"+valoresEntreVirgula[2]);
				obj.setId(Long.parseLong(valoresEntreVirgula[0]));
				obj.setCodigo(valoresEntreVirgula[1]);
				obj.setNome(valoresEntreVirgula[2]);
				obj.setCnpj(valoresEntreVirgula[3]);
				retorno.add(obj);
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
     
		return retorno;
	}
	
	
	public static void exportarPlanilhaParticipantesCompleta(String file,List<RelacaoDosParticipantes> participantes) {
		BufferedWriter writer;

		try {
			writer = new BufferedWriter(new FileWriter(new File(file)));
		
			String linha = " ";

			linha = cabecalho();

			writer.write(linha);
			writer.newLine();
		
			List<String> cnpjs = new ArrayList<String>();
			Map<String,RelacaoDosParticipantes> mpRelacaoParticipantes = new HashMap<String,RelacaoDosParticipantes>();
		
		    for(int i = 2100; i < 2260; i++) {
		    	cnpjs.add(participantes.get(i).getCnpj());
		    	mpRelacaoParticipantes.put(UtilsEConverters.mascaraCnpj(participantes.get(i).getCnpj()),participantes.get(i));
		    }
		
		    
		    ExecutorService ex = Executors.newCachedThreadPool();
		    
		    CallableBuscaInformacoesPeloCnpj callCnpj = new CallableBuscaInformacoesPeloCnpj(cnpjs);
		    
		    Future<List<Entidade>> submit = ex.submit(callCnpj);
		    
		    for(Entidade ent : submit.get()){
		    	PlanilhaCompletaParticipantes plan = new PlanilhaCompletaParticipantes();
		    	
    	        System.out.println(ent.getCnpj() + " - "+ ent.getNome()+ "\n\n" +
		           "Atividade Principal : " + "\n" +
		           ent.getCodigoAtivPrincipal() +" - "+ ent.getAtividadePrincipal() + "\n" +
		           "Natureza Jurídica : "+ ent.getNaturezaJuridica()+ "\n");
    	       
    	        plan.setId(mpRelacaoParticipantes.get(ent.getCnpj()).getId());
    	        plan.setCodigo(mpRelacaoParticipantes.get(ent.getCnpj()).getCodigo());
		        plan.setNome(ent.getNome());
		        plan.setCnpj(ent.getCnpj());
		        plan.setCodatividadePrincipal(ent.getCodigoAtivPrincipal());
		        plan.setAtividadePrincipal(ent.getAtividadePrincipal());
		        plan.setNaturezaJuridica(ent.getNaturezaJuridica());
		        plan.setPorte(ent.getPorte());
		        plan.setSituacao(ent.getSituacao());
		        plan.setStatus((ent.getStatus()==null ? "" : ent.getStatus()));
    	        
		        linha = formatacaoPlanilha(plan);
		        writer.write(linha);
				writer.newLine();
		    }
		    
		    ex.shutdown(); 
		    
			writer.close();
			System.out.println("Exportado com Sucesso!!!");
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		} catch (ExecutionException e) {
		
			e.printStackTrace();
		}
	}
	
	
	
	private static String cabecalho() {
		String linha = "";
	    StringBuilder sb = new StringBuilder();
	    linha = sb.append("ID")
	    		  .append(";")
	    		  .append("CODIGO_FORTES")
	    		  .append(";")
	    		  .append("NOME")
	    		  .append(";")
	    		  .append("CNPJ")
	    		  .append(";")
	    		  .append("COD_ATIV_PRINCIPAL")
	    		  .append(";")
	    		  .append("ATIV_PRINCIPAL")
	    		  .append(";")
	    		  .append("NATUREZA_JURIDICA")
	    		  .append(";")
	    		  .append("PORTE")
	    		  .append(";")
	    		  .append("STATUS")
	    		  .toString();
		return linha;
	}


	private static String formatacaoPlanilha(PlanilhaCompletaParticipantes plan) {
		String linha = "";
		
		linha  = String.valueOf(plan.getId());
		linha += ";";
		linha += plan.getCodigo();
		linha += ";";
		linha += "".concat(plan.getNome());
		linha += ";";
		linha += plan.getCnpj();
		linha += ";";
		linha += plan.getCodatividadePrincipal();
		linha += ";";
		linha += plan.getAtividadePrincipal();
		linha += ";";
		linha += plan.getNaturezaJuridica();
		linha += ";";
		linha += plan.getPorte();
		linha += ";";
		linha += plan.getStatus();
		linha += ";";
		
		
		return linha;
	}
	public static void main(String[] args) throws Exception {

		    //cadastrarEmpresasSellene();
	       //cadastrarMegafarmaSellene();
		   //cadastrarEmpresasBenelux();
		   //cadastrarEmpresasDeposito();
		   //cadastrarDeliveryfarmaSellene();
		   //cadastrarMegadiet();
		
		   //cadastrarEmpresasOrved();
		
		    List<String> cnpjs =  new ArrayList<String>();
		    cnpjs.add("23443518000103");
		    cnpjs.add("07272404000183");
		    cnpjs.add("05268526000766");
		    cnpjs.add("09529603000150");
		    cnpjs.add("63381073000190");
		    cnpjs.add("15126437000224");
		    cnpjs.add("07965184000173");
		    cnpjs.add("05329222000176");
		    
//            for(int i = 0; i < cnpjs.size(); i++) {           	
//                try {
//                    //System.out.println(cnpjs.get(i));
//                    Entidade buscaInformacoesPeloCnpj = ServicoCnpj.buscaInformacoesPeloCnpj(cnpjs.get(i));
//        	        System.out.println(buscaInformacoesPeloCnpj.getCnpj() + " - "+ buscaInformacoesPeloCnpj.getNome()+ "\n\n" +
//        	        		           "Atividade Principal : " + "\n" +
//        	        		           buscaInformacoesPeloCnpj.getCodigoAtivPrincipal() +" - "+ buscaInformacoesPeloCnpj.getAtividadePrincipal() + "\n" +
//        	        		           "Natureza Jurídica : "+ buscaInformacoesPeloCnpj.getNaturezaJuridica()+ "\n");
//                    //assuming it takes 20 secs to complete the task
//                    Thread.sleep(21000);                   
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
		    


		    
		    
		    String caminho = "E:\\EMPRESAS".concat("\\").concat("SELLENE").concat("\\Grupo_Sellene_Participantes_base".concat(".csv"));
		    String dirPlanilha   = "E:\\EMPRESAS".concat("\\").concat("SELLENE").concat("\\").concat("\\RelacaoParticipantesCompleta".concat(".csv"));
		    lerRelacaoParticipantes(caminho);
		    exportarPlanilhaParticipantesCompleta(dirPlanilha, lerRelacaoParticipantes(caminho));
		    
	}
	
	private static void cadastrarMegadiet()  throws Exception{
		LocalDate data = LocalDate.of(2009, Month.OCTOBER, 22);
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String valorFormatado = data.format(formatador);
        
        EmpresaDao dao = new EmpresaDao();

		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Sellene Comercio e Representações Ltda");
		empresa.setNmFantasia("Sellene Matriz");
		empresa.setCnpjBase("05329222");
		empresa.setDtIniAtiv(UtilsEConverters.getStringParaData4(valorFormatado));
		
		Endereco end1 = new Endereco();

		com.cnpj.dominio.Endereco ender1 = ServicoCEP.buscaEnderecoPeloCEP("60140140");

		end1.setCep(ender1.getCep());
		end1.setNmLogradouro(ender1.getLogradouro());
		end1.setNumLogradouro("45");
		end1.setBairro(ender1.getBairro());
		end1.setCodMun(ender1.getIbge().substring(2, 7));
		end1.setNmMun(ender1.getLocalidade());
		end1.setCodUf(ender1.getIbge().substring(0, 2));
		end1.setUf(ender1.getUf());
		
		Estabelecimento matriz = new Estabelecimento("05329222000176", "Sellene Comercio e Representações Ltda",
				"Matriz", end1, empresa);

		
		Endereco end6 = new Endereco();
		com.cnpj.dominio.Endereco ender6 = ServicoCEP.buscaEnderecoPeloCEP("60115220");

		end6.setCep(ender6.getCep());
		end6.setNmLogradouro(ender6.getLogradouro());
		end6.setNumLogradouro("195");
		end6.setBairro(ender6.getBairro());  
		end6.setCodMun(ender6.getIbge().substring(2, 7));
		end6.setNmMun(ender6.getLocalidade());
		end6.setCodUf(ender6.getIbge().substring(0, 2));
		end6.setUf(ender6.getUf());
		
		Estabelecimento megadiet = new Estabelecimento("05329222000419", "Sellene Comercio e Representações Ltda",
				"Megadiet", end6, empresa);
		
		List<EquipamentoECF> ecfsMegadiet = new ArrayList<EquipamentoECF>();
		
		EquipamentoECF ecf1_mega = new EquipamentoECF();
		ecf1_mega.setCodModDocFiscal("2D");
		ecf1_mega.setModeloEquip("ECF");
		ecf1_mega.setNumSerieFabECF("BE090910100010023046");
		ecf1_mega.setNumECF("001");
		ecfsMegadiet.add(ecf1_mega);
		
		EquipamentoECF ecf2_mega = new EquipamentoECF();
		ecf2_mega.setCodModDocFiscal("2D");
		ecf2_mega.setModeloEquip("ECF");
		ecf2_mega.setNumSerieFabECF("BE090910100010021930");
		ecf2_mega.setNumECF("003");
		ecfsMegadiet.add(ecf2_mega);
		
		EquipamentoECF ecf3_mega = new EquipamentoECF();
		ecf3_mega.setCodModDocFiscal("2D");
		ecf3_mega.setModeloEquip("ECF");
		ecf3_mega.setNumSerieFabECF("BE091210100011203011");
		ecf3_mega.setNumECF("004");
		ecfsMegadiet.add(ecf3_mega);
		
		EquipamentoECF ecf4_mega = new EquipamentoECF();
		ecf4_mega.setCodModDocFiscal("2D");
		ecf4_mega.setModeloEquip("ECF");
		ecf4_mega.setNumSerieFabECF("BE091410100011250892");
		ecf4_mega.setNumECF("006");
		ecfsMegadiet.add(ecf4_mega);
		
		megadiet.setEquipEcf(ecfsMegadiet);

		empresa.adicionaEstab(matriz);
		empresa.adicionaEstab(megadiet);

		dao.adiciona(empresa);
		

	}
	
	private static void cadastrarEmpresasDeposito() throws Exception {
		LocalDate data = LocalDate.of(2009, Month.OCTOBER, 22);
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String valorFormatado = data.format(formatador);
        
        EmpresaDao dao = new EmpresaDao();

		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Sellene Comercio e Representações Ltda");
		empresa.setNmFantasia("Sellene Matriz");
		empresa.setCnpjBase("05329222");
		empresa.setDtIniAtiv(UtilsEConverters.getStringParaData4(valorFormatado));
		
		Endereco end1 = new Endereco();

		com.cnpj.dominio.Endereco ender1 = ServicoCEP.buscaEnderecoPeloCEP("61603005");

		end1.setCep(ender1.getCep());
		end1.setNmLogradouro(ender1.getLogradouro());
		end1.setNumLogradouro("45");
		end1.setBairro(ender1.getBairro());
		end1.setCodMun(ender1.getIbge().substring(2, 7));
		end1.setNmMun(ender1.getLocalidade());
		end1.setCodUf(ender1.getIbge().substring(0, 2));
		end1.setUf(ender1.getUf());
		
		Estabelecimento matriz = new Estabelecimento("05329222000508", "DEPOSITO CAUCAIA",
				"Deposito", end1, empresa);

		empresa.adicionaEstab(matriz);

		dao.adiciona(empresa);
		
		Endereco end6 = new Endereco();
		com.cnpj.dominio.Endereco ender6 = ServicoCEP.buscaEnderecoPeloCEP("60115220");

		end6.setCep(ender6.getCep());
		end6.setNmLogradouro(ender6.getLogradouro());
		end6.setNumLogradouro("195");
		end6.setBairro(ender6.getBairro());  
		end6.setCodMun(ender6.getIbge().substring(2, 7));
		end6.setNmMun(ender6.getLocalidade());
		end6.setCodUf(ender6.getIbge().substring(0, 2));
		end6.setUf(ender6.getUf());
	}
	
	private static void cadastrarEmpresasOrved() throws Exception {
		LocalDate data = LocalDate.of(2003, Month.OCTOBER, 04);
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String valorFormatado = data.format(formatador);
		
		EmpresaDao dao = new EmpresaDao();
		
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("ORVED & BROCK INDUSTRIA DE MAQUINAS A VACUO LTDA");
		empresa.setNmFantasia("ORVED & BROCK");
		empresa.setCnpjBase("01629886");
		empresa.setDtIniAtiv(UtilsEConverters.getStringParaData4(valorFormatado));
		
		Endereco end1 = new Endereco();
		
		com.cnpj.dominio.Endereco ender1 = ServicoCEP.buscaEnderecoPeloCEP("60160280");

		end1.setCep(ender1.getCep());
		end1.setNmLogradouro(ender1.getLogradouro());
		end1.setNumLogradouro("915");
		end1.setCompl("Sala 1001 - Edifíco Ébano");
		end1.setBairro(ender1.getBairro());
		end1.setCodMun(ender1.getIbge().substring(2, 7));
		end1.setNmMun(ender1.getLocalidade());
		end1.setCodUf(ender1.getIbge().substring(0, 2));
		end1.setUf(ender1.getUf());
		
		Estabelecimento matriz = new Estabelecimento("01629886000108", "ORVED & BROCK INDUSTRIA DE MAQUINAS A VACUO LTDA",
				"Matriz", end1, empresa);

		empresa.adicionaEstab(matriz);

		dao.adiciona(empresa);
	}

	private static void cadastrarEmpresasBenelux() throws Exception {
		
		LocalDate data = LocalDate.of(2016, Month.APRIL, 22);
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String valorFormatado = data.format(formatador);
		
		EmpresaDao dao = new EmpresaDao();
		
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("BENELUX DISTRIBUIDORA DE ALIMENTOS LTDA");
		empresa.setNmFantasia("BENELUX NUTRITION");
		empresa.setCnpjBase("24653373");
		empresa.setDtIniAtiv(UtilsEConverters.getStringParaData4(valorFormatado));
		
		Endereco end1 = new Endereco();

		com.cnpj.dominio.Endereco ender1 = ServicoCEP.buscaEnderecoPeloCEP("60115125");

		end1.setCep(ender1.getCep());
		end1.setNmLogradouro(ender1.getLogradouro());
		end1.setNumLogradouro("1500");
		end1.setBairro(ender1.getBairro());
		end1.setCodMun(ender1.getIbge().substring(2, 7));
		end1.setNmMun(ender1.getLocalidade());
		end1.setCodUf(ender1.getIbge().substring(0, 2));
		end1.setUf(ender1.getUf());
		
		Estabelecimento matriz = new Estabelecimento("24653373000120", "BENELUX DISTRIBUIDORA DE ALIMENTOS LTDA",
				"Matriz", end1, empresa);

		empresa.adicionaEstab(matriz);

		dao.adiciona(empresa);
	}
	
	private static void cadastrarMegafarmaSellene() throws Exception {
		LocalDate data = LocalDate.of(2005, Month.NOVEMBER, 03 );
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String valorFormatado = data.format(formatador);
        
        EmpresaDao dao = new EmpresaDao();

		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Sellene Comercio e Representações Ltda");
		empresa.setNmFantasia("Sellene Matriz");
		empresa.setCnpjBase("05329222");
		empresa.setDtIniAtiv(UtilsEConverters.getStringParaData4(valorFormatado));
		
		
		Endereco end1 = new Endereco();

		com.cnpj.dominio.Endereco ender1 = ServicoCEP.buscaEnderecoPeloCEP("60140140");

		end1.setCep(ender1.getCep());
		end1.setNmLogradouro(ender1.getLogradouro());
		end1.setNumLogradouro("205");
		end1.setBairro(ender1.getBairro());
		end1.setCodMun(ender1.getIbge().substring(2, 7));
		end1.setNmMun(ender1.getLocalidade());
		end1.setCodUf(ender1.getIbge().substring(0, 2));
		end1.setUf(ender1.getUf());
		
		Estabelecimento matriz = new Estabelecimento("05329222000176", "Sellene Comercio e Representações Ltda",
				"Matriz", end1, empresa);
		
        Endereco end6 = new Endereco();
		com.cnpj.dominio.Endereco ender6 = ServicoCEP.buscaEnderecoPeloCEP("60115220");

		end6.setCep(ender6.getCep());
		end6.setNmLogradouro(ender6.getLogradouro());
		end6.setNumLogradouro("195");
		end6.setBairro(ender6.getBairro());  
		end6.setCodMun(ender6.getIbge().substring(2, 7));
		end6.setNmMun(ender6.getLocalidade());
		end6.setCodUf(ender6.getIbge().substring(0, 2));
		end6.setUf(ender6.getUf());
		
		
		Estabelecimento megafarma = new Estabelecimento("05329222000680", "Sellene Comercio e Representações Ltda",
				"Megafarma", end6, empresa);
		
		List<EquipamentoECF> ecfsMegafarma = new ArrayList<EquipamentoECF>();

		EquipamentoECF ecf1_loja06 = new EquipamentoECF();
		ecf1_loja06.setCodModDocFiscal("2D");
		ecf1_loja06.setModeloEquip("ECF");
		ecf1_loja06.setNumSerieFabECF("BE091010100011201429");
		ecf1_loja06.setNumECF("1");
		ecfsMegafarma.add(ecf1_loja06);

		EquipamentoECF ecf2_loja06 = new EquipamentoECF();
		ecf2_loja06.setCodModDocFiscal("2D");
		ecf2_loja06.setModeloEquip("ECF");
		ecf2_loja06.setNumSerieFabECF("DR0814BR000000407938");
		ecf2_loja06.setNumECF("2");
		ecfsMegafarma.add(ecf2_loja06);

		EquipamentoECF ecf3_loja06 = new EquipamentoECF();
		ecf3_loja06.setCodModDocFiscal("2D");
		ecf3_loja06.setModeloEquip("ECF");
		ecf3_loja06.setNumSerieFabECF("BE090910100010021907");
		ecf3_loja06.setNumECF("001");
		ecfsMegafarma.add(ecf3_loja06);
		
		megafarma.setEquipEcf(ecfsMegafarma);

		empresa.adicionaEstab(matriz);
		empresa.adicionaEstab(megafarma);

		dao.adiciona(empresa);
		
	}
	
	private static void cadastrarDeliveryfarmaSellene() throws Exception {
		LocalDate data = LocalDate.of(2005, Month.NOVEMBER, 03 );
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String valorFormatado = data.format(formatador);
        
        EmpresaDao dao = new EmpresaDao();

		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Sellene Comercio e Representações Ltda");
		empresa.setNmFantasia("Sellene Matriz");
		empresa.setCnpjBase("05329222");
		empresa.setDtIniAtiv(UtilsEConverters.getStringParaData4(valorFormatado));
		
		
		Endereco end1 = new Endereco();

		com.cnpj.dominio.Endereco ender1 = ServicoCEP.buscaEnderecoPeloCEP("60140140");

		end1.setCep(ender1.getCep());
		end1.setNmLogradouro(ender1.getLogradouro());
		end1.setNumLogradouro("205");
		end1.setBairro(ender1.getBairro());
		end1.setCodMun(ender1.getIbge().substring(2, 7));
		end1.setNmMun(ender1.getLocalidade());
		end1.setCodUf(ender1.getIbge().substring(0, 2));
		end1.setUf(ender1.getUf());
		
		Estabelecimento matriz = new Estabelecimento("05329222000176", "Sellene Comercio e Representações Ltda",
				"Matriz", end1, empresa);
		
        Endereco end6 = new Endereco();
		com.cnpj.dominio.Endereco ender6 = ServicoCEP.buscaEnderecoPeloCEP("60140140");

		end6.setCep(ender6.getCep());
		end6.setNmLogradouro(ender6.getLogradouro());
		end6.setNumLogradouro("195");
		end6.setBairro(ender6.getBairro());  
		end6.setCodMun(ender6.getIbge().substring(2, 7));
		end6.setNmMun(ender6.getLocalidade());
		end6.setCodUf(ender6.getIbge().substring(0, 2));
		end6.setUf(ender6.getUf());
		
		
		Estabelecimento delivery = new Estabelecimento("05329222000338", "Sellene Comercio e Representações Ltda",
				"Delivery", end6, empresa);
		
		List<EquipamentoECF> ecfsDelivery = new ArrayList<EquipamentoECF>();

		EquipamentoECF ecf1_loja03 = new EquipamentoECF();
		ecf1_loja03.setCodModDocFiscal("2D");
		ecf1_loja03.setModeloEquip("ECF");
		ecf1_loja03.setNumSerieFabECF("BE0107SC56000035178");
		ecf1_loja03.setNumECF("003");
		ecfsDelivery.add(ecf1_loja03);

		EquipamentoECF ecf2_loja03 = new EquipamentoECF();
		ecf2_loja03.setCodModDocFiscal("2D");
		ecf2_loja03.setModeloEquip("ECF");
		ecf2_loja03.setNumSerieFabECF("BE0107SC56000035180");
		ecf2_loja03.setNumECF("004");
		ecfsDelivery.add(ecf2_loja03);

		EquipamentoECF ecf3_loja03 = new EquipamentoECF();
		ecf3_loja03.setCodModDocFiscal("2D");
		ecf3_loja03.setModeloEquip("ECF");
		ecf3_loja03.setNumSerieFabECF("BE051175610000105333");
		ecf3_loja03.setNumECF("005");
		ecfsDelivery.add(ecf3_loja03);
		
		EquipamentoECF ecf4_loja03 = new EquipamentoECF();
		ecf4_loja03.setCodModDocFiscal("2D");
		ecf4_loja03.setModeloEquip("ECF");
		ecf4_loja03.setNumSerieFabECF("BE051175610000105336");
		ecf4_loja03.setNumECF("006");
		ecfsDelivery.add(ecf4_loja03);
		
		EquipamentoECF ecf5_loja03 = new EquipamentoECF();
		ecf5_loja03.setCodModDocFiscal("2D");
		ecf5_loja03.setModeloEquip("ECF");
		ecf5_loja03.setNumSerieFabECF("1234");
		ecf5_loja03.setNumECF("001");
		ecfsDelivery.add(ecf5_loja03);
		
		EquipamentoECF ecf6_loja03 = new EquipamentoECF();
		ecf6_loja03.setCodModDocFiscal("2D");
		ecf6_loja03.setModeloEquip("ECF");
		ecf6_loja03.setNumSerieFabECF("4321");
		ecf6_loja03.setNumECF("002");
		ecfsDelivery.add(ecf6_loja03);
		
		delivery.setEquipEcf(ecfsDelivery);

		empresa.adicionaEstab(matriz);
		empresa.adicionaEstab(delivery);

		dao.adiciona(empresa);
		
	}
	
	private static void cadastrarEmpresasSellene() throws Exception {
		LocalDate agora = LocalDate.now();

		EmpresaDao dao = new EmpresaDao();

		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Sellene Comercio e Representações Ltda");
		empresa.setNmFantasia("Sellene Matriz");
		empresa.setCnpjBase("05329222");
		empresa.setDtIniAtiv(agora);

		Endereco end1 = new Endereco();

		com.cnpj.dominio.Endereco ender1 = ServicoCEP.buscaEnderecoPeloCEP("60140140");

		end1.setCep(ender1.getCep());
		end1.setNmLogradouro(ender1.getLogradouro());
		end1.setNumLogradouro("205");
		end1.setBairro(ender1.getBairro());
		end1.setCodMun(ender1.getIbge().substring(2, 7));
		end1.setNmMun(ender1.getLocalidade());
		end1.setCodUf(ender1.getIbge().substring(0, 2));
		end1.setUf(ender1.getUf());

		Endereco end2 = new Endereco();
		com.cnpj.dominio.Endereco ender2 = ServicoCEP.buscaEnderecoPeloCEP("60115220");

		end2.setCep(ender2.getCep());
		end2.setNmLogradouro(ender2.getLogradouro());
		end2.setNumLogradouro("1253");
		end2.setBairro(ender2.getBairro());
		end2.setCodMun(ender2.getIbge().substring(2, 7));
		end2.setNmMun(ender2.getLocalidade());
		end2.setCodUf(ender2.getIbge().substring(0, 2));
		end2.setUf(ender2.getUf());

		Endereco end3 = new Endereco();
		com.cnpj.dominio.Endereco ender3 = ServicoCEP.buscaEnderecoPeloCEP("60175047");

		end3.setCep(ender3.getCep());
		end3.setNmLogradouro(ender3.getLogradouro());
		end3.setNumLogradouro("5753");
		end3.setBairro(ender3.getBairro());
		end3.setCodMun(ender3.getIbge().substring(2, 7));
		end3.setNmMun(ender3.getLocalidade());
		end3.setCodUf(ender3.getIbge().substring(0, 2));
		end3.setUf(ender3.getUf());

		Endereco end4 = new Endereco();
		com.cnpj.dominio.Endereco ender4 = ServicoCEP.buscaEnderecoPeloCEP("60160230");

		end4.setCep(ender4.getCep());
		end4.setNmLogradouro(ender4.getLogradouro());
		end4.setNumLogradouro("1233");
		end4.setBairro(ender4.getBairro());
		end4.setCodMun(ender4.getIbge().substring(2, 7));
		end4.setNmMun(ender4.getLocalidade());
		end4.setCodUf(ender4.getIbge().substring(0, 2));
		end4.setUf(ender4.getUf());

		Endereco end5 = new Endereco();
		com.cnpj.dominio.Endereco ender5 = ServicoCEP.buscaEnderecoPeloCEP("60140140");

		end5.setCep(ender5.getCep());
		end5.setNmLogradouro(ender5.getLogradouro());
		end5.setNumLogradouro("195");
		end5.setBairro(ender5.getBairro());
		end5.setCodMun(ender5.getIbge().substring(2, 7));
		end5.setNmMun(ender5.getLocalidade());
		end5.setCodUf(ender5.getIbge().substring(0, 2));
		end5.setUf(ender5.getUf());

		Endereco end6 = new Endereco();
		com.cnpj.dominio.Endereco ender6 = ServicoCEP.buscaEnderecoPeloCEP("60115220");

		end6.setCep(ender6.getCep());
		end6.setNmLogradouro(ender6.getLogradouro());
		end6.setNumLogradouro("195");
		end6.setBairro(ender6.getBairro());
		end6.setCodMun(ender6.getIbge().substring(2, 7));
		end6.setNmMun(ender6.getLocalidade());
		end6.setCodUf(ender6.getIbge().substring(0, 2));
		end6.setUf(ender6.getUf());

		Estabelecimento matriz = new Estabelecimento("05329222000176", "Sellene Comercio e Representações Ltda",
				"Matriz", end1, empresa);

		Estabelecimento mega = new Estabelecimento("05329222000419", "Sellene Comercio e Representações Ltda",
				"Megadiet", end2, empresa);

		Estabelecimento sao = new Estabelecimento("05329222000761", "Sellene Comercio e Representações Ltda",
				"Sao Mateus", end3, empresa);

		Estabelecimento harm = new Estabelecimento("05329222000842", "Sellene Comercio e Representações Ltda",
				"Harmony", end4, empresa);

		Estabelecimento loja3 = new Estabelecimento("05329222000338", "Sellene Comercio e Representações Ltda",
				"Delivery", end5, empresa);

		Estabelecimento megafarma = new Estabelecimento("05329222000680", "Sellene Comercio e Representações Ltda",
				"Megafarma", end6, empresa);

		List<EquipamentoECF> ecfsMegadiet = new ArrayList<EquipamentoECF>();
		EquipamentoECF ecf1 = new EquipamentoECF();
		ecf1.setCodModDocFiscal("2D");
		ecf1.setModeloEquip("ECF");
		ecf1.setNumSerieFabECF("BE090910100010021930");
		ecf1.setNumECF("3");
		ecfsMegadiet.add(ecf1);

		EquipamentoECF ecf2 = new EquipamentoECF();
		ecf2.setCodModDocFiscal("2D");
		ecf2.setModeloEquip("ECF");
		ecf2.setNumSerieFabECF("BE091210100011203011");
		ecf2.setNumECF("4");
		ecfsMegadiet.add(ecf2);

		EquipamentoECF ecf3 = new EquipamentoECF();
		ecf3.setCodModDocFiscal("2D");
		ecf3.setModeloEquip("ECF");
		ecf3.setNumSerieFabECF("BE091410100011250892");
		ecf3.setNumECF("6");
		ecfsMegadiet.add(ecf3);

		List<EquipamentoECF> ecfsLoja03 = new ArrayList<EquipamentoECF>();
		EquipamentoECF ecf1_loja03 = new EquipamentoECF();
		ecf1_loja03.setCodModDocFiscal("2D");
		ecf1_loja03.setModeloEquip("ECF");
		ecf1_loja03.setNumSerieFabECF("1234");
		ecf1_loja03.setNumECF("1");
		ecfsLoja03.add(ecf1_loja03);

		EquipamentoECF ecf2_loja03 = new EquipamentoECF();
		ecf2_loja03.setCodModDocFiscal("2D");
		ecf2_loja03.setModeloEquip("ECF");
		ecf2_loja03.setNumSerieFabECF("4321");
		ecf2_loja03.setNumECF("2");
		ecfsLoja03.add(ecf2_loja03);

		EquipamentoECF ecf3_loja03 = new EquipamentoECF();
		ecf3_loja03.setCodModDocFiscal("2D");
		ecf3_loja03.setModeloEquip("ECF");
		ecf3_loja03.setNumSerieFabECF("BE0107SC56000035178");
		ecf3_loja03.setNumECF("3");
		ecfsLoja03.add(ecf3_loja03);

		EquipamentoECF ecf4_loja03 = new EquipamentoECF();
		ecf4_loja03.setCodModDocFiscal("2D");
		ecf4_loja03.setModeloEquip("ECF");
		ecf4_loja03.setNumSerieFabECF("BE0107SC56000035180");
		ecf4_loja03.setNumECF("4");
		ecfsLoja03.add(ecf4_loja03);

		EquipamentoECF ecf5_loja03 = new EquipamentoECF();
		ecf5_loja03.setCodModDocFiscal("2D");
		ecf5_loja03.setModeloEquip("ECF");
		ecf5_loja03.setNumSerieFabECF("BE051175610000105333");
		ecf5_loja03.setNumECF("5");
		ecfsLoja03.add(ecf5_loja03);

		EquipamentoECF ecf6_loja03 = new EquipamentoECF();
		ecf6_loja03.setCodModDocFiscal("2D");
		ecf6_loja03.setModeloEquip("ECF");
		ecf6_loja03.setNumSerieFabECF("BE051175610000105336");
		ecf6_loja03.setNumECF("6");
		ecfsLoja03.add(ecf6_loja03);

		List<EquipamentoECF> ecfsMegafarma = new ArrayList<EquipamentoECF>();

		EquipamentoECF ecf1_loja06 = new EquipamentoECF();
		ecf1_loja06.setCodModDocFiscal("2D");
		ecf1_loja06.setModeloEquip("ECF");
		ecf1_loja06.setNumSerieFabECF("BE091010100011201429");
		ecf1_loja06.setNumECF("1");
		ecfsMegafarma.add(ecf1_loja06);

		EquipamentoECF ecf2_loja06 = new EquipamentoECF();
		ecf2_loja06.setCodModDocFiscal("2D");
		ecf2_loja06.setModeloEquip("ECF");
		ecf2_loja06.setNumSerieFabECF("DR0814BR000000407938");
		ecf2_loja06.setNumECF("2");
		ecfsMegafarma.add(ecf2_loja06);

		EquipamentoECF ecf3_loja06 = new EquipamentoECF();
		ecf3_loja06.setCodModDocFiscal("2D");
		ecf3_loja06.setModeloEquip("ECF");
		ecf3_loja06.setNumSerieFabECF("BE090910100010021907");
		ecf3_loja06.setNumECF("001");
		ecfsMegafarma.add(ecf3_loja06);

		EquipamentoECF ecf4_loja06 = new EquipamentoECF();
		ecf4_loja06.setCodModDocFiscal("2D");
		ecf4_loja06.setModeloEquip("ECF");
		ecf4_loja06.setNumSerieFabECF("BE05117561000010533");
		ecf4_loja06.setNumECF("002");
		ecfsMegafarma.add(ecf4_loja06);

		loja3.setEquipEcf(ecfsLoja03);
		mega.setEquipEcf(ecfsMegadiet);
		megafarma.setEquipEcf(ecfsMegafarma);

		empresa.adicionaEstab(matriz);
		empresa.adicionaEstab(mega);
		empresa.adicionaEstab(sao);
		empresa.adicionaEstab(harm);
		empresa.adicionaEstab(loja3);
		empresa.adicionaEstab(megafarma);

		dao.adiciona(empresa);
	}
}

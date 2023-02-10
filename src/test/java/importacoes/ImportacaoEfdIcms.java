package importacoes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.zeta.dao.ItemTotalizadoPorLoteDao;
import com.zeta.dao.LoteImportacaoSpedFiscalDao;
import com.zeta.dao.MetadadosDB;
import com.zeta.dao.OutrasUnidDao;
import com.zeta.dao.ParticipanteDao;
import com.zeta.dao.ProdutoDao;
import com.zeta.handler.CruzamentoNotasSpedsComXMLs;
import com.zeta.handler.ExportaHistoricoItem;
import com.zeta.handler.ExportaQuantitativoEstoque;
import com.zeta.handler.ExportaRelacaoInventario;
import com.zeta.handler.ExportaTotalizadorFinanceiroAnual;
import com.zeta.handler.ExportaQuantitativoEstoque;
import com.zeta.handler.ImportaEfdIcms;
import com.zeta.model.LoteImportacaoSpedFiscal;
import com.zeta.model.OutrasUnid;
import com.zeta.model.Participante;
import com.zeta.model.Produto;
import com.zeta.util.JPAUtil;

import modulos.efdicms.manager.LeitorEfdIcms;

public class ImportacaoEfdIcms {
	
	public static Produto buscarProdutoPorCodigo(List<Produto> collectProd, String codItem) {
		Map<String,Produto> mapProduto = new HashMap<String, Produto>();		
		for(Produto p :  collectProd){
			mapProduto.put(p.getCodUtilizEstab(), p);
		}
	    return mapProduto.get(codItem);	
	}
	
	public static String produtoJoinOutUnidadeMedida(List<Produto> collectProd, List<OutrasUnid> collectOutUnd,String codItem) {
				
		Map<String,Produto> mapProduto = new HashMap<String, Produto>();		
		for(Produto p :  collectProd){
			mapProduto.put(p.getCodUtilizEstab(), p);
		}		
		Map<String,OutrasUnid> mapOutUndMedida = new HashMap<String, OutrasUnid>();
		for(OutrasUnid p :  collectOutUnd){
			mapOutUndMedida.put(p.getCodProd(), p);
		}
		//System.out.println(mapProduto.get(codItem).getCodUtilizEstab() +"|"+(mapOutUndMedida.get(codItem) == null ? "NULL":mapOutUndMedida.get(codItem).getUndEquivPadrao()));
		codItem = mapProduto.get(codItem).getCodUtilizEstab() +"|"+(mapOutUndMedida.get(codItem) == null ? "NULL":mapOutUndMedida.get(codItem).getUndEquivPadrao()); 
		return codItem;
	}

	
	
	public static void main(String[] args) {
		
	   
		//MetadadosDB banco = new MetadadosDB();
		
		
		String ano   = "2021";
		String emp   = "SELLENE";
		String estab = "MEGAFARMA";
		String cnpj  = "05329222000680";
		
		String anomes1  = ano.concat("01").concat(".txt");
		String anomes2  = ano.concat("02").concat(".txt");
		String anomes3  = ano.concat("03").concat(".txt");
		String anomes4  = ano.concat("04").concat(".txt");
		String anomes5  = ano.concat("05").concat(".txt");
		String anomes6  = ano.concat("06").concat(".txt");
		String anomes7  = ano.concat("07").concat(".txt");
		String anomes8  = ano.concat("08").concat(".txt");
		String anomes9  = ano.concat("09").concat(".txt");
		String anomes10 = ano.concat("10").concat(".txt");
		String anomes11 = ano.concat("11").concat(".txt");
		String anomes12 = ano.concat("12").concat(".txt");
		
        Path xP1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\jan"));
        Path xT1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\jan"));
	    Path p1  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1));
		
	    Path xP2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\fev"));
	    Path p2  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes2));
		
	    Path xP3 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\mar"));
	    Path p3  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes3));
	    
	    Path xP4 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\abr"));
	    Path p4  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes4));
	    
	    Path xP5 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\mai"));
	    Path p5  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes5));
	    
	    Path xP6 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\jun"));
	    Path p6  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes6));
	    
	    Path xP7 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\jul"));
	    Path p7  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes7));
	    
	    Path xP8 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\ago"));
	    Path p8  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes8));
	    
	    Path xP9 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\set"));
	    Path p9  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes9));
	     
	    Path xP10 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\out"));
	    Path p10  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes10));
	    
	    Path xP11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\nov"));
	    Path p11  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes11));
	    
	    Path xP12 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Proprios").concat("\\").concat("\\dez"));
	    Path xT12 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\").concat("Terceiros").concat("\\").concat("\\dez"));
	    Path p12  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes12));
	    

	    //Verificar de criar a pasta de Proprios e Terceiros dentro da Pasta do XML
	    Path p = p12;
		Path x = xP12;
			
		LeitorEfdIcms leitor = new LeitorEfdIcms();
	
		
		Long id0000 = 0L;
		Long idC100 = 0L;
		Long idC405 = 0L;
		Long idC420 = 0L;
		Long idC860 = 0L;

		//id0000 = (banco.getIncremento("tb_importspedfiscal")==null ? 0 : banco.getIncremento("tb_importspedfiscal"));

		leitor.leitorSpedFiscal(p,0L,
				0L,0L,0L,
				0L, 0L );
		
		try {
			
			
			LoteImportacaoSpedFiscalDao loteDao = new LoteImportacaoSpedFiscalDao();
			ParticipanteDao daoPart = new ParticipanteDao();
			ProdutoDao daoProd = new ProdutoDao();
			OutrasUnidDao daoOut = new OutrasUnidDao();
			ImportaEfdIcms importa = new ImportaEfdIcms();	
			
				
			List<Participante> participantes = importa.getParticipantes(leitor,1L, 2L);
			
			List<Participante> collectPartBD = daoPart.listaTodos().stream()
					.collect(Collectors.toList());	
			
			List<Produto> collectProdBD = daoProd.listaTodos().stream()
					.collect(Collectors.toList());	
			
			LoteImportacaoSpedFiscal loteImportacao = importa.getLoteImportacao(leitor,collectProdBD, x.toString(), 1L, 2L);
			
			List<OutrasUnid> collectOutUnd = daoOut.listaTodos().stream()
			           .collect(Collectors.toList());	
			
			List<Produto> produtosSped = importa.getProdutosSped(leitor,1L, 2L);
			produtosSped.addAll(importa.getProdutos());
			List<Produto> collectProdutos = produtosSped.stream().distinct().collect(Collectors.toList());
			List<Participante> colecaoParticipantes = new ArrayList<>();
			List<Produto> colecaoProdutos = new ArrayList<>();
				if(!loteDao.listaTodos().contains(loteImportacao)){			
					for(Participante part : participantes){
						if(!collectPartBD.contains(part)) {
							colecaoParticipantes.add(part);
						}
					}
					
					daoPart.adicionaLote(colecaoParticipantes);
					
					for(Produto prod :  collectProdutos){					
						if(buscarProdutoPorCodigo(collectProdBD,prod.getCodUtilizEstab()) == null) {
							colecaoProdutos.add(prod);
							// daoProd.adiciona(prod);
							// System.out.println("Cadastrando produto -> " + prod.getCodUtilizEstab());
						}
						
						else if(importa.linha(prod).equals(produtoJoinOutUnidadeMedida(collectProdBD,collectOutUnd,prod.getCodUtilizEstab())) == false
								&&  produtoJoinOutUnidadeMedida(collectProdBD,collectOutUnd,prod.getCodUtilizEstab()).contains("NULL") == true){
							
							Produto buscaPorCodigo = daoProd.buscaPorCodigo(prod.getCodUtilizEstab());
					    	daoProd.remove(buscaPorCodigo);
					    	daoProd.atualiza(prod);
					    	System.out.println("Alterando o produto -> " + prod.getCodUtilizEstab());
						}
					}
					daoProd.adicionaLote(colecaoProdutos);
					loteDao.adiciona(loteImportacao);	
			}else {
				System.out.println("Lote já importado!!!");
			}
				
				
				
				
				
				

//			CruzamentoNotasSpedsComXMLs cruzamentos = new CruzamentoNotasSpedsComXMLs();
//			cruzamentos.cruzamentosNotasSpedFiscalComXMLProprios(x.toString(), leitor);
			
			//Verificar as emissões próprias no arquivei das entradas
			//cruzamentos.cruzamentosNotasSpedFiscalComXMLTerceiros(x.toString(), leitor);
			
			ExportaQuantitativoEstoque exp            = new ExportaQuantitativoEstoque();
			ExportaTotalizadorFinanceiroAnual expFin  = new ExportaTotalizadorFinanceiroAnual();
			ExportaHistoricoItem expHist              = new ExportaHistoricoItem();
			ExportaRelacaoInventario  expRelInv       = new ExportaRelacaoInventario();
			String dirPlanilha                        = "E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\CONTROLE_ESTOQUE_".concat(cnpj).concat("_").concat(ano).concat(".csv"));
			String dirPlanilhaFin                     = "E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\CONTROLE_FINANCEIRO_".concat(cnpj).concat("_").concat(ano).concat(".csv"));
			String dirPlanilhaFinDePara               = "E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\CONTROLE_FINANCEIRO_DE_PARA_".concat(cnpj).concat("_").concat(ano).concat(".csv"));
			String dirPlanHistItem                    = "E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\").concat("fichas_estoques").concat("\\");
			String dirListaProds                      = "E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\").concat("ListaCodItem2.csv");
			
			
			
			String dirSaldInvIni                      = "E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\").concat("INV_INI_2021.csv");
			
			String dirPlanInv                         = "E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\INVENTARIO_".concat(cnpj).concat("_").concat(ano).concat(".csv"));
			
		     //exp.exportaControleQuantitativos(dirPlanilha,cnpj,ano);
		     //expFin.exportaTotalizadorFinanceiroEstoque(dirPlanilhaFin,Integer.valueOf(ano), cnpj,1L, 1L);
			
			
			//Path csv1  = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\").concat("CONTROLE_FINANCEIRO_05329222000176_2015.csv"));
			//expFin.exportaTotalizadorFinanceiroEstoqueDaPlanilha(dirPlanilhaFinDePara, csv1);
			
	        //expHist.exportarHistoricoItem(dirPlanHistItem, ano, cnpj, "3236",1L,1L);
	        //expHist.exportarHistoricoItensComLista(dirListaProds, dirPlanHistItem,  ano, cnpj, 1L, 6L);
			
			
			//expRelInv.exportRelacaoInventario(dirSaldInvIni, dirPlanInv, cnpj, ano);
			  
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			JPAUtil.fecha();
		}


	}

}

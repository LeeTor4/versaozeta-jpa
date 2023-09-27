package com.zeta.handler;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import com.cnpj.dominio.AtividadesSecundarias;
import com.cnpj.dominio.Entidade;
import com.cnpj.servico.ServicoCnpj;
import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.leetor4.model.nfe.Produtos;
import com.zeta.dao.EquipamentoEcfDao;
import com.zeta.dao.ProdutoDao;
import com.zeta.model.Cfop;
import com.zeta.model.HistoricoItens;
import com.zeta.model.InventarioDeclarado;
import com.zeta.model.ItemTotalizadoPorLote;
import com.zeta.model.ItensInventario;
import com.zeta.model.LoteImportacaoSpedFiscal;
import com.zeta.model.OutrasUnid;
import com.zeta.model.Participante;
import com.zeta.model.Produto;
import com.zeta.util.UtilsEConverters;

import modulos.efdicms.entidades.Reg0000;
import modulos.efdicms.entidades.Reg0150;
import modulos.efdicms.entidades.Reg0200;
import modulos.efdicms.entidades.Reg0220;
import modulos.efdicms.entidades.RegC100;
import modulos.efdicms.entidades.RegC170;
import modulos.efdicms.entidades.RegC860;
import modulos.efdicms.entidades.RegH005;
import modulos.efdicms.entidades.RegH010;
import modulos.efdicms.manager.LeitorEfdIcms;

public class ImportaEfdIcms {

	
	private List<Produto> produtos = new ArrayList<Produto>();
	private Set<String> listaProdutos = new LinkedHashSet<String>();
	private List<ItemTotalizadoPorLote> itensTotalizadosSaidas = new ArrayList<ItemTotalizadoPorLote>();
	private List<ItemTotalizadoPorLote> itensTotalizadosEntradas = new ArrayList<ItemTotalizadoPorLote>();
	
	public LoteImportacaoSpedFiscal getLoteImportacao(LeitorEfdIcms leitor,List<Produto> collectProd, String file,Long idEmp, Long idEst) {
		LoteImportacaoSpedFiscal importacao = new LoteImportacaoSpedFiscal();

		for (Reg0000 lote : leitor.getRegs0000()) {
			
			importacao.setIdEmp(idEmp);
			importacao.setIdEst(idEst);
			importacao.setCodVersao(lote.getCodVer());
			importacao.setCodFinalidade(lote.getCodFin());
			importacao.setDtIni(lote.getDtIni());
			importacao.setDtFin(lote.getDtFin());
			importacao.setNome(lote.getNome());
			importacao.setCnpj(lote.getCnpj());
			importacao.setCpf(lote.getCpf());
			importacao.setUf(lote.getUf());
			importacao.setIe(lote.getIe());
			importacao.setCodMun(lote.getCodMun());
			importacao.setIM(lote.getIm());
			importacao.setSuframa(lote.getSuframa());
			importacao.setIndPerfil(lote.getIndPerfil());
			importacao.setIndAtiv(lote.getIndAtiv());
			
			
			importacao.setHistItens(getHistoricoItensGeral(leitor,collectProd, file, idEmp, idEst));
			
			importacao.setSaldoPorLote(totalizadoresGeral(
					
					
					itensTotalizadosPorLoteEntrada(String.valueOf(lote.getDtIni().getYear()), 
							String.valueOf(lote.getDtIni().getMonth().getValue()), lote.getCnpj()),
					
					
					
					itensTotalizadosPorLoteSaida(String.valueOf(lote.getDtIni().getYear()), 
							String.valueOf(lote.getDtIni().getMonth().getValue()), lote.getCnpj())));
			
			
			
			
			importacao.setInvDec(getInvDeclarado(leitor, idEmp, idEst));
		}

		
		return importacao;
	}
	
	
	public List<Participante> getParticipantes(LeitorEfdIcms leitor, Long idEmp, Long idEst){
		List<Participante> retorno = new ArrayList<Participante>();
		for(Reg0150 part : leitor.getRegs0150()){
			Participante participante = new Participante();
			
			participante.setIdPai(idEst);
			participante.setCodPart(part.getCodPart());
			participante.setNome(part.getNome());
			participante.setCnpj(part.getCnpj());
			participante.setCpf(part.getCpf());
			participante.setSuframa(part.getSuframa());
			participante.setEndereco(part.getEndereco());
			participante.setNum(part.getNum());
			participante.setBairro(part.getBairro());
			participante.setCompl(part.getCompl());
			participante.setCodMun(part.getCodMun());
			participante.setIe(part.getIe());
			participante.setCodPais(part.getCodPais());
			
		
			retorno.add(participante);
		}
		
		return retorno;
	}
	
	
	
	public List<Produto> getProdutosSped(LeitorEfdIcms leitor, Long idEmp, Long idEst) {
		
		List<Produto> retorno = new ArrayList<Produto>();
        for(Reg0200 prod :  leitor.getRegs0200()){
        	Produto p = new Produto();      	 
			p.setIdEmp(idEmp);
			p.setCodUtilizEstab(prod.getCodItem());
			p.setDescricao(prod.getDescrItem());
			p.setUnidadedeMedidaPadrao(prod.getUnidInv());
			p.setNcm(prod.getCodNcm());
			p.setCodigodeBarras(prod.getCodBarra());
			
			for(Reg0220 out : prod.getOutrasUndMedidas()){
				OutrasUnid outUnd = new OutrasUnid();
			    outUnd.setIdPaiEmp(idEmp);
			    outUnd.setIdPaiEst(idEst);
			    
			    //outUnd.setIdPai(idPaiReg0200(prod.getCodItem(),idEmp,idEst));
			    outUnd.setCodProd(prod.getCodItem());
			    outUnd.setUndMed(out.getUndConv());
			    outUnd.setUndEquivPadrao(out.getFatConv());
			    
			    p.adicionaOutrasUnd(outUnd);
			}
				retorno.add(p);	
        }
        return retorno;
	}
	
	private List<HistoricoItens> getHistoricoItensGeral(LeitorEfdIcms leitor,List<Produto> collectProd, String file, Long idEmp,
			Long idEst){
		
		List<HistoricoItens> historicoItensDocTerceiros = getHistoricoItensDocTerceiros(leitor, idEmp, idEst);
		List<HistoricoItens> historicoItensDocProprios = getHistoricoItensDocProprios(leitor,collectProd, file, idEmp, idEst);
		List<HistoricoItens> historicoItensECFs = getHistoricoItensECFs(leitor, idEmp, idEst);
		historicoItensDocProprios.addAll(historicoItensECFs);
		historicoItensDocTerceiros.addAll(historicoItensDocProprios);
		
		return historicoItensDocProprios;
	}
	private List<HistoricoItens> getHistoricoItensDocTerceiros(LeitorEfdIcms leitor,Long idEmp,
			Long idEst){
		List<HistoricoItens>  retorno = new ArrayList<HistoricoItens>();
		for (RegC100 nota : leitor.getRegsC100()) {
		
			for (RegC170 pNF : nota.getProdutosNota()) {

				if (insereNotasTerceiros(leitor, nota, pNF) != null) {
					if (insereNotasTerceiros(leitor, nota, pNF).getChaveDoc() != null) {
						if (insereNotasTerceiros(leitor, nota, pNF).getChaveDoc() != null) {
							retorno.add(insereNotasTerceiros(leitor, nota, pNF));

							// Rever esse metodo para extrair daqui
							if ((pNF.getCfop().startsWith("1")
									&& codigosCfopsQueMovimentamEstoque().contains(pNF.getCfop()))
									|| (pNF.getCfop().startsWith("2")
											&& codigosCfopsQueMovimentamEstoque().contains(pNF.getCfop()))) {
								itensTotalizadosEntradas.add(new ItemTotalizadoPorLote("E", pNF.getCodItem(),
										pNF.getQtd(), pNF.getVlItem()));
							}

						}
					}
				}

			}
		}
		return retorno;
	}
	private List<HistoricoItens> getHistoricoItensECFs(LeitorEfdIcms leitor, Long idEmp,
			Long idEst){
		List<HistoricoItens>  retorno = new ArrayList<HistoricoItens>();
		ExecutorService ex1 = null;
		ExecutorService ex2 = null;
		ExecutorService ex3 = null;
		try {
			ex1 = Executors.newCachedThreadPool();
			for (int i = 0; i < leitor.getRegsC400().size(); i++) {
				leituraEcf_ate_dia_10(ex1, leitor, idEmp, idEst,i,1,32, retorno);
			}
			ex1.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			if(ex1 != null) {
				ex1.shutdown();
			}
		}
		
		return retorno;
	}
	private List<HistoricoItens> getHistoricoItensDocProprios(LeitorEfdIcms leitor,List<Produto> collectProd,String file, Long idEmp,
			Long idEst){
		List<HistoricoItens>  retorno = new ArrayList<HistoricoItens>();
		ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
		ExecutorService ex1 = null;
		ExecutorService ex2 = null;
		ExecutorService ex3 = null;
		try {
			ex1 = Executors.newCachedThreadPool();
			ex2 = Executors.newCachedThreadPool();
			ex3 = Executors.newCachedThreadPool();
			for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {  
				leituraXmlProprios(doc, leitor,collectProd, parseDocXML, f, ex1,1,11,idEmp ,idEst , retorno);
				leituraXmlProprios(doc, leitor,collectProd,parseDocXML, f, ex2,11,21, idEmp ,idEst ,retorno);
				leituraXmlProprios(doc, leitor,collectProd, parseDocXML, f, ex3,21,32,idEmp ,idEst ,  retorno);
			}
			
			ex1.awaitTermination(5, TimeUnit.SECONDS);
			ex2.awaitTermination(5, TimeUnit.SECONDS);
			ex3.awaitTermination(5, TimeUnit.SECONDS);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			if(ex1 != null) {
				ex1.shutdown();
			}
			if(ex2 != null) {
				ex2.shutdown();
			}
			if(ex3 != null) {
				ex3.shutdown();
			}
		}
		return retorno;
	}
	
	private void leituraEcf_ate_dia_10(ExecutorService ex, LeitorEfdIcms leitor,Long idEmp,
			Long idEst,int i, int pDia,int uDia,List<HistoricoItens>  retorno) {
		
		for (int z = 0; z < leitor.getRegsC400().get(i).getRegsC405().size(); z++) {
			
			if(leitor.getRegsC400().get(i).getRegsC405().get(z).getDtDoc().getDayOfMonth() >= pDia
					&& leitor.getRegsC400().get(i).getRegsC405().get(z).getDtDoc().getDayOfMonth() < uDia) {
				for (int l = 0; l < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().size(); l++) {
					for (int m = 0; m < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l)
							.getRegsC425().size(); m++) {
                        
						if (insereReducoes(leitor,i, z, l, m,idEst) != null) {
							
							CallableHistItensECFs hist = new CallableHistItensECFs(leitor, i, z, l, m,idEst,pDia,uDia);
							Future<HistoricoItens> submit = ex.submit(hist);
							try {
								
								itensTotalizadosSaidas.add(new ItemTotalizadoPorLote("S", 
										submit.get().getCodItem(),
										submit.get().getQtde().doubleValue(), 
										submit.get().getVlLiq().doubleValue()));
								
	
								retorno.add(submit.get());
							    //System.out.println(submit.get().getDtDoc().getDayOfMonth() + "|" + leitor.getRegsC400().get(i).getRegsC405().get(z).getPosicaoRDZ());

							} catch (InterruptedException e) {
								
								e.printStackTrace();
							} catch (ExecutionException e) {
								
								e.printStackTrace();
							}
						}
						
						
						   
					}
				}
			}

		}
		
	}
	
	private void leituraXmlProprios(DocumentoFiscalEltronico doc,LeitorEfdIcms leitor,List<Produto> collectProd, ParseDocXML parseDocXML, File f,
			ExecutorService ex, int pDia,int uDia, Long idEmp,Long idEst,List<HistoricoItens> retorno) {
		if (doc.getIdent().getModeloDoc().equals("59")) {
			
			for (RegC860 regC860 : leitor.getRegsC860()) {
				
				if (regC860.getDtEmissao().getDayOfMonth() >= pDia && regC860.getDtEmissao().getDayOfMonth() < uDia) {
					
					if (regC860.getId() == idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor)) {
						
						for (Produtos p : doc.getProds()) {
							
							if (insereHistCFes(leitor, regC860, f, p, doc).getDtDoc().getDayOfMonth() >=  pDia
									&& insereHistCFes(leitor, regC860, f, p, doc).getDtDoc().getDayOfMonth() < uDia) {
								CallableHistItensCFes hist = new CallableHistItensCFes(leitor, regC860, f, p, doc,pDia,uDia);
								Future<HistoricoItens> submit = ex.submit(hist);
								try {
									retorno.add(submit.get());
									System.out.println(submit.get().getDtDoc().getDayOfMonth() + "|"
											+ regC860.getNumSerieEquipSat());

								} catch (InterruptedException e) {

									e.printStackTrace();
								} catch (ExecutionException e) {

									e.printStackTrace();
								}
							}
							
							if(!collectProd.contains(insereProdutosProprios(p, idEmp, idEst))){
								produtos.add(insereProdutosProprios(p, idEmp, idEst));
							}
							//Observar se é o valor do Produto ou do Item
							itensTotalizadosSaidas.add(new ItemTotalizadoPorLote("S",p.getCodItem(),Double.valueOf(p.getQtdComercial()), 
									Double.valueOf(p.getVlItem())));
						}
					}
				}
			}
			
		}else if (doc.getIdent().getModeloDoc().equals("55")
				&& UtilsEConverters.getStringParaData(doc.getIdent().getDataEmissao()).getDayOfMonth() >= pDia
				&& UtilsEConverters.getStringParaData(doc.getIdent().getDataEmissao()).getDayOfMonth() < uDia) {
			Double desc;
			Double vlItem = 0.0;
			
			for (Produtos p : doc.getProds()) {
				
				
				if (insereNotasProprias(leitor, p, doc).getChaveDoc() != null) {
					retorno.add(insereNotasProprias(leitor, p, doc));
				}

				if(!collectProd.contains(insereProdutosProprios(p, idEmp, idEst))){
					produtos.add(insereProdutosProprios(p, idEmp, idEst));
				}	
				
				//Rever esse trecho e extrair o metodo

				if(p.getvDesc() != null) {
					desc = Double.valueOf(p.getvDesc());
				}else {
					desc = 0.0;
				}
				if (p.getVlItem() != null || p.getVlProduto() != null) {
					vlItem = Double.valueOf(p.getVlProduto()) - desc;
							
				}
				
				if(insereNotasProprias(leitor, p, doc).getCodSitDoc() != null) {
					//Somente notas com a situação de documento regular
					if(insereNotasProprias(leitor, p, doc).getCodSitDoc().equals("00")) {
						
						//itensTotalizados.add(totalizador);
						if((p.getCfop().startsWith("5") && codigosCfopsQueMovimentamEstoque().contains(p.getCfop())) 
						|| (p.getCfop().startsWith("6") && codigosCfopsQueMovimentamEstoque().contains(p.getCfop()))){
												
								itensTotalizadosSaidas.add(new ItemTotalizadoPorLote("S",p.getCodItem(),Double.valueOf(p.getQtdComercial()), 
										vlItem));
						
						}else if((p.getCfop().startsWith("1") && codigosCfopsQueMovimentamEstoque().contains(p.getCfop())) 
							  || (p.getCfop().startsWith("2") && codigosCfopsQueMovimentamEstoque().contains(p.getCfop()))){
								itensTotalizadosEntradas.add(new ItemTotalizadoPorLote("E",p.getCodItem(),Double.valueOf(p.getQtdComercial()), 
										vlItem));
						}
					}
					
				}


				
			}

		}
		
		
	}
	
	public Long idPaiEquipCFe(String numDOc, LeitorEfdIcms leitor) {
		Long id = 0L;
		int num = Integer.valueOf(numDOc);
		for (Long key : leitor.getMpC860().keySet()) {

			if (num >= Integer.valueOf(leitor.getMpC860().get(key).getDocInicial())
					&& num <= Integer.valueOf(leitor.getMpC860().get(key).getDocFinal())) {
					id = leitor.getMpC860().get(key).getId();
			}
		}
		return id;
	}
	
	public  HistoricoItens insereReducoes(LeitorEfdIcms leitor, int i, int z, int l, int m, Long idEst) {
		EquipamentoEcfDao dao = new EquipamentoEcfDao();
		HistoricoItens retorno = new HistoricoItens();

		retorno.setCodMod(leitor.getRegsC400().get(i).getCodModelo());
		retorno.setEmpresa(leitor.getRegs0000().get(0).getCnpj());
		retorno.setOperacao("S");
		retorno.setCodSitDoc("");
		retorno.setNumItem("");
		retorno.setEcfCx(dao.buscaPorNumFab(leitor.getRegsC400().get(i).getNumSerieFabECF(),idEst).getNumECF());
		retorno.setDtDoc(leitor.getRegsC400().get(i).getRegsC405().get(z).getDtDoc());
		retorno.setCodItem(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem());
		retorno.setQtde(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getQtd()));
		retorno.setUnd(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getUnd());
		retorno.setVlUnit(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlItem()/leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getQtd()));
		retorno.setVlLiq(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlItem()));
		retorno.setVlBruto(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlItem()));
		retorno.setCfop(leitor.getMpC490().get(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getIdPaiRedZ()).getCfop());
		retorno.setCst(leitor.getMpC490().get(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getIdPaiRedZ()).getCstIcms());
		if(leitor.getMpProdTerc().get(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem()) != null) {
			retorno.setDescricao(leitor.getMpProdTerc().get(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem()).getDescrItem());
		}
		retorno.setNumDoc(leitor.getRegsC400().get(i).getRegsC405().get(z).getNumCOOFin());
		retorno.setAliqIcms(BigDecimal.valueOf(0.0));
		retorno.setDesconto(BigDecimal.valueOf(0.0));
		retorno.setChaveDoc("");
		retorno.setNome("");
		retorno.setCpfCnpj("");
										
		return retorno;
	}
	
   public HistoricoItens insereHistCFes(LeitorEfdIcms leitor,RegC860 regC860,File file,Produtos p,DocumentoFiscalEltronico doc) {
		
		HistoricoItens retorno = new HistoricoItens();			
		retorno.setEmpresa(leitor.getRegs0000().get(0).getCnpj());
		retorno.setOperacao("S");
		retorno.setEcfCx(regC860.getNumSerieEquipSat());
		retorno.setDtDoc(regC860.getDtEmissao());
		retorno.setCodItem(p.getCodItem());
		retorno.setQtde(BigDecimal.valueOf(Double.valueOf(p.getQtdComercial())));
		retorno.setUnd(p.getVlUnComerial());		
		retorno.setVlUnit(BigDecimal.valueOf(Double.valueOf(p.getVlUnComerial())));		
		retorno.setVlBruto(BigDecimal.valueOf(Double.valueOf(p.getVlProduto())));		
		if(p.getvDesc() != null) {
			retorno.setDesconto(BigDecimal.valueOf(Double.valueOf(p.getvDesc())));
		}else {
			retorno.setDesconto(BigDecimal.valueOf(0.0));
		}		
		if(p.getVlItem() != null) {
			retorno.setVlLiq(BigDecimal.valueOf(Double.valueOf(p.getVlItem())));	
		}		
		retorno.setCfop(p.getCfop());
		retorno.setCst(p.getOrig().concat(p.getCst()));
		retorno.setCodSitDoc("");
		if(p.getAliqIcms() != null) {
			retorno.setAliqIcms(BigDecimal.valueOf(Double.valueOf(p.getAliqIcms())));
		}else {
			
			retorno.setAliqIcms(BigDecimal.valueOf(0.0));
		}		
		retorno.setCodMod(regC860.getCodModDocFiscal());
		retorno.setDescricao(p.getDescricao());
		retorno.setNumDoc(doc.getIdent().getNumDoc());
		retorno.setNumItem(p.getNumItem());
		retorno.setChaveDoc(doc.getIdent().getChaveeletronica());
		retorno.setNome(doc.getDestinatario().getNome());		
		String cnpjCpf = "";
		if(doc.getDestinatario().getCnpj() != null) {
			cnpjCpf += doc.getDestinatario().getCnpj();
		}		
		if(doc.getDestinatario().getCpf() != null) {
			cnpjCpf += doc.getDestinatario().getCnpj();
		}		
		if(cnpjCpf != null) {
			retorno.setCpfCnpj(cnpjCpf);
		}		
		return retorno;
	}
   
	private HistoricoItens insereNotasTerceiros(LeitorEfdIcms leitor,RegC100 nota,RegC170 pNF) {
		HistoricoItens retorno = new HistoricoItens();
        if(!nota.getCodSit().equals("06")) {
        	
        	retorno.setEmpresa(leitor.getRegs0000().get(0).getCnpj());

    		if (nota.getIndOper().equals("0")) {
    			retorno.setOperacao("E");
    		} else {
    			retorno.setOperacao("S");
    		}
    		retorno.setEcfCx("");
    		retorno.setDtDoc(nota.getDtDoc());
    		retorno.setNumItem(pNF.getNumItem());
    		retorno.setCodItem(pNF.getCodItem());
    		retorno.setQtde(BigDecimal.valueOf(pNF.getQtd()));
    		retorno.setUnd(pNF.getUnid());
    		
    		
    		retorno.setVlUnit(BigDecimal.valueOf(pNF.getVlItem()/pNF.getQtd()));
    		retorno.setVlBruto(BigDecimal.valueOf(pNF.getVlItem()));
    		
    		//Verificar esse set do Desconto
    		retorno.setDesconto(BigDecimal.valueOf(0.0));
    		
    		retorno.setVlLiq(BigDecimal.valueOf(pNF.getVlItem()));
    		
    		retorno.setCfop(pNF.getCfop());
    		retorno.setCst(pNF.getCstIcms());
    		
    		if((pNF.getAliqIcms() != null)) {
    			retorno.setAliqIcms(BigDecimal.valueOf(pNF.getAliqIcms()));
    		}else {
    			retorno.setAliqIcms(BigDecimal.valueOf(0.0));
    		}
    		
    		retorno.setCodSitDoc(nota.getCodSit());
    		retorno.setCodMod(nota.getCodMod());
    		
    		if(leitor.getMpProdTerc().get(pNF.getCodItem()) != null) {
    			retorno.setDescricao(leitor.getMpProdTerc().get(pNF.getCodItem()).getDescrItem());
    		}
    		
    		
    		retorno.setNumDoc(nota.getNumDoc());
    		retorno.setChaveDoc(nota.getChvNfe());
    		
    		if(leitor.getMpParticipante().get(nota.getCodPart()) != null) {
    			retorno.setNome(leitor.getMpParticipante().get(nota.getCodPart()).getNome());
    		}
    		
    		String doc = "";
    		
            if(leitor.getMpParticipante().get(nota.getCodPart()) != null) {
            	doc += leitor.getMpParticipante().get(nota.getCodPart()).getCnpj();            	
    		}
    		
            if(leitor.getMpParticipante().get(nota.getCodPart()) != null) {
            	doc += leitor.getMpParticipante().get(nota.getCodPart()).getCpf();           	
    		}
            
            if(doc != null) {
            	retorno.setCpfCnpj(doc);
            }
        }
		

	 return retorno;	

   }
	
	public HistoricoItens insereNotasProprias(LeitorEfdIcms leitor,Produtos p, DocumentoFiscalEltronico doc) {
		 HistoricoItens retorno = new HistoricoItens();
		  
	 if(leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()) != null) {
		 
		 if (!leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getCodSit().equals("02")) {
				
			 if (doc.getIdent().getModeloDoc().equals("55")) {
				 
			 if(leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()) != null){

						if (leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()) != null) {
							retorno.setCodSitDoc(
									leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getCodSit());							
						}
						retorno.setEmpresa(leitor.getRegs0000().get(0).getCnpj());
						if (p.getCfop().startsWith("1") || p.getCfop().startsWith("2")) {
							retorno.setOperacao("E");
						} else {
							retorno.setOperacao("S");
						}
						retorno.setEcfCx("");
						retorno.setDtDoc(UtilsEConverters.getStringParaData(doc.getIdent().getDataEmissao()));
						retorno.setNumItem(p.getNumItem());
						retorno.setCodItem(p.getCodItem());
						retorno.setQtde(BigDecimal.valueOf(Double.valueOf(p.getQtdComercial())));
						retorno.setUnd(p.getUndComercial());
						retorno.setVlUnit(BigDecimal.valueOf(Double.valueOf(p.getVlUnComerial())));
						retorno.setVlBruto(BigDecimal.valueOf(Double.valueOf(p.getVlProduto())));
						
						if(p.getvDesc() != null) {
							retorno.setDesconto(BigDecimal.valueOf(Double.valueOf(p.getvDesc())));
						}else {
							retorno.setDesconto(BigDecimal.ZERO);
						}
						
						
						
						if (p.getVlItem() != null || p.getVlProduto() != null) {
							retorno.setVlLiq(BigDecimal.valueOf(Double.valueOf(p.getVlProduto()))
									.subtract(retorno.getDesconto()));
						}
						
						
						
						
						retorno.setCfop(p.getCfop());
						if (p.getOrig() != null && p.getCst() != null) {
							retorno.setCst(p.getOrig().concat(p.getCst()));
						}
						if ((p.getAliqIcms() != null)) {
							retorno.setAliqIcms(BigDecimal.valueOf(Double.valueOf(p.getAliqIcms())));
						} else {
							retorno.setAliqIcms(BigDecimal.valueOf(0.0));
						}
						retorno.setCodMod(doc.getIdent().getModeloDoc());
						retorno.setDescricao(p.getDescricao());
						retorno.setNumDoc(doc.getIdent().getNumDoc());
						retorno.setChaveDoc(doc.getIdent().getChaveeletronica());
						retorno.setNome(doc.getDestinatario().getNome());
						String cpfCnpj = "";
						if(doc.getDestinatario().getCnpj() != null) {
							 cpfCnpj += doc.getDestinatario().getCnpj();
						}
						if(doc.getDestinatario().getCpf() != null) {
							 cpfCnpj += doc.getDestinatario().getCpf();
						}
						if(cpfCnpj != null) {
							retorno.setCpfCnpj(cpfCnpj);
						}
						

					}
				 }
			 }
	 }
		return retorno;
	}
	
	 public Produto insereProdutosProprios(com.leetor4.model.nfe.Produtos prod , Long idEmp, Long idEst) {
	    	Produto p = new Produto();
	 
			p.setIdEmp(idEmp);
			p.setCodUtilizEstab(prod.getCodItem());
			p.setDescricao(prod.getDescricao());
			p.setUnidadedeMedidaPadrao(prod.getUndComercial());
			p.setNcm(prod.getNcm());
			p.setCodigodeBarras(prod.getCodEanTrib());

			listaProdutos.add(prod.getCodItem());
							
	    	return p;
	 }
	 
	 
		public Map<String, List<ItemTotalizadoPorLote>> mapaProdutosTotalizadosSaidas(){
			Map<String, List<ItemTotalizadoPorLote>> prodTotalizadosQtde = new HashMap<String, List<ItemTotalizadoPorLote>>();
	    	
	    	for(ItemTotalizadoPorLote totalizadorProd : itensTotalizadosSaidas){
	    		String codigo = totalizadorProd.getCodItem();
	    		List<ItemTotalizadoPorLote> prodEncontrado = prodTotalizadosQtde.get(codigo);
	    		if(prodEncontrado == null) { 
	    			prodEncontrado = new ArrayList<ItemTotalizadoPorLote>();
	    			prodEncontrado.add(totalizadorProd);
	    			prodTotalizadosQtde.put(codigo, prodEncontrado);
	    			continue;
	    		}
	    		prodEncontrado.add(totalizadorProd);
	    	}
	    	
	    	return prodTotalizadosQtde;
		}
		
		public Map<String, List<ItemTotalizadoPorLote>> mapaProdutosTotalizadosEntradas(){
			Map<String, List<ItemTotalizadoPorLote>> prodTotalizadosQtde = new HashMap<String, List<ItemTotalizadoPorLote>>();
	    	
	    	for(ItemTotalizadoPorLote totalizadorProd : itensTotalizadosEntradas){
	    		String codigo = totalizadorProd.getCodItem();
	    		List<ItemTotalizadoPorLote> prodEncontrado = prodTotalizadosQtde.get(codigo);
	    		if(prodEncontrado == null) { 
	    			prodEncontrado = new ArrayList<ItemTotalizadoPorLote>();
	    			prodEncontrado.add(totalizadorProd);
	    			prodTotalizadosQtde.put(codigo, prodEncontrado);
	    			continue;
	    		}
	    		prodEncontrado.add(totalizadorProd);
	    	}
	    	
	    	return prodTotalizadosQtde;
		}
		
		public List<ItemTotalizadoPorLote> itensTotalizadosPorLoteEntrada(String ano, String mes, String cnpj){
			List<ItemTotalizadoPorLote> retorno = new ArrayList<ItemTotalizadoPorLote>();
			for(String key :  mapaProdutosTotalizadosEntradas().keySet()){ 
				ItemTotalizadoPorLote obj = new ItemTotalizadoPorLote();
				obj.setOperacao(totais(key, mapaProdutosTotalizadosEntradas()).getOperacao());
				obj.setAno(ano);
				obj.setCnpj(cnpj);			
				obj.setMes(mes);
				obj.setFrequencia(totais(key, mapaProdutosTotalizadosEntradas()).getFrequencia());
				obj.setCodItem(totais(key, mapaProdutosTotalizadosEntradas()).getCodItem());
				obj.setVlTotQtde(totais(key, mapaProdutosTotalizadosEntradas()).getVlTotQtde());
				obj.setVlTotItem(totais(key, mapaProdutosTotalizadosEntradas()).getVlTotItem());
				
				retorno.add(obj);
			}
			return retorno;
		}
		public List<ItemTotalizadoPorLote> itensTotalizadosPorLoteSaida(String ano, String mes, String cnpj){
			List<ItemTotalizadoPorLote> retorno = new ArrayList<ItemTotalizadoPorLote>();
			for(String key :  mapaProdutosTotalizadosSaidas().keySet()){ 
				ItemTotalizadoPorLote obj = new ItemTotalizadoPorLote();
								
				obj.setOperacao(totais(key, mapaProdutosTotalizadosSaidas()).getOperacao());
				obj.setAno(ano);
				obj.setCnpj(cnpj);			
				obj.setMes(mes);
				obj.setFrequencia(totais(key, mapaProdutosTotalizadosSaidas()).getFrequencia());
				obj.setCodItem(totais(key, mapaProdutosTotalizadosSaidas()).getCodItem());
				obj.setVlTotQtde(totais(key, mapaProdutosTotalizadosSaidas()).getVlTotQtde());
				obj.setVlTotItem(totais(key, mapaProdutosTotalizadosSaidas()).getVlTotItem());
				
				retorno.add(obj);
			}
			return retorno;
		}
		
		public ItemTotalizadoPorLote totais(String key,Map<String, List<ItemTotalizadoPorLote>> prodTotalizadosQtde) {
			Double qtde = 0.0;
			Double vl = 0.0;
			int cont = 0;
			ItemTotalizadoPorLote retorno = new ItemTotalizadoPorLote();
			for(ItemTotalizadoPorLote z : prodTotalizadosQtde.get(key)){
				cont++;
				qtde += z.getVlTotQtde();
				vl   += z.getVlTotItem();
				retorno.setOperacao(z.getOperacao());
			}
			
			retorno.setFrequencia(cont);
			retorno.setCodItem(key);
			retorno.setVlTotQtde(qtde);
			retorno.setVlTotItem(vl);
			return retorno;
		}
		 
		public List<ItemTotalizadoPorLote> totalizadoresGeral(List<ItemTotalizadoPorLote> itensTotalizadosPorLoteEntrada,List<ItemTotalizadoPorLote> itensTotalizadosPorLoteSaida){
			List<ItemTotalizadoPorLote> retorno = new ArrayList<ItemTotalizadoPorLote>();			
			retorno.addAll(itensTotalizadosPorLoteEntrada);
			retorno.addAll(itensTotalizadosPorLoteSaida);
			return retorno;
		}
		
		public List<Produto> getProdutos() {
			List<Produto> novoRetorno = produtos.stream().distinct().collect(Collectors.toList());
			return novoRetorno;
		}
		
		public String linha(Produto prod) {
			String lin = prod.getCodUtilizEstab();
			for(OutrasUnid out : prod.getOutrasUnds()){
			
				    lin += "|";
					lin += out.getUndEquivPadrao();
			}
			
			return lin;
		}
		
		public List<InventarioDeclarado> getInvDeclarado(LeitorEfdIcms leitor, Long idEmp, Long idEst){
			List<InventarioDeclarado> retorno = new ArrayList<InventarioDeclarado>();
			for(RegH005 inv : leitor.getRegsH005()){
				InventarioDeclarado invDec = new InventarioDeclarado();
				invDec.setIdEmp(idEmp);
				invDec.setIdEst(idEst);
				invDec.setMotivoInventario(inv.getMotivoInventario());
				invDec.setDataInv(inv.getDataInv());
				invDec.setVlTotal(inv.getVlTotEstoque());
				for(RegH010 itens :  inv.getRegsH010()){
					ItensInventario itensInv = new ItensInventario();
					itensInv.setCodItem(itens.getCodItem());
					itensInv.setQtde(itens.getQtde());
					itensInv.setVlUnit(itens.getVlUnit());
					itensInv.setVlItem(itens.getVlItem());
					itensInv.setCodCta(itens.getCodCtda());
					itensInv.setUnd(itens.getUnd());
					itensInv.setCodPart(itens.getCodPart());
					itensInv.setTxtCompl(itens.getTxtCompl());
					
					invDec.adicionaItemInv(itensInv);
				}
				
				retorno.add(invDec);
			}
			
			return retorno;
		}

		private List<String> codigosCfopsQueMovimentamEstoque(){
			String caminhoCfop = "src\\main\\resources\\utils\\rel_cfop_v1.csv";	
			List<String> retorno = new ArrayList<String>();
			List<Cfop> lerRelacaoCfopQueMovimentaEstoque = UtilsEConverters.lerRelacaoCfop(caminhoCfop).stream()
					   .filter(c -> c.getMovimentaEstoque().equals("S"))
					   .collect(Collectors.toList());
			for(Cfop cfop : lerRelacaoCfopQueMovimentaEstoque){
				retorno.add(cfop.getCodigo());
			}
			return retorno;
		}
}

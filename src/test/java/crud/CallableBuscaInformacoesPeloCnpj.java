package crud;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.cnpj.dominio.Entidade;
import com.cnpj.servico.ServicoCnpj;

public class CallableBuscaInformacoesPeloCnpj implements Callable<List<Entidade>>{
	
	private List<String> listaCnpjs; 

	public CallableBuscaInformacoesPeloCnpj(List<String> listaCnpjs) {
		super();
		this.listaCnpjs = listaCnpjs;
	}

	@Override
	public List<Entidade> call() throws Exception {
		List<Entidade> retorno = new ArrayList<Entidade>();
		
        for(int i = 0; i < this.listaCnpjs.size(); i++) {           	
            try {
                System.out.println(this.listaCnpjs.get(i) + " - Aguarde processando!!!");
            	Entidade buscaInformacoesPeloCnpj = ServicoCnpj.buscaInformacoesPeloCnpj(this.listaCnpjs.get(i));
//    	        System.out.println(buscaInformacoesPeloCnpj.getCnpj() + " - "+ buscaInformacoesPeloCnpj.getNome()+ "\n\n" +
//    	        		           "Atividade Principal : " + "\n" +
//    	        		           buscaInformacoesPeloCnpj.getCodigoAtivPrincipal() +" - "+ buscaInformacoesPeloCnpj.getAtividadePrincipal() + "\n" +
//    	        		           "Natureza Jurídica : "+ buscaInformacoesPeloCnpj.getNaturezaJuridica()+ "\n");
    	        
    	        retorno.add(buscaInformacoesPeloCnpj);
                //assuming it takes 20 secs to complete the task
                Thread.sleep(21000);                   
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
           
        }
	
		return retorno;
	}

}

package Json;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.zeta.handler.ExportaPlanilhaNcmAtualizada;



public class Main {

	public static void main(String[] args) throws IOException {
		
//		 // {"Codigo":"01","Descricao":"Animais vivos.","Data_Inicio":"01/04/2022","Data_Fim":"31/12/9999","Tipo_Ato":"Res Camex","Numero_Ato":"272","Ano_Ato":"2021"}
//		 Gson gson = new Gson();
//		 JsonReader reader = new JsonReader(new FileReader("C:\\Users\\chico\\Downloads\\Tabela_NCM_Vigente_20231130.json"));
//		 JsonElement element = gson.fromJson(reader, JsonElement.class);
//		 JsonObject jsonObj = element.getAsJsonObject();
//		 System.out.println(jsonObj.get("Ato").getAsString());
//		 JsonArray asJsonArrayNomenclaturas = jsonObj.get("Nomenclaturas").getAsJsonArray();
//		 for(int i=0; i < asJsonArrayNomenclaturas.size(); i++) {
//			
//			 String descricao = asJsonArrayNomenclaturas.get(i).getAsJsonObject().get("Descricao").getAsString();
//			 byte[] descricaoBytes = descricao.getBytes();
//			 String descricaoUTF8EncodedString = new String(descricaoBytes, StandardCharsets.UTF_8);
//			
//			 
//			 System.out.println(
//						 asJsonArrayNomenclaturas.get(i).getAsJsonObject().get("Codigo").getAsString() + " = " 
//				           +
//						 descricaoUTF8EncodedString + " = " 
//						   +
//						 asJsonArrayNomenclaturas.get(i).getAsJsonObject().get("Data_Inicio").getAsString() + " = " 
//			          );
//		 }
		
		
		String dirPlanilha   = "E:\\".concat("Planilha_NCM_Atualizada".concat(".csv"));
		
		ExportaPlanilhaNcmAtualizada.exportaPlanilhaNcm(dirPlanilha, ExportaPlanilhaNcmAtualizada.listaNcm("C:\\Users\\chico\\Downloads\\Tabela_NCM_Vigente_20231130.json"));
		
	}

}

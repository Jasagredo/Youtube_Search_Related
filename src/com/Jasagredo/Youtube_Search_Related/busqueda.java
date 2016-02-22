package com.Jasagredo.Youtube_Search_Related;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class busqueda {
	/* Declaración de las constantes, reemplazar el contenido de API_KEY por tu api key
	 * Los valores son los que necesito para mi búsqueda. Para obtener otros 
	 * resultados se pueden modificar estas constantes.
	 */
	private static final String API_URL = "https://www.googleapis.com/";
	private static final String API_KEY = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	private static final String PART = "id,snippet";
	private static final int MAX_RESULTS = 10;
	private static final String FIELDS ="items(id/videoId,snippet/title)";
	private static final String TYPE = "video";
	
	private static Random rnd = new Random();
    private static int index = 0;
	
	//Interfaz para la llamada de retrofit
	  public interface Youtube {
	    @GET("youtube/v3/search")
	    Call<Respuesta> videos(
	        @Query("part") String parte,
	        @Query("maxResults") int maxx,
	        @Query("relatedToVideoId") String IDaBuscar,
	        @Query("fields") String campos,
	        @Query("type") String tipo,
	        @Query("key") String apikey
	        
	        );
	  }

	  public static void main(String[] args) throws IOException {
		
		//Instanciación de Retrofit
	    Retrofit retrofit = new Retrofit.Builder()
	        .baseUrl(API_URL)
	        .addConverterFactory(GsonConverterFactory.create())
	        .build();

	    // Instancia de la llamada
	    Youtube youtube = retrofit.create(Youtube.class);
	    
	    // Solicitud del primer ID sobre el que empezar la búsqueda
	    String IdInicial = pedirID();

	    // Declaración de la llamada
	    Call<Respuesta> call = llamada(IdInicial, youtube);
	    		

	    // Obtención de resultados
	    Respuesta resultado = call.execute().body();
	    
	    for (int i = 0; i < 100; i++) {
	    	
	    	//Aleatoriamente elegimos uno de los resultados obtenidos para iterar la búsqueda
	    	index = rnd.nextInt(10);
	    	
	    	//Mostrar el resultado escogido
	    	printing(resultado.getItems().get(index), i);
	    	
	    	//Obtención del nuevo ID sobre el que iterar
	    	String nID = resultado.getItems().get(index).getId().getVideoId();
	    	
	    	//Declaración de la nueva llamada y ejecución
	    	call = llamada (nID, youtube);
	    	resultado = call.execute().body();
	    }
	  }

	private static void printing(Item item, int i) {
		System.out.println("Iteración " + i);
    	System.out.println("\t ID: " + item.getId().getVideoId());
    	System.out.println("\t Titulo:" + item.getSnippet().getTitle());
    	System.out.println("");
		
	}

	private static Call<Respuesta> llamada(String idABuscar, Youtube youtube) {
		return youtube.videos(PART, MAX_RESULTS, idABuscar, FIELDS , TYPE, API_KEY);
	}

	private static String pedirID() {
		String inputQuery = "";

	    System.out.print("Dame el ID del primer video: ");
	    BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
	    try {
			inputQuery = bReader.readLine();
		} catch (IOException e) {
			System.out.println("Ha habido un error en la entrada del ID.");
			e.printStackTrace();
		}

	    if (inputQuery.length() < 1) {
	      inputQuery = "wVybZeky3Rk";
	    }
	    return inputQuery;
	}

}

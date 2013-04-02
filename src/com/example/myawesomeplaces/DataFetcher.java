package com.example.myawesomeplaces;

public class DataFetcher {
	
	private static DataFetcher df;
	private String apiKey = "AIzaSyDE18NVNXy8LQApHw9j50OYaF3wtKbTM1k";
	
	public static DataFetcher getDataFetcher() {
		if (df == null) {
			df = new DataFetcher();
		}
		return df;
		
		

	}
	
	
}

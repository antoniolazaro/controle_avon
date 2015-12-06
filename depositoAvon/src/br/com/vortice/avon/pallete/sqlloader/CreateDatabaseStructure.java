package br.com.vortice.avon.pallete.sqlloader;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;

import org.h2.tools.RunScript;

import br.com.vortice.avon.pallete.dao.DAOAb;

public class CreateDatabaseStructure {
	
	public static final String DATABASE_PATH = "~/pallete";
	
	public static void initDatabase() throws Exception{
		Connection connection = new DAOAb().getDBConnection();
		File database = new File(DATABASE_PATH+".h2.db"); 
		if(!database.exists()){
			executeScript(connection,"database/create-database.sql");
			executeScript(connection,"database/carga-produto.sql");
			executeScript(connection,"database/carga-pallete.sql");
			connection.close();
		}
	}
	
	private static void executeScript(Connection connection,String fileReaderPath){
		try{
			RunScript.execute(connection,new FileReader(fileReaderPath));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}

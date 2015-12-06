package br.com.vortice.avon.pallete.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.DeleteDbFiles;

import br.com.vortice.avon.pallete.sqlloader.CreateDatabaseStructure;

public class DAOAb {
	
	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:"+CreateDatabaseStructure.DATABASE_PATH;
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";
	
	public DAOAb() {
		DeleteDbFiles.execute("~", "test", true);
	}
	
	public Connection getDBConnection() {
		Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
        }catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
	
}

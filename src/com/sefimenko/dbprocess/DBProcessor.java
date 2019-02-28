package com.sefimenko.dbprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class DBProcessor {
	static String filename = "resources/names.txt";

	static final String url = "jdbc:postgresql://localhost:5433/resume?user=postgres&password=123";
	Properties props = new Properties();
	static final String user = "postgres";
	static final String password = "123";

	public static void main(String[] args) {

	}

	// sets up a connection with specified URL
	public Connection connect() {
		
		try {
			Connection conn = DriverManager.getConnection(url);
			return conn;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Connection failed");
				return null;
			}
		System.out.println("Connection successful");
		}

	try(

	BufferedReader br = Files.newBufferedReader(Paths.get(filename)))
	{

		List<String> fullNames = br.lines().collect(Collectors.toList());
		System.out.println("Reading successful");

		for (int i = 0; i < fullNames.size(); i++) {
			names.add(fullNames.get(i).split(" "));
		}

	}catch(
	IOException e)
	{
		e.printStackTrace();
	}

}}
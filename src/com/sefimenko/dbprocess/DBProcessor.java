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
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBProcessor {
	// resources
	static private String namesFilename = "resources/names.txt";
	static private String sentencesFilename = "resources/strings.txt";

	// DB connection
	static final String url = "jdbc:postgresql://localhost:5433/resume?user=postgres&password=123";
	static final String user = "postgres";
	static final String password = "123";
	Connection conn;
	
	// Lists of read data
	static List<String> fullNames;
	static List<String> sentences;
	
	// Objects
	static Randomizer r = new Randomizer();

	public static void main(String[] args) {

	
	}

	// sets up a connection with a specified URL
	public void connect() {

		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection failed");
		}
	}

	// reads names from file and adds them to fullNames list
	public static void readNames() {

		try (BufferedReader br = Files.newBufferedReader(Paths.get(namesFilename))) {

			fullNames = br.lines().collect(Collectors.toList());

			System.out.println("Reading names successful");


		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot read names");
		}

	}

	
	// returns count of random sentences from sentences list
	public String getRandomSentence(int count) {

		StringBuffer string = new StringBuffer("");
		for(int i=0; i<count; i++) {
			
			string.append(sentences.get(r.getRandomNumber(sentences.size())));
		}
		return string.toString();
	}
	
	// returns random sentence from sentences list
		public String getRandomSentence() {
							
			return sentences.get(r.getRandomNumber(sentences.size()));
		}
	

	// reads sentences from string.txt and adds them to sentences list divided by "."
	public static void readSentences() {
		try (Stream<String> stream = Files.lines(Paths.get(sentencesFilename))) {
			stream.forEach(s -> {
				// not safe if contents of the file is large, need to come up with check 
				sentences = Arrays.asList(s.split("(?<=\\.)"));
				sentences.forEach(String::trim);
				System.out.println("Split successfully");
			});

			if (!sentences.isEmpty()) {
				System.out.println("Reading sentences successful");
			} else {
				System.out.println("list is empty");
			}


		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot read sentences");
		}

	}
}
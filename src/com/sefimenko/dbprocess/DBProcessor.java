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
	static String namesFilename = "resources/names.txt";
	static String sentencesFilename = "resources/strings.txt";

	static final String url = "jdbc:postgresql://localhost:5433/resume?user=postgres&password=123";
	Properties props = new Properties();
	static final String user = "postgres";
	static final String password = "123";
	Connection conn;
	static List<String> fullNames;
	static List<String> sentences;
	Randomizer r = new Randomizer();

	public static void main(String[] args) {

//		readNames();
//		fullNames.forEach(System.out::println);
//		readSentences();
//		sentences.forEach(System.out::println);
	
	}

	// sets up a connection with specified URL
	public void connect() {

		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection failed");
		}
	}

	public static void readNames() {

		try (BufferedReader br = Files.newBufferedReader(Paths.get(namesFilename))) {

			fullNames = br.lines().collect(Collectors.toList());

			System.out.println("Reading names successful");

//		for (int i = 0; i < fullNames.size(); i++) {
//			List<String> names;
//			names.add(fullNames.get(i).split(" "));
//		}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot read names");
		}

	}

	public String getRandomSentence(int count) {

		StringBuffer string = new StringBuffer("");
		for(int i=0; i<count; i++) {
			
			string.append(sentences.get(r.getRandomNumber(sentences.size())));
		}
		return string.toString();
	}

	public static void readSentences() {
		try (Stream<String> stream = Files.lines(Paths.get(sentencesFilename))) {
			stream.forEach(s -> {
				// not safe if contents of the file is large, need to come up with check 
				sentences = Arrays.asList(s.split(". "));
			});

			if (!sentences.isEmpty())
				System.out.println("Reading sentences successful");

//		for (int i = 0; i < fullNames.size(); i++) {
//			List<String> names;
//			names.add(fullNames.get(i).split(" "));
//		}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot read sentences");
		}

	}
}
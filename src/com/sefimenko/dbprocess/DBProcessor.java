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
		
		readNames();
		readSentences();
		DBProcessor dbp = new DBProcessor();
		dbp.populateUser(fullNames.get(11).split("\\s+"));
		System.out.println(fullNames.get(0).split("\\s+")[0]);
	
	}
	
	public void populateUser(String[] fullName) {
	
	User user = new User();
	String bdate = r.getRandomDateOfBirth();
	String username = generateUsername(fullName);
	user.setBirthday(bdate);
	user.setCertName(getRandomSentence());
	user.setCity(getRandomSentence());
	user.setContactFacebook("https://www.facebook.com/" + username);
	user.setContactGithub("https://www.github.com/" + username);
	user.setContactLinkedin("https://www.linkedin.com/" + username);
	user.setContactSkype(username);
	user.setContactStack(username);
	user.setContactVK(username);
	user.setCountry(getRandomSentence());
	user.setCourseEndDate(r.getRandomDate(bdate));
	user.setCourseName(getRandomSentence());
	user.setCourseSchoolName(getRandomSentence());
	user.setEduDescription(getRandomSentence(3));
	user.setEduStartdate(r.getRandomDate(bdate));
	user.setEduEndDate(r.getRandomDate(user.getEduStartdate()));
	user.setEduFaculty(getRandomSentence());
	user.setEduUniversity(getRandomSentence());
	user.setEmail(username + "@mail.com");
	user.setFirstName(fullName[0]);
	user.setHobbyName(getRandomSentence());
	user.setInfo(getRandomSentence(5));
	user.setLangLevel(getRandomSentence());
	user.setLangName(getRandomSentence());
	user.setLangType(getRandomSentence());
	user.setLastName(fullName[1]);
	user.setPassword("1234");
	user.setPhone("+" + r.getRandomNumber(9999999));
	user.setPracticeCompany(getRandomSentence());
	user.setPracticeDemoURL(getRandomSentence());
	user.setPracticeDescription(getRandomSentence(5));
	user.setPracticeStartDate(r.getRandomDate(bdate));
	user.setPracticeEndDate(r.getRandomDate(user.getPracticeStartDate()));
	user.setPracticeName(getRandomSentence());
	user.setPracticeSourceCode(getRandomSentence());
	user.setQualification(getRandomSentence());
	user.setSkillCategory(getRandomSentence());
	user.setSkillDescription(getRandomSentence());
	user.setWantJob(getRandomSentence());
	
	
	
	
	if(user.isCompleted()) {
		
	user.setCompleted(true);
	}
	
	System.out.println(user.toString());
	
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
	
public String generateUsername(String[] fullName) {
		
//		String initial = fullName[0].split(".")[0];
		StringBuffer username = new StringBuffer();
		username.append(fullName[0].toCharArray()[0]);
		System.out.println(fullName[0].toCharArray()[0]);
		username.append(fullName[1]);
		username.append(r.getRandomNumber(9999));
		return username.toString();
	}
}
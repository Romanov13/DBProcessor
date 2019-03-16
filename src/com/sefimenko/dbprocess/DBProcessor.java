package com.sefimenko.dbprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBProcessor {
	
//	This class connects to the DB with and populates it pulling data from names.txt and strings.txt
	
	
	// resources
	static private String namesFilename = "src/main/resources/names.txt";
	static private String sentencesFilename = "src/main/resources/strings.txt";
	static private String IMGDirectory = "src/main/resources/faces";
	static private String certificateLink = "src/main/resources/certificate/sample-celtificate.jpg";

	// DB connection
	static final String url = "jdbc:postgresql://localhost:5433/resume?user=postgres&password=123";
	static final String user = "postgres";
	static final String password = "123";
	Connection conn;
	
	// Lists of read data
	static List<String> fullNames;
	static List<String> sentences;
	static List<String> IMGLinks;
	
	// Objects
	static Randomizer r = new Randomizer();
	
	public DBProcessor() {
		connect();
	}

	public static void main(String[] args) {
		
		// reading data from files and stornig them in Lists
		readNames(namesFilename);
		readSentences(sentencesFilename);
		readIMGLinks(IMGDirectory);
	
		// setting up a connection in constructor and comprising a SQL command to populate the DB
		DBProcessor dbp = new DBProcessor();
		String SQL = dbp.populateUser();
		
		
//		System.out.println(SQL);
		
		dbp.processSQLCommand(SQL);
	
	}
	
	// processes passes SQL command to a DB with established connection
	public void processSQLCommand(String SQL) {
		
		try (Statement statement = conn.createStatement();){
			statement.executeUpdate(SQL);
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	// creates users and adds their properties to return a string ready to be used in SQL statement
	public String populateUser() {
	
		StringBuffer toDB = new StringBuffer("");
		for(int i = 0; i<fullNames.size(); i++) {
	User user = new User();
	String[] fullName = checkName(fullNames.get(i));
	String bdate = r.getRandomDateOfBirth();
	String username = generateUsername(fullName);
	user.setBirthday(bdate);
	user.setCertName(getRandomSentence());
	user.setCity("City");
	user.setLargePhotoURL(IMGLinks.get(i));
	user.setSmallPhotoURL(IMGLinks.get(i));
	user.setContactFacebook("https://www.facebook.com/" + username);
	user.setContactGithub("https://www.github.com/" + username);
	user.setContactLinkedin("https://www.linkedin.com/" + username);
	user.setContactSkype(username);
	user.setContactStack(username);
	user.setContactVK(username);
	user.setCountry("USA");
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
	
	// may be extracted to separate method
	String uid = "id" + i + 1;
	if(i>0) {toDB.append(", ");}
	toDB.append("(" + i + ", ");
	toDB.append("'" + uid + "', '");
	toDB.append(user.getFirstName() + "', '");
	toDB.append(user.getLastName() + "', '");
	toDB.append(user.getEmail() + "', '");
	toDB.append(user.getPhone() + "', '");
	toDB.append(user.getPassword() + "', '");
	toDB.append(user.getLargePhotoURL() + "', '");
	toDB.append(user.getBirthday() + "', '");
	toDB.append(user.getCountry() + "', '");
	toDB.append(user.getCity() + "', '");
	toDB.append(user.getWantJob() + "', '");
	toDB.append(user.getQualification() + "', '");
	toDB.append(user.getContactSkype() + "', '");
	toDB.append(user.getContactVK() + "', '");
	toDB.append(user.getContactFacebook() + "', '");
	toDB.append(user.getContactLinkedin() + "', '");
	toDB.append(user.getContactGithub() + "', '");
	toDB.append(user.getContactStack() + "', '");
	toDB.append(user.getSmallPhotoURL() + "', '");
	toDB.append(user.getInfo() + "', ");
	toDB.append(user.isCompleted() + ", ");
	toDB.append("'2018-01-01'");
	toDB.append(")\n");
		}

	String toReturn = insertNewUserCommand(toDB.toString());
	return toReturn;
	}

	// replaces apostrophe in string and splits first and last name
	private String[] checkName(String name) {
		
		name = name.replaceAll("'", "''");

		String[] result = name.split("\\s+");
		return result;
	}

	// generates and returns initial part of the insert statement
	public String insertNewUserCommand(String userData) {
		StringBuffer SQL = new StringBuffer("INSERT INTO profile (id, uid, "
				+ "first_name, "
				+ "last_name, "
				+ "email, "
				+ "phone, "
				+ "password, "
				+ "large_photo, "
				+ "birthday, "
				+ "country, "
				+ "city, "
				+ "want_job, "
				+ "qualification, "
				+ "contact_skype, "
				+ "contact_vk, "
				+ "contact_facebook, "
				+ "contact_linkedin, "
				+ "contact_github, "
				+ "contact_stack, "
				+ "small_photo, "
				+ "info, "
				+ "completed, "
				+ "created) " + "VALUES ");
		String command = SQL.append(userData + ";").toString();
		return command;
	}

	// sets up a connection to a DB with a specified URL
	public void connect() {

		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection failed");
		}
	}

	// reads names from file and adds them to fullNames list
	public static void readNames(String namesFilename) {

		try (BufferedReader br = Files.newBufferedReader(Paths.get(namesFilename))) {

			fullNames = br.lines().collect(Collectors.toList());

			System.out.println("Reading names successful");


		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot read names");
		}

	}
	
	// reads list of files in images folder and adds them to the list
	public static void readIMGLinks(String IMGDirectory) {
	
		try(Stream<Path> walk = Files.walk(Paths.get(IMGDirectory))){
			IMGLinks = walk.map(x -> x.toString())
					.filter(f -> f.matches(".*.(.jpeg|.jpg)$")).collect(Collectors.toList());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	// returns specified number of random sentences from sentences list
	public String getRandomSentence(int count) {

		StringBuffer string = new StringBuffer("");
		for(int i=0; i<count; i++) {
			
			string.append(sentences.get(r.getRandomNumber(sentences.size())));
		}
		return string.toString();
		
	}
	
	// returns a random sentence from sentences list
		public String getRandomSentence() {
							
			String toReturn = sentences.get(r.getRandomNumber(sentences.size()));
		
			if(toReturn.length() > 10) {
			toReturn = toReturn.substring(0, 10);}
			return toReturn;
		}
	
	

	// reads sentences from file and adds them to sentences list divided by "."
	public static void readSentences(String sentencesFilename) {
		try (Stream<String> stream = Files.lines(Paths.get(sentencesFilename))) {
			stream.forEach(s -> {
				// not safe if contents of the file is large, need to come up with check 
				sentences = Arrays.asList(s.split("(?<=\\.)"));
				sentences.forEach(String::trim);
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
	
	
	// returns a user name generate from a first letter of name and full surname of the person with random digits appended
public String generateUsername(String[] fullName) {
		
		StringBuffer username = new StringBuffer();
		username.append(fullName[0].toCharArray()[0]);
		username.append(fullName[1]);
		username.append(r.getRandomNumber(9999));
		return username.toString();
	}
}
package com.sefimenko.dbprocess;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
	
	// generates one random digit based on the array of range borders provided
	public int getRandomNumber(int[] range) {
		
		int result = range[0] + (int)(Math.random()*range[1]);
		return result;
	}
	
		// generates one random digits with upper range border specified
public int getRandomNumber(int range) {
		
		int result = 0 + (int)(Math.random()*range);
		return result;
	}
	
	
	
	// generates array of random digits based on the array of range borders provided
	public int[] getRandomNumber(int count, int[] range) {
		int i = 0;
		int[] result = new int[count];
		while(i!=count) {
		result[i] = range[0] + (int)(Math.random()*range[1]);
		i++;		
		}
		return result;
		
	}
	
	// returns random date between 1970 and 2000
	public String getRandomDateOfBirth() {
		long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
		long maxDay = LocalDate.of(2000, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		System.out.println(maxDay);

		return LocalDate.ofEpochDay(randomDay).toString();
		
	}
	

	// returns a random date not lower than birth date
	public String getRandomDate(String BDDate) {
		String[] BDDateDiv = BDDate.split("-");
		long minDay = LocalDate.of(Integer.parseInt(BDDateDiv[0]), Integer.parseInt(BDDateDiv[1]), Integer.parseInt(BDDateDiv[2])).toEpochDay();
		long maxDay = LocalDate.of(2000, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		return LocalDate.ofEpochDay(randomDay).toString();
		
	}
	

}

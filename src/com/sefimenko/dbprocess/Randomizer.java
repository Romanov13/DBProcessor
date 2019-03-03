package com.sefimenko.dbprocess;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
	
	// generates one random digit based on the range provided
	public int getRandomNumber(int[] range) {
		
		int result = range[0] + (int)(Math.random()*range[1]);
		return result;
	}
	
public int getRandomNumber(int range) {
		
		int result = 0 + (int)(Math.random()*range);
		return result;
	}
	
	
	
	// generates array of random digits based on the range provided
	public int[] getRandomNumber(int count, int[] range) {
		int i = 0;
		int[] result = new int[count];
		while(i!=count) {
		result[i] = range[0] + (int)(Math.random()*range[1]);
		i++;		
		}
		return result;
		
	}
	
	public String getRandomDateOfBirth() {
		long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
		long maxDay = LocalDate.of(2000, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		System.out.println(maxDay);

		return LocalDate.ofEpochDay(randomDay).toString();
		
	}
	

	public String getRandomDate(String dateOfBirth) {
		long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
		long maxDay = LocalDate.of(2000, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		return LocalDate.ofEpochDay(randomDay).toString();
		
	}
	

}

package com.qa.mmt.base;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CheckDate {
	public static void main(String[] args) {
		LocalDate mydate = LocalDate.of(2019, 6, 30);
		//LocalDate objDate = LocalDate.now();
		LocalDate objDate = mydate;
		
		//objDate = objDate.plus(1, ChronoUnit.DAYS);
		String coOrd = getCoordinate(objDate);
		System.out.println("CoOrdinate is :" + coOrd);
	}
	
	public static String getCoordinate(LocalDate objDate) {
		String cordinate = "";
		// DateFormat dateFormat = new SimpleDateFormat("dd MMM yy");

		//String myDate = objDate.toString();
		int row = 0;
		int month = objDate.getMonthValue();
		int year = objDate.getYear();
		
		LocalDate firstdayoftheMonth = LocalDate.of(year, month, 1);
		
		int firstcol = firstdayoftheMonth.getDayOfWeek().getValue();
		
		if (firstcol == 7) {
			firstcol = 1;
		}else {
			firstcol += 1;
		}
		
		int offset = 0;
		if (firstcol==1) {
			offset = 7;
			row =1;
		}else {
			offset = (7-firstcol)+1;
			row=1;
		}
		
		int dayoftheMonth = objDate.getDayOfMonth();
		
		int restcells = dayoftheMonth-offset;
		
		if (restcells>0) {
			int addrow= 0;
			if (restcells%7 == 0) {
				addrow = (restcells/7);
			}else {
				addrow = (restcells/7)+1;
			}
			
			row =row+addrow;
		}
		
		int col = objDate.getDayOfWeek().getValue();
		
		if (col == 7) {
			col = 1;
		} else {
			col += 1;
		}
		
		/*
		String day = myDate.split("-")[2];
		int dayInt = Integer.parseInt(day);	

		row = dayInt / 7;

		if (dayInt % 7 != 0) {
			// If condition to check if date is present in first row
			if (dayInt < 7 && dayInt > col) {
				row = 1;
			}

			row += 1;

		} else { // Else condition to check if date is present in first coloumn and is divisible
					// by 7 (e.g. for dates 7,14,21,28)
			if (col == 1) {
				row += 1;
			}

		}*/
		cordinate = "" + row + "-" + col;
		return cordinate;
	}
}

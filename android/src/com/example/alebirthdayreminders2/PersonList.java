package com.example.alebirthdayreminders2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.content.Context;

public class PersonList {
	private DatabaseHelper dbHelper;

	public PersonList() {
	}

	public void initialize(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public ArrayList<Person> getPersonList() {
		return dbHelper.getAllPersons();
	}

	/**
	 * Saves person to the database. Will generate an ID if needed.
	 * @param person
	 */
	public void savePerson(Person person) {
		if (person.getId() != -1 && dbHelper.getPerson(person.getId()) != null) {
			dbHelper.updatePerson(person);
		} else {
			dbHelper.addPerson(person);
		}
	}
	
	/**
	 * Retrieves the person with the given ID
	 * @return null if not found
	 */
	public Person getPersonById(int id) {
		return dbHelper.getPerson(id);
	}
	
	public int[] getPersonIds() {
		return dbHelper.getAllPersonIds();
	}
	
	public ArrayList<Person> getAllPersonsByName() {
		return dbHelper.getAllPersonsByName();
	}
	
	public ArrayList<Person> getAllPersonsByBirthday() {
		ArrayList<Person> list = dbHelper.getAllPersons();
		Collections.sort(list);
		return list;
	}
	
	public ArrayList<Person> getPersonsForBirthday(Date date) {
		return dbHelper.getPersonsForBirthday(date);
	}
}

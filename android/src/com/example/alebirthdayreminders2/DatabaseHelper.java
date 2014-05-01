package com.example.alebirthdayreminders2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "PeopleManager";
	private static final String TABLE_NAME = "People";

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	// Field names for the table
	private static final String FIELD_ID = "id";
	private static final String FIELD_NAME = "name";
	private static final String FIELD_EMAIL = "email";
	private static final String FIELD_BIRTHDAY = "birthday";
	private static final String FIELD_IMAGE = "image";

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			DATE_FORMAT, Locale.US);

	DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + FIELD_ID
				+ " INTEGER PRIMARY KEY," + FIELD_NAME + " TEXT," + FIELD_EMAIL
				+ " TEXT," + FIELD_BIRTHDAY + " TEXT, " 
				+ FIELD_IMAGE + " TEXT );";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new Person
	void addPerson(Person person) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = populateValues(person);

		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}

	// Getting single Person. Will return null if doesn't exist.
	Person getPerson(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_ID,
				FIELD_NAME, FIELD_EMAIL, FIELD_BIRTHDAY, FIELD_IMAGE }, FIELD_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Person person = generatePersonFromCursor(cursor);
		return person;
	}

	// Get All Persons
	public ArrayList<Person> getAllPersons() {
		String selectQuery = "SELECT  * FROM " + TABLE_NAME;

		return runSelectAndReturnList(selectQuery);
	}
	
	public ArrayList<Person> getAllPersonsByName() {
		String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + FIELD_NAME;

		return runSelectAndReturnList(selectQuery);
	}
	
	public ArrayList<Person> getPersonsForBirthday(Date date) {
		String selectQuery = "SELECT * FROM " + TABLE_NAME
				+ " WHERE " + FIELD_BIRTHDAY + " == " + sdf.format(date);

		return runSelectAndReturnList(selectQuery);
	}
	
	private ArrayList<Person> runSelectAndReturnList(String selectQuery) {
		ArrayList<Person> personList = new ArrayList<Person>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Person person = generatePersonFromCursor(cursor);
				if (person != null) {
					personList.add(person);
				}
			} while (cursor.moveToNext());
		}
		return personList;
	}

	// Get All Person IDs
	public int[] getAllPersonIds() {
		// Select All Query
		String selectQuery = "SELECT " + FIELD_ID + " FROM " + TABLE_NAME;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		int[] ids = new int[cursor.getCount()];

		cursor.moveToFirst();
		for (int i = 0; i < ids.length; ++i) {
			ids[i] = Integer.parseInt(cursor.getString(0));
			cursor.moveToNext();
		}
		return ids;
	}

	// Updating single Person
	public int updatePerson(Person person) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = populateValues(person);

		// updating row
		return db.update(TABLE_NAME, values, FIELD_ID + " = ?",
				new String[] { String.valueOf(person.getId()) });
	}

	// Deleting single Person
	public void deletePerson(Person person) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, FIELD_ID + " = ?",
				new String[] { String.valueOf(person.getId()) });
		db.close();
	}

	// Getting Persons Count
	public int getPersonsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	private Person generatePersonFromCursor(Cursor cursor) {
		Person person = null;
		try {
			person = new Person(Integer.parseInt(cursor.getString(0)),
					cursor.getString(1), cursor.getString(2), sdf.parse(cursor
							.getString(3)), cursor.getString(4));
		} catch (IllegalArgumentException e) {
			Log.e("DatabaseHelper",
					"Unable to parse birthday for " + cursor.getString(1));
		} catch (ParseException e) {
			Log.e("DatabaseHelper",
					"Unable to parse birthday for " + cursor.getString(1));
		}
		return person;
	}
	
	private ContentValues populateValues(Person person) {
		ContentValues values = new ContentValues();
		values.put(FIELD_NAME, person.getName()); // Person name
		values.put(FIELD_EMAIL, person.getEmail());
		values.put(FIELD_BIRTHDAY, sdf.format(person.getBirthday()));
		values.put(FIELD_IMAGE, person.getImageLocation());
		return values;
	}
}

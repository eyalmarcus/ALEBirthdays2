package com.example.alebirthdayreminders2;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Fragment for editing a person's info.
 */
public class EditPersonFragment extends Fragment {
	Integer personId;
	Person person;
	EditText nameField;
	TextView birthdayText;
	Date birthday;
	Button saveButton;
	Button photoButton;
	Button dateButton;
	PersonList personProvider;
	
	public EditPersonFragment() {}
	
	void setPersonProvider(PersonList personProvider) {
		this.personProvider = personProvider;
	}
	
	public void updateBirthDate(Date date) {
		birthday = date;
		birthdayText.setText(birthday.toString()); 
	}
	
	// Loads the info of a person for editing.
	public void loadPerson(int id) {
		// TODO: populate fields.
		// TODO: Warn if current person is not saved.
		personId = id;
		new AsyncTask<Integer, Integer, Person>() {

			@Override
			protected Person doInBackground(Integer... params) {
				return personProvider.getPersonById(params[0]);
			}

			@Override
			protected void onPostExecute(Person result) {
				populateInfo(result);
			}
			
		}.execute(Integer.valueOf(id));
	}
	
	void populateInfo(Person person) {
		nameField.setText(person.getName());
		updateBirthDate(person.getBirthday());
	}
	
	public void createNewPerson() {
		personId = null;
	}
	
	// Save the person's info.
	public void savePerson() {
		String name = nameField.getEditableText().toString();
		Log.e("", "Saving person with name " + name);
		
		String image = "";
		String email = "";
		Person person;
		if (personId != null) {
			person = new Person(personId, name, email, birthday, image);
			Log.e("", "Saving existing person.");
		} else {
			person = new Person(name, email, birthday, image);
			Log.e("", "Saving new person.");
		}
		new AsyncTask<Person, Void, Void>() {
			@Override
			protected Void doInBackground(Person... params) {
				PersonList personList = new PersonList();
				personList.initialize(getActivity());
				personList.savePerson(params[0]);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				((EditPerson) getActivity()).personSaved(null);
			}
			
			
		}.execute(person);
	}
	
	public void getPersonPhoto() {
		/*
		Intent intent = new Intent(Intent.ACTION_GET_CONTEXT);
		intent.setType("image/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, REQUEST_CODE);
		*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_edit_person,
				container, false);

		nameField = (EditText) rootView.findViewById(R.id.person_name);
		birthdayText = (TextView) rootView.findViewById(R.id.person_birth_date);
		saveButton = (Button) rootView.findViewById(R.id.person_save);
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View button) {
				savePerson();
			}
		});
		photoButton = (Button) rootView.findViewById(R.id.person_photo);
		photoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View button) {
				getPersonPhoto();
			}
		});
		dateButton = (Button) rootView.findViewById(R.id.person_change_date_button);
		dateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View button) {
				DialogFragment newFragment = new DatePickerFragment();
			    newFragment.show(getFragmentManager(), "timePicker");;
			}
		});
		
		
		return rootView;
	}
	
	public static class DatePickerFragment extends DialogFragment
		implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			return new DatePickerDialog(getActivity(), this,
					c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		}

		@Override
		public void onDateSet(DatePicker picker, int year, int month, int day) {
			Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
			cal.set(year, month, day, 0, 0, 0);
			EditPerson activity = (EditPerson) getActivity();
			Log.e("", "Date is " + cal.getTime());
			activity.updateBirthday(cal.getTime());
		}
		
	}
}
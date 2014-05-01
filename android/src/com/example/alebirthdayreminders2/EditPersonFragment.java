package com.example.alebirthdayreminders2;

import java.util.Date;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Fragment for editing a person's info.
 */
public class EditPersonFragment extends Fragment {
	Integer personId;
	Person person;
	EditText nameField;
	Button saveButton;
	
	public EditPersonFragment() {
	}
	
	// Loads the info of a person for editing.
	public void loadPerson(int id) {
		// TODO: populate fields.
		// TODO: Warn if current person is not saved.
		personId = id;
		new AsyncTask<Integer, Integer, Person>() {

			@Override
			protected Person doInBackground(Integer... params) {
				PersonList personList = new PersonList();
				personList.initialize(getActivity());
				return personList.getPersonById(params[0]);
			}

			@Override
			protected void onPostExecute(Person result) {
				populateInfo(result);
			}
			
		}.execute(id);
	}
	
	void populateInfo(Person person) {
		nameField.setText(person.getName());
	}
	
	public void createNewPerson() {
		personId = null;
	}
	
	// Save the person's info.
	public void savePerson() {
		String name = nameField.getEditableText().toString();
		Date birthday = new Date();
		String image = "";
		String email = "";
		Person person;
		if (personId != null) {
			person = new Person(personId, email, image, birthday);
			Log.e("", "Saving existing person.");
		} else {
			person = new Person(name, email, birthday);
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_edit_person,
				container, false);

		nameField = (EditText) rootView.findViewById(R.id.person_name);
		nameField.setText("some text");
		saveButton = (Button) rootView.findViewById(R.id.person_save);
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View button) {
				savePerson();
			}
		});
		
		return rootView;
	}
}
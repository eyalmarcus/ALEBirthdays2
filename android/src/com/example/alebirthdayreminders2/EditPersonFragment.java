package com.example.alebirthdayreminders2;

import java.util.Date;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Fragment for editing a person's info.
 */
public class EditPersonFragment extends Fragment {
	Integer personId;
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
			protected Person doInBackground(Integer... id) {
				// TODO: Get person from db.
				return null;
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
		populateInfo(new Person("", "", new Date()));
	}
	
	// Save the person's info.
	public void savePerson() {
		if (personId != null) {
			// TODO
		} else {
			// TODO
		}
		// TODO: Refresh the other view.
		// TODO(eyalma): Maybe move save action to background.
		// TODO(eyalma): Get id if it was just created.
		((EditPerson) getActivity()).personSaved(personId);
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
package com.example.alebirthdayreminders2;

import java.util.Date;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

public class EditPerson extends Activity {
	PersonList personProvider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_person);
		
		personProvider = new PersonList();
		personProvider.initialize(this);
		
		// TODO(eyalma): Choose what to load based on intent.
		if (savedInstanceState == null) {
			ListPersonsFragment fragment = new ListPersonsFragment();
			fragment.setPersonProvider(personProvider);
			getFragmentManager().beginTransaction()
					.add(R.id.container, fragment).commit();			
		}
	}

	void personSaved(Integer id) {
		// TODO(eyalma): Close edit person fragment.
		// TODO TODO
		// TODO: If list fragment is visible, update it.
		Log.e("", "popping stack");
		getFragmentManager().popBackStack();
		//getFragmentManager().
//		ListPersonsFragment listPersons =
//				(ListPersonsFragment)getFragmentManager().findFragmentById(R.id.persons_list);
//		if (listPersons != null) {
//		  listPersons.updatePerson(id);
//		}
	}
	
	// If id is null, creates a new person
	void editPerson(Integer id) {
		Fragment listFragment = getFragmentManager().findFragmentById(R.id.container);
		EditPersonFragment editFragment = new EditPersonFragment();
		editFragment.setPersonProvider(personProvider);
		if (id != null) {
			editFragment.loadPerson(id);
		} else {
			editFragment.createNewPerson();
		}
		getFragmentManager().beginTransaction().addToBackStack("startEdit")
			.remove(listFragment)
			.add(R.id.container, editFragment)
			.commit();
	}
	
	void updateBirthday(Date date) {
		EditPersonFragment fragment =
				(EditPersonFragment)getFragmentManager().findFragmentById(R.id.container);
		if (fragment == null) {
			Log.e("", "Not edit person fragment found");
			return;
		}
		Log.e("", "Updating birth date");
		fragment.updateBirthDate(date);
	}
}

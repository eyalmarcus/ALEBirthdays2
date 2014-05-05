package com.example.alebirthdayreminders2;

import java.util.Date;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

public class EditPerson extends Activity {
	PersonList personProvider;
	
	private boolean showSideBySide() {
		return getResources().getConfiguration().screenWidthDp > 400;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (showSideBySide()) {
			setContentView(R.layout.activity_side_by_side);
		} else {
			setContentView(R.layout.activity_edit_person);
		}
		
		personProvider = new PersonList();
		personProvider.initialize(this);
		
		// TODO(eyalma): Choose what to load based on intent.
		if (savedInstanceState == null) {
			if (showSideBySide()) {
				getFragmentManager().beginTransaction()
					.add(R.id.person_list_fragment, new ListPersonsFragment())
					.add(R.id.person_edit_fragment, new EditPersonFragment())
					.commit();			
			} else {
				getFragmentManager().beginTransaction()
						.add(R.id.container, new ListPersonsFragment()).commit();
			}
		}
	}

	void personSaved(Integer id) {
		// TODO(eyalma): Close edit person fragment.
		// TODO TODO
		// TODO: If list fragment is visible, update it.
		if (showSideBySide()) {
			ListPersonsFragment listPersons =
					(ListPersonsFragment)getFragmentManager().findFragmentById(R.id.person_list_fragment);
			Log.e("", "Updating person");
			listPersons.updatePerson(id);		
		} else {
			Log.e("", "popping stack");
			getFragmentManager().popBackStack();
		}
		//getFragmentManager().
//		ListPersonsFragment listPersons =
//				(ListPersonsFragment)getFragmentManager().findFragmentById(R.id.persons_list);
//		if (listPersons != null) {
//		  listPersons.updatePerson(id);
//		}
	}
	
	// If id is null, creates a new person
	void editPerson(Integer id) {
		if (showSideBySide()) {
			EditPersonFragment editFragment =
				(EditPersonFragment)getFragmentManager().findFragmentById(R.id.person_edit_fragment);
			if (id != null) {
				editFragment.loadPerson(id);
			} else {
				editFragment.createNewPerson();
			}
		} else {
			Fragment listFragment = getFragmentManager().findFragmentById(R.id.container);
			EditPersonFragment editFragment = new EditPersonFragment();
			editFragment.setPerson(id);
			getFragmentManager().beginTransaction().addToBackStack("startEdit")
				.remove(listFragment)
				.add(R.id.container, editFragment)
				.commit();
		}
	}
	
	void updateBirthday(Date date) {
		EditPersonFragment fragment;
		if (showSideBySide()) {
			 fragment = (EditPersonFragment)getFragmentManager().findFragmentById(R.id.person_edit_fragment);			
		} else {
		 fragment = (EditPersonFragment)getFragmentManager().findFragmentById(R.id.container);
		}
		if (fragment == null) {
			Log.e("", "Not edit person fragment found");
			return;
		}
		Log.e("", "Updating birth date");
		fragment.updateBirthDate(date);
	}

	public PersonList getPersonProvider() {
		return personProvider;
	}
}

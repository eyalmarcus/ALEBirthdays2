package com.example.alebirthdayreminders2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Build;

public class EditPerson extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_person);
		
		// TODO(eyalma): Choose what to load based on intent.
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new EditPersonFragment()).commit();			
		}
	}

	void personSaved(Integer id) {
		// TODO: If list fragment is visible, update it.
		ListPersonsFragment listPersons =
				(ListPersonsFragment)getFragmentManager().findFragmentById(R.id.persons_list);
		if (listPersons != null) {
		  listPersons.updatePerson(id);
		}
	}
	
	// If id is null, creates a new person
	void editPerson(Integer id) {
		// TODO.
	}
}

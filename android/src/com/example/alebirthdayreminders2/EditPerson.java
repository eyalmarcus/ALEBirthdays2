package com.example.alebirthdayreminders2;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_person, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Fragment for editing a person's info.
	 */
	public static class EditPersonFragment extends Fragment {
		Integer personId;
		EditText nameField;
		
		public EditPersonFragment() {
		}
		
		public void loadPerson(int id) {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_edit_person,
					container, false);

			nameField = (EditText) rootView.findViewById(R.id.person_name);
			
			nameField.setText("some text");
			
			return rootView;
		}
	}

}

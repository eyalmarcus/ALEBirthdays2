package com.example.alebirthdayreminders2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

// Fragment for showing the list of people.
public class ListPersonsFragment extends Fragment {
	PersonListAdapter adapter;
	ListView listView;
	
	public ListPersonsFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_edit_person,
				container, false);
		listView = (ListView) rootView.findViewById(R.id.persons_list);
		adapter = new PersonListAdapter(getActivity());
		// TODO Auto-generated method stub
		return rootView;
	}
	
	class PersonListAdapter extends BaseAdapter {
		private List<Integer> ids;
		PersonList personProvider;
		private Map<Integer, Person> personCache;
		Context context;
		
		PersonListAdapter(Context context) {
			this.context = context;
			personProvider = new PersonList();
			ids = new ArrayList<Integer>();
			refreshDataSet();
		}
		
		void refreshDataSet() {
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					// TODO(eyalma): get ids from personProvider.
					ids = null;
					return null;
				}
				
				@Override
				protected void onPostExecute(Void result) {
					notifyDataSetChanged();
				}
			}.execute();
		}
		
		void invalidateCacheEntry(Integer id) {
			personCache.remove(id);
			notifyDataSetChanged();
		}
		
		public void updateList(String namePrefix) {
			// TODO(eyalma): Update ids from db.
			refreshDataSet();
		}
		
		@Override
		public int getCount() {
			return ids.size();
		}

		@Override
		public Person getItem(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int index) {
			return ids.get(index);
		}

		@Override
		public View getView(int index, View convertView, ViewGroup parent) {
			View entry;
			Integer id = ids.get(index);
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater)context.getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
				entry = inflater.inflate(R.layout.person_entry, null);
			} else {
				entry = convertView;
			}
			if (personCache.containsKey(id)) {
				Person person = personCache.get(id);
				((TextView) entry.findViewById(R.id.person_entry_name))
					.setText(person.getName());
			} else {
				// TODO(eyalma): AsyncTask
			}
			
			return entry;
		}
		
	}

	// Recalculates the list (e.g. if a name changed).
	public void updatePerson(Integer id) {
		adapter.invalidateCacheEntry(id);
		// TODO Auto-generated method stub
		
	}
}
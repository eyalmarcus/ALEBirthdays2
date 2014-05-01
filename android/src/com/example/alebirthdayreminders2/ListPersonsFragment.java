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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
		View rootView = inflater.inflate(R.layout.fragment_list_persons,
				container, false);
		listView = (ListView) rootView.findViewById(R.id.persons_list);
		adapter = new PersonListAdapter(getActivity());
		
		Button button = (Button) rootView.findViewById(R.id.list_add_person);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditPerson activity = (EditPerson) getActivity();
				activity.editPerson(null);
			}
		});
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
			personProvider.initialize(context);
			ids = new ArrayList<Integer>();
			refreshDataSet();
		}
		
		void refreshDataSet() {
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					// TODO(eyalma): get ids from personProvider.
					ArrayList<Integer> new_ids = new ArrayList<Integer>();
					int[] ids_array = personProvider.getPersonIds();
					for (int index = 0; index < ids_array.length; ++index) {
						new_ids.add(ids_array[index]);
					}
					ids = new_ids;
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
			final Integer id = ids.get(index);
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
				entry.setOnClickListener(new OnClickListener() {
					Integer personId = id;
					
					@Override
					public void onClick(View v) {
						((EditPerson) getActivity()).editPerson(personId);
					}
				});
			} else {
				new AsyncTask<Integer, Void, Void>() {

					@Override
					protected Void doInBackground(Integer... params) {
						Person person = personProvider.getPersonById(params[0]);
						personCache.put(params[0], person);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						notifyDataSetChanged();
					}
				};
			}
			
			return entry;
		}
		
	}

	// Recalculates the list (e.g. if a name changed).
	public void updatePerson(Integer id) {
		if (id != null) {
			adapter.invalidateCacheEntry(id);
		}
		adapter.notifyDataSetChanged();
	}
}
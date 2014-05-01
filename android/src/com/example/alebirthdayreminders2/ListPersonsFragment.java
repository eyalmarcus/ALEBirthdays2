package com.example.alebirthdayreminders2;

import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

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
		// TODO Auto-generated method stub
		return rootView;
	}
	
	class PersonListAdapter extends BaseAdapter {
		private List<Integer> ids;
		private Map<Integer, Person> personCache;
		
		PersonListAdapter() {
			updateList("");
		}
		
		void invalidateCacheEntry(Integer id) {
			personCache.remove(id);
		}
		
		public void updateList(String namePrefix) {
			// TODO(eyalma): Update ids from db.
		}
		
		@Override
		public int getCount() {
			return ids.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int index) {
			return ids.get(index);
		}

		@Override
		public View getView(int index, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	// Recalculates the list (e.g. if a name changed).
	public void updatePerson(Integer id) {
		adapter.invalidateCacheEntry(id);
		// TODO Auto-generated method stub
		
	}
}
package com.example.alebirthdayreminders2;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

// Fragment for showing the list of people.
public class ListPersonsFragment extends Fragment {
	public ListPersonsFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	class PersonListAdapter extends BaseAdapter {
		private List<Integer> ids;
		
		PersonListAdapter() {
			updateList("");
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
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
package com.example.alebirthdayreminders2;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Fragment for editing a person's info.
 */
public class EditPersonFragment extends Fragment {
	private static final int REQUEST_TAKE_PHOTO = 1;
	private static final String IMAGE_PREFIX = "JPEG_";

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			DATE_FORMAT, Locale.US);

	Integer personId;
	Person person;
	EditText nameField;
	String imageFile;
	TextView birthdayText;
	Date birthday;
	ImageView imageView;
	Button saveButton;
	Button photoButton;
	Button dateButton;
	PersonList personProvider;

	public EditPersonFragment() {
		birthday = new Date();
		imageFile = "";
	}

	public void updateBirthDate(Date date) {
		birthday = date;
		birthdayText.setText(sdf.format(birthday));
	}

	public void updateImageFileFromString(String image) {
		if (image != null && !image.isEmpty()) {
			Log.e("EditPersonFragment", "image=" + image);
			imageFile = image;
			Uri uri = Uri.parse(image);
			Log.e("EditPersonFragment", "Uri=" + uri);
			imageView.setImageURI(Uri.parse(image));
			setPic(imageFile);
		}
	}

	public void updateImageFileFromUri(Uri image) {
		if (image != null) {
			imageFile = image.toString();
			imageView.setImageURI(image);
			setPic(imageFile);
		}
	}

	// Loads the info of a person for editing.
	public void loadPerson(int id) {
		// TODO: populate fields.
		// TODO: Warn if current person is not saved.
		personId = id;
		new AsyncTask<Integer, Integer, Person>() {

			@Override
			protected Person doInBackground(Integer... params) {
				Log.e("", "Fetching person " + params[0]);
				return personProvider.getPersonById(params[0]);
			}

			@Override
			protected void onPostExecute(Person result) {
				Log.e("", "Populating result");
				populateInfo(result);
			}

		}.execute(Integer.valueOf(id));
	}

	void populateInfo(Person person) {
		nameField.setText(person.getName());
		updateBirthDate(person.getBirthday());
		updateImageFileFromString(person.getImageLocation());
	}

	public void createNewPerson() {
		personId = null;
		Person person = new Person("", "", new Date(), "");
		populateInfo(person);
	}

	public void startPopulate() {
		if (personId != null) {
			loadPerson(personId);
		} else {
			createNewPerson();
		}
	}

	// Save the person's info.
	public void savePerson() {
		String name = nameField.getEditableText().toString();
		Log.e("", "Saving person with name " + name);

		String image = "";
		String email = "";
		Person person;
		if (personId != null) {
			image = (getImageFileName().isEmpty()) ? IMAGE_PREFIX + personId : getImageFileName();
			person = new Person(personId, name, email, birthday, image);
			Log.e("", "Saving existing person.");
		} else {
			person = new Person(name, email, birthday, image);
			Log.e("", "Saving new person.");
		}
		new AsyncTask<Person, Void, Integer>() {
			@Override
			protected Integer doInBackground(Person... params) {
				Person person = params[0];
				PersonList personList = new PersonList();
				personList.initialize(getActivity());
				personList.savePerson(person);
				// TODO(eyalma): Handle the case where we don't know the id.
				return Integer.valueOf(person.getId());
			}

			@Override
			protected void onPostExecute(Integer id) {
				((EditPerson) getActivity()).personSaved(id);
			}

		}.execute(person);
	}

	public void getPersonPhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException e) {
				Log.e("EditPersonFragment", "Couldn't create image file ");
				e.printStackTrace();
			}
			if (photoFile != null) {
				Log.e("EditPersonFragment", "photoFile=" + photoFile.getAbsolutePath());
				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				Log.e("EditPersonFragment", "intent=" + intent.getAction());
				startActivityForResult(intent, REQUEST_TAKE_PHOTO);
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("EditPersonFragment", "Got to onActivityResult");
		Log.e("EditPersonFragment", "requestCode=" + requestCode);
		Log.e("EditPersonFragment", "resultCode=" + resultCode);
		Log.e("EditPersonFragment", "data=" + data);
		switch (requestCode) {
		case REQUEST_TAKE_PHOTO:
			if (resultCode == Activity.RESULT_OK && data != null) {
				Log.e("data=", data.toString());
				Bundle extras = data.getExtras();
				Uri uri = (Uri) extras.get(MediaStore.EXTRA_OUTPUT);
				updateImageFileFromUri(uri);
			} else if (data == null) {
				Log.e("null data", "");
			}
		}
	}

	private String getImageFileName() {
		return imageFile;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String imageFileName = IMAGE_PREFIX + personId;
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, /* prefix */
				".jpg", /* suffix */
				storageDir /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		updateImageFileFromString("file:" + image.getAbsolutePath());
		return image;
	}

	public void setPerson(Integer id) {
		personId = id;
	}
	
	private void setPic(String imagePath) {
	    // Get the dimensions of the View
	    int targetW = imageView.getWidth();
	    int targetH = imageView.getHeight();

	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(imagePath, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    // Determine how much to scale down the image
	    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
	    imageView.setImageBitmap(bitmap);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_edit_person,
				container, false);

		EditPerson activity = (EditPerson) getActivity();
		personProvider = activity.getPersonProvider();

		nameField = (EditText) rootView.findViewById(R.id.person_name);
		birthdayText = (TextView) rootView.findViewById(R.id.person_birth_date);
		saveButton = (Button) rootView.findViewById(R.id.person_save);
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View button) {
				savePerson();
			}
		});
		imageView = (ImageView) rootView.findViewById(R.id.person_image_view);
		photoButton = (Button) rootView.findViewById(R.id.person_photo);
		photoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View button) {
				getPersonPhoto();
			}
		});
		dateButton = (Button) rootView
				.findViewById(R.id.person_change_date_button);
		dateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View button) {
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getFragmentManager(), "timePicker");
				;
			}
		});

		startPopulate();

		return rootView;
	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			return new DatePickerDialog(getActivity(), this,
					c.get(Calendar.YEAR), c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH));
		}

		@Override
		public void onDateSet(DatePicker picker, int year, int month, int day) {
			Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
			cal.set(year, month, day, 0, 0, 0);
			EditPerson activity = (EditPerson) getActivity();
			Log.e("", "Date is " + cal.getTime());
			activity.updateBirthday(cal.getTime());
		}

	}
}
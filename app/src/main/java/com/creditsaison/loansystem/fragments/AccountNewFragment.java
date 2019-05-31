package com.creditsaison.loansystem.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentAccountNewBinding;
import com.creditsaison.loansystem.viewmodel.AccountNewViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountNewFragment extends Fragment implements View.OnClickListener{

        private static final int client_pic_code = 1;

        SharedPreferences sharedpreferences;

        ConstraintLayout layout1;
        ConstraintSet set;
        TextView text, text1, date, page_title;
        Button btn_client_photo, btn_gov_id, btn_doc_photo, btn_next;
        ImageView client_image;
        File photoFile = null;
        File govIdFile = null;
        File docFile = null;
        Bitmap rotatedBitmap = null;
        Spinner govSpinner, docSpinner, gender, marital_status, educational_status;

        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        String govFileName, docFileName;

        DatePickerDialog datePickerDialog;

        EditText first_name, middle_name, last_name, mobile_no, nationality, birth_place, dependents, gov_id_number, document_source;

        //arrays for dropdown values and their corresponding ids
        List<String> genderArray, maritalArray, educStatArray;
        List<Integer> genderIds, maritalStatIds, educStatIds;


        private FragmentAccountNewBinding binding;
        private AccountNewViewModel viewModel;

        public AccountNewFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            viewModel = ViewModelProviders.of(this).get(AccountNewViewModel.class);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            binding = FragmentAccountNewBinding.inflate(inflater, container, false);
            binding.setViewModel(viewModel);

            page_title = (TextView) binding.getRoot().findViewById(R.id.tv_page_title);

            btn_client_photo = (Button) binding.getRoot().findViewById(R.id.btn_add_photo);
            btn_gov_id = (Button) binding.getRoot().findViewById(R.id.btn_add_gov_id);
            btn_doc_photo = (Button) binding.getRoot().findViewById(R.id.btn_add_doc);
            client_image = (ImageView) binding.getRoot().findViewById(R.id.client_image);
            btn_next = (Button) binding.getRoot().findViewById(R.id.btn_next);

            // input bindings
            first_name = (EditText) binding.getRoot().findViewById(R.id.et_client_first_anme);
            middle_name = (EditText) binding.getRoot().findViewById(R.id.et_client_middle_name);
            last_name = (EditText) binding.getRoot().findViewById(R.id.et_client_last_name);
            mobile_no = (EditText) binding.getRoot().findViewById(R.id.et_client_mobile_no);
            nationality = (EditText) binding.getRoot().findViewById(R.id.et_nationality);
            birth_place = (EditText) binding.getRoot().findViewById(R.id.et_birthplace);
            dependents = (EditText) binding.getRoot().findViewById(R.id.et_num_of_dependents);
            gov_id_number = (EditText) binding.getRoot().findViewById(R.id.et_gov_id_num);
            document_source = (EditText) binding.getRoot().findViewById(R.id.et_doc_source);
            gender = (Spinner) binding.getRoot().findViewById(R.id.sp_gender);
            marital_status = (Spinner) binding.getRoot().findViewById(R.id.sp_marital);
            educational_status = (Spinner) binding.getRoot().findViewById(R.id.sp_educ_stat);
            govSpinner = (Spinner) binding.getRoot().findViewById(R.id.sp_gov_id_type);
            docSpinner = (Spinner) binding.getRoot().findViewById(R.id.sp_doc_type);

            btn_client_photo.setOnClickListener(this);
            btn_gov_id.setOnClickListener(this);
            btn_doc_photo.setOnClickListener(this);
            btn_next.setOnClickListener(this);

            layout1 = (ConstraintLayout)binding.getRoot().findViewById(R.id.ConstraintLayout);
            set = new ConstraintSet();

            date = (TextView) binding.getRoot().findViewById(R.id.tv_dateofbirth);
            date.setOnClickListener(this);

            sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

            String restoredText = sharedpreferences.getString("createWhat", " ");

            if (restoredText == "coMaker"){
                page_title.setText("Create Co-Maker");
            }

            genderArray = new ArrayList<>();
            maritalArray = new ArrayList<>();
            educStatArray = new ArrayList<>();
            genderIds = new ArrayList<>();
            maritalStatIds = new ArrayList<>();
            educStatIds = new ArrayList<>();


            // (1) get a reference to the spinner
  //          Spinner sp_gender = (Spinner) binding.getRoot().findViewById(R.id.sp_gender);

            // (2) create a simple static list of strings
//            List<Integer> spinnerArray = new ArrayList<>();
//            spinnerArray.add(10);
//            spinnerArray.add(11);

            // (3) create an adapter from the list
//            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
//                    getActivity(),
//                    android.R.layout.simple_spinner_item,
//                    spinnerArray
//            );

//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// (4) set the adapter on the spinner
//            spinner1.setAdapter(adapter);

            // this is the part where you assign the saved data in sharedpreference to array
            try {

                JSONArray arr_gender = new JSONArray(sharedpreferences.getString("arr_gender", " "));
                JSONArray arr_marital_status = new JSONArray(sharedpreferences.getString("arr_marital_status", " "));
                JSONArray arr_educ_attain = new JSONArray(sharedpreferences.getString("arr_educ_attain", " "));

                //populate arrays for gender values(String) and ids(Int)
                for(int i = 0; i < arr_gender.length(); i++) {
                    JSONObject jsonObject1 = arr_gender.getJSONObject(i);
                    Integer id = jsonObject1.optInt("id");
                    String name = jsonObject1.optString("name");
                    genderArray.add(name);
                    genderIds.add(id);
                }
                //setting spinner for gender
                ArrayAdapter<String> adapter_gender = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_spinner_item,
                        genderArray
                );
                gender.setAdapter(adapter_gender);

                //for marital_status
                for(int i = 0; i < arr_marital_status.length(); i++) {
                    JSONObject jsonObject1 = arr_marital_status.getJSONObject(i);
                    Integer id = jsonObject1.optInt("id");
                    String name = jsonObject1.optString("name");
                    maritalArray.add(name);
                    maritalStatIds.add(id);
                }
                ArrayAdapter<String> adapter_marital_stat = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_spinner_item,
                        maritalArray
                );
                marital_status.setAdapter(adapter_marital_stat);

                //for educational attainment
                for(int i = 0; i < arr_educ_attain.length(); i++) {
                    JSONObject jsonObject1 = arr_educ_attain.getJSONObject(i);
                    Integer id = jsonObject1.optInt("id");
                    String name = jsonObject1.optString("name");
                    educStatArray.add(name);
                    educStatIds.add(id);
                }
                ArrayAdapter<String> adapter_educ_stat = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_spinner_item,
                        educStatArray
                );
                educational_status.setAdapter(adapter_educ_stat);

                Log.i("GENDER SIZE", String.valueOf(genderArray.size()));
                Log.i("MARITAL SIZE", String.valueOf(maritalArray.size()));
                Log.i("EDUC SIZE", String.valueOf(educStatArray.size()));


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return binding.getRoot();
        }

    /**
     * This handles the functionalities of elements (e.g. buttons) set with an OnClickListener.
     * Includes invoking of a datepicker for the date field.
     * Also invokes the camera application of the device and checks/requests for permissions in accessing the device storage.
     * @param v - refers to the clicked element
     */
    @Override
    public void onClick(View v) {
            if(v == date) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            } else if(v == btn_next) {
                // Saving
                // get inputs
                String str_firstName = first_name.getText().toString();
                String str_middleName = middle_name.getText().toString();
                String str_lastName = last_name.getText().toString();
                String str_mobileNo = mobile_no.getText().toString();
                String str_nationality = nationality.getText().toString();
                String str_birthDate = date.getText().toString();
                String str_birthPlace = birth_place.getText().toString();
                String str_dependents = dependents.getText().toString();
                String str_govIdNumber = gov_id_number.getText().toString();
                String str_docSource = document_source.getText().toString();
                String str_govSpinner = govSpinner.getSelectedItem().toString();
                String str_docSpinner = docSpinner.getSelectedItem().toString();

                //get selected gender and its corresponding id
                String str_gender = gender.getSelectedItem().toString();
                int gender_index = genderArray.indexOf(str_gender);
                int gender_id = genderIds.get(gender_index);
                Log.i("SELECTED GENDER + ID", str_gender + String.valueOf(gender_id));

                //for marital status
                String str_maritalStatus = marital_status.getSelectedItem().toString();
                int marital_index = maritalArray.indexOf(str_maritalStatus);
                int marital_status_id = maritalStatIds.get(marital_index);
                Log.i("SELECTED MARITAL + ID", str_maritalStatus + String.valueOf(marital_status_id));

                //for educational stat
                String str_educStat = educational_status.getSelectedItem().toString();
                int educ_index = educStatArray.indexOf(str_educStat);
                int educ_status_id = educStatIds.get(educ_index);
                Log.i("SELECTED EDUC + ID", str_educStat + String.valueOf(educ_status_id));

                sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedpreferences.edit();

                String restoredText = sharedpreferences.getString("createWhat", " ");

                if (restoredText == "client") {
                    editor.putString("clientfirstName", str_firstName);
                    editor.putString("clientmiddleName", str_middleName);
                    editor.putString("clientlastName", str_lastName);
                    editor.putString("clientMobileNo", str_mobileNo);
                    editor.putString("clientNationality", str_nationality);
                    editor.putString("clientbirthDate", str_birthDate);
                    editor.putString("clientbirthPlace", str_birthPlace);
                    editor.putString("clientDependents", str_dependents);
                    editor.putString("clientGovIdNo", str_govIdNumber);
                    editor.putString("clientDocSource", str_docSource);
                    editor.putInt("clientGender", gender_id);
                    editor.putInt("clientMaritalStatus", marital_status_id);
                    editor.putInt("clientEducStat", educ_status_id);
                    //editor.putString("clientGovSpinner", str_govSpinner);
                    //editor.putString("clientDocSpinner", str_docSpinner);

                    if(photoFile != null) {
                        editor.putString("clientPhoto", photoFile.getAbsolutePath());
                    }
                    if(govIdFile != null) {
                        editor.putString("clientGovImage", govIdFile.getAbsolutePath());;
                    }
                    if(docFile != null) {
                        editor.putString("clientDocImage", docFile.getAbsolutePath());
                    }



                } else {
                    editor.putString("coMakerfirstName", str_firstName);
                    editor.putString("coMakermiddleName", str_middleName);
                    editor.putString("coMakerlastName", str_lastName);
                    editor.putString("coMakerMobileNo", str_mobileNo);
                    editor.putString("coMakerNationality", str_nationality);
                    editor.putString("coMakerbirthDate", str_birthDate);
                    editor.putString("coMakerbirthPlace", str_birthPlace);
                    editor.putString("coMakerDependents", str_dependents);
                    editor.putString("coMakerGovIdNo", str_govIdNumber);
                    editor.putString("coMakerDocSource", str_docSource);
                    editor.putInt("coMakerGender", gender_id);
                    editor.putInt("coMakerMaritalStatus", marital_status_id);
                    editor.putInt("coMakerEducStat", educ_status_id);
                    //editor.putString("coMakerGovSpinner", str_govSpinner);
                    //editor.putString("coMakerDocSpinner", str_docSpinner);

                    if(photoFile != null) {
                        editor.putString("coMakerPhoto", photoFile.getAbsolutePath());
                    }
                    if(govIdFile != null) {
                        editor.putString("coMakerGovImage", govIdFile.getAbsolutePath());;
                    }
                    if(docFile != null) {
                        editor.putString("coMakerDocImage", docFile.getAbsolutePath());
                    }

                }

                editor.commit();

                // turn to next page
                Navigation.findNavController(v).navigate(R.id.action_accountNewFragment_to_residenceInformationFragment);
            }else {
                if(EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA) &&
                        EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (camera_intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        // Create the File where the photo should go
                        try {
                            if(v == btn_client_photo) {
                                photoFile = createImageFile(1);
                                if (photoFile != null) {
                                    Uri photoURI = FileProvider.getUriForFile(getContext(),
                                            "com.creditsaison.loansystem.fileprovider",
                                            photoFile);
                                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                    startActivityForResult(camera_intent, client_pic_code);
                                }
                            } else if(v == btn_gov_id) {
                                govIdFile = createImageFile(2);
                                if (govIdFile != null) {
                                    Uri photoURI = FileProvider.getUriForFile(getContext(),
                                            "com.creditsaison.loansystem.fileprovider",
                                            govIdFile);
                                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                    startActivityForResult(camera_intent, 2);
                                }
                            } else {
                                docFile = createImageFile(3);
                                if (docFile != null) {
                                    Uri photoURI = FileProvider.getUriForFile(getContext(),
                                            "com.creditsaison.loansystem.fileprovider",
                                            docFile);
                                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                    startActivityForResult(camera_intent, 3);
                                }
                            }

                        } catch (Exception ex) {
                            // Error occurred while creating the File
                            Log.i("error", "error while creating the file");
                        }
                    }
                } else {
                    EasyPermissions.requestPermissions(getActivity(), getString(R.string.rationale_camera),1, perms);
                }
            }

    }


    public void onResume() {
        super.onResume();

        if(rotatedBitmap != null) {
            //Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            client_image.setImageBitmap(rotatedBitmap);
        } else {
            client_image.setImageResource(R.drawable.ic_dp_placeholder);
        }
    }


    /**
     * This function retrieves the created image file.
     * If the requestCode passed in the startActivityForResult is 1, meaning the button for client's photo is the one clicked, the image will be
     * set to the value of client_photo to be displayed in the imageview.
     * If the requestCode is 2 or 3, for gov id and other document respectively, a textview will be added to the layout to display the filename
     * of the image.
     * @param requestCode - the requestCode passed in the startActivityForResult, way to determine which startActivityForResult was called
     * @param resultCode - result code specified by startActivityForResult if the operation was successful or the operation failed.
     * @param data - the result data of the operation.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)){
            if(requestCode == 1) { //client photo
                if(resultCode == Activity.RESULT_OK) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    try {
                        checkOrientation(myBitmap);
                        client_image.setImageBitmap(rotatedBitmap);
                    } catch (IOException dfg) {
                       Log.i("rotation", "error in rotation");
                    }

                } else {
                    photoFile = null;
                    client_image.setImageResource(R.drawable.ic_dp_placeholder);
                }
            } else if(requestCode == 2 && resultCode == Activity.RESULT_OK){ //gov id
                if(text == null) {
                    text = new TextView(getContext());
                    text.setId(View.generateViewId());

                    if(govIdFile != null) {
                        govFileName = govIdFile.getAbsolutePath().substring(govIdFile.getAbsolutePath().lastIndexOf("/")+1);
                        text.setText(govFileName);
                    }

                    layout1.addView(text,0);
                    set.clone(layout1);
                    set.connect(text.getId(), ConstraintSet.TOP, R.id.cb_gov_id, ConstraintSet.BOTTOM, 0);
                    set.connect(text.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
                    set.connect(text.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
                    set.connect(text.getId(), ConstraintSet.BOTTOM, R.id.cb_doc, ConstraintSet.TOP, 0);

                    set.applyTo(layout1);
                } else {
                    if(govIdFile != null) {
                        govFileName = govIdFile.getAbsolutePath().substring(govIdFile.getAbsolutePath().lastIndexOf("/")+1);
                        text.setText(govFileName);
                    }
                }

            } else if(requestCode == 3 && resultCode == Activity.RESULT_OK) { //docu photo
                if(text1 == null) {
                    text1 = new TextView(getContext());
                    text1.setId(View.generateViewId());

                    if(docFile != null) {
                        docFileName = docFile.getAbsolutePath().substring(docFile.getAbsolutePath().lastIndexOf("/")+1);
                        text1.setText(docFileName);
                    }

                    layout1.addView(text1,0);
                    set.clone(layout1);
                    set.connect(text1.getId(), ConstraintSet.TOP, R.id.cb_doc, ConstraintSet.BOTTOM, 0);
                    set.connect(text1.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0);
                    set.connect(text1.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
                    set.connect(text1.getId(), ConstraintSet.BOTTOM, R.id.btn_submit, ConstraintSet.TOP, 0);

                    set.applyTo(layout1);
                } else {
                    if(docFile != null) {
                        docFileName = docFile.getAbsolutePath().substring(docFile.getAbsolutePath().lastIndexOf("/")+1);
                        text1.setText(docFileName);
                    }
                }

            }

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),1, Manifest.permission.READ_EXTERNAL_STORAGE);
        }

    }

    /**
     * Function that checks the orientation of the captured photo and then calls the function rotateImage to set the image to portrait.
     * @param bitmap - corresponds to the image file
     * @throws IOException
     */
    public void checkOrientation (Bitmap bitmap) throws IOException
    {

        ExifInterface ei = new ExifInterface(photoFile.getAbsolutePath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        switch(orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }
    }

    /**
     * Sets the filename of the image and the directory to where the file(s) will be stored.
     * If the photo that will be taken is a gov id or document, selected type value from the dropdowns(spinner) will be appended in the filename.
     * @param clickedButton - determiner if the file will be for the client's photo, gov_id, or other documents.
     * @return
     */
    private File createImageFile(int clickedButton)
    {
        // External sdcard location
        File mediaStorageDir = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String type = "";
        if(clickedButton == 2) {
            type = String.valueOf(govSpinner.getSelectedItem()).replaceAll("\\s+","_");;
        } else if(clickedButton == 3) {
            type = String.valueOf(docSpinner.getSelectedItem()).replaceAll("\\s+","_");;
        }

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + (type == "" ? "" : type+"_")
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    /**
     * Handles the result of the permission requests.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, AccountNewFragment.this);
    }

    /**
     * Rotates the image to a specified angle.
     * @param source - the bitmap photo to be rotated
     * @param angle - refers to how many degrees the image will be rotated
     * @return - the final(rotated) bitmap photo
     */
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


}

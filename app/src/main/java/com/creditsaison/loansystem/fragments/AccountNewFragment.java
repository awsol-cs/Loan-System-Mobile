package com.creditsaison.loansystem.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
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

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentAccountNewBinding;
import com.creditsaison.loansystem.viewmodel.AccountNewViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;
/**
 * A simple {@link Fragment} subclass.
 */
public class AccountNewFragment extends Fragment implements View.OnClickListener{

        // Define the pic id
        private static final int client_pic_code = 1;

        ConstraintLayout layout1;
        ConstraintSet set;
        TextView text, text1, date;
        Button btn_client_photo, btn_gov_id, btn_doc_photo;
        ImageView client_image;
        File photoFile = null;
        File govIdFile = null;
        File docFile = null;
        Bitmap rotatedBitmap = null;
        Spinner govSpinner, docSpinner;

        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        String govFileName, docFileName;

        DatePickerDialog datePickerDialog;

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

            btn_client_photo = (Button) binding.getRoot().findViewById(R.id.btn_add_photo);
            btn_gov_id = (Button) binding.getRoot().findViewById(R.id.btn_add_gov_id);
            btn_doc_photo = (Button) binding.getRoot().findViewById(R.id.btn_add_doc);
            client_image = (ImageView) binding.getRoot().findViewById(R.id.client_image);

            govSpinner = (Spinner) binding.getRoot().findViewById(R.id.sp_gov_id_type);
            docSpinner = (Spinner) binding.getRoot().findViewById(R.id.sp_doc_type);

            btn_client_photo.setOnClickListener(this);
            btn_gov_id.setOnClickListener(this);
            btn_doc_photo.setOnClickListener(this);

            layout1 = (ConstraintLayout)binding.getRoot().findViewById(R.id.ConstraintLayout);
            set = new ConstraintSet();

            date = (TextView) binding.getRoot().findViewById(R.id.tv_dateofbirth);
            date.setOnClickListener(this);

        return binding.getRoot();
    }

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
                                date.setText((monthOfYear + 1) + "/"
                                        + dayOfMonth + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            } else {
                if(EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA) &&
                        EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Log.i("permission", "has permissions");

                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (camera_intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        // Create the File where the photo should go
                        try {
                            if(v == btn_client_photo) {
                                Log.i("onClick", "client photo");
                                photoFile = createImageFile(1);
                                if (photoFile != null) {
                                    Log.i("Mayank", photoFile.getAbsolutePath());
                                    // Uri photoURI = Uri.fromFile(photoFile);
                                    Uri photoURI = FileProvider.getUriForFile(getContext(),
                                            "com.creditsaison.loansystem.fileprovider",
                                            photoFile);
                                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                    startActivityForResult(camera_intent, client_pic_code);
                                }
                            } else if(v == btn_gov_id) {
                                Log.i("onClick", "gov id");
                                govIdFile = createImageFile(2);
                                if (govIdFile != null) {
                                    Log.i("Mayank", govIdFile.getAbsolutePath());
                                    Uri photoURI = FileProvider.getUriForFile(getContext(),
                                            "com.creditsaison.loansystem.fileprovider",
                                            govIdFile);
                                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                    startActivityForResult(camera_intent, 2);
                                }
                            } else {
                                Log.i("onClick", "doc photo");
                                docFile = createImageFile(3);
                                if (docFile != null) {
                                    Log.i("Mayank", docFile.getAbsolutePath());
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
                    Log.i("permission", "has no camera permission");
                    EasyPermissions.requestPermissions(getActivity(), getString(R.string.rationale_camera),1, perms);
                }
            }

    }


    public void onResume(){
        super.onResume();

        if(rotatedBitmap != null) {
            Log.i("resume", photoFile.getAbsolutePath());
            //Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            client_image.setImageBitmap(rotatedBitmap);
        } else {
            Log.i("resumed", "NULL");
            client_image.setImageResource(R.drawable.ic_dp_placeholder);
        }

    }


    // This method will help to retrieve the image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i("activityResult", "entered activity result");
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, AccountNewFragment.this);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

}

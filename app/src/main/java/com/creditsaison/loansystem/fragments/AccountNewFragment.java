package com.creditsaison.loansystem.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ImageView;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentAccountNewBinding;
import com.creditsaison.loansystem.viewmodel.AccountNewViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;
/**
 * A simple {@link Fragment} subclass.
 */
public class AccountNewFragment extends Fragment {

        // Define the pic id
        private static final int client_pic_code = 1;

        // Define the button and imageview type variable
        Button btn_add_photo, btn_gov_id;
        ImageView client_image;
        File photoFile = null;

        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

            btn_add_photo = (Button) binding.getRoot().findViewById(R.id.btn_add_photo);
            btn_gov_id = (Button) binding.getRoot().findViewById(R.id.btn_add_gov_id);
            client_image = (ImageView) binding.getRoot().findViewById(R.id.client_image);

            btn_add_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(EasyPermissions.hasPermissions(getContext(), Manifest.permission.CAMERA) &&
                            EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        Log.i("permission", "has permissions");

                        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (camera_intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            // Create the File where the photo should go
                            try {
                                photoFile = createImageFile();
                                if (photoFile != null) {
                                    Log.i("Mayank", photoFile.getAbsolutePath());
                                    // Uri photoURI = Uri.fromFile(photoFile);
                                    Uri photoURI = FileProvider.getUriForFile(getContext(),
                                            "com.creditsaison.loansystem.fileprovider",
                                            photoFile);
                                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                    startActivityForResult(camera_intent, client_pic_code);
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
            });

        return binding.getRoot();
    }


    public void onResume(){
        super.onResume();

        if(photoFile != null) {
            Log.i("resume", photoFile.getAbsolutePath());
            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            client_image.setImageBitmap(myBitmap);
        } else {
            Log.i("resumed", "NULL");
        }

    }


    // This method will help to retrieve the image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i("activityResult", "entered activity result");
        if(EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)){
            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            client_image.setImageBitmap(myBitmap);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),1, Manifest.permission.READ_EXTERNAL_STORAGE);
        }

    }


    private File createImageFile()
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
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, AccountNewFragment.this);
    }

}

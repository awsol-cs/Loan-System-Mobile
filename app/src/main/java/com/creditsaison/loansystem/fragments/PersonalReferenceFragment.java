package com.creditsaison.loansystem.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentPersonalReferenceBinding;
import com.creditsaison.loansystem.viewmodel.PersonalReferenceViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PersonalReferenceFragment extends Fragment {

    SharedPreferences sharedpreferences;
    EditText reference_name, relationship, reference_employer, contact_no, ref_mobile,
            related_officer_name, officer_contact_no;
    Spinner relationship_to_staff;
    CheckBox related_to_staff;
    Button btn_next;

    //arrays for dropdown values and their corresponding ids
    List<String> relationshipArray, isRelatedArray;
    List<Integer> relationshipIds, isRelatedIds;

    private FragmentPersonalReferenceBinding binding;
    private PersonalReferenceViewModel viewModel;

    public PersonalReferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(PersonalReferenceViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPersonalReferenceBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);


        btn_next = (Button) binding.getRoot().findViewById(R.id.btn_next);

        // bind elements
        reference_name = (EditText) binding.getRoot().findViewById(R.id.et_reference_name);
        relationship = (EditText) binding.getRoot().findViewById(R.id.et_relationship);
        reference_employer = (EditText) binding.getRoot().findViewById(R.id.et_reference_employer);
        contact_no = (EditText) binding.getRoot().findViewById(R.id.et_contact_no);
        ref_mobile = (EditText) binding.getRoot().findViewById(R.id.et_ref_mobile);
        related_officer_name = (EditText) binding.getRoot().findViewById(R.id.et_related_officer_name);
        officer_contact_no = (EditText) binding.getRoot().findViewById(R.id.et_officer_contact_no);
        relationship_to_staff = (Spinner) binding.getRoot().findViewById(R.id.sp_relationship_to_staff);
        related_to_staff = (CheckBox) binding.getRoot().findViewById(R.id.cb_related_to_staff);

        sharedpreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        relationshipArray = new ArrayList<>();
        relationshipIds = new ArrayList<>();
        isRelatedArray = new ArrayList<>();
        isRelatedIds = new ArrayList<>();

        // this is the part where you assign the saved data in sharedpreference to array
        try {

            JSONArray arr_relationship = new JSONArray(sharedpreferences.getString("arr_relationship", " "));
            JSONArray arr_isRelated = new JSONArray(sharedpreferences.getString("arr_isRelated", " "));

            //populate arrays for relationship type values(String) and ids(Int)
            for(int i = 0; i < arr_relationship.length(); i++) {
                JSONObject jsonObject1 = arr_relationship.getJSONObject(i);
                Integer id = jsonObject1.optInt("id");
                String name = jsonObject1.optString("name");
                relationshipArray.add(name);
                relationshipIds.add(id);
            }
            //setting spinner for gender
            ArrayAdapter<String> adapter_relationship = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_spinner_item,
                    relationshipArray
            );
            relationship_to_staff.setAdapter(adapter_relationship);

            for(int i = 0; i < arr_isRelated.length(); i++) {
                JSONObject jsonObject1 = arr_isRelated.getJSONObject(i);
                Integer id = jsonObject1.optInt("id");
                String name = jsonObject1.optString("name");
                isRelatedArray.add(name);
                isRelatedIds.add(id);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // start of click event
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save
                // get inputs
                String str_referenceName = reference_name.getText().toString();
                String str_relationship = relationship.getText().toString();
                String str_referenceEmployer = reference_employer.getText().toString();
                String str_contactNo = contact_no.getText().toString();
                String str_refMobile = ref_mobile.getText().toString();
                String str_relatedOfficerName = related_officer_name.getText().toString();
                String str_officerContactNo = officer_contact_no.getText().toString();

                //getting selected values from dropdowns and its corresponding ids
                //for relationship to staff
                String str_relationshipToStaff = relationship_to_staff.getSelectedItem().toString();
                int rel_index = relationshipArray.indexOf(str_relationshipToStaff);
                int relationship_id = relationshipIds.get(rel_index);


                boolean cbRelatedtoStaff = related_to_staff.isChecked();
                String str_isRelated;

                if (cbRelatedtoStaff){
                    str_isRelated = "Yes";
                } else {
                    str_isRelated = "No";
                }

                int isRelated_index = isRelatedArray.indexOf(str_isRelated);
                int related_id = isRelatedIds.get(isRelated_index);


                SharedPreferences.Editor editor = sharedpreferences.edit();

                String restoredText = sharedpreferences.getString("createWhat", " ");

                if (restoredText == "client") {
                    editor.putString("clientRefName", str_referenceName);
                    editor.putString("clientRefRelationship", str_relationship);
                    editor.putString("clientRefEmployer", str_referenceEmployer);
                    editor.putString("clientRefContactNo", str_contactNo);
                    editor.putString("clientRefMobile", str_refMobile);
                    editor.putString("clientRelatedOfficerName", str_relatedOfficerName);
                    editor.putString("clientOfficerContactNo", str_officerContactNo);
                    editor.putInt("clientRelationshipToStaff", relationship_id);
                    editor.putInt("clientIsRelated", related_id);

                    editor.commit();

                    // turn to next page
                    Navigation.findNavController(v).navigate(R.id.action_personalReferenceFragment_to_loanApplicationFragment);
                } else {
                    editor.putString("coMakerRefName", str_referenceName);
                    editor.putString("coMakerRefRelationship", str_relationship);
                    editor.putString("coMakerRefEmployer", str_referenceEmployer);
                    editor.putString("coMakerRefContactNo", str_contactNo);
                    editor.putString("coMakerRefMobile", str_refMobile);
                    editor.putString("coMakerRelatedOfficerName", str_relatedOfficerName);
                    editor.putString("coMakerOfficerContactNo", str_officerContactNo);
                    editor.putInt("coMakerRelationshipToStaff", relationship_id);
                    editor.putInt("coMakerIsRelated", related_id);

                    editor.commit();

                    // turn to next page
                    Navigation.findNavController(v).navigate(R.id.action_personalReferenceFragment_to_termsConditionFragment);
                }

            }
        });
        // end of click event

        return binding.getRoot();
    }

}

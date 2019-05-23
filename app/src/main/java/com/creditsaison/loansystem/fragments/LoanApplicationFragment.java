package com.creditsaison.loansystem.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentLoanApplicationBinding;
import com.creditsaison.loansystem.viewmodel.LoanApplicationViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanApplicationFragment extends Fragment implements View.OnClickListener {

    private FragmentLoanApplicationBinding binding;
    private LoanApplicationViewModel viewModel;

    TextView submissionDate, disbursementDate;
    DatePickerDialog datePickerDialog;

    public LoanApplicationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoanApplicationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoanApplicationBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        submissionDate = (TextView) binding.getRoot().findViewById(R.id.tv_submission_date);
        String date = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault()).format(new Date());
        submissionDate.setText(date);
        submissionDate.setOnClickListener(this);

        disbursementDate = (TextView) binding.getRoot().findViewById(R.id.tv_disbursementon_date);
        disbursementDate.setOnClickListener(this);

        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        // date picker dialog
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // set day of month , month and year value in the textview
                    ((TextView) v).setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                }

        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

}

package com.creditsaison.loansystem.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentPromiNoteBinding;
import com.creditsaison.loansystem.viewmodel.PromissoryViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class PromissoryFragment extends Fragment implements View.OnClickListener {


    SharedPreferences sp;
    ImageView signImage;
    Button btn_agree;

    private FragmentPromiNoteBinding binding;
    private PromissoryViewModel viewModel;

    public PromissoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(PromissoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPromiNoteBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        signImage = (ImageView) binding.getRoot().findViewById(R.id.signature);


        return binding.getRoot();
    }

    @Override
    public void onResume(){
        super.onResume();

        btn_agree = (Button) getActivity().findViewById(R.id.btn_agree3);
        btn_agree.setOnClickListener(this);

        sp = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("currentSign", "promissoryNote");
        editor.commit();

        String restoredText = sp.getString("promNoteImg", " ");

        if (restoredText != "default"){
            String image_path = restoredText;
            Bitmap bitmap = BitmapFactory.decodeFile(image_path);
            signImage.setImageBitmap(bitmap);
            btn_agree.setEnabled(true);
        } else {
            signImage.setImageResource(R.drawable.signature_logo);
            btn_agree.setEnabled(false);
        }
    }


    @Override
    public void onClick(View v) {
        sp = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        String restoredText = sp.getString("termsConditionImg", " ");

        JSONObject client = new JSONObject();
        JSONObject kyc = new JSONObject();
        JSONObject loan = new JSONObject();
        JSONObject coMaker = new JSONObject();
        JSONObject coMakerInfo = new JSONObject();
        JSONObject coMakerKyc = new JSONObject();
        JSONObject image = new JSONObject();
        JSONObject documents = new JSONObject();
        JSONObject signatures = new JSONObject();
        JSONObject finalObject = new JSONObject();


        JSONObject client1 = new JSONObject();
        JSONObject kyc1 = new JSONObject();
        JSONObject loan1 = new JSONObject();
        JSONObject coMaker1 = new JSONObject();
        JSONObject coMakerInfo1 = new JSONObject();
        JSONObject coMakerKyc1 = new JSONObject();

        try {
            //client
            client.put("firstname", sp.getString("clientfirstName", " "));
            client.put("middlename", sp.getString("clientmiddleName", " "));
            client.put("lastname", sp.getString("clientlastName", " "));
            client.put("mobile_no", sp.getString("clientMobileNo", " "));
            client.put("gender", sp.getString("clientGender", " "));
            client.put("nationality", sp.getString("clientNationality", " "));
            client.put("date_of_birth", sp.getString("clientbirthDate", " "));
            client.put("birthplace", sp.getString("clientbirthPlace", " "));
            client.put("dependents", sp.getString("clientDependents", null));
            client.put("marital_status", sp.getString("clientMaritalStatus", " "));
            client.put("educational_status", sp.getString("clientEducStat", " "));
            //client.put("idNumber", sp.getString("clientGovIdNo", " "));
            //client.put("documentSource", sp.getString("clientDocSource", " "));
            //client.put("id", sp.getString("clientGovSpinner", " "));
            //client.put("id", sp.getString("clientDocSpinner", " "));


            // client residence
            kyc.put("addressType", sp.getString("clientAddressType", " "));
            kyc.put("street", sp.getString("clientStreet", " "));
            kyc.put("address_line_1", sp.getString("clientAddressLine1", " "));
            kyc.put("address_line_2", sp.getString("clientAddressLine2", " "));
            kyc.put("address_line_3", sp.getString("clientAddressLine3", " "));
            kyc.put("city", sp.getString("clientCity", " "));
            kyc.put("state_province", sp.getString("clientStateProvince", " "));
            kyc.put("postal_code", sp.getString("clientPostalCode", " "));
            kyc.put("residence_owner", sp.getString("clientResidenceOwner", " "));

            // client employment
            kyc.put("employment_type", sp.getString("clientEmploymentType", " "));
            kyc.put("other_employment", sp.getString("clientEmploymentOthers", " "));
            kyc.put("selfEmploymentType", sp.getString("clientSelfEmployed", " "));
            kyc.put("operation_years", sp.getString("clientOperationYears", " "));
            kyc.put("present_employer", sp.getString("clientPresentEmployer", " "));
            kyc.put("business_nature", sp.getString("clientBusinessNature", " "));
            kyc.put("office_address", sp.getString("clientOfficeAddress", " "));
            kyc.put("office_phone", sp.getString("clientOfficePhone", " "));
            kyc.put("office_mobile", sp.getString("clientOfficeMobile", " "));
            kyc.put("local_no", sp.getString("clientLocalNo", " "));
            kyc.put("fax_no", sp.getString("clientFaxNo", " "));
            kyc.put("email_ad", sp.getString("clientEmailAddress", " "));
            kyc.put("position", sp.getString("clientPosition", " "));
            kyc.put("gross_income", sp.getString("clientGrossIncome", " "));
            kyc.put("other_income", sp.getString("clientOtherIncome", " "));
            kyc.put("previous_employer", sp.getString("clientPreviousEmployer", " "));
            kyc.put("previous_employer_office", sp.getString("clientPreviousEmployerOffice", " "));
            kyc.put("years_with_present_employer", sp.getString("clientPresentEmployerYears", " "));
            kyc.put("years_with_previous_employer", sp.getString("clientPreviousEmployerYears", " "));

            // client reference
            kyc.put("reference_name", sp.getString("clientRefName", " "));
            kyc.put("relationship", sp.getString("clientRefRelationship", " "));
            kyc.put("employer", sp.getString("clientRefEmployer", " "));
            kyc.put("contact_no", sp.getString("clientRefContactNo", " "));
            kyc.put("mobile_no", sp.getString("clientRefMobile", " "));
            kyc.put("isRelatedToStaff", sp.getString("clientIsRelated", " "));
            kyc.put("staff_name", sp.getString("clientRelatedOfficerName", " "));
            kyc.put("staff_contact", sp.getString("clientOfficerContactNo", " "));
            kyc.put("staff_relationship", sp.getString("clientRelationshipToStaff", " "));


            //loan
            loan.put("product", sp.getString("LoanProduct", " "));
            loan.put("loan_purpose", sp.getString("LoanPurpose", " "));
            loan.put("submittedon_date", sp.getString("LoanSubmissionDate", " "));
            loan.put("disbursedon_date", sp.getString("LoanDisbursement", " "));
            loan.put("principal_amount", sp.getString("LoanPrincipal", " "));

            coMakerInfo.put("firstname", sp.getString("coMakerfirstName", " "));
            coMakerInfo.put("middlename", sp.getString("coMakermiddleName", " "));
            coMakerInfo.put("lastname", sp.getString("coMakerlastName", " "));
            coMakerInfo.put("mobile_no", sp.getString("coMakerMobileNo", " "));
            coMakerInfo.put("gender", sp.getString("coMakerGender", " "));
            coMakerInfo.put("nationality", sp.getString("coMakerNationality", " "));
            coMakerInfo.put("birthdate", sp.getString("coMakerbirthDate", " "));
            coMakerInfo.put("birthplace", sp.getString("coMakerbirthPlace", " "));
            coMakerInfo.put("dependents", sp.getString("coMakerDependents", null));
            coMakerInfo.put("marital_status", sp.getString("coMakerMaritalStatus", " "));
            coMakerInfo.put("educational_status", sp.getString("coMakerEducStat", " "));
            //coMakerInfo.put("id", sp.getString("coMakerGovIdNo", " "));
            //coMakerInfo.put("id", sp.getString("coMakerDocSource", " "));
            //coMakerInfo.put("id", sp.getString("coMakerGovSpinner", " "));
            //coMakerInfo.put("id", sp.getString("coMakerDocSpinner", " "));

            //comaker residence
            coMakerKyc.put("address_type", sp.getString("coMakerAddressType", " "));
            coMakerKyc.put("street", sp.getString("coMakerStreet", " "));
            coMakerKyc.put("address_line_1", sp.getString("coMakerAddressLine1", " "));
            coMakerKyc.put("address_line_2", sp.getString("coMakerAddressLine2", " "));
            coMakerKyc.put("address_line_3", sp.getString("coMakerAddressLine3", " "));
            coMakerKyc.put("city", sp.getString("coMakerCity", " "));
            coMakerKyc.put("state_province", sp.getString("coMakerStateProvince", " "));
            coMakerKyc.put("postal_code", sp.getString("coMakerPostalCode", " "));
            coMakerKyc.put("residence_owner", sp.getString("coMakerResidenceOwner", " "));

            // comaker employment
            coMakerKyc.put("employment_type", sp.getString("coMakerEmploymentType", " "));
            coMakerKyc.put("other_employment", sp.getString("coMakerEmploymentOthers", " "));
            coMakerKyc.put("selfEmploymentType", sp.getString("coMakerSelfEmployed", " "));
            coMakerKyc.put("operation_years", sp.getString("coMakerOperationYears", " "));
            coMakerKyc.put("present_employer", sp.getString("coMakerPresentEmployer", " "));
            coMakerKyc.put("business_nature", sp.getString("coMakerBusinessNature", " "));
            coMakerKyc.put("office_address", sp.getString("coMakerOfficeAddress", " "));
            coMakerKyc.put("office_phone", sp.getString("coMakerOfficePhone", " "));
            coMakerKyc.put("office_mobile", sp.getString("coMakerOfficeMobile", " "));
            coMakerKyc.put("local_no", sp.getString("coMakerLocalNo", " "));
            coMakerKyc.put("fax_no", sp.getString("coMakerFaxNo", " "));
            coMakerKyc.put("email_ad", sp.getString("coMakerEmailAddress", " "));
            coMakerKyc.put("position", sp.getString("coMakerPosition", " "));
            coMakerKyc.put("gross_income", sp.getString("coMakerGrossIncome", " "));
            coMakerKyc.put("other_income", sp.getString("coMakerOtherIncome", " "));
            coMakerKyc.put("previous_employer", sp.getString("coMakerPreviousEmployer", " "));
            coMakerKyc.put("previous_employer_office", sp.getString("coMakerPreviousEmployerOffice", " "));
            coMakerKyc.put("years_with_present_employer", sp.getString("coMakerPresentEmployerYears", " "));
            coMakerKyc.put("years_with_previous_employer", sp.getString("coMakerPreviousEmployerYears", " "));

            // comaker reference
            coMakerKyc.put("reference_name", sp.getString("coMakerRefName", " "));
            coMakerKyc.put("relationship", sp.getString("coMakerRefRelationship", " "));
            coMakerKyc.put("employer", sp.getString("coMakerRefEmployer", " "));
            coMakerKyc.put("contact_no", sp.getString("coMakerRefContactNo", " "));
            coMakerKyc.put("mobile_no", sp.getString("coMakerRefMobile", " "));
            coMakerKyc.put("isRelatedToStaff", sp.getString("coMakerRelatedOfficerName", " "));
            coMakerKyc.put("staff_name", sp.getString("coMakerOfficerContactNo", " "));
            coMakerKyc.put("staff_contact", sp.getString("coMakerRelationshipToStaff", " "));
            coMakerKyc.put("staff_relationship", sp.getString("coMakerIsRelated", " "));

            //final comaker object
            coMaker.put("info", coMakerInfo);
            coMaker.put("kyc", coMakerKyc);

            //images - client and comaker photo
            if(sp.contains("clientPhoto")) {
                String clientImagePath = sp.getString("clientPhoto", null);
                Bitmap myBitmap = BitmapFactory.decodeFile(clientImagePath);
                String str_clientImage = convertImage(myBitmap, 1);
                image.put("clientImage", str_clientImage);
            }

            if(sp.contains("coMakerPhoto")) {
                String coMakerImagePath = sp.getString("coMakerPhoto", null);
                Bitmap myBitmap = BitmapFactory.decodeFile(coMakerImagePath);
                String str_coMakerImage = convertImage(myBitmap, 1);
                image.put("coMakerImage", str_coMakerImage);
            }

            //documents
            if(sp.contains("clientGovImage")) {
                String clientGovImagePath = sp.getString("clientGovImage", null);
                Bitmap myBitmap = BitmapFactory.decodeFile(clientGovImagePath);
                String str_clientGovIdImage = convertImage(myBitmap, 1);
                documents.put("clientGovId", str_clientGovIdImage);
            }

            if(sp.contains("clientDocImage")) {
                String clientDocImagePath = sp.getString("clientDocImage", null);
                Bitmap myBitmap = BitmapFactory.decodeFile(clientDocImagePath);
                String str_clientDocImage = convertImage(myBitmap, 1);
                documents.put("clientDocument", str_clientDocImage);
            }

            if(sp.contains("coMakerGovImage")) {
                String coMakerGovImagePath = sp.getString("coMakerGovImage", null);
                Bitmap myBitmap = BitmapFactory.decodeFile(coMakerGovImagePath);
                String str_coMakerGovIdImage = convertImage(myBitmap, 1);
                documents.put("comakerGovId", str_coMakerGovIdImage);
            }

            if(sp.contains("coMakerDocImage")) {
                String coMakerDocImagePath = sp.getString("coMakerDocImage", null);
                Bitmap myBitmap = BitmapFactory.decodeFile(coMakerDocImagePath);
                String str_coMakerDocImage = convertImage(myBitmap, 1);
                documents.put("comakerDocument", str_coMakerDocImage);
            }

            //signatures
            String termsConditionImgPath = sp.getString("termsConditionImg", null);
            String loanAgreementImgPath = sp.getString("loanAgreementImg", null);
            String promiNoteImgPath = sp.getString("promNoteImg", null);

            Bitmap termsSignBitmap = BitmapFactory.decodeFile(termsConditionImgPath);
            Bitmap loanAgreementSignBitmap = BitmapFactory.decodeFile(loanAgreementImgPath);
            Bitmap promiNoteSignBitmap = BitmapFactory.decodeFile(promiNoteImgPath);

            String str_termsConditionSign = convertImage(termsSignBitmap, 2);
            String str_loanAgreementSign = convertImage(loanAgreementSignBitmap, 2);
            String str_promiNoteSign = convertImage(promiNoteSignBitmap, 2);

            signatures.put("termsConditionSign", str_termsConditionSign);
            signatures.put("loanAgreementSign", str_loanAgreementSign);
            signatures.put("promiNoteSign", str_promiNoteSign);


            //final object to be sent to API
//            finalObject.put("client", client);
//            finalObject.put("kyc", kyc);
//            finalObject.put("loan", loan);
//            finalObject.put("comaker", coMaker);
//            finalObject.put("image", image);
//            finalObject.put("documents", documents);
//            finalObject.put("signature", signatures);



            client1.put("activationDate", "06 May 2019");
            client1.put("active", "true");
            client1.put("dateFormat", "dd MMMM yyyy");
            client1.put("firstname", "trial");
            client1.put("lastname", "trial");
            client1.put("officeId", 1);
            client1.put("locale", "en");
            client1.put("savingsProductId", null);
            client1.put("submittedOnDate", "06 May 2019");

            kyc1.put("nationality", "Filipino");
            kyc1.put("noOfDependents", 0);
            kyc1.put("othersEducationalAttainment", "College");
            kyc1.put("placeOfBirth", "trialate");
            kyc1.put("motherMaidenName", "trial");
            kyc1.put("rentedResidenceOwnership", 10000);
            kyc1.put("otherEmployment", "Foreman");
            kyc1.put("yearsInOperation", 1);
            kyc1.put("noOfEmployees", 1);
            kyc1.put("nameOfPresentEmployerBusiness", "AWS");
            kyc1.put("nameReference", "Mother");
            kyc1.put("grossAnnualIncome", 100000);
            kyc1.put("otherIncome", 100000);
            kyc1.put("yearsWithPresentEmployer", 1);
            kyc1.put("locale", "en");

            loan1.put("allowPartialPeriodInterestCalcualtion", false);
            loan1.put("amortizationType", 1);
            loan1.put("expectedDisbursementDate", "07 May 2019");
            loan1.put("interestCalculationPeriodType", 1);
            loan1.put("interestRatePerPeriod",  2);
            loan1.put("interestType", 0);
            loan1.put("isEqualAmortization", false);
            loan1.put("loanTermFrequency", 6);
            loan1.put("loanTermFrequencyType", 2);
            loan1.put("loanType", "individual");
            loan1.put("numberOfRepayments", 6);
            loan1.put("principal", 10000);
            loan1.put("productId", 1);
            loan1.put("repaymentEvery", 1);
            loan1.put("locale", "en");
            loan1.put("repaymentFrequencyType", 2);
            loan1.put("submittedOnDate", "07 May 2019");
            loan1.put("transactionProcessingStrategyId", 1);

            finalObject.put("client", client1);
            finalObject.put("kyc", kyc1);
            finalObject.put("loan", loan1);



            sendPost(finalObject);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //conversion of an image to Base64
    public static String convertImage(Bitmap image, int type) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        image.compress((type == 1 ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG), 100, bos);
        byte[] byte_arr = bos.toByteArray();

        String file = Base64.encodeToString(byte_arr, Base64.DEFAULT);

        return file;
    }



    public void sendPost(JSONObject finalData) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String uNameandPword = "mifos:password";
                    String basicAutoPayload = "Basic " + Base64.encodeToString(uNameandPword.getBytes(), Base64.DEFAULT);
                    URL auth = new URL("https://192.168.227.159/fineract-provider/api/v1/cs_clients");
 //                   String url = "https://192.168.227.159/fineract-provider/api/v1/clients";
                    trustAllHosts();

//                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                            new Response.Listener<String>(){
//
//                                @Override
//                                public void onResponse(String response) {
//                                    Log.i("STATUS", response);
//                                }
//                            }, new Response.ErrorListener() {
//
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.i("STATUS", error.toString());
//                        }
//                    });
//
//                    requestQueue.add(stringRequest);




//                    HttpURLConnection authConn = null;
//                    if (auth.getProtocol().toLowerCase().equals("https")) {
//                        trustAllHosts();
//                        HttpsURLConnection https = (HttpsURLConnection) auth.openConnection();
//                        https.setHostnameVerifier(DO_NOT_VERIFY);
//                        authConn = https;
//                    } else {
//                        authConn = (HttpURLConnection) auth.openConnection();
//                    }
//
//                    authConn.setRequestMethod("POST");
//                    authConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//                    authConn.setRequestProperty("Fineract-Platform-TenantId", "default");
//                    authConn.setRequestProperty("Accept","application/json");
//                    authConn.setRequestProperty("Authorization",basicAutoPayload);
//                    authConn.setDoOutput(true);
//                    authConn.setDoInput(true);
//                    Log.i("JSON", finalData.toString());
//                    DataOutputStream os = new DataOutputStream(authConn.getOutputStream());
//                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
//                    os.writeBytes(finalData.toString());
//
//
//                    Log.i("STATUS", String.valueOf(authConn.getResponseCode()));
//                    Log.i("MSG" , authConn.getResponseMessage());
//
//                    authConn.disconnect();


                    URL url = new URL("https://192.168.227.159/fineract-provider/api/v1/cs_clients");
                    //HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    HttpURLConnection conn = null;

                    if (url.getProtocol().toLowerCase().equals("https")) {
                        trustAllHosts();
                        HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                        https.setHostnameVerifier(DO_NOT_VERIFY);
                        conn = https;
                    } else {
                        conn = (HttpURLConnection) url.openConnection();
                    }

                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Fineract-Platform-TenantId", "default");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setRequestProperty("Authorization",basicAutoPayload);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    Log.i("JSON", finalData.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(finalData.toString());

                    os.flush();
                    os.close();


                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    // always verify the host - dont check for certificate
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * Trust every server - dont check for any certificate
     */
    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

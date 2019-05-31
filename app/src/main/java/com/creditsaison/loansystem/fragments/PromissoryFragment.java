package com.creditsaison.loansystem.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.creditsaison.loansystem.R;
import com.creditsaison.loansystem.databinding.FragmentPromiNoteBinding;
import com.creditsaison.loansystem.viewmodel.PromissoryViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

import static java.nio.charset.StandardCharsets.UTF_8;

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

        JSONObject client = new JSONObject();
        JSONObject addressObject = new JSONObject();
        JSONArray addressArray = new JSONArray();
        JSONObject kyc = new JSONObject();
        JSONObject loan = new JSONObject();
        JSONObject coMaker = new JSONObject();
        JSONObject coMakerInfo = new JSONObject();
        JSONObject coMakerKyc = new JSONObject();
        JSONObject image = new JSONObject();
        JSONObject documents = new JSONObject();
        JSONObject signatures = new JSONObject();
        JSONObject finalObject = new JSONObject();

        try {
            //client
            client.put("activationDate", sp.getString("LoanSubmissionDate", null));
            client.put("active", true);
            client.put("dateFormat", "M/dd/yyyy");
            client.put("firstname", sp.getString("clientfirstName", " "));
            client.put("middlename", sp.getString("clientmiddleName", " "));
            client.put("lastname", sp.getString("clientlastName", " "));
            client.put("mobileNo", sp.getString("clientMobileNo", " "));
            client.put("genderId", sp.getInt("clientGender", 0));
            client.put("genderId", 38);
            client.put("dateOfBirth", sp.getString("clientbirthDate", " "));
            client.put("submittedOnDate", sp.getString("LoanSubmissionDate", null));
            client.put("locale", "en");
            client.put("officeId", 1);

            //client address
            addressObject.put("addressLine1", sp.getString("clientAddressLine1", ""));
            addressObject.put("addressLine2", sp.getString("clientAddressLine2", ""));
            addressObject.put("addressLine3", sp.getString("clientAddressLine3", ""));
            addressObject.put("addressTypeId", sp.getInt("clientAddressType", 0));
            addressObject.put("city", sp.getString("clientCity", ""));
            addressObject.put("countryId", sp.getInt("clientCountry", 0));
            addressObject.put("postalCode", sp.getString("clientPostalCode", "1234"));
            addressObject.put("stateProvinceId", sp.getInt("clientStateProvince", 0));
            addressObject.put("street", sp.getString("clientStreet", ""));
            addressArray.put(addressObject);
            client.put("address", addressArray);

            //client.put("idNumber", sp.getString("clientGovIdNo", " "));
            //client.put("documentSource", sp.getString("clientDocSource", " "));
            //client.put("id", sp.getString("clientGovSpinner", " "));
            //client.put("id", sp.getString("clientDocSpinner", " "));

            kyc.put("nationality", sp.getString("clientNationality", " "));
            kyc.put("placeOfBirth", sp.getString("clientbirthPlace", " "));
            kyc.put("noOfDependents", sp.getString("clientDependents", null));
            kyc.put("maritalStatusId", sp.getInt("clientMaritalStatus", 0));
            kyc.put("educationalAttainmentId", sp.getInt("clientEducStat", 0));
            kyc.put("residenceOwnershipId", sp.getInt("clientResidenceOwner", 0));

            // client employment
            kyc.put("employmentId", sp.getInt("clientEmploymentType", 0));
            kyc.put("other_employment", sp.getString("clientEmploymentOthers", " "));
            kyc.put("selfEmployedId", sp.getInt("clientSelfEmployed", 0));
            kyc.put("yearsInOperation", sp.getString("clientOperationYears", " "));
            kyc.put("nameOfPresentEmployerBusiness", sp.getString("clientPresentEmployer", " "));
            kyc.put("natureOfBusiness", sp.getString("clientBusinessNature", " "));
            kyc.put("officeAddress", sp.getString("clientOfficeAddress", " "));
            kyc.put("officePhone", sp.getString("clientOfficePhone", " "));
            kyc.put("officeMobilePhone", sp.getString("clientOfficeMobile", " "));
            kyc.put("officeLocalNumber", sp.getString("clientLocalNo", " "));
            kyc.put("officeFaxNumber", sp.getString("clientFaxNo", " "));
            kyc.put("officeEmailAddress", sp.getString("clientEmailAddress", " "));
            kyc.put("titleOrPosition", sp.getString("clientPosition", " "));
            kyc.put("grossAnnualIncome", sp.getString("clientGrossIncome", " "));
            kyc.put("otherIncome", sp.getString("clientOtherIncome", " "));
            kyc.put("nameOfPreviousEmployer", sp.getString("clientPreviousEmployer", " "));
            kyc.put("officeAddressPrevious", sp.getString("clientPreviousEmployerOffice", " "));
            kyc.put("yearsWithPresentEmployer", sp.getString("clientPresentEmployerYears", " "));
            kyc.put("yearsWithPreviousEmployer", sp.getString("clientPreviousEmployerYears", " "));

            // client reference
            kyc.put("nameReference", sp.getString("clientRefName", " "));
            kyc.put("relationshipReference", sp.getString("clientRefRelationship", " "));
            kyc.put("employerReference", sp.getString("clientRefEmployer", " "));
            kyc.put("contactNumberReference", sp.getString("clientRefContactNo", " "));
            kyc.put("mobileReference", sp.getString("clientRefMobile", " "));
            kyc.put("relatedToOfficerId", sp.getInt("clientIsRelated", 0));
            kyc.put("nameOfOfficer", sp.getString("clientRelatedOfficerName", " "));
            kyc.put("contactNumberOfficer", sp.getString("clientOfficerContactNo", " "));
            kyc.put("relationshipOfOfficerId", sp.getInt("clientRelationshipToStaff", 0));
            kyc.put("locale", "en");

            //loan
            loan.put("dateFormat", "M/dd/yyyy");
            loan.put("locale", "en");
            loan.put("allowPartialPeriodInterestCalcualtion", false);
            loan.put("amortizationType", "1");
            loan.put("interestCalculationPeriodType", 1);
            loan.put("interestRatePerPeriod", 1);
            loan.put("interestType", 0);
            loan.put("isEqualAmortization", false);
            loan.put("loanTermFrequency", 6);
            loan.put("loanTermFrequencyType", 2);
            loan.put("loanType", "individual");
            loan.put("numberOfRepayments", 6);
            loan.put("productId", 1);
            //loan.put("loan_purpose", sp.getString("LoanPurpose", " "));
            loan.put("submittedOnDate", sp.getString("LoanSubmissionDate", " "));
            loan.put("expectedDisbursementDate", sp.getString("LoanDisbursement", " "));
            loan.put("principal", sp.getString("LoanPrincipal", " "));
            loan.put("repaymentEvery", 1);
            loan.put("repaymentFrequencyType", 2);
            loan.put("transactionProcessingStrategyId", 1);


            coMakerInfo.put("dateFormat", "M/dd/yyyy");
            coMakerInfo.put("firstname", sp.getString("coMakerfirstName", " "));
            coMakerInfo.put("middlename", sp.getString("coMakermiddleName", " "));
            coMakerInfo.put("lastname", sp.getString("coMakerlastName", " "));
            coMakerInfo.put("mobileNo", sp.getString("coMakerMobileNo", " "));
            coMakerInfo.put("genderId", sp.getInt("coMakerGender", 0));
            coMakerInfo.put("dateOfBirth", sp.getString("coMakerbirthDate", " "));
            coMakerInfo.put("locale", "en");

            //coMakerInfo.put("id", sp.getString("coMakerGovIdNo", " "));
            //coMakerInfo.put("id", sp.getString("coMakerDocSource", " "));
            //coMakerInfo.put("id", sp.getString("coMakerGovSpinner", " "));
            //coMakerInfo.put("id", sp.getString("coMakerDocSpinner", " "));

            coMakerKyc.put("locale", "en");
            coMakerKyc.put("nationality", sp.getString("coMakerNationality", " "));
            coMakerKyc.put("placeOfBirth", sp.getString("coMakerbirthPlace", " "));
            coMakerKyc.put("dependents", sp.getString("coMakerDependents", null));
            coMakerKyc.put("maritalStatusId", sp.getInt("coMakerMaritalStatus", 0));
            coMakerKyc.put("educationalAttainmentId", sp.getInt("coMakerEducStat", 0));

            //comaker residence
//            coMakerKyc.put("address_type", sp.getString("coMakerAddressType", " "));
//            coMakerKyc.put("street", sp.getString("coMakerStreet", " "));
//            coMakerKyc.put("address_line_1", sp.getString("coMakerAddressLine1", " "));
//            coMakerKyc.put("address_line_2", sp.getString("coMakerAddressLine2", " "));
//            coMakerKyc.put("address_line_3", sp.getString("coMakerAddressLine3", " "));
//            coMakerKyc.put("city", sp.getString("coMakerCity", " "));
//            coMakerKyc.put("state_province", sp.getString("coMakerStateProvince", " "));
//            coMakerKyc.put("postal_code", sp.getString("coMakerPostalCode", " "));
//            coMakerKyc.put("residenceOwnershipId", sp.getString("coMakerResidenceOwner", " "));

            // comaker employment
            coMakerKyc.put("employmentId", sp.getInt("coMakerEmploymentType", 0));
            coMakerKyc.put("other_employment", sp.getString("coMakerEmploymentOthers", " "));
            coMakerKyc.put("selfEmploymentType", sp.getInt("coMakerSelfEmployed", 0));
            coMakerKyc.put("operation_years", sp.getString("coMakerOperationYears", " "));
            coMakerKyc.put("nameOfPresentEmployerBusiness", sp.getString("coMakerPresentEmployer", " "));
            coMakerKyc.put("natureOfBusiness", sp.getString("coMakerBusinessNature", " "));
            coMakerKyc.put("officeAddress", sp.getString("coMakerOfficeAddress", " "));
            coMakerKyc.put("officePhone", sp.getString("coMakerOfficePhone", " "));
            coMakerKyc.put("officeMobilePhone", sp.getString("coMakerOfficeMobile", " "));
            coMakerKyc.put("officeLocalNumber", sp.getString("coMakerLocalNo", " "));
            coMakerKyc.put("officeFaxNumber", sp.getString("coMakerFaxNo", " "));
            coMakerKyc.put("officeEmailAddress", sp.getString("coMakerEmailAddress", " "));
            coMakerKyc.put("titleOrPosition", sp.getString("coMakerPosition", " "));
            coMakerKyc.put("grossAnnualIncome", sp.getString("coMakerGrossIncome", " "));
            coMakerKyc.put("otherIncome", sp.getString("coMakerOtherIncome", " "));
            coMakerKyc.put("nameOfPreviousEmployer", sp.getString("coMakerPreviousEmployer", " "));
            coMakerKyc.put("officeAddressPrevious", sp.getString("coMakerPreviousEmployerOffice", " "));
            coMakerKyc.put("yearsWithPresentEmployer", sp.getString("coMakerPresentEmployerYears", " "));
            coMakerKyc.put("yearsWithPreviousEmployer", sp.getString("coMakerPreviousEmployerYears", " "));

            // comaker reference
            coMakerKyc.put("nameReference", sp.getString("coMakerRefName", " "));
            coMakerKyc.put("relationshipReference", sp.getString("coMakerRefRelationship", " "));
            coMakerKyc.put("employerReference", sp.getString("coMakerRefEmployer", " "));
            coMakerKyc.put("contactNumberReference", sp.getString("coMakerRefContactNo", " "));
            coMakerKyc.put("mobileReference", sp.getString("coMakerRefMobile", " "));
            coMakerKyc.put("relatedToOfficerId", sp.getInt("coMakerIsRelated", 0));
            coMakerKyc.put("staff_name", sp.getString("coMakerRelatedOfficerName", " "));
            coMakerKyc.put("staff_contact", sp.getString("coMakerOfficerContactNo", " "));
            coMakerKyc.put("staff_relationship", sp.getInt("coMakerRelationshipToStaff", 0));

            //final comaker object
            coMaker.put("info", coMakerInfo);
            coMaker.put("kyc", coMakerKyc);

            //images - client and comaker photo
            if(sp.contains("clientPhoto")) {
                String clientImagePath = sp.getString("clientPhoto", null);
                Bitmap myBitmap = BitmapFactory.decodeFile(clientImagePath);
                String str_clientImage = convertImage(myBitmap, 1);
                image.put("clientImage", "data:image/jpeg;base64," + str_clientImage);
                Log.i("Client", str_clientImage);
            }

            if(sp.contains("coMakerPhoto")) {
                String coMakerImagePath = sp.getString("coMakerPhoto", null);
                Bitmap myBitmap = BitmapFactory.decodeFile(coMakerImagePath);
                String str_coMakerImage = convertImage(myBitmap, 1);
                image.put("coMakerImage", "data:image/jpeg;base64," + str_coMakerImage);
            }

            //documents
            if(sp.contains("clientGovImage")) {
                String clientGovImagePath = sp.getString("clientGovImage", null);
                Bitmap myBitmap = BitmapFactory.decodeFile(clientGovImagePath);
                String str_clientGovIdImage = convertImage(myBitmap, 1);
                documents.put("clientGovId", "data:image/jpeg;base64," + str_clientGovIdImage);
            }

            if(sp.contains("clientDocImage")) {
                String clientDocImagePath = sp.getString("clientDocImage", null);
                Bitmap myBitmap = BitmapFactory.decodeFile(clientDocImagePath);
                String str_clientDocImage = convertImage(myBitmap, 1);
                documents.put("clientDocument", "data:image/jpeg;base64," + str_clientDocImage);
            }

            if(sp.contains("coMakerGovImage")) {
                String coMakerGovImagePath = sp.getString("coMakerGovImage", null);
                Bitmap myBitmap = BitmapFactory.decodeFile(coMakerGovImagePath);
                String str_coMakerGovIdImage = convertImage(myBitmap, 1);
                documents.put("comakerGovId", "data:image/jpeg;base64," + str_coMakerGovIdImage);
            }

            if(sp.contains("coMakerDocImage")) {
                String coMakerDocImagePath = sp.getString("coMakerDocImage", null);
                Bitmap myBitmap = BitmapFactory.decodeFile(coMakerDocImagePath);
                String str_coMakerDocImage = convertImage(myBitmap, 1);
                documents.put("comakerDocument", "data:image/jpeg;base64," + str_coMakerDocImage);
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

            signatures.put("termsConditionSign", "data:image/png;base64," + str_termsConditionSign);
            signatures.put("loanAgreementSign", "data:image/png;base64," + str_loanAgreementSign);
            signatures.put("promiNoteSign", "data:image/png;base64," + str_promiNoteSign);


            //final object to be sent to API
            finalObject.put("client", client);
            finalObject.put("kyc", kyc);
            finalObject.put("loan", loan);
            finalObject.put("comaker", coMaker);
            finalObject.put("image", image);
            finalObject.put("documents", documents);
            finalObject.put("signature", signatures);


            sendPost(finalObject, v);

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



    public void sendPost(JSONObject finalData, View v) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String uNameandPword = "mifos:password";
                    String basicAutoPayload = "Basic " + Base64.encodeToString(uNameandPword.getBytes(), Base64.DEFAULT);

 //                   String url = "https://192.168.227.159/fineract-provider/api/v1/clients";
 //                   trustAllHosts();
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

//                    InputStream in = new BufferedInputStream(conn.getInputStream());

                    os.flush();
                    os.close();


                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    if(conn.getResponseCode() != 200){
                        Log.i("OUTPUT", readStream(conn.getErrorStream()));

                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "Request Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else {
                        Log.i("OUTPUT", readStream(conn.getInputStream()));
                        Navigation.findNavController(v).navigate(R.id.action_promiNoteFragment_to_loanApplicationSentFragment);
                    }

                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        thread.start();
    }

    public String readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;

        while((length = inputStream.read(buffer)) != -1){
            result.write(buffer, 0, length);
        }

        return result.toString(UTF_8.name());
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

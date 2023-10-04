package com.fintech.superadmin.activities.aeps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;

import androidx.appcompat.app.ActionBar;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.common.ShareActivity;
import com.fintech.superadmin.adapters.PdfDocumentAdapter;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.network.responses.AnalyticsResponseModel;
import com.fintech.superadmin.data.network.responses.DetailedHistoryResponse;
import com.fintech.superadmin.databinding.ActivityAepsDetailedAnalyticBinding;
import com.fintech.superadmin.util.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class AepsDetailedAnalytic extends ShareActivity {

    ActivityAepsDetailedAnalyticBinding binding;
    String myStatus = "";
    String shareResponse = "";
    String amount_in_word="";
//    String charges ="";
    String gst_no = "";
    String amount_label = "Amount";
    private final String filename = "invoice.pdf";
    private final String filepath = "MyInvoices";
    String shareValue = "Nothing";
    User user;
    private long mLastClickTime = 0;
    DetailedHistoryResponse historyResponse;
    AnalyticsResponseModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAepsDetailedAnalyticBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("AePS Transaction");
        historyResponse = (DetailedHistoryResponse)getIntent().getSerializableExtra("detailed");
        model = (AnalyticsResponseModel)getIntent().getSerializableExtra("regular");
        setJsonData();
        binding.setRegularDetailModel(model);
        AppDatabase appDatabase = AppDatabase.getAppDatabase(AepsDetailedAnalytic.this);
        user = appDatabase.getUserDao().getRegularUser();
        setListener();
        setConditionals();
    }


    @SuppressLint("SetTextI18n")
    private void setConditionals(){
        try {
            if (model.getCause().equals("commission")){
                amount_label = "Commission";
                binding.amountLabel.setText(amount_label);
            }
            else if (model.getCause().equals("charge")){
                amount_label = "Charge";
                binding.amountLabel.setText(amount_label);;
                binding.responseMessage.setText(binding.responseMessage.getText().toString()+"\n No Charges are required to pay for this");
            }
            else{
                amount_label = "Amount";
                binding.amountLabel.setText(amount_label);
                binding.responseMessage.setText(binding.responseMessage.getText().toString()+"\n No Charges are required to pay for this");
            }

        }catch (NullPointerException e){

        }

        shareValue = shareOfNormal();
    }

    private String shareOfNormal() {
        return "Status: "+ myStatus
                +"\nAadhaar Number: "+model.getOnMobile()
                +"\nTransaction Type: "+binding.transactionType.getText().toString()
                +"\n"+amount_label+": "+model.getAmount()
                +"\nGST No :"+gst_no
                +"\nAmount in Words "+amount_in_word
                +"\nTransaction id: "+model.getTxn_id()
                +"\nResponse: "+shareResponse
                +"\nDate-Time"+model.getDate()
                +"\nSystem User: "+user.getName()+" "+user.getLastname()
                +"\nSystem User Mobile: "+user.getMobile();

    }


    private String shareOfCommission() {
        return "Status: "+ myStatus
                +"\nAadhaar Number: "+model.getOnMobile()
                +"\nTransaction Type: "+binding.transactionType.getText().toString()
                +"\n"+amount_label+": "+model.getAmount()
                +"\nTDS Charge :"+model.getTds()
                +"\nAmount in Words "+amount_in_word
                +"\nTransaction id: "+model.getTxn_id()
                +"\nResponse: "+shareResponse
                +"\nDate-Time"+model.getDate()
                +"\nSystem User: "+user.getName()+" "+user.getLastname()
                +"\nSystem User Mobile: "+user.getMobile();
    }


    @SuppressLint("SetTextI18n")
    private void setJsonData() {
        String success = "Success";
        String failed = "Failed";
        String pending = "Pending";
        try {

            String st = model.getStatus().toLowerCase();
            if(historyResponse.getData_response()!=null) {
                JSONObject object = new JSONObject(historyResponse.getData_response());
                if (st.equals("success")) {
                    binding.gifStatus.setImageResource(R.drawable.success);
                    binding.textStatus.setText(success);
                    binding.responseMobile.setText("**** **** " + object.getString("last_aadhar"));
                    myStatus = success;
                } else if (st.equals("pending")) {
                    binding.gifStatus.setImageResource(R.drawable.warning);
                    binding.textStatus.setText(pending);
                    binding.responseMobile.setText("**** **** " + object.getString("last_aadhar"));
                    myStatus = pending;
                } else {
                    binding.gifStatus.setImageResource(R.drawable.failed);
                    binding.textStatus.setText(failed);
                    myStatus = failed;
                    binding.responseMobile.setText("**** **** " +model.getOnMobile());
                }

                shareResponse = object.getString("message");
                binding.responseMessage.setText(object.getString("message"));
                binding.bankIinValue.setText(object.getString("bankiin"));
                if(object.has("balanceamount")){
                    binding.balanceAmountLayout.setVisibility(View.VISIBLE);
                    binding.balanceAmount.setText(object.getString("balanceamount"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject getObject = new JSONObject(historyResponse.getAdditional_info());
            amount_in_word = getObject.getString("amount_in_word");

//            charges = getObject.getString("charges");
//            binding.charges.setText(charges);
            gst_no = getObject.getString("gst_no");
            binding.gstNo.setText(gst_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(historyResponse.getType_response()==null){
            historyResponse.setType_response("No Data");
        }
        else if(historyResponse.getType_response().equals("be")){
            historyResponse.setType_response("Balance Enquiry");
        }
        else if(historyResponse.getType_response().equals("ms")){
            historyResponse.setType_response("Mini Statement");
        }
        else if(historyResponse.getType_response().equals("cw")){
            historyResponse.setType_response("Cash Withdrawal");
        }
        else if(historyResponse.getType_response().equals("m")){
            historyResponse.setType_response("Aadhaar Pay");
        }
        binding.transactionType.setText(historyResponse.getType_response());
    }

    private void setListener(){
        binding.printReceipt.setOnClickListener(v -> Dexter.withContext(AepsDetailedAnalytic.this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        createPDFFile(getExternalFilesDir(filepath)+"/"+filename);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        ViewUtils.showToast(getApplicationContext(), "Permission Denied for Printing");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                })
                .check());
    }


    private void createPDFFile(String path){
        if(new File(path).exists()){
            new File(path).delete();
        }

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor(getResources().getString(R.string.app_name));
            document.addCreator(user.getName()+" "+user.getLastname());

            //Font Setting
            BaseColor colorAccent = new BaseColor(0, 153, 204, 255);
            float fontSize = 20.0f;
            float valueFontSize = 26.0f;

            InputStream inputStream = getAssets().open("logo.png");
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image companyLogo = Image.getInstance(stream.toByteArray());
            companyLogo.setAlignment(Element.ALIGN_CENTER);
            companyLogo.scalePercent(25);
            document.add(companyLogo);

            AddLineSeparator(document);

            //Custom Font
            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            Font titleFont = new Font(fontName, 36.0f, Font.NORMAL, BaseColor.BLACK);
            addNewItem(document, model.getPayment_type()+" "+binding.transactionType.getText().toString(), Element.ALIGN_CENTER, titleFont);


            Font orderNumberFont = new Font(fontName, fontSize, Font.NORMAL, colorAccent);
            Font orderNumberValueFont = new Font(fontName, fontSize, Font.NORMAL, colorAccent);
            AddLineSeparator(document);

            addNewItem(document, "Aadhaar No:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getOnMobile(), Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);


            addNewItem(document, "Bank IIN:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, binding.bankIinValue.getText().toString(), Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            if(binding.balanceAmountLabel.getVisibility()==View.VISIBLE){
                addNewItem(document, "Balance Amount:", Element.ALIGN_LEFT, orderNumberFont);
                addNewItem(document, binding.balanceAmount.getText().toString(), Element.ALIGN_LEFT, orderNumberValueFont);
                AddLineSeparator(document);
            }

            addNewItem(document, "Transaction:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, binding.textStatus.getText().toString(), Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            addNewItem(document, "Transaction ID:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getTxn_id(), Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            addNewItem(document, "TDS Charge:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getTds(), Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            addNewItem(document, "Amount:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getAmount(), Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            addNewItem(document, "Amount in Words:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getAmount_in_word(), Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            addNewItem(document, "Response:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, shareResponse, Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            addNewItem(document, "Date:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getDate(), Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            addNewItem(document, getResources().getString(R.string.note_gst_any_physical_signature), Element.ALIGN_CENTER, orderNumberFont);

            AddLineSeparator(document);

            document.close();
            printPDF();


        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            ViewUtils.showToast(this, e.getMessage());
        }
    }

    private void printPDF() {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        try {
            String path = getExternalFilesDir(filepath)+"/"+filename;
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(this, getExternalFilesDir(filepath)+"/"+filename);
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());
        }catch (Exception e){
            ViewUtils.showToast(this, e.getMessage());
        }
    }

    private void AddLineSeparator(Document document) throws DocumentException {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 0));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);
    }

    private void addLineSpace(Document document) throws DocumentException {
        document.add(new Paragraph());
    }

    private void addNewItem(Document document, String text, int align, Font font) throws DocumentException {
        Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        document.add(paragraph);
    }

}
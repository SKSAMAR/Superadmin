package com.fintech.prepe.activities.fastag;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;

import androidx.appcompat.app.ActionBar;

import com.fintech.prepe.R;
import com.fintech.prepe.activities.common.ShareActivity;
import com.fintech.prepe.adapters.PdfDocumentAdapter;
import com.fintech.prepe.data.db.AppDatabase;
import com.fintech.prepe.data.db.entities.User;
import com.fintech.prepe.data.network.responses.AnalyticsResponseModel;
import com.fintech.prepe.data.network.responses.DetailedHistoryResponse;
import com.fintech.prepe.databinding.ActivityFastagDetailedAnalyticBinding;
import com.fintech.prepe.util.ViewUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class FASTagDetailedAnalytic extends ShareActivity {


    String gst_no = "";
    String amount_label = "Amount";
    private final String filename = "invoice.pdf";
    private final String filepath = "MyInvoices";
    String shareValue = "Nothing";

    String myStatus = "";
    String shareResponse = "";
    String transactionType = "";
    User user;
    private long mLastClickTime = 0;
    DetailedHistoryResponse historyResponse;
    AnalyticsResponseModel model;
    ActivityFastagDetailedAnalyticBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFastagDetailedAnalyticBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("FASTag Transaction");
        historyResponse = (DetailedHistoryResponse)getIntent().getSerializableExtra("detailed");
        model = (AnalyticsResponseModel)getIntent().getSerializableExtra("regular");
        setJsonData();
        binding.setRegularDetailModel(model);
        AppDatabase appDatabase = AppDatabase.getAppDatabase(FASTagDetailedAnalytic.this);
        user = appDatabase.getUserDao().getRegularUser();
        setListener();
    }

    private void setJsonData() {
        String success = "Success";
        String pending = "Pending";
        String failed = "Failed";
        String refunded = "Refunded";
        String comStatus = "";
        if(model.getStatus()!=null){
            comStatus = model.getStatus().toLowerCase();
        }
        try {
            JSONObject object = new JSONObject(historyResponse.getData_response());
            JSONObject detObject = new JSONObject(historyResponse.getData_check_response());
            switch (comStatus) {
                case "success":
                    binding.gifStatus.setImageResource(R.drawable.success);
                    binding.textStatus.setText(success);
                    myStatus = success;
                    break;
                case "pending":
                    binding.gifStatus.setImageResource(R.drawable.warning);
                    binding.textStatus.setText(pending);
                    myStatus = pending;
                    break;
                case "refund":
                    binding.gifStatus.setImageResource(R.drawable.success);
                    binding.textStatus.setText(refunded);
                    myStatus = refunded;
                    break;
                default:
                    binding.gifStatus.setImageResource(R.drawable.failed);
                    binding.textStatus.setText(failed);
                    myStatus = failed;
                    break;
            }
            shareResponse = object.getString("message");
            binding.responseMessage.setText(object.getString("message"));
            transactionType = historyResponse.getType_response();
            if(transactionType==null){
                transactionType = "";
            }
            binding.transactionType.setText(transactionType.toUpperCase());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void shareTheData(){

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        else{

            if(shareResponse==null){
                shareResponse="nothing";
            }

            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");

            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Status: "+ myStatus
                    +"\nCA Number: "+model.getOnMobile()
                    +"\nTransaction Type: "+transactionType
                    +"\nOperator: "+model.getOp_id()
                    +"\nAmount: "+model.getAmount()
                    +"\nCommission: "+model.getCommission_amount()
                    +"\nOpening Balance: "+model.getAmount_earlier()
                    +"\nClosing Balance: "+model.getAmount_left()
                    +"\nTransaction id: "+model.getTxn_id()
                    +"\nResponse: "+shareResponse
                    +"\nDate-Time"+model.getDate()
                    +"\nSystem User: "+user.getName()+" "+user.getLastname()
                    +"\nSystem User Mobile: "+user.getMobile()
            );
            try {

                startActivity(Intent.createChooser(whatsappIntent, "Send Using: "));

            } catch (android.content.ActivityNotFoundException ex) {

                ViewUtils.showToast(FASTagDetailedAnalytic.this, "No app found to share..");

            }

        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }


    private void setListener(){
        binding.printReceipt.setOnClickListener(v -> Dexter.withContext(this)
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


            InputStream inputStream = getAssets().open("splash_logo.png");
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
            addNewItem(document, model.getPayment_type(), Element.ALIGN_CENTER, titleFont);

            //Add More
            Font orderNumberFont = new Font(fontName, fontSize, Font.NORMAL, colorAccent);
            Font orderNumberValueFont = new Font(fontName, fontSize, Font.NORMAL, colorAccent);

            AddLineSeparator(document);

            addNewItem(document, "CA Number:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getOnMobile(), Element.ALIGN_LEFT, orderNumberValueFont);

            AddLineSeparator(document);


            addNewItem(document, "Operator Name:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getOperator_name(), Element.ALIGN_LEFT, orderNumberValueFont);

            AddLineSeparator(document);

            addNewItem(document, "Transaction:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, myStatus, Element.ALIGN_LEFT, orderNumberValueFont);


            AddLineSeparator(document);

            addNewItem(document, "Transaction ID:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getTxn_id(), Element.ALIGN_LEFT, orderNumberValueFont);


            AddLineSeparator(document);

            addNewItem(document, "TDS Charge:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getTds(), Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);


            addNewItem(document, "Amount:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, "₹"+getResources().getString(R.string.rupee_sign)+" "+model.getAmount(), Element.ALIGN_LEFT, orderNumberValueFont);

            AddLineSeparator(document);

            addNewItem(document, "Amount in Words:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getAmount_in_word(), Element.ALIGN_LEFT, orderNumberValueFont);

            AddLineSeparator(document);


            addNewItem(document, "Response:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, shareResponse, Element.ALIGN_LEFT, orderNumberValueFont);

            //Added More
            AddLineSeparator(document);

            addNewItem(document, "Date:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getDate(), Element.ALIGN_LEFT, orderNumberValueFont);

            AddLineSeparator(document);


            addNewItem(document, "Retailer:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, user.getName()+" "+user.getLastname(), Element.ALIGN_LEFT, orderNumberValueFont);

            AddLineSeparator(document);

            addNewItem(document, "Retailer Mobile:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, user.getMobile(), Element.ALIGN_LEFT, orderNumberValueFont);

            AddLineSeparator(document);

            addNewItem(document, getResources().getString(R.string.note_this_is_a_computer_generated_receipt_and_does_not_require_any_physical_signature), Element.ALIGN_CENTER, orderNumberFont);

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
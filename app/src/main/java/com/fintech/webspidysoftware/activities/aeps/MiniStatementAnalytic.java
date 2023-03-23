package com.fintech.webspidysoftware.activities.aeps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;

import com.fintech.webspidysoftware.activities.common.BaseActivity;

import androidx.recyclerview.widget.GridLayoutManager;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.fintech.webspidysoftware.R;
import com.fintech.webspidysoftware.adapters.MiniStatementAdapter;
import com.fintech.webspidysoftware.adapters.PdfDocumentAdapter;
import com.fintech.webspidysoftware.data.db.AppDatabase;
import com.fintech.webspidysoftware.data.db.entities.User;
import com.fintech.webspidysoftware.data.network.responses.AnalyticsResponseModel;
import com.fintech.webspidysoftware.data.network.responses.DetailedHistoryResponse;
import com.fintech.webspidysoftware.data.network.responses.MiniStatementData;
import com.fintech.webspidysoftware.databinding.ActivityMiniStatementAnalyticBinding;
import com.fintech.webspidysoftware.util.ExecuteUtil;
import com.fintech.webspidysoftware.util.ViewUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MiniStatementAnalytic extends BaseActivity {

    ActivityMiniStatementAnalyticBinding binding;
    String myStatus = "";
    String shareResponse = "";
    User user;
    private long mLastClickTime = 0;
    DetailedHistoryResponse historyResponse;
    AnalyticsResponseModel model;
    String balance_amount = "";
    String acknowlege_no = "";
    List<MiniStatementData> list;
    String amount_label = "Amount";

    private final String filename = "invoice.pdf";
    private final String filepath = "MyInvoices";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMiniStatementAnalyticBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("AePS Transaction");
        historyResponse = (DetailedHistoryResponse) getIntent().getSerializableExtra("detailed");
        model = (AnalyticsResponseModel) getIntent().getSerializableExtra("regular");
        setJsonData();
        binding.setRegularDetailModel(model);
        AppDatabase appDatabase = AppDatabase.getAppDatabase(MiniStatementAnalytic.this);
        user = appDatabase.getUserDao().getRegularUser();
        binding.getRoot().setOverScrollMode(View.OVER_SCROLL_NEVER);
        setListener();
        setConditionals();
    }

    @SuppressLint("SetTextI18n")
    private void setJsonData() {
        JSONArray ministatement = null;
        String success = "Success";
        String failed = "Failed";
        try {
            JSONObject object = new JSONObject(historyResponse.getData_response());
            if (object.getBoolean("status") && object.getInt("response_code") == 1) {
                binding.gifStatus.setImageResource(R.drawable.success);
                binding.textStatus.setText(success);
                binding.responseMobile.setText("**** **** **** " + object.getString("last_aadhar"));
                myStatus = success;
                ministatement = object.getJSONArray("ministatement");
            } else {
                binding.gifStatus.setImageResource(R.drawable.failed);
                binding.textStatus.setText(failed);
                myStatus = failed;
                binding.responseMobile.setText(model.getOnMobile());
            }
            shareResponse = object.getString("message");
            binding.responseMessage.setText(object.getString("message"));
            if (historyResponse.getType_response() == null) {
                historyResponse.setType_response("No Data");
            } else if (historyResponse.getType_response().equals("be")) {
                historyResponse.setType_response("Balance Enquiry");
            } else if (historyResponse.getType_response().equals("ms")) {
                historyResponse.setType_response("Mini Statement");
            } else if (historyResponse.getType_response().equals("cw")) {
                historyResponse.setType_response("Cash Withdrawal");
            }
            binding.transactionType.setText(historyResponse.getType_response());

            binding.givenBalance.setText(object.getString("balanceamount"));
            binding.givenBankName.setText(object.getString("bankiin"));
            binding.givenDateTime.setText(object.getString("datetime"));
            binding.givenAckno.setText(object.getString("ackno"));
            balance_amount = object.getString("balanceamount");
            acknowlege_no = object.getString("ackno");
            if (object.has("balanceamount")) {
                binding.balanceAmountLayout.setVisibility(View.VISIBLE);
                binding.balanceAmount.setText(object.getString("balanceamount"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        list = new ArrayList<>();

        if (ministatement != null) {
            try {

                for (int i = 0; i < Objects.requireNonNull(ministatement).length(); i++) {
                    JSONObject ob = ministatement.getJSONObject(i);
                    String date = ob.getString("date");
                    String txnType = ob.getString("txnType");
                    String amount = ob.getString("amount");
                    String narration = ob.getString("narration");
                    list.add(new MiniStatementData(date, txnType, amount, narration));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setRecyclerView(list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            finish();
            return true;
        } else if (item.getItemId() == R.id.share) {
            shareTheData();
        } else if (item.getItemId() == R.id.homePage) {
            ExecuteUtil.ThrowOut(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.response_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void setConditionals() {
        try {
            if (model.getCause().equals("commission")) {
                amount_label = "Commission";
                binding.amountLabel.setText(amount_label);
            } else if (model.getCause().equals("charge")) {
                amount_label = "Charge";
                binding.amountLabel.setText(amount_label);
            } else {
                amount_label = "Amount";
                binding.amountLabel.setText(amount_label);
            }

        } catch (NullPointerException e) {

        }
    }


    private void shareTheData() {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        } else {

            if (shareResponse == null) {
                shareResponse = "nothing";
            }

            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");

            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Status: " + myStatus
                    + "\nAadhaar Number: " + model.getOnMobile()
                    + "\nTransaction Type: " + binding.transactionType.getText().toString()
                    + "\n" + amount_label + ": " + model.getAmount()
                    + "\nCommission: " + model.getCommission_amount()
                    + "\nOpening Balance: " + model.getAmount_earlier()
                    + "\nClosing Balance: " + model.getAmount_left()
                    + "\nTransaction id: " + model.getTxn_id()
                    + "\nResponse: " + shareResponse
                    + "\nDate-Time" + model.getDate()
                    + "\nSystem User: " + user.getName() + " " + user.getLastname()
                    + "\nSystem User Mobile: " + user.getMobile()
            );
            try {
                startActivity(Intent.createChooser(whatsappIntent, "Send Using: "));
            } catch (android.content.ActivityNotFoundException ex) {

                ViewUtils.showToast(MiniStatementAnalytic.this, "No app found to share..");

            }

        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }

    private void setRecyclerView(List<MiniStatementData> list) {
        binding.miniStateRecycler.setLayoutManager(new GridLayoutManager(MiniStatementAnalytic.this, 1, GridLayoutManager.VERTICAL, false));
        binding.miniStateRecycler.setAdapter(new MiniStatementAdapter(list));
        if (list == null) {
            binding.miniStatementLayout.setVisibility(View.GONE);
        } else if (list.size() == 0) {
            binding.miniStatementLayout.setVisibility(View.GONE);
        }

        binding.miniStateRecycler.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }


    private void setListener() {
        binding.printReceipt.setOnClickListener(v -> Dexter.withContext(MiniStatementAnalytic.this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        createPDFFile(getExternalFilesDir(filepath) + "/" + filename);
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


    private void createPDFFile(String path) {
        if (new File(path).exists()) {
            new File(path).delete();
        }

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor(getResources().getString(R.string.app_name));
            document.addCreator(user.getName() + " " + user.getLastname());

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
            addNewItem(document, model.getPayment_type() + " " + binding.transactionType.getText().toString(), Element.ALIGN_CENTER, titleFont);


            Font orderNumberFont = new Font(fontName, fontSize, Font.NORMAL, colorAccent);
            Font orderNumberValueFont = new Font(fontName, fontSize, Font.NORMAL, colorAccent);
            AddLineSeparator(document);

            addNewItem(document, "Aadhaar No:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, "**** **** " + model.getOnMobile(), Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            addNewItem(document, "Bank IIN:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, binding.bankIinValue.getText().toString(), Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            if (binding.balanceAmountLabel.getVisibility() == View.VISIBLE) {
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

            addNewItem(document, "Acknowledge No:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, acknowlege_no, Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            addNewItem(document, "Account Balance :", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, balance_amount, Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);


            addNewItem(document, "Response:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, shareResponse, Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            addNewItem(document, "Date:", Element.ALIGN_LEFT, orderNumberFont);
            addNewItem(document, model.getDate(), Element.ALIGN_LEFT, orderNumberValueFont);
            AddLineSeparator(document);

            try {

                if (list.size() > 0) {
                    addNewItem(document, "Mini Statement", Element.ALIGN_CENTER, titleFont);
                    AddLineSeparator(document);
                    for (int i = 0; i < list.size(); i++) {

                        AddNewItemOnSameLine(document, "Date:", list.get(i).getDate(), orderNumberFont);
                        AddNewItemOnSameLine(document, "Transaction Type:", list.get(i).getTxnType(), orderNumberFont);
                        AddNewItemOnSameLine(document, "Amount:", list.get(i).getAmount(), orderNumberFont);
                        AddNewItemOnSameLine(document, "Narration:", list.get(i).getNarration(), orderNumberFont);
                        AddLineSeparator(document);
                    }

                }

            } catch (NullPointerException exception) {
                ViewUtils.showToast(MiniStatementAnalytic.this, "Statement data got corrupted");
            }


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
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(this, getExternalFilesDir(filepath) + "/" + filename);
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());
        } catch (Exception e) {
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

    private void AddNewItemOnSameLine(Document document, String left, String right, Font font) throws DocumentException {
        Paragraph para = new Paragraph();
        Chunk glue = new Chunk(new VerticalPositionMark());
        Phrase ph1 = new Phrase();

        Paragraph main = new Paragraph();
        ph1.add(new Chunk(left, font));
        ph1.add(glue); // Here I add special chunk to the same phrase.
        ph1.add(new Chunk(right, font));
        para.add(ph1);
        document.add(para);
        AddLineSeparator(document);
    }

}
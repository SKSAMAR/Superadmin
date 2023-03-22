package com.fintech.petoindia.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.fintech.petoindia.R;
import com.fintech.petoindia.deer_listener.Receiver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ViewUtils {

    //Make a toast request
    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showSnackBar(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }


    //Make a ProgressBar visible
    public static void showProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
    }

    //Make a ProgressBar visible
    public static void hideProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.GONE);
    }

    public static void setFocusable(TextInputEditText editText, Activity activity){
        editText.setShowSoftInputOnFocus(true);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        editText.requestFocus();
    }
    public static boolean checkIfNameIsValid(String namVar)
    {
        return namVar.matches( "[A-Z][a-z]*" );
    }

    public static boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isRegexValid(String data, String regex) {
        Pattern pat = Pattern.compile(regex);
        if (data == null)
            return false;
        return pat.matcher(data).matches();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.toggleSoftInputFromWindow(
                view.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }


    public static<T> void onSpinnerViewBring(String title, Context context, List<T> list, Receiver<T> receiver){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.my_spinner_row);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditText searchOperator = dialog.findViewById(R.id.SearchOperator);
        ListView myOperatorListView = dialog.findViewById(R.id.MyOperatorListView);
        TextView head_title_section = dialog.findViewById(R.id.head_title_section);
        head_title_section.setText(title);
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(context, android.R.layout.simple_list_item_1,list);
        myOperatorListView.setAdapter(adapter);
        searchOperator.setVisibility(View.GONE);

        myOperatorListView.setOnItemClickListener((parent, view1, position, id) -> {
            receiver.getData(adapter.getItem(position));
            dialog.dismiss();
        });
    }


    public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if(pairs.length!=idx){
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        }
        return query_pairs;
    }

    public static Boolean whatIsFileSize(Uri uri, Context context){
        Cursor returnCursor = context.getContentResolver().
                query(uri, null, null, null, null);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        int size = returnCursor.getInt(sizeIndex) / 1000;
        returnCursor.close();
        return size <= Constants.MAX_FILE_SIZE;
    }


    public static<T> void onSpinnerViewFinger(String title, Context context, List<T> list, Receiver<Integer> receiver){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.my_spinner_row);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditText searchOperator = dialog.findViewById(R.id.SearchOperator);
        ListView myOperatorListView = dialog.findViewById(R.id.MyOperatorListView);
        TextView head_title_section = dialog.findViewById(R.id.head_title_section);
        head_title_section.setText(title);
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(context, android.R.layout.simple_list_item_1,list);
        myOperatorListView.setAdapter(adapter);
        searchOperator.setVisibility(View.GONE);

        myOperatorListView.setOnItemClickListener((parent, view1, position, id) -> {
            receiver.getData(position);
            dialog.dismiss();
        });
    }
}

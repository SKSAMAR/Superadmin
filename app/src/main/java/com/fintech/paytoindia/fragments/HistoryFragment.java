package com.fintech.paytoindia.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.fintech.paytoindia.R;
import com.fintech.paytoindia.adapters.HistoryAdapter;
import com.fintech.paytoindia.data.model.HistoryModel;
import com.fintech.paytoindia.data.network.responses.AnalyticsResponseModel;
import com.fintech.paytoindia.databinding.BottomSheetTransactionTypeBinding;
import com.fintech.paytoindia.databinding.FragmentHistoryBinding;
import com.fintech.paytoindia.listeners.BringHistoryListener;
import com.fintech.paytoindia.util.MyAlertUtils;
import com.fintech.paytoindia.viewmodel.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistoryFragment extends Fragment implements BringHistoryListener {

    AlertDialog alertDialog;
    String fromDate="";
    String toDate="";
    String result="";
    String transType="";
    String id= "";

    String tRecharge="";
    String tBBPS ="";
    String tAePS ="";
    String tDMT ="";
    String tMicroAtm ="";
    String tAddfund = "";

    ProgressDialog dialog;
    FragmentHistoryBinding binding;
    String indexing = "0";
    GridLayoutManager gridLayoutManager;
    List<HistoryModel> historyList = new ArrayList<>();
    HistoryAdapter adapter;
    HomeViewModel homeViewModel;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setHistoryViewModel(homeViewModel);
        homeViewModel.historyListener = this;
        dialog = new ProgressDialog(requireActivity());
        startFiltering();
        gridLayoutManager = new GridLayoutManager(requireActivity(), 1, GridLayoutManager.VERTICAL, false);
        binding.historyData.setLayoutManager(gridLayoutManager);
        adapter  = new HistoryAdapter(this.historyList);
        binding.historyData.setAdapter(adapter);
        onScrolledToBottom();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
    }

    @Override
    public void onStart(String message) {
        dialog.setTitle(message);
        dialog.show();
    }

    @Override
    public void onComplete(String message) {
        dialog.dismiss();
    }

    @Override
    public void onHistoryBrought(List<HistoryModel> list) {
        if(list.size()==0 && indexing.equals("0")){
            binding.noRecords.setVisibility(View.VISIBLE);
        }
        else{
            binding.noRecords.setVisibility(View.GONE);
        }
        setHistory(list);
    }

    @Override
    public void onFailure(String message) {
        dialog.setTitle(message);
    }

    @Override
    public void onAnalyticsBrought(List<AnalyticsResponseModel> list) {

    }

    @SuppressLint("NotifyDataSetChanged")
    private void setHistory(List<HistoryModel> historyList){
        adapter.setAllData(historyList);
        adapter.notifyDataSetChanged();
    }


    private void onScrolledToBottom() {
        binding.historyData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    int position = getLast();
                    if(position== adapter.getItemCount()-1){
                        id = adapter.getLastPositionId();
                        indexing = String.valueOf(position+1);
                        startFiltering();
                    }
                }
            }
        });
    }

    private int getLast(){
        return ((GridLayoutManager) Objects.requireNonNull(binding.historyData.getLayoutManager()))
                .findLastCompletelyVisibleItemPosition();
    }




    final Calendar myCalendar = Calendar.getInstance();

    public void setPopUpFilterDates(){
        AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity(), R.style.MyTransparentBottomSheetDialogTheme);
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        View view = inflater.inflate(R.layout.pop_up_filter,null,false);
        Button reset = view.findViewById(R.id.FilterAnalyticResetButton);
        Button search = view.findViewById(R.id.FilterAnalyticButton);
        TextView fromDate = view.findViewById(R.id.FromFilterAnalyticDate);
        TextView toDate = view.findViewById(R.id.ToFilterAnalyticDate);
        ImageView cancel_image = view.findViewById(R.id.cancel_image);
        toDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_calendar_today_24, 0, 0, 0);
        toDate.setCompoundDrawablePadding(20);
        fromDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_calendar_today_24, 0, 0, 0);
        fromDate.setCompoundDrawablePadding(20);
        alert.setView(view);
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        toDate.setText(getToDate());
        fromDate.setText(getFromDate());
        DatePickerDialog.OnDateSetListener fromDatePicker = (view1, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String value = updateLabel(fromDate);
            setFromDates(value);
        };

        DatePickerDialog.OnDateSetListener toDatePicker = (view1, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String value = updateLabel(toDate);
            setToDates(value);
        };

        toDate.setOnClickListener(view12 -> new DatePickerDialog(requireActivity(), toDatePicker, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        fromDate.setOnClickListener(view13 -> new DatePickerDialog(requireActivity(), fromDatePicker, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        reset.setOnClickListener(view15 -> {
            toDate.setText("");
            fromDate.setText("");
            resetDates();
            id = "0";
            indexing ="0";
            adapter.resetTheList();
            startFiltering();
            alertDialog.dismiss();
            //Execute the next code

        });
        search.setOnClickListener(v -> {
            //execute to search..
            if(toDate.getText().toString().isEmpty() || fromDate.getText().toString().isEmpty()){
                MyAlertUtils.showAlertDialog(requireActivity(),"Warning", "Provide both dates", R.drawable.warning);
            }else{
            adapter.resetTheList();
            indexing = "0";
            startFiltering();
            alertDialog.dismiss();
            }
        });

        cancel_image.setOnClickListener(view14 -> alertDialog.dismiss());

    }

    private String updateLabel(TextView textView) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        textView.setText(sdf.format(myCalendar.getTime()));
        return  textView.getText().toString();
    }

    private void resetDates(){
        this.toDate = "";
        this.fromDate = "";
    }

    private void setToDates(String value){
        toDate = value;
    }
    private void setFromDates(String value){
        fromDate = value;
    }
    private String getFromDate(){
        return fromDate;
    }
    private String getToDate(){
        return toDate;
    }

    private void setListeners(){
        binding.selectedDate.setOnClickListener(view -> setPopUpFilterDates());
        binding.categories.setOnClickListener(view -> bottomSheetTransactionType());
        binding.statusFilter.setOnClickListener(view -> onSpinnerClickResult());
    }

    private void startFiltering(){
        if(adapter!=null){
            id = adapter.getLastPositionId();
        }
        homeViewModel.homeRepository.getMyHistories(this, indexing, fromDate, toDate, transType, result, id);
    }


    private void bottomSheetTransactionType(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity(), R.style.MyTransparentBottomSheetDialogTheme);
        BottomSheetTransactionTypeBinding categoryBinding = BottomSheetTransactionTypeBinding.inflate(LayoutInflater.from(requireActivity()));
        bottomSheetDialog.setContentView(categoryBinding.getRoot());
        bottomSheetDialog.show();
        bottomSheetDialog.setDismissWithAnimation(true);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        categoryBinding.cancelImage.setOnClickListener(view -> bottomSheetDialog.dismiss());

        categoryBinding.rechargeCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                tRecharge = "R";
            }else{
                tRecharge = "";
            }
        });

        categoryBinding.bbpsCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                tBBPS = "B";
            }else{
                tBBPS = "";
            }
        });

        categoryBinding.aepsCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                tAePS = "A";
            }else{
                tAePS = "";
            }
        });

        categoryBinding.dmtCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                tDMT = "D";
            }else{
                tDMT = "";
            }
        });

        categoryBinding.microatmCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                tMicroAtm = "M";
            }else{
                tMicroAtm = "";
            }
        });

        categoryBinding.addFundCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                tAddfund = "F";
            }else{
                tAddfund = "";
            }
        });


        if(tRecharge.equals("R")){
            categoryBinding.rechargeCheck.setChecked(true);
        }
        if(tBBPS.equals("B")){
            categoryBinding.bbpsCheck.setChecked(true);
        }
        if(tAePS.equals("A")){
            categoryBinding.aepsCheck.setChecked(true);
        }
        if(tDMT.equals("D")){
            categoryBinding.dmtCheck.setChecked(true);
        }
        if(tMicroAtm.equals("M")){
            categoryBinding.microatmCheck.setChecked(true);
        }
        if(tAddfund.equals("F")){
            categoryBinding.addFundCheck.setChecked(true);
        }

        categoryBinding.clearText.setOnClickListener(view -> {
            categoryBinding.rechargeCheck.setChecked(false);
            categoryBinding.bbpsCheck.setChecked(false);
            categoryBinding.aepsCheck.setChecked(false);
            categoryBinding.dmtCheck.setChecked(false);
            categoryBinding.microatmCheck.setChecked(false);
            categoryBinding.addFundCheck.setChecked(false);
            tAddfund = "";
            tRecharge= "";
            tBBPS="";
            tAePS="";
            tDMT="";
            tMicroAtm="";
            transType ="";
            //Reset from below
        });

        categoryBinding.applyFilter.setOnClickListener(view -> {
            transType = tRecharge+tBBPS+tAePS+tDMT+tMicroAtm+tAddfund;
            if(transType.isEmpty()){
                MyAlertUtils.showAlertDialog(requireActivity(),"Warning", "Select any category", R.drawable.warning);
            }
            else{
                adapter.resetTheList();
                indexing = "0";
                startFiltering();
                bottomSheetDialog.dismiss();
            }
            //Fetch with more filter
        });

    }

    @SuppressLint("SetTextI18n")
    public void onSpinnerClickResult(){
        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.my_spinner_row);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        EditText searchOperator = dialog.findViewById(R.id.SearchOperator);
        ListView myOperatorListView = dialog.findViewById(R.id.MyOperatorListView);
        TextView titleBar = dialog.findViewById(R.id.head_title_section);
        List<String> list = new ArrayList<>();
        titleBar.setText("Transaction Status");
        list.add("All");
        list.add("Success");
        list.add("Failed");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1,list);
        myOperatorListView.setAdapter(adapter);
        searchOperator.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        myOperatorListView.setOnItemClickListener((parent, view1, position, id) -> {
            result = adapter.getItem(position).toLowerCase();
            this.adapter.resetTheList();
            indexing = "0";
            startFiltering();
            dialog.dismiss();
        });
    }
}
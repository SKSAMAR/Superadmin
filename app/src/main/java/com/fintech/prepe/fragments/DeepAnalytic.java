package com.fintech.prepe.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.tabs.TabLayout;
import com.fintech.prepe.R;
import com.fintech.prepe.data.model.GraphModel;
import com.fintech.prepe.data.network.responses.AnalCardReport;
import com.fintech.prepe.databinding.FragmentDeepAnalyticBinding;
import com.fintech.prepe.listeners.BaseAnalyticListener;
import com.fintech.prepe.util.MyAlertUtils;
import com.fintech.prepe.viewmodel.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeepAnalytic extends Fragment implements BaseAnalyticListener {

    public DeepAnalytic() {
        // Required empty public constructor
    }

    String fromDate="";
    String toDate="";

    String days;
    String options;
    FragmentDeepAnalyticBinding binding;
    HomeViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deep_analytic, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setAnalyticViewModel(viewModel);
        setData();
        setListeners();
    }

    private void setData(){
        days = Objects.requireNonNull(Objects.requireNonNull(binding.daysTab.getTabAt(binding.daysTab.getSelectedTabPosition())).getText()).toString();
        options = Objects.requireNonNull(Objects.requireNonNull(binding.optionTab.getTabAt(binding.daysTab.getSelectedTabPosition())).getText()).toString();
        viewModel.homeRepository.bringCardInfo(requireActivity(), options, days, getFromDate(), getToDate(), this);

        binding.dateSelector.setOnClickListener(view -> setPopUpFilterDates());
    }

    private void setListeners(){
        binding.optionTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                options = Objects.requireNonNull(tab.getText()).toString();
                viewModel.homeRepository.bringCardInfo(requireActivity(), options, days, getFromDate(), getToDate(), DeepAnalytic.this);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        binding.daysTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                days = Objects.requireNonNull(tab.getText()).toString();
                viewModel.homeRepository.bringCardInfo(requireActivity(), options, days, getFromDate(), getToDate(),DeepAnalytic.this);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void getCardReport(AnalCardReport report) {
        String lastDesc = "Last Total";
        if(days.equalsIgnoreCase("daily")){
            lastDesc = "Yesterday's total";
        }
        if(days.equalsIgnoreCase("weekly")){
            lastDesc = "Last Week's total";
        }
        if(days.equalsIgnoreCase("monthly")){
            lastDesc = "Last Months' total";
        }
        binding.earningData.setText(report.getCurrent_earning());
        binding.businessData.setText(report.getCurrent_business());
        binding.transactionData.setText(report.getCurrent_transaction());
        binding.lastBusinessData.setText(report.getLast_business());
        binding.lastEarningData.setText(report.getLast_earning());
        binding.lastTransactionData.setText(report.getLast_transaction());
        binding.lastBusinessDesc.setText(lastDesc);
        binding.lastEarningDesc.setText(lastDesc);
        binding.lastTransactionDesc.setText(lastDesc);
    }

    @Override
    public void getGraphReport(ArrayList<GraphModel> list) {
        List<String> stringList = new ArrayList<>();
        ArrayList<BarEntry> amounts = new ArrayList<>();
        for(int i = 0; i< list.size(); i++ ){
            if(list.get(i).getAmount()!=null && list.get(i).getDate()!=null){
                amounts.add(new BarEntry(list.get(i).getAmount(), i));
                stringList.add(list.get(i).getDate());
            }
        }
        List<IBarDataSet> iBarDataSets = new ArrayList<>();
        iBarDataSets.add(new BarDataSet(amounts, "Analytics"));
        BarData barData = new BarData(stringList, iBarDataSets);
        binding.barChart.setData(barData);
        binding.barChart.animateY(2000);
    }

    final Calendar fromCalendar = Calendar.getInstance();
    final Calendar toCalendar = Calendar.getInstance();
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
        AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        toDate.setText(getToDate());
        fromDate.setText(getFromDate());
        DatePickerDialog.OnDateSetListener fromDatePicker = (view1, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            fromCalendar.set(Calendar.YEAR, year);
            fromCalendar.set(Calendar.MONTH, monthOfYear);
            fromCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String value = updateLabelFrom(fromDate);
            setFromDates(value);
        };

        DatePickerDialog.OnDateSetListener toDatePicker = (view1, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            toCalendar.set(Calendar.YEAR, year);
            toCalendar.set(Calendar.MONTH, monthOfYear);
            toCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String value = updateLabelTo(toDate);
            setToDates(value);
        };

        toDate.setOnClickListener(view12 -> new DatePickerDialog(requireActivity(), toDatePicker, toCalendar
                .get(Calendar.YEAR), toCalendar.get(Calendar.MONTH),
                toCalendar.get(Calendar.DAY_OF_MONTH)).show());

        fromDate.setOnClickListener(view13 -> new DatePickerDialog(requireActivity(), fromDatePicker, fromCalendar
                .get(Calendar.YEAR), fromCalendar.get(Calendar.MONTH),
                fromCalendar.get(Calendar.DAY_OF_MONTH)).show());

        reset.setOnClickListener(view15 -> {
            toDate.setText("");
            fromDate.setText("");
            setFromDates("");
            setToDates("");
            alertDialog.dismiss();
            //Execute the next code
        });
        search.setOnClickListener(v -> {
            //execute to search..
            if(toDate.getText().toString().isEmpty() || fromDate.getText().toString().isEmpty()){
                MyAlertUtils.showAlertDialog(requireActivity(),"Warning", "Provide both dates", R.drawable.warning);
            }else{
                // search now...
                viewModel.homeRepository.bringGraphInfo(requireActivity(),options, days, getFromDate(), getToDate(), this );
                alertDialog.dismiss();
            }
        });

        cancel_image.setOnClickListener(view14 -> alertDialog.dismiss());

    }

    private String updateLabelFrom(TextView textView) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        textView.setText(sdf.format(fromCalendar.getTime()));
        return  textView.getText().toString();
    }

    private String updateLabelTo(TextView textView) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        textView.setText(sdf.format(toCalendar.getTime()));
        return  textView.getText().toString();
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

}
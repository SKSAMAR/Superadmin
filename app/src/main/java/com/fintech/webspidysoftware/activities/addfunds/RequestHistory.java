package com.fintech.webspidysoftware.activities.addfunds;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.lifecycle.ViewModelProvider;

import com.fintech.webspidysoftware.activities.utilactivities.UtilHistory;
import com.fintech.webspidysoftware.adapters.RequestHistoryAdapter;
import com.fintech.webspidysoftware.data_model.request.RequestedHistoryModel;
import com.fintech.webspidysoftware.util.DisplayMessageUtil;
import com.fintech.webspidysoftware.viewmodel.FundViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RequestHistory extends UtilHistory {

    FundViewModel viewModel;
    RequestHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FundViewModel.class);
        actionBar.setTitle("Request History");
        binding.searchView.setQueryHint("Search by transaction id");
        DisplayMessageUtil.loading(RequestHistory.this);
        viewModel.getRequestedHistory( "", this::setRecycler);
        initialize();
    }


    private void setRecycler(List<RequestedHistoryModel> list){
        DisplayMessageUtil.dismissDialog();
        notFound(list == null || list.isEmpty());
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new RequestHistoryAdapter(list);
        binding.recyclerView.setAdapter(adapter);
    }

    private void initialize(){
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                viewModel.getRequestedHistory( s, data -> setRecycler(data));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                viewModel.getRequestedHistory( s, data -> setRecycler(data));
                return false;
            }
        });
    }
}
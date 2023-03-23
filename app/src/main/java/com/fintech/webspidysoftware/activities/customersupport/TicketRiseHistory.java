package com.fintech.webspidysoftware.activities.customersupport;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.lifecycle.ViewModelProvider;

import com.fintech.webspidysoftware.activities.utilactivities.UtilHistory;
import com.fintech.webspidysoftware.adapters.TicketHistoryAdapter;
import com.fintech.webspidysoftware.data.model.TicketHistoryModel;
import com.fintech.webspidysoftware.util.DisplayMessageUtil;
import com.fintech.webspidysoftware.viewmodel.CustomerSupportViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TicketRiseHistory extends UtilHistory {

    CustomerSupportViewModel viewModel;
    TicketHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar.setTitle("Ticket History");
        viewModel = new ViewModelProvider(this).get(CustomerSupportViewModel.class);
        binding.searchView.setQueryHint("Search by Transaction Id");
        DisplayMessageUtil.loading(TicketRiseHistory.this);
        viewModel.getTicketHistory("", this::setRecycler);
        
        initialize();
    }

    private void setRecycler(List<TicketHistoryModel> list){
        DisplayMessageUtil.dismissDialog();
        notFound(list == null || list.isEmpty());
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new TicketHistoryAdapter(list);
        binding.recyclerView.setAdapter(adapter);
    }

    private void initialize(){
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                viewModel.getTicketHistory( s, data -> {
                    setRecycler(data);
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                viewModel.getTicketHistory(s, data -> {
                    setRecycler(data);
                });
                return false;
            }
        });
    }
    


}
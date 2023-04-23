package com.fintech.superadmin.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.superadmin.R;
import com.fintech.superadmin.data.model.SliderItem;
import com.fintech.superadmin.databinding.ItemContainerSliderImageBinding;
import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {

    private List<SliderItem> sliderList;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(List<SliderItem> sliderList){
        this.sliderList = sliderList;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(layoutInflater == null){
            layoutInflater  = LayoutInflater.from(parent.getContext());
        }
        ItemContainerSliderImageBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_container_slider_image, parent, false);

        return new ImageSliderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.binding.setSliderItem(sliderList.get(position));
    }

    @Override
    public int getItemCount() {
        if(sliderList == null){
            return 0;
        }
        return sliderList.size();
    }

    static class ImageSliderViewHolder extends RecyclerView.ViewHolder{
        private ItemContainerSliderImageBinding binding;

        public ImageSliderViewHolder(@NonNull ItemContainerSliderImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

package com.fintech.payware.fragments.sliders;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.fintech.payware.R;
import com.fintech.payware.adapters.ImageSliderAdapter;
import com.fintech.payware.data.db.AppDatabase;
import com.fintech.payware.data.db.entities.User;
import com.fintech.payware.data.model.SliderItem;
import com.fintech.payware.data.network.APIServices;
import com.fintech.payware.databinding.FragmentSliderBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class SliderFragment extends Fragment {

    @Inject
    APIServices apiServices;

    private FragmentSliderBinding binding;
    private final Handler sliderHandler = new Handler();
    private List<SliderItem> list = new ArrayList<>();

    public SliderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_slider, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            setBanners();
        }
    }

    private void setSliders(List<SliderItem> list) {

        binding.sliderViewPager.setAdapter(new ImageSliderAdapter(list));
        //sliderIndicators(list.size());

        binding.sliderViewPager.setClipToPadding(false);
        binding.sliderViewPager.setClipChildren(false);
        binding.sliderViewPager.setOffscreenPageLimit(3);

        binding.sliderViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(8));
        binding.sliderViewPager.setPageTransformer(compositePageTransformer);
        binding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //setCurrentSliderIndicators(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);

            }
        });


    }

    /*
    private void sliderIndicators(int count){
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.setMargins(8,0,0,0);

        for(int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(requireContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(requireContext(),
                    R.drawable.backgound_slider_layout_indicator_active));
            indicators[i].setLayoutParams(layoutParams);
            binding.LayoutSliderIndicators.addView(indicators[i]);
        }
        setCurrentSliderIndicators(0);
    }

    private void setCurrentSliderIndicators(int position){
        int childCount = binding.LayoutSliderIndicators.getChildCount();
        for(int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) binding.LayoutSliderIndicators.getChildAt(i);
            if(i == position){
                imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.backgound_slider_layout_indicator_active));
            }
            else{
                imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.backgound_slider_layout_indicator_inactive));
            }
        }
    }

    */

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (binding.sliderViewPager.getCurrentItem() == list.size() - 1) {
                binding.sliderViewPager.setCurrentItem(0);
            } else {
                binding.sliderViewPager.setCurrentItem(binding.sliderViewPager.getCurrentItem() + 1);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    private void setBanners() {
        String getUserstatus = "";
        try {
            User user = AppDatabase.getAppDatabase(requireActivity()).getUserDao().getRegularUser();
            getUserstatus = user.getUserstatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        apiServices.getMeBanners(getUserstatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SliderItem>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<SliderItem> list) {
                        SliderFragment.this.list = list;
                        setSliders(SliderFragment.this.list);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
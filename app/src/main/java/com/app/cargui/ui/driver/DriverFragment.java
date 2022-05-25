package com.app.cargui.ui.driver;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.app.cargui.R;
import com.app.cargui.databinding.FragmentDriverBinding;
import com.app.cargui.databinding.FragmentImeiBinding;
import com.app.cargui.ui.imei.ImeiViewModel;

public class DriverFragment extends Fragment {

    private DriverViewModel mViewModel;

    public static DriverFragment newInstance() { return new DriverFragment(); }

    private FragmentDriverBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DriverViewModel driverViewModel = new ViewModelProvider(this).get(DriverViewModel.class);
        binding = FragmentDriverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textDriver;
        driverViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
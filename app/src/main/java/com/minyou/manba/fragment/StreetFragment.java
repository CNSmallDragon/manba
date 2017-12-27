package com.minyou.manba.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minyou.manba.R;
import com.minyou.manba.databinding.FragmentStreetBinding;

public class StreetFragment extends DataBindingBaseFragment {

	private FragmentStreetBinding binding;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_street, container, false);

		return binding.getRoot();
	}

}

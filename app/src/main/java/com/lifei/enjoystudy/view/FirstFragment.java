package com.lifei.enjoystudy.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lifei.enjoystudy.R;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        RecyclerView rv = view.findViewById(R.id.rv);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add(i + "");
        }
        rv.setAdapter(new FirstAdapter(datas));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}

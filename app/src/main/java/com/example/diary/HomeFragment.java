package com.example.diary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    FloatingActionButton btnAddPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.btnAddPost = (FloatingActionButton) view.findViewById(R.id.btn_add_new_post);
        this.btnAddPost.setOnClickListener(goToAddPostView);

    }

    // Click Listeners
    View.OnClickListener goToAddPostView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container, new AddPostFragment()).addToBackStack(null).commit();
        }
    };

    View.OnClickListener goToForgetPasswordPage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };
}
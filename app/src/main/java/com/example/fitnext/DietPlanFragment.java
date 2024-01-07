package com.example.fitnext;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DietPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietPlanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button muscleGainBtn;
    private Button weightGainBtn;
    private Button weightLossBtn;
    private Button diabetesPlanBtn;

    public DietPlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DietPlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DietPlanFragment newInstance(String param1, String param2) {
        DietPlanFragment fragment = new DietPlanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_diet_plan, container, false);

        //getting views
        muscleGainBtn=view.findViewById(R.id.muscleGainBtn);
        weightGainBtn=view.findViewById(R.id.weightGainBtn);
        weightLossBtn=view.findViewById(R.id.weightLossBtn);
        diabetesPlanBtn=view.findViewById(R.id.diabetesPlanBtn);

     muscleGainBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,new MuscleGainFragment());
             fragmentTransaction.addToBackStack(null);
             fragmentTransaction.commit();
         }
     });

     weightGainBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,new WeightGainFragment());
             fragmentTransaction.addToBackStack(null);
             fragmentTransaction.commit();
         }
     });

     weightLossBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,new WeightLossFragment());
             fragmentTransaction.addToBackStack(null);
             fragmentTransaction.commit();
         }
     });


     diabetesPlanBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             fragmentTransaction.replace(R.id.fragment_container,new DiabetesPlanFragment());
             fragmentTransaction.addToBackStack(null);
             fragmentTransaction.commit();
         }
     });


        return view;
    }
}
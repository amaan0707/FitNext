package com.example.fitnext;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.chatgpt.ChatGPT_Dashboard;

public class HomeFragment extends Fragment {
    Button physicalOption;
    Button mentalFitnessOption;
    Button medicineReminderOption;
    Button chatBotOption;
    Button meditationMusicOption;
    Button buyMedicineOption;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        //getting views
       physicalOption=view.findViewById(R.id.option1);
       mentalFitnessOption=view.findViewById(R.id.option2);
       medicineReminderOption=view.findViewById(R.id.option3);
       chatBotOption=view.findViewById(R.id.option4);
      meditationMusicOption=view.findViewById(R.id.option5);
      buyMedicineOption=view.findViewById(R.id.option6);

       //rest of the code
        physicalOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,new PhysicalFitnessFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        mentalFitnessOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,new MentalFitnessFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        medicineReminderOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(getContext(),FirstActivity.class));
            }
        });

        chatBotOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), ChatGPT_Dashboard.class);
                startActivity(intent);
            }
        });

        meditationMusicOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           startActivity(new Intent(getContext(), SongActivity.class));
            }
        });

       buyMedicineOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BuyMedicineActivity.class));
            }
        });
        return view;
    }

}
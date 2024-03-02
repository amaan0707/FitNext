package com.example.fitnext;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OcdFragment extends Fragment {

    private ocdQuestionModel mQues = new ocdQuestionModel();
    private TextView mQuesView;
    private Button mChoiceA;
    private Button mChoiceB;
    private Button mChoiceC;
    private Button mChoiceD;
    private Button mChoiceE;
    private int mPoint = 0;
    private int mQuesNumber = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ocd, container, false);

        mQuesView = view.findViewById(R.id.questions);
        mChoiceA = view.findViewById(R.id.choiceA);
        mChoiceB = view.findViewById(R.id.choiceB);
        mChoiceC = view.findViewById(R.id.choiceC);
        mChoiceD = view.findViewById(R.id.choiceD);
        mChoiceE = view.findViewById(R.id.choiceE);

        updateQuestion();

        mChoiceA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mQuesNumber == mQues.getLength()) {
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        mChoiceB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mPoint = mPoint + 1;
                if (mQuesNumber == mQues.getLength()) {
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        mChoiceC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mPoint = mPoint + 2;
                if (mQuesNumber == mQues.getLength()) {
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        mChoiceD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mPoint = mPoint + 3;
                if (mQuesNumber == mQues.getLength()) {
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        mChoiceE.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mPoint = mPoint + 4;
                if (mQuesNumber == mQues.getLength()) {
                    updateResult();
                } else {
                    updateQuestion();
                }
            }
        });

        return view;
    }

    private void updateQuestion() {
        mQuesView.setText(mQues.getQuestion(mQuesNumber));
        mChoiceA.setText(mQues.getChoiceA(mQuesNumber));
        mChoiceB.setText(mQues.getChoiceB(mQuesNumber));
        mChoiceC.setText(mQues.getChoiceC(mQuesNumber));
        mChoiceD.setText(mQues.getChoiceD(mQuesNumber));
        mChoiceE.setText(mQues.getChoiceE(mQuesNumber));

        mQuesNumber++;
    }

    private void updateResult() {
        OcdResultFragment resultFragment = new OcdResultFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("points", mPoint);
        resultFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, resultFragment)
                .addToBackStack(null)  // Optional: Add transaction to back stack
                .commit();
    }
}
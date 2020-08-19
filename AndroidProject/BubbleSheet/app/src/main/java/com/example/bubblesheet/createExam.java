package com.example.bubblesheet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import maes.tech.intentanim.CustomIntent;

public class createExam extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView txt1, txt2, txt3, txt4, txt5,
            txt6, txt7, txt8, txt9, txt10,
            txt11, txt12, txt13, txt14, txt15,
            txt16, txt17, txt18, txt19, txt20,
            txt21, txt22, txt23, txt24, txt25;
    String questionsNumber;
    Spinner spinner;
    Toolbar toolbar;
    RadioGroup group1, group2, group3, group4, group5,
            group6, group7, group8, group9, group10,
            group11, group12, group13, group14, group15,
            group16, group17, group18, group19, group20,
            group21, group22, group23, group24, group25;

    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5,
            radioButton6, radioButton7, radioButton8, radioButton9, radioButton10,
            radioButton11, radioButton12, radioButton13, radioButton14, radioButton15,
            radioButton16, radioButton17, radioButton18, radioButton19, radioButton20,
            radioButton21, radioButton22, radioButton23, radioButton24, radioButton25;

    Button btn_createExam;
    public static String answers = "";
    EditText exam_name;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);
        spinner = findViewById(R.id.spinner);
        toolbar = findViewById(R.id.toolbar_createExam);
        toolbar.setTitle("Create Exam");
        exam_name = findViewById(R.id.txtExam_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        group1 = findViewById(R.id.group1);
        group2 = findViewById(R.id.group2);
        group3 = findViewById(R.id.group3);
        group4 = findViewById(R.id.group4);
        group5 = findViewById(R.id.group5);
        group6 = findViewById(R.id.group6);
        group7 = findViewById(R.id.group7);
        group8 = findViewById(R.id.group8);
        group9 = findViewById(R.id.group9);
        group10 = findViewById(R.id.group10);
        group11 = findViewById(R.id.group11);
        group12 = findViewById(R.id.group12);
        group13 = findViewById(R.id.group13);
        group14 = findViewById(R.id.group14);
        group15 = findViewById(R.id.group15);
        group16 = findViewById(R.id.group16);
        group17 = findViewById(R.id.group17);
        group18 = findViewById(R.id.group18);
        group19 = findViewById(R.id.group19);
        group20 = findViewById(R.id.group20);
        group21 = findViewById(R.id.group21);
        group22 = findViewById(R.id.group22);
        group23 = findViewById(R.id.group23);
        group24 = findViewById(R.id.group24);
        group25 = findViewById(R.id.group25);

        radioButton1 = findViewById(R.id.r11);
        radioButton2 = findViewById(R.id.r21);
        radioButton3 = findViewById(R.id.r31);
        radioButton4 = findViewById(R.id.r41);
        radioButton5 = findViewById(R.id.r51);
        radioButton6 = findViewById(R.id.r61);
        radioButton7 = findViewById(R.id.r71);
        radioButton8 = findViewById(R.id.r81);
        radioButton9 = findViewById(R.id.r91);
        radioButton10 = findViewById(R.id.r10_1);
        radioButton11 = findViewById(R.id.r11_1);
        radioButton12 = findViewById(R.id.r12_1);
        radioButton13 = findViewById(R.id.r13_1);
        radioButton14 = findViewById(R.id.r14_1);
        radioButton15 = findViewById(R.id.r15_1);
        radioButton16 = findViewById(R.id.r16_1);
        radioButton17 = findViewById(R.id.r17_1);
        radioButton18 = findViewById(R.id.r18_1);
        radioButton19 = findViewById(R.id.r19_1);
        radioButton20 = findViewById(R.id.r20_1);
        radioButton21 = findViewById(R.id.r21_1);
        radioButton22 = findViewById(R.id.r22_1);
        radioButton23 = findViewById(R.id.r23_1);
        radioButton24 = findViewById(R.id.r24_1);
        radioButton25 = findViewById(R.id.r25_1);

        txt1 = findViewById(R.id.textView12);
        txt2 = findViewById(R.id.textView11);
        txt3 = findViewById(R.id.textView10);
        txt4 = findViewById(R.id.textView13);
        txt5 = findViewById(R.id.textView14);
        txt6 = findViewById(R.id.textView6);
        txt7 = findViewById(R.id.textView7);
        txt8 = findViewById(R.id.textView8);
        txt9 = findViewById(R.id.textView9);
        txt10 = findViewById(R.id.textView20);
        txt11 = findViewById(R.id.textView21);
        txt12 = findViewById(R.id.textView22);
        txt13 = findViewById(R.id.textView23);
        txt14 = findViewById(R.id.textView24);
        txt15 = findViewById(R.id.textView25);
        txt16 = findViewById(R.id.textView26);
        txt17 = findViewById(R.id.textView27);
        txt18 = findViewById(R.id.textView28);
        txt19 = findViewById(R.id.textView29);
        txt20 = findViewById(R.id.textView200);
        txt21 = findViewById(R.id.textView20_1);
        txt22 = findViewById(R.id.textView20_2);
        txt23 = findViewById(R.id.textView20_3);
        txt24 = findViewById(R.id.textView20_4);
        txt25 = findViewById(R.id.textView20_5);

        show15Q();

        btn_createExam = findViewById(R.id.btn_createExam);
        btn_createExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("j",exam_name.getText().toString());
                //Toast.makeText(createExam.this, exam_name.getText(), Toast.LENGTH_SHORT).show();
                if (!exam_name.getText().toString().equals("")) {
                    answers = "";
                    //Toast.makeText(createExam.this, "hiiiiiiiiii", Toast.LENGTH_SHORT).show();
                    if (questionsNumber.equals("15")) {
                        answers += radioButton1.getText().toString() + "" + radioButton2.getText() + "" + radioButton3.getText() + "" + radioButton4.getText() + "" + radioButton5.getText();
                        answers += radioButton6.getText().toString() + "" + radioButton7.getText() + "" + radioButton8.getText() + "" + radioButton9.getText() + "" + radioButton10.getText();
                        answers += radioButton11.getText().toString() + "" + radioButton12.getText() + "" + radioButton13.getText() + "" + radioButton14.getText() + "" + radioButton15.getText();
                    } else if (questionsNumber.equals("20")) {
                        answers += radioButton1.getText().toString() + "" + radioButton2.getText() + "" + radioButton3.getText() + "" + radioButton4.getText() + "" + radioButton5.getText();
                        answers += radioButton6.getText().toString() + "" + radioButton7.getText() + "" + radioButton8.getText() + "" + radioButton9.getText() + "" + radioButton10.getText();
                        answers += radioButton11.getText().toString() + "" + radioButton12.getText() + "" + radioButton13.getText() + "" + radioButton14.getText() + "" + radioButton15.getText();
                        answers += radioButton16.getText().toString() + "" + radioButton17.getText() + "" + radioButton18.getText() + "" + radioButton19.getText() + "" + radioButton20.getText();
                    } else if (questionsNumber.equals("25")) {
                        answers += radioButton1.getText().toString() + "" + radioButton2.getText() + "" + radioButton3.getText() + "" + radioButton4.getText() + "" + radioButton5.getText();
                        answers += radioButton6.getText().toString() + "" + radioButton7.getText() + "" + radioButton8.getText() + "" + radioButton9.getText() + "" + radioButton10.getText();
                        answers += radioButton11.getText().toString() + "" + radioButton12.getText() + "" + radioButton13.getText() + "" + radioButton14.getText() + "" + radioButton15.getText();
                        answers += radioButton16.getText().toString() + "" + radioButton17.getText() + "" + radioButton18.getText() + "" + radioButton19.getText() + "" + radioButton20.getText();
                        answers += radioButton21.getText().toString() + "" + radioButton22.getText() + "" + radioButton23.getText() + "" + radioButton24.getText() + "" + radioButton25.getText();

                    }

                    //Toast.makeText(createExam.this, answers, Toast.LENGTH_SHORT).show();
                    Exam exam = new Exam(exam_name.getText().toString(), answers, Integer.parseInt(questionsNumber));
                    firestore.collection("users").document(Login.email).collection("exams").document(exam_name.getText() + "").set(exam);
                    Support.Alerter(createExam.this, "Ok", "your exam saved");
                } else {
                    Support.Alerter(createExam.this, "Error", "please, Enter the name of exam!!!");
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        questionsNumber = parent.getItemAtPosition(position).toString();
        if (questionsNumber.equals("15")) {
            show15Q();
        } else if (questionsNumber.equals("20")) {
            show20Q();
        } else if (questionsNumber.equals("25")) {
            show25Q();
        }
        //Toast.makeText(this, questionsNumber, Toast.LENGTH_SHORT).show();
    }

    private void show15Q() {
        reset();
        group16.setVisibility(View.GONE);
        group17.setVisibility(View.GONE);
        group18.setVisibility(View.GONE);
        group19.setVisibility(View.GONE);
        group20.setVisibility(View.GONE);
        group21.setVisibility(View.GONE);
        group22.setVisibility(View.GONE);
        group23.setVisibility(View.GONE);
        group24.setVisibility(View.GONE);
        group25.setVisibility(View.GONE);

        txt16.setVisibility(View.GONE);
        txt17.setVisibility(View.GONE);
        txt18.setVisibility(View.GONE);
        txt19.setVisibility(View.GONE);
        txt20.setVisibility(View.GONE);
        txt21.setVisibility(View.GONE);
        txt22.setVisibility(View.GONE);
        txt23.setVisibility(View.GONE);
        txt24.setVisibility(View.GONE);
        txt25.setVisibility(View.GONE);

    }

    private void show20Q() {
        reset();
        group21.setVisibility(View.GONE);
        group22.setVisibility(View.GONE);
        group23.setVisibility(View.GONE);
        group24.setVisibility(View.GONE);
        group25.setVisibility(View.GONE);

        txt21.setVisibility(View.GONE);
        txt22.setVisibility(View.GONE);
        txt23.setVisibility(View.GONE);
        txt24.setVisibility(View.GONE);
        txt25.setVisibility(View.GONE);
    }

    private void show25Q() {
        reset();
    }

    void reset() {
        group16.setVisibility(View.VISIBLE);
        group17.setVisibility(View.VISIBLE);
        group18.setVisibility(View.VISIBLE);
        group19.setVisibility(View.VISIBLE);
        group20.setVisibility(View.VISIBLE);
        group21.setVisibility(View.VISIBLE);
        group22.setVisibility(View.VISIBLE);
        group23.setVisibility(View.VISIBLE);
        group24.setVisibility(View.VISIBLE);
        group25.setVisibility(View.VISIBLE);

        txt16.setVisibility(View.VISIBLE);
        txt17.setVisibility(View.VISIBLE);
        txt18.setVisibility(View.VISIBLE);
        txt19.setVisibility(View.VISIBLE);
        txt20.setVisibility(View.VISIBLE);
        txt21.setVisibility(View.VISIBLE);
        txt22.setVisibility(View.VISIBLE);
        txt23.setVisibility(View.VISIBLE);
        txt24.setVisibility(View.VISIBLE);
        txt25.setVisibility(View.VISIBLE);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void checkRadioButton1(View view) {

        int radioId = group1.getCheckedRadioButtonId();
        radioButton1 = findViewById(radioId);
        Toast.makeText(createExam.this, radioButton1.getText(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, radioButton1.getText(), Toast.LENGTH_SHORT).show();

    }

    public void checkRadioButton5(View view) {
        //Toast.makeText(createExam.this, "5", Toast.LENGTH_SHORT).show();
        int radioId = group5.getCheckedRadioButtonId();
        radioButton5 = findViewById(radioId);
        //Toast.makeText(createExam.this, radioButton5.getText(), Toast.LENGTH_SHORT).show();
    }

    public void checkRadioButton4(View view) {
        //Toast.makeText(createExam.this, "4", Toast.LENGTH_SHORT).show();
        int radioId = group4.getCheckedRadioButtonId();
        radioButton4 = findViewById(radioId);
        //Toast.makeText(createExam.this, radioButton4.getText(), Toast.LENGTH_SHORT).show();
    }

    public void checkRadioButton3(View view) {
        //Toast.makeText(createExam.this, "3", Toast.LENGTH_SHORT).show();
        int radioId = group3.getCheckedRadioButtonId();
        radioButton3 = findViewById(radioId);
        //Toast.makeText(createExam.this, radioButton3.getText(), Toast.LENGTH_SHORT).show();
    }

    public void checkRadioButton2(View view) {
        //Toast.makeText(createExam.this, "2", Toast.LENGTH_SHORT).show();
        int radioId = group2.getCheckedRadioButtonId();
        radioButton2 = findViewById(radioId);
        //Toast.makeText(createExam.this, radioButton2.getText(), Toast.LENGTH_SHORT).show();
    }

    public void checkRadioButton6(View view) {
        //Toast.makeText(createExam.this, "6", Toast.LENGTH_SHORT).show();
        int radioId = group6.getCheckedRadioButtonId();
        radioButton6 = findViewById(radioId);
        //Toast.makeText(createExam.this, radioButton6.getText(), Toast.LENGTH_SHORT).show();
    }

    public void checkRadioButton7(View view) {
        //Toast.makeText(createExam.this, "7", Toast.LENGTH_SHORT).show();
        int radioId = group7.getCheckedRadioButtonId();
        radioButton7 = findViewById(radioId);
        //Toast.makeText(createExam.this, radioButton7.getText(), Toast.LENGTH_SHORT).show();
    }

    public void checkRadioButton8(View view) {
        //Toast.makeText(createExam.this, "8", Toast.LENGTH_SHORT).show();
        int radioId = group8.getCheckedRadioButtonId();
        radioButton8 = findViewById(radioId);
        //Toast.makeText(createExam.this, radioButton8.getText(), Toast.LENGTH_SHORT).show();
    }

    public void checkRadioButton9(View view) {
        //Toast.makeText(createExam.this, "9", Toast.LENGTH_SHORT).show();
        int radioId = group9.getCheckedRadioButtonId();
        radioButton9 = findViewById(radioId);
        //Toast.makeText(createExam.this, radioButton9.getText(), Toast.LENGTH_SHORT).show();
    }

    public void checkRadioButton10(View view) {
        //Toast.makeText(createExam.this, "10", Toast.LENGTH_SHORT).show();
        int radioId = group10.getCheckedRadioButtonId();
        radioButton10 = findViewById(radioId);
        //Toast.makeText(createExam.this, radioButton10.getText(), Toast.LENGTH_SHORT).show();
    }

    public void checkRadioButton11(View view) {
        //Toast.makeText(createExam.this, "11", Toast.LENGTH_SHORT).show();
        int radioId = group11.getCheckedRadioButtonId();
        radioButton11 = findViewById(radioId);
    }

    public void checkRadioButton12(View view) {
        //Toast.makeText(createExam.this, "12", Toast.LENGTH_SHORT).show();
        int radioId = group12.getCheckedRadioButtonId();
        radioButton12 = findViewById(radioId);
    }

    public void checkRadioButton13(View view) {
        //Toast.makeText(createExam.this, "13", Toast.LENGTH_SHORT).show();
        int radioId = group13.getCheckedRadioButtonId();
        radioButton13 = findViewById(radioId);
    }

    public void checkRadioButton14(View view) {
        //Toast.makeText(createExam.this, "14", Toast.LENGTH_SHORT).show();
        int radioId = group14.getCheckedRadioButtonId();
        radioButton14 = findViewById(radioId);
    }

    public void checkRadioButton15(View view) {
        //Toast.makeText(createExam.this, "15", Toast.LENGTH_SHORT).show();
        int radioId = group15.getCheckedRadioButtonId();
        radioButton15 = findViewById(radioId);
    }

    public void checkRadioButton16(View view) {
        //Toast.makeText(createExam.this, "16", Toast.LENGTH_SHORT).show();
        int radioId = group16.getCheckedRadioButtonId();
        radioButton16 = findViewById(radioId);
    }

    public void checkRadioButton17(View view) {
        //Toast.makeText(createExam.this, "17", Toast.LENGTH_SHORT).show();
        int radioId = group17.getCheckedRadioButtonId();
        radioButton17 = findViewById(radioId);
    }

    public void checkRadioButton18(View view) {
        //Toast.makeText(createExam.this, "18", Toast.LENGTH_SHORT).show();
        int radioId = group18.getCheckedRadioButtonId();
        radioButton18 = findViewById(radioId);
    }

    public void checkRadioButton19(View view) {
        //Toast.makeText(createExam.this, "19", Toast.LENGTH_SHORT).show();
        int radioId = group19.getCheckedRadioButtonId();
        radioButton19 = findViewById(radioId);
    }

    public void checkRadioButton20(View view) {
        //Toast.makeText(createExam.this, "20", Toast.LENGTH_SHORT).show();
        int radioId = group20.getCheckedRadioButtonId();
        radioButton20 = findViewById(radioId);
    }
    public void checkRadioButton21(View view) {
        //Toast.makeText(createExam.this, "18", Toast.LENGTH_SHORT).show();
        int radioId = group21.getCheckedRadioButtonId();
        radioButton21 = findViewById(radioId);
    }

    public void checkRadioButton22(View view) {
        //Toast.makeText(createExam.this, "19", Toast.LENGTH_SHORT).show();
        int radioId = group22.getCheckedRadioButtonId();
        radioButton22 = findViewById(radioId);
    }

    public void checkRadioButton23(View view) {
        //Toast.makeText(createExam.this, "20", Toast.LENGTH_SHORT).show();
        int radioId = group23.getCheckedRadioButtonId();
        radioButton23 = findViewById(radioId);
    }
    public void checkRadioButton24(View view) {
        //Toast.makeText(createExam.this, "18", Toast.LENGTH_SHORT).show();
        int radioId = group24.getCheckedRadioButtonId();
        radioButton24 = findViewById(radioId);
    }

    public void checkRadioButton25(View view) {
        //Toast.makeText(createExam.this, "19", Toast.LENGTH_SHORT).show();
        int radioId = group25.getCheckedRadioButtonId();
        radioButton25 = findViewById(radioId);
    }



    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(createExam.this, "right-to-left");
    }
}
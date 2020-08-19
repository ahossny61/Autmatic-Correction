package com.example.bubblesheet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.graphics.Matrix;

public class imageTest extends AppCompatActivity {

    private static final int port_number = 17960;
    static Socket s = null;
    static DataInputStream DIS = null;
    static ObjectOutputStream oos = null;
    Bitmap currentBitMap = null;
    private static final int permission_code = 1000;
    private static final int pic_id = 123;
    ImageView imageView;
    Uri imageUri;
    FloatingActionButton btn_openCamera, btn_openGallery;
    String currentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    TextView grade_show;
    ProgressBar progressBar;
    boolean isprogress = false;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String email = user.getEmail();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    ArrayList<Exam> exams = new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    //spinnerArrayAdapter adapter;
    Spinner spinner;
    ArrayList<String> names = new ArrayList<>();
    int examsPostition;
    TextView showAnswers, showGradeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);
        imageView = findViewById(R.id.photo);
        btn_openCamera = findViewById(R.id.btn_openCamera);
        btn_openGallery = findViewById(R.id.btn_openGallarey);
        grade_show = findViewById(R.id.txt_grade);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        spinner = findViewById(R.id.exams_spinner);
        showAnswers = findViewById(R.id.txt_showAnswers);
        showGradeType = findViewById(R.id.txt_typeofGrade);
        //Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        //adapter=new spinnerArrayAdapter(this,exams);
        //seqIds.addAll(response.body().getData().getEquipmentList().getEquipIds());
        //adapter.notifyDataSetChanged(); // Add this line to notify your adapter about new data
        //spinner.setAdapter(adapter);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        firestore.collection("users").document(email).collection("exams").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(imageTest.this, "no Exams found..... or check your network connection", Toast.LENGTH_SHORT).show();
                        } else {
                            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                Exam exam = snapshot.toObject(Exam.class);
                                exams.add(exam);

                            }
                            for (int i = 0; i < exams.size(); i++)
                                names.add(exams.get(i).getName());
                            //Toast.makeText(imageTest.this, names.size() + "", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        spinner.setAdapter(adapter);
        examsPostition = 0;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                examsPostition = position;
                Exam currentExam = exams.get(position);
                showAnswers.setText("The correct answers :\n" + currentExam.getAnswers());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(imageTest.this, "no no no", Toast.LENGTH_SHORT).show();

            }
        });
        //Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
        btn_openGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isprogress)
                    openGallery();
                else
                    Support.Alerter(imageTest.this, "Warn", "please wait till the grade show !!!");
            }
        });

        btn_openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isprogress) {
                    Support.Alerter(imageTest.this, "Steps", "Show the rectangle of the questions'border in the taken image");
                    openCamera();
                } else
                    Support.Alerter(imageTest.this, "Warn", "please wait till the grade show !!!");
            }
        });

        Support.Alerter(this, "Infomation", "Select open Camera or open Gallarey to correct an exam ,thank u ^-^");
        AsyncTaskInit init = new AsyncTaskInit();
        init.execute();
        //openGallery();

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, pic_id);
    }

    private void openCamera() {

        String fileName = "photo";
        File storadDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile = File.createTempFile(fileName, ".jpg", storadDir);

            currentPhotoPath = imageFile.getAbsolutePath();

            Uri image_uri = FileProvider.getUriForFile(imageTest.this, "com.example.bubblesheet.fileprovider", imageFile);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id && resultCode == RESULT_OK && data != null && data.getData() != null) {

            try {
                Exam currentExam = exams.get(examsPostition);
                showAnswers.setText("The correct answers :\n" + currentExam.getAnswers());

                imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                if (bitmap.getWidth() > bitmap.getHeight()) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, false);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    currentBitMap = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

                }
                else {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    currentBitMap = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
                }

                imageView.setImageBitmap(currentBitMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.VISIBLE);
            isprogress = true;
            DescripImage();

        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Exam currentExam = exams.get(examsPostition);
            showAnswers.setText("The correct answers :\n" + currentExam.getAnswers());

            try {
                Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                if (bitmap.getWidth() > bitmap.getHeight()) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, false);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    currentBitMap = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
                } else {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    currentBitMap = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
                }
                imageView.setImageBitmap(currentBitMap);
            } catch (Exception e) {
                e.printStackTrace();
            }

            progressBar.setVisibility(View.VISIBLE);
            isprogress = true;
            DescripImage();
            //Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
        }


    }


    public void DescripImage() {
        if (imageView.getDrawable() != null) {
            //Toast.makeText(this, getString(R.string.Description), Toast.LENGTH_SHORT).show();
            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute();
            AsyncTaskRespons responsee = new AsyncTaskRespons();
            responsee.execute();
        }

    }


    private class AsyncTaskInit extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (s == null) {
                try {
                    s = new Socket("2.tcp.ngrok.io", port_number);
                    DIS = new DataInputStream(s.getInputStream());
                    oos = new ObjectOutputStream(s.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    private class AsyncTaskRespons extends AsyncTask<String, String, String> {

        String serverRespond;

        @Override
        protected String doInBackground(String... strings) {
            try {
                serverRespond = DIS.readUTF();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return serverRespond;
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(imageTest.this, s, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            isprogress = false;
            Exam currentExam = exams.get(examsPostition);
            if (s.length() != currentExam.getQuestionNumber() + 1) {
                grade_show.setText("Error in the image");
                Toast.makeText(imageTest.this, s.length() + " " + currentExam.getQuestionNumber(), Toast.LENGTH_SHORT).show();
            } else {
                String examAnswer = currentExam.getAnswers();
                int questionNumber = currentExam.getQuestionNumber();
                float grade = 0;
                String convertedAnswer = "";
                for (int i = 0; i < examAnswer.length(); i++) {
                    if (examAnswer.charAt(i) == 'A')
                        convertedAnswer += "0";
                    else if (examAnswer.charAt(i) == 'B')
                        convertedAnswer += "1";
                    else if (examAnswer.charAt(i) == 'C')
                        convertedAnswer += "2";
                    else if (examAnswer.charAt(i) == 'D')
                        convertedAnswer += "3";
                    else if (examAnswer.charAt(i) == 'E')
                        convertedAnswer += "4";

                }
                showAnswers.append("\nStudent'answers :\n");
                String convertedS = "";
                for (int i = 0; i < convertedAnswer.length(); i++) {
                    if (s.charAt(i) == convertedAnswer.charAt(i)) {
                        grade++;
                        if (s.charAt(i) == '0') {
                            Spannable spannable = new SpannableString("A");
                            spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            showAnswers.append(spannable);

                        } else if (s.charAt(i) == '1') {
                            Spannable spannable = new SpannableString("B");
                            spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            showAnswers.append(spannable);
                        } else if (s.charAt(i) == '2') {
                            Spannable spannable = new SpannableString("C");
                            spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            showAnswers.append(spannable);
                        } else if (s.charAt(i) == '3') {
                            Spannable spannable = new SpannableString("D");
                            spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            showAnswers.append(spannable);
                        } else if (s.charAt(i) == '4') {
                            Spannable spannable = new SpannableString("E");
                            spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            showAnswers.append(spannable);
                        }
                    } else {
                        if (s.charAt(i) == '0') {
                            Spannable spannable = new SpannableString("A");
                            spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            showAnswers.append(spannable);

                        } else if (s.charAt(i) == '1') {
                            Spannable spannable = new SpannableString("B");
                            spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            showAnswers.append(spannable);
                        } else if (s.charAt(i) == '2') {
                            Spannable spannable = new SpannableString("C");
                            spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            showAnswers.append(spannable);
                        } else if (s.charAt(i) == '3') {
                            Spannable spannable = new SpannableString("D");
                            spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            showAnswers.append(spannable);
                        } else if (s.charAt(i) == '4') {
                            Spannable spannable = new SpannableString("E");
                            spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            showAnswers.append(spannable);
                        }
                    }
                }

               /* for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '0')
                        convertedS += "A";
                    else if (s.charAt(i) == '1')
                        convertedS += "B";
                    else if (s.charAt(i) == '2')
                        convertedS += "C";
                    else if (s.charAt(i) == '3')
                        convertedS += "D";
                    else if (s.charAt(i) == '4')
                        convertedS += "E";

                }*/

                //Toast.makeText(imageTest.this, grade + "", Toast.LENGTH_SHORT).show();
                grade = (grade / questionNumber * 1.0f) * 100.0f;
                String final_grad = String.format("%.02f", grade);
                grade_show.setText("Grade : " + String.format(Locale.ENGLISH, "%s", final_grad) + "%");
                if (grade >= 85)
                    showGradeType.setText("Excellent ");
                else if (grade >= 75)
                    showGradeType.setText("VeryGood ");
                else if (grade >= 65)
                    showGradeType.setText("Good ");
                else if (grade >= 50)
                    showGradeType.setText("Acceptable");
                else if (grade < 50)
                    showGradeType.setText("Fail");
                //Toast.makeText(imageTest.this, s, Toast.LENGTH_SHORT).show();
                //showAnswers.append("\nStudent'answers :\n" + convertedS);
            }
        }
    }

    private class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                currentBitMap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] imageInByte = baos.toByteArray();
                baos.close();
                oos.writeObject(imageInByte);
                Exam currentExam = exams.get(examsPostition);
                String examAnswer = currentExam.getAnswers();
                int questionNumber = currentExam.getQuestionNumber();
                oos.writeObject((Object) questionNumber);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}


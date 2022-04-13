package com.example.user.goodguide;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//투어 정보 수정 화면
public class TourInfoActivity extends AppCompatActivity {
    private AlertDialog dialog;
    public String tourID;
    String tourName, tourPrice, tourCountry, tourCity, estTime, meetTime, meetPlace, minNum, maxNum, mapImage;
    AlertDialog.Builder builder;

    Uri uri;
    final int GALLERY_CODE = 0;
    final int IMAGE_CODE = 1;
    final int CROP_CODE = 2;
    String absoultePath;
    Bitmap btmimg;
    ImageView img_cmr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);

        Intent intent = getIntent();
        String tourN = intent.getStringExtra("tourName");
        actionBar.setTitle(tourN);
        builder = new AlertDialog.Builder(TourInfoActivity.this, R.style.AppCompatAlertDialogStyle);

        final EditText edtTourName = findViewById(R.id.edtTourName);
        final EditText edtCountry = findViewById(R.id.edtCountry);
        final EditText edtCity = findViewById(R.id.edtCity);
        final EditText edtEstTime = findViewById(R.id.edtEstTime);
        final EditText edtMinNum = findViewById(R.id.edtMinNum);
        final EditText edtMaxNum = findViewById(R.id.edtMaxNum);
        final EditText edtPrice = findViewById(R.id.edtPrice);
        final EditText edtMeetTime = findViewById(R.id.edtMeetTime);
        final EditText edtMeetPlace = findViewById(R.id.edtMeetPlace);
        img_cmr = (findViewById(R.id.img_cmr1));
        Button btnUpdate = findViewById(R.id.button4);

        btnUpdate.setText("수정");
        TourFragment tourFragment = new TourFragment();
        String tourid = tourFragment.tourID;
        tourID = tourid;

        img_cmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectGallery();
            }
        });

        findViewById(R.id.btn_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TourInfoActivity.this,Detail_Schedule_Update.class);
                startActivity(intent);
            }
        });

        final Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    boolean success = jsonResponse.getBoolean("success");
                    tourName = jsonResponse.getString("tourName");
                    tourPrice = jsonResponse.getString("tourPrice");
                    tourCountry = jsonResponse.getString("tourCountry");
                    tourCity = jsonResponse.getString("tourCity");
                    estTime = jsonResponse.getString("estTime");
                    meetTime = jsonResponse.getString("meetTime");
                    meetPlace = jsonResponse.getString("meetPlace");
                    minNum = jsonResponse.getString("minNum");
                    maxNum = jsonResponse.getString("maxNum");
                    mapImage = jsonResponse.getString("mapImage");

                    if(success) {
                        edtTourName.setText(tourName);
                        edtPrice.setText(tourPrice);
                        edtCity.setText(tourCity);
                        edtCountry.setText(tourCountry);
                        edtEstTime.setText(estTime);
                        edtMeetPlace.setText(meetPlace);
                        edtMeetTime.setText(meetTime);
                        edtMaxNum.setText(maxNum);
                        edtMinNum.setText(minNum);
                        imgoutput(mapImage);
                    }else{
                        dialog = builder.setMessage("투어 정보 불러들이기 실패")
                                .setPositiveButton("확인",null)
                                .create();
                        dialog.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        TourInfoRequest tourInfoRequest = new TourInfoRequest(tourID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(TourInfoActivity.this);
        queue.add(tourInfoRequest);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tourName = edtTourName.getText().toString();
                final String tourPrice = edtPrice.getText().toString();
                final String tourCity = edtCity.getText().toString();
                final String tourCountry = edtCountry.getText().toString();
                final String estTime = edtEstTime.getText().toString();
                final String meetPlace = edtMeetPlace.getText().toString();
                final String meetTime = edtMeetTime.getText().toString();
                final String maxNum = edtMaxNum.getText().toString();
                final String minNum = edtMinNum.getText().toString();
                mapImage =  absoultePath.substring(absoultePath.length()-17, absoultePath.length());

                final Response.Listener responseListener1 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            response = response.trim();
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                builder.setMessage("투어수정 성공");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }else{
                                dialog = builder.setMessage("투어수정 실패")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                TourUpdateRequest tourUpdateRequest = new TourUpdateRequest(tourID, tourName, tourPrice, tourCity,
                        tourCountry, minNum, maxNum, meetPlace, meetTime, estTime, mapImage, responseListener1);
                RequestQueue queue1 = Volley.newRequestQueue(TourInfoActivity.this);
                queue1.add(tourUpdateRequest);
            }
        });
    }
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    private class ImgOutPut extends AsyncTask<String, String, Bitmap> {
        Context context;
        HttpURLConnection conn = null;

        public ImgOutPut(Context context) {
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.disconnect();
                InputStream is = conn.getInputStream();
                btmimg = BitmapFactory.decodeStream(is);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return btmimg;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
                img_cmr.setImageBitmap(btmimg);
        }
    }

    public void imgoutput(String imgName) {
        String urlString = "http://210.179.97.55:55555/GoodGuide/newImage/" + imgName;
        try {
            ImgOutPut imgoutput = new ImgOutPut(TourInfoActivity.this);
            imgoutput.execute(urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectGallery() {
        //앨범호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE: {
                    uri = data.getData();
                }
                case IMAGE_CODE: {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(uri, "image/*");

                    intent.putExtra("outputX", 800);
                    intent.putExtra("outputY", 500);
                    intent.putExtra("aspectX", 3);
                    intent.putExtra("aspectY", 2);
                    intent.putExtra("scale", true);
                    intent.putExtra("return-data", true);

                    startActivityForResult(intent, CROP_CODE);
                }
                break;
                case CROP_CODE: {
                    if (resultCode != RESULT_OK)
                        return;
                    final Bundle extras = data.getExtras();

                    //crop한 이미지를 저장하기 위한 경로, SD카드의 전체 경로
                    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/GoodGuide/" + System.currentTimeMillis() + ".jpg";

                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data"); //비트맵으로 저장
                        img_cmr.setImageBitmap(photo); //이미지뷰에 보여줌
                        storeIMG(photo, filePath); //이미지를 외부저장소, 앨범에 저장하기 위한 함수 호출
                        absoultePath = filePath;
                        imgupload(absoultePath);//서버에 사진을 업로드하기 위한 함수 호출
                        break;
                    }
                    //임시로 저장된 파일 삭제
                    File f = new File(uri.getPath());
                    if (f.exists()) {
                        f.delete();
                    }
                }
            }
        }
    }

    //
    private void storeIMG(Bitmap src, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GoodGuide";
        File directory_SmartWheel = new File(dirPath);

        // /SmartWeel이라는 디렉토리가 있는지 확인하고 없으면 .mkdir()을 통해 만들기
        if (!directory_SmartWheel.exists()) {
            directory_SmartWheel.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile)); //출력 스트림, 불러온 파일 출력
            src.compress(Bitmap.CompressFormat.JPEG, 100, out);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile))); //앨범에 crop된 사진을 갱신
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ImgUpLoad extends AsyncTask<String, String, String> {
        Context context;
        ProgressDialog pDialog;
        String fileName;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024*1024;

        File sourceFile;
        int serverResponseCode;
        String TAG = "FileUpLoad";

        public ImgUpLoad(Context context) {
            this.context = context;
        }

        public void setPath(String uploadFilePath) {
            this.fileName = uploadFilePath;
            this.sourceFile = new File(uploadFilePath);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("이미지 로딩중입니다...");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            if(!sourceFile.isFile()){ // 해당 위치의 파일이 있는지 검사
                return null;
            }else {
                String success = "success";
                try {
                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    URL url = new URL(strings[0]);
                    Log.i("strings[0]", strings[0]);

                    // open connection
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", fileName);

                    // write data
                    dos = new DataOutputStream(conn.getOutputStream());

                    //newImage 폴더 생성
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"data1\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes("newImage"); //data1에 newImage 전달
                    dos.writeBytes(lineEnd);

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + fileName + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);

                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();

                    if (serverResponseCode == 200) {
                        Log.d("Test","success");
                    }

                    BufferedReader rd = null;
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    String line = null;

                    while ((line = rd.readLine()) != null) {
                        Log.i("Updatastate", line);
                    }
                    fileInputStream.close();
                    dos.flush(); // finish upload...
                    dos.close();
                } catch (MalformedURLException ex) {
                    Log.d("Test", "MalformedURLException " + ex.getMessage());
                } catch (NetworkOnMainThreadException ne) {
                    Log.d("Test", "NetworkOnMain " + ne.getMessage());
                } catch (Exception e) {
                    Log.d("Test", "exception " + e.getMessage());
                    // TODO: handle exception
                }
                return success;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.cancel();
        }
    }

    public void imgupload(String filePath) {
        String urlString = "http://210.179.97.55:55555/GoodGuide/DetailRegister.php";
        try {
            ImgUpLoad imgupload = new ImgUpLoad(TourInfoActivity.this);
            imgupload.setPath(filePath);
            imgupload.execute(urlString);
        } catch (Exception e) {
            Log.d("Test","out");
        }
    }
}
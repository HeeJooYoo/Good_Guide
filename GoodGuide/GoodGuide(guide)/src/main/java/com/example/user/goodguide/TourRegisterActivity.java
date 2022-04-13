package com.example.user.goodguide;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//투어 정보를 등록하기 위한 클래스
public class TourRegisterActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private final int REQUEST_PERMISSION_CODE = 2222; //권한동의를 위한 변수
    public static boolean registerCheck;

    Uri uri;
    final int GALLERY_CODE = 0;
    final int IMAGE_CODE = 1;
    final int CROP_CODE = 2;
    String absoultePath;
    boolean imageCheck;
    ImageView img_cmr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("투어 등록");

        registerCheck = false;

        final EditText edtTourName = findViewById(R.id.edtTourName);
        final EditText edtCountry = findViewById(R.id.edtCountry);
        final EditText edtCity = findViewById(R.id.edtCity);
        final EditText edtEstTime = findViewById(R.id.edtEstTime);
        final EditText edtMinNum = findViewById(R.id.edtMinNum);
        final EditText edtMaxNum = findViewById(R.id.edtMaxNum);
        final EditText edtPrice = findViewById(R.id.edtPrice);
        final EditText edtMeetTime = findViewById(R.id.edtMeetTime);
        final EditText edtMeetPlace = findViewById(R.id.edtMeetPlace);
        final Button btn_detail = (Button)findViewById(R.id.btn_detail);
        img_cmr = (findViewById(R.id.img_cmr1));


        img_cmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectGallery();
            }
        });

        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tourName = edtTourName.getText().toString().trim();
                String tourPrice = edtPrice.getText().toString().trim();
                String tourCity = edtCity.getText().toString().trim();
                String tourCountry = edtCountry.getText().toString().trim();
                String minNum = edtMinNum.getText().toString().trim();
                String maxNum = edtMaxNum.getText().toString().trim();
                String meetPlace = edtMeetPlace.getText().toString().trim();
                String meetTime = edtMeetTime.getText().toString().trim();
                String estTime = edtEstTime.getText().toString().trim();

                if(tourName.equals("") || tourPrice.equals("") || tourCity.equals("") || tourCountry.equals("") || minNum.equals("")
                        || maxNum.equals("") || meetPlace.equals("") || meetTime.equals("") || estTime.equals("") || img_cmr.getDrawable() == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(TourRegisterActivity.this, R.style.AppCompatAlertDialogStyle);
                    dialog = builder.setTitle("코스 소개 등록 안내")
                            .setMessage("모든 정보를 입력해야만\n코스 소개를 등록할 수 있습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                int permissionCheck = ContextCompat.checkSelfPermission(TourRegisterActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(TourRegisterActivity.this,Detail_Schedule_Activity.class);
                    startActivity(intent);
                } else {
                    requestPermission();
                    if (ActivityCompat.shouldShowRequestPermissionRationale(TourRegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast toast = Toast.makeText(TourRegisterActivity.this, "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(TourRegisterActivity.this, "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });

        (findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity loginActivity = new LoginActivity();
                String id = loginActivity.guideID;
                final String guideID = id;
                final TourFragment tourFragment = new TourFragment();
                String tourid = tourFragment.tourID;
                final String tourID = tourid;
                String tourName = edtTourName.getText().toString().trim();
                String tourPrice = edtPrice.getText().toString().trim();
                String tourCity = edtCity.getText().toString().trim();
                String tourCountry = edtCountry.getText().toString().trim();
                String minNum = edtMinNum.getText().toString().trim();
                String maxNum = edtMaxNum.getText().toString().trim();
                String meetPlace = edtMeetPlace.getText().toString().trim();
                String meetTime = edtMeetTime.getText().toString().trim();
                String estTime = edtEstTime.getText().toString().trim();
                String mapImage = absoultePath.substring(absoultePath.length()-17, absoultePath.length());

                //모든 정보 입력 안할시 다이얼로그창 띄우기
                if(tourName.equals("") || tourPrice.equals("") || tourCity.equals("") || tourCountry.equals("") || minNum.equals("")
                        || maxNum.equals("") || meetPlace.equals("") || meetTime.equals("") || estTime.equals("") || img_cmr.getDrawable() == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(TourRegisterActivity.this, R.style.AppCompatAlertDialogStyle);
                    dialog = builder.setMessage("모든 정보 입력")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Detail_Schedule_Activity detailActivity = new Detail_Schedule_Activity();
                if(registerCheck == false){
                    AlertDialog.Builder builder = new AlertDialog.Builder(TourRegisterActivity.this, R.style.AppCompatAlertDialogStyle);
                    dialog = builder.setTitle("투어 등록 안내")
                            .setMessage("코스 소개를 등록하셔야 투어 등록이 완료됩니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                final Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            response = response.trim();
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(TourRegisterActivity.this, R.style.AppCompatAlertDialogStyle);
                                builder.setMessage("투어등록 성공");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(TourRegisterActivity.this, R.style.AppCompatAlertDialogStyle);
                                dialog = builder.setMessage("투어등록 실패")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                TourRegisterRequest tourRegisterRequest = new TourRegisterRequest(tourID, tourName, tourPrice, tourCity,
                        tourCountry, minNum, maxNum, meetPlace, meetTime, estTime, mapImage, guideID,responseListener);
                RequestQueue queue = Volley.newRequestQueue(TourRegisterActivity.this);
                queue.add(tourRegisterRequest);
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(TourRegisterActivity.this,Detail_Schedule_Activity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(this, "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }
    }

    private void selectGallery() {
        //앨범호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE); //데이터를 이전 액티비티로 돌려줌
    }

    //startActivityForResult로 호출됨
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE: {
                    uri = data.getData(); //갤러리의 uri를 가져옴
                }
                case IMAGE_CODE: {
                    //가져올 이미지 크기를 정하기 위해 CROP 호출
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(uri, "image/*");

                    intent.putExtra("outputX", 800); //crop할 이미지의 가로 크기
                    intent.putExtra("outputY", 500); //crop할 이미지의 세로 크기
                    intent.putExtra("aspectX", 3); //crop의 가로 비율
                    intent.putExtra("aspectY", 2); //crop의 세로 비율
                    intent.putExtra("scale", true);
                    intent.putExtra("return-data", true);

                    startActivityForResult(intent, CROP_CODE); //이미지의 정보를 CROP_CODE로 넘겨줌
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
                        imageCheck = true;
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
            ImgUpLoad imgupload = new ImgUpLoad(TourRegisterActivity.this);
            imgupload.setPath(filePath);
            imgupload.execute(urlString);
        } catch (Exception e) {
            Log.d("Test","out");
        }
    }
}

package com.example.user.goodguide;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class Detail_Schedule_Activity extends AppCompatActivity{
    int num = 0;
    int index = 0;
    int[] map_array = {R.id.img_map1, R.id.img_map2, R.id.img_map3, R.id.img_map4, R.id.img_map5};
    int[] cmr_array = {R.id.img_cmr1, R.id.img_cmr2, R.id.img_cmr3, R.id.img_cmr4, R.id.img_cmr5};
    int[] place_array = {R.id.ed_place1, R.id.ed_place2, R.id.ed_place3, R.id.ed_place4, R.id.ed_place5};
    int[] content_array = {R.id.ed_content1, R.id.ed_content2, R.id.ed_content3, R.id.ed_content4, R.id.ed_content5};
    ImageView[] img_cmr = new ImageView[5];
    ImageView[] img_map = new ImageView[5];
    EditText[] ed_place = new EditText[5];
    EditText[] ed_content = new EditText[5];

    Uri uri;
    final int GALLERY_CODE = 0;
    final int IMAGE_CODE = 1;
    final int CROP_CODE = 2;

    String[] absoultePath = new String[5];
    private AlertDialog dialog;
    AlertDialog.Builder builder;

    TourFragment tourFragment = new TourFragment();
    String tourid = tourFragment.tourID;
    TourRegisterActivity tourRegisterActivity = new TourRegisterActivity();

    boolean[] imageCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바에 홈버튼 생김
        getSupportActionBar().setTitle("코스 소개");
        builder = new AlertDialog.Builder(Detail_Schedule_Activity.this, R.style.AppCompatAlertDialogStyle);
        imageCheck = new boolean[5];
        tourRegisterActivity.registerCheck = false;

        for (int i = 0; i < 5; i++) {
            img_map[i] = findViewById(map_array[i]);
            ed_place[i] = findViewById(place_array[i]);
            img_cmr[i] = findViewById(cmr_array[i]);
            ed_content[i] = findViewById(content_array[i]);
            absoultePath[i] = null;
            imageCheck[i] = false;
        }

        img_cmr[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = 0;
                selectGallery();
            }
        });

        img_cmr[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = 1;
                selectGallery();
            }
        });

        img_cmr[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = 2;
                selectGallery();
            }
        });

        img_cmr[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = 3;
                selectGallery();
            }
        });

        img_cmr[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = 4;
                selectGallery();
            }
        });

        findViewById(R.id.btn_tour_save).setOnClickListener(new View.OnClickListener() {
            String[] tourID, tourNum, tourPlace, tourImage, tourExplain;
            @Override
            public void onClick(View v) {
                tourID = new String[5]; tourNum = new String[5]; tourPlace = new String[5];
                tourImage = new String[5]; tourExplain = new String[5];
                try {
                    for (int i=0; i <= num; i++) {
                        tourID[i]= tourid;
                        tourNum[i] = String.valueOf(i + 1);
                        tourPlace[i] = ed_place[i].getText().toString();
                        tourImage[i] = absoultePath[i].substring(absoultePath[i].length()-17, absoultePath[i].length());
                        tourExplain[i] = ed_content[i].getText().toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                builder.setMessage("성공");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        tourRegisterActivity.registerCheck = true;
                                        finish();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                tourRegisterActivity.registerCheck = false;
                                dialog = builder.setMessage("실패")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            }

                        }  catch (Exception e) {
                            e.printStackTrace();
                            Log.d("Test", e + "!");
                        }
                    }
                };

                if(num == 0){
                    dialog = builder.setTitle("코스 소개 등록 안내")
                            .setMessage("최소 한 개 이상의 정보를 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    tourRegisterActivity.registerCheck = false;
                    return;
                }else if(num > 0){
                    for(int j = 0; j < num; j++){
                        if(ed_place[j].getText().toString().equals("") || ed_content[j].getText().toString().equals("") || imageCheck[j] == false){
                            dialog = builder.setTitle("코스 소개 등록 안내")
                                    .setMessage("장소 이름, 사진, 소개를 모두 입력해주세요.")
                                    .setPositiveButton("확인", null)
                                    .create();
                            dialog.show();
                            tourRegisterActivity.registerCheck = false;
                            return;
                        }
                    }
                }
                tourRegisterActivity.registerCheck = true;
                for (int i=0; i <= num; i++) {
                    Detail_Schedule_Request DRequest = new Detail_Schedule_Request(tourID[i], tourNum[i], tourPlace[i], tourImage[i], tourExplain[i], responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Detail_Schedule_Activity.this);
                    queue.add(DRequest);
                }
                Toast.makeText(getApplication(),"코스 등록이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //툴바 우측 상단에 추가 버튼(옵션 메뉴) 생성
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed(); //뒤로가기
                return true; //http://recipes4dev.tistory.com/141(참고자료)
            case R.id.add: //추가버튼을 클릭하면 xml에 생성한 뷰 보여짐
                if (num < 5) {
                    img_map[num].setVisibility(View.VISIBLE);
                    ed_place[num].setVisibility(View.VISIBLE);
                    img_cmr[num].setVisibility(View.VISIBLE);
                    ed_content[num].setVisibility(View.VISIBLE);
                    num++;
                } else {
                    Toast.makeText(getApplicationContext(), num + "개 까지 등록하실 수 있습니다.", Toast.LENGTH_SHORT).show();
                    num--;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectGallery() {
        //앨범호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE); //데이터를 이전 액티비티로 돌려줌
    }

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
                        img_cmr[index].setImageBitmap(photo); //이미지뷰에 보여줌
                        imageCheck[index] = true;
                        storeIMG(photo, filePath); //이미지를 외부저장소, 앨범에 저장하기 위한 함수 호출
                        absoultePath[index] = filePath;
                        imgupload(absoultePath[index]);//서버에 사진을 업로드하기 위한 함수 호출
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
            ImgUpLoad imgupload = new ImgUpLoad(Detail_Schedule_Activity.this);
            imgupload.setPath(filePath);
            imgupload.execute(urlString);
        } catch (Exception e) {
            Log.d("Test","out");
        }
    }
}



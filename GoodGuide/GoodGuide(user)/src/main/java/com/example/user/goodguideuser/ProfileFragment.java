package com.example.user.goodguideuser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

//정다은 ppt p46 마이페이지창
public class ProfileFragment extends Fragment {
    private AlertDialog dialog;
    public static String TuserName;
    public static String TuserNumber;
    public static String TuserAddress;
    TextView name_Text, phone_Text, address_Text, logoutBtn;
    String userID;
    AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name_Text = view.findViewById(R.id.name_Text);
        phone_Text = view.findViewById(R.id.phone_Text);
        address_Text = view.findViewById(R.id.address_Text);
        logoutBtn = view.findViewById(R.id.logout_Btn);
        builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);

        LoginActivity loginActivity = new LoginActivity();
        userID = loginActivity.userID;

        TextView name_Btn = view.findViewById(R.id.name_Btn);
        name_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NameChange.class);
                startActivity(intent);
            }
        });

        TextView image_Btn = view.findViewById(R.id.image_Btn);
        //사진등록 및 수정 텍스트 눌렀을때 수정페이지로 이동
        image_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ImageChange.class);
                startActivity(intent);
            }
        });

        TextView phone_Btn = view.findViewById(R.id.phone_Btn);
        //전화번호수정 버튼 눌렀을때 수정페이지로 이동
        phone_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PhoneChange.class);
                startActivity(intent);
            }
        });

        TextView address_Btn = view.findViewById(R.id.address_Btn);
        //거주지수정 버튼 눌렀을때 수정페이지로 이동
        address_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddressChange.class);
                startActivity(intent);
            }
        });

        //로그아웃 버튼 눌렀을때 대화상자뜸
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("로그아웃 하시겠습니까?");
                builder.setTitle("Good Guide")
                        .setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }) //네 버튼을 누르면 앱 종료
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }); //아니요 버튼을 누르면 대화상자 사라짐
                AlertDialog alert = builder.create();
                alert.setTitle("Good Guide");
                alert.show();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onList();
    }

    public void onList(){
        //회원 정보 불러들이기
        final Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    response = response.trim();
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    boolean success = jsonResponse.getBoolean("success");
                    String userName = jsonResponse.getString("TuserName");
                    String userNumber = jsonResponse.getString("TuserNumber");
                    String userAddress = jsonResponse.getString("TuserAddress");

                    if(success) {
                        name_Text.setText(userName);
                        phone_Text.setText(userNumber);
                        address_Text.setText(userAddress);
                        TuserName = userName;
                        TuserNumber = userNumber;
                        TuserAddress = userAddress;
                    }else{
                        dialog = builder.setMessage("회원정보 실패")
                                .setNegativeButton("확인",null)
                                .create();
                        dialog.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        TUserInfoRequest userInfoRequest = new TUserInfoRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(userInfoRequest);
    }
}
// https://blog.naver.com/rbamtori/220802348848 대화상자 참고
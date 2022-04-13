package com.example.user.goodguide;

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

public class ProfileFragment extends Fragment {
    private AlertDialog dialog;
    public static String guideName;
    public static String guideNumber;
    public static String guideAddress;
    TextView tvName;
    TextView tvPhone;
    TextView tvAddress;
    String guideID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        LoginActivity loginActivity = new LoginActivity();
        String id = loginActivity.guideID;
        guideID = id;

        tvName = view.findViewById(R.id.name_Text);
        tvPhone = view.findViewById(R.id.phone_Text);
        tvAddress = view.findViewById(R.id.address_Text);

        //이름수정 버튼 눌렀을때 수정페이지로 이동
        final TextView name_Btn = (TextView) view.findViewById(R.id.name_Btn);
        name_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NameChange.class);
                startActivity(intent);
            }
        });

        //사진등록 및 수정 텍스트 눌렀을때 수정페이지로 이동
        final TextView image_Btn = (TextView) view.findViewById(R.id.image_Btn);
        image_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ImageChange.class);
                startActivity(intent);
            }
        });

        //전화번호수정 버튼 눌렀을때 수정페이지로 이동
        final TextView phone_Btn = (TextView) view.findViewById(R.id.phone_Btn);
        phone_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PhoneChange.class);
                startActivity(intent);
            }
        });

        //거주지수정 버튼 눌렀을때 수정페이지로 이동
        final TextView address_Btn = (TextView) view.findViewById(R.id.address_Btn);
        address_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddressChange.class);
                startActivity(intent);
            }
        });

        //로그아웃 버튼 눌렀을때 대화상자뜸
        final TextView logoutBtn = (TextView) view.findViewById(R.id.logout_Btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
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
                    String userName = jsonResponse.getString("userName");
                    String userNumber = jsonResponse.getString("userNumber");
                    String userAddress = jsonResponse.getString("userAddress");

                    if(success) {
                        tvName.setText(userName);
                        tvPhone.setText(userNumber);
                        tvAddress.setText(userAddress);
                        guideName = userName;
                        guideNumber = userNumber;
                        guideAddress = userAddress;
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle);
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

        GuideInfoRequest guideInfoRequest = new GuideInfoRequest(guideID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(guideInfoRequest);
    }
}
// https://blog.naver.com/rbamtori/220802348848 대화상자 참고
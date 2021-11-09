package com.example.mystore;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignInFragment extends Fragment {


    public SignInFragment() {
        // Required empty public constructor
    }

private TextView dontHaveanaccount;
    private FrameLayout ParentframeLayout;
    private EditText email;
    private EditText password;
    private TextView forgotpass;
    private Button button;
    private ImageButton cls_btn;
    private FirebaseAuth firebaseAuth;
    private String emailpattern="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_sign_in, container, false);
       dontHaveanaccount=view.findViewById(R.id.signup_id);
       ParentframeLayout=getActivity().findViewById(R.id.register_framelayout);
       email=view.findViewById(R.id.email_id);
       password=view.findViewById(R.id.pass_id);
       forgotpass=view.findViewById(R.id.frgt_id);
       button=view.findViewById(R.id.signin_id);
       cls_btn=view.findViewById(R.id.cls_btn);
       firebaseAuth=FirebaseAuth.getInstance();

       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());
            }

        });
    email.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            check();

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });
    password.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        check();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkemailandpassword();
        }
    });
    cls_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent main=new Intent(getActivity(),MainActivity.class);
            startActivity(main);
            getActivity().finish();
        }
    });
    }



    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(ParentframeLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
   public void check(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                button.setEnabled(true);
            }else{
                button.setEnabled(false);

            }

        }else{
            button.setEnabled(false);
        }

    }
    public void checkemailandpassword(){
        if(email.getText().toString().matches(emailpattern)){
            if(password.length()>=8){
                button.setEnabled(false);
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent main=new Intent(getActivity(),MainActivity.class);
                                    startActivity(main);
                                    getActivity().finish();
                                }else{
                                    button.setEnabled(true);
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }else{
                Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
        }

    }


}
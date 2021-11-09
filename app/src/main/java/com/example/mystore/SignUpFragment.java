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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }
    private TextView Alreadyhaveanaccount;
    private FrameLayout ParentframeLayout;
    private EditText email;
    private EditText fullname;
    private EditText password;
    private EditText confirmpassword;
    private ImageButton cross;
    private Button button;
    private FirebaseAuth firebaseAuth;
    private String emailpattern="[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseFirestore firebaseFirestore;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        Alreadyhaveanaccount=view.findViewById(R.id.sign_id1);
        ParentframeLayout=getActivity().findViewById(R.id.register_framelayout);
        email=view.findViewById(R.id.email_id1);
        fullname=view.findViewById(R.id.name_id);
        password=view.findViewById(R.id.pass_id1);
        confirmpassword=view.findViewById(R.id.pass_id2);
        cross=view.findViewById(R.id.cls_btn1);
        button=view.findViewById(R.id.signup_id2);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore =FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Alreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();

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
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        confirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailandPassword();

            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
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
    private void checkInputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(fullname.getText())){
                if(!TextUtils.isEmpty(password.getText())&&password.length()>=8) {
                    if(!TextUtils.isEmpty(confirmpassword.getText())){
                        button.setEnabled(true);

                    }else{
                        button.setEnabled(false);

                    }

                }else{
                    button.setEnabled(false);

                }
            }else{
                button.setEnabled(false);

            }

        }else{
            button.setEnabled(false);

        }
    }
    private void checkEmailandPassword(){
        if(email.getText().toString().matches(emailpattern)){
            if(password.getText().toString().equals(confirmpassword.getText().toString())){
                button.setEnabled(false);

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Map<Object,String> userdata=new HashMap<>();
                                    userdata.put("fullname",fullname.getText().toString());
                                    firebaseFirestore.collection("USERS").add(userdata).
                                            addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if(task.isSuccessful()){
                                                        Intent mainIntent=new Intent(getActivity(),MainActivity.class);
                                                        startActivity(mainIntent);
                                                        getActivity().finish();

                                                    }else{
                                                        button.setEnabled(true);
                                                        String error=task.getException().getMessage();
                                                        Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });


                                }else{
                                    button.setEnabled(true);
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }else{
                confirmpassword.setError("Password Doesn't Match");
            }

        }else{
            email.setError("Enter a valid Email");


        }

    }
}
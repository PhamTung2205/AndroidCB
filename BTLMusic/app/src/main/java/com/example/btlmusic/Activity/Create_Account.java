package com.example.btlmusic.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btlmusic.Manager.AccountManager;
import com.example.btlmusic.Opject.Account;
import com.example.btlmusic.R;

import java.util.List;

public class Create_Account extends AppCompatActivity {
    EditText edtcreNameAccount,edtcrePassAccounnt,edtcrePhoneAccount,edtcreConfirmPass;
    Button btnCreateAccount ,btnCreateLogAccount;
    Account account;
    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        getView();
        try {
            accountManager = new AccountManager(this);
            accountManager.open();
        }catch (Exception ex){
            Log.d(ex.getMessage(),"onCreate :");
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        clickCreatAccount();
        loginAcc();

    }
    public void getView(){
        edtcreNameAccount = findViewById(R.id.edt_creNameAccount);
        edtcrePassAccounnt = findViewById(R.id.edt_crePassAccount);
        edtcreConfirmPass = findViewById(R.id.edt_creConfirmPass);
        edtcrePhoneAccount = findViewById(R.id.edt_crePhoneAccount);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnCreateLogAccount = findViewById(R.id.btnCreLogAccount);
    }
    public void clickCreatAccount(){
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rest="";
                boolean flag = true;
                boolean checkAcc = checkAcc();
                String name = edtcreNameAccount.getText().toString().trim();
                String pass = edtcrePassAccounnt.getText().toString().trim();
                String confimpass = edtcreConfirmPass.getText().toString().trim();
                String phone =edtcrePhoneAccount.getText().toString().trim();

                if(name.isEmpty()||pass.isEmpty()||phone.isEmpty()){
                    rest+="Không được để trống các ô\n";
                    flag=false;
                }
                if(pass.compareTo(confimpass)!=0){
                    rest+="Mật khẩu không giống nhau kiểm tra lại\n";
                    flag = false;
                }

                if(phone.length()!=10){
                    rest+="Số điện thoại phải là 10 số\n";
                    flag=false;
                }

                if(!checkAcc){
                    rest+="Số điện thoại đã được sử dụng\n";
                    flag=false;
                }
                if(!flag){
                    Toast.makeText(Create_Account.this,rest,Toast.LENGTH_LONG).show();
                }
                if(flag) {
                    account = new Account();
                    account.setName(name);
                    account.setPass(pass);
                    account.setPhone(phone);
                    account.setImg("logo");

                    try {
                        int i = accountManager.insertAccount(account);
                        if (i < 0) {
                            Toast.makeText(Create_Account.this, "Lỗi không thêm được", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Create_Account.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
                        Toast.makeText(Create_Account.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    public boolean checkAcc(){
        List<Account> accountList = accountManager.selectAllAccount();
        String phone = edtcrePhoneAccount.getText().toString().trim();
        boolean flag = true;
        for (Account item:accountList) {
            if(phone.equals(item.getPhone())){
                flag=false;
            }
        }
        return flag;
    }

    public void loginAcc(){
        btnCreateLogAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
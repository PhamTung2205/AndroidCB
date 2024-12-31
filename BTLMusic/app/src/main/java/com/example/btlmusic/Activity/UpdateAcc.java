package com.example.btlmusic.Activity;

import android.content.Intent;
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

public class UpdateAcc extends AppCompatActivity {
    String SDT="";
    AccountManager accountManager;
    Account account;
    EditText edtNameAcc, edtPassAcc,edtConfirmAcc,edtPhoneAcc;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_acc);
        getView();
        try {
            accountManager = new AccountManager(this);
            accountManager.open();

        }catch (Exception ex){
            Log.d(ex.getMessage(),"onCreate :");
            Toast.makeText(this,"Lỗi :"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("SDT");
        SDT = bundle.getString("SDT");
        selectAcc();
        addView();
        clickUpdate();
    }
    public void getView(){
        edtNameAcc = findViewById(R.id.edt_UpdateNameAcc);
        edtPassAcc = findViewById(R.id.edt_UpdatePassAccount);
        edtConfirmAcc = findViewById(R.id.edt_UpdatePassConfirmPass);
        edtPhoneAcc = findViewById(R.id.edt_UpdatePhoneAccount);
        btnUpdate = findViewById(R.id.btn_UpdateAcc);
    }
    public void addView(){
        edtNameAcc.setText(account.getName().toString().trim());
        edtPhoneAcc.setText(account.getPhone().toString().trim());
    }
    public int check(){
        String name = edtNameAcc.getText().toString().trim();
        String pass = edtPassAcc.getText().toString().trim();
        String cofpass = edtConfirmAcc.getText().toString().trim();
        String phone = edtPhoneAcc.getText().toString().trim();

        int check =0;
        if(phone.length()!=10){

            check=1;
        }
        if(pass.compareTo(cofpass)!=0){
            check=2;
        }
        if(name.isEmpty()||pass.isEmpty()||cofpass.isEmpty()||phone.isEmpty()){

            check=3;
        }
        return check;
    }

    public void clickUpdate(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = account.getId();
                String name = edtNameAcc.getText().toString().trim();
                String pass= edtPassAcc.getText().toString().trim();
                String phone = edtPhoneAcc.getText().toString().trim();
                String img = account.getImg();
                int checkAcc = check();
                String rest = "";
                if(checkAcc==1){
                    rest+="Số điện thoại phải là 10 số\n";
                }
                if(checkAcc==2){
                    rest+="Mật khẩu nhập lại không đúng\n";
                }
                if(checkAcc==3){
                    rest+="Không được để trống\n";
                }
                if(checkAcc==0){
                    rest+="Update thành công\n";
                    accountManager.updateAccount(id,name,pass,phone,img);
                    finish();
                }
                Toast.makeText(UpdateAcc.this,rest,Toast.LENGTH_LONG).show();
            }
        });
    }
    public void selectAcc (){
        List<Account> accountList = accountManager.selectAllAccount();
        for(Account item : accountList){
            if(item.getPhone().equals(SDT)){
                account = item;
            }
        }
    }
}
package com.example.btlmusic.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class Login_Account extends AppCompatActivity {
    EditText edtLoginPhoneAcc , edtLoginPassAcc;
    CheckBox chkLoginSaveAcc;
    Button btnLoginAcc,btnLoginCrAcc,btnInsert,btnSearchSong;
    public static  final String FName ="acount.xml";
    SharedPreferences preferences;
    public static final int MODE = Activity.MODE_PRIVATE;
    Account account;
    AccountManager accountManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_account);
        try {
            accountManager = new AccountManager(this);
            accountManager.open();

        }catch (Exception ex){
            Log.d(ex.getMessage(),"onCreate :");
        }
        getView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        preferences = getSharedPreferences(FName,MODE);
        readAcount();
        clickbtnDangnhap();
        clickbtnTaotaikhoan();
        clickDefault();
        //clickSearchSong();
    }
    private void getView() {
        edtLoginPhoneAcc = findViewById(R.id.edtLogin_PhoneAccount);
        edtLoginPassAcc = findViewById(R.id.edtLogin_PassAccount);
        chkLoginSaveAcc = findViewById(R.id.chkLogin_CheckSave);
        btnLoginAcc = findViewById(R.id.btn_LoginAccount);
        btnLoginCrAcc = findViewById(R.id.btn_LoginCrAccount);
        btnInsert = findViewById(R.id.btndefault);
        //btnSearchSong = findViewById(R.id.btnsearch);
    }
    private void saveAcount(){
        if(chkLoginSaveAcc.isChecked()){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("saveAcc",chkLoginSaveAcc.isChecked());
            editor.putString("nameAcc",edtLoginPhoneAcc.getText().toString());
            editor.putString("passAcc",edtLoginPassAcc.getText().toString());
            editor.commit();
        }
    }
    private void readAcount() {

        boolean isSave = preferences.getBoolean("saveAcc",false);
        if(isSave){
            String name = preferences.getString("nameAcc",null);
            String pass = preferences.getString("passAcc",null);
            edtLoginPhoneAcc.setText(name);
            edtLoginPassAcc.setText(pass);
        }
    }
    private void clickbtnDangnhap(){
        btnLoginAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkAcc = checkAcc();
                String rest ="";
                String phone = edtLoginPhoneAcc.getText().toString().trim();
                String pass = edtLoginPassAcc.getText().toString().trim();
                if(phone.isEmpty()){
                    rest+="Không được để trống\n";
                    edtLoginPhoneAcc.findFocus();
                }
                if(pass.isEmpty()){
                    rest+="Không được để trống\n";
                    edtLoginPassAcc.findFocus();
                }
                if(checkAcc==1){
                    rest+="Đăng nhập thành công\n";
                    saveAcount();
                    Intent intent = new Intent(Login_Account.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("SDT",edtLoginPhoneAcc.getText().toString());
                    intent.putExtra("User",bundle);
                    startActivity(intent);
                }
                if(checkAcc==-1){
                    rest+="Tài khoản không tồn tại\n";
                    edtLoginPhoneAcc.findFocus();
                }
                if(checkAcc==2){
                    rest+="Mật khẩu không đúng\n";
                    edtLoginPassAcc.findFocus();
                }
                if(checkAcc==3){
                    rest+="Tài khoản không tồn tại\n";
                    edtLoginPhoneAcc.findFocus();
                }
                Toast.makeText(Login_Account.this,rest,Toast.LENGTH_LONG).show();
            }
        });
    }

    public int checkAcc(){
        List<Account> accountList = accountManager.selectAllAccount();
        String phone = edtLoginPhoneAcc.getText().toString().trim();
        String pass = edtLoginPassAcc.getText().toString().trim();
        int check =0;
        if(accountList.isEmpty()){
            Toast.makeText(Login_Account.this,"Chưa có tài khoản nào được tạo",Toast.LENGTH_LONG).show();
        }

        for ( Account item : accountList){
            if(phone.equals(item.getPhone())&&pass.equals(item.getPass())){
                check =1;//Đăng nhậpj
                return check;
            }
            if(!phone.equals(item.getPhone())){
                check =-1;

            }
            if(!pass.equals(item.getPass())){
                check =2;
            }
            if(!phone.equals(item.getPhone())&&!pass.equals(item.getPass())){
                check=3;
            }

        }
        return check;
    }

    private void clickbtnTaotaikhoan(){
        btnLoginCrAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Account.this,Create_Account.class);
                startActivity(intent);
            }
        });
    }

   private void clickDefault(){
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Account.this,Default.class);
                startActivity(intent);
            }
        });
    }


     /*<Button
        android:id="@+id/btndefault"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="default"/>
    <Button
        android:id="@+id/btnsearch"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="searchSong"/>

    private void clickSearchSong(){
        btnSearchSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Account.this,SearchSong.class);
                startActivity(intent);
            }
        });
    }*/
}
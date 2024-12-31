package com.example.btlmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btlmusic.Activity.InfoAcc;
import com.example.btlmusic.Activity.Login_Account;
import com.example.btlmusic.Activity.MainActivity;
import com.example.btlmusic.Activity.SonglistActivity;
import com.example.btlmusic.Activity.UpdateAcc;
import com.example.btlmusic.Manager.AccountManager;
import com.example.btlmusic.Opject.Account;
import com.example.btlmusic.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Account extends Fragment {
    AccountManager accountManager;
    String SDT = "";
    Button btnUpdateAcc,btnEndAcc;
    EditText edtNameAcc;
    MainActivity mainActivity;
    SonglistActivity songlistActivity;
    public Account account;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Account.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Account newInstance(String param1, String param2) {
        Fragment_Account fragment = new Fragment_Account();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        getView(view);
        try {
            accountManager = new AccountManager(getContext());
            accountManager.open();
        }catch (Exception e){
            Log.e("DB_ERROR", "Lỗi khi thêm dữ liệu: " + e.getMessage());
            Toast.makeText(getContext(),"Lỗi :"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        mainActivity = (MainActivity) getActivity();
        SDT = mainActivity.SDT;
        account = selectAccount(SDT);
        edtNameAcc.setText(account.getName());
        clickUpdate();
        clickEnd();
        // Inflate the layout for this fragment
        return view;
    }
    //Select Account
    public Account selectAccount(String sdt){
        List<Account> accounts = accountManager.selectAllAccount();
        Account user = new Account();
        for(Account item : accounts){
            if(item.getPhone().compareTo(sdt)==0){
                user = item;
            }
        }
        return user;
    }
    public void getView(View view){
        btnUpdateAcc = view.findViewById(R.id.fragAcc_btnUpdatAcc);
        btnEndAcc = view.findViewById(R.id.fragAcc_btnEndAcc);
        edtNameAcc = view.findViewById(R.id.fragAcc_edtNameAcc);
    }


    public void clickUpdate(){
        btnUpdateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpdateAcc.class);
                Bundle bundle = new Bundle();
                bundle.putString("SDT",SDT);
                intent.putExtra("SDT",bundle);
                startActivity(intent);
            }
        });
    }
    public void clickEnd(){
        btnEndAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Login_Account.class);
                startActivity(intent);

            }
        });
    }

    public void loadData(){}
}
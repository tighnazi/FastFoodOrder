package com.example.project162.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.project162.Domain.Foods;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.project162.Adapter.CartAdapter;
import com.example.project162.Helper.ChangeNumberItemsListener;
import com.example.project162.Helper.ManagmentCart;
import com.example.project162.R;
import com.example.project162.databinding.ActivityCartBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String address = getIntent().getStringExtra("ADDRESS");
        String phone = getIntent().getStringExtra("PHONE");
        String name = getIntent().getStringExtra("NAME");
        managmentCart = new ManagmentCart(this);
        String namecart= managmentCart.getnamecart();
        setVariable();
        calculateCart();
        initList();
        tax = Math.round(managmentCart.getTotalFee() * 0.02 * 100.0) / 100;
        double total = Math.round((managmentCart.getTotalFee() + tax + 10) * 100) / 100;
        Button buttonOk = findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị thông báo thành công
               Toast.makeText(CartActivity.this, "Người dùng"+name+"Thành công! Đặt món ăn tới địa chỉ: " + address + " với số điện thoại: " + phone, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(CartActivity.this,MainActivity.class);
                startActivity(intent);
                saveData(name,address,phone,namecart,total);
            }
        });
    }

    private void initList() {
        if (managmentCart.getListCart().isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);
        } else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, () -> calculateCart());
        binding.cardView.setAdapter(adapter);
    }

    public void calculateCart() {
        double percentTax = 0.02; //percent 2% tax
        double delivery = 10; // 10 Dollar

        tax = Math.round(managmentCart.getTotalFee() * percentTax * 100.0) / 100;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;

        binding.totalFeeTxt.setText("$" + itemTotal);
        binding.taxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);
    }
    private void saveData(String name, String address,String phone,String food,double fee) {
        // Tạo một đối tượng dữ liệu
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("address", address);
        user.put("phone",phone);
        user.put("food",food);
        user.put("fee",fee);
        // Lưu vào Firestore
        db.collection("users").add(user);
    }
    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
    }
}
package com.qr.quickadapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.qr.library.adapter.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rvContent);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        List<String> strings = new ArrayList<>();
        final QuickAdapter<String> adapter = new QuickAdapter<>(R.layout.item_content, R.layout.item_content, R.layout.item_content, strings);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new QuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(QuickAdapter quickAdapter, View view, int position) {
                Log.d("QuickAdapter", "item click : " + position);
                adapter.remove(position);
            }
        });
        adapter.setOnItemLongClickListener(new QuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(QuickAdapter quickAdapter, View view, int position) {
                Log.d("QuickAdapter", "item long click : " + position);
                List<String> strings1 = new ArrayList<>();
                strings1.add("1");
                strings1.add("1");
                strings1.add("1");
                adapter.setNewData(strings1);
                return true;
            }
        });
        adapter.setOnFooterClickListener(new QuickAdapter.OnFooterClickListener() {
            @Override
            public void onFooterClick(QuickAdapter quickAdapter, View view, int position) {
                Log.d("QuickAdapter", "footer click : " + position);
                int size = adapter.getData().size();
                adapter.addData("add data : " + size);
            }
        });
        adapter.setOnHeaderClickListener(new QuickAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(QuickAdapter quickAdapter, View view, int position) {
                Log.d("QuickAdapter", "header click : " + position);
                int size = adapter.getData().size();
                adapter.addData(0, "add data : " + size);
            }
        });
        adapter.setOnFooterLongClickListener(new QuickAdapter.OnFooterLongClickListener() {
            @Override
            public boolean onFooterLongClick(QuickAdapter quickAdapter, View view, int position) {
                Log.d("QuickAdapter", "footer long click : " + position);
                List<String> strings1 = new ArrayList<>();
                strings1.add("1");
                strings1.add("1");
                strings1.add("1");
                adapter.addData(strings1);
                return true;
            }
        });
        adapter.setOnHeaderLongClickListener(new QuickAdapter.OnHeaderLongClickListener() {
            @Override
            public boolean onHeaderLongClick(QuickAdapter quickAdapter, View view, int position) {
                Log.d("QuickAdapter", "header long click : " + position);
                List<String> strings1 = new ArrayList<>();
                strings1.add("1");
                strings1.add("1");
                strings1.add("1");
                adapter.addData(0, strings1);
                return true;
            }
        });
        adapter.setOnHeaderConvertListener(new QuickAdapter.OnHeaderConvertListener() {
            @Override
            public void onHeaderConvert(QuickAdapter.HeaderViewHolder headerViewHolder) {
                AppCompatTextView appCompatTextView = headerViewHolder.getView(R.id.tv_content);
                appCompatTextView.setText("Header");
            }
        });
        adapter.setOnFooterConvertListener(new QuickAdapter.OnFooterConvertListener() {
            @Override
            public void onFooterConvert(QuickAdapter.FooterViewHolder footerViewHolder) {
                AppCompatTextView appCompatTextView = footerViewHolder.getView(R.id.tv_content);
                appCompatTextView.setText("Footer");
            }
        });
        adapter.setOnContentConvertListener(new QuickAdapter.OnContentConvertListener<String>() {
            @Override
            public void onContentConvert(QuickAdapter.ContentViewHolder contentViewHolder, String item) {
                AppCompatTextView appCompatTextView = contentViewHolder.getView(R.id.tv_content);
                appCompatTextView.setText(item);
            }
        });
    }
}

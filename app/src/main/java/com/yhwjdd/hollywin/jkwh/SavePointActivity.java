package com.yhwjdd.hollywin.jkwh;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.View;

import com.yhwjdd.hollywin.jkwh.adapter.ImagePickerListAdapter;
import com.yhwjdd.hollywin.jkwh.adapter.base.AbstractRenderAdapter;
import com.yhwjdd.hollywin.jkwh.adapter.base.AbstractRender;

import com.yhwjdd.hollywin.jkwh.utils.ImageCompressUtils;
//import com.yhwjdd.hollywin.jkwh.utils.QiniuUtils;
//import com.yhwjdd.hollywin.jkwh.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by HollyWin on 2017/7/18.
 */

public class SavePointActivity extends AppCompatActivity {
    public CustomApplication app;
    private String TAG = "监控维护";
    public static final int REQUEST_IMAGE = 21;
    private List<String> mImagePathes = new ArrayList<>();
    private ImagePickerListAdapter mImagePickerAdapter;
    private RecyclerView mImagePickerListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savepoint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                post();
//            }
//        });

        initImagePicker();

    }
    private void initImagePicker() {
        //adapter
        mImagePickerAdapter = new ImagePickerListAdapter(new ArrayList<String>());
        mImagePickerAdapter.setOnItemClickListener(new AbstractRenderAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.d(TAG, "clicked");
            }
        });

        //recycler view
        mImagePickerListView = com.yhwjdd.hollywin.jkwh.utils.ViewUtils.findViewById(this, R.id.list_image_picker);
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);

        mImagePickerListView.setLayoutManager(linearLayoutManager);
        mImagePickerListView.setAdapter(mImagePickerAdapter);

        //footer
        View footer = getLayoutInflater().inflate(R.layout.footer_image_picker, null);
        mImagePickerAdapter.setFooterView(footer);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPickImageGallery();
            }
        });
    }
    private void openPickImageGallery() {
        Intent intent = new Intent(SavePointActivity.this, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        startActivityForResult(intent, REQUEST_IMAGE);
    }
}

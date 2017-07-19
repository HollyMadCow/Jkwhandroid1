package com.yhwjdd.hollywin.jkwh.adapter;

import android.annotation.TargetApi;
import android.view.ViewGroup;


import com.yhwjdd.hollywin.jkwh.adapter.base.AbstractRender;
import com.yhwjdd.hollywin.jkwh.adapter.base.AbstractRenderAdapter;
import com.yhwjdd.hollywin.jkwh.adapter.base.AbstractViewHolder;
import com.yhwjdd.hollywin.jkwh.adapter.render.ImagePickerRender;

import java.util.List;

/**
 * Copyright (C) 2016 Huijimuhe Technologies. All rights reserved.
 * Contact: 20903213@qq.com Zengweizhou
 */
public class ImagePickerListAdapter extends AbstractRenderAdapter<String> {

    public ImagePickerListAdapter(List<String> data) {
        this.mDataset = data;
    }

    public AbstractViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //header view
        AbstractViewHolder holder = super.onCreateViewHolder(viewGroup, viewType);
        if (holder != null) {
            return holder;
        }

        ImagePickerRender render = new ImagePickerRender(viewGroup, this);
        AbstractViewHolder articleHolder = render.getReusableComponent();
        articleHolder.itemView.setTag(android.support.design.R.id.list_item, render);
        return articleHolder;
    }

    @TargetApi(4)
    public void onBindViewHolder(AbstractViewHolder holder, int position) {
        AbstractRender render = (AbstractRender) holder.itemView.getTag(android.support.design.R.id.list_item);
        render.bindData(position);
    }
}


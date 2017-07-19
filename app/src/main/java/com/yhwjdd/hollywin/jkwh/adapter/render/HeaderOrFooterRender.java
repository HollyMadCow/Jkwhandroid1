package com.yhwjdd.hollywin.jkwh.adapter.render;

import android.view.View;

import com.yhwjdd.hollywin.jkwh.adapter.base.AbstractRender;
import com.yhwjdd.hollywin.jkwh.adapter.base.AbstractViewHolder;

public class HeaderOrFooterRender extends AbstractRender {

    private ViewHolder mHolder;

    public HeaderOrFooterRender(View view) {
        this.mHolder = new ViewHolder(view);
    }

    @Override
    public void bindData(int position) {
    }

    @Override
    public AbstractViewHolder getReusableComponent() {
        return this.mHolder;
    }

    public class ViewHolder extends AbstractViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }
}

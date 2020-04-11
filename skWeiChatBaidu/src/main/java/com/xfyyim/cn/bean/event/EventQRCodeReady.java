package com.xfyyim.cn.bean.event;

import android.graphics.Bitmap;

public class EventQRCodeReady {
    private Bitmap bitmap;

    public EventQRCodeReady(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

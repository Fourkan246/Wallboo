package com.example.mobin.wallboo;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class Blurrer {

    public static Bitmap blur(Context context, Bitmap image) {
        int width = Math.round(image.getWidth() * .6f);
        int height = Math.round(image.getHeight() * .6f);

        Bitmap inputstream = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputstream = Bitmap.createBitmap(inputstream);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpin = Allocation.createFromBitmap(rs, inputstream);
        Allocation tmpout = Allocation.createFromBitmap(rs, outputstream);
        scriptIntrinsicBlur.setRadius(1f);
        scriptIntrinsicBlur.setInput(tmpin);
        scriptIntrinsicBlur.forEach(tmpout);
        tmpout.copyTo(outputstream);
//        outputstream = BitmapFactory.decodeStream()

//        ByteArrayOutputStream boutputStream = new ByteArrayOutputStream();
//        outputstream.compress(Bitmap.CompressFormat.JPEG, 100, boutputStream);
//        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(boutputStream.toByteArray()));
        return outputstream;
    }

}

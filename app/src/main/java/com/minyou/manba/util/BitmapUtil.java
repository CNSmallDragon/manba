package com.minyou.manba.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class BitmapUtil {

	/**
	 * 
	 * @param source
	 * @return
	 */
	public static Bitmap createCircleImage(Bitmap source){
		Bitmap target = null;
		if(null == source){
			return target;
		}
		int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(target);
		canvas.drawCircle(length/2, length/2, length/2, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(source, 0, 0, paint);
		return target;
	}

	/**
	 * 转化图片为圆形
	 * @param bitmap
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap){
		Bitmap output = null;
		if(null == bitmap){
			return output;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if(width <= height){
			roundPx = width /2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		}else{
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = dst_top = 0;
			dst_right = dst_bottom = height;
		}
		output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		Paint paint = new Paint();
		final Rect src = new Rect((int)left,(int)top,(int)right,(int)bottom);
		final Rect dst = new Rect((int)dst_left,(int)dst_top,(int)dst_right,(int)dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		
		return output;
	}

}

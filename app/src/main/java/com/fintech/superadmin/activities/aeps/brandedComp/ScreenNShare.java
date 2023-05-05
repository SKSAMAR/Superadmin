package com.fintech.superadmin.activities.aeps.brandedComp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;





public class ScreenNShare
{
  private static Bitmap mergeBitmap(Bitmap bmp1, Bitmap bmp2) {
/*  29 */     Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp2.getHeight() + bmp1.getHeight(), bmp1.getConfig());
/*  30 */     Canvas canvas = new Canvas(bmOverlay);
/*  31 */     canvas.drawBitmap(bmp1, 0.0F, 0.0F, null);
/*  32 */     canvas.drawBitmap(bmp2, 0.0F, bmp1.getHeight(), null);
/*  33 */     return bmOverlay;
  }
  
  private Bitmap getBitmapFromView(View view, int height, int width) {
/*  37 */     Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    
/*  39 */     Canvas canvas = new Canvas(bitmap);
/*  40 */     Drawable bgDrawable = view.getBackground();
/*  41 */     if (bgDrawable != null) {
/*  42 */       bgDrawable.draw(canvas);
    } else {
/*  44 */       canvas.drawColor(-1);
/*  45 */     }  view.draw(canvas);
/*  46 */     return bitmap;
  }
  public void sendImage(ScrollView view, View titlebar, Context context) {
    int bitmap1_Height;
/*  50 */     view.setDrawingCacheEnabled(true);
    
    Activity activity;
/*  53 */     if ((activity = (Activity)context).getWindow().getDecorView().getHeight() > view.getChildAt(0).getHeight()) {
/*  54 */       bitmap1_Height = activity.getWindow().getDecorView().getHeight();
    } else {
/*  56 */       bitmap1_Height = view.getChildAt(0).getHeight();
    } 
/*  58 */     view.setDrawingCacheEnabled(false);

    
/*  61 */     Bitmap bitmap2 = getBitmapFromView((View)view, bitmap1_Height, view.getWidth());
/*  62 */     Bitmap bitmap1 = getBitmapFromView(titlebar, titlebar.getHeight(), titlebar.getWidth());
/*  63 */     Bitmap bitmap = mergeBitmap(bitmap1, bitmap2);
/*  64 */     view.setDrawingCacheEnabled(false);
/*  65 */     store(bitmap, context);
  }

  
  public void sendSimpleImage(View v, Context context) {
/*  70 */     v.setDrawingCacheEnabled(true);
/*  71 */     Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
/*  72 */     v.setDrawingCacheEnabled(false);
/*  73 */     store(bitmap, context);
  }


























  
  private static void shareImage(File file, Context context) {
/* 103 */     Uri uriForFile = null;
/* 104 */     if (Build.VERSION.SDK_INT >= 24) {
/* 105 */       if (file.exists()) {
/* 106 */         uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".files", file);
      }
/* 108 */     } else if (file.exists()) {
/* 109 */       uriForFile = Uri.fromFile(file);
    } 

    
/* 113 */     Intent intent = new Intent();
/* 114 */     intent.setAction("android.intent.action.SEND");
/* 115 */     intent.setType("image/*");
/* 116 */     intent.putExtra("android.intent.extra.SUBJECT", "");
/* 117 */     intent.putExtra("android.intent.extra.TEXT", "");
/* 118 */     intent.putExtra("android.intent.extra.STREAM", (Parcelable)uriForFile);
/* 119 */     String s = "Share Screenshot";
    try {
/* 121 */       context.startActivity(Intent.createChooser(intent, "Share Screenshot"));
/* 122 */     } catch (ActivityNotFoundException ex) {
/* 123 */       Toast.makeText(context, "No App Available", Toast.LENGTH_SHORT).show();
    } 
  }
  
  private static void store(Bitmap finalBitmap, Context context) {
    try {
/* 129 */       shareImage(saveFileToLocal(finalBitmap, context), context);
/* 130 */     } catch (Exception e) {
/* 131 */       e.printStackTrace();
    } 
  }
  
/* 135 */   static String tempDirectoryName = "reports";
  
  public static String getTempFolder(Context context) {
/* 138 */     File tempDirectory = new File(context.getExternalFilesDir(null) + File.separator + tempDirectoryName);
/* 139 */     if (!tempDirectory.exists()) {
/* 140 */       System.out.println("creating directory: temp");
/* 141 */       tempDirectory.mkdir();
    } 
/* 143 */     return tempDirectory.getAbsolutePath();
  }
  
  public static File saveFileToLocal(Bitmap finalBitmap, Context context) throws IOException {
/* 147 */     File tempDirectory = new File(context.getExternalFilesDir(null) + File.separator + tempDirectoryName);
/* 148 */     if (tempDirectory.exists()) {
      try {
/* 150 */         for (File f : tempDirectory.listFiles()) f.delete(); 
/* 151 */       } catch (Exception e) {
/* 152 */         e.printStackTrace();
      } 
    }
/* 155 */     File tempFile = new File(getTempFolder(context), "tran_report.jpg");
/* 156 */     String mFilePath = tempFile.getAbsolutePath();

    
/* 159 */     FileOutputStream fos = null;
    
/* 161 */     File imageFile = null;
/* 162 */     if (mFilePath == null || mFilePath.isEmpty()) {
/* 163 */       imageFile = File.createTempFile(Long.toString((new Date()).getTime()), "invoice");
    } else {
/* 165 */       imageFile = new File(mFilePath);
    } 

    
/* 169 */     File parentFile = imageFile.getParentFile();
/* 170 */     if (parentFile == null) {
/* 171 */       return null;
    }
/* 173 */     if (!parentFile.exists() && !parentFile.mkdirs()) {
/* 174 */       throw new IllegalStateException("Couldn't create directory: " + parentFile);
    }
/* 176 */     boolean fileExists = imageFile.exists();
    
/* 178 */     if (fileExists) {
/* 179 */       fileExists = !imageFile.delete();
    }
    try {
/* 182 */       if (!fileExists)
      {
/* 184 */         fileExists = imageFile.createNewFile();
      }
      
/* 187 */       if (fileExists) {
        
/* 189 */         fos = new FileOutputStream(imageFile);
/* 190 */         finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
/* 191 */         fos.flush();
/* 192 */         fos.close();
      } 
/* 194 */       return imageFile;
/* 195 */     } catch (IOException exception) {
/* 196 */       exception.printStackTrace();
/* 197 */       if (fos != null) {
/* 198 */         fos.close();
      }
/* 200 */       throw exception;
    } 
  }
}

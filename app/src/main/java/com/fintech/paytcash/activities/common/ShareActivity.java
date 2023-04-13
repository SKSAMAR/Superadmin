package com.fintech.paytcash.activities.common;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.fintech.paytcash.R;
import com.fintech.paytcash.util.Accessable;
import com.fintech.paytcash.util.DisplayMessageUtil;
import com.fintech.paytcash.util.ExecuteUtil;
import com.fintech.paytcash.util.PermissionUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class ShareActivity extends BaseActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.share){
            if(Accessable.isAccessable()){
                takeScreenshot();
            }
        }
        else if(item.getItemId() == R.id.homePage){
            ExecuteUtil.ThrowOut(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.response_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void takeScreenshot(){

        PermissionUtil.givePermission(ShareActivity.this, data -> {
            if(data.equals(1)){
                View view = getWindow().getDecorView().getRootView();
                Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                view.draw(canvas);

                File fileScreenShot = new File(ShareActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        Calendar.getInstance().getTime() +".jpg");
                DisplayMessageUtil.loading(ShareActivity.this);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(fileScreenShot);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    share(bitmap);
                }catch (Exception e){
                    DisplayMessageUtil.error(ShareActivity.this,fileScreenShot+"First Exception.. "+ e.getLocalizedMessage());
                }
            }
            else{
                DisplayMessageUtil.error(ShareActivity.this, "Permission Denied");
            }
        });

    }


    public void takeScreenshot(ScrollView scrollView){

        PermissionUtil.givePermission(ShareActivity.this, data -> {
            if(data.equals(1)){
                Bitmap bitmap = getBitmapFromView(scrollView, scrollView.getChildAt(0).getHeight(), scrollView.getChildAt(0).getWidth());
                File fileScreenShot = new File(ShareActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        Calendar.getInstance().getTime() +".jpg");
                DisplayMessageUtil.loading(ShareActivity.this);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(fileScreenShot);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    share(bitmap);
                }catch (Exception e){
                    DisplayMessageUtil.error(ShareActivity.this,fileScreenShot+"First Exception.. "+ e.getLocalizedMessage());
                }
            }
            else{
                DisplayMessageUtil.error(ShareActivity.this, "Permission Denied");
            }
        });

    }


    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(ShareActivity.this.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }


    private void share(Bitmap bitmap){
        Uri uri = getImageUri(bitmap);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        DisplayMessageUtil.dismissDialog();
        startActivity(Intent.createChooser(shareIntent, "Share receipt"));
    }


}

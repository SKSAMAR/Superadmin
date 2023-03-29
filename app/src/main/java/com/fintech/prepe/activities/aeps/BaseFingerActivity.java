package com.fintech.prepe.activities.aeps;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import com.fintech.prepe.activities.common.BaseActivity;

import com.fintech.prepe.deer_listener.FingerPrintListener;
import com.fintech.prepe.util.ViewUtils;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.net.Uri;
import android.widget.Toast;

import com.fintech.prepe.activities.aeps.fingerUtil.AllString;
import com.fintech.prepe.activities.aeps.fingerUtil.DeviceDataModel;
import com.fintech.prepe.activities.aeps.fingerUtil.utilDevices;

import java.util.List;

public class BaseFingerActivity extends BaseActivity implements FingerPrintListener {

    String IciciPidDatas;

    private DeviceDataModel morphoDeviceData;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPidData();

    }

    private void setPidData(){
        //String IciciPidDatas = "<?xml version=\"1.0\"?><PidOptions ver=\"1.0\"><Opts env=\"P\" fCount=\"1\" fType=\"0\" iCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"15000\" wadh=\"E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=\" posh=\"UNKNOWN\" /></PidOptions>";
        String IciciPidDatas = "<?xml version=\"1.0\"?><PidOptions ver=\"1.0\"><Opts env=\"P\" fCount=\"1\" fType=\"2\" iCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"15000\" posh=\"UNKNOWN\" /></PidOptions>";

        intent = new Intent();
        intent.putExtra("icipiddata", "" + IciciPidDatas);
        this.IciciPidDatas = intent.getStringExtra("icipiddata");
    }

    @Override
    public void captureFingerBegin() {
        deviceSelection();
    }

    @Override
    public void checkDevice() {

    }

    @Override
    public void fingerPrintCaptured(String result) {

    }

    @Override
    public void fingerError(String error) {
    }

    private void scanDevice() {
        int indexCount = 50;
        String deviceValue = AllString.getValue(this, AllString.SELECTED_DEVICE_INDEX);
        if (deviceValue != null && deviceValue.length() > 0) {
            indexCount = Integer.parseInt(deviceValue);
        }

        switch(indexCount) {
            case 0:
                this.MantraFinger();
                break;
            case 1:
                this.MorphoDevice();
                break;
            case 2:
                this.TatvikFinger();
                break;
            case 3:
                this.StarTekFinger();
                break;
            case 4:
                this.SecuGenFinger();
                break;
            case 5:
                this.EvoluteFinger();
        }

    }

    private void MorphoDevice() {
        try {
            Intent intent = new Intent("in.gov.uidai.rdservice.fp.INFO");
            intent.setPackage("com.scl.rdservice");
            this.startActivityForResult(intent, 1);
        } catch (Exception e){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Morpho RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    BaseFingerActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.scl.rdservice")));
                } catch (Exception var4) {
                    Toast.makeText(BaseFingerActivity.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();
                    var4.printStackTrace();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        }

    }

    private void MorphoFinger() {
        Intent intent2 = new Intent();
        intent2.setComponent(new ComponentName("com.scl.rdservice", "com.scl.rdservice.FingerCaptureActivity"));
        intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
        intent2.putExtra("PID_OPTIONS", this.IciciPidDatas);
        this.startActivityForResult(intent2, 2);
    }

    private void MantraFinger() {
        try {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.mantra.rdservice", "com.mantra.rdservice.RDServiceActivity"));
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.putExtra("PID_OPTIONS", this.IciciPidDatas);
            this.startActivityForResult(intent2, 3);
        } catch (Exception e){
            e.printStackTrace();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Mantra RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    BaseFingerActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.mantra.rdservice")));
                } catch (Exception var4) {
                    Toast.makeText(BaseFingerActivity.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();
                    var4.printStackTrace();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        }

    }

    private void SecuGenFinger() {
        try {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.secugen.rdservice", "com.secugen.rdservice.Capture"));
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.putExtra("PID_OPTIONS", this.IciciPidDatas);
            this.startActivityForResult(intent2, 4);
        } catch (Exception e){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("SecuGen RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    BaseFingerActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.secugen.rdservice")));
                } catch (Exception var4) {
                    Toast.makeText(BaseFingerActivity.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();
                    var4.printStackTrace();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        }

    }

    private void TatvikFinger() {
        try {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.tatvik.bio.tmf20", "com.tatvik.bio.tmf20.RDMainActivity"));
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.putExtra("PID_OPTIONS", this.IciciPidDatas);
            this.startActivityForResult(intent2, 5);
        } catch (Exception e){
            e.printStackTrace();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Tatvik RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    BaseFingerActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.tatvik.bio.tmf20")));
                } catch (Exception var4) {
                    Toast.makeText(BaseFingerActivity.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();
                    var4.printStackTrace();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        }

    }

    public void StarTekFinger() {
        try {
            Intent intent = new Intent();
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent.setComponent(new ComponentName("com.acpl.registersdk", "com.acpl.registersdk.MainActivity"));
            intent.putExtra("PID_OPTIONS", this.IciciPidDatas);
            this.startActivityForResult(intent, 6);

        }catch (Exception e){
            e.printStackTrace();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Startek RD Service not found. Click OK to download now.");
            alertDialog.setPositiveButton("OK", (dialogInterface, i) -> {
                try {
                    BaseFingerActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.acpl.registersdk")));
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, i) -> dialog.dismiss());
            alertDialog.show();
        }
    }

    private void EvoluteFinger() {
        try {
            Intent intent2 = new Intent();
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.setComponent(new ComponentName("com.evolute.rdservice", "com.evolute.rdservice.RDserviceActivity"));
            intent2.putExtra("PID_OPTIONS", this.IciciPidDatas);
            this.startActivityForResult(intent2, 7);
        }catch (Exception e){
            e.printStackTrace();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Evolute RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    BaseFingerActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.evolute.rdservice")));
                } catch (Exception var4) {
                    Toast.makeText(BaseFingerActivity.this, "Something went wrong.Please try again later.", Toast.LENGTH_SHORT).show();
                    var4.printStackTrace();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DeviceDataModel dataModel;
        switch(requestCode) {
            case 1:
                if (resultCode == -1) {
                    this.morphoDeviceData = (new utilDevices()).morphoDeviceData(BaseFingerActivity.this, data.getStringExtra("DEVICE_INFO"));
                    if (this.morphoDeviceData.getErrCode().equalsIgnoreCase("919")) {
                        this.MorphoFinger();
                    }
                }
                break;
            case 2:
                if (resultCode == -1) {
                    dataModel = (new utilDevices()).morphoFingerData(BaseFingerActivity.this, data.getStringExtra("PID_DATA"), this.morphoDeviceData);
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        fingerPrintCaptured(data.getStringExtra("PID_DATA"));
                    } else if (utilDevices.getPreferredPackage(this, "com.scl.rdservice").equalsIgnoreCase("")) {
                        Toast.makeText(this, dataModel.getErrCode() + " : Morpho " + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        (new utilDevices()).clearDefaultSetting(this, utilDevices.getPreferredPackage(this, "com.scl.rdservice"));
                    }
                }
                break;
            case 3:
                if (resultCode == -1) {
                    dataModel = (new utilDevices()).mantraData(BaseFingerActivity.this, data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        fingerPrintCaptured(data.getStringExtra("PID_DATA"));
                    } else if (utilDevices.getPreferredPackage(this, "com.mantra.rdservice").equalsIgnoreCase("")) {
                        Toast.makeText(this, dataModel.getErrCode() + " : Mantra " + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        (new utilDevices()).clearDefaultSetting(this, utilDevices.getPreferredPackage(this, "com.mantra.rdservice"));
                    }
                }
                break;
            case 4:
                if (resultCode == -1) {
                    dataModel = (new utilDevices()).secugenData(BaseFingerActivity.this, data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        fingerPrintCaptured(data.getStringExtra("PID_DATA"));
                    } else if (utilDevices.getPreferredPackage(this, "com.secugen.rdservice").equalsIgnoreCase("")) {
                        Toast.makeText(this, dataModel.getErrCode() + " : Secugen " + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        (new utilDevices()).clearDefaultSetting(this, utilDevices.getPreferredPackage(this, "com.secugen.rdservice"));
                    }
                }
                break;
            case 5:
                if (resultCode == -1) {
                    dataModel = (new utilDevices()).tatvikData(BaseFingerActivity.this, data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        fingerPrintCaptured(data.getStringExtra("PID_DATA"));
                    } else if (utilDevices.getPreferredPackage(this, "com.tatvik.bio.tmf20").equalsIgnoreCase("")) {

                        Toast.makeText(this, dataModel.getErrCode() + " : Tatvik " + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        (new utilDevices()).clearDefaultSetting(this, utilDevices.getPreferredPackage(this, "com.tatvik.bio.tmf20"));
                    }
                }
                break;
            case 6:
                if (resultCode == -1) {
                    dataModel = (new utilDevices()).starTekData(BaseFingerActivity.this, data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        fingerPrintCaptured(data.getStringExtra("PID_DATA"));
                    } else if (utilDevices.getPreferredPackage(this, "com.acpl.registersdk").equalsIgnoreCase("")) {

                        Toast.makeText(this, dataModel.getErrCode() + " : Startek " + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        (new utilDevices()).clearDefaultSetting(this, "com.acpl.registersdk");
                    }
                }
                break;
            case 7:
                if (resultCode == -1) {
                    dataModel = (new utilDevices()).EvoluteData(BaseFingerActivity.this, data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        fingerPrintCaptured(data.getStringExtra("PID_DATA"));
                    } else if (utilDevices.getPreferredPackage(this, "com.evolute.rdservice").equalsIgnoreCase("")) {
                        Toast.makeText(this, dataModel.getErrCode() + " : Evolute" + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        (new utilDevices()).clearDefaultSetting(this, utilDevices.getPreferredPackage(this, "com.evolute.rdservice"));
                    }
                }
        }

    }

    private void deviceSelection() {
        List<String> deviceType = new ArrayList<>();
        deviceType.add("Mantra");
        deviceType.add("Morpho");
        deviceType.add("Tatvik");
        deviceType.add("Startek");
        deviceType.add("Secugen");
        deviceType.add("Evolute");

        ViewUtils.onSpinnerViewFinger("Select Fingerprint Device", BaseFingerActivity.this, deviceType, which->{

            AllString.setValue(BaseFingerActivity.this, AllString.SELECTED_DEVICE, deviceType.get(which));
            AllString.setValue(BaseFingerActivity.this, AllString.SELECTED_DEVICE_INDEX, String.valueOf(which));
            scanDevice();
        });
    }


}
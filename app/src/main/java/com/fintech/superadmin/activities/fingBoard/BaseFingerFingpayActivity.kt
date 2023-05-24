package com.fintech.superadmin.activities.fingBoard

import android.app.AlertDialog
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.fintech.superadmin.activities.aeps.fingerUtil.AllString
import com.fintech.superadmin.activities.aeps.fingerUtil.DeviceDataModel
import com.fintech.superadmin.activities.aeps.fingerUtil.utilDevices
import com.fintech.superadmin.clean.common.BaseComponentAct
import com.fintech.superadmin.deer_listener.FingerPrintListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFingerFingpayActivity : BaseComponentAct(), FingerPrintListener {
    
    var IciciPidDatas: String? = null
    private var morphoDeviceData: DeviceDataModel? = null
    lateinit var myIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPidData()
    }

    private fun setPidData() {
        val IciciPidDatas =
            "<?xml version=\"1.0\"?><PidOptions ver=\"1.0\"><Opts env=\"P\" fCount=\"1\" fType=\"2\" iCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"15000\" wadh=\"E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=\" posh=\"UNKNOWN\" /></PidOptions>"
        //        String IciciPidDatas = "<?xml version=\"1.0\"?><PidOptions ver=\"1.0\"><Opts env=\"P\" fCount=\"1\" fType=\"2\" iCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"15000\" posh=\"UNKNOWN\" /></PidOptions>";
        myIntent = Intent()
        myIntent.putExtra("icipiddata", "" + IciciPidDatas)
        this.IciciPidDatas = myIntent.getStringExtra("icipiddata")
    }

    override fun captureFingerBegin() {
        deviceSelection()
    }

    override fun checkDevice() {}
    override fun fingerPrintCaptured(result: String) {}
    override fun fingerError(error: String) {}
    private fun scanDevice() {
        var indexCount = 50
        val deviceValue = AllString.getValue(this, AllString.SELECTED_DEVICE_INDEX)
        if (deviceValue != null && deviceValue.length > 0) {
            indexCount = deviceValue.toInt()
        }
        when (indexCount) {
            0 -> MantraFinger()
            1 -> MorphoDevice()
            2 -> TatvikFinger()
            3 -> StarTekFinger()
            4 -> SecuGenFinger()
            5 -> {
                EvoluteFinger()
                AnyFinger()
            }
            else -> AnyFinger()
        }
    }

    private fun MorphoDevice() {
        try {
            val myIntent = Intent("in.gov.uidai.rdservice.fp.INFO")
            myIntent.setPackage("com.scl.rdservice")
            this.startActivityForResult(myIntent, 1)
        } catch (e: Exception) {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Get Service")
            alertDialog.setMessage("Morpho RD Services Not Found.Click OK to Download Now.")
            alertDialog.setPositiveButton(
                "OK"
            ) { dialog: DialogInterface?, which: Int ->
                try {
                    this@BaseFingerFingpayActivity.startActivity(
                        Intent(
                            "android.myIntent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=com.scl.rdservice")
                        )
                    )
                } catch (var4: Exception) {
                    Toast.makeText(
                        this@BaseFingerFingpayActivity,
                        "Something went wrong.Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                    var4.printStackTrace()
                }
            }
            alertDialog.setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            alertDialog.show()
        }
    }

    private fun MorphoFinger() {
        val myIntent2 = Intent()
        myIntent2.component =
            ComponentName("com.scl.rdservice", "com.scl.rdservice.FingerCaptureActivity")
        myIntent2.action = "in.gov.uidai.rdservice.fp.CAPTURE"
        myIntent2.putExtra("PID_OPTIONS", IciciPidDatas)
        this.startActivityForResult(myIntent2, 2)
    }

    private fun AnyFinger() {
        try {
            val myIntent2 = Intent()
            myIntent2.action = "in.gov.uidai.rdservice.fp.CAPTURE"
            myIntent2.putExtra("PID_OPTIONS", IciciPidDatas)
            this.startActivityForResult(myIntent2, 3)
        } catch (e: Exception) {
            e.printStackTrace()
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Get Service")
            alertDialog.setMessage("Mantra RD Services Not Found.Click OK to Download Now.")
            alertDialog.setPositiveButton(
                "OK"
            ) { dialog: DialogInterface?, which: Int ->
                try {
                    this@BaseFingerFingpayActivity.startActivity(
                        Intent(
                            "android.myIntent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=com.mantra.rdservice")
                        )
                    )
                } catch (var4: Exception) {
                    Toast.makeText(
                        this@BaseFingerFingpayActivity,
                        "Something went wrong.Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                    var4.printStackTrace()
                }
            }
            alertDialog.setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            alertDialog.show()
        }
    }

    private fun MantraFinger() {
        try {
            val myIntent2 = Intent()
            myIntent2.component =
                ComponentName("com.mantra.rdservice", "com.mantra.rdservice.RDServiceActivity")
            myIntent2.action = "in.gov.uidai.rdservice.fp.CAPTURE"
            myIntent2.putExtra("PID_OPTIONS", IciciPidDatas)
            this.startActivityForResult(myIntent2, 3)
        } catch (e: Exception) {
            e.printStackTrace()
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Get Service")
            alertDialog.setMessage("Mantra RD Services Not Found.Click OK to Download Now.")
            alertDialog.setPositiveButton(
                "OK"
            ) { dialog: DialogInterface?, which: Int ->
                try {
                    this@BaseFingerFingpayActivity.startActivity(
                        Intent(
                            "android.myIntent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=com.mantra.rdservice")
                        )
                    )
                } catch (var4: Exception) {
                    Toast.makeText(
                        this@BaseFingerFingpayActivity,
                        "Something went wrong.Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                    var4.printStackTrace()
                }
            }
            alertDialog.setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            alertDialog.show()
        }
    }

    private fun SecuGenFinger() {
        try {
            val myIntent2 = Intent()
            myIntent2.component =
                ComponentName("com.secugen.rdservice", "com.secugen.rdservice.Capture")
            myIntent2.action = "in.gov.uidai.rdservice.fp.CAPTURE"
            myIntent2.putExtra("PID_OPTIONS", IciciPidDatas)
            this.startActivityForResult(myIntent2, 4)
        } catch (e: Exception) {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Get Service")
            alertDialog.setMessage("SecuGen RD Services Not Found.Click OK to Download Now.")
            alertDialog.setPositiveButton(
                "OK"
            ) { dialog: DialogInterface?, which: Int ->
                try {
                    this@BaseFingerFingpayActivity.startActivity(
                        Intent(
                            "android.myIntent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=com.secugen.rdservice")
                        )
                    )
                } catch (var4: Exception) {
                    Toast.makeText(
                        this@BaseFingerFingpayActivity,
                        "Something went wrong.Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                    var4.printStackTrace()
                }
            }
            alertDialog.setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            alertDialog.show()
        }
    }

    private fun TatvikFinger() {
        try {
            val myIntent2 = Intent()
            myIntent2.component =
                ComponentName("com.tatvik.bio.tmf20", "com.tatvik.bio.tmf20.RDMainActivity")
            myIntent2.action = "in.gov.uidai.rdservice.fp.CAPTURE"
            myIntent2.putExtra("PID_OPTIONS", IciciPidDatas)
            this.startActivityForResult(myIntent2, 5)
        } catch (e: Exception) {
            e.printStackTrace()
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Get Service")
            alertDialog.setMessage("Tatvik RD Services Not Found.Click OK to Download Now.")
            alertDialog.setPositiveButton(
                "OK"
            ) { dialog: DialogInterface?, which: Int ->
                try {
                    this@BaseFingerFingpayActivity.startActivity(
                        Intent(
                            "android.myIntent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=com.tatvik.bio.tmf20")
                        )
                    )
                } catch (var4: Exception) {
                    Toast.makeText(
                        this@BaseFingerFingpayActivity,
                        "Something went wrong.Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                    var4.printStackTrace()
                }
            }
            alertDialog.setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            alertDialog.show()
        }
    }

    fun StarTekFinger() {
        try {
            val myIntent = Intent()
            myIntent.action = "in.gov.uidai.rdservice.fp.CAPTURE"
            myIntent.component =
                ComponentName("com.acpl.registersdk", "com.acpl.registersdk.MainActivity")
            myIntent.putExtra("PID_OPTIONS", IciciPidDatas)
            this.startActivityForResult(myIntent, 6)
        } catch (e: Exception) {
            e.printStackTrace()
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Get Service")
            alertDialog.setMessage("Startek RD Service not found. Click OK to download now.")
            alertDialog.setPositiveButton(
                "OK"
            ) { dialogInterface: DialogInterface?, i: Int ->
                try {
                    this@BaseFingerFingpayActivity.startActivity(
                        Intent(
                            "android.myIntent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=com.acpl.registersdk")
                        )
                    )
                } catch (var4: Exception) {
                    var4.printStackTrace()
                }
            }
            alertDialog.setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, i: Int -> dialog.dismiss() }
            alertDialog.show()
        }
    }

    private fun EvoluteFinger() {
        try {
            val myIntent2 = Intent()
            myIntent2.action = "in.gov.uidai.rdservice.fp.CAPTURE"
            myIntent2.component =
                ComponentName("com.evolute.rdservice", "com.evolute.rdservice.RDserviceActivity")
            myIntent2.putExtra("PID_OPTIONS", IciciPidDatas)
            this.startActivityForResult(myIntent2, 7)
        } catch (e: Exception) {
            e.printStackTrace()
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Get Service")
            alertDialog.setMessage("Evolute RD Services Not Found.Click OK to Download Now.")
            alertDialog.setPositiveButton(
                "OK"
            ) { dialog: DialogInterface?, which: Int ->
                try {
                    this@BaseFingerFingpayActivity.startActivity(
                        Intent(
                            "android.myIntent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=com.evolute.rdservice")
                        )
                    )
                } catch (var4: Exception) {
                    Toast.makeText(
                        this@BaseFingerFingpayActivity,
                        "Something went wrong.Please try again later.",
                        Toast.LENGTH_SHORT
                    ).show()
                    var4.printStackTrace()
                }
            }
            alertDialog.setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            alertDialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val dataModel: DeviceDataModel
        when (requestCode) {
            1 -> if (resultCode == -1) {
                morphoDeviceData = utilDevices().morphoDeviceData(
                    this@BaseFingerFingpayActivity,
                    data!!.getStringExtra("DEVICE_INFO")
                )
                if (morphoDeviceData!!.errCode.equals("919", ignoreCase = true)) {
                    MorphoFinger()
                }
            }
            2 -> if (resultCode == -1) {
                dataModel = utilDevices().morphoFingerData(
                    this@BaseFingerFingpayActivity, data!!.getStringExtra("PID_DATA"),
                    morphoDeviceData
                )
                if (dataModel.errCode.equals("0", ignoreCase = true)) {
                    fingerPrintCaptured(data.getStringExtra("PID_DATA")!!)
                } else if (utilDevices.getPreferredPackage(this, "com.scl.rdservice")
                        .equals("", ignoreCase = true)
                ) {
                    Toast.makeText(
                        this,
                        dataModel.errCode + " : Morpho " + dataModel.errMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    utilDevices().clearDefaultSetting(
                        this,
                        utilDevices.getPreferredPackage(this, "com.scl.rdservice")
                    )
                }
            }
            3 -> if (resultCode == -1) {
                dataModel = utilDevices().mantraData(
                    this@BaseFingerFingpayActivity,
                    data!!.getStringExtra("PID_DATA")
                )
                if (dataModel.errCode.equals("0", ignoreCase = true)) {
                    fingerPrintCaptured(data.getStringExtra("PID_DATA")!!)
                } else if (utilDevices.getPreferredPackage(this, "com.mantra.rdservice")
                        .equals("", ignoreCase = true)
                ) {
                    Toast.makeText(
                        this,
                        dataModel.errCode + " : Mantra " + dataModel.errMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    utilDevices().clearDefaultSetting(
                        this,
                        utilDevices.getPreferredPackage(this, "com.mantra.rdservice")
                    )
                }
            }
            4 -> if (resultCode == -1) {
                dataModel = utilDevices().secugenData(
                    this@BaseFingerFingpayActivity,
                    data!!.getStringExtra("PID_DATA")
                )
                if (dataModel.errCode.equals("0", ignoreCase = true)) {
                    fingerPrintCaptured(data.getStringExtra("PID_DATA")!!)
                } else if (utilDevices.getPreferredPackage(this, "com.secugen.rdservice")
                        .equals("", ignoreCase = true)
                ) {
                    Toast.makeText(
                        this,
                        dataModel.errCode + " : Secugen " + dataModel.errMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    utilDevices().clearDefaultSetting(
                        this,
                        utilDevices.getPreferredPackage(this, "com.secugen.rdservice")
                    )
                }
            }
            5 -> if (resultCode == -1) {
                dataModel = utilDevices().tatvikData(
                    this@BaseFingerFingpayActivity,
                    data!!.getStringExtra("PID_DATA")
                )
                if (dataModel.errCode.equals("0", ignoreCase = true)) {
                    fingerPrintCaptured(data.getStringExtra("PID_DATA")!!)
                } else if (utilDevices.getPreferredPackage(this, "com.tatvik.bio.tmf20")
                        .equals("", ignoreCase = true)
                ) {
                    Toast.makeText(
                        this,
                        dataModel.errCode + " : Tatvik " + dataModel.errMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    utilDevices().clearDefaultSetting(
                        this,
                        utilDevices.getPreferredPackage(this, "com.tatvik.bio.tmf20")
                    )
                }
            }
            6 -> if (resultCode == -1) {
                dataModel = utilDevices().starTekData(
                    this@BaseFingerFingpayActivity,
                    data!!.getStringExtra("PID_DATA")
                )
                if (dataModel.errCode.equals("0", ignoreCase = true)) {
                    fingerPrintCaptured(data.getStringExtra("PID_DATA")!!)
                } else if (utilDevices.getPreferredPackage(this, "com.acpl.registersdk")
                        .equals("", ignoreCase = true)
                ) {
                    Toast.makeText(
                        this,
                        dataModel.errCode + " : Startek " + dataModel.errMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    utilDevices().clearDefaultSetting(this, "com.acpl.registersdk")
                }
            }
            7 -> if (resultCode == -1) {
                dataModel = utilDevices().EvoluteData(
                    this@BaseFingerFingpayActivity,
                    data!!.getStringExtra("PID_DATA")
                )
                if (dataModel.errCode.equals("0", ignoreCase = true)) {
                    fingerPrintCaptured(data.getStringExtra("PID_DATA")!!)
                } else if (utilDevices.getPreferredPackage(this, "com.evolute.rdservice")
                        .equals("", ignoreCase = true)
                ) {
                    Toast.makeText(
                        this,
                        dataModel.errCode + " : Evolute" + dataModel.errMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    utilDevices().clearDefaultSetting(
                        this,
                        utilDevices.getPreferredPackage(this, "com.evolute.rdservice")
                    )
                }
            }
        }
    }

    private fun deviceSelection() {
        val deviceType: MutableList<String> = ArrayList()
        deviceType.add("Mantra")
        deviceType.add("Morpho")
        deviceType.add("Tatvik")
        deviceType.add("Startek")
        deviceType.add("Secugen")
        deviceType.add("Evolute")
        scanDevice()
    }
}
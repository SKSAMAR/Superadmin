package com.fintech.petoindia.activities.aeps.fingerUtil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class utilDevices {
    public utilDevices() {

    }

    public DeviceDataModel morphoDeviceData(Context context, String mainData) {
        DeviceDataModel dataModel = new DeviceDataModel();
        dataModel.setErrMsg("Please connect your Morpho device!");
        dataModel.setErrCode("111");

        try {
            if (mainData != null) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));
                NodeList nodeList = doc.getElementsByTagName("DeviceInfo");
                NodeList n2 = ((Element)nodeList.item(0)).getElementsByTagName("additional_info");
                if (n2.item(0) == null) {
                    Toast.makeText(context, "Please connect your Morpho Device!", Toast.LENGTH_SHORT).show();
                } else {
                    Element serialNo = (Element)n2.item(0).getChildNodes().item(1);
                    dataModel.setSrno("" + serialNo.getAttribute("value"));
                    dataModel.setSysid("" + serialNo.getAttribute("value"));
                    dataModel.setTs("" + getTimeStamp());
                    if (!dataModel.getSrno().equalsIgnoreCase("") || !dataModel.getSysid().equalsIgnoreCase("")) {
                        dataModel.setErrCode("919");
                    }
                }
            }
        } catch (Exception var10) {
            Toast.makeText(context, "Please check your Morpho device or contact customer support!", Toast.LENGTH_SHORT).show();
            var10.printStackTrace();
        }

        return dataModel;
    }

    public static String getTimeStamp() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
        calendar.setTime(date);
        return sdf.format(calendar.getTime());
    }

    public DeviceDataModel morphoFingerData(Context context, String mainData, DeviceDataModel morphoDeviceData) {
        DeviceDataModel dataModel = new DeviceDataModel();
        dataModel.setErrMsg("Please connect your Morpho device!");
        dataModel.setErrCode("111");

        try {
            if (mainData != null) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));
                Node node = doc.getElementsByTagName("PidData").item(0);
                Element element = (Element)node;
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                Element element1 = (Element)nodeList1.item(0);
                dataModel.setErrCode(element1.getAttribute("errCode"));
                dataModel.setErrMsg(element1.getAttribute("errInfo"));
                dataModel.setNmPoints(element1.getAttribute("nmPoints"));
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    Element element2 = (Element)nodeList2.item(0);
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    Element element3 = (Element)nodeList3.item(0);
                    dataModel.setFingerData("" + getValue("Data", element));
                    dataModel.setHmac("" + getValue("Hmac", element));
                    dataModel.setSkey("" + getValue("Skey", element));
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    dataModel.setSrno("" + morphoDeviceData.getSrno());
                    dataModel.setSysid("" + morphoDeviceData.getSysid());
                    dataModel.setTs("" + morphoDeviceData.getTs());
                    Toast.makeText(context, "Finger Captured Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception var16) {
            dataModel.setErrMsg("Please check your Morpho device or contact customer support!");
            var16.printStackTrace();
        }

        return dataModel;
    }

    public DeviceDataModel mantraData(Context context, String mainData) {
        DeviceDataModel dataModel = new DeviceDataModel();
        dataModel.setErrMsg("Please connect your Mantra device!");
        dataModel.setErrCode("111");

        try {
            if (mainData != null) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes(StandardCharsets.UTF_8)));
                Node node = doc.getElementsByTagName("PidData").item(0);
                Element element = (Element)node;
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                Element element1 = (Element)nodeList1.item(0);
                dataModel.setErrCode(element1.getAttribute("errCode"));
                dataModel.setErrMsg(element1.getAttribute("errInfo"));
                dataModel.setNmPoints(element1.getAttribute("nmPoints"));
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    Element element2 = (Element)nodeList2.item(0);
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    Element element3 = (Element)nodeList3.item(0);
                    NodeList nodeList4 = ((Element)nodeList3.item(0)).getElementsByTagName("additional_info");
                    Element element4 = (Element)nodeList4.item(0).getChildNodes().item(1);
                    String srno = element4.getAttribute("value");
                    element4 = (Element)nodeList4.item(0).getChildNodes().item(3);
                    String sysid = element4.getAttribute("value");
                    element4 = (Element)nodeList4.item(0).getChildNodes().item(5);
                    String ts = element4.getAttribute("value");
                    dataModel.setFingerData("" + getValue("Data", element));
                    dataModel.setHmac("" + getValue("Hmac", element));
                    dataModel.setSkey("" + getValue("Skey", element));
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    dataModel.setSrno("" + srno);
                    dataModel.setSysid("" + sysid);
                    dataModel.setTs("" + ts);
                    Toast.makeText(context, "Finger Captured Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception var20) {
            dataModel.setErrMsg("Please check your Mantra device or contact customer support!");
            var20.printStackTrace();
        }

        return dataModel;
    }

    public DeviceDataModel secugenData(Context context, String mainData) {
        DeviceDataModel dataModel = new DeviceDataModel();
        dataModel.setErrMsg("Please connect your Secugen device!");
        dataModel.setErrCode("111");

        try {
            if (mainData != null) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));
                Node node = doc.getElementsByTagName("PidData").item(0);
                Element element = (Element)node;
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                Element element1 = (Element)nodeList1.item(0);
                dataModel.setErrCode(element1.getAttribute("errCode"));
                dataModel.setErrMsg(element1.getAttribute("errInfo"));
                dataModel.setNmPoints(element1.getAttribute("nmPoints"));
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    Element element2 = (Element)nodeList2.item(0);
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    Element element3 = (Element)nodeList3.item(0);
                    NodeList nodeList4 = ((Element)nodeList3.item(0)).getElementsByTagName("additional_info");
                    Element element4 = (Element)nodeList4.item(0).getChildNodes().item(1);
                    dataModel.setFingerData("" + getValue("Data", element));
                    dataModel.setHmac("" + getValue("Hmac", element));
                    dataModel.setSkey("" + getValue("Skey", element));
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    dataModel.setSrno("" + element4.getAttribute("value"));
                    dataModel.setSysid("" + element4.getAttribute("value"));
                    dataModel.setTs("" + getTimeStamp());
                    Toast.makeText(context, "Finger Captured Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception var17) {
            dataModel.setErrMsg("Please check your Secugen device or contact customer support!");
            var17.printStackTrace();
        }

        return dataModel;
    }

    public DeviceDataModel tatvikData(Context context, String mainData) {
        DeviceDataModel dataModel = new DeviceDataModel();
        dataModel.setErrMsg("Please connect your Tatvik device!");
        dataModel.setErrCode("111");

        try {
            if (mainData != null) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));
                Node node = doc.getElementsByTagName("PidData").item(0);
                Element element = (Element)node;
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                Element element1 = (Element)nodeList1.item(0);
                dataModel.setErrCode(element1.getAttribute("errCode"));
                dataModel.setErrMsg(element1.getAttribute("errInfo"));
                dataModel.setNmPoints(element1.getAttribute("nmPoints"));
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    Element element2 = (Element)nodeList2.item(0);
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    Element element3 = (Element)nodeList3.item(0);
                    NodeList nodeList4 = ((Element)nodeList3.item(0)).getElementsByTagName("additional_info");
                    Element element4 = (Element)nodeList4.item(0).getChildNodes().item(0);
                    dataModel.setFingerData("" + getValue("Data", element));
                    dataModel.setHmac("" + getValue("Hmac", element));
                    dataModel.setSkey("" + getValue("Skey", element));
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    dataModel.setSrno("" + element4.getAttribute("value"));
                    dataModel.setSysid("" + element4.getAttribute("value"));
                    dataModel.setTs("" + getTimeStamp());
                    Toast.makeText(context, "Finger Captured Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception var17) {
            dataModel.setErrMsg("Please check your Tatvik device or contact customer support!");
            var17.printStackTrace();
        }

        return dataModel;
    }

    public DeviceDataModel starTekData(Context context, String mainData) {
        DeviceDataModel dataModel = new DeviceDataModel();
        dataModel.setErrMsg("Please connect your Startek device!");
        dataModel.setErrCode("111");

        try {
            if (mainData != null) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));
                Node node = doc.getElementsByTagName("PidData").item(0);
                Element element = (Element)node;
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                Element element1 = (Element)nodeList1.item(0);
                dataModel.setErrCode(element1.getAttribute("errCode"));
                dataModel.setErrMsg(element1.getAttribute("errInfo"));
                dataModel.setNmPoints(element1.getAttribute("nmPoints"));
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    Element element2 = (Element)nodeList2.item(0);
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    Element element3 = (Element)nodeList3.item(0);
                    NodeList nodeList4 = ((Element)nodeList3.item(0)).getElementsByTagName("additional_info");
                    Element element4 = (Element)nodeList4.item(0).getChildNodes().item(0);
                    dataModel.setFingerData("" + getValue("Data", element));
                    dataModel.setHmac("" + getValue("Hmac", element));
                    dataModel.setSkey("" + getValue("Skey", element));
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    dataModel.setSrno("" + element4.getAttribute("value"));
                    dataModel.setSysid("" + element4.getAttribute("value"));
                    dataModel.setTs("" + getTimeStamp());
                    Toast.makeText(context, "Finger Captured Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception var17) {
            dataModel.setErrMsg("Please check your Startek device or contact customer support!");
            var17.printStackTrace();
        }

        return dataModel;
    }

    public DeviceDataModel EvoluteData(Context context, String mainData) {
        DeviceDataModel dataModel = new DeviceDataModel();
        dataModel.setErrMsg("Please connect your Evolute device!");
        dataModel.setErrCode("111");

        try {
            if (mainData != null) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));
                Node node = doc.getElementsByTagName("PidData").item(0);
                Element element = (Element)node;
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                Element element1 = (Element)nodeList1.item(0);
                dataModel.setErrCode(element1.getAttribute("errCode"));
                dataModel.setErrMsg(element1.getAttribute("errInfo"));
                dataModel.setNmPoints(element1.getAttribute("nmPoints"));
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    Element element2 = (Element)nodeList2.item(0);
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    Element element3 = (Element)nodeList3.item(0);
                    NodeList nodeList4 = ((Element)nodeList3.item(0)).getElementsByTagName("additional_info");
                    Element element4 = (Element)nodeList4.item(0).getChildNodes().item(0);
                    dataModel.setFingerData("" + getValue("Data", element));
                    dataModel.setHmac("" + getValue("Hmac", element));
                    dataModel.setSkey("" + getValue("Skey", element));
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    dataModel.setSrno("" + element4.getAttribute("value"));
                    dataModel.setSysid("" + element4.getAttribute("value"));
                    dataModel.setTs("" + getTimeStamp());
                    Toast.makeText(context, "Finger Captured Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception var17) {
            dataModel.setErrMsg("Please check your Startek device or contact customer support!");
            var17.printStackTrace();
        }

        return dataModel;
    }

    private static String getValue(String mainStr, Element element) {
        NodeList nodeList = element.getElementsByTagName(mainStr).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public static String getPreferredPackage(Context context, String deviceId) {
        String data = "";

        try {
            Intent intent = new Intent();
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            PackageManager packageManager = context.getPackageManager();
            ResolveInfo resolveInfo = packageManager.resolveActivity(intent, 0);
            String temp = resolveInfo.activityInfo.packageName;
            if (!temp.equalsIgnoreCase("android") && !temp.equalsIgnoreCase(deviceId)) {
                data = temp;
            } else {
                data = "";
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return data;
    }

    public void clearDefaultSetting(final Context context, final String PackageId) {
        Intent showSettings = new Intent();
        showSettings.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        Uri uriAppSettings = Uri.fromParts("package", PackageId, (String)null);
        showSettings.setData(uriAppSettings);
        context.startActivity(showSettings);
    }
}

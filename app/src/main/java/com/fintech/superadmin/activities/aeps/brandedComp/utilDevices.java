package com.fintech.superadmin.activities.aeps.brandedComp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.widget.RelativeLayout;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class utilDevices {
    public DeviceDataModel morphoDeviceData(RelativeLayout ParentLayout, String mainData) {
        DeviceDataModel dataModel = new DeviceDataModel();
        dataModel.setErrMsg("Please connect your Morpho device!");
        dataModel.setErrCode("111");
        try {
            if (mainData != null) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));
                NodeList nodeList = doc.getElementsByTagName("DeviceInfo");
                NodeList n2 = ((Element) nodeList.item(0)).getElementsByTagName("additional_info");
                if (n2.item(0) == null) {
                    (new util()).snackBar((View) ParentLayout, "Please connect your Morpho Device!", AllString.SnackBarBackGroundColor);
                } else {
                    Element serialNo = (Element) n2.item(0).getChildNodes().item(1);
                    dataModel.setSrno("" + serialNo.getAttribute("value"));
                    dataModel.setSysid("" + serialNo.getAttribute("value"));
                    dataModel.setTs("" + (new util()).getTimeStamp());
                    if (!dataModel.getSrno().equalsIgnoreCase("") || !dataModel.getSysid().equalsIgnoreCase(""))
                        dataModel.setErrCode("919");
                }
            }
        } catch (Exception e) {
            (new util()).snackBar((View) ParentLayout, "Please check your Morpho device or contact customer support!", AllString.SnackBarBackGroundColor);
            e.printStackTrace();
        }
        return dataModel;
    }

    public DeviceDataModel morphoFingerData(RelativeLayout ParentLayout, String mainData, DeviceDataModel morphoDeviceData) {
        DeviceDataModel dataModel = new DeviceDataModel();
        dataModel.setErrMsg("Please connect your Morpho device!");
        /*  89 */
        dataModel.setErrCode("111");
        try {
            /*  91 */
            if (mainData != null) {
                /*  92 */
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                /*  93 */
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                /*  94 */
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));

                /*  96 */
                Node node = doc.getElementsByTagName("PidData").item(0);
                /*  97 */
                Element element = (Element) node;

                /*  99 */
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                /* 100 */
                Element element1 = (Element) nodeList1.item(0);

                /* 102 */
                dataModel.setErrCode(element1.getAttribute("errCode"));
                /* 103 */
                dataModel.setErrMsg(element1.getAttribute("errInfo"));
                /* 104 */
                dataModel.setNmPoints(element1.getAttribute("nmPoints"));

                /* 106 */
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {

                    /* 109 */
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    /* 110 */
                    Element element2 = (Element) nodeList2.item(0);

                    /* 112 */
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    /* 113 */
                    Element element3 = (Element) nodeList3.item(0);


                    /* 116 */
                    dataModel.setFingerData("" + getValue("Data", element));
                    /* 117 */
                    dataModel.setHmac("" + getValue("Hmac", element));
                    /* 118 */
                    dataModel.setSkey("" + getValue("Skey", element));
                    /* 119 */
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    /* 120 */
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    /* 121 */
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    /* 122 */
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    /* 123 */
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    /* 124 */
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    /* 125 */
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    /* 126 */
                    dataModel.setSrno("" + morphoDeviceData.getSrno());
                    /* 127 */
                    dataModel.setSysid("" + morphoDeviceData.getSysid());
                    /* 128 */
                    dataModel.setTs("" + morphoDeviceData.getTs());

                    /* 130 */
                    (new util()).snackBar((View) ParentLayout, "Finger Captured Successfully!", AllString.SnackBarBackGroundColorGreen);
                }

            }

        }
        /* 136 */ catch (Exception e) {

            /* 138 */
            dataModel.setErrMsg("Please check your Morpho device or contact customer support!");
            /* 139 */
            e.printStackTrace();
        }

        /* 142 */
        return dataModel;
    }

    public DeviceDataModel mantraData(RelativeLayout ParentLayout, String mainData) {
        /* 146 */
        DeviceDataModel dataModel = new DeviceDataModel();
        /* 147 */
        dataModel.setErrMsg("Please connect your Mantra device!");
        /* 148 */
        dataModel.setErrCode("111");
        try {
            /* 150 */
            if (mainData != null) {
                /* 151 */
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                /* 152 */
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                /* 153 */
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));

                /* 155 */
                Node node = doc.getElementsByTagName("PidData").item(0);
                /* 156 */
                Element element = (Element) node;

                /* 158 */
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                /* 159 */
                Element element1 = (Element) nodeList1.item(0);

                /* 161 */
                dataModel.setErrCode(element1.getAttribute("errCode"));
                /* 162 */
                dataModel.setErrMsg(element1.getAttribute("errInfo"));
                /* 163 */
                dataModel.setNmPoints(element1.getAttribute("nmPoints"));

                /* 165 */
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {
                    /* 167 */
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    /* 168 */
                    Element element2 = (Element) nodeList2.item(0);

                    /* 170 */
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    /* 171 */
                    Element element3 = (Element) nodeList3.item(0);

                    /* 173 */
                    NodeList nodeList4 = ((Element) nodeList3.item(0)).getElementsByTagName("additional_info");
                    /* 174 */
                    Element element4 = (Element) nodeList4.item(0).getChildNodes().item(1);
                    /* 175 */
                    String srno = element4.getAttribute("value");
                    /* 176 */
                    element4 = (Element) nodeList4.item(0).getChildNodes().item(3);
                    /* 177 */
                    String sysid = element4.getAttribute("value");
                    /* 178 */
                    element4 = (Element) nodeList4.item(0).getChildNodes().item(5);
                    /* 179 */
                    String ts = element4.getAttribute("value");



                    /* 183 */
                    dataModel.setFingerData("" + getValue("Data", element));
                    /* 184 */
                    dataModel.setHmac("" + getValue("Hmac", element));
                    /* 185 */
                    dataModel.setSkey("" + getValue("Skey", element));
                    /* 186 */
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    /* 187 */
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    /* 188 */
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    /* 189 */
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    /* 190 */
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    /* 191 */
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    /* 192 */
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    /* 193 */
                    dataModel.setSrno("" + srno);
                    /* 194 */
                    dataModel.setSysid("" + sysid);
                    /* 195 */
                    dataModel.setTs("" + ts);

                    /* 197 */
                    (new util()).snackBar((View) ParentLayout, "Finger Captured Successfully!", AllString.SnackBarBackGroundColorGreen);
                }

            }

        }
        /* 203 */ catch (Exception e) {

            /* 205 */
            dataModel.setErrMsg("Please check your Mantra device or contact customer support!");
            /* 206 */
            e.printStackTrace();
        }

        /* 209 */
        return dataModel;
    }

    public DeviceDataModel secugenData(RelativeLayout ParentLayout, String mainData) {
        /* 213 */
        DeviceDataModel dataModel = new DeviceDataModel();
        /* 214 */
        dataModel.setErrMsg("Please connect your Secugen device!");
        /* 215 */
        dataModel.setErrCode("111");
        try {
            /* 217 */
            if (mainData != null) {
                /* 218 */
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                /* 219 */
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                /* 220 */
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));

                /* 222 */
                Node node = doc.getElementsByTagName("PidData").item(0);
                /* 223 */
                Element element = (Element) node;

                /* 225 */
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                /* 226 */
                Element element1 = (Element) nodeList1.item(0);

                /* 228 */
                dataModel.setErrCode(element1.getAttribute("errCode"));
                /* 229 */
                dataModel.setErrMsg(element1.getAttribute("errInfo"));
                /* 230 */
                dataModel.setNmPoints(element1.getAttribute("nmPoints"));

                /* 232 */
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {

                    /* 235 */
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    /* 236 */
                    Element element2 = (Element) nodeList2.item(0);

                    /* 238 */
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    /* 239 */
                    Element element3 = (Element) nodeList3.item(0);

                    /* 241 */
                    NodeList nodeList4 = ((Element) nodeList3.item(0)).getElementsByTagName("additional_info");
                    /* 242 */
                    Element element4 = (Element) nodeList4.item(0).getChildNodes().item(1);


                    /* 245 */
                    dataModel.setFingerData("" + getValue("Data", element));
                    /* 246 */
                    dataModel.setHmac("" + getValue("Hmac", element));
                    /* 247 */
                    dataModel.setSkey("" + getValue("Skey", element));
                    /* 248 */
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    /* 249 */
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    /* 250 */
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    /* 251 */
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    /* 252 */
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    /* 253 */
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    /* 254 */
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    /* 255 */
                    dataModel.setSrno("" + element4.getAttribute("value"));
                    /* 256 */
                    dataModel.setSysid("" + element4.getAttribute("value"));
                    /* 257 */
                    dataModel.setTs("" + (new util()).getTimeStamp());

                    /* 259 */
                    (new util()).snackBar((View) ParentLayout, "Finger Captured Successfully!", AllString.SnackBarBackGroundColorGreen);
                }

            }

            /* 264 */
        } catch (Exception e) {

            /* 266 */
            dataModel.setErrMsg("Please check your Secugen device or contact customer support!");
            /* 267 */
            e.printStackTrace();
        }

        /* 270 */
        return dataModel;
    }

    public DeviceDataModel tatvikData(RelativeLayout ParentLayout, String mainData) {
        /* 274 */
        DeviceDataModel dataModel = new DeviceDataModel();
        /* 275 */
        dataModel.setErrMsg("Please connect your Tatvik device!");
        /* 276 */
        dataModel.setErrCode("111");
        try {
            /* 278 */
            if (mainData != null) {
                /* 279 */
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                /* 280 */
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                /* 281 */
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));

                /* 283 */
                Node node = doc.getElementsByTagName("PidData").item(0);
                /* 284 */
                Element element = (Element) node;

                /* 286 */
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                /* 287 */
                Element element1 = (Element) nodeList1.item(0);

                /* 289 */
                dataModel.setErrCode(element1.getAttribute("errCode"));
                /* 290 */
                dataModel.setErrMsg(element1.getAttribute("errInfo"));
                /* 291 */
                dataModel.setNmPoints(element1.getAttribute("nmPoints"));

                /* 293 */
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {

                    /* 296 */
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    /* 297 */
                    Element element2 = (Element) nodeList2.item(0);

                    /* 299 */
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    /* 300 */
                    Element element3 = (Element) nodeList3.item(0);

                    /* 302 */
                    NodeList nodeList4 = ((Element) nodeList3.item(0)).getElementsByTagName("additional_info");
                    /* 303 */
                    Element element4 = (Element) nodeList4.item(0).getChildNodes().item(0);


                    /* 306 */
                    dataModel.setFingerData("" + getValue("Data", element));
                    /* 307 */
                    dataModel.setHmac("" + getValue("Hmac", element));
                    /* 308 */
                    dataModel.setSkey("" + getValue("Skey", element));
                    /* 309 */
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    /* 310 */
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    /* 311 */
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    /* 312 */
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    /* 313 */
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    /* 314 */
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    /* 315 */
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    /* 316 */
                    dataModel.setSrno("" + element4.getAttribute("value"));
                    /* 317 */
                    dataModel.setSysid("" + element4.getAttribute("value"));
                    /* 318 */
                    dataModel.setTs("" + (new util()).getTimeStamp());

                    /* 320 */
                    (new util()).snackBar((View) ParentLayout, "Finger Captured Successfully!", AllString.SnackBarBackGroundColorGreen);
                }

            }

            /* 325 */
        } catch (Exception e) {

            /* 327 */
            dataModel.setErrMsg("Please check your Tatvik device or contact customer support!");
            /* 328 */
            e.printStackTrace();
        }

        /* 331 */
        return dataModel;
    }

    public DeviceDataModel starTekData(RelativeLayout ParentLayout, String mainData) {
        /* 335 */
        DeviceDataModel dataModel = new DeviceDataModel();
        /* 336 */
        dataModel.setErrMsg("Please connect your Startek device!");
        /* 337 */
        dataModel.setErrCode("111");
        try {
            /* 339 */
            if (mainData != null) {
                /* 340 */
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                /* 341 */
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                /* 342 */
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));

                /* 344 */
                Node node = doc.getElementsByTagName("PidData").item(0);
                /* 345 */
                Element element = (Element) node;

                /* 347 */
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                /* 348 */
                Element element1 = (Element) nodeList1.item(0);

                /* 350 */
                dataModel.setErrCode(element1.getAttribute("errCode"));
                /* 351 */
                dataModel.setErrMsg(element1.getAttribute("errInfo"));
                /* 352 */
                dataModel.setNmPoints(element1.getAttribute("nmPoints"));

                /* 354 */
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {
                    /* 356 */
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    /* 357 */
                    Element element2 = (Element) nodeList2.item(0);

                    /* 359 */
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    /* 360 */
                    Element element3 = (Element) nodeList3.item(0);

                    /* 362 */
                    NodeList nodeList4 = ((Element) nodeList3.item(0)).getElementsByTagName("additional_info");
                    /* 363 */
                    Element element4 = (Element) nodeList4.item(0).getChildNodes().item(0);


                    /* 366 */
                    dataModel.setFingerData("" + getValue("Data", element));
                    /* 367 */
                    dataModel.setHmac("" + getValue("Hmac", element));
                    /* 368 */
                    dataModel.setSkey("" + getValue("Skey", element));
                    /* 369 */
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    /* 370 */
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    /* 371 */
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    /* 372 */
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    /* 373 */
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    /* 374 */
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    /* 375 */
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    /* 376 */
                    dataModel.setSrno("" + element4.getAttribute("value"));
                    /* 377 */
                    dataModel.setSysid("" + element4.getAttribute("value"));
                    /* 378 */
                    dataModel.setTs("" + (new util()).getTimeStamp());

                    /* 380 */
                    (new util()).snackBar((View) ParentLayout, "Finger Captured Successfully!", AllString.SnackBarBackGroundColorGreen);
                }

            }

            /* 385 */
        } catch (Exception e) {

            /* 387 */
            dataModel.setErrMsg("Please check your Startek device or contact customer support!");
            /* 388 */
            e.printStackTrace();
        }

        /* 391 */
        return dataModel;
    }

    public DeviceDataModel EvoluteData(RelativeLayout ParentLayout, String mainData) {
        /* 395 */
        DeviceDataModel dataModel = new DeviceDataModel();
        /* 396 */
        dataModel.setErrMsg("Please connect your Evolute device!");
        /* 397 */
        dataModel.setErrCode("111");
        try {
            /* 399 */
            if (mainData != null) {
                /* 400 */
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                /* 401 */
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                /* 402 */
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));

                /* 404 */
                Node node = doc.getElementsByTagName("PidData").item(0);
                /* 405 */
                Element element = (Element) node;

                /* 407 */
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                /* 408 */
                Element element1 = (Element) nodeList1.item(0);

                /* 410 */
                dataModel.setErrCode(element1.getAttribute("errCode"));
                /* 411 */
                dataModel.setErrMsg(element1.getAttribute("errInfo"));
                /* 412 */
                dataModel.setNmPoints(element1.getAttribute("nmPoints"));

                /* 414 */
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {
                    /* 416 */
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    /* 417 */
                    Element element2 = (Element) nodeList2.item(0);

                    /* 419 */
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    /* 420 */
                    Element element3 = (Element) nodeList3.item(0);

                    /* 422 */
                    NodeList nodeList4 = ((Element) nodeList3.item(0)).getElementsByTagName("additional_info");
                    /* 423 */
                    Element element4 = (Element) nodeList4.item(0).getChildNodes().item(0);


                    /* 426 */
                    dataModel.setFingerData("" + getValue("Data", element));
                    /* 427 */
                    dataModel.setHmac("" + getValue("Hmac", element));
                    /* 428 */
                    dataModel.setSkey("" + getValue("Skey", element));
                    /* 429 */
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    /* 430 */
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    /* 431 */
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    /* 432 */
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    /* 433 */
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    /* 434 */
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    /* 435 */
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    /* 436 */
                    dataModel.setSrno("" + element4.getAttribute("value"));
                    /* 437 */
                    dataModel.setSysid("" + element4.getAttribute("value"));
                    /* 438 */
                    dataModel.setTs("" + (new util()).getTimeStamp());

                    /* 440 */
                    (new util()).snackBar((View) ParentLayout, "Finger Captured Successfully!", AllString.SnackBarBackGroundColorGreen);
                }

            }
            /* 444 */
        } catch (Exception e) {

            /* 446 */
            dataModel.setErrMsg("Please check your Startek device or contact customer support!");
            /* 447 */
            e.printStackTrace();
        }

        /* 450 */
        return dataModel;
    }

    public DeviceDataModel NextBIoData(RelativeLayout ParentLayout, String mainData) {
        /* 454 */
        DeviceDataModel dataModel = new DeviceDataModel();
        /* 455 */
        dataModel.setErrMsg("Please connect your NextBioMetric device!");
        /* 456 */
        dataModel.setErrCode("111");
        try {
            /* 458 */
            if (mainData != null) {
                /* 459 */
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                /* 460 */
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                /* 461 */
                Document doc = dBuilder.parse(new ByteArrayInputStream(mainData.getBytes("UTF-8")));

                /* 463 */
                Node node = doc.getElementsByTagName("PidData").item(0);
                /* 464 */
                Element element = (Element) node;

                /* 466 */
                NodeList nodeList1 = doc.getElementsByTagName("Resp");
                /* 467 */
                Element element1 = (Element) nodeList1.item(0);

                /* 469 */
                dataModel.setErrCode(element1.getAttribute("errCode"));
                /* 470 */
                dataModel.setErrMsg(element1.getAttribute("errInfo"));


                /* 473 */
                if (element1.getAttribute("errCode").equalsIgnoreCase("0")) {
                    /* 475 */
                    NodeList nodeList2 = doc.getElementsByTagName("Skey");
                    /* 476 */
                    Element element2 = (Element) nodeList2.item(0);

                    /* 478 */
                    NodeList nodeList3 = doc.getElementsByTagName("DeviceInfo");
                    /* 479 */
                    Element element3 = (Element) nodeList3.item(0);

                    /* 481 */
                    NodeList nodeList4 = ((Element) nodeList3.item(0)).getElementsByTagName("additional_info");
                    /* 482 */
                    Element serialNo = (Element) nodeList4.item(0).getChildNodes().item(1);
                    /* 483 */
                    String srno = serialNo.getAttribute("value");

                    /* 485 */
                    serialNo = (Element) nodeList4.item(0).getChildNodes().item(3);


                    /* 488 */
                    String ts = serialNo.getAttribute("value");







                    /* 496 */
                    dataModel.setFingerData("" + getValue("Data", element));
                    /* 497 */
                    dataModel.setHmac("" + getValue("Hmac", element));
                    /* 498 */
                    dataModel.setSkey("" + getValue("Skey", element));
                    /* 499 */
                    dataModel.setCi("" + element2.getAttribute("ci"));
                    /* 500 */
                    dataModel.setDc("" + element3.getAttribute("dc"));
                    /* 501 */
                    dataModel.setDpId("" + element3.getAttribute("dpId"));
                    /* 502 */
                    dataModel.setMc("" + element3.getAttribute("mc"));
                    /* 503 */
                    dataModel.setMi("" + element3.getAttribute("mi"));
                    /* 504 */
                    dataModel.setRdsId("" + element3.getAttribute("rdsId"));
                    /* 505 */
                    dataModel.setRdsVer("" + element3.getAttribute("rdsVer"));
                    /* 506 */
                    dataModel.setSrno("" + srno);

                    /* 508 */
                    dataModel.setTs("" + ts);

                    /* 510 */
                    (new util()).snackBar((View) ParentLayout, "Finger Captured Successfully!", AllString.SnackBarBackGroundColorGreen);
                }

            }

        }
        /* 516 */ catch (Exception e) {

            /* 518 */
            dataModel.setErrMsg("Please check your NextBiometric device or contact customer support!");
            /* 519 */
            e.printStackTrace();
        }

        /* 522 */
        return dataModel;
    }

    private static String getValue(String mainStr, Element element) {
        /* 526 */
        NodeList nodeList = element.getElementsByTagName(mainStr).item(0).getChildNodes();
        /* 527 */
        Node node = nodeList.item(0);

        /* 529 */
        return node.getNodeValue();
    }

    public static String getPreferredPackage(Context context, String deviceId) {
        /* 533 */
        String data = "";
        try {
            /* 535 */
            Intent intent = new Intent();
            /* 536 */
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            /* 537 */
            PackageManager packageManager = context.getPackageManager();
            /* 538 */
            ResolveInfo resolveInfo = packageManager.resolveActivity(intent, 0);
            /* 539 */
            String temp = resolveInfo.activityInfo.packageName;
            /* 540 */
            if (temp.equalsIgnoreCase("android") || temp.equalsIgnoreCase(deviceId)) {
                /* 541 */
                data = "";
            } else {
                /* 543 */
                data = temp;
            }

            /* 546 */
        } catch (Exception e) {
            /* 547 */
            e.printStackTrace();
        }

        /* 550 */
        return data;
    }

}

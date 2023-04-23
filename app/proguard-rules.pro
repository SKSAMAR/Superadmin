-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface
-keepattributes *Annotation*

-dontwarn com.razorpay.**
-keep class com.razorpay.** {*;}

-optimizations !method/inlining/*

-keepclasseswithmembers class * {
  public void onPayment*(...);
}

#later for fingerprint

-libraryjars <java.home>/lib/rt.jar(java/**,javax/**)
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
# (3)Not remove unused code
#-dontshrink

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class com.android.vending.licensing.ILicensingService
# (2)Simple XML
-keep public class org.simpleframework.**{ *; }
-keep class org.simpleframework.xml.**{ *; }
-keep class org.simpleframework.xml.core.**{ *; }
-keep class org.simpleframework.xml.util.**{ *; }
# (1)Annotations and signatures
-keepclassmembers class * {
    @org.simpleframework.xml.* *;
}
-keepattributes ElementList, Root, Element, Attribute
-keepattributes *Annotation*
-keepattributes Signature

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepattributes EnclosingMethod

-keepattributes *Annotation*,Signature
-keep class com.mantra.mfs100.** { *; }
-keepattributes Signature

-keep class org.junit.** { *; }
-dontwarn org.junit.**

-keep class junit.** { *; }
-dontwarn junit.**

-keep public class org.bouncycastle.**{ *; }
-keep public class org.apache.**{ *; }
-dontwarn org.apache.**

-keep public class com.mantra.mfs100regdvcsample.model.**{ *; }
# Shading the BluetoothLeService package
-keep class com.anfu.pos.library.bluetooth4.** { *; }
-dontwarn com.anfu.pos.library.bluetooth4.**
-dontnote com.anfu.pos.library.bluetooth4.**
-repackageclasses com.example.shaded.bluetooth4
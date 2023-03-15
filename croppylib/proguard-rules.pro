# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# keep everything in this package from being removed or renamed
-keep class com.lyrebirdstudio.croppylib.cropview.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.cropview.** { *; }
# keep everything in this package from being removed or renamed
-keep class com.lyrebirdstudio.croppylib.inputview.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.inputview.** { *; }

-keep class com.lyrebirdstudio.croppylib.main.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.main.** { *; }


-keep class com.lyrebirdstudio.croppylib.state.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.state.** { *; }

-keep class com.lyrebirdstudio.croppylib.ui.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.ui.** { *; }

-keep class com.lyrebirdstudio.croppylib.util.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.util.** { *; }

-keep class com.lyrebirdstudio.croppylib.cropview.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.croppylib.cropview.** { *; }

-keep class com.lyrebirdstudio.aspectratiorecyclerviewlib.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.lyrebirdstudio.aspectratiorecyclerviewlib.** { *; }


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
-keep class com.whatsycrrop.dpmaker.filter.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.whatsycrrop.dpmaker.filter.** { *; }
# keep everything in this package from being removed or renamed
-keep class com.whatsycrrop.dpmaker.activity.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.whatsycrrop.dpmaker.activity.** { *; }
-keep class com.whatsycrrop.dpmaker.adapter.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.whatsycrrop.dpmaker.adapter.** { *; }
-keep class com.whatsycrrop.dpmaker.utiles.** { *; }

# keep everything in this package from being renamed only
-keepnames class com.whatsycrrop.dpmaker.utiles.** { *; }

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

# keep everything in this package from being removed or renamed
-keep class com.whatsycrrop.dpmaker.adapter.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.whatsycrrop.dpmaker.adsclass.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.whatsycrrop.dpmaker.filter.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.whatsycrrop.dpmaker.interfaceces.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.whatsycrrop.dpmaker.utiles.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.whatsycrrop.dpmaker.activity.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.whatsycrrop.dpmaker.photoeditor.** { *; }

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

-keep class ja.burhanrashid52.photoeditor.shape.** { *; }
-keep class ja.burhanrashid52.photoeditor.BitmapUtil.** { *; }
-keep class ja.burhanrashid52.photoeditor.BoxHelper.** { *; }
-keep class ja.burhanrashid52.photoeditor.BrushDrawingStateListener.** { *; }
-keep class ja.burhanrashid52.photoeditor.BrushDrawingStateListener.** { *; }
-keep class ja.burhanrashid52.photoeditor.CustomEffect.** { *; }
-keep class ja.burhanrashid52.photoeditor.DrawingView.** { *; }
-keep class ja.burhanrashid52.photoeditor.Emoji.** { *; }
-keep class ja.burhanrashid52.photoeditor.FilterImageView.** { *; }
-keep class ja.burhanrashid52.photoeditor.GLToolbox.** { *; }
-keep class ja.burhanrashid52.photoeditor.Graphic.** { *; }
-keep class ja.burhanrashid52.photoeditor.GraphicManager.** { *; }
-keep class ja.burhanrashid52.photoeditor.ImageFilterView.** { *; }
-keep class ja.burhanrashid52.photoeditor.MultiTouchListener.** { *; }
-keep class ja.burhanrashid52.photoeditor.OnPhotoEditorListener.** { *; }
-keep class ja.burhanrashid52.photoeditor.OnSaveBitmap.** { *; }
-keep class ja.burhanrashid52.photoeditor.PhotoEditor.** { *; }
-keep class ja.burhanrashid52.photoeditor.PhotoEditorImageViewListener.** { *; }
-keep class ja.burhanrashid52.photoeditor.PhotoEditorImpl.** { *; }
-keep class ja.burhanrashid52.photoeditor.PhotoEditorImageViewListener.** { *; }
-keep class ja.burhanrashid52.photoeditor.PhotoEditorImpl.** { *; }
-keep class ja.burhanrashid52.photoeditor.PhotoEditorViewState.** { *; }
-keep class ja.burhanrashid52.photoeditor.PhotoFilter.** { *; }
-keep class ja.burhanrashid52.photoeditor.PhotoSaverTask.** { *; }
-keep class ja.burhanrashid52.photoeditor.SaveSettings.** { *; }
-keep class ja.burhanrashid52.photoeditor.ScaleGestureDetector.** { *; }
-keep class ja.burhanrashid52.photoeditor.Sticker.** { *; }
-keep class ja.burhanrashid52.photoeditor.Text.** { *; }
-keep class ja.burhanrashid52.photoeditor.TextBorder.** { *; }
-keep class ja.burhanrashid52.photoeditor.TextShadow.** { *; }
-keep class ja.burhanrashid52.photoeditor.TextStyleBuilder.** { *; }

-keep class ja.burhanrashid52.photoeditor.Vector2D.** { *; }
-keep class ja.burhanrashid52.photoeditor.ViewType.** { *; }


-keep class ja.burhanrashid52.photoeditor.TextureRenderer.** { *; }

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

-keep class com.google.ads.** # Don't proguard AdMob classes
-dontwarn com.google.ads.** # Temporary workaround for v6.2.1. It gives a warning that you can ignore
# Add this global rule
-keepattributes Signature

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
-keepclassmembers class com.whatsycrrop.dpmaker.databas.** {
*;
}
# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.



-keepclassmembers class com.whatsycrrop.dpmaker.databas.WhtasyCrop.** {
*;
}-keepclassmembers class com.whatsycrrop.dpmaker.databas.DataStatuses.** {
*;
}



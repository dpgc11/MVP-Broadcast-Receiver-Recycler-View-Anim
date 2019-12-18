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


-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception


#-keep class com.crashlytics.** { *; }
#-dontwarn com.crashlytics.**

-dontwarn com.android.support.**
-dontwarn com.facebook.android.**
-dontwarn com.google.android.gms.**
-dontwarn com.squareup.picasso.**
-dontwarn com.jakewharton.**
-dontwarn com.google.dagger.**
-dontwarn com.google.apis.**
-dontwarn com.squareup.retrofit2.**
-dontwarn com.google.api-client.**
-dontwarn com.squareup.okhttp.**
-dontwarn com.google.code.gson.**
-dontwarn com.hootsuite.android.nachos.**
-dontwarn com.github.PhilJay.**

-dontwarn com.github.deano2390.**
-dontwarn com.github.amlcurran.showcaseview.**
-dontwarn id.zelory.**
-dontwarn com.lapism.**
-dontwarn jp.wasabeef.**
-dontwarn com.github.ittianyu.**
-dontwarn com.firebase.**
-dontwarn com.getkeepsafe.taptargetview.**
-dontwarn com.jakewharton.timber.**
-dontwarn com.crashlytics.sdk.android.**
-dontwarn com.nineoldandroids.**
-dontwarn com.daimajia.slider.**
-dontwarn com.github.aakira.**

# google play referrer
-dontwarn com.android.installreferrer
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
## The support library contains references to newer platform versions.
## Don't warn about those in case this app is linking against an older
## platform version. We know about them, and they are safe.
#-dontwarn android.support.**
#-dontwarn com.google.ads.**

# newest change(most extreme)
#-keep class com.makepath.lemonop.** { *; }
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

-keep class com.firebase.** { *; }
-keep class org.apache.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-ignorewarnings

# Only necessary if you downloaded the SDK jar directly instead of from maven.
-keep class com.shaded.fasterxml.jackson.** { *; }
-keep class com.github.mikephil.charting.** { *; }

-keep public class com.google.android.material.bottomnavigation.BottomNavigationView { *; }
-keep public class com.google.android.material.bottomnavigation.BottomNavigationMenuView { *; }

-keep public class com.google.android.material.bottomnavigation.BottomNavigationPresenter { *; }
-keep public class com.google.android.material.bottomnavigation.BottomNavigationItemView { *; }

-keep public class android.support.design.internal.BottomNavigationPresenter { *; }

# Add this global rule
-keepattributes Signature

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
-keepclassmembers class com.makepath.lemonop.aspirant.data.network.model.** {
  *;
}

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

## Application classes that will be serialized/deserialized over Gson
-keep class com.makepath.lemonop.aspirant.data.** { *; }

-keepclassmembers class com.makepath.lemonop.aspirant.data.db.model.** {
  *;
}

-dontwarn com.squareup.picasso.**
-dontwarn com.bumptech.glide.**


# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# rich text preview
-keeppackagenames org.jsoup.nodes

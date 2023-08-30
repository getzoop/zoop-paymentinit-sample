import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

val localProperties = gradleLocalProperties(rootDir)

android {
    namespace = "com.zoop.paymentinit.sample"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.zoop.paymentinit.sample"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField ("String", "MARKETPLACE_ID", localProperties["MARKETPLACE_ID"].toString())
        buildConfigField ("String", "SELLER_ID", localProperties["SELLER_ID"].toString())
        buildConfigField ("String", "ACCESS_KEY", localProperties["ACCESS_KEY"].toString())

        manifestPlaceholders["MARKETPLACE_ID"] = localProperties["MARKETPLACE_ID"].toString().replace("\"", "")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Zoop SDK
    implementation("com.zoop.sdk.plugin:paymentinit-debug:2.2.0-rc-1-SNAPSHOT")

    // Zoop SDK - Dependencies
    implementation("com.google.android.material:material:1.5.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.0")
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.caverock:androidsvg-aar:1.4")
    implementation("com.github.bumptech.glide:glide:4.13.2")
    kapt("com.github.bumptech.glide:compiler:4.13.2")

}
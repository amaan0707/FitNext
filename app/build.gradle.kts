plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.fitnext"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fitnext"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources= true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-messaging:24.0.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation ("com.firebaseui:firebase-ui-database:8.0.2")
    implementation ("com.firebaseui:firebase-ui-firestore:8.0.2")
    implementation ("com.firebaseui:firebase-ui-auth:8.0.2")
    implementation ("com.firebaseui:firebase-ui-storage:8.0.2")
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-database:21.0.0")
    implementation ("com.hbb20:ccp:2.7.1")

    implementation ("io.github.chaosleung:pinview:1.4.4")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.28")

    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    implementation("com.google.firebase:firebase-inappmessaging-display")
    implementation("com.google.firebase:firebase-analytics")
}

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.fitnext"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.example.fitnext"
        minSdk = 31
        targetSdk = 36
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

    implementation("com.google.android.gms:play-services-auth:21.5.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("com.google.firebase:firebase-auth:24.0.1")
    implementation("com.google.firebase:firebase-messaging:25.0.1")
    implementation("androidx.media3:media3-common:1.9.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    implementation("androidx.core:core-splashscreen:1.2.0")
    implementation ("com.firebaseui:firebase-ui-database:9.1.1")
    implementation ("com.firebaseui:firebase-ui-firestore:9.1.1")
    implementation ("com.firebaseui:firebase-ui-auth:9.1.1")
    implementation ("com.firebaseui:firebase-ui-storage:9.1.1")
    implementation(platform("com.google.firebase:firebase-bom:34.8.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-database:22.0.1")
    implementation ("com.hbb20:ccp:2.7.3")

    implementation ("io.github.chaosleung:pinview:1.4.4")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.30")

    implementation("com.squareup.okhttp3:okhttp:5.3.2")
    implementation("androidx.activity:activity-ktx:1.12.2") // Or latest version
    implementation("androidx.fragment:fragment-ktx:1.8.9")
    implementation("com.google.firebase:firebase-inappmessaging-display")
    implementation("com.google.firebase:firebase-analytics")

    }


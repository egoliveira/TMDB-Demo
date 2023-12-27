plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "br.com.gielamo.tmdb"
    compileSdk = Build.CompileSDKVersion

    defaultConfig {
        applicationId = "br.com.gielamo.tmdb"
        minSdk = Build.MinSDKVersion
        targetSdk = Build.TargetSDKVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
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

        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
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
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Dependencies.Timber)
    implementation(Dependencies.AndroidX.CoreKTX)
    implementation(Dependencies.AndroidX.LifecycleRuntime)
    implementation(Dependencies.Compose.ActivityCompose)
    implementation(platform(Dependencies.Compose.ComposeBOM))
    implementation(Dependencies.Compose.UI)
    implementation(Dependencies.Compose.UIGraphics)
    implementation(Dependencies.Compose.UIToolingPreview)
    implementation(Dependencies.Compose.Material3)
    implementation(Dependencies.AccompanistDrawablePainter)
    implementation(Dependencies.Compose.Navigation)
    implementation(Dependencies.Compose.LifecycleRuntime)
    implementation(Dependencies.Compose.LifecycleViewModel)
    implementation(Dependencies.HiltAndroid)
    implementation(Dependencies.Compose.HiltNavigation)
    implementation(Dependencies.Coil)
    implementation(Dependencies.Moshi)

    debugImplementation(Dependencies.Compose.UITooling)
    debugImplementation(Dependencies.Compose.UITestManifest)

    coreLibraryDesugaring(Dependencies.DesugarJDKLibs)

    ksp(Dependencies.HiltAndroidCompiler)

    testImplementation(Dependencies.Test.JUnit)

    androidTestImplementation(Dependencies.AndroidTest.JUnit)
    androidTestImplementation(Dependencies.AndroidTest.EspressoCore)
    androidTestImplementation(platform(Dependencies.Compose.ComposeBOM))
    androidTestImplementation(Dependencies.AndroidTest.UITestJUnit4)
}
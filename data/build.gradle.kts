import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "br.com.gielamo.tmdb.data"
    compileSdk = Build.CompileSDKVersion

    buildFeatures.buildConfig = true

    defaultConfig {
        minSdk = Build.MinSDKVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val localProperties = Properties()
        val localPropertiesFile = project.file("local.properties")

        if (localPropertiesFile.canRead()) {
            localProperties.load(localPropertiesFile.inputStream())

            if (!localProperties.containsKey("TMDB_BEARER_TOKEN")) {
                throw GradleException(
                    "TMDB_BEARER_TOKEN property isn't defined in local.properties file."
                )
            }
        } else {
            throw GradleException("local.properties file not found in data module.")
        }

        buildConfigField(
            "String",
            "TMDB_BEARER_TOKEN",
            localProperties.getProperty("TMDB_BEARER_TOKEN")
        )
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
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Dependencies.Timber)
    implementation(Dependencies.AndroidX.CoreKTX)
    implementation(Dependencies.HiltAndroid)
    implementation(Dependencies.Retrofit)
    implementation(Dependencies.RetrofitMoshiConverter)

    ksp(Dependencies.HiltAndroidCompiler)

    coreLibraryDesugaring(Dependencies.DesugarJDKLibs)

    testImplementation(Dependencies.Test.JUnit)

    androidTestImplementation(Dependencies.AndroidTest.JUnit)
    androidTestImplementation(Dependencies.AndroidTest.EspressoCore)
}
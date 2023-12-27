object Versions {
    object AndroidX {
        const val CoreKTX = "1.12.0"
        const val LifecycleRuntime = "2.6.2"
    }

    object Compose {
        const val ActivityCompose = "1.8.0"
        const val Compose = "1.5.4"
        const val ComposeBOM = "2023.03.00"
        const val HiltNavigation = "1.1.0"
        const val LifecycleRuntime = "2.6.2"
        const val LifecycleViewModel = "2.6.2"
        const val Material3 = "1.1.2"
        const val Navigation = "2.7.5"
    }

    object Test {
        const val JUnit = "4.13.2"
    }

    object AndroidTest {
        const val EspressoCore = "3.5.1"
        const val JUnit = "1.1.5"
    }

    const val AccompanistDrawablePainter = "0.32.0"
    const val Coil = "2.4.0"
    const val DesugarJDKLibs = "2.0.4"
    const val Hilt = "2.48"
    const val Moshi = "1.8.0"
    const val Retrofit = "2.9.0"
    const val Timber = "5.0.1"
}

object Dependencies {
    object AndroidX {
        const val CoreKTX = "androidx.core:core-ktx:${Versions.AndroidX.CoreKTX}"
        const val LifecycleRuntime =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.LifecycleRuntime}"
    }

    object Compose {
        const val ActivityCompose =
            "androidx.activity:activity-compose:${Versions.Compose.ActivityCompose}"
        const val ComposeBOM = "androidx.compose:compose-bom:${Versions.Compose.ComposeBOM}"
        const val HiltNavigation =
            "androidx.hilt:hilt-navigation-compose:${Versions.Compose.HiltNavigation}"
        const val LifecycleRuntime =
            "androidx.lifecycle:lifecycle-runtime-compose:${Versions.Compose.LifecycleRuntime}"
        const val LifecycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.LifecycleViewModel}"
        const val Material3 = "androidx.compose.material3:material3:${Versions.Compose.Material3}"
        const val Navigation =
            "androidx.navigation:navigation-compose:${Versions.Compose.Navigation}"
        const val UI = "androidx.compose.ui:ui:${Versions.Compose.Compose}"
        const val UIGraphics = "androidx.compose.ui:ui-graphics:${Versions.Compose.Compose}"
        const val UITooling = "androidx.compose.ui:ui-tooling:${Versions.Compose.Compose}"
        const val UITestManifest =
            "androidx.compose.ui:ui-test-manifest:${Versions.Compose.Compose}"
        const val UIToolingPreview =
            "androidx.compose.ui:ui-tooling-preview:${Versions.Compose.Compose}"
    }

    object Test {
        const val JUnit = "junit:junit:${Versions.Test.JUnit}"
    }

    object AndroidTest {
        const val EspressoCore =
            "androidx.test.espresso:espresso-core:${Versions.AndroidTest.EspressoCore}"
        const val JUnit = "androidx.test.ext:junit:${Versions.AndroidTest.JUnit}"
        const val UITestJUnit4 = "androidx.compose.ui:ui-test-junit4:${Versions.Compose.Compose}"
    }

    const val AccompanistDrawablePainter =
        "com.google.accompanist:accompanist-drawablepainter:${Versions.AccompanistDrawablePainter}"
    const val Coil = "io.coil-kt:coil-compose:${Versions.Coil}"
    const val DesugarJDKLibs = "com.android.tools:desugar_jdk_libs:${Versions.DesugarJDKLibs}"
    const val HiltCore = "com.google.dagger:hilt-core:${Versions.Hilt}"
    const val HiltAndroid = "com.google.dagger:hilt-android:${Versions.Hilt}"
    const val HiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Hilt}"
    const val Moshi = "com.squareup.moshi:moshi:${Versions.Moshi}"
    const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.Retrofit}"
    const val RetrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.Retrofit}"
    const val Timber = "com.jakewharton.timber:timber:${Versions.Timber}"
}
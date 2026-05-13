        plugins {
            alias(libs.plugins.android.application)
            alias(libs.plugins.kotlin.android)
            alias(libs.plugins.ksp)
            id("com.google.dagger.hilt.android")
        }

        android {
            namespace = "kh.edu.rupp.to_dolistapp"
            compileSdk = 36

            defaultConfig {
                applicationId = "kh.edu.rupp.to_dolistapp"
                minSdk = 24
                targetSdk = 36
                versionCode = 1
                versionName = "1.0"

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
            buildFeatures {
                viewBinding = true
                dataBinding = true
            }
            kotlinOptions {
                jvmTarget = "11"
            }
        }

        dependencies {

            implementation(libs.appcompat)
            implementation(libs.material)
            implementation(libs.constraintlayout)
            implementation(libs.lifecycle.livedata.ktx)
            implementation(libs.lifecycle.viewmodel.ktx)
            implementation(libs.navigation.fragment)
            implementation(libs.navigation.ui)
            implementation(libs.recyclerview)
            implementation(libs.retrofit)
            implementation(libs.converter.gson)
            implementation(libs.picasso)
            implementation(libs.viewpager2)
            implementation(libs.room.runtime)
            implementation(libs.core.ktx)
            testImplementation(libs.junit)
            implementation("androidx.lifecycle:lifecycle-viewmodel:2.10.0")
            implementation("androidx.lifecycle:lifecycle-livedata:2.10.0")
            implementation("androidx.room:room-runtime:2.8.4")
            implementation("androidx.room:room-rxjava3:2.8.4")
            ksp("androidx.room:room-compiler:2.8.4")
            implementation("androidx.datastore:datastore-preferences-rxjava3:1.2.0")
            implementation("io.reactivex.rxjava3:rxjava:3.1.6")
            implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
            implementation("com.google.android.material:material:1.11.0")
            androidTestImplementation(libs.ext.junit)
            androidTestImplementation(libs.espresso.core)


            implementation("com.google.dagger:hilt-android:2.56")     // was 2.52
            ksp("com.google.dagger:hilt-compiler:2.56")
        }
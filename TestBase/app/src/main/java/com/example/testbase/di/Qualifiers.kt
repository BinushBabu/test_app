package com.example.testbase.di

import javax.inject.Qualifier

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AppContext

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class MainScheduler

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class IoScheduler

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ComputationScheduler

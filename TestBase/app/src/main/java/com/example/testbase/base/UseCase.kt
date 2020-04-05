package com.example.testbase.base

import io.reactivex.Single

abstract class UseCase<in P, R> {
    abstract fun execute(parameters: P): Single<R>
}

abstract class UseCaseDefault{
    abstract fun excute()
}
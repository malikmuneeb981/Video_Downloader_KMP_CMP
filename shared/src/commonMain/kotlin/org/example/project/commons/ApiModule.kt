package org.example.project.commons

import org.koin.core.module.Module


interface ApiModule{
    val module: Module
}
expect fun provideApiModule(): Module
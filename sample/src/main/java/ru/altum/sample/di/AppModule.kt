package ru.altum.sample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.altum.composione.Composione
import ru.altum.composione.destination.Destination
import ru.altum.sample.core.navigation.AppRouter
import ru.altum.sample.ui.screen.Home
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideStartDestination(): Destination = Home()

    @Provides
    @Singleton
    fun provideRouter(): AppRouter = AppRouter()

    @Provides
    @Singleton
    fun provideCicerone(router: AppRouter): Composione<AppRouter> = Composione.create(router)
}
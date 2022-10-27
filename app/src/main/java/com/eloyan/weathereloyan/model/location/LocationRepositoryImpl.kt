package com.eloyan.weathereloyan.model.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.eloyan.weathereloyan.R
import com.eloyan.weathereloyan.model.common.RequestCompleteListener
import com.eloyan.weathereloyan.model.common.RequestResult
import com.eloyan.weathereloyan.model.location.data.Coordinates
import com.eloyan.weathereloyan.utils.showLocationSettingsDialog
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import javax.inject.Inject
import javax.inject.Singleton

interface LocationRepository {
    fun locationRequest(
        callback: RequestCompleteListener<Coordinates, LocationError>
    )
}

enum class LocationError {
    EnableGPS,
    PermissionDenied
}

@Singleton
class LocationRepositoryImpl @Inject constructor(private val appContext: Context) :
    LocationRepository {

    private val locationClient = LocationServices.getFusedLocationProviderClient(appContext)

    override fun locationRequest(callback: RequestCompleteListener<Coordinates, LocationError>) {
        if (isGPSEnabled()) {
            getLocation(callback)
        } else {
            callback.onRequestFailed(LocationError.EnableGPS)
        }
    }

    private fun getLocation(callback: RequestCompleteListener<Coordinates, LocationError>) {
        val cancellation = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            callback.onRequestFailed(LocationError.PermissionDenied)
        }
        locationClient
            .getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellation.token
            )
            .addOnCompleteListener {
                val coordinates = Coordinates(
                    it.result.latitude.toString(),
                    it.result.longitude.toString()
                )

                callback.onRequestSuccess(coordinates)
            }
    }

    private fun isGPSEnabled(): Boolean {
        val locationService =
            appContext?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationService.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}
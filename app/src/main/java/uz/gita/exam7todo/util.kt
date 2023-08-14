package uz.gita.exam7todo

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

/**
 *    Created by Kamolov Samandar on 03.06.2023 at 21:54
 */

@RequiresApi(Build.VERSION_CODES.O)
fun dateToLong(stringDate: String ): Long{
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-hh:mm", Locale.ENGLISH)
    val millisecondsSinceEpoch: Long = LocalDate.parse(stringDate, dateFormatter)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()
    Log.d("TTT", "dateToLong: ${(millisecondsSinceEpoch-System.currentTimeMillis()).toNormalTime()}")
    return millisecondsSinceEpoch
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toNormalTime(): String {
    return DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        .withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochMilli(this))
}

fun requestPermissions(context: Context, permissions: List<String>, callback: (Boolean) -> Unit) {
    Dexter.withContext(context)
        .withPermissions(permissions)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                val allPermissionsGranted = report?.areAllPermissionsGranted() ?: false
                callback.invoke(allPermissionsGranted)
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }
        })
        .check()
}
fun showRationaleDialog(activity: Activity, token: PermissionToken?) {
    AlertDialog.Builder(activity)
        .setTitle("Permissions Required")
        .setMessage("This app requires certain permissions to function properly.")
        .setPositiveButton("OK") { _, _ ->
            // Continue with the permission request
            token?.continuePermissionRequest()
        }
        .setNegativeButton("Cancel") { _, _ ->
            // Cancel the permission request
            token?.cancelPermissionRequest()
        }
        .setOnDismissListener {
            // Dismiss the permission dialog
            token?.cancelPermissionRequest()
        }
        .show()
}

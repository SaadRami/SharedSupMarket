package be.supinfo.supermarketapp.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun localDateTimefromValues(localDateTimeString: String): LocalDate? {
        Log.i("DateTime1", localDateTimeString)
        return LocalDate.parse(localDateTimeString, dateTimeFormatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun localDateTimeToString(date: LocalDate?): String? {
        if (date != null) {
            Log.i("DateTime2", date.toString())
            return date.format(dateTimeFormatter)
        }
        return null
    }
}
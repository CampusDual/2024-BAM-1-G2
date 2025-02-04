package com.example.baseproject.data.converters

import androidx.room.TypeConverter
import com.example.baseproject.domain.entities.RegisteredUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserConverter {

    @TypeConverter
    fun fromPlayerList(value: List<RegisteredUser>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toPlayerList(value: String?): List<RegisteredUser>? {
        val listType = object : TypeToken<List<RegisteredUser>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
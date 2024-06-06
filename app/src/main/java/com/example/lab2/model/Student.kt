package com.example.lab2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity
data class Student(@PrimaryKey val id:UUID=UUID.randomUUID(),
                   var name:String="",var group:String="",
    var birthYear:Int=0,var phis:Int=0,var math:Int=0,var inf:Int=0):Serializable
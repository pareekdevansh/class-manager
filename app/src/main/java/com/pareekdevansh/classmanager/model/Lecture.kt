package com.pareekdevansh.classmanager.model

import javax.security.auth.Subject

data class Lecture(
    val courseName : String = "",
    val professorName : String = "" ,
    val courseCode : String = "" ,
    val day : Int = 0,
    val startingTime :String = "",
    val link : String = ""
)

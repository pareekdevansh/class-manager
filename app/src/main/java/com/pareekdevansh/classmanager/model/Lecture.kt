package com.pareekdevansh.classmanager.model

import javax.security.auth.Subject

data class Lecture(
    val subject : String,
    val professorName : String,
    val courseCode : String,
    val day : String,
    val startingTime : String,
    val link : String
)

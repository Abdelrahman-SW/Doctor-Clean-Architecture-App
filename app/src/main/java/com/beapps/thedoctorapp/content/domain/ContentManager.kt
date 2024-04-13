package com.beapps.thedoctorapp.content.domain

import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result

interface ContentManager {
    fun displayDoctorPatients(doctorId : String) : Result<List<Patient> , Error.GetDoctorContentErrors>
}
package com.example.pockemongame

import android.location.Location

class Pockemon {
    var name: String? = null
    var des: String? = null
    var img: Int? = null
    var power: Double? = null
    var lat: Double? = null
    var long: Double? = null
    var isCatch: Boolean? = false
    var location: Location? = null

    constructor(img: Int, name: String, des: String, power: Double, lat: Double, long: Double) {
        this.name = name
        this.img = img
        this.des = des
        this.power = power
        this.location = Location(name)
        this.location!!.latitude = lat
        this.location!!.longitude = long
        this.isCatch = false
    }
}
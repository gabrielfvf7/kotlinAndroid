package com.hussein.startup

class Animal  {
    var name: String? = null
    var des: String? = null
    var img: Int? = null
    var isKiller: Boolean = false

    constructor(name: String, des: String, img: Int, isKiller: Boolean) {
        this.name = name
        this.des = des
        this.img = img
        this.isKiller = isKiller
    }
}
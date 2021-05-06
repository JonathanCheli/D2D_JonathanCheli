package com.example.door2door_jc.base

interface BaseMapper<in D, out V> {

    fun mapDataModelToViewModel(dataModel: D): V
}
package com.houmei.workout

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class Exercises(
    private var id: Int,
    private var name: String,
    private var isCompleted: Boolean = false,
    private var isSelected: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getIsCompleted(): Boolean {
        return isCompleted
    }

    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }

    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeByte(if (isCompleted) 1 else 0)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exercises> {
        override fun createFromParcel(parcel: Parcel): Exercises {
            return Exercises(parcel)
        }

        override fun newArray(size: Int): Array<Exercises?> {
            return arrayOfNulls(size)
        }
    }
}

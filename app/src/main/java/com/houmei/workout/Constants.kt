package com.houmei.workout

object Constants {

    fun defaultExerciseList(): ArrayList<Exercises> {

        val exerciseList = ArrayList<Exercises>()
        val stairs =
            Exercises(1, "stairs")
        exerciseList.add(stairs)
        exerciseList.add(stairs)
        exerciseList.add(stairs)
        exerciseList.add(stairs)

        return exerciseList
    }

}
package com.houmei.workout

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.houmei.workout.databinding.ActivityAddWorkoutBinding

class AddWorkoutActivity : AppCompatActivity() {
    private var binding: ActivityAddWorkoutBinding? = null
    private var exercises: ArrayList<Exercises>? = null
    private var exerciseCounter: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddWorkoutBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val intent = Intent(this@AddWorkoutActivity, ExcerciseActivity::class.java)
        setContentView(binding?.root)
        binding?.btnAddWorkout?.setOnClickListener {
            exercises = exerciseList()
            intent.putExtra("exercise", exercises)
            binding?.etAddWorkout?.setText("")
        }

    }

    private fun exerciseList(): ArrayList<Exercises> {
        var exerciseList = ArrayList<Exercises>()
        var editText: EditText
        val aWorkout: String
        editText = findViewById(R.id.etAddWorkout)
        aWorkout = editText.text.toString()
        val workout = Exercises(exerciseCounter++, aWorkout)
        exerciseList.add(workout)
        Toast.makeText(this@AddWorkoutActivity, "exerciseList()", Toast.LENGTH_SHORT).show()
        return exerciseList
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
package com.houmei.workout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.houmei.workout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val startButton : FrameLayout = findViewById(R.id.flstart)
        binding?.flstart?.setOnClickListener(){
            val workout = Intent(this, ExcerciseActivity::class.java)
            startActivity(workout)
        }

        binding?.flHistory?.setOnClickListener(){
            val history = Intent(this, HistoryActivity::class.java)
            startActivity(history)
        }
        binding?.flWorkout?.setOnClickListener() {
            val addWorkout = Intent(this, AddWorkoutActivity::class.java)
            startActivity(addWorkout)
        }

    }
    override fun onDestroy(){
        super.onDestroy()
        binding = null
    }
}

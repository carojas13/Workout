package com.houmei.workout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.houmei.workout.databinding.ActivityExcerciseBinding
import com.houmei.workout.databinding.DialogueCustomBackConfirmationBinding
import android.util.Log



class ExcerciseActivity : AppCompatActivity() {
    private var binding: ActivityExcerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseList: ArrayList<Exercises>? = null
    private var currentExecisePointer = -1
    private var excerciseTimer: CountDownTimer? = null
    private var excerciseProgress = 0
    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()
        //exerciseList = intent.getParcelableArrayListExtra("exercise")

        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogueForBackButton()
        }

        setRestView()
        setUpExerciseStatusRecyclerView()
    }

    override fun onBackPressed() {
        customDialogueForBackButton()
    }

    private fun customDialogueForBackButton() {
        val customDialogue = Dialog(this)
        val dialogueBinding = DialogueCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialogue.setContentView(dialogueBinding.root)
        customDialogue.setCanceledOnTouchOutside(false)
        dialogueBinding.tvYes.setOnClickListener {
            this@ExcerciseActivity.finish()
            customDialogue.dismiss()
        }
        dialogueBinding.tvNo.setOnClickListener {
            customDialogue.dismiss()
        }
        customDialogue.show()
    }

    private fun setUpExerciseStatusRecyclerView() {
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setRestView() {
        try {
            val soundURI = Uri.parse(
                "android.resource://com.houmei.workout/" + R.raw.tiktoksnore)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        }catch (e: Exception) {
            e.printStackTrace()
        }
        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExcerciseProgressBar?.visibility = View.INVISIBLE
        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setExcerciseView() {
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExcerciseProgressBar?.visibility = View.VISIBLE
        if(excerciseTimer != null) {
            excerciseTimer?.cancel()
            excerciseProgress = 0
        }
        binding?.tvExerciseName?.text = exerciseList!![currentExecisePointer].getName()
        setExcerciseProgressBar()
    }


    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress
        restTimer = object: CountDownTimer(15000, 1500){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 1 -restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExecisePointer++
                exerciseList!![currentExecisePointer].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                Toast.makeText(this@ExcerciseActivity, "Starting excercise...", Toast.LENGTH_SHORT).show()
                setExcerciseView()
            }

        }.start()


    }


    private fun setExcerciseProgressBar(){
        binding?.excerciseProgressBar?.progress = excerciseProgress
        excerciseTimer = object: CountDownTimer(30000, 1000){
            override fun onTick(p0: Long) {
                excerciseProgress++
                binding?.excerciseProgressBar?.progress = 30 -excerciseProgress
                binding?.tvExcerciseTimer?.text = (30 - excerciseProgress).toString()
            }

            override fun onFinish() {

                if(currentExecisePointer <exerciseList?.size!! -1) {
                    exerciseList!![currentExecisePointer].setIsSelected(false)
                    exerciseList!![currentExecisePointer].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExcerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()


    }


    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if(excerciseTimer != null) {
            excerciseTimer?.cancel()
            excerciseProgress = 0
        }
        if(player != null) {
            player!!.stop()
        }
        binding = null
    }
}
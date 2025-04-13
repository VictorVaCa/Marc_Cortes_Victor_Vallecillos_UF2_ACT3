package com.example.marc_cortes_victor_vallecillos_uf2_act3

import android.content.Intent
import android.media.MediaPlayer
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    lateinit var character: ImageView
    lateinit var idleAnimation: AnimationDrawable
    private var currentAttackHandler: Handler? = null
    private var currentAttackRunnable: Runnable? = null
    private var isAttackRunning = false
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var attackSoundA: MediaPlayer
    private lateinit var attackSoundB: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.bgmusic) // Crea un archivo raw/tu_musica.mp3
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        character = findViewById(R.id.character)
        character.setBackgroundResource(R.drawable.idleanim)
        idleAnimation = character.background as AnimationDrawable
        idleAnimation.start()

        attackSoundA = MediaPlayer.create(this, R.raw.totsugeki)
        attackSoundB = MediaPlayer.create(this, R.raw.korugatta)


        val btnAttackA: Button = findViewById(R.id.btnAttackA)
        val btnAttackB: Button = findViewById(R.id.btnAttackB)
        val btnChangeActivity: Button = findViewById(R.id.btnChangeActivity)

        btnAttackA.setOnClickListener {
            executeAttack(R.drawable.vmrdanim)
            attackSoundA.start()
        }

        btnAttackB.setOnClickListener {
            executeAttack(R.drawable.spinneranim)
            attackSoundB.start()
        }

        btnChangeActivity.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause() // Pausar al salir de la actividad
    }

    override fun onResume() {
        super.onResume()
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start() // Reanudar al volver
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release() // Liberar recursos
    }

    private fun executeAttack(attackResId: Int) {
        currentAttackHandler?.removeCallbacks(currentAttackRunnable!!)

        if (isAttackRunning && character.background.constantState ==
            ContextCompat.getDrawable(this, attackResId)?.constantState) {
            return
        }

        if (isAttackRunning) {
            (character.background as? AnimationDrawable)?.stop()
        } else {
            idleAnimation.stop()
        }

        character.setBackgroundResource(attackResId)
        val attackAnimation = character.background as AnimationDrawable

        val totalDuration = attackAnimation.run {
            (0 until numberOfFrames).sumOf { getDuration(it).toLong() }
        }

        currentAttackRunnable = Runnable {
            character.setBackgroundResource(R.drawable.idleanim)
            idleAnimation = character.background as AnimationDrawable
            idleAnimation.start()
            isAttackRunning = false
        }

        currentAttackHandler = Handler(Looper.getMainLooper()).apply {
            postDelayed(currentAttackRunnable!!, totalDuration)
        }

        attackAnimation.setOneShot(true)
        attackAnimation.start()
        isAttackRunning = true
    }
}

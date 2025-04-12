package com.example.marc_cortes_victor_vallecillos_uf2_act3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import android.widget.Button

class MainActivity3 : AppCompatActivity() {
    private var isLeft = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val layout = findViewById<ConstraintLayout>(R.id.thirdLayout)
        val button = findViewById<Button>(R.id.movingButton)

        button.setOnClickListener {
            val constraintSet = ConstraintSet()
            constraintSet.clone(layout)

            if (isLeft) {
                constraintSet.clear(R.id.movingButton, ConstraintSet.START)
                constraintSet.connect(R.id.movingButton, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16)
            } else {
                constraintSet.clear(R.id.movingButton, ConstraintSet.END)
                constraintSet.connect(R.id.movingButton, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16)
            }

            constraintSet.applyTo(layout)
            isLeft = !isLeft
        }
    }
}

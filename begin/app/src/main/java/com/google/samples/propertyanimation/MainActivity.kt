/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.propertyanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListeners()
    }

    private fun setupListeners() {
        rotateButton.setOnClickListener {
            rotater()
        }

        translateButton.setOnClickListener {
            translater()
        }

        scaleButton.setOnClickListener {
            scaler()
        }

        fadeButton.setOnClickListener {
            fader()
        }

        colorizeButton.setOnClickListener {
            colorizer()
        }

        showerButton.setOnClickListener {
            shower()
        }
    }

    private fun rotater() {
        with(ObjectAnimator.ofFloat(star,View.ROTATION,    -360f, 0f)) {
            runAnimationWithDefaultProperties(
                view = rotateButton,
                animatorDuration = AnimatorDuration.LONG
            )
        }
    }

    private fun translater() {
       with(ObjectAnimator.ofFloat(star,View.TRANSLATION_X,200f)) {
           runAnimationWithDefaultProperties(translateButton)
       }
    }

    private fun scaler() {
        // The value 4f stands for the scale multiplier. The 4f means that the object will scale to
        // 4x its own size.
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
        with(ObjectAnimator.ofPropertyValuesHolder(star,scaleX,scaleY)) {
            runAnimationWithDefaultProperties(scaleButton)
        }
    }

    private fun fader() {
        with(ObjectAnimator.ofFloat(star, View.ALPHA, 0f)) {
            runAnimationWithDefaultProperties(
                view = colorizeButton,
                animatorDuration = AnimatorDuration.LONG
            )
        }
    }

    /**
     * Requires minimum API 21
     */
    @RequiresApi(value = 21)
    private fun colorizer() {
        val animator = ObjectAnimator.ofArgb(
            star.parent,
            "backgroundColor",
            Color.BLACK, Color.RED, Color.BLUE
        )
        animator.runAnimationWithDefaultProperties(
            view = colorizeButton,
            animatorDuration = AnimatorDuration.EXTRA
        )
    }

    private fun shower() {
    }

    private fun Animator.disableViewDuringAnimation(view: View) {
        /**
         * This adapter class provides empty implementations of the methods from
         * Animator.AnimatorListener. Any custom listener that cares only about a subset of the
         * methods of this listener can simply subclass this adapter class instead of implementing
         * the interface directly.
         */
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

    private fun ObjectAnimator.runAnimationWithDefaultProperties(
        view: View,
        customRepeatCount: Int = 1,
        customRepeatMode: Int = ObjectAnimator.REVERSE,
        animatorDuration: AnimatorDuration = AnimatorDuration.DEFAULT
    ) {
        disableViewDuringAnimation(view)
        duration = animatorDuration.timeInMillis
        repeatCount = customRepeatCount
        repeatMode = customRepeatMode
        start()
    }
}

enum class AnimatorDuration(val timeInMillis: Long) {
    DEFAULT(300),
    MEDIUM(500),
    LONG(1000),
    EXTRA(2000)
}
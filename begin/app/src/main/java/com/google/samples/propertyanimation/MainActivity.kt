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
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        val animator = ObjectAnimator.ofFloat(
            star,
            View.ROTATION,      // The view property ro animate over time
        -360f,          // Start value
            0f                 // End value
        )
        // The default animation duration in Android is 300ms. How ever we can change it:
        animator.duration = 1000
        //animator.startDelay = 1500

        // Fixing Genk behaviour using cal backs. There are call backs for ending, pausing,
        // resuming, repeating
        /**
         * This adapter class provides empty implementations of the methods from
         * Animator.AnimatorListener. Any custom listener that cares only about a subset of the
         * methods of this listener can simply subclass this adapter class instead of implementing
         * the interface directly.
         */
        animator.disableViewDuringAnimation(rotateButton)

        animator.start()

    }

    private fun translater() {
        val animator = ObjectAnimator.ofFloat(
            star,
            View.TRANSLATION_X,
            200f
        )
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(translateButton)
        animator.start()
    }

    private fun scaler() {
        // The value 4f stands for the scale multiplier. The 4f means that the object will scale to
        // 4x its own size.
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(
            star,
            scaleX,
            scaleY
        )
        animator.disableViewDuringAnimation(scaleButton)

        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE

        animator.start()

    }

    private fun fader() {
    }

    private fun colorizer() {
    }

    private fun shower() {
    }

    private fun Animator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

}

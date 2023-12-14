package com.example.memorinak


import android.app.ActionBar
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val layout = LinearLayout(applicationContext)
        layout.orientation = LinearLayout.VERTICAL

        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.weight = 1.toFloat() // единичный вес

        val catViews = ArrayList<ImageView>()
        for (i in 1..16) {
            catViews.add( // вызываем конструктор для создания нового ImageView
                ImageView(applicationContext).apply {
                    setImageResource(R.drawable.squarecat)
                    layoutParams = params
                    tag = "cat" // TODO: указать тег в зависимости от картинки
                    setOnClickListener(colorListener)
                })
        }

        val rows = Array(4, { LinearLayout(applicationContext)})

        var count = 0
        for (view in catViews) {
            val row: Int = count / 4
            rows[row].addView(view)
            count ++
        }
        for (row in rows) {
            layout.addView(row)
        }
        setContentView(layout)
    }

    suspend fun setBackgroundWithDelay(v: View) {
        delay(1000)
        v.setBackgroundColor(Color.YELLOW)
        delay(1000)
        v.visibility = View.INVISIBLE
        v.isClickable = false
    }

    suspend fun openCards() {

    }

    // обработчик нажатия на кнопку
    val colorListener = View.OnClickListener() {
        // запуск функции в фоновом потоке
        GlobalScope.launch (Dispatchers.Main)
        { setBackgroundWithDelay(it) }
    }
}
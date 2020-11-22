package com.studioos.batuguntingkertas

import android.icu.util.TimeUnit
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.isVisible
import java.util.*
import java.util.concurrent.Executors
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), CallbackHasil {

    lateinit var ivReset: ImageView
    lateinit var ivBatu1: ImageView
    lateinit var ivKertas1: ImageView
    lateinit var ivGunting1: ImageView
    lateinit var flBatu1: FrameLayout
    lateinit var flKertas1: FrameLayout
    lateinit var flGunting1: FrameLayout
    lateinit var flBatu2: FrameLayout
    lateinit var flKertas2: FrameLayout
    lateinit var flGunting2: FrameLayout
    lateinit var llVS: LinearLayout
    lateinit var llPemain1Win: LinearLayout
    lateinit var llPemain2Win: LinearLayout
    lateinit var llDraw: LinearLayout
    lateinit var tvPemain1: TextView
    lateinit var tvPemain2: TextView
    var pilihan1 = ""
    var numAcak = 0
    var acakDuration = 100L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        ivReset = findViewById(R.id.iv_reset)
        flBatu1 = findViewById(R.id.fl_batu1)
        flKertas1 = findViewById(R.id.fl_kertas1)
        flGunting1 = findViewById(R.id.fl_gunting1)
        flBatu2 = findViewById(R.id.fl_batu2)
        flKertas2 = findViewById(R.id.fl_kertas2)
        flGunting2 = findViewById(R.id.fl_gunting2)
        ivBatu1 = findViewById(R.id.iv_batu1)
        ivKertas1 = findViewById(R.id.iv_kertas1)
        ivGunting1 = findViewById(R.id.iv_gunting1)
        llVS = findViewById(R.id.ll_vs)
        llPemain1Win = findViewById(R.id.ll_p1_win)
        llPemain2Win = findViewById(R.id.ll_p2_win)
        llDraw = findViewById(R.id.ll_draw)
        tvPemain1 = findViewById(R.id.tv_player01)
        tvPemain2 = findViewById(R.id.tv_player02)
        reset()

        val ivpilihan = mutableListOf(ivBatu1, ivKertas1, ivGunting1)

        ivpilihan.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                if (it == ivBatu1) {
                    if (flBatu1.isVisible == false) {
                        flBatu1.visibility = View.VISIBLE
                    }
                    pilihan1 = "batu"
                } else if (it == ivKertas1) {
                    if (flKertas1.isVisible == false) {
                        flKertas1.visibility = View.VISIBLE
                    }
                    pilihan1 = "kertas"
                } else {
                    if (flGunting1.isVisible == false) {
                        flGunting1.visibility = View.VISIBLE
                    }
                    pilihan1 = "gunting"
                }
                Log.e("MainActivity", "memilih $pilihan1")
                lockButton()
                loopAnimmasiAcak()
//                startCompare()
            }
        }

        ivReset.setOnClickListener {
            reset()
        }

    }

    private fun loopAnimmasiAcak() {
        mutableListOf(flBatu2, flKertas2, flGunting2)
            .forEachIndexed { index, i ->
//                if (i.alpha == 1f){
//                    i.animate().alpha(0f).setDuration(0).start()
                Handler().postDelayed({
                    if (i.isVisible == true) {
                        i.visibility = View.INVISIBLE
                        Log.e("MainActivity", "Com Chosen background invisible")
                    }
                }, acakDuration)
            }
        var controllerAnim = ContollerPermainan(this)
        Handler().postDelayed({
            if (numAcak <= 30) {
                controllerAnim.animasiAcak()
                Log.e("MainActivity", "repeat #${numAcak}")
            } else {
                startCompare()
                numAcak = 0
            }
        }, acakDuration)
//        }
    }

    private fun startCompare() {
        var suit1 = ModelSuit(pilihan1)
        Log.e("MainActivity", "panggil ModelSuit")
        var controller = ContollerPermainan(this)
        controller.setSuit1(suit1)
        controller.pilihanCom()
        controller.cariPemenang()
    }

    private fun lockButton() {
        ivBatu1.isEnabled = false
        ivKertas1.isEnabled = false
        ivGunting1.isEnabled = false
        Log.e("MainActivity", "lock Choices Button")
    }

    private fun unlookButton() {
        if (ivBatu1.isEnabled == false) {
            ivBatu1.isEnabled = true
            Log.e("MainActivity", "unlock Choices Button")
        }
        if (ivKertas1.isEnabled == false) {
            ivKertas1.isEnabled = true
            Log.e("MainActivity", "unlock Choices Button")
        }
        if (ivGunting1.isEnabled == false) {
            ivGunting1.isEnabled = true
            Log.e("MainActivity", "unlock Choices Button")
        }
    }

    private fun reset() {
        mutableListOf(flBatu1, flKertas1, flGunting1, flBatu2, flKertas2, flGunting2)
            .forEachIndexed { index, i ->
                if (i.isVisible == true) {
                    i.visibility = View.INVISIBLE
                    Log.e("MainActivity", "Chosen background invisible")
                }
            }
        mutableListOf(llPemain1Win, llPemain2Win, llDraw)
            .forEachIndexed { index, i ->
                i.animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).rotation(-180f).setDuration(0)
                    .start()
            }
        llVS.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(1000).start()
        Log.e("MainActivity", "Make Win Label Alpha and VS label no Alpha")
        pilihan1 = ""
        unlookButton()
        ivReset.animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).rotation(180f).setDuration(1000)
            .start()
        tvPemain1.animate().scaleX(1f).scaleY(1f).translationY(0f).translationX(0f)
            .setDuration(1000).start()
        tvPemain2.animate().scaleX(1f).scaleY(1f).translationY(0f).translationX(0f)
            .setDuration(1000).start()
    }

    override fun animAcak(animAcak: String) {
        when (animAcak) {
            "batu" -> {
                flBatu2.visibility = View.VISIBLE
                Log.e("MainActivity", "anim batu")
            }
            "kertas" -> {
                flKertas2.visibility = View.VISIBLE
                Log.e("MainActivity", "anim kertas")
            }
            "gunting" -> {
                flGunting2.visibility = View.VISIBLE
                Log.e("MainActivity", "anim gunting")
            }
        }
        numAcak++
        loopAnimmasiAcak()
    }

    override fun hasilAcak(hasilAcak: String) {
//        Toast.makeText(this, "computer memilih $hasilAcak", Toast.LENGTH_SHORT).show()
        when (hasilAcak) {
            "batu" -> flBatu2.visibility = View.VISIBLE
            "kertas" -> flKertas2.visibility = View.VISIBLE
            "gunting" -> flGunting2.visibility = View.VISIBLE
        }
        Log.e("MainActivity", "pilihan Computer $hasilAcak")
    }

    override fun hasilMenang(hasilMenang: String) {
        llVS.animate().alpha(0f).scaleX(0.5f).scaleY(0.5f).setDuration(0).start()
        when (hasilMenang) {
            "0" -> {
                llDraw.visibility = View.VISIBLE
                llDraw.animate().alpha(1f).scaleX(1f).scaleY(1f).rotation(350f).setDuration(1000)
                    .start()
            }
            "1" -> {
                llPemain1Win.visibility = View.VISIBLE
                tvPemain1.animate().scaleX(1.5f).scaleY(1.5f).translationY(-30f).translationX(110f)
                    .setDuration(1000).start()
                llPemain1Win.animate().alpha(1f).scaleX(1f).scaleY(1f).rotation(350f)
                    .setDuration(1000).start()
            }
            "2" -> {
                llPemain2Win.visibility = View.VISIBLE
                tvPemain2.animate().scaleX(1.5f).scaleY(1.5f).translationY(-30f).translationX(-70f)
                    .setDuration(1000).start()
                llPemain2Win.animate().alpha(1f).scaleX(1f).scaleY(1f).rotation(350f)
                    .setDuration(1000).start()
            }
        }
        ivReset.animate().alpha(1f).scaleX(1f).scaleY(1f).rotation(-180f).setDuration(1000).start()
        Log.e("MainActivity", "pemenangnya $hasilMenang")
    }

}
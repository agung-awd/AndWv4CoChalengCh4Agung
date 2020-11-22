package com.studioos.batuguntingkertas

import android.util.Log
import java.util.logging.Handler

public class ContollerPermainan (private val listener:CallbackHasil){
    private var suit1 : ModelSuit? = null
    private var pilihan2 = arrayListOf("batu", "kertas", "gunting")
    var suit2 = pilihan2.random()

    fun animasiAcak(){
        var animAcak = pilihan2.random()
        listener.animAcak(animAcak)
    }

    fun pilihanCom(){
        listener.hasilAcak(suit2)
        Log.e("ContollerPermainan", "Com memilih = $suit2")
    }

    fun cariPemenang() {
        var hasilSuit: String
        if (suit1!!.suit1.equals("batu") && suit2.equals("batu") ||
            suit1!!.suit1.equals("gunting") && suit2.equals("gunting") ||
            suit1!!.suit1.equals("kertas") && suit2.equals("kertas"))
            hasilSuit = "0"
        else if (suit1!!.suit1.equals("batu") && suit2.equals("kertas") ||
            suit1!!.suit1.equals("kertas") && suit2.equals("gunting") ||
            suit1!!.suit1.equals("gunting") && suit2.equals("batu"))
            hasilSuit = "2"
        else {
            hasilSuit = "1"
        }
        listener.hasilMenang(hasilSuit)
        Log.e("ContollerPermainan", "Pemenang Pemain $hasilSuit")
    }

    fun setSuit1(suit1:ModelSuit){
    this.suit1 = suit1
        Log.e("ContollerPermainan", "Ambil data Pilihan pemain 1 $suit1 dari Model")
    }
}
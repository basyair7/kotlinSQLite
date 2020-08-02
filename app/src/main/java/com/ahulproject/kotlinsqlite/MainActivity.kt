package com.ahulproject.kotlinsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    //In kotlin 'var' is used to declare a metable variable, On the other hand
    //'Internal' means a variable is visible within a given module
    internal var dbHelper = DatabaseHelper(this)

    /**
     * Let's created a function to show Toast messange
     */
    fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()

    }
    /**
     * Let's create a function to show an alert dialog with data dialog.
     */
    fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    /**
     * Let's create a method to clear out edittext
     */
    fun clearEditTexts(){
        nameTxt.setText("")
        npmNmr.setText("")
        jurusanTxt.setText("")
        idTxt.setText("")

    }
    /**
     * Let's override our onCreate method
     */
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleMasuk()
        handleUpdates()
        handleHapus()
        handleTampil()
    }

    /**
     * When out handleMasuk button is clicked.
     */
    fun handleMasuk(){
        masukBtn.setOnClickListener {
            try {
                dbHelper.insertData(nameTxt.text.toString(), npmNmr.text.toString(),
                        jurusanTxt.text.toString())
                clearEditTexts()
            } catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    /**
     * When out handleUpdates data button is clicked
     */
    fun handleUpdates(){
        updateBtn.setOnClickListener {
            try {
                val isUpdate = dbHelper.updateData(idTxt.text.toString(),
                        nameTxt.text.toString(),
                        npmNmr.text.toString(),
                        jurusanTxt.text.toString())
                if (isUpdate == true)
                    showToast("Data Sudah diperbarui")
                else
                    showToast("Data Not Update")
            } catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    /**
     * When out handleHapus button is clicked
     */
    fun handleHapus(){
        hapusBtn.setOnClickListener {
            try {
                dbHelper.deleteData(idTxt.text.toString())
                clearEditTexts()
            } catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    /**
     * When out Tampilkan semua is clicked
     */
    fun handleTampil(){
        tampilBtn.setOnClickListener (
            View.OnClickListener {
                val res = dbHelper.allData
                if (res.count == 0){
                    showDialog("Error", "Data tidak ada")
                    return@OnClickListener
                }
                val buffer = StringBuffer()
                while (res.moveToNext()){
                    buffer.append("ID               : " + res.getString(0) + "\n")
                    buffer.append("Name             : " + res.getString(1) + "\n")
                    buffer.append("NPM              : " + res.getString(2) + "\n")
                    buffer.append("Jurusan(Prodi)   : " + res.getString(3) + "\n\n")
                }
                showDialog("Data Listing", buffer.toString())
            }
        )
    }
}
// End
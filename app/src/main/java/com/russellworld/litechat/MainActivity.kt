package com.russellworld.litechat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.russellworld.litechat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val database = Firebase.database
        val myRef = database.getReference("messages")
        mBinding.bSend.setOnClickListener {
            myRef.setValue(mBinding.eMessage.text.toString())
        }
        onChangeListener(myRef)
    }

    private fun onChangeListener(dbRef: DatabaseReference) {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mBinding.apply {
                    rcVIew.append("\n")
                    rcVIew.append(snapshot.value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
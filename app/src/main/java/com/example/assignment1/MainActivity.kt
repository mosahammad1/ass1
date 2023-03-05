package com.example.assignment1


import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        findViewById<Button>(R.id.save).setOnClickListener {

            val name = findViewById<EditText>(R.id.PersonName).text.toString()
            val number = findViewById<EditText>(R.id.PersonNumber).text.toString()
            val address = findViewById<EditText>(R.id.PersonAddress).text.toString()


            val person = hashMapOf(
                "name" to name,
                "number" to number,
                "address" to address
            )

            db.collection("Persons")
                .add(person)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(applicationContext,"${documentReference.id}",Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener { e ->
                    Toast.makeText(applicationContext,"$e",Toast.LENGTH_SHORT).show()


                }


            findViewById<Button>(R.id.getData).setOnClickListener {
                val docRef = db.collection("Persons").document()
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            Log.d(TAG, "Person data: ${document.data}")
                        } else {
                            Log.d(TAG, "No such Person")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }

            }



            findViewById<Button>(R.id.delete).setOnClickListener {
                db.collection("Persons").document("number")
                    .delete()
                    .addOnSuccessListener { Log.d(TAG, "successfully deleted!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting", e) }

            }





        }
    }
}
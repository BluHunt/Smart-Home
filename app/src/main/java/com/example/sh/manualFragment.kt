package com.example.sh


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_manual.*

class manualFragment : Fragment() {
    private var db : DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private var relay1_db : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("REL1")
    private var relay2_db : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("REL2")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        relay1_db.setValue("1")
        relay2_db.setValue("1")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manual, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bulb.visibility=View.INVISIBLE
        fan.visibility=View.INVISIBLE
        relay1.setOnClickListener{
            if(relay1.text == "OFF"){
                relay1.setText("ON")
                relay1_db.setValue("0")
                bulb.visibility=View.VISIBLE
                context?.toast("Turing ON Bulb")
            }else{
                relay1.setText("OFF")
                relay1_db.setValue("1")
                bulb.visibility=View.INVISIBLE
                context?.toast("Turing OFF Bulb")
            }
        }
        relay2.setOnClickListener{
            if(relay2.text == "OFF"){
                relay2.setText("ON")
                relay2_db.setValue("0")
                fan.visibility=View.VISIBLE
                context?.toast("Turing ON FAN")
            }else{
                relay2.setText("OFF")
                relay2_db.setValue("1")
                fan.visibility=View.INVISIBLE
                context?.toast("Turing OFF FAN")
            }
        }
        /*relay1.setOnClickListener{
            pbar.visibility = View.VISIBLE
                led.setValue("1")
            pbar.visibility = View.INVISIBLE
        }

        off_button.setOnClickListener{
            pbar.visibility = View.VISIBLE
            led.setValue("0")
            pbar.visibility = View.INVISIBLE
        }
*/
    }

}

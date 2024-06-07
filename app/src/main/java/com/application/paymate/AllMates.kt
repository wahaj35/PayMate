package com.application.paymate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.paymate.databinding.FragmentAllMatesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllMates : Fragment() {
    private lateinit var binding: FragmentAllMatesBinding
    private lateinit var matesList: ArrayList<MatesInfo>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_mates, container, false)

        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference("admin_profiles")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Mates")
        matesList = ArrayList()

        val recyclerView = binding.allMatesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = AllMatesAdapter(matesList,requireContext())
        recyclerView.adapter = adapter

        databaseReference.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                for(data in snapshot.children){
                    val mateInfo = data.getValue(MatesInfo::class.java)
                    matesList.add(mateInfo!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

}
package com.reihanalavi.mvpbarclays.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.reihanalavi.mvpbarclays.R

/**
 * A simple [Fragment] subclass.
 */
class PastListDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val views = inflater.inflate(R.layout.fragment_past_list_detail, container, false)
        return views
    }

}

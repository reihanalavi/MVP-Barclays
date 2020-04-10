package com.reihanalavi.mvpbarclays.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.reihanalavi.mvpbarclays.R
import kotlinx.android.synthetic.main.fragment_teams_list_detail.view.*

/**
 * A simple [Fragment] subclass.
 */
class TeamsListDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams_list_detail, container, false)
    }

}

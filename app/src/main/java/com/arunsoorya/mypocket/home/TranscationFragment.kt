package com.arunsoorya.mypocket.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arunsoorya.mypocket.R
import kotlinx.android.synthetic.main.fragment_personal.view.*

class TranscationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_personal, container, false)
        return rootView
    }

    companion object {
        fun newInstance(): TranscationFragment {
            val fragment = TranscationFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
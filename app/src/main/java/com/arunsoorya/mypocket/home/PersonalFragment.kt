package com.arunsoorya.mypocket.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arunsoorya.mypocket.R
import com.arunsoorya.mypocket.sync.PsmsVo
import kotlinx.android.synthetic.main.fragment_personal.*

class PersonalFragment : Fragment() {
    lateinit var smsAdapter: SmsAdapter
    var smslist: MutableList<PsmsVo> = arrayListOf()

    companion object {
        fun newInstance(): PersonalFragment {
            val fragment = PersonalFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_personal, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        recycler_view.setHasFixedSize(true)
//        val layoutManager = LinearLayoutManager(activity)
//        recycler_view.layoutManager = layoutManager
//        smsAdapter = SmsAdapter(context, smslist)
//        recycler_view.setAdapter(smsAdapter)
    }


}
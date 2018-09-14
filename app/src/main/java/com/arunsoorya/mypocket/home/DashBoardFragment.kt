package com.arunsoorya.mypocket.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arunsoorya.mypocket.R
import com.arunsoorya.mypocket.home.dashboard.DashboardViewModel
import com.arunsoorya.mypocket.home.dashboard.FuelAdapter
import com.arunsoorya.mypocket.home.dashboard.FuelCity
import com.arunsoorya.mypocket.logd
import com.arunsoorya.mypocket.sync.PsmsVo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sync_sms_new.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashBoardFragment : Fragment() {
    lateinit var fuelAdapter: FuelAdapter
    private var compositeDisposable = CompositeDisposable()

    companion object {
        fun newInstance(): DashBoardFragment {
            val fragment = DashBoardFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager


        val dashboardViewModel = DashboardViewModel(context as Activity)

        val disposable: Disposable = dashboardViewModel.downloadInformation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ },
                        {
                            showSyncMessage(it.toString())
                            it.printStackTrace()
                        })

        val disposable1: Disposable = dashboardViewModel.mLoadingIndicatorSubject
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) progressbar_f.visibility = View.VISIBLE
                    else
                        progressbar_f.visibility = View.INVISIBLE
                }, {
                    it.printStackTrace()
                })

        val disposable2: Disposable = dashboardViewModel.mSnackbarText
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ showSyncMessage(it) },
                        {
                            showSyncMessage(it.toString())
                            it.printStackTrace()
                        })
        val showAllCities: Disposable = dashboardViewModel.getFuelList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ showSimInfo(it) },
                        {
                            showSyncMessage(it.toString())
                            it.printStackTrace()
                        })

        compositeDisposable.add(disposable1)
        compositeDisposable.add(disposable2)
        compositeDisposable.add(disposable)
        compositeDisposable.add(showAllCities)


    }

    fun showSimInfo(simInfos: List<FuelCity>) {
        simInfos.toString().logd()
        showSyncMessage(simInfos.toString())
        fuelAdapter = FuelAdapter(context, simInfos)
        recyclerView.setAdapter(fuelAdapter)
    }

    fun showSyncMessage(message: String) {
        syncMessage_f.visibility = View.VISIBLE
//        syncMessage_f.text = message
    }


}
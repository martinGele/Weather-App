package com.martin.one.ui.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.github.di.Injectable
import com.martin.one.R
import com.martin.one.adapter.CitiesAdapter
import com.martin.one.databinding.FragmentDetailScreenBinding
import com.martin.weatherestonia.model.Forecast
import kotlinx.android.synthetic.main.fragment_detail_screen.*

/**
 * A simple [Fragment] subclass.
 */
class DetailScreenFragment : Fragment(), Injectable {

    var weather: Forecast? = null


    private val listAdapterCities = CitiesAdapter(arrayListOf())
    lateinit var dataBinding: FragmentDetailScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        dataBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_detail_screen,
                container,
                false
            )
        return dataBinding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { weather = DetailScreenFragmentArgs.fromBundle(it).Forecast }
        dataBinding.weather = weather

        dataBinding.weather?.day?.places?.let { listAdapterCities.updateCitiesList(it) }

        recycleViewCities.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapterCities

        }


    }

}

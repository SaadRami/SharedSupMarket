package be.supinfo.supermarketapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import be.supinfo.supermarketapp.R

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // SUBSCRIBE TO CHANGES IN VM DATA
        // To Subscrie to changes in a ViewModel's data
        // from the datalive object, call the observe function, this function takes a lifecycle owner (this) + an instance of an observer
        // class that has a single function
        // it : is the value published from the viewModel
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        recyclerView = view.findViewById(R.id.rvProductList)


        // Creating and attaching a viewModel to an Activity
        // ViewModelProvider is responsible of instatiating the viewmodel an return its reference
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.products.observe(viewLifecycleOwner, Observer {
//            for (product in it) {
//                Log.i(VIEWMMODEL_TAG, "${product.productTitle}  (\$${product.price})")
//            }

            val adapter = ProductsRecyclerViewAdapter(requireContext(), it)
            recyclerView.adapter = adapter
//            val sb = StringBuilder()
//            for (product in it) {
//                sb.append("${product.title}  (\$${product.price})")
//                    .append("\n")
//            }
            //tvProducts.text = sb
        })


        // Inflate the layout for this fragment
        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
////        view.findViewById<Button>(R.id.button_first).setOnClickListener {
////            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
////        }
//
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//    }
}
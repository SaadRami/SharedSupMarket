package be.supinfo.supermarketapp.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import be.supinfo.supermarketapp.App
import be.supinfo.supermarketapp.BaseFragment
import be.supinfo.supermarketapp.R
import be.supinfo.supermarketapp.util.ViewModelFactory
import javax.inject.Inject


class TransactionsFragment : BaseFragment() {
    private lateinit var transactionsViewModel: TransactionsViewModel
    private lateinit var rvTransactions: RecyclerView

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.component.inject(this)
        val view = inflater.inflate(R.layout.fragment_transactions, container, false)

        rvTransactions = view.findViewById(R.id.rvTransactions)

        setUpFragment(
            getString(R.string.transactions),
            isHome = false,
            hideFab = false,
            isDrawerEnabled = false,
            toolbarFlag = true
        )

        transactionsViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(TransactionsViewModel::class.java)

        transactionsViewModel.transactions.observe(viewLifecycleOwner, Observer {
            rvTransactions.adapter = TransactionsRecyclerViewAdapter(requireContext(), it, null)
        })


        return view
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
}
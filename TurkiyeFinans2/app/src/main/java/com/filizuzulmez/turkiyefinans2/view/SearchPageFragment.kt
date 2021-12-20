package com.filizuzulmez.turkiyefinans2.view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.filizuzulmez.turkiyefinans2.R
import com.filizuzulmez.turkiyefinans2.adapter.SectionListAdapter
import com.filizuzulmez.turkiyefinans2.model.Result
import com.filizuzulmez.turkiyefinans2.viewModel.SearchFragmentViewModel
import kotlinx.coroutines.flow.collectLatest

class SearchPageFragment: Fragment() {

    private lateinit var rvSectionList: RecyclerView
    lateinit var sectionListAdapter: SectionListAdapter
    private lateinit var etSearchProduct: EditText

    private var wordToSearch: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_search_page, container, false)

        rvSectionList = v.findViewById(R.id.rvSectionList)
        etSearchProduct = v.findViewById(R.id.etSearchProduct)

        initRecyclerView()
        setEdittext()

        sectionListAdapter.setCallBack(object : SectionListAdapter.CallBack{
            override fun onClickProduct(product: Result?, position: Int) {
                Log.d("clicked item: ", product!!.getArtistName())

                var fragment =  DetailPageFragment()
                fragment.getData(product!!)
                var fragmentTransaction = requireFragmentManager().beginTransaction()
                fragmentTransaction.replace(R.id.frameLayout, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        })


        return  v;
    }

    private fun setEdittext() {

        etSearchProduct.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                var handled = false
                if (p1 == EditorInfo.IME_ACTION_SEARCH){

                    wordToSearch = etSearchProduct.text.toString()

                    if (wordToSearch != ""){

                        initViewModel(wordToSearch!!)

                    }
                    handled = true
                }
                return handled
            }

        })
    }

    private fun initRecyclerView() {
        rvSectionList.apply {
            layoutManager = GridLayoutManager(activity,2)
            val decoration  = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            sectionListAdapter = SectionListAdapter(requireActivity())
            rvSectionList.adapter = sectionListAdapter



        }
    }

    private fun initViewModel(searchItem: String) {
        val viewModel  = ViewModelProvider(this).get(SearchFragmentViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getListData(searchItem).collectLatest {
                sectionListAdapter.submitData(it)
            }
        }

    }
}
package com.example.keepnotes.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.keepnotes.R
import com.example.keepnotes.databinding.FragmentListBinding
import com.example.keepnotes.viewmodel.NotesViewModel

class ListFragment : Fragment() {

    private  val adapter: ListAdapter by lazy { ListAdapter() }

    private val viewModel: NotesViewModel by viewModels()
    private  var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Binding
        _binding = FragmentListBinding.inflate(inflater, container, false)

        binding.extendedFab.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
            binding.extendedFab.hide()
        }


        // Set up Recycler View
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)



        binding.recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Scroll Down
                    if (binding.extendedFab.isShown) {
                        binding.extendedFab.hide()
                    }
                } else if (dy < 0) {
                    // Scroll Up
                    if (!binding.extendedFab.isShown) {
                        binding.extendedFab.show()
                    }
                }
            }
        })


        viewModel.getAllNotes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setData(it)
            }
        })

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

}
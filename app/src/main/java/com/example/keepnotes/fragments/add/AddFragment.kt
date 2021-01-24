package com.example.keepnotes.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.keepnotes.R
import com.example.keepnotes.database.Notes
import com.example.keepnotes.databinding.FragmentAddBinding
import com.example.keepnotes.viewmodel.NotesViewModel
import com.example.keepnotes.viewmodel.SharedViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddFragment : Fragment() {
    private val viewModel: NotesViewModel  by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private var _binding : FragmentAddBinding? = null
    private val  binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // View Binding
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.addPrioritySpinner.onItemSelectedListener = sharedViewModel.listener

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_add_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.add_save -> insertToDataBase()
        }

        return super.onOptionsItemSelected(item)

    }

    private fun insertToDataBase() {
        // Get input text
        val mTitle = binding.addTitle.editText?.text.toString()

        binding.addTitle.editText?.doOnTextChanged { inputText, _, _, _ ->
            // Respond to input text change
        }
        val mDescription = binding.addDescriptionTv.text.toString().trim()

        val mPriority = binding.addPrioritySpinner.selectedItem.toString()

        if (sharedViewModel.verifyDataFromUser(mTitle, mDescription)){
            viewModel.insertData(
                    Notes(
                            id = 0,
                            mTitle,
                            mDescription,
                            mPriority
                    )
            )
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
        }

    }



    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

}
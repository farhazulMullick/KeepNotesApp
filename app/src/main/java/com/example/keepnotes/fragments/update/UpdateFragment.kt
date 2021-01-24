package com.example.keepnotes.fragments.update

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.compose.navArgument
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.keepnotes.R
import com.example.keepnotes.database.Notes
import com.example.keepnotes.databinding.FragmentUpdateBinding
import com.example.keepnotes.viewmodel.NotesViewModel
import com.example.keepnotes.viewmodel.SharedViewModel
import com.google.android.material.transition.MaterialContainerTransform


class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel by viewModels<SharedViewModel>()
    private val notesViewModel by viewModels<NotesViewModel>()

    private var _binding : FragmentUpdateBinding? = null
    private val  binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        binding.updateTitleTv.setText(args.currentItem.title)
        binding.updateDescriptionTv.setText(args.currentItem.description)
        binding.updatePrioritySpinner.onItemSelectedListener = sharedViewModel.listener

        binding.updatePrioritySpinner.setSelection(
            when(args.currentItem.priority){
                "HIGH" -> 0
                "MEDIUM" -> 1
                else -> 2
            }
        )

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_update_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.update_save -> updateItem()
            R.id.update_delete -> confirmDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDelete() {

        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok
                ) { _, _ ->
                    // User clicked OK button
                    deleteItem()
                }
                setNegativeButton(getString(R.string.cancel)
                ) { _, _ ->
                    // User cancelled the dialog
                }
            }
            // Set other dialog properties
            builder.setMessage("${args.currentItem.title} will be deleted from Notes")
                    .setTitle("Do you want to delete ${args.currentItem.title}?")

            // Create the AlertDialog
            builder.show()
        }
    }

     private fun deleteItem() {
        Log.d("UPDATE_FRAGMENT", "deleteItem")
        notesViewModel.deleteItem(
                Notes(
                        args.currentItem.id,
                        args.currentItem.title,
                        args.currentItem.description,
                        args.currentItem.priority,
                )
        )
        Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

    private fun updateItem() {
        val mTitle = binding.updateTitleTv.text.toString().trim()
        val mPriority = binding.updatePrioritySpinner.selectedItem.toString()
        val mDescription = binding.updateDescriptionTv.text.toString().trim()

        if(sharedViewModel.verifyDataFromUser(mTitle, mDescription)){
            notesViewModel.updateData(
                    Notes(
                            args.currentItem.id,
                            mTitle,
                            mDescription,
                            mPriority
                    )
            )
            Toast.makeText(requireActivity(), "Updated Successfully!",  Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireActivity(), "Fields Cannot be empty",  Toast.LENGTH_SHORT).show()
        }

    }

}
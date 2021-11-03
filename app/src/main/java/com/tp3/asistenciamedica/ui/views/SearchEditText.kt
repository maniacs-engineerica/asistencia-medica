package com.tp3.asistenciamedica.ui.views

import android.R
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import java.util.*

class SearchEditText @kotlin.jvm.JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatAutoCompleteTextView(context, attrs, defStyleAttr), AdapterView.OnItemClickListener {

    interface OnItemSelected {
        fun onItemSelected(item: Any?)
    }

    var onItemSelected: OnItemSelected? = null
    var fetcher: ((String) -> List<Any>)? = null

    private var selectedItem: Any? = null

    init {
        onItemClickListener = this
        setAdapter(SearchAdapter())
        threshold = 0
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) {
            if (selectedItem == null || selectedItem.toString() != getText().toString()) {
                setSelectedItem(null)
            }
        } else if (selectedItem == null) {
            performFiltering(text, 0)
        }
    }

    override fun enoughToFilter(): Boolean {
        return true
    }

    override fun onItemClick(adapterView: AdapterView<*>, view: View?, i: Int, l: Long) {
        selectedItem = adapterView.getItemAtPosition(i)
        if (onItemSelected != null) {
            onItemSelected!!.onItemSelected(selectedItem)
        }
    }

    fun setSelectedItem(selectedItem: Any?) {
        this.selectedItem = selectedItem
        if (selectedItem != null) {
            setText(selectedItem.toString())
        } else {
            setText(null)
        }
    }

    fun getSelectedItem(): Any? {
        return selectedItem
    }

    inner class SearchAdapter : ArrayAdapter<Any>(this.context, R.layout.simple_dropdown_item_1line),
        Filterable {
        private var items: List<Any> = listOf()

        override fun getCount(): Int {
            return items.size
        }

        override fun getItem(index: Int): Any {
            return items[index]
        }

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence): FilterResults {
                    val filterResults = FilterResults()

                    items = fetcher?.let { it(constraint.toString()) } ?: listOf()

                    filterResults.values = items
                    filterResults.count = items.size
                    return filterResults
                }

                override fun publishResults(constraint: CharSequence, results: FilterResults) {
                    if (results.count > 0) {
                        notifyDataSetChanged()
                    } else {
                        notifyDataSetInvalidated()
                    }
                }
            }
        }
    }
}
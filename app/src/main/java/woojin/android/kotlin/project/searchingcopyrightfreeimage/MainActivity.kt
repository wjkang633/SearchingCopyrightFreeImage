package woojin.android.kotlin.project.searchingcopyrightfreeimage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import woojin.android.kotlin.project.searchingcopyrightfreeimage.data.Repository
import woojin.android.kotlin.project.searchingcopyrightfreeimage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val scope = MainScope()
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        bindViews()
        fetchRandomPhotos()
    }

    override fun onDestroy() {
        super.onDestroy()

        scope.cancel()
    }

    private fun initViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = PhotoAdapter()
    }

    private fun bindViews() {
        //검색바
        binding.searchEditText.setOnEditorActionListener { editText, actionId, event ->
            //키보드 내리기&포커스 날리기
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                currentFocus?.let { view ->
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

                    view.clearFocus()
                }

                fetchRandomPhotos(editText.text.toString())
            }

//            return@setOnEditorActionListener true
            true
        }

        binding.refreshLayout.setOnRefreshListener {
            fetchRandomPhotos(binding.searchEditText.text.toString())
        }
    }

    private fun fetchRandomPhotos(query: String? = null) = scope.launch {
        try {
            Repository.getRandomPhotos(query)?.let { photos ->
                (binding.recyclerView.adapter as? PhotoAdapter)?.apply {
                    this.photos = photos
                    notifyDataSetChanged()
                }
            }
            binding.recyclerView.isVisible = true
            binding.errorDescriptionTextView.isVisible = false
        } catch (exception: Exception) {
            binding.recyclerView.isVisible = false
            binding.errorDescriptionTextView.isVisible = true
        } finally {
            binding.shimmerLayout.isVisible = false
            binding.refreshLayout.isRefreshing = false
        }
    }
}
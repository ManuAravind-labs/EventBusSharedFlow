package com.android4you.eventbuswithflow

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android4you.eventbuswithflow.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val eventBus = EventBus()
    private val eventJob = Job()
    private val eventScope = CoroutineScope(Dispatchers.Main + eventJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventScope.launch {
            eventBus.events
                .collect { event ->
                    when (event) {
                        is Event.MessageEvent -> {
                            // Handle MessageEvent
                            showToast(event.message)
                        }
                        // Handle other event types as needed
                    }
                }
        }

        binding.button.setOnClickListener {
            lifecycleScope.launch {
                eventBus.emitEvent(Event.MessageEvent("Button Clicked"))
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        eventJob.cancel()
    }
}

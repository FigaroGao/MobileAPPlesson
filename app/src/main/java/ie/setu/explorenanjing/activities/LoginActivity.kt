package ie.setu.explorenanjing.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ie.setu.explorenanjing.adapters.ImageCarouselAdapter
import ie.setu.explorenanjing.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var autoScroll: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 背景轮播图
        val images = listOf(
            "https://cdn.jsdelivr.net/gh/FigaroGao/APPlesson-nanjing-data@main/images/fuzimiao.jpg",
            "https://cdn.jsdelivr.net/gh/FigaroGao/APPlesson-nanjing-data@main/images/nanjing_museum.jpg"
        )
        binding.viewPager.adapter = ImageCarouselAdapter(this, images)
        autoScroll = object : Runnable {
            override fun run() {
                binding.viewPager.currentItem = (binding.viewPager.currentItem + 1) % images.size
                handler.postDelayed(this, 3500)
            }
        }
        handler.postDelayed(autoScroll, 3500)

        // 四个按钮都直接跳主页
        binding.btnVisitor.setOnClickListener { jumpToMain() }
        binding.btnContinue.setOnClickListener { jumpToMain() }
        binding.btnGoogle.setOnClickListener { jumpToMain() }
        binding.btnApple.setOnClickListener { jumpToMain() }
    }

    private fun jumpToMain() {
        startActivity(Intent(this, AttractionListActivity::class.java))

    }

    override fun onDestroy() {
        handler.removeCallbacks(autoScroll)
        super.onDestroy()
    }
}
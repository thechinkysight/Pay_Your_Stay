package thechinkysight.app.payyourstay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import thechinkysight.app.payyourstay.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        _navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(_navController)
    }


}
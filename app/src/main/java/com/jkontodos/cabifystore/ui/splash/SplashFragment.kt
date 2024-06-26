package com.jkontodos.cabifystore.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.jkontodos.cabifystore.R
import com.jkontodos.cabifystore.databinding.FragmentSplashBinding
import com.jkontodos.cabifystore.ui.commons.visibleOrNot
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSplashLayout()
        startAnimations()
    }

    /**
     * Selects the type of layout to be shown on the splash screen using the three available options,
     * and randomly chooses one each time the app initializes
     */
    private fun setSplashLayout() {
        val options = listOf(
            Pair(R.string.splash_store, R.drawable.ic_store),
            Pair(R.string.splash_discounts, R.drawable.ic_discounts),
            Pair(R.string.splash_shipping, R.drawable.ic_shipping)
        )

        val randomOption = options.random()
        binding.tvSplashTitleLower.setText(randomOption.first)
        binding.tvSplashTitle.setText(randomOption.first)
        binding.ivBottomImage.setImageResource(randomOption.second)
    }

    /**
     * Selects and starts the animations at the bottom of the splash screen
     */
    private fun startAnimations() {
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up_logo)
        val anim2 = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up_image)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.ivBottomImage.visibleOrNot(true)
                binding.lyBottomTop.visibleOrNot(true)
                binding.tvSplashTitle.visibleOrNot(true)
                binding.ivBottomImage.startAnimation(anim2)

            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        anim2.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                lifecycleScope.launch {
                    delay(800)
                    navigateToStoreFragment()
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })

        binding.lyBottom.startAnimation(anim)
    }

    /** * Navigates to the store fragment */
    private fun navigateToStoreFragment() {
        val action = SplashFragmentDirections.actionSplashFragmentToStoreFragment()
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.splashFragment, true)
            .setEnterAnim(R.anim.slide_in)
            .setExitAnim(R.anim.slide_out)
            .setPopEnterAnim(R.anim.slide_in)
            .setPopExitAnim(R.anim.slide_out)
            .build()
        findNavController().navigate(
            action,
            navOptions
        )
    }
}
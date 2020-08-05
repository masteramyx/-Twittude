package com.example.twittude

import com.bluelinelabs.conductor.Controller
import com.example.twittude.ui.TwitMainController
import com.karakum.base.BaseControllerActivity

class MainActivity : BaseControllerActivity() {

    override fun getRootController(): Controller =
        TwitMainController()

}

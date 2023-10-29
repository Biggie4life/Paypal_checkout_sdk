package com.example.paypalcheckoutsdk

import android.app.Application
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction

class App : Application() {

    var clientID = "AY2j0F-O6hV9AYAccP3Gg8SPF6HjSyrAe83Lo0hUXvrVGDm78eFoxsNzsDA2NQglhrXqObUOegjhfjJo"
    var returnUrl = "com.example.paypalcheckoutsdk://paypalpay"

    override fun onCreate() {
        super.onCreate()

        val config = CheckoutConfig(
            application = this,
            clientId = clientID,
            environment = Environment.SANDBOX,
            returnUrl = returnUrl,
            currencyCode = CurrencyCode.USD,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = true
            )
        )
        PayPalCheckout.setConfig(config)
    }

}
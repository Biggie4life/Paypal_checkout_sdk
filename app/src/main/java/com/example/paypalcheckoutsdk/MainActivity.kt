package com.example.paypalcheckoutsdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.paypalcheckoutsdk.databinding.ActivityMainBinding
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.OrderRequest
import com.paypal.checkout.order.PurchaseUnit


class MainActivity : ComponentActivity() {

    lateinit var binding: ActivityMainBinding
    val TAG = "MyTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.paymentButtonContainer.setup(
            createOrder = CreateOrder { createOrderActions ->
                val customAmountEditText = findViewById<EditText>(R.id.customAmountEditText)
                val customAmountText = customAmountEditText.text.toString()
                val customAmount = if (customAmountText.isNotEmpty()) customAmountText else "10.00"

                val order = OrderRequest(
                    intent = OrderIntent.CAPTURE,
                    appContext = AppContext(userAction = UserAction.PAY_NOW),
                    purchaseUnitList = listOf(
                        PurchaseUnit(
                            amount = Amount(currencyCode = CurrencyCode.USD, value = customAmount)
                        )
                    )
                )
                createOrderActions.create(order)
            },
            onApprove =
            OnApprove { approval ->
                approval.orderActions.capture { captureOrderResult ->
                    Log.d(TAG, "CaptureOrderResult: $captureOrderResult")
                    Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
                }
            },
            onCancel = OnCancel {
                Log.d(TAG, "Buyer Cancelled This Purchase")
                Toast.makeText(this, "Payment Cancelled", Toast.LENGTH_SHORT).show()
            },
            onError = OnError { errorInfo ->
                Log.d(TAG, "Error: $errorInfo")
                Toast.makeText(this, "Payment Error", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
package com.example.javascriptinterfaceexample

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

  lateinit var webView: WebView
  lateinit var etNum1: EditText
  lateinit var etNum2: EditText
  lateinit var btMultiply: Button
  lateinit var btSum: Button

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    etNum1 = findViewById(R.id.et_num1)
    etNum2 = findViewById(R.id.et_num2)
    btMultiply = findViewById(R.id.bt_multiply)
    btSum = findViewById(R.id.bt_sum)

    webView = findViewById(R.id.wv_example)
    webView.settings.javaScriptEnabled = true
    webView.addJavascriptInterface(WebInterface(this), "WebInterface")
    webView.loadUrl("file:///android_asset/index.html");

    btMultiply.setOnClickListener {
      val num1 = etNum1.text
      val num2 = etNum2.text
      webView.evaluateJavascript("multiply($num1, $num2)", null)
    }

    btSum.setOnClickListener {
      val num1 = etNum1.text
      val num2 = etNum2.text
      webView.evaluateJavascript("sum($num1, $num2)", ValueCallback {
        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
      })
    }
  }


}

class WebInterface(private val context: Context) {
  @JavascriptInterface
  fun showResult(result: String){
    Toast.makeText(context, result, Toast.LENGTH_LONG).show()
  }
}
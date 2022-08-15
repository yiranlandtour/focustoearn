package com.threedust.app.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import com.just.agentweb.*
import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import com.threedust.app.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.icl_web_title.*

class WebActivity : BaseActivity() {

    private var mAgentWeb : AgentWeb? = null
    // chrome client
    private var mWebChromeClient : WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
        }
    }
    private var mWebViewClient : WebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        }
        override fun onPageFinished(view: WebView, url: String) {
        }
    }

    override fun layoutId(): Int {
        return R.layout.activity_web
    }

    override fun initView() {
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, ll_web_wrap)

        tv_title.text = intent.getStringExtra(WEB_TITLE)
        iv_head_back.setOnClickListener { finish() }
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(ll_web_container, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator(Color.parseColor("#22b0b2"), 1)
            //.setWebChromeClient(mWebChromeClient)
            //.setWebViewClient(mWebViewClient)
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setWebLayout(WebLayout(this))
            .setAgentWebWebSettings(CustomWebSettings())
            .createAgentWeb()
            .ready()
            .go(intent.getStringExtra(WEB_URL))
    }

    override fun initData() {
    }

    override fun retryRequest() {
    }

    companion object {
        val WEB_TITLE = "web_title"
        val WEB_URL = "web_url"

        fun createIntent(ctx: Context, url: String, title: String): Intent {
            val intent = Intent(ctx, WebActivity::class.java)
            intent.putExtra(WEB_URL, url)
            intent.putExtra(WEB_TITLE, title)
            return intent
        }
    }

    class CustomWebSettings : AgentWebSettingsImpl() {
        override fun toSetting(webView: WebView?): IAgentWebSettings<*>? {
            super.toSetting(webView)
            webSettings.mediaPlaybackRequiresUserGesture = false
            return this
        }
    }

    class WebLayout(ctx: Context) : IWebLayout<WebView, ViewGroup> {
        private var mWebView: WebView;
        private var mLayout: LinearLayout;

        init {
            mLayout = LayoutInflater.from(ctx).inflate(R.layout.view_agent_webview, null) as LinearLayout
            mWebView = mLayout.findViewById(R.id.agentWebView)
        }
        override fun getLayout(): ViewGroup {
            return mLayout
        }
        override fun getWebView(): WebView {
            return mWebView
        }
    }
}
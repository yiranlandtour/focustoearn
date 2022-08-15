package com.threedust.app.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.github.nukc.stateview.StateView
import com.gyf.immersionbar.ImmersionBar
import com.threedust.app.R
import com.threedust.app.api.BaseResponse
import com.threedust.app.api.RetrofitMgr
import com.threedust.app.rx.scheduler.SchedulerUtils
import com.threedust.app.utils.ConfigUtils
import com.threedust.app.utils.Logger
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import pub.devrel.easypermissions.EasyPermissions

/**
 * @author zzh
 * created:
 * desc:
 */

abstract class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks, IView {

    protected var mStateView: StateView? = null

    protected var mApi = RetrofitMgr.api

    private var mView: View? = null

    private var mCompositeDisposable = CompositeDisposable()

    companion object {
        var gActivityCount = 0
        var gCurrentActivity: AppCompatActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (mView == null) {
            mView = layoutInflater.inflate(layoutId(), null)
        }

        setContentView(mView)

        mView?.apply {
            mStateView = StateView.inject(this)
        }

        mStateView?.apply {
            loadingResource = R.layout.view_loading
            retryResource = R.layout.view_net_error
        }


        initView()

        initData()


        mStateView?.onRetryClickListener = object : StateView.OnRetryClickListener {
            override fun onRetryClick() {
                retryRequest()
            }
        }
        if (darkStatusBar())
            ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable.clear()
        }
    }

    fun addSubscription(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    fun <T> addSubscription(
        request: Observable<BaseResponse<T>>,
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit
    ) {
        return addSubscription(
            request.compose(SchedulerUtils.ioToMain())
                .subscribe({ r: BaseResponse<T> ->
                    if (r.code == BaseResponse.SUCESS) {
                        try { onSuccess(r.data) } catch (e: Exception){ Logger.e(e) }
                    } else {
                        onError(r.msg)
                    }
                }, { t ->
                    onError(t.toString())
                })
        )
    }

    fun <T> addSubscription(
        request: Observable<BaseResponse<T>>,
        onSuccess: (T) -> Unit
    ) {
        return addSubscription(request, onSuccess, { str ->
            Logger.e(str)
        })
    }

    abstract fun layoutId(): Int

    abstract fun initView()

    abstract fun initData()

    abstract fun retryRequest()

    open fun darkStatusBar(): Boolean { return true }

    fun openKeyboard(editText: EditText, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun closeKeyboard(editText: EditText, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }


    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

    }

    override fun showLoading() {
        mStateView?.showLoading()
    }

    override fun dismissLoading() {
        mStateView?.showContent()
    }

    fun showRetry() {
        mStateView?.showRetry()
    }

    fun showContent() {
        mStateView?.showContent()
    }

    override fun onStart() {
        super.onStart()
        gActivityCount += 1
    }

    override fun onStop() {
        super.onStop()
        gActivityCount -= 1
        if (gActivityCount == 0) {
            ConfigUtils.finalize()
        }
    }

    override fun onResume() {
        super.onResume()
        gCurrentActivity = this
    }

    override fun onPause() {
        super.onPause()
        gCurrentActivity = null
    }
}

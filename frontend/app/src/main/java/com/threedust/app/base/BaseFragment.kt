package com.threedust.app.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.github.nukc.stateview.StateView
import com.threedust.app.R
import com.threedust.app.api.BaseResponse
import com.threedust.app.api.RetrofitMgr
import com.threedust.app.rx.scheduler.SchedulerUtils
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

abstract class BaseFragment : Fragment(), EasyPermissions.PermissionCallbacks, IView {

    companion object {
        const val ARG_TITLE = "TIME"
    }

    var mTitle: String? = null

    protected var mStateView: StateView? = null

    protected var mApi = RetrofitMgr.api

    private var mView: View? = null

    private var mIsViewPrepared = false

    private var mIsDataLoaded = false

    private var mCompositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView == null) {
            mView = layoutInflater.inflate(layoutId(), null)
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsViewPrepared = true

        mTitle = arguments?.getString(ARG_TITLE, "")

        initView()

        mView?.apply {
            mStateView = StateView.inject(this)
        }

        mStateView?.apply {
            loadingResource = R.layout.view_loading
            retryResource = R.layout.view_net_error
        }


        mStateView?.onRetryClickListener = object : StateView.OnRetryClickListener {
            override fun onRetryClick() {
                lazyLoad()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable.clear()
        }
    }

    override fun onResume() {
        super.onResume()
        lazyLoadDataIfPrepared()
    }

    private fun lazyLoadDataIfPrepared() {
        if (mIsViewPrepared && !mIsDataLoaded) {
            lazyLoad()
            mIsDataLoaded = true
        }
    }

    private fun addSubscription(disposable: Disposable) {
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
                        onSuccess(r.data)
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

    abstract fun lazyLoad()


    override fun showLoading() {
        mStateView?.showLoading()
    }

    override fun dismissLoading() {
        mStateView?.showContent()
    }

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
}
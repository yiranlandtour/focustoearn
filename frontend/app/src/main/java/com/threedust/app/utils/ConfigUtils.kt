@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.threedust.app.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.threedust.app.MyApp
import com.threedust.app.api.BaseResponse
import com.threedust.app.model.entity.AppConf
import com.threedust.app.model.entity.TaskItem
import com.threedust.app.model.entity.User
import com.threedust.app.rx.scheduler.SchedulerUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

object ConfigUtils {


    private const val CONFIG_FILE_NAME = "config"

    private var mCompositeDisposable = CompositeDisposable()

    fun addSubscription(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }
    //
    fun finalize() {
        Logger.d("finalize")
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable.clear()
        }
    }

    // default
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
    // no onError
    fun <T> addSubscription(
        request: Observable<BaseResponse<T>>,
        onSuccess: (T) -> Unit
    ) {
        return addSubscription(request, onSuccess, { str ->
            Logger.e(str)
        })
    }

    // get appConf from local storage
    fun getAppConf(): AppConf {
        val appConf : AppConf? = JsonUtils.fromJson(getString("appConf", ""), AppConf::class.java)
        if (appConf == null) return AppConf()
        return appConf
    }

    // get user from local storage
    fun getUser(): User {
        var user : User? = JsonUtils.fromJson(getString("user", ""), User::class.java)
        if (user == null) user = User()
        MyApp.user = user
        return user
    }

    fun storeUser() {
        putString("user", JsonUtils.toJson(MyApp.user))
    }

    // whether login or not
    fun isLogin(): Boolean {
        return MyApp.user.wallet_id.isNotEmpty()
    }

    fun login(wallet_id : String) {
        MyApp.user.wallet_id = wallet_id
        storeUser()
    }

    fun logout() {
        MyApp.user.wallet_id = ""
        putString("phantom_session", "")
        putString("phantom_public_key", "")
        storeUser()
    }

    fun getSession() : String {
        return getString("phantom_session", "")
    }

    fun storeSession(session: String?) {
        session?.let { putString("phantom_session", it) }
    }

    fun getUserWalletPubKey() : String {
        return getString("phantom_user_public_key", "")
    }

    fun storeUserWalletPubKey(key: String?) {
        key?.let { putString("phantom_user_public_key", it) }
    }

    fun getPhantomEncPubKey() : String {
        return getString("phantom_public_key", "")
    }

    fun storePhantomEncPubKey(key: String?) {
        key?.let { putString("phantom_public_key", it) }
    }

    fun isHaveLand() : Boolean {
        return getBoolean("have_land", false)
    }

    fun recordBuyLand() {
        putBoolean("have_land", true)
    }

    fun isHaveTree() : Boolean {
        return getBoolean("have_tree", false)
    }

    fun recordBuyTree() {
        putBoolean("have_tree", true)
    }

    fun getUserCoinCount() : Float {
        return MyApp.user.coin_num
    }

    fun addUserCoinCount(coin: Float) {
        MyApp.user.coin_num += coin
        storeUser()
    }

    fun isFirstUseApp() : Boolean {
        return !getBoolean("not_app_first", false)
    }

    fun setNotFirstUseApp() {
        putBoolean("not_app_first", true)
    }

    fun storeTaskList(taskList: ArrayList<TaskItem>) {
        putString("key_task_list", JsonUtils.toJson(taskList))
    }

    fun getTaskList() : ArrayList<TaskItem> {
        val taskList : ArrayList<TaskItem>? = Gson().fromJson(getString("key_task_list", ""), object: TypeToken<ArrayList<TaskItem>>() {}.type)
        if (taskList == null || taskList.isEmpty()) return AppConf().task_list
        return taskList
    }

    fun putBoolean(key: String, value: Boolean) {
        val sp: SharedPreferences = MyApp.getContext()
            .getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        sp.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        val sp: SharedPreferences = MyApp.getContext()
            .getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(key, defValue)
    }

    fun putString(key: String, value: String) {
        val sp: SharedPreferences = MyApp.getContext()
            .getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        sp.edit().putString(key, value).apply()
    }

    fun getString(key: String, defValue: String): String {
        val sp: SharedPreferences = MyApp.getContext()
            .getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        return sp.getString(key, defValue)!!
    }

    fun putInt(key: String, value: Int) {
        val sp: SharedPreferences = MyApp.getContext()
            .getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        sp.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defValue: Int): Int {
        val sp: SharedPreferences = MyApp.getContext()
            .getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        return sp.getInt(key, defValue)
    }

    fun putLong(key: String, value: Long) {
        val sp: SharedPreferences = MyApp.getContext()
            .getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        sp.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defValue: Long): Long {
        val sp: SharedPreferences = MyApp.getContext()
            .getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        return sp.getLong(key, defValue)
    }

    fun putFloat(key: String, value: Float) {
        val sp: SharedPreferences = MyApp.getContext()
            .getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        sp.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String, defValue: Float): Float {
        val sp: SharedPreferences = MyApp.getContext()
            .getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
        return sp.getFloat(key, defValue)
    }
}
package com.threedust.app.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import com.google.gson.Gson
import com.solana.Solana
import com.solana.api.Api
import com.solana.core.Account
import com.solana.core.PublicKey
import com.solana.core.Transaction
import com.solana.programs.SystemProgram
import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import com.threedust.app.model.Constant
import com.threedust.app.model.entity.Payload
import com.threedust.app.model.entity.PtConnetDat
import com.threedust.app.utils.JsonUtils
import com.threedust.app.utils.Logger
import com.threedust.app.utils.nacl.NaCl
import kotlinx.android.synthetic.main.activity_wallet.*
import org.bitcoinj.core.Base58
import com.solana.api.*
import com.solana.models.RpcSendTransactionConfig
import com.solana.networking.OkHttpNetworkingRouter
import com.solana.networking.RPCEndpoint
import com.threedust.app.MyApp
import com.threedust.app.ui.dialog.SelectWalletDialog
import com.threedust.app.utils.ConfigUtils
import com.threedust.app.utils.SysUtils
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.RuntimeException
import java.util.*

class WalletActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_wallet

    override fun initView() {
        iv_back.setOnClickListener { finish() }
    }
    fun testTransaction() {

        val userPublickey = ConfigUtils.getUserWalletPubKey()
        val session = ConfigUtils.getSession()
        val nonce = NaCl.randomBytes(24)
        val userPublicKeyObj = PublicKey(userPublickey)
        val focusPublickeyObj = PublicKey(Constant.focusPublicKey)

        val transaction = Transaction()
        transaction.addInstruction(
            SystemProgram.transfer(
                userPublicKeyObj,
                focusPublickeyObj,
                100L
            )
        )
        transaction.setFeePayer(userPublicKeyObj)
        val solana = Solana(OkHttpNetworkingRouter(RPCEndpoint.devnetSolana))
        tv_btn_select.setOnClickListener {
            val sharedSecretDapp = NaCl.box.before(Base58.decode(ConfigUtils.getPhantomEncPubKey()), Constant.keyPair.second)
            solana.api.getRecentBlockhash { result ->
                result.map { recentBlockHash ->
                    transaction.setRecentBlockHash(recentBlockHash)
                    val encodeTransaction = Base58.encode(transaction.serialize())
                    val payload = Payload(encodeTransaction, session)

                    val encryptedPayload = NaCl.secretbox.seal(
                        JsonUtils.toJson(payload).toByteArray(),
                        nonce,
                        sharedSecretDapp
                    )

                    val uriBuilder =
                        Uri.parse("https://phantom.app/ul/v1/signAndSendTransaction").buildUpon()
                            .appendQueryParameter(
                                "dapp_encryption_public_key",
                                Base58.encode(Constant.keyPair.first)
                            )
                            .appendQueryParameter("nonce", Base58.encode(nonce))
                            .appendQueryParameter(
                                "redirect_link",
                                "focuson://focuson.xuyaomaoxian.com/TransactionActivity"
                            )
                            .appendQueryParameter("payload", Base58.encode(encryptedPayload))

                    val intent = Intent(Intent.ACTION_VIEW, uriBuilder.build())
                    startActivity(intent)
                }
            }
        }

    }

    override fun initData() {
        val data = intent.data
        Logger.d(data)
        data?.let { d ->
            val key = d.getQueryParameter("phantom_encryption_public_key")
            ConfigUtils.storePhantomEncPubKey(key)
            val nonce = d.getQueryParameter("nonce")
            val dat = d.getQueryParameter("data")
            val connectData = NaCl.box.open(
                Base58.decode(dat),
                Base58.decode(nonce),
                Base58.decode(key),
                Constant.keyPair.second
            )
            val dataString = connectData?.let { String(it) }
            val responseDat = JsonUtils.fromJson(dataString, PtConnetDat::class.java)
            responseDat?.let {
                Logger.d(it.public_key)
                ConfigUtils.storeSession(it.session)
                ConfigUtils.storeUserWalletPubKey(it.public_key)
                ConfigUtils.login(it.public_key)
                refreshView()
            }
        }

        tv_btn_select.setOnClickListener {
            SelectWalletDialog(this).walletBtnClickListener {
                finish()
            }.title("Select Wallet").show()
        }

        ll_wallet_id.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Label", MyApp.user.wallet_id)
            clipboardManager.setPrimaryClip(clipData)
            SysUtils.showToast("Wallet public key copied to clipboard.")
        }

        refreshView()

/*
        Logger.d(data?.queryParameterNames)
        val key = data?.getQueryParameter("phantom_encryption_public_key")
        val nonce = data?.getQueryParameter("nonce")
        val dat = data?.getQueryParameter("data")

        //val sharedSecretDapp = NaCl.box.before(Base58.decode(key), Constant.secretKey.toByteArray())
        Logger.d(key)
        ConfigUtils.storePhantomEncPubKey(key)

//        Logger.d(Base58.encode(Constant.keyPair.first))
//        Logger.d(Base58.encode(Constant.keyPair.second))

        val connectData = NaCl.box.open(
            Base58.decode(dat),
            Base58.decode(nonce),
            Base58.decode(key),
            Constant.keyPair.second
        )

        val dataString = connectData?.let { String(it) }
        val d = JsonUtils.fromJson(dataString, PtConnetDat::class.java)
        ConfigUtils.storeSession(d?.session)
        ConfigUtils.storeUserWalletPubKey(d?.public_key)
        //Logger.d(d?.session, d?.public_key)

        /*
        val sharedSecretDapp = NaCl.box.before(Base58.decode(key), Constant.keyPair.second)
        val session = d!!.session
        val walletPublicKey = PublicKey(d.public_key)


        val transaction = Transaction()
        transaction.addInstruction(
            SystemProgram.transfer(
                PublicKey(d!!.public_key),
                PublicKey(Constant.focusPublicKey),
                1L
            )
        )
        transaction.setFeePayer(PublicKey(d.public_key))

        val solana = Solana(OkHttpNetworkingRouter(RPCEndpoint.devnetSolana))

        solana.api.getRecentBlockhash { result ->
            result.map { recentBlockHash ->

                Logger.d(recentBlockHash)
                transaction.setRecentBlockHash(recentBlockHash)
                val encodeTransaction = Base58.encode(transaction.serialize())
                val payload = Payload(encodeTransaction, session)
                val nonce = NaCl.randomBytes(24)
                val encryptedPayload = NaCl.secretbox.seal(
                    JsonUtils.toJson(payload).toByteArray(),
                    nonce,
                    sharedSecretDapp
                )


                val uriBuilder =
                    Uri.parse("https://phantom.app/ul/v1/signAndSendTransaction").buildUpon()
                        //.appendQueryParameter("dapp_encryption_public_key", Base58.encode(Constant.keyPair.first))
                        //.appendQueryParameter("nonce", Base58.encode(nonce))
                        .appendQueryParameter("redirect_link", "focuson://focuson.xuyaomaoxian.com")
                //.appendQueryParameter("payload", Base58.encode(encryptedPayload))

                val intent = Intent(Intent.ACTION_VIEW, uriBuilder.build())
                startActivity(intent)

            }
        }
*/

        //Logger.d(dataString)

        val walletData = Base58.decode(data?.getQueryParameter("data"))
        //Logger.d()
        //Logger.d(data?.toString())

        testTransaction()

        //mintCoin()
        */

    }

    fun refreshView() {
        if (ConfigUtils.isLogin()) {
            ll_empty.visibility = View.GONE
            ll_coin.visibility = View.VISIBLE
            tv_btn_select.visibility = View.GONE
            ll_wallet_id.visibility = View.VISIBLE

            tv_wallet_id.text = MyApp.user.wallet_id
            tv_coin_num.text = MyApp.user.coin_num.toString() + " SOL"

        } else {
            ll_empty.visibility = View.VISIBLE
            ll_coin.visibility = View.GONE
            ll_wallet_id.visibility = View.GONE
            tv_btn_select.visibility = View.VISIBLE
        }
    }

    fun mintCoin() {
        val userPublickey = ConfigUtils.getUserWalletPubKey()
        val nonce = NaCl.randomBytes(24)
        val userPublicKeyObj = PublicKey(userPublickey)
        val focusPublickeyObj = PublicKey(Constant.focusPublicKey)
        val focusAccount = Account(Base58.decode(Constant.focusSecretKey))

        val transaction = Transaction()
        transaction.addInstruction(
            SystemProgram.transfer(
                focusPublickeyObj,
                userPublicKeyObj,
                100L
            )
        )
        transaction.setFeePayer(focusPublickeyObj)
        //transaction.sign(focusAccount)


        val solana = Solana(OkHttpNetworkingRouter(RPCEndpoint.devnetSolana))




        tv_btn_select.setOnClickListener {
            solana.api.getRecentBlockhash { result ->
                result.map { recentBlockHash ->
                    solana.api.sendTransaction(transaction, arrayListOf(focusAccount), recentBlockHash) {
                        it.map {
                            Logger.d(it)
                        }
                    }
                }
            }
        }
    }

    override fun retryRequest() {

    }
}
package com.threedust.app.ui.dialog

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import com.solana.Solana
import com.solana.api.getRecentBlockhash
import com.solana.core.PublicKey
import com.solana.core.Transaction
import com.solana.networking.OkHttpNetworkingRouter
import com.solana.networking.RPCEndpoint
import com.solana.programs.SystemProgram
import com.threedust.app.R
import com.threedust.app.base.BaseDialog
import com.threedust.app.model.Constant
import com.threedust.app.model.entity.Payload
import com.threedust.app.utils.JsonUtils
import com.threedust.app.utils.Logger
import com.threedust.app.utils.nacl.NaCl
import kotlinx.android.synthetic.main.dialog_wallet_select.*
import org.bitcoinj.core.Base58
import org.bouncycastle.jcajce.provider.symmetric.ARC4

class SelectWalletDialog(context: Context) : BaseDialog(context) {

    init {
        setContentView(R.layout.dialog_wallet_select)
        walletBtnClickListener()
    }
    fun title(titleStr: String): SelectWalletDialog {
        tv_title.text = titleStr
        return this
    }

    fun walletBtnClickListener(listener: (()->Unit)? = null): SelectWalletDialog {
        /*
        val userPublickey = "3RaLFRSay1mu1ppHvKASLvVjZ6XzWfy7xRDsEupq36xE"
        val session = "3NFRXMawJjF4NT2rdFdjjxu2RTeLq1A9q7jifhaxMrzPw2hyzwszZn5PrNUpQwmXZhpbHRZ39EkCNX2FPnhMUNc41xL9gLh9xtzjRUWSrnsXiRLGgEMrT7PwwXr4V5RV4VyceyZJ4vHgZdq6AnST7zmFnWWK58vEmBW15Uu3jWBYGAV9GtpnvGwSgoiuvZBDzoagngtdQZxTpvtnBwHRKJNzYdyruoX5DscgmTzA5A"
        val nonce = NaCl.randomBytes(24)
        val userPublicKeyObj = PublicKey(userPublickey)
        val focusPublickeyObj = PublicKey(Constant.focusPublicKey)
        val sharedSecretDapp = NaCl.box.before(userPublickey.toByteArray(), Constant.keyPair.second)

        val transaction = Transaction()
        transaction.addInstruction(SystemProgram.transfer(userPublicKeyObj, focusPublickeyObj, 100L))
        transaction.setFeePayer(userPublicKeyObj)
        val solana = Solana(OkHttpNetworkingRouter(RPCEndpoint.devnetSolana))
        tv_btn_wallet.setOnClickListener {
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
                    context.startActivity(intent)
                }
            }
            dismiss()
        }

        */


        tv_btn_wallet.setOnClickListener {
            val uriBuilder = Uri.parse("https://phantom.app/ul/v1/connect").buildUpon()
            uriBuilder.appendQueryParameter("app_url", "http://focuson.xuyaomaoxian.com")
                .appendQueryParameter("dapp_encryption_public_key", Base58.encode(Constant.keyPair.first))
                .appendQueryParameter("redirect_link", "focuson://focuson.xuyaomaoxian.com")
                .appendQueryParameter("cluster", "devnet")
            val intent = Intent(ACTION_VIEW, uriBuilder.build())
            context.startActivity(intent)
            listener?.let { it() }
            dismiss()
        }
        return this
    }
}
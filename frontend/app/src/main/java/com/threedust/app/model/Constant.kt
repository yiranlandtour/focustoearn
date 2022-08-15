package com.threedust.app.model

import com.threedust.app.utils.nacl.NaCl

object Constant {

    //val publicKey = "6mykzhCRDzJvFdfc6GxUWHgTEG5R6qcBQpY6UfK2Kg8n"
    //val secretKey = "3GScJKt7tQhmwZ8dju7HWuVYa3Eh5gKkQP2sJDxpFgmT1evn8hpWZAnxqrNPi1nnPBpikCjYuYhLCFhASTRpSmqD"
    val keyPair = NaCl.box.keyPair()
}